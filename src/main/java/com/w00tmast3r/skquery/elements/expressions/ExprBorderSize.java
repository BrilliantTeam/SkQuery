package com.w00tmast3r.skquery.elements.expressions;

import org.bukkit.WorldBorder;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;

@UsePropertyPatterns
@PropertyFrom("worldborders")
@PropertyTo("[world[ ]border[s]] (size|diameter) [over [a [(time|period) of]] %-timespan%]")
public class ExprBorderSize extends PropertyExpression<WorldBorder, Number> {

	private Expression<Timespan> timespan;

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		setExpr((Expression<WorldBorder>) exprs[1 - matchedPattern]);
		timespan = (Expression<Timespan>) exprs[matchedPattern];
		return true;
	}

	@Override
	protected Number[] get(Event event, WorldBorder[] source) {
		return get(source, border -> border.getSize());
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "size of world borders " + getExpr().toString(event, debug) + " over time " + timespan.toString(event, debug);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE || mode == ChangeMode.ADD)
			return Collect.asArray(Number.class);
		return null;
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (delta[0] == null || timespan == null)
			return;
		Timespan time = timespan.getSingle(event);
		double amount = ((Number) delta[0]).doubleValue();
		long milliseconds = time == null ? -1L : time.getMilliSeconds() / 1000L;
		switch (mode) {
			case SET:
				for (WorldBorder border : getExpr().getArray(event)) {
					if (milliseconds == -1L)
						border.setSize(amount);
					else
						border.setSize(amount, milliseconds);
				}
				break;
			case ADD:
				for (WorldBorder border : getExpr().getArray(event)) {
					double size = border.getSize();
					if (milliseconds == -1L)
						border.setSize(size + amount);
					else
						border.setSize(size + amount, milliseconds);
				}
				break;
			case REMOVE:
				for (WorldBorder border : getExpr().getArray(event)) {
					double size = border.getSize();
					if (milliseconds == -1L)
						border.setSize(size - amount);
					else
						border.setSize(size - amount, milliseconds);
				}
				break;
			case REMOVE_ALL:
			case DELETE:
			case RESET:
			default:
				break;
		}
	}

}
