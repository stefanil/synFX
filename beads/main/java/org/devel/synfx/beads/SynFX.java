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

    public static final float FREQ_C = 440.0f;
    private Glide gainGlide;
    private Gain sineGain;
    private AudioContext ac;

    private WavePlayer cWave;

    @FXML
    private void initialize() {
        ac = new AudioContext();

        gainGlide = new Glide(ac, 0.0f, 50.0f);
        gainGlide.setValue(0.9f);
        sineGain = new Gain(ac, 1, gainGlide);
        ac.out.addInput(sineGain);

        ac.start();
    }

    @FXML
    void cPressed(MouseEvent event) {
        cWave = new WavePlayer(ac, FREQ_C, Buffer.SINE);
        sineGain.addInput(cWave);
        cWave.start();
    }

    @FXML
    void cReleased(MouseEvent event) {
        cWave.kill();
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
        if (cWave != null && gainGlide != null) {
            cWave.setFrequency(pitchToFrequency(midiPitch));
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
