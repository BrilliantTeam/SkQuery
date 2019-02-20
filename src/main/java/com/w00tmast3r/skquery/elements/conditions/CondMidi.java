package com.w00tmast3r.skquery.elements.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.note.MidiUtil;

@Name("Midi is playing")
@Description("Checks whether or not a midi ID is playing.")
@Patterns({"midi [ids] %strings% (are|is) playing", "midi [ids] %strings% (are|is)(n't| not) playing"})
public class CondMidi extends Condition {

	private Expression<String> ID;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		ID = (Expression<String>) expressions[0];
		return true;
	}
	
	@Override
	public boolean check(Event event) {
		String title = ID.getSingle(event);
		return MidiUtil.isPlaying(title);
	}
	
	@Override
	public String toString(Event event, boolean debug) {
		return "midi " + ID.toString(event, debug) + " is playing";
	}

}
