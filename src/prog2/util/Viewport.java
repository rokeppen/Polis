package prog2.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Dit paneel biedt een (gedeeltelijk) 'zicht' op een ander paneel (= de 'client').
 *
 * Je kan het muiswiel gebruiken om in en uit te zoomen op de client, of de plus- en mintoetsen.
 *
 * Door de muis dicht tegen de rand van het paneel te bewegen, verschuif je de client weg van die rand.
 * Verschuiven kan ook door de pijltjestoetsen (blijvend) in te drukken.
 *
 * Met de toets 0, breng je de client terug naar zijn beginpositie en -grootte.
 */
public class Viewport extends Pane {

    private int sensitiveMargin;
    private int moveInterval;
    private double moveDistance;

    private final Pane client;

    private final double initialZoom;

    private final Timeline timeline;

    /**
     * Een viewport waarin je de gegeven <i>client</i> kan zien.
     * @param initialZoom Zoomfactor waarmee de client initieel wordt getoond.
     */
    public Viewport(Pane client, double initialZoom) {
        this.client = client;
        this.initialZoom = initialZoom;

        this.sensitiveMargin = 20;
        this.moveInterval = 30;
        this.moveDistance = 10.0;

        client.setScaleX(initialZoom);
        client.setScaleY(initialZoom);
        getChildren().add(client);

        setOnScroll(this::handleScroll);

        this.timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(moveInterval),
                        this::eachTick)
        );
        setOnMouseMoved(this::mouseMoved);
        setOnMouseExited(o -> timeline.stop());

        setOnKeyReleased(this::handleKeyReleased);
        setOnKeyPressed(this::handleKeyPressed);

    }

    /**
     * Marge waarbinnen de muispointer de client doet verschuiven
     */
    public void setSensitiveMargin(int sensitiveMargin) {
        this.sensitiveMargin = sensitiveMargin;
    }

    /**
     * Hoeveel milliseconden er verlopen tussen elke verschuiving
     */
    public void setMoveInterval(int moveInterval) {
        this.moveInterval = moveInterval;
    }

    /**
     * Hoeveel pixels de client verschuift bij elke verschuiving
     */
    public void setMoveDistance(double moveDistance) {
        this.moveDistance = moveDistance;
    }

    private void handleKeyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case LEFT:
            case RIGHT:
            case UP:
            case DOWN:
                timeline.stop();
                break;
        }
        switch (keyEvent.getText()) {
            case "+":
                zoomRelative(1.1);
                break;
            case "-":
                zoomRelative(0.91);
                break;
            case "0":
                resetView();
                break;
        }
    }

    private void handleKeyPressed(KeyEvent keyEvent) {
        // Deze code heeft heel wat gemeenschappelijk met
        // die van mouseMoved hieronder, maar dat lijkt niet gemakkelijk
        // te vermijden
        switch (keyEvent.getCode()) {
            case LEFT:
                dY = 0;
                dX = -moveDistance;
                timeline.play();
                break;
            case RIGHT:
                dY = 0;
                dX = moveDistance;
                timeline.play();
                break;
            case UP:
                dX = 0;
                dY = -moveDistance;
                timeline.play();
                break;
            case DOWN:
                dX = 0;
                dY = moveDistance;
                timeline.play();
                break;
        }
    }

    private double dX;
    private double dY;

    private void eachTick(ActionEvent actionEvent) {
        client.setTranslateX(client.getTranslateX() + dX * client.getScaleX());
        client.setTranslateY(client.getTranslateY() + dY * client.getScaleY());
    }

    private void mouseMoved(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        dX = 0.0;
        dY = 0.0;
        if (x <= sensitiveMargin) {
            dX = moveDistance;
        } else if (x + sensitiveMargin >= getWidth()) {
            dX = -moveDistance;
        }
        if (y <= sensitiveMargin) {
            dY = moveDistance;
        } else if (y + sensitiveMargin >= getHeight()) {
            dY = -moveDistance;
        }
        if (dX == 0.0 && dY == 0.0) {
            timeline.stop();
        } else {
            timeline.play();
        }
    }

    private void handleScroll(ScrollEvent scrollEvent) {
        double deltaY = scrollEvent.getDeltaY();
        double zoomFactor;
        if (deltaY == 0.0) {
            return; // eigenaardig genoeg komt dit inderdaad regelmatig voor
        } else if (deltaY < 0) {
            zoomFactor = 0.91;
        } else {
            zoomFactor = 1.1;
        }
        zoomRelative(zoomFactor);
    }

    private void zoomRelative(double zoomFactor) {
        double newScale = client.getScaleX() * zoomFactor;
        client.setScaleX(newScale);
        client.setScaleY(newScale);
    }

    /**
     * Plaatst de client terug in de beginpositie en -grootte
     */
    private void resetView() {
        client.setScaleX(initialZoom);
        client.setScaleY(initialZoom);
        client.setTranslateX(0.0);
        client.setTranslateY(0.0);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        client.relocate(
                0.5 * (getWidth() - client.getWidth()),
                0.5 * (getHeight() - client.getHeight())
        );
    }

}
