package polis.panes;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import polis.core.Cursor;
import polis.utils.RCCoordinate;

import static polis.utils.PropertyReader.CELL_SIZE;

/**
 * Het CursorPane bevat de tekening van de cursor en luistert naar het cursormodel.
 */
public class CursorPane extends Group implements InvalidationListener {

    private final Cursor cursor;

    public CursorPane(Cursor cursor) {
        this.cursor = cursor;
        cursor.addListener(this);
        setMouseTransparent(true);
    }

    // Herteken de cursor: teken een deelcursor op zijn positie voor elke coördinaat in de coördinatenmap en
    // link telkens de juiste stijlklasse, afhankelijk van het cursortype en het geldig zijn van de positie
    @Override
    public void invalidated(Observable observable) {
        getChildren().clear();
        for (RCCoordinate coordinate : cursor.getCoordinates().keySet()) {
            int size = cursor.getSize()*CELL_SIZE;
            Polygon poly = new Polygon(0, 0, size, 0.5*size, 0, size, -size, 0.5*size);
            poly.getStyleClass().addAll(cursor.getCoordinates().get(coordinate) ? "valid" : "invalid", cursor.getState().getClassName());
            poly.setTranslateX(coordinate.toXY().getX());
            poly.setTranslateY(coordinate.toXY().getY());
            getChildren().add(poly);
        }
    }
}
