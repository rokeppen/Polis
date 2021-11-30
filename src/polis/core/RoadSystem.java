package polis.core;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import polis.tiles.Road;
import polis.utils.RCCoordinate;

import java.util.ArrayList;

/**
 * Een stratensysteem is een verzameling straten.
 * Een stratensysteem luistert naar een stad, om bij veranderingen van straten de levels van de straten aan te passen.
 */
public class RoadSystem implements InvalidationListener {

    private final ArrayList<Road> roads = new ArrayList<>();

    public RoadSystem(Polis polis) {
        polis.addListener(this);
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    // Geef de straat op de gegeven coördinaat, als die bestaat
    public Road getRoadAt(RCCoordinate coordinate) {
        return roads.stream().filter(t -> t.isOn(coordinate)).findFirst().orElse(null);
    }

    public void addRoad(Road road) {
        roads.add(road);
    }

    // Herbereken het level van de straat om de correcte afbeelding weer te geven
    @Override
    public void invalidated(Observable observable) {
        for (Road road : roads) {
            int ne = getRoadAt(road.getCoordinate().east()) == null ? 0 : 1;
            int se = getRoadAt(road.getCoordinate().south()) == null ? 0 : 2;
            int sw = getRoadAt(road.getCoordinate().west()) == null ? 0 : 4;
            int nw = getRoadAt(road.getCoordinate().north()) == null ? 0 : 8;
            road.setLevel(ne+se+sw+nw);
        }
    }
}
