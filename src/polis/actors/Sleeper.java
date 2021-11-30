package polis.actors;

import polis.core.Polis;
import polis.utils.Adres;

public class Sleeper extends SilentActor {

    public Sleeper(Polis polis, Adres adres) {
        super(polis, adres.getHomeLocation(), adres);
    }

    // Een slaper wordt een werkzoekende, als zijn thuislocatie niet verdwenen is tijdens het slapen
    @Override
    public Actor evolve() {
        return canGoHome() ? new Applicant(polis, location, adres) : null;
    }
}
