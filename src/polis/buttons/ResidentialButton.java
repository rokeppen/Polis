package polis.buttons;

import polis.core.Cursor;
import polis.core.CursorState;
import polis.core.Polis;
import polis.tiles.Residence;
import polis.utils.RCCoordinate;

/**
 * De ResidentialButton voegt een residentie toe aan de stad.
 */
public class ResidentialButton extends ToolButton {

    public ResidentialButton(Polis polis, Cursor cursor) {
        super(polis);
        setOnAction(ev -> {
            polis.setTool(isSelected() ? this : null);
            cursor.setSize(2);
            cursor.setState(isSelected() ? CursorState.BUILD : CursorState.NONE);
        });
    }

    @Override
    public void handle(RCCoordinate coordinate) {
        polis.getResidences().add(new Residence(coordinate));
    }
}
