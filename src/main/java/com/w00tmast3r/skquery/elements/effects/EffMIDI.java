package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.custom.note.MidiUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;

@Name("Play MIDI")
@Description("Plays a file with the extention .mid to a player.")
@Examples("on join:;->play midi \"login\" to player")
@Patterns("play midi [from (website|link)] %string% (for|to) %players% [at [tempo] %-number%] [with id %-string%]")
public class EffMIDI extends Effect {

	private Expression<String> midi, ID;
	private Expression<Player> players;
	private Expression<Number> tempo;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		midi = (Expression<String>) expressions[0];
		players = (Expression<Player>) expressions[1];
		tempo = (Expression<Number>) expressions[2];
		ID = (Expression<String>) expressions[3];
		return true;
	}
	
	@Override
	protected void execute(Event event) {
		String track = midi.getSingle(event);
		if (track == null) return;
		File file = new File(Skript.getInstance().getDataFolder().getAbsolutePath() + File.separator + Skript.SCRIPTSFOLDER + File.separator + track + ".mid");
		boolean check = false;
		if (!file.exists()) {
			file = new File(track);
			if (!file.exists() && !file.getName().endsWith(".mid")) {
				check = true;
				Bukkit.getLogger().warning("Could not find midi file " + track + ".mid");
				return;
			}
		}
		Number t = 1;
		if (t!= null) t = tempo.getSingle(event);
		Player[] p = players.getArray(event);
		String i = track;
		if (i != null) i = ID.getSingle(event);
		if (p == null) return;
		try {
			if (check) {
				URL url = new URL(track);
				if (url != null) MidiUtil.playMidi(url.openStream(), t.floatValue(), p, i);
			} else {
				MidiUtil.playMidi(file, t.floatValue(), p, i);
			}
		} catch (InvalidMidiDataException | IOException e) {
			Skript.exception(e, "Could not play midi file " + track);
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "midi " + midi.toString(event, debug) + " to " + players.toString(event, debug);
	}

}
