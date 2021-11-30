package polis.actors;

import polis.core.Polis;
import polis.tiles.Commerce;
import polis.tiles.Zone;
import polis.utils.Adres;
import polis.utils.RCCoordinate;

public class Shopper extends MovingActor {

    public Shopper(Polis polis, Adres adres) {
        super(polis, adres.getHomeLocation(), adres);
    }

    // Een winkel kan binnengegaan worden als er genoeg capaciteit is en er genoeg handelaren en producten zijn
    @Override
    public Zone getDestination(RCCoordinate coordinate) {
        Commerce commerce = polis.getCommerceAt(coordinate);
        if (commerce != null && commerce.canEnter()) {
            if (commerce.canShop()) {
                return commerce;
            }
            // negatief effect van een niet-volle winkel met te weinig producten/handelaars
            commerce.noTrade();
        }
        return null;
    }

    // Een shopper gaat een winkel binnen en wordt een klant
    @Override
    public Actor evolve() {
        Commerce commerce = polis.getCommerceAt(direction.move(location));
        // positief effect op de residentie
        adres.getHome().shopFound();
        commerce.enter();
        // positief effect op de winkel
        commerce.trade();
        return new Customer(polis, location, adres, commerce);
    }

    // Een niet-succesvolle shopper gaat naar huis, als dat nog bestaat, en wordt een slaper
    @Override
    public Actor die() {
        // negatief effect op de residentie
        adres.getHome().noShopFound();
        return canGoHome() ? new Sleeper(polis, adres) : null;
    }
}
