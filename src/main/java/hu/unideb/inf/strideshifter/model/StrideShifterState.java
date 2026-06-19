package hu.unideb.inf.strideshifter.model;

import java.util.Set;

public class StrideShifterState {

    /** The size of the board (8x8).*/
    public static final int BOARD_SIZE=8;

    /** The starting position of the token (top-left corner). */
    public static final Position START_POSITION=new Position(0,0);

    /** The goal position to reach (bottom-right corner). */
    public static final Position GOAL_POSITION=new Position(BOARD_SIZE-1,BOARD_SIZE-1);

    /** The initial step size of the token. */
    public static final int INITIAL_STEP_SIZE=2;

    /** The positions of the walls, where the token cannot step. */
    public static final Set<Position> WALLS=Set.of(
            new Position(2,2),
            new Position(2,7),
            new Position(4,1),
            new Position(5,5),
            new Position(7,3)
    );

    /** The positions of the step-changing cells. */
    public static final Set<Position> SHIFTER_CELLS=Set.of(
            new Position(0,4),
            new Position(1,2),
            new Position(1,6),
            new Position(3,2),
            new Position(3,4),
            new Position(4,0),
            new Position(4,3),
            new Position(4,7),
            new Position(5,3),
            new Position(5,6),
            new Position(6,2),
            new Position(6,7),
            new Position(7,0)
    );

    private Position currentPosition;
    private int currentStepSize;

    /**
     * Creates a new game state set to the initial position and step size.
     */
    public StrideShifterState(){
        this.currentPosition=START_POSITION;
        this.currentStepSize=INITIAL_STEP_SIZE;
    }

    /**
     * Gets the current position of the token on the board.
     *
     * @return the current position
     */
    public Position getCurrentPosition(){
        return currentPosition;
    }

    /**
     * Gets the current required step size
     *
     * @return the current step size
     */
    public int getCurrentStepSize(){
        return currentStepSize;
    }

    /**
     * Checks if the puzzle is solved (the token has reached the goal).
     *
     * @return true if the token is at the goal position, false otherwise
     */
    public boolean isGoal(){
        return currentPosition.equals(GOAL_POSITION);
    }
}
