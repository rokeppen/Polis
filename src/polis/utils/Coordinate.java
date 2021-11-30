package polis.utils;

/**
 * Een coördinaat is een koppel, bestaande uit een x- en y-waarde, dat de locatie in een rooster voorstelt.
 */
public abstract class Coordinate {

    protected final double x;
    protected final double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Twee coördinaten zijn gelijk als hun x- en y-waarden gelijk zijn.
    @Override
    public boolean equals(Object o) {
        return o instanceof Coordinate && x == ((Coordinate) o).getX() && y == ((Coordinate) o).getY();
    }

    @Override
    public int hashCode() {
        return (int) (Math.pow(2,x)*Math.pow(3,y));
    }

    @Override
    public String toString() {
        return "(" + (int) x + "," + (int) y + ")";
    }
}
