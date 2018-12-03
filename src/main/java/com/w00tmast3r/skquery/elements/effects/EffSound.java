package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.Event;

@Name("Play Raw Sound")
@Description("Imitates the functionality of the /playsound command, without the ability to specify target players.")
@Examples("on sneak toggle:;->play raw sound \"mob.bat.death\" at player with pitch 1 volume 10")
@Patterns("play [raw] sound %string/sound% at %locations% with pitch %number% [and] volume %number%")
public class EffSound extends Effect {

	private Expression<Object> sound;
	private Expression<Location> locations;
	private Expression<Number> pitch, volume;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		sound = (Expression<Object>) expressions[0];
		locations = (Expression<Location>) expressions[1];
		pitch = (Expression<Number>) expressions[2];
		volume = (Expression<Number>) expressions[3];
		return true;
	}

	@Override
	protected void execute(Event event) {
		String s = null;
		Object object = sound.getSingle(event);
		if (object instanceof Sound) s = ((Sound)object).name();
		else s = ((String)object);
		float p = pitch.getSingle(event).floatValue();
		float v = volume.getSingle(event).floatValue();
		if (s == null) return;
		Sound spigotSound = Sound.valueOf(s);
		for (Location location : locations.getArray(event)) {
			if (spigotSound != null) location.getWorld().playSound(location, spigotSound, v, p);
			else location.getWorld().playSound(location, s, v, p);
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "sound " + sound.toString(event, debug);
	}

}
