package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.event.Event;
import org.bukkit.inventory.meta.FireworkMeta;

import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;

@Name("Launch Fireworks")
@Description("Launch a firework with any number of firework effects at a given location and flight duration. Use (../expressions/(ExprFireworkEffect)this expression) to create firework effects.")
@Examples("on death:;->launch creeper firework colored red at victim timed 1")
@Patterns("(launch|deploy) %fireworkeffects% at %locations% (with duration|timed) %number%")
public class EffLaunchFirework extends Effect {

	private Expression<FireworkEffect> effects;
	private Expression<Location> locations;
	private Expression<Number> lifetime;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		effects = (Expression<FireworkEffect>) expressions[0];
		locations = (Expression<Location>) expressions[1];
		lifetime = (Expression<Number>) expressions[2];
		return true;
	}
	
	@Override
	protected void execute(Event event) {
		Number power = lifetime.getSingle(event);
		if (power == null) return;
		for (Location location : locations.getAll(event)){
			Firework firework = location.getWorld().spawn(location, Firework.class);
			FireworkMeta meta = firework.getFireworkMeta();
			meta.setPower(power.intValue());
			meta.addEffects(effects.getArray(event));
			firework.setFireworkMeta(meta);
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "launch firework effects " + effects.toString(event, debug) + " at " + locations.toString(event, debug);
	}

}
