package polis.utils;

/**
 * Een model met de bijkomende eigenschap dat een InfoVisitor er statistieken van kan opvragen.
 */
public abstract class InfoVisitorAcceptor extends Model {

    public <R> R accept(InfoVisitor<R> visitor) {
        return null;
    }
}
