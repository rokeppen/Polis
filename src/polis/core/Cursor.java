package polis.core;

import polis.utils.Model;
import polis.utils.RCCoordinate;

import java.util.HashMap;
import java.util.Map;

/**
 * Een cursor bestaat uit een grootte, een verzameling coördinaten die telkens al dan niet geldig zijn, en een staat.
 * Een cursor is een model waarnaar de afbeelding van de cursor kan luisteren.
 */
public class Cursor extends Model {

    private int size = 1;
    private Map<RCCoordinate, Boolean> coordinates = new HashMap<>();
    private CursorState state = CursorState.NONE;

    public CursorState getState() {
        return state;
    }

    public void setState(CursorState state) {
        this.state = state;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        fireInvalidationEvent();
    }

    public Map<RCCoordinate, Boolean> getCoordinates() {
        return coordinates;
    }

    // Bepaal, voor een gegeven begin en eindpositie, de coördinaten van de cursor en leg er telkens bij vast of ze geldig zijn
    public void recalculate(RCCoordinate origin, RCCoordinate destination, Polis polis) {
        clearCoordinates();
        for (RCCoordinate coordinate : origin.pathTo(destination, size)) {
            boolean valid = coordinate.zoneSet(size).stream().allMatch(c -> state.isValid(c, polis));
            coordinates.put(coordinate, valid);
        }
        fireInvalidationEvent();
    }

    // Verwijder de huidige coördinaten
    public void clearCoordinates() {
        coordinates = new HashMap<>();
        fireInvalidationEvent();
    }
}
