package polis.tiles;

import javafx.beans.Observable;
import polis.core.Polis;
import polis.actors.Actor;
import polis.actors.Merchant;
import polis.utils.Adres;
import polis.utils.InfoVisitor;
import polis.utils.RCCoordinate;
import static polis.utils.PropertyReader.readDouble;

public class Commerce extends Employer {

    private int customers = 0;
    private int goods = 0;
    private final double cpt = readDouble("customers.per.trader");
    private final double gpc = readDouble("goods.per.customer");
    private double jobCapacity = capacity.getValue()/cpt;
    private double goodsCapacity = capacity.getValue()*gpc;

    public Commerce(RCCoordinate coordinate) {
        super(coordinate);
    }

    // Update de klantencapaciteit nadat een klant is binnengekomen, op voorwaarde dat de maximale capaciteit hierdoor bereikt werd
    public void trade() {
        if (customers == capacity.intValue()) {
            capacity.setValue(capacity.getValue() * readDouble("factor.good.trade"));
        }
    }

    // Update de klantencapaciteit nadat een klant geweigerd werd wegens een tekort aan goederen of handelaars
    public void noTrade() {
        capacity.setValue(Math.max(capacity.getValue()* readDouble("factor.bad.trade"), minimalCapacity));
    }

    // Kijk of een klant kan winkelen: als er genoeg goederen en handelaars zijn voor een extra klant
    public boolean canShop() {
        return workers*cpt >= customers+1 && goods >= customers + 1;
    }

    // Kijk of een klant kan binnenkomen
    public boolean canEnter() {
        return customers + 1 <= capacity.getValue();
    }

    // Kijk of een extra handelaar kan aangenomen worden
    public boolean canApply() {
        return workers + 1 <= jobCapacity;
    }

    // Kijk of een goed kan binnengebracht worden
    public boolean canStore() {
        return goods + 1 <= goodsCapacity;
    }

    // Registreer een nieuw goed
    public void store() {
        // Initialisatie van de winkel als die nog op level 0 stond
        level += level == 0 ? 1 : 0;
        goods += canStore() ? 1 : 0;
    }

    // Registreer een nieuwe klant en verwerk de impact op de capaciteit
    public void enter() {
        customers += canShop() ? 1 : 0;
    }

    // Registreer een aankoop
    public void shop() {
        goods--;
    }

    // Registreer het vertrekken van een klant
    public void leave() {
        customers--;
    }

    // Het aantal klanten in een winkel
    public int getCustomers() {
        return customers;
    }

    // Het aantal goederen in een winkel
    public int getGoods() {
        return goods;
    }

    // Hoeveel handelaars in een winkel tewerk gesteld kunnen worden
    public double getJobCapacity() {
        return jobCapacity;
    }

    // Hoeveel goederen een winkel kan ontvangen
    public double getGoodsCapacity() {
        return goodsCapacity;
    }

    // Verwerk de invloed van de nieuwe klantencapaciteit op de jobcapaciteit en de goederencapaciteit
    @Override
    public void invalidated(Observable observable) {
        super.invalidated(observable);
        jobCapacity = capacity.getValue()/cpt;
        goodsCapacity = capacity.getValue()*gpc;
    }

    @Override
    public Actor getEmployee(Polis polis, RCCoordinate coordinate, Adres adres) {
        return new Merchant(polis, coordinate, adres, this);
    }

    @Override
    public <R> R accept(InfoVisitor<R> visitor) {
        return visitor.visitCommerce(this);
    }
}
