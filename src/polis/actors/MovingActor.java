package polis.actors;

import polis.core.Polis;
import polis.tiles.Zone;
import polis.utils.Adres;
import polis.utils.Direction;
import polis.utils.RCCoordinate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Een bewegende actor verplaatst zich over de straten van zijn stad.
 */
public abstract class MovingActor extends Actor {

    public MovingActor(Polis polis, RCCoordinate location) {
        super(polis, location);
    }

    public MovingActor(Polis polis, RCCoordinate location, Adres adres) {
        super(polis, location, adres);
    }

    // Kijk of een bestemming op de gegeven coördinaat ligt en verwerk de eventuele gevolgen van een onsuccesvol bezoek
    public abstract Zone getDestination(RCCoordinate coordinate);

    // Evolueer de actor in zijn volgende vorm en verwerk de eventuele gevolgen daarvan
    public abstract Actor evolve();

    // Verwerk het bereiken van de uiterste leeftijd
    public abstract Actor die();

    @Override
    public Actor tick() {
        // Maak de actor een jaar ouder en verwerk het eventuele bereiken van zijn uiterste leeftijd
        age--;
        if (age == 0) {
            return die();
        }
        // Kijk random links en rechts van de actor of de bestemming bereikt is en verwerk telkens het al dan niet geldig zijn
        ArrayList<Direction> toLook = new ArrayList<>();
        toLook.add(direction.left());
        toLook.add(direction.right());
        Collections.shuffle(toLook);
        for (Direction dir : toLook) {
            if (getDestination(dir.move(location)) != null) {
                direction = dir;
                // Een geldige bestemming is bereikt, zet de actor om in zijn volgende rol
                return evolve();
            }
        }
        // Verplaats de actor random vooruit, links of rechts, en anders achteruit
        ArrayList<Direction> toGo = new ArrayList<>(toLook);
        toGo.add(direction);
        Collections.shuffle(toGo);
        toGo.add(direction.back());
        for (Direction dir : toGo) {
            if (polis.getRoadSystem().getRoadAt(dir.move(location)) != null) {
                direction = dir;
                location = dir.move(location);
                return this;
            }
        }
        // Alle straten rond de actor zijn verdwenen, hij blijft staan
        return this;
    }
}
