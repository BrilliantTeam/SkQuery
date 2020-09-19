package com.w00tmast3r.skquery.elements.conditions;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.conditions.base.PropertyCondition.PropertyType;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Patterns;

@Name("Is Block")
@Description("Checks whether or not a certain itemtype is a placeable block.")
@Patterns({"%itemtype% is [a] block", "%itemtype% (isn't|is not) [a] block"})
public class CondIsBlock extends Condition {

	private Expression<ItemType> itemtype;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		itemtype = (Expression<ItemType>) expressions[0];
		setNegated(matchedPattern == 1);
		return true;
	}
	
	@Override
	public boolean check(Event event) {
		return isNegated() ? !itemtype.getSingle(event).hasBlock() : itemtype.getSingle(event).hasBlock();
	}

	@Override
	public String toString(Event event, boolean debug) {
		return PropertyCondition.toString(this, PropertyType.BE, event, debug, itemtype, "block");
	}

}
