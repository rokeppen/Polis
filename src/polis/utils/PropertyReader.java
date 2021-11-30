package polis.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import polis.Main;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * Utility class waarin het inlezen en de afhandeling van de parameters gebeurt.
 */
public final class PropertyReader {

    public static final Properties properties = new Properties();
    public static final int CELL_SIZE = 64;
    public static final int POLIS_DIMENSION = 32;
    public static final int ACTOR_SIZE = 10;

    public PropertyReader() {
        throw new UnsupportedOperationException();
    }

    public static void read() {
        try (InputStream in = Main.class.getResourceAsStream("engine.properties")) {
            properties.load(in);
        } catch (Exception ignored) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The property file could not be read.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(1);
            }
        }
    }

    public static int readInt(String string) {
        if (properties.isEmpty()) {
            read();
        }
        return Integer.parseInt(properties.getProperty(string));
    }

    public static double readDouble(String string) {
        if (properties.isEmpty()) {
            read();
        }
        return Double.parseDouble(properties.getProperty(string));
    }
}
