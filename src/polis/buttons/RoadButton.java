package polis.buttons;

import polis.core.Cursor;
import polis.core.CursorState;
import polis.core.Polis;
import polis.tiles.Road;
import polis.utils.RCCoordinate;

/**
 * De RoadButton voegt een straat toe aan de stad.
 */
public class RoadButton extends ToolButton {

    public RoadButton(Polis polis, Cursor cursor) {
        super(polis);
        setOnAction(ev -> {
            polis.setTool(isSelected() ? this : null);
            cursor.setSize(1);
            cursor.setState(isSelected() ? CursorState.BUILD : CursorState.NONE);
        });
    }

    @Override
    public void handle(RCCoordinate coordinate) {
        polis.getRoadSystem().addRoad(new Road(coordinate));
    }
}
