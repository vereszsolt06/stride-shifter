package hu.unideb.inf.strideshifter.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.tinylog.Logger;

/**
 * Controller class for the main game board scene of the StrideShifter game.
 * Manages the visuals of the 8x8 grid and handles user inputs.
 */
public class StrideShifterController {

    /** The label that displays the current status of the game, including player name and step size. */
    @FXML
    private Label statusLabel;

    /** The grid pane container that holds the 8x8 game cells. */
    @FXML
    private GridPane boardGrid;

    /** The name of the player currently playing the game. */
    private String playerName;

    /**
     * Initializes the controller class.
     * Automatically called after the FXML file has been loaded.
     */
    @FXML
    public void initialize(){
        Logger.info("The JavaFX Controller initialization has started.");
    }

    /**
     * Sets the player name for the current session and updates the status label.
     * Called by the OpeningController during scene switching.
     *
     * @param playerName the name of the player passed by an opening scene
     */
    public void setPlayerName(String playerName){
        this.playerName=playerName;
        Logger.info("Game controller received player name: {}", playerName);
        statusLabel.setText(playerName+"'s Game | Step Size: 2");
    }
}
