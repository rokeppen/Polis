package polis.buttons;

import polis.core.Cursor;
import polis.core.CursorState;
import polis.core.Polis;
import polis.tiles.Zone;
import polis.utils.RCCoordinate;

public class UpgradeButton extends ToolButton {

    public UpgradeButton(Polis polis, Cursor cursor) {
        super(polis);
        setOnAction(ev -> {
            polis.setTool(isSelected() ? this : null);
            cursor.setSize(1);
            cursor.setState(isSelected() ? CursorState.SELECT : CursorState.NONE);
        });
    }

    @Override
    public void handle(RCCoordinate coordinate) {
        Zone zone = polis.getZoneAt(coordinate);
        if (zone != null) {
            zone.setLevel((zone.getLevel() + 1) % 4);
        }
    }
}
