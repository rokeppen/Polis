package polis.panes;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import polis.core.Cursor;
import polis.core.Polis;
import polis.utils.RCCoordinate;
import polis.utils.XYCoordinate;

import static polis.utils.PropertyReader.CELL_SIZE;
import static polis.utils.PropertyReader.POLIS_DIMENSION;

/**
 * Het ClickPane is een onzichtbare laag bovenop de stad die de muisacties opvangt en verwerkt.
 */
public class ClickPane extends Group {

    private final Polis polis;
    private final Cursor cursor;
    private RCCoordinate origin;
    private RCCoordinate destination;

    public ClickPane(Polis polis, Cursor cursor) {
        this.polis = polis;
        this.cursor = cursor;
        // het ClickPane is groter dan het PolisPane omdat de cursor deels buiten het PolisPane kan komen
        int size = CELL_SIZE*POLIS_DIMENSION+2*CELL_SIZE;
        Polygon polygon = new Polygon(0, 0, size, 0.5*size, 0, size, -size, 0.5*size);
        polygon.setTranslateY(-CELL_SIZE);
        polygon.setId("clickBasis");
        getChildren().addAll(polygon, new CursorPane(cursor));
        setOnMousePressed(this::handlePress);
        setOnMouseReleased(this::handleRelease);
        setOnMouseMoved(this::handleMove);
        setOnMouseDragged(this::handleDrag);
        setOnMouseExited(this::handleExit);
    }

    // Wijs de begincoördinaat toe bij het indrukken van de muis
    public void handlePress(MouseEvent mouseEvent) {
        origin = new XYCoordinate(mouseEvent.getX(), mouseEvent.getY()).toRowCol();
        destination = origin;
    }

    // Handel het loslaten van de muis af door de polis de locaties van de cursor te geven en de locaties leeg te maken
    public void handleRelease(MouseEvent mouseEvent) {
        polis.clickedAt(cursor.getCoordinates());
        cursor.clearCoordinates();
        // om de cursor direct terug te hertekenen, anders is er eerst een beweging nodig
        handleMove(mouseEvent);
    }

    // Herteken de cursor als de positie buiten de huidige tegel gaat
    public void handleMove(MouseEvent mouseEvent) {
        RCCoordinate coordinate = new XYCoordinate(mouseEvent.getX(), mouseEvent.getY()).toRowCol();
        if (polis.positionOnMap(coordinate) && !cursor.getCoordinates().containsKey(coordinate)) {
            cursor.clearCoordinates();
            cursor.recalculate(coordinate, coordinate, polis);
        }
    }

    // Breid de cursor uit bij een nieuw toegevoegde positie
    public void handleDrag(MouseEvent mouseEvent) {
        RCCoordinate coordinate = new XYCoordinate(mouseEvent.getX(), mouseEvent.getY()).toRowCol();
        if (polis.positionOnMap(coordinate) && !destination.equals(coordinate)) {
            cursor.recalculate(origin, coordinate, polis);
            destination = coordinate;
        }
    }

    // Verwijder de cursor als er buiten de kaart wordt bewogen, tenzij er een drag aan de gang is
    public void handleExit(MouseEvent mouseEvent) {
        // om de selectie te behouden als per ongeluk buiten de kaart wordt gegaan, handleDragExit werkt hiervoor niet
        if (mouseEvent.getButton() == MouseButton.NONE) {
            cursor.clearCoordinates();
        }
    }
}
