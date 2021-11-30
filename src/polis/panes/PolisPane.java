package polis.panes;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import polis.actors.MovingActor;
import polis.core.Polis;
import polis.actors.Actor;
import polis.tiles.Tile;
import polis.utils.XYCoordinate;

import static polis.utils.PropertyReader.*;

/**
 * Een PolisPane bevat de tekening van de stad.
 */
public class PolisPane extends Group implements InvalidationListener {

    private final Polis polis;

    public PolisPane(Polis polis) {
        this.polis = polis;
        invalidated(polis);
        polis.addListener(this);
    }

    // Herteken alle componenten van een stad
    @Override
    public void invalidated(Observable observable) {
        getChildren().clear();
        int size = CELL_SIZE*POLIS_DIMENSION;
        Polygon polygon = new Polygon(0, 0, size, 0.5*size, 0, size, -size, 0.5*size);
        polygon.setId("basis");
        getChildren().add(polygon);
        polis.getResidences().forEach(this::drawTile);
        polis.getIndustries().forEach(this::drawTile);
        polis.getCommerces().forEach(this::drawTile);
        polis.getRoadSystem().getRoads().forEach(this::drawTile);
        polis.getActors().stream().filter(actor -> actor instanceof MovingActor).forEach(this::drawActor);
    }

    // Tekenfunctie voor tegels: link de juiste afbeelding, verplaats het referentiepunt,
    // bereken de tekenvolgorde en teken op de omgezette coördinaat
    public void drawTile(Tile tile) {
        XYCoordinate newCoordinate = tile.getCoordinate().toXY();
        String imageName = "polis/tiles/" + tile.getClass().getSimpleName().toLowerCase() + '-' + tile.getLevel() + ".png";
        Image image = new Image(imageName);
        ImageView imageView = new ImageView(image);
        imageView.setTranslateX(newCoordinate.getX());
        imageView.setTranslateY(newCoordinate.getY());
        double width = image.getWidth();
        double height = image.getHeight();
        imageView.setX(-0.5 * width);
        imageView.setY(0.5 * width - height);
        imageView.setViewOrder(-tile.getCoordinate().getRow()-tile.getCoordinate().getColumn() - tile.getSize());
        getChildren().add(imageView);
    }

    // Tekenfunctie voor actoren: teken een cirkel op de omgezette coördinaat (samen met de verschuiving
    // afhankelijk van de oriëntatie), bereken de tekenvolgorde en de stijklasse en teken op de omgezette coördinaat
    public void drawActor(Actor actor) {
        XYCoordinate newCoordinate = actor.getLocation().toXY();
        XYCoordinate shift = actor.getDirection().getShift();
        Circle circle = new Circle(newCoordinate.getX() + shift.getX(), newCoordinate.getY() + shift.getY(), ACTOR_SIZE);
        circle.setViewOrder(-actor.getLocation().getRow()-actor.getLocation().getColumn()-1.5);
        circle.getStyleClass().add(actor.getClass().getSimpleName().toLowerCase());
        getChildren().add(circle);
    }
}
