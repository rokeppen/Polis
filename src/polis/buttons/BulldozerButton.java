package polis.buttons;

import polis.core.Cursor;
import polis.core.CursorState;
import polis.core.Polis;
import polis.tiles.Road;
import polis.tiles.Zone;
import polis.utils.RCCoordinate;

/**
 * De BulldozerButton verwijdert een tegel uit de stad.
 */
public class BulldozerButton extends ToolButton {

    public BulldozerButton(Polis polis, Cursor cursor) {
        super(polis);
        setOnAction(ev -> {
            polis.setTool(isSelected() ? this : null);
            cursor.setSize(1);
            cursor.setState(isSelected() ? CursorState.DELETE : CursorState.NONE);
        });
    }

    @Override
    public void handle(RCCoordinate coordinate) {
        Zone zone = polis.getZoneAt(coordinate);
        Road road = polis.getRoadSystem().getRoadAt(coordinate);
        if (zone != null) {
            polis.getCommerces().removeIf(c -> c.isOn(coordinate));
            polis.getIndustries().removeIf(i -> i.isOn(coordinate));
            polis.getResidences().removeIf(r -> r.isOn(coordinate));
        } else if (road != null && road.isRemovable()) {
            // verwijder bij de straat ook de actoren die zich erop bevinden
            polis.getActors().removeIf(a -> a.getLocation().equals(coordinate));
            polis.getRoadSystem().getRoads().removeIf(r -> r.isOn(coordinate));
        }
    }
}
