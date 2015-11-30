package org.devel;

import javax.sound.midi.*;

/**
 * Created by stefan.illgen on 30.11.2015.
 */
public class SynthesizerTest0 {

    public static final int MAX_PITCH_BEND = 16383;
    private static final int CENTER_PITCH_BEND = 8192;
    private static final int MIN_PITCH_BEND = 0;

    public static void main(String[] args) throws MidiUnavailableException, InterruptedException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();

        // Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();
        Instrument[] instruments = synth.getAvailableInstruments();
        Instrument instrument = instruments[16];
        synth.loadInstrument(instrument);

        final MidiChannel[] mc = synth.getChannels();
        mc[1].programChange(instrument.getPatch().getBank(), instrument.getPatch().getProgram());

        mc[1].noteOn(40,600);

        mc[1].setPitchBend(MAX_PITCH_BEND);
        Thread.sleep( 2000 );
        mc[1].setPitchBend(MIN_PITCH_BEND);
        Thread.sleep( 2000 );
        mc[1].setPitchBend(CENTER_PITCH_BEND);
        Thread.sleep( 2000 );
    }

}
