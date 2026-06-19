package hu.unideb.inf.strideshifter;

import hu.unideb.inf.strideshifter.model.Direction;
import hu.unideb.inf.strideshifter.model.StrideShifterState;
import org.tinylog.Logger;
import puzzle.solver.BreadthFirstSearch;

/**
 * The main entry point of the StrideShifter application.
 */
public class MainConsole {

    /**
     * The main method that executes the BFS solver on the puzzle.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Logger.info("StrideShifter has started!");
        Logger.info("Breadth-First Search initialization...");

        BreadthFirstSearch<Direction, StrideShifterState> solver = new BreadthFirstSearch<>();

        solver.solveAndPrintSolution(new StrideShifterState());

        Logger.info("Search has ended.");
    }
}