package polis.buttons;

import polis.core.Cursor;
import polis.core.CursorState;
import polis.core.Polis;
import polis.tiles.Commerce;
import polis.utils.RCCoordinate;

/**
 * De CommercialButton voegt een winkel toe aan de stad.
 */
public class CommercialButton extends ToolButton {

    public CommercialButton(Polis polis, Cursor cursor) {
        super(polis);
        setOnAction(ev -> {
            polis.setTool(isSelected() ? this : null);
            cursor.setSize(2);
            cursor.setState(isSelected() ? CursorState.BUILD : CursorState.NONE);
        });
    }

    @Override
    public void handle(RCCoordinate coordinate) {
        polis.getCommerces().add(new Commerce(coordinate));
    }
}
