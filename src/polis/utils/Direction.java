package polis.utils;

import static polis.utils.PropertyReader.CELL_SIZE;
import static polis.utils.PropertyReader.ACTOR_SIZE;

/**
 * Een oriëntatie op de kaart.
 */
public enum Direction {
    NORTH (new XYCoordinate(0, 1.5*ACTOR_SIZE)) {
        @Override
        public Direction left() {
            return EAST;
        }

        @Override
        public Direction right() {
            return WEST;
        }

        @Override
        public Direction back() {
            return SOUTH;
        }

        @Override
        public RCCoordinate move(RCCoordinate coordinate) {
            return coordinate.north();
        }
    },

    EAST (new XYCoordinate(CELL_SIZE-2*ACTOR_SIZE, 0.5*CELL_SIZE)){
        @Override
        public Direction left() {
            return NORTH;
        }

        @Override
        public Direction right() {
            return SOUTH;
        }

        @Override
        public Direction back() {
            return WEST;
        }

        @Override
        public RCCoordinate move(RCCoordinate coordinate) {
            return coordinate.east();
        }
    },

    SOUTH (new XYCoordinate(0,CELL_SIZE-1.5*ACTOR_SIZE)){
        @Override
        public Direction left() {
            return WEST;
        }

        @Override
        public Direction right() {
            return EAST;
        }

        @Override
        public Direction back() {
            return NORTH;
        }

        @Override
        public RCCoordinate move(RCCoordinate coordinate) {
            return coordinate.south();
        }
    },

    WEST (new XYCoordinate(-CELL_SIZE+2*ACTOR_SIZE,0.5*CELL_SIZE)) {
        @Override
        public Direction left() {
            return SOUTH;
        }

        @Override
        public Direction right() {
            return NORTH;
        }

        @Override
        public Direction back() {
            return EAST;
        }

        @Override
        public RCCoordinate move(RCCoordinate coordinate) {
            return coordinate.west();
        }
    };

    private final XYCoordinate shift;

    Direction(XYCoordinate shift) {
        this.shift = shift;
    }

    // Extra om de locatie op een tegel verschillend te maken naargelang de oriëntatie (zorgt voor minder overlap)
    public XYCoordinate getShift() {
        return shift;
    }

    // Geef de richting terug als we vanuit deze richting naar links draaien
    public abstract Direction left();

    // Geef de richting terug als we vanuit deze richting naar rechts draaien
    public abstract Direction right();

    // Geef de richting terug als we vanuit deze richting omkeren
    public abstract Direction back();

    // Geef de locatie terug waar we terechtkomen als in deze richting verplaatst wordt vanuit een gegeven locatie
    public abstract RCCoordinate move(RCCoordinate coordinate);
}
