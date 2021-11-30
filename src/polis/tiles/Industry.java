package polis.tiles;

import polis.core.Polis;
import polis.actors.Actor;
import polis.actors.Laborer;
import polis.utils.Adres;
import polis.utils.InfoVisitor;
import polis.utils.RCCoordinate;
import static polis.utils.PropertyReader.readDouble;

public class Industry extends Employer {

    public Industry(RCCoordinate coordinate) {
        super(coordinate);
    }

    // Update de werkcapaciteit nadat een geproduceerd goed een winkel vond
    public void shopFound() {
        capacity.setValue(capacity.getValue()* readDouble("factor.goods.delivered"));
    }

    // Update de werkcapaciteit nadat een geproduceerd goed geen winkel vond
    public void noShopFound() {
        capacity.setValue(Math.max(capacity.getValue()* readDouble("factor.goods.not.delivered"), minimalCapacity));
    }

    @Override
    public Actor getEmployee(Polis polis, RCCoordinate coordinate, Adres adres) {
        return new Laborer(polis, adres.getHomeLocation(), adres, this);
    }

    @Override
    public <R> R accept(InfoVisitor<R> visitor) {
        return visitor.visitIndustry(this);
    }
}
