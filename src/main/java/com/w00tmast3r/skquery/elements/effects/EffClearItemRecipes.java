package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.Reflection;

import org.bukkit.event.Event;

import java.lang.reflect.InvocationTargetException;

@Name("Clear Single Recipes")
@Description("Clears all recipes for a single item.")
@Examples("on script load:;->wipe crafting recipes for stick and iron pickaxe")
@Patterns("wipe crafting recipes for %itemtypes%")
public class EffClearItemRecipes extends Effect {

	private Expression<ItemType> items;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		items = (Expression<ItemType>) expressions[0];
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void execute(Event event) {
		for (ItemType type : items.getArray(event)) {
			try {
				Object craftingInstance = Reflection.getNMSClass("CraftingManager").getMethod("getInstance").invoke(null);
				Object recipes = Reflection.getField(craftingInstance.getClass(), "recipes").get(craftingInstance);
				Reflection.getMethod(recipes.getClass(), "remove").invoke(recipes, type.getRandom().getType().getId());
			} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "clear recipes " + items.toString(event, debug);
	}

}
