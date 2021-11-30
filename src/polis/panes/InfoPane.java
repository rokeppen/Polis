package polis.panes;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import polis.utils.GetStatistics;
import polis.utils.InfoVisitorAcceptor;

/**
 * Het InfoPane bevat de statistieken van de component die geselecteerd werd.
 */
public class InfoPane extends VBox implements InvalidationListener {

    private InfoVisitorAcceptor model;

    public InfoPane(InfoVisitorAcceptor model) {
        this.model = model;
        model.addListener(this);
        invalidated(model);
    }

    public void setModel(InfoVisitorAcceptor model) {
        this.model = model;
        invalidated(model);
    }

    // Laat de statistieken van het model opnieuw berekenen als het model gewijzigd wordt
    @Override
    public void invalidated(Observable observable) {
        getChildren().clear();
        Label header = new Label(model.toString());
        VBox subPane = GetStatistics.of(model);
        getChildren().addAll(header, subPane);
    }
}
