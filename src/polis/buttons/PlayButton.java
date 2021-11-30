package polis.buttons;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import polis.core.Engine;

/**
 * De PlayButton start/stopt de simulatie.
 */
public class PlayButton extends Button {

    private boolean playing = false;
    private final Engine engine;

    public PlayButton(Engine engine) {
        this.engine = engine;
        setFocusTraversable(false);
        setOnAction(this::handle);
    }

    public void handle(ActionEvent event) {
        if (playing) {
            engine.getTimeline().stop();
        } else {
            engine.getTimeline().play();
        }
        playing = !playing;
        // verander het uitzicht van de knop
        getStyleClass().remove(playing ? "pause" : "playing");
        getStyleClass().add(playing ? "playing" : "pause");
    }
}
