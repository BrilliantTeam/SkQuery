package com.w00tmast3r.skquery.util.note;

import org.bukkit.Note;
import org.bukkit.Note.Tone;

import javax.sound.midi.ShortMessage;

public class ToneUtil {

	@SuppressWarnings("deprecation")
	private static final byte BASE_NOTE = new Note(1, Tone.F, true).getId();
	private static final int MIDI_BASE_FSHARP = 54;

	@SuppressWarnings("deprecation")
	public static double noteToPitch(Note note) {
		double semitones = note.getId() - BASE_NOTE;
		return Math.pow(2.0, semitones / 12.0);
	}

	public static Note midiToNote(ShortMessage message) {
		int semitones = message.getData1() - MIDI_BASE_FSHARP % 12;
		return new Note(semitones % 24);
	}

	public static float midiToPitch(ShortMessage message) {
		return (float) noteToPitch(midiToNote(message));
	}

}
