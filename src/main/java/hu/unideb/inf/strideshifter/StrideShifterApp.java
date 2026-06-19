package hu.unideb.inf.strideshifter;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

/**
 * The main JavaFX application class for StrideShifter.
 */
public class StrideShifterApp extends Application {

    /**
     * Starts the JavaFX application by loading the opening menu scene.
     *
     * @param stage the primary stage for this application, onto which the scene will be set
     * @throws IOException if the opening FXML file cannot be found or loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        Logger.info("The JavaFX application starts...");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/opening.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("StrideShifter");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}