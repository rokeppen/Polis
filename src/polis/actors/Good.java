package polis.actors;

import polis.core.Polis;
import polis.tiles.Commerce;
import polis.tiles.Industry;
import polis.tiles.Zone;
import polis.utils.RCCoordinate;

public class Good extends MovingActor {

    private final Industry industry;

    public Good(Polis polis, RCCoordinate location, Industry industry) {
        super(polis, location);
        this.industry = industry;
    }

    // Een winkel kan een product ontvangen als zijn goederencapaciteit dat toelaat
    @Override
    public Zone getDestination(RCCoordinate coordinate) {
        Commerce commerce = polis.getCommerceAt(coordinate);
        return commerce != null && commerce.canStore() ? commerce : null;
    }

    // Een goed dat een winkel vindt, registreert zichzelf bij de winkel en verdwijnt
    @Override
    public Actor evolve() {
        Commerce commerce = polis.getCommerceAt(direction.move(location));
        commerce.store();
        // positief effect op de industrie
        industry.shopFound();
        return null;
    }

    // Een goed dat geen winkel vindt, verdwijnt
    @Override
    public Actor die() {
        // negatief effect op de industrie
        industry.noShopFound();
        return null;
    }
}
