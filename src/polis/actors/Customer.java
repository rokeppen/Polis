package polis.actors;

import polis.core.Polis;
import polis.tiles.Commerce;
import polis.utils.Adres;
import polis.utils.RCCoordinate;

public class Customer extends SilentActor {

    private final Commerce commerce;

    public Customer(Polis polis, RCCoordinate location, Adres adres, Commerce commerce) {
        super(polis, location, adres);
        this.commerce = commerce;
    }

    // Een klant koopt een goed, verlaat de winkel en wordt een slaper op zijn thuislocatie, als die nog bestaat
    @Override
    public Actor evolve() {
        commerce.shop();
        commerce.leave();
        return canGoHome() ? new Sleeper(polis, adres) : null;
    }
}
