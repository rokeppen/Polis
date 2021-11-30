package polis.actors;

import polis.core.Polis;
import polis.tiles.Residence;
import polis.tiles.Zone;
import polis.utils.Adres;
import polis.utils.Direction;
import polis.utils.RCCoordinate;

public class Immigrant extends MovingActor {

    public Immigrant(Polis polis, RCCoordinate location) {
        super(polis, location);
        // start altijd westelijk in de richting van de vaste straat
        this.direction = Direction.WEST;
    }

    // Een immigrant kan in een residentie gaan wonen als er nog vrije woonplaatsen zijn
    @Override
    public Zone getDestination(RCCoordinate coordinate) {
        Residence residence = polis.getResidenceAt(coordinate);
        return residence != null && residence.isFree() ? residence : null;
    }

    // Een immigrant krijgt een adres toegewezen en wordt een slaper
    @Override
    public Actor evolve() {
        Residence residence = polis.getResidenceAt(direction.move(location));
        Adres adres = new Adres(residence, location, residence.getResidents() + 1);
        residence.register();
        return new Sleeper(polis, adres);
    }

    // Een immigrant die geen woonplaats vindt, verdwijnt
    @Override
    public Actor die() {
        // negatief effect op de regio
        polis.getRegion().noResidenceFound();
        return null;
    }
}
