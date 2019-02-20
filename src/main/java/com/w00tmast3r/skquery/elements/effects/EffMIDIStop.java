package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.note.MidiUtil;

import org.bukkit.event.Event;

@Name("Stop MIDI")
@Description("Stops a midi file that is playing.")
@Examples("on join:;->play midi \"login\" to player;wait 5 seconds;stop midi \"login\"")
@Patterns("stop midi [id] %string%")
public class EffMIDIStop extends Effect {

	private Expression<String> ID;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int markedPattern, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		ID = (Expression<String>) expressions[0];
		return true;
	}
	
	@Override
	protected void execute(Event event) {
		String track = ID.getSingle(event);
		if (track == null)
			return;
		if (MidiUtil.isPlaying(track))
			MidiUtil.stop(track);
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "stop midi " + ID.toString(event, debug);
	}

}
