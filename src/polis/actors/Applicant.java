package polis.actors;

import polis.core.Polis;
import polis.tiles.Employer;
import polis.tiles.Zone;
import polis.utils.Adres;
import polis.utils.RCCoordinate;

public class Applicant extends MovingActor {

    public Applicant(Polis polis, RCCoordinate location, Adres adres) {
        super(polis, location, adres);
    }

    // Een werkloze kan gaan werken bij een bedrijf als er nog vrije werkplaatsen zijn
    @Override
    public Zone getDestination(RCCoordinate coordinate) {
        Employer employer = polis.getEmployerAt(coordinate);
        return employer != null && employer.canApply() ? employer : null;
    }

    // Een werkloze wordt een werknemer (handelaar, arbeider, ...) en registreert zichzelf bij de werkgever
    @Override
    public Actor evolve() {
        Employer employer = polis.getEmployerAt(direction.move(location));
        employer.apply();
        // positief effect op de residentie
        adres.getHome().jobFound();
        return employer.getEmployee(polis, location, adres);
    }

    // Een werkloze die geen job vindt, wordt een slaper en keert terug naar zijn residentie, als die nog bestaat
    @Override
    public Actor die() {
        adres.getHome().noJobFound();
        return canGoHome() ? new Sleeper(polis, adres) : null;
    }
}
