package hu.unideb.inf.strideshifter.controller;

import hu.unideb.inf.strideshifter.model.GameResult;
import hu.unideb.inf.strideshifter.model.LeaderboardManager;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.tinylog.Logger;

/**
 * Controller class for the Leaderboard scene.
 * It loads and displays the top 10 game results in a TableView.
 */
public class LeaderboardController {

    @FXML
    private TableView<GameResult> leaderboardTable;

    @FXML
    private TableColumn<GameResult, String> playerColumn;

    @FXML
    private TableColumn<GameResult, Integer> movesColumn;

    @FXML
    private TableColumn<GameResult, String> dateColumn;

    /**
     * Initializes the controller class. Reads the sorted data from the JSON file
     * and populates the Top 10 results into the table.
     */
    @FXML
    public void initialize() {
        Logger.info("LeaderboardController initialized.");

        // Oszlopok összekötése a Record mezőivel
        playerColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().playerName()));

        movesColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().moveCount()));

        dateColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return new ReadOnlyStringWrapper(cellData.getValue().created().format(formatter));
        });

        loadTopTen();
    }

    /**
     * Loads the top 10 results from the persistence layer.
     */
    private void loadTopTen() {
        try {
            List<GameResult> allResults = LeaderboardManager.loadResults();
            // Csak a legjobb 10-et jelenítjük meg (mivel eleve rendezve vannak mentve)
            int limit = Math.min(allResults.size(), 10);
            List<GameResult> topTen = allResults.subList(0, limit);

            ObservableList<GameResult> observableResult = FXCollections.observableArrayList(topTen);
            leaderboardTable.setItems(observableResult);
        } catch (IOException e) {
            Logger.error(e, "Failed to load leaderboard data for UI.");
        }
    }

    /**
     * Handles the action to return to the main opening menu.
     *
     * @param event the button click event
     * @throws IOException if the opening scene FXML cannot be loaded
     */
    @FXML
    private void backToMenuAction(ActionEvent event) throws IOException {
        Logger.info("Returning to main menu from leaderboard.");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/opening.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 800));
        stage.show();
    }
}