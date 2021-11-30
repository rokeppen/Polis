package polis.tiles;

import polis.utils.InfoVisitorAcceptor;
import polis.utils.RCCoordinate;

/**
 * Een tegel is een element van de stad.
 * Een tegel heeft een grootte, een level (startend op 0) en een coördinaat.
 */
public abstract class Tile extends InfoVisitorAcceptor {

    protected int level = 0;
    protected int size;
    protected final RCCoordinate coordinate;
    protected boolean removable = true;

    public Tile(RCCoordinate coordinate) {
        this.coordinate = coordinate;
    }

    // Is de tegel verwijderbaar?
    public boolean isRemovable() {
        return removable;
    }

    // De coördinaat van de tegel in rij-kolomvorm
    public RCCoordinate getCoordinate() {
        return coordinate;
    }

    // Het level van de tegel
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // De grootte van de tegel in aantal vakjes
    public int getSize() {
        return size;
    }

    // Kijk of een coördinaat overlapt met een tegel
    public boolean isOn(RCCoordinate coordinate) {
        return coordinate.getRow() - this.coordinate.getRow() < size && coordinate.getRow() - this.coordinate.getRow() >= 0
                && coordinate.getColumn() - this.coordinate.getColumn() < size && coordinate.getColumn() - this.coordinate.getColumn() >= 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " @" + coordinate.toString();
    }
}
