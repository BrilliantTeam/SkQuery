/**
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Copyright 2011-2017 Peter Güttinger and contributors
 */
package ch.njol.skript.expressions.base;

import java.lang.reflect.ParameterizedType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAPIException;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.SyntaxElement;
import ch.njol.skript.lang.util.ConvertedLiteral;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.lang.util.SimpleLiteral;
import ch.njol.skript.registrations.Converters;
import ch.njol.util.Kleenean;

/**
 * Represents an expression that may only work on the defined Entity.
 */
public abstract class EntityExpression<E extends Entity, T> extends SimpleExpression<T> {
	
	@SuppressWarnings("null")
	private Class<E> entity;
	
	@SuppressWarnings("null")
	private Expression<E> expression;
	
	public int mark;
	
	/**
	 * Registers an entity expression as {@link ExpressionType#PROPERTY} with the two default property patterns "property of %types%" and "%types%'[s] property"
	 * 
	 * @param c The expression class itself.
	 * @param type The class type that this expression should return, such as a Number or String.
	 * @param property The property name for the syntax, for example <i>shoulder</i> in <i>shoulder of %entities%</i>
	 */
	protected static <E, C extends Expression<T>, T> void register(Class<C> c, Class<T> type, String property) {
		Skript.registerExpression(c, type, ExpressionType.PROPERTY, "[the] " + property + " of %entities%", "%entities%'[s] " + property);
	}
	
	@SuppressWarnings({"null", "unchecked"})
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		parseResult.
		mark = parseResult.mark;
		entity = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return false;
	}
	
	public final Expression<? extends E> getExpr() {
		return expression;
	}
	
	@SuppressWarnings({"null", "unchecked"})
	@Override
	public Class<T> getReturnType() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@SuppressWarnings("null")
	@Override
	protected final T[] get(Event e) {
		for (Entity entity : expression.getArray(e)) {
			if (!entity.getClass().equals(this.entity)) {
				Skript.error("The entities inputted is not of a " + this.entity.getSimpleName() + ". This syntax only accepts those entities.");
				return null;
			}
		}
		return get(e, expression.getArray(e));
	}
	
	@SuppressWarnings("null")
	@Override
	public final T[] getAll(Event e) {
		for (Entity entity : expression.getAll(e)) {
			if (!entity.getClass().equals(this.entity)) {
				Skript.error("The entities inputted is not of a " + this.entity.getSimpleName() + ". This syntax only accepts those entities.");
				return null;
			}
		}
		return get(e, expression.getAll(e));
	}
	
	/**
	 * Converts the given source object(s) to the correct type.
	 * <p>
	 * Please note that the returned array must neither be null nor contain any null elements!
	 * 
	 * @param e
	 * @param source
	 * @return An array of the converted entities, which may contain less elements than the source array, but must not be null.
	 * @see Converters#convert(Object[], Class, Converter)
	 */
	protected abstract T[] get(Event e, E[] source);
	
	/**
	 * @param source
	 * @param converter must return instances of {@link #getReturnType()}
	 * @return An array containing the converted values
	 * @throws ArrayStoreException if the converter returned invalid values
	 */
	protected T[] get(E[] source, Converter<? super E, ? extends T> converter) {
		assert source != null;
		assert converter != null;
		return Converters.convertUnsafe(source, getReturnType(), converter);
	}

	@Override
	public final boolean isSingle() {
		return expression.isSingle();
	}
	
	@Override
	public final boolean getAnd() {
		return expression.getAnd();
	}
	
}
