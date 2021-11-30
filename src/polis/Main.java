package polis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import polis.panes.MainPane;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new MainPane());
        scene.getStylesheets().add("polis/style.css");
        stage.setScene(scene);
        stage.setTitle("Polis - 2021 © Universiteit Gent");
        stage.show();
    }
}
