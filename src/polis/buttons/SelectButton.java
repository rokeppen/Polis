package polis.buttons;

import polis.core.Cursor;
import polis.core.CursorState;
import polis.core.Polis;
import polis.panes.InfoPane;
import polis.tiles.Zone;
import polis.utils.RCCoordinate;

/**
 * De SelectButton zorgt ervoor dat het juiste model aan het informatiepaneel doorgegeven wordt.
 * De verandering van de cursorgrootte en het cursortype gebeurt in elk subtype.
 */
public class SelectButton extends ToolButton {

    private final InfoPane infoPane;

    public SelectButton(Polis polis, Cursor cursor, InfoPane infoPane) {
        super(polis);
        this.infoPane = infoPane;
        setOnAction(ev -> {
            polis.setTool(isSelected() ? this : null);
            cursor.setSize(1);
            cursor.setState(isSelected() ? CursorState.SELECT : CursorState.NONE);
        });
    }

    @Override
    public void handle(RCCoordinate coordinate) {
        Zone zone = polis.getZoneAt(coordinate);
        infoPane.setModel(zone == null ? polis : zone);
    }
}
