package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Patterns;

@Name("Clear Server Recipes")
@Description("Clears all recipes registered in the server. Use ((EffClearItemRecipe)this effect) to clear individual recipes.")
@Examples("on script load:;->wipe server crafting recipes")
@Patterns("wipe server crafting recipes")
public class EffClearRecipes extends Effect {

	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		return true;
	}
	
	@Override
	protected void execute(Event event) {
		Bukkit.getServer().clearRecipes();
	}

	@Override
	public String toString(Event event, boolean b) {
		return "clear recipes";
	}

}
