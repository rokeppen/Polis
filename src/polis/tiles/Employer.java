package polis.tiles;

import polis.core.Polis;
import polis.actors.Actor;
import polis.utils.Adres;
import polis.utils.RCCoordinate;

public abstract class Employer extends Zone {

    protected int workers = 0;

    public Employer(RCCoordinate coordinate) {
        super(coordinate);
    }

    // Kijk of er nog een werknemer kan worden aangenomen
    public boolean canApply() {
        return workers + 1 <= capacity.getValue();
    }

    // Registreer een nieuwe werknemer
    public void apply() {
        level += level == 0 ? 1 : 0;
        if (canApply()) {
            workers++;
        }
    }

    // Registreer het ontslag van een werknemer
    public void resign() {
        workers--;
    }

    // Het aantal werknemers in een werkplaats
    public int getWorkers() {
        return workers;
    }

    // Genereer de passende actor bij de huidige werkgever
    public abstract Actor getEmployee(Polis polis, RCCoordinate coordinate, Adres adres);
}
