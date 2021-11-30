package polis.actors;

import polis.core.Polis;
import polis.tiles.Commerce;
import polis.utils.Adres;
import polis.utils.RCCoordinate;

public class Merchant extends SilentActor {

    private final Commerce commerce;

    public Merchant(Polis polis, RCCoordinate location, Adres adres, Commerce commerce) {
        super(polis, location, adres);
        this.commerce = commerce;
    }

    // Een handelaar neemt ontslag en wordt een shopper, vertrekkende van zijn thuislocatie, als die nog bestaat
    @Override
    public Actor evolve() {
        commerce.resign();
        return canGoHome() ? new Shopper(polis, adres) : null;
    }
}
