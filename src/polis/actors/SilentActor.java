package polis.actors;

import polis.core.Polis;
import polis.utils.Adres;
import polis.utils.RCCoordinate;

/**
 * Een stille actor wacht een tijdje uit het zicht af voor het evolueert
 */
public abstract class SilentActor extends Actor {

    public SilentActor(Polis polis, RCCoordinate location, Adres adres) {
        super(polis, location, adres);
    }

    public abstract Actor evolve();

    // Pas als zijn leeftijd bereikt is, evolueert een stille actor
    @Override
    public Actor tick() {
        age--;
        return age == 0 ? evolve() : this;
    }
}
