package polis.core;

import polis.actors.Immigrant;
import polis.utils.RCCoordinate;
import static polis.utils.PropertyReader.readDouble;

import java.util.Random;

/**
 * Een regio brengt op veranderlijke tijdstippen een immigrant in de stad.
 * Een regio bestaat uit een tijdsaanduiding, een tempo en een stad waarin de actoren geplaatst worden.
 */
public class Region {

    private double rate;
    private final Polis polis;
    private int timestamp;

    public Region(Polis polis) {
        this.polis = polis;
        this.rate = readDouble("region.initial.rate");
        this.timestamp = new Random().nextInt((int) rate);
    }

    // Elke iteratie gaat de tijd 1 naar beneden en wordt het tempo versneld. Als de tijd op 0 komt, wordt een immigrant gegenereerd.
    public void tick() {
        timestamp--;
        if (timestamp <= 0) {
            polis.getActors().add(new Immigrant(polis, new RCCoordinate(0,15)));
            timestamp = new Random().nextInt((int) rate + 1);
        }
        rate *= readDouble("region.factor.recovery");
    }

    // Registreer dat een immigrant geen woning heeft gevonden
    public void noResidenceFound() {
        rate = Math.min(rate*readDouble("region.factor.slow.down"), readDouble("region.slowest.rate"));
    }
}
