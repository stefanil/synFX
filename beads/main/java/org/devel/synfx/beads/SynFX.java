package org.devel.synfx.beads;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.WavePlayer;

/**
 * Created by stefan.illgen on 01.12.2015.
 */
public class SynFX {

  public static final int OCTAVE = 8;
  private AudioContext ac;

  @FXML
  private Slider pitch;

  @FXML
  private Slider gain;

  @FXML
  public RadioButton sineBuffer;

  @FXML
  public RadioButton sawBuffer;

  @FXML
  private HBox octave;

  @FXML
  private void initialize() {
    ac = new AudioContext();
    final Gain mainGain = new Gain(ac, 7, 0.0f);
    ac.out.addInput(mainGain);


    for (int i = 0; i < octave.getChildren().size(); i++) {
      final int index = i;
      final Node key = octave.getChildren().get(i);

      final Gain keyGain = new Gain(ac, 1, 0.0f);
      mainGain.addInput(keyGain);

      final Envelope pitchGain = new Envelope(ac, pitchToFrequency(OCTAVE * 8 + index));

      pitch.valueProperty().addListener((observable, oldValue, newValue) -> {
        pitchGain.setValue(newValue.floatValue() / 100.0f * pitchToFrequency(OCTAVE * 8 + index));
      });

      final WavePlayer wave = new WavePlayer(ac, pitchGain, Buffer.SINE);
      key.setOnMousePressed(event -> {
        keyGain.setGain(1.0f);
      });
      key.setOnMouseReleased(event -> {
        keyGain.setGain(0.0f);
      });
      keyGain.addInput(wave);

      sineBuffer.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if(newValue) {
          wave.setBuffer(Buffer.SINE);
        }
      });
      sawBuffer.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if(newValue) {
          wave.setBuffer(Buffer.SAW);
        }
      });
      sineBuffer.setSelected(true);
    }

    gain.valueProperty().addListener((observable, oldGain, newGain) -> {
      final float converted = newGain.floatValue() / 1000.0f;
      mainGain.setGain(converted);
      System.out.println("Gain: " + mainGain.getGain());
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
