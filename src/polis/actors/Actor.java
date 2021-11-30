package polis.actors;

import polis.core.Polis;
import polis.utils.Adres;
import polis.utils.Direction;
import polis.utils.RCCoordinate;
import static polis.utils.PropertyReader.readInt;

import java.util.Random;

/**
 * Een actor is iemand die zich in de stad beweegt. Elke actor heeft een locatie in de stad
 * en een richting waarin hij momenteel kijkt. Actoren hebben ook een leeftijd, als die 0 wordt evolueren/sterven ze.
 * Bepaalde actoren hebben een adres, bij anderen is het adres onbepaald.
 */
public abstract class Actor {

    protected RCCoordinate location;
    protected Direction direction = Direction.values()[new Random().nextInt(4)];
    protected final Polis polis;
    protected int age;
    protected Adres adres;

    public Actor(Polis polis, RCCoordinate location) {
        this.polis = polis;
        this.location = location;
        this.age = readInt(getClass().getSimpleName().toLowerCase() + ".age");
    }

    public Actor(Polis polis, RCCoordinate location, Adres adres) {
        this(polis, location);
        this.adres = adres;
    }

    public Direction getDirection() {
        return direction;
    }

    public RCCoordinate getLocation() {
        return location;
    }

    // Een actor kan pas naar huis gaan als zijn residentie en thuislocatie nog bestaan
    // en als de capaciteit van zijn residentie hem nog kan ontvangen
    public boolean canGoHome() {
        return adres != null && polis.getResidences().contains(adres.getHome()) &&
                polis.getRoadSystem().getRoadAt(adres.getHomeLocation()) != null &&
                adres.getHome().getCapacity() >= adres.getResidenceNumber();
    }

    // Evaluatiefunctie die elke periode wordt uitgevoerd
    public abstract Actor tick();
}
