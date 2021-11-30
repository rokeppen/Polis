package polis.tiles;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import polis.utils.RCCoordinate;
import static polis.utils.PropertyReader.readDouble;

/**
  * Een zone is een tegel van grootte 2.
  * Elke zone heeft een capaciteit en een minimale capaciteit.
  * De capaciteit is een Property waar het level van de zone afhankelijk van is.
  **/
public abstract class Zone extends Tile implements InvalidationListener {

    protected final DoubleProperty capacity = new SimpleDoubleProperty();
    protected final double minimalCapacity;

    public Zone(RCCoordinate coordinate) {
        super(coordinate);
        this.size = 2;
        this.minimalCapacity = readDouble(getClass().getSimpleName().toLowerCase() + ".capacity.minimal");
        capacity.setValue(readDouble(getClass().getSimpleName().toLowerCase() + ".capacity.initial"));
        capacity.addListener(this);
    }

    // De capaciteit van een zone
    public double getCapacity() {
        return capacity.getValue();
    }

    // Verwerk de impact van de nieuwe capaciteit op het level van de zone
    @Override
    public void invalidated(Observable observable) {
        if (level > 1) {
            double lowerBound = readDouble(getClass().getSimpleName().toLowerCase() + ".level" + level + "to" + (level - 1));
            level -= capacity.getValue() <= lowerBound ? 1 : 0;
        }
        if (level > 0 && level < 3) {
            double upperBound = readDouble(getClass().getSimpleName().toLowerCase() + ".level" + level + "to" + (level + 1));
            level += capacity.getValue() > upperBound ? 1 : 0;
        }
    }
}
