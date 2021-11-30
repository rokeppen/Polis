package polis.core;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import polis.actors.Actor;

import java.util.ArrayList;

/**
 * Een motor organiseert de simulatie door elke iteratie een actie uit te voeren.
 * Een motor bestaat uit een tijdslijn en een stad.
 */
public class Engine {

    private final Timeline timeline = new Timeline();
    private final Polis polis;

    public Engine(Polis polis) {
        this.polis = polis;
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250),this::eachTick));
    }

    public Timeline getTimeline() {
        return timeline;
    }

    // Elke stap worden alle actoren geüpdated en wordt de regio op de hoogte gebracht dat een stap voorbij is.
    // Vervolgens wordt aan de stad doorgegeven dat ze veranderd is, opdat die zichzelf zou hertekenen.
    public void eachTick(ActionEvent actionEvent) {
        for (Actor actor : new ArrayList<>(polis.getActors())) {
            Actor newActor = actor.tick();
            polis.getActors().remove(actor);
            if (newActor != null) {
                polis.getActors().add(newActor);
            }
        }
        polis.getRegion().tick();
        polis.fireInvalidationEvent();
    }
}
