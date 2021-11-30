package polis.utils;

import java.util.HashSet;
import static polis.utils.PropertyReader.CELL_SIZE;

/**
 * Een coördinaat in rij-kolomvorm.
 */
public class RCCoordinate extends Coordinate {

    public RCCoordinate(double row, double column) {
        super(row, column);
    }

    // Doet hetzelfde als getX, maar ziet er logischer uit
    public double getRow() {
        return x;
    }

    // Doet hetzelfde als getY, maar ziet er logischer uit
    public double getColumn() {
        return y;
    }

    // Zet deze coördinaat om naar een coördinaat in x-yvorm
    public XYCoordinate toXY() {
        return new XYCoordinate(CELL_SIZE*(y-x), CELL_SIZE*(x+y)/2);
    }

    // Koppel de oriëntatie van de kaart aan de verandering van rijen/kolommen
    public RCCoordinate east() {
        return new RCCoordinate(x-1, y);
    }

    public RCCoordinate south() {
        return new RCCoordinate(x, y+1);
    }

    public RCCoordinate west() {
        return new RCCoordinate(x+1, y);
    }

    public RCCoordinate north() {
        return new RCCoordinate(x, y-1);
    }

    // Bepaal alle coördinaten die op een pad liggen van het huidige punt naar het gegeven punt, met sprongen van de gegeven grootte
    public HashSet<RCCoordinate> pathTo(RCCoordinate destination, int size) {
        HashSet<RCCoordinate> result = new HashSet<>();
        double xCorrection = Math.abs(destination.getRow()-x) % size;
        double yCorrection = Math.abs(destination.getColumn()-y) % size;
        if (xCorrection != 0) {
            return this.pathTo(new RCCoordinate(destination.getRow()-xCorrection, destination.getColumn()), size);
        } else if (yCorrection != 0) {
            return this.pathTo(new RCCoordinate(destination.getRow(), destination.getColumn()-yCorrection), size);
        }
        for (double i = Math.min(x, destination.getRow()); i <= Math.max(x, destination.getRow()); i += size) {
            result.add(new RCCoordinate(i, y));
        }
        for (double i = Math.min(y, destination.getColumn()); i <= Math.max(y, destination.getColumn()); i += size) {
            result.add(new RCCoordinate(destination.getRow(), i));
        }
        return result;
    }

    // Bepaal alle coördinaten die overlappen met een tegel van de gegeven grootte
    public HashSet<RCCoordinate> zoneSet(int size) {
        HashSet<RCCoordinate> result = new HashSet<>();
        for (double i = x; i < x + size; i++) {
            for (double j = y; j < y + size; j++) {
                result.add(new RCCoordinate(i, j));
            }
        }
        return result;
    }
}
