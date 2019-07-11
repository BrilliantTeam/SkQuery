package com.w00tmast3r.skquery.elements.expressions;

import org.bukkit.Location;
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
@PropertyFrom("worldborders")
@PropertyTo("[world[ ]border[s]] center")
public class ExprWorldBorderCenter extends SimplePropertyExpression<WorldBorder, Location> {

	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}

	@Override
	protected String getPropertyName() {
		return "world border";
	}

	@Override
	@Nullable
	public Location convert(WorldBorder border) {
		return border.getCenter();
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return Collect.asArray(Location.class);
		return null;
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (delta[0] == null)
			return;
		Location location = (Location) delta[0];
		for (WorldBorder border : getExpr().getArray(event))
			border.setCenter(location);
	}

}
