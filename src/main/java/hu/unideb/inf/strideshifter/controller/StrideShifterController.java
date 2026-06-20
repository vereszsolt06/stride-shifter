package hu.unideb.inf.strideshifter.controller;

import hu.unideb.inf.strideshifter.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for the main game board scene of the StrideShifter game.
 * Manages the visuals of the 8x8 grid and handles user inputs.
 */
public class StrideShifterController {

    /**
     * The label that displays the current status of the game, including player name and step size.
     */
    @FXML
    private Label statusLabel;

    /**
     * The label that displays feedbacks from the game.
     */
    @FXML
    private Label feedbackLabel;

    /**
     * The grid pane container that holds the 8x8 game cells.
     */
    @FXML
    private GridPane boardGrid;

    /**
     * The name of the player currently playing the game.
     */
    private String playerName;

    /**
     * The core game logic model.
     */
    private StrideShifterState gameState;

    /**
     * Tracks the number of valid moves the player has made.
     */
    private int numberOfMoves;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        Logger.info("The JavaFX Controller initialization has started.");
    }

    /**
     * Sets the player name, initializes a new game state, and draws the board.
     *
     * @param playerName the name of the player passed by an opening scene
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        this.gameState = new StrideShifterState();
        this.numberOfMoves = 0;
        Logger.info("Starting new game for: {}", playerName);
        updateStatusLabel();
        drawBoard();
    }

    /**
     * Handles the action when the restart button is clicked.
     * Resets the current puzzle state and the move counter, then redraws the board.
     *
     * @param event the action event triggered by clicking the button
     */
    @FXML
    private void handleRestart(javafx.event.ActionEvent event) {
        Logger.info("Restarting the game session for player: {}", playerName);

        this.gameState = new StrideShifterState();
        this.numberOfMoves = 0;

        updateStatusLabel();
        drawBoard();
    }

    /**
     * Handles the action when the back to menu button is clicked during a game.
     * Switches the scene back to the opening screen.
     *
     * @param event the action event triggered by clicking the button
     * @throws IOException if the opening FXML file cannot be loaded
     */
    @FXML
    private void handleBackToMenu(ActionEvent event) throws IOException {
        Logger.info("Player returning to main menu from active game.");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/opening.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));
        stage.show();
    }

    /**
     * Makes a valid move on the board.
     * Called by clicking on a valid highlighted cell.
     *
     * @param direction the direction to move
     */
    private void performMove(Direction direction) {
        if (gameState.isSolved()) {
            return;
        }

        gameState.makeMove(direction);
        numberOfMoves++;

        Logger.debug("Player moved {}. Total moves: {}", direction, numberOfMoves);

        updateStatusLabel();
        drawBoard();
        checkWinCondition();
    }

    /**
     * Checks if the game has been solved after a move.
     */
    private void checkWinCondition() {
        if (gameState.isSolved()) {
            Logger.info("Game solved by {} in {} moves.", playerName, numberOfMoves);
            feedbackLabel.setStyle("-fx-text-fill: green;");
            feedbackLabel.setText("Congratulations! You reached the goal!");

            try{
                GameResult result=new GameResult(playerName, numberOfMoves, ZonedDateTime.now());
                LeaderboardManager.addResult(result);

                switchToLeaderboard();
            } catch (Exception e){
                Logger.error(e,"Failed to save result or load leaderboard UI");
            }
        }
    }

    /**
     * Switches the current scene to the Leaderboard view.
     *
     * @throws Exception if the FXML cannot be loaded
     */
    private void switchToLeaderboard() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/leaderboard.fxml"));
        Parent root = fxmlLoader.load();

        // A boardGrid segítségével lekérjük az aktuális ablakot (Stage)
        Stage stage = (Stage) boardGrid.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));
        stage.show();
    }

    /**
     * Updates the text of the status label to reflect the current game state.
     */
    private void updateStatusLabel() {
        statusLabel.setText(String.format("%s's Game | Current Step Size: %d",
                playerName, gameState.getCurrentStepSize()));
    }

    /**
     * Dynamically generates and draws the 8x8 game board based on the Model's state.
     * Calculates the destinations after possible moves for the player.
     */
    private void drawBoard() {
        boardGrid.getChildren().clear();

        Map<Position, Direction> validDestinations = new HashMap<>();
        if (!gameState.isSolved())
            for (Direction dir : gameState.getLegalMoves()) {
                Position targetPosition = gameState.getCurrentPosition().move(dir, gameState.getCurrentStepSize());
                validDestinations.put(targetPosition, dir);
            }

        for (int i = 0; i < StrideShifterState.BOARD_SIZE; i++) {
            for (int j = 0; j < StrideShifterState.BOARD_SIZE; j++) {
                StackPane cell = createCell(new Position(i, j), validDestinations);
                boardGrid.add(cell, j, i);
            }
        }
    }

    /**
     * Creates a visual representation (StackPane) for a single cell on the board.
     *
     * @param pos the position of the cell
     * @return the styled StackPane containing the necessary visuals (walls, tokens, etc.)
     */
    private StackPane createCell(Position pos, Map<Position, Direction> validDestinations) {
        StackPane cell = new StackPane();
        cell.setPrefSize(70, 70);

        //Background color
        if (StrideShifterState.WALLS.contains(pos)) {
            cell.setStyle("-fx-background-color: #2c3e50; -fx-border-color: #34495e; -fx-border-width: 1px;");
        } else {
            cell.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 1px;");
        }

        //Shifter cells
        if (StrideShifterState.SHIFTER_CELLS.contains(pos)) {
            Circle shifterCircle = new Circle(15, Color.valueOf("#f39c12")); // Narancssárga kisebb kör
            cell.getChildren().add(shifterCircle);
        }

        //Goal cell
        if (StrideShifterState.GOAL_POSITION.equals(pos)) {
            Label goalLabel = new Label("GOAL");
            goalLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
            cell.getChildren().add(goalLabel);
        }

        //Player's token
        if (gameState.getCurrentPosition().equals(pos)) {
            Circle playerToken = new Circle(25, Color.valueOf("#2980b9")); // Kék nagyobb kör
            cell.getChildren().add(playerToken);
        }

        //Valid destination
        if (validDestinations.containsKey(pos)) {
            Circle highlight = new Circle(12, Color.rgb(46, 204, 113, 0.6));
            cell.getChildren().add(highlight);
            cell.setStyle(cell.getStyle() + "-fx-cursor: hand;");

            cell.setOnMouseClicked(event -> {
                Direction directionToMove = validDestinations.get(pos);
                performMove(directionToMove);
            });
        }
        return cell;
    }
}
