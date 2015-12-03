package org.devel.synfx.beads;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.WavePlayer;

/**
 * Created by stefan.illgen on 01.12.2015.
 */
public class SynFX {

  public static final int OCTAVE = 8;
  
  private AudioContext ac;

  @FXML
  private HBox octave;

  @FXML
  private void initialize() {
    ac = new AudioContext();

    for (int i = 0; i < octave.getChildren().size(); i++) {
      final Node key = octave.getChildren().get(i);

      final Glide gainGlide = new Glide(ac, 0.0f, 50.0f);
      final Gain sineGain = new Gain(ac, 1, gainGlide);
      ac.out.addInput(sineGain);

      final WavePlayer wave = new WavePlayer(ac, pitchToFrequency(OCTAVE * 8 + i), Buffer.SINE);
      key.setOnMousePressed(event -> {
        gainGlide.setValue(0.9f);
      });
      key.setOnMouseReleased(event -> {
        gainGlide.setValue(0.0f);
      });
      sineGain.addInput(wave);
    }

    ac.start();
  }

  /*
   *  MIDI pitch number to frequency conversion equation from http://newt.phys.unsw.edu.au/jw/notes.html
   */
  private float pitchToFrequency(int midiPitch) {
    double exponent = (midiPitch - 69.0) / 12.0;
    return (float)(Math.pow(2, exponent) * 440.0f);
  }
}
