package polis.utils;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import polis.core.Polis;
import polis.tiles.*;

/**
 * Bepaal de gegevens die over een bepaald informatiemodel worden weergegeven in het infopaneel.
 */
public class GetStatistics implements InfoVisitor<VBox> {
    @Override
    public VBox visitCommerce(Commerce commerce) {
        Label l1 = new Label("Jobs: " + commerce.getWorkers() + '/' + round(commerce.getJobCapacity()));
        Label l2 = new Label("Goods: " + commerce.getGoods() + '/' + round(commerce.getGoodsCapacity()));
        Label l3 = new Label("Customers: " + commerce.getCustomers() + '/' + round(commerce.getCapacity()));
        return new VBox(l1, l2, l3);
    }

    @Override
    public VBox visitIndustry(Industry industry) {
        Label l = new Label("Jobs: " + industry.getWorkers() + '/' + round(industry.getCapacity()));
        return new VBox(l);
    }

    @Override
    public VBox visitResidence(Residence residence) {
        Label l = new Label("Residents: " + residence.getResidents() + '/' + round(residence.getCapacity()));
        return new VBox(l);
    }

    @Override
    public VBox visitPolis(Polis polis) {
        int jobs = polis.getCommerces().stream().mapToInt(Commerce::getWorkers).sum() +
                polis.getIndustries().stream().mapToInt(Industry::getWorkers).sum();
        double maxJobs = polis.getCommerces().stream().mapToDouble(Commerce::getJobCapacity).sum() +
                polis.getIndustries().stream().mapToDouble(Industry::getCapacity).sum();
        int residents = polis.getResidences().stream().mapToInt(Residence::getResidents).sum();
        double maxResidents = polis.getResidences().stream().mapToDouble(Residence::getCapacity).sum();
        int goods = polis.getCommerces().stream().mapToInt(Commerce::getGoods).sum();
        double maxGoods = polis.getCommerces().stream().mapToDouble(Commerce::getGoodsCapacity).sum();
        int customers = polis.getCommerces().stream().mapToInt(Commerce::getCustomers).sum();
        double maxCustomers = polis.getCommerces().stream().mapToDouble(Commerce::getCapacity).sum();
        Label l1 = new Label("Residents: " + residents + '/' + round(maxResidents));
        Label l2 = new Label("Jobs: " + jobs + '/' + round(maxJobs));
        Label l3 = new Label("Goods: " + goods + '/' + round(maxGoods));
        Label l4 = new Label("Customers: " + customers + '/' + round(maxCustomers));
        return new VBox(l1, l2, l3, l4);
    }

    public static VBox of(InfoVisitorAcceptor model) {
        return model.accept(new GetStatistics());
    }

    public double round(double d) {
        return Math.round(d*10)/10.0;
    }
}
