package com.w00tmast3r.skquery.util.note;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NoteBlockReceiver implements Receiver {
	
	private final Map<Integer, Integer> patches = new HashMap<>();
	private final float VOLUME_RANGE = 10.0f;
	private final Player[] listeners;
	private final String ID;

	public NoteBlockReceiver(String ID, Player... listeners) {
		this.listeners = listeners;
		this.ID = ID;
	}

	@Override
	public void send(MidiMessage midi, long time) {
		if (midi instanceof ShortMessage) {
			ShortMessage message = (ShortMessage) midi;
			int channel = message.getChannel();
			switch (message.getCommand()) {
				case ShortMessage.PROGRAM_CHANGE:
					int patch = message.getData1();
					patches.put(channel, patch);
					break;
				case ShortMessage.NOTE_ON:
					float volume = VOLUME_RANGE * (message.getData2() / 127.0f);
					float pitch = (float) ToneUtil.midiToPitch(message);
					Sound instrument = Instrument.PLING.getSound();
					Optional<Integer> optional = Optional.ofNullable(patches.get(message.getChannel()));
					if (optional.isPresent()) {
						if (channel == 9) {
							instrument = MidiUtil.patchToInstrument(optional.get());
						} else {
							instrument = MidiUtil.patchToInstrument(optional.get());
						}
					}
					for (Player player : listeners)
						player.playSound(player.getLocation(), instrument, volume, pitch);
					break;
				case ShortMessage.NOTE_OFF:
				default:
					break;
			}
		}
	}

	@Override
	public void close() {
		MidiUtil.stop(ID);
		patches.clear();
	}

}
