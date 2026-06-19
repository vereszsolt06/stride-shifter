package hu.unideb.inf.strideshifter.controller;

import hu.unideb.inf.strideshifter.model.Position;
import hu.unideb.inf.strideshifter.model.StrideShifterState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    /** The core game logic model. */
    private StrideShifterState gameState;

    /**
     * Initializes the controller class.
     * Automatically called after the FXML file has been loaded.
     */
    @FXML
    public void initialize(){
        Logger.info("The JavaFX Controller initialization has started.");
    }

    /**
     * Sets the player name, initializes a new game state,
     * and draws the board.
     *
     * @param playerName the name of the player passed by an opening scene
     */
    public void setPlayerName(String playerName){
        this.playerName=playerName;
        this.gameState=new StrideShifterState();
        Logger.info("Starting new game for: {}", playerName);
        updateStatusLabel();
        drawBoard();
    }


    /**
     * Updates the text of the status label to reflect the current game state.
     */
    private void updateStatusLabel() {
        statusLabel.setText(String.format("%s's Game | Current Step Size: %d",
                playerName,gameState.getCurrentStepSize()));
    }

    /**
     * Dynamically generates and draws the 8x8 game board based on the Model's state.
     */
    private void drawBoard() {
        boardGrid.getChildren().clear();

        for(int i=0;i<StrideShifterState.BOARD_SIZE;i++){
            for (int j=0;j<StrideShifterState.BOARD_SIZE;j++){
                StackPane cell=createCell(new Position(i,j));
                boardGrid.add(cell,j,i);
            }
        }
    }

    /**
     * Creates a visual representation (StackPane) for a single cell on the board.
     *
     * @param pos the position of the cell
     * @return the styled StackPane containing the necessary visuals (walls, tokens, etc.)
     */
    private StackPane createCell(Position pos) {
        StackPane cell = new StackPane();
        cell.setPrefSize(70, 70); // 70x70 pixeles mezők

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

        return cell;
    }
}
