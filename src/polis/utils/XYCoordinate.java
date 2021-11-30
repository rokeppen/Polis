package polis.utils;

import static polis.utils.PropertyReader.CELL_SIZE;

/**
 * Een coördinaat in x-yvorm
 */
public class XYCoordinate extends Coordinate {

    public XYCoordinate(double x, double y) {
        super(x, y);
    }

    // Zet deze coördinaat om naar een coördinaat in rij-kolomvorm
    public RCCoordinate toRowCol() {
        return new RCCoordinate(Math.floor((2*y-x)/(2*CELL_SIZE)), Math.floor((x+2*y)/(2*CELL_SIZE)));
    }
}
