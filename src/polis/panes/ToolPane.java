package polis.panes;

import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import polis.core.Cursor;
import polis.core.Polis;
import polis.buttons.*;

/**
 * Een ToolPane bevat de knoppen die de stad besturen
 */
public class ToolPane extends VBox {

    private final ToolButton bulldozerButton;
    // private final ToolButton upgradeButton;
    private final ToolButton selectButton;
    private final ToolButton residentialButton;
    private final ToolButton commercialButton;
    private final ToolButton industrialButton;
    private final ToolButton roadButton;

    public ToolPane(Polis polis, Cursor cursor, InfoPane infoPane){
        // upgradeButton = new UpgradeButton(polis, cursor);
        selectButton = new SelectButton(polis, cursor, infoPane);
        residentialButton = new ResidentialButton(polis, cursor);
        commercialButton = new CommercialButton(polis, cursor);
        industrialButton = new IndustrialButton(polis, cursor);
        roadButton = new RoadButton(polis, cursor);
        bulldozerButton = new BulldozerButton(polis, cursor);
        HBox row1 = new HBox(residentialButton, industrialButton, commercialButton);
        HBox row2 = new HBox(roadButton, bulldozerButton);
        // HBox row3 = new HBox(upgradeButton, selectButton);
        HBox row3 = new HBox(selectButton);
        this.getChildren().addAll(row1, row2, row3);
        ToggleGroup toggleGroup = new ToggleGroup();
        // toggleGroup.getToggles().addAll(residentialButton, industrialButton, commercialButton, roadButton, bulldozerButton, selectButton, upgradeButton);
        toggleGroup.getToggles().addAll(residentialButton, industrialButton, commercialButton, roadButton, bulldozerButton, selectButton);
        this.getChildren().forEach(ch -> ch.setPickOnBounds(false));
        this.setPickOnBounds(false);
    }

    // Selecteer de juiste knop bij het indrukken van een toets
    public void handleKey(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case B:
                bulldozerButton.fire();
                break;
            case ESCAPE:
                // upgradeButton.fire();
                selectButton.fire();
                break;
            case S:
                roadButton.fire();
                break;
            case R:
                residentialButton.fire();
                break;
            case I:
                industrialButton.fire();
                break;
            case C:
                commercialButton.fire();
                break;
        }
    }
}
