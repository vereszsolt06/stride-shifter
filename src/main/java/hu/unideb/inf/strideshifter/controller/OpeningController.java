package hu.unideb.inf.strideshifter.controller;

import hu.unideb.inf.strideshifter.model.StrideShifterState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class OpeningController {

    /** The text field where the player inputs their name. */
    @FXML
    private TextField playerNameField;

    /** The button used to launch the game after entering a valid name. */
    @FXML
    private Button startButton;

    /** The label used to display error messages to the user. */
    @FXML
    private Label errorLabel;

    /**
     * Initializes the controller class.
     * Automatically called after the FXML file has been loaded.
     * Binds the start button's disabled property
     * to the empty state of the player name text field.
     */
    @FXML
    public void initialize(){
        Logger.info("OpeningController has been successfully initialized.");
        startButton.disableProperty().bind(playerNameField.textProperty().isEmpty());
    }


    @FXML
    private void startAction(ActionEvent event) throws IOException{
        String playerName=playerNameField.getText().trim();
        if(playerName.isEmpty()){
            errorLabel.setText("Please enter a name!");
            return;
        }

        Logger.info("Starting game for player: {}",playerName);
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/ui/game.fxml"));
        Parent root=fxmlLoader.load();

        StrideShifterController gameController=fxmlLoader.getController();
        gameController.setPlayerName(playerName);

        Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root,800,800));
        stage.show();
    }

    /**
     * Handles the action when the view leaderboard button is clicked.
     * Switches the scene directly to the leaderboard view.
     *
     * @param event the action event triggered by clicking the button
     * @throws IOException if the leaderboard FXML file cannot be loaded
     */
    @FXML
    private void viewLeaderboardAction(ActionEvent event) throws IOException {
        Logger.info("Switching to leaderboard scene from the main menu.");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/leaderboard.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));
        stage.show();
    }
}
