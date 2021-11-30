package polis.tiles;

import javafx.beans.Observable;
import polis.utils.InfoVisitor;
import polis.utils.RCCoordinate;
import static polis.utils.PropertyReader.readDouble;

public class Residence extends Zone {

    private int residents = 0;

    public Residence(RCCoordinate coordinate) {
        super(coordinate);
    }

    // Registreer een nieuwe bewoner
    public void register() {
        level += level == 0 ? 1 : 0;
        residents += isFree() ? 1 : 0;
    }

    // Kijk of er nog een bewoner kan bijkomen
    public boolean isFree() {
        return residents + 1 <= capacity.getValue();
    }

    // Update de wooncapaciteit nadat een bewoner een job heeft gevonden
    public void jobFound() {
        capacity.setValue(capacity.getValue()* readDouble("factor.job.found"));
    }

    // Update de wooncapaciteit nadat een bewoner geen job vond
    public void noJobFound() {
        capacity.setValue(Math.max(capacity.getValue()* readDouble("factor.job.not.found"), minimalCapacity));
    }

    // Update de wooncapaciteit nadat een bewoner een winkel heeft gevonden
    public void shopFound() {
        capacity.setValue(capacity.getValue()* readDouble("factor.shop.found"));
    }

    // Update de wooncapaciteit nadat een bewoner geen winkel vond
    public void noShopFound() {
        capacity.setValue(Math.max(capacity.getValue()* readDouble("factor.shop.not.found"), minimalCapacity));
    }

    // Het aantal bewoners in een residentie
    public int getResidents() {
        return residents;
    }

    // Breng het aantal residenten eventueel terug naar het maximum als de capaciteit gezakt is
    @Override
    public void invalidated(Observable observable) {
        super.invalidated(observable);
        residents = Math.min(residents, capacity.getValue().intValue());
    }

    @Override
    public <R> R accept(InfoVisitor<R> visitor) {
        return visitor.visitResidence(this);
    }
}
