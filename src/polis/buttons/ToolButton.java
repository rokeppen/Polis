package polis.buttons;

import javafx.scene.control.ToggleButton;
import polis.core.Polis;
import polis.utils.RCCoordinate;

/**
 * Een standaardknop van het ToolPane, die de stad toewijst en de focus afneemt.
 */
public abstract class ToolButton extends ToggleButton {

    protected final Polis polis;

    public ToolButton(Polis polis) {
        this.polis = polis;
        setFocusTraversable(false);
    }

    public abstract void handle(RCCoordinate coordinate);
}
