package org.devel.synfx.beads;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.WavePlayer;

/**
 * Created by stefan.illgen on 01.12.2015.
 */
public class SynFX {

    private WavePlayer sine;
    private Glide gainGlide;
    private Gain sineGain;

    @FXML
    private void initialize() {
        AudioContext ac = new AudioContext();

        sine = new WavePlayer(ac, 440.0f, Buffer.SINE);

        // set up the Gain and Glide objects and connect them
        gainGlide = new Glide(ac, 0.0f, 50.0f);
        sineGain = new Gain(ac, 1, gainGlide);
        sineGain.addInput(sine);
        ac.out.addInput(sineGain);

        ac.start();
    }

    @FXML
    void cPressed(MouseEvent event) {
        keyDown(50);
    }

    @FXML
    void dPressed(MouseEvent event) {
        keyDown(51);
    }

    @FXML
    void ePressed(MouseEvent event) {
        keyDown(52);
    }

    @FXML
    void fPressed(MouseEvent event) {
        keyDown(53);
    }

    @FXML
    void gPressed(MouseEvent event) {
        keyDown(54);
    }

    @FXML
    void aPressed(MouseEvent event) {
        keyDown(55);
    }

    @FXML
    void hPressed(MouseEvent event) {
        keyDown(56);
    }

    private void keyDown(int midiPitch) {
        if (sine != null && gainGlide != null) {
            sine.setFrequency(pitchToFrequency(midiPitch));
            gainGlide.setValue(0.9f);
        }
    }

    /*
     *  MIDI pitch number to frequency conversion equation from http://newt.phys.unsw.edu.au/jw/notes.html
     */
    private float pitchToFrequency(int midiPitch) {
        double exponent = (midiPitch - 69.0) / 12.0;
        return (float) (Math.pow(2, exponent) * 440.0f);
    }
}
