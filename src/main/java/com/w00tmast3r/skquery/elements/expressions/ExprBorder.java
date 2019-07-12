package com.w00tmast3r.skquery.elements.expressions;

import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

@UsePropertyPatterns
@PropertyFrom("worlds")
@PropertyTo("world[ ]border[s]")
public class ExprBorder extends SimplePropertyExpression<World, WorldBorder> {

	@Override
	public Class<? extends WorldBorder> getReturnType() {
		return WorldBorder.class;
	}

	@Override
	protected String getPropertyName() {
		return "world border";
	}

	@Override
	@Nullable
	public WorldBorder convert(World world) {
		return world.getWorldBorder();
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.RESET || mode == ChangeMode.DELETE)
			return Collect.asArray(Object.class);
		return null;
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		for (World world : getExpr().getArray(event))
			world.getWorldBorder().reset();
	}

}
