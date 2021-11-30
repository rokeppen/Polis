package polis.tiles;

import polis.utils.RCCoordinate;

public class Road extends Tile {

    public Road(RCCoordinate coordinate) {
        super(coordinate);
        this.size = 1;
    }

    public Road(RCCoordinate coordinate, boolean removable) {
        this(coordinate);
        this.removable = removable;
    }
}
