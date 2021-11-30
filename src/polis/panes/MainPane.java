package polis.panes;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import polis.core.Cursor;
import polis.core.Engine;
import polis.core.Polis;
import polis.buttons.PlayButton;
import prog2.util.Viewport;

/**
 * Het MainPane is het basispaneel waarop alle andere componenten gehecht worden.
 * Het initialiseert ook alle modellen.
 */
public class MainPane extends StackPane {

    public MainPane() {
        Polis polis = new Polis();
        Cursor cursor = new Cursor();
        Engine engine = new Engine(polis);
        PolisPane polisPane = new PolisPane(polis);
        ClickPane clickPane = new ClickPane(polis, cursor);
        InfoPane infoPane = new InfoPane(polis);
        Viewport viewport = new Viewport(new StackPane(polisPane, clickPane),0.5);
        viewport.setFocusTraversable(true);
        ToolPane toolPane = new ToolPane(polis, cursor, infoPane);
        PlayButton playButton = new PlayButton(engine);
        StackPane wrapper1 = new StackPane(playButton);
        wrapper1.setId("playButtonWrapper");
        wrapper1.setPickOnBounds(false);
        StackPane wrapper2 = new StackPane(infoPane);
        wrapper2.setId("infoPaneWrapper");
        wrapper2.setPickOnBounds(false);
        getChildren().addAll(viewport, toolPane, wrapper1, wrapper2);
        setOnKeyPressed(ev -> {
            if (ev.getCode() == KeyCode.SPACE) {
                playButton.fire();
            } else {
                toolPane.handleKey(ev);
            }
        });
    }
}
