package hu.unideb.inf.strideshifter.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.tinylog.Logger;

public class StrideShifterController {

    @FXML
    private Label statusLabel;

    @FXML
    private GridPane boardGrid;

    @FXML
    public void initialize(){
        Logger.info("The JavaFX Controller initialization has started.");

    }
}
