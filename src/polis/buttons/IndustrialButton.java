package polis.buttons;

import polis.core.Cursor;
import polis.core.CursorState;
import polis.core.Polis;
import polis.tiles.Industry;
import polis.utils.RCCoordinate;

/**
 * De IndustrialButton voegt een industrie toe aan de stad.
 */
public class IndustrialButton extends ToolButton {

    public IndustrialButton(Polis polis, Cursor cursor) {
        super(polis);
        setOnAction(ev -> {
            polis.setTool(isSelected() ? this : null);
            cursor.setSize(2);
            cursor.setState(isSelected() ? CursorState.BUILD : CursorState.NONE);
        });
    }

    @Override
    public void handle(RCCoordinate coordinate) {
        polis.getIndustries().add(new Industry(coordinate));
    }
}