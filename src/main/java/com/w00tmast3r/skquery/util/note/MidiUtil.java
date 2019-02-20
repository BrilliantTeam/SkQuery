package com.w00tmast3r.skquery.util.note;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import ch.njol.skript.Skript;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MidiUtil {
	
	private static HashMap<String, Sequencer> playing = new HashMap<>();
	
	private static void playMidi(Sequence sequence, float tempo, String ID, Player... listeners) {
		try {
			Sequencer sequencer = MidiSystem.getSequencer(false);
			sequencer.setSequence(sequence);
			sequencer.open();

			// slow it down just a bit
			sequencer.setTempoFactor(tempo);
			NoteBlockReceiver reciever = new NoteBlockReceiver(ID, listeners);
			sequencer.getTransmitter().setReceiver(reciever);
			sequencer.start();
			playing.put(ID.toLowerCase(), sequencer);
		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			Skript.exception(e, "Error attempting to play a midi file");
		}
	}
	
	public static void playMidi(File file, float tempo, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidi(MidiSystem.getSequence(file), tempo, ID, listeners);
	}

	public static void playMidi(InputStream stream, float tempo, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidi(MidiSystem.getSequence(stream), tempo, ID, listeners);
	}

	public static void playMidiQuietly(File file, float tempo, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidi(file, tempo, ID, listeners);
	}
	
	public static void playMidiQuietly(InputStream stream, float tempo, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidi(stream, tempo, ID, listeners);
	}

	public static void playMidiQuietly(File file, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidiQuietly(file, 1.0f, ID, listeners);
	}

	public static void playMidiQuietly(InputStream stream, String ID, Player... listeners) throws InvalidMidiDataException, IOException {
		playMidiQuietly(stream, 1.0f, ID, listeners);
	}
	
	public static boolean isPlaying(String ID) {
		return playing.containsKey(ID);
	}
	
	public static void stop(String ID) {
		String id = ID.toLowerCase();
		if (playing.containsKey(id)) {
			Sequencer sequencer = playing.get(id);
			try {
				sequencer.getReceiver().close();
			} catch (MidiUnavailableException e) {}
			sequencer.stop();
			sequencer.close();
			playing.remove(id);
		}
	}

	// provided by github.com/sk89q/craftbook
	private static final int[] instruments = {
			0, 0, 0, 0, 0, 0, 0, 5, // 8
			9, 9, 9, 9, 9, 6, 0, 9, // 16
			9, 0, 0, 0, 0, 0, 0, 5, // 24
			5, 5, 5, 5, 5, 5, 5, 1, // 32
			1, 1, 1, 1, 1, 1, 1, 5, // 40
			1, 5, 5, 5, 5, 5, 5, 5, // 48
			5, 5, 5, 8, 8, 8, 8, 8, // 56
			8, 8, 8, 8, 8, 8, 8, 8, // 64
			8, 8, 8, 8, 8, 8, 8, 8, // 72
			8, 8, 8, 8, 8, 8, 8, 8, // 80
			0, 0, 0, 0, 0, 0, 0, 0, // 88
			0, 0, 0, 0, 0, 0, 0, 0, // 96
			0, 0, 0, 0, 0, 0, 0, 5, // 104
			5, 5, 5, 9, 8, 5, 8, 6, // 112
			6, 3, 3, 2, 2, 2, 6, 5, // 120
			1, 1, 1, 6, 1, 2, 4, 7, // 128
	};
	
	private static final int[] percussion = {
			9, 6, 4, 4, 3, 2, 3, 2, //40 - Electric Snare
			2, 2, 2, 2, 2, 2, 2, 2, //48 - Hi Mid Tom
			7, 2, 7, 7, 6, 3, 7, 6, //56 - Cowbell
			7, 3, 7, 2, 2, 3, 3, 3, //64 - Low Conga
			2, 2, 6, 6, 2, 2, 0, 0, //72 - Long Whistle
			3, 3, 3, 3, 3, 3, 5, 5, //80 - Open Cuica
			10, 10,				    //82 - Open Triangle
	};
	
	private static byte bytePercussion(Integer patch) {
		if (patch == null)
			return 0;
		int i = patch - 33;
		if (i < 0 || i >= percussion.length)
			return 1;
		return (byte) percussion[i];
}
	
	private static byte byteInstrument(Integer patch) {
		if (patch == null)
			return 0;
		if (patch < 0 || patch >= instruments.length)
			return 0;
		return (byte) instruments[patch];
	}
	
	public static Sound patchToPercussion(int patch) {
		return Instrument.fromByte(bytePercussion(patch)).getSound();
	}

	public static Sound patchToInstrument(int patch) {
		return Instrument.fromByte(byteInstrument(patch)).getSound();
	}

}
