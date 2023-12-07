package ber.soundboard.berssoundboard;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class Mixer {
    public BorderPane playBtn, predeterTransitionBtn, transitionerBtn, freeBtn;
    public Slider volumeSlider;
    public Label soundName, soundCue, predeterTransitionVolume, predeterTransitionTime;
    public TextField transitionerVolume, transitionerTime;
    public Mixer(BorderPane playBtn, BorderPane predeterTransitionBtn, BorderPane transitionerBtn, BorderPane freeBtn,
                 Slider volumeSlider, Label soundName, Label soundCue, Label predeterTransitionVolume, Label predeterTransitionTime,
                 TextField transitionerVolume, TextField transitionerTime) {
        this.playBtn = playBtn;
        this.predeterTransitionBtn = predeterTransitionBtn;
        this.transitionerBtn = transitionerBtn;
        this.freeBtn = freeBtn;
        this.volumeSlider = volumeSlider;
        this.soundName = soundName;
        this.soundCue = soundCue;
        this.predeterTransitionVolume = predeterTransitionVolume;
        this.predeterTransitionTime = predeterTransitionTime;
        this.transitionerVolume = transitionerVolume;
        this.transitionerTime = transitionerTime;
    }
}
