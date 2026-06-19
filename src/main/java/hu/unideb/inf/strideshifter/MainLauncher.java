package hu.unideb.inf.strideshifter;

import javafx.application.Application;

/**
 * A wrapper class to launch JavaFX Application.
 */
public class MainLauncher {
    /**
     * The main entry point to launch the StrideShifter JavaFX application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Application.launch(StrideShifterApp.class,args);
    }
}