package com.w00tmast3r.skquery.elements.expressions;

import org.bukkit.WorldBorder;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.w00tmast3r.skquery.annotations.PropertyFrom;
import com.w00tmast3r.skquery.annotations.PropertyTo;
import com.w00tmast3r.skquery.annotations.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

@UsePropertyPatterns
@PropertyFrom("worldborders")
@PropertyTo("[world[ ]border[s]] warning distance")
public class ExprBorderWarningDistance extends SimplePropertyExpression<WorldBorder, Number> {

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	protected String getPropertyName() {
		return "warning distance";
	}

	@Override
	@Nullable
	public Number convert(WorldBorder border) {
		return border.getWarningDistance();
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return Collect.asArray(Number.class);
		return null;
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (delta[0] == null)
			return;
		Number amount = (Number) delta[0];
		for (WorldBorder border : getExpr().getArray(event))
			border.setWarningDistance(amount.intValue());
	}

}
