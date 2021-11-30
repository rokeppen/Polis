package polis.core;

import polis.utils.RCCoordinate;

/**
 * De CursorState linkt het geldig zijn van een bepaalde positie met de geselecteerde tool.
 * Onrechtstreeks wordt hiermee ook het uitzicht van de cursor bepaald: een className wordt vertaald naar een css-klasse.
 */
public enum CursorState {
    BUILD("build") {
        @Override
        public boolean isValid(RCCoordinate coordinate, Polis polis) {
            return polis.emptyOnMapPosition(coordinate);
        }
    },

    DELETE("delete") {
        @Override
        public boolean isValid(RCCoordinate coordinate, Polis polis) {
            return !polis.emptyOnMapPosition(coordinate) && polis.getTileAt(coordinate).isRemovable();
        }
    },

    SELECT("select"), NONE("none");

    CursorState(String className) {
        this.className = className;
    }

    private final String className;

    public String getClassName() {
        return this.className;
    }

    public boolean isValid(RCCoordinate coordinate, Polis polis) {
        return true;
    }
}
