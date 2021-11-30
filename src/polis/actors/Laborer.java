package polis.actors;

import polis.core.Polis;
import polis.tiles.Industry;
import polis.utils.Adres;
import polis.utils.RCCoordinate;
import static polis.utils.PropertyReader.readInt;

public class Laborer extends SilentActor {

    private final Industry industry;

    public Laborer(Polis polis, RCCoordinate location, Adres adres, Industry industry) {
        super(polis, location, adres);
        this.industry = industry;
    }

    // Een arbeider neemt ontslag en wordt een shopper, vertrekkende van zijn thuislocatie, als die nog bestaat
    @Override
    public Actor evolve() {
        industry.resign();
        return canGoHome() ? new Shopper(polis, adres) : null;
    }

    // Een arbeider produceert bijkomstig om de x aantal stappen een goed
    @Override
    public Actor tick() {
        Actor actor = super.tick();
        if (polis.getIndustries().contains(industry) && age % readInt("steps.per.goods") == 0) {
            polis.getActors().add(new Good(polis, location, industry));
        }
        return actor;
    }
}
