package hu.unideb.inf.strideshifter.controller;

import hu.unideb.inf.strideshifter.model.Direction;
import hu.unideb.inf.strideshifter.model.Position;
import hu.unideb.inf.strideshifter.model.StrideShifterState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.tinylog.Logger;

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
            // TODO: Ide fog jönni a LeaderboardManager-es mentés!
        }
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
