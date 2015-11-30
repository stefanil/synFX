package org.devel.javax.sound;

import javax.sound.midi.*;

/**
 * Created by stefan.illgen on 30.11.2015.
 */
public class SynthesizerTest2 {

    public static void main(String[] args) throws InvalidMidiDataException, MidiUnavailableException, InterruptedException {
        // Play the note Middle C (60) moderately loud
        // (velocity = 93)on channel 4 (zero-based).
        ShortMessage myMsg = new ShortMessage();
        myMsg.setMessage(ShortMessage.NOTE_ON, 4, 60, 93);

        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();

        Receiver synthRcvr = synth.getReceiver();
        synthRcvr.send(myMsg, -1); // -1 means no time stamp

        Thread.sleep(5000);
    }
}
