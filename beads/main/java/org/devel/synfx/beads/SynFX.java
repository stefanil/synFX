package org.devel.synfx.beads;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.WavePlayer;

/**
 * Created by stefan.illgen on 01.12.2015.
 */
public class SynFX {

  public static final int OCTAVE = 8;

  private AudioContext ac;

  @FXML
  private Slider bitchBend;

  @FXML
  private Slider gain;

  @FXML
  private HBox octave;

  @FXML
  private void initialize() {
    ac = new AudioContext();
    final Gain mainGain = new Gain(ac, 7, 0.0f);
    ac.out.addInput(mainGain);


    for (int i = 0; i < octave.getChildren().size(); i++) {
      final Node key = octave.getChildren().get(i);

      final Gain keyGain = new Gain(ac, 1, 0.0f);
      mainGain.addInput(keyGain);

      final WavePlayer wave = new WavePlayer(ac, pitchToFrequency(OCTAVE * 8 + i), Buffer.SINE);
      key.setOnMousePressed(event -> {
        keyGain.setGain(0.9f);
      });
      key.setOnMouseReleased(event -> {
        keyGain.setGain(0.0f);
      });
      keyGain.addInput(wave);
    }

    bitchBend.valueProperty().addListener((observable, oldValue, newValue) -> {

    });
    gain.valueProperty().addListener((observable, oldGain, newGain) -> {
      final float converted = newGain.floatValue() / 1000.0f;
      mainGain.setGain(converted);
      System.out.println(mainGain.getGain());
    });

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
