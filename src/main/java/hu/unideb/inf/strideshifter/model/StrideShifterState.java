package hu.unideb.inf.strideshifter.model;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import puzzle.State;

public class StrideShifterState implements State<Direction, StrideShifterState> {

    /**
     * The size of the board (8x8).
     */
    public static final int BOARD_SIZE = 8;

    /**
     * The starting position of the token (top-left corner).
     */
    public static final Position START_POSITION = new Position(0, 0);

    /**
     * The goal position to reach (bottom-right corner).
     */
    public static final Position GOAL_POSITION = new Position(BOARD_SIZE - 1, BOARD_SIZE - 1);

    /**
     * The initial step size of the token.
     */
    public static final int INITIAL_STEP_SIZE = 2;

    /**
     * Feature Toggle: if true, the token could not jump over walls.
     * If false, the token cannot step on the walls but could jump over.
     */
    public static final boolean STRICT_WALLS = true;

    /**
     * The positions of the walls, where the token cannot step.
     */
    public static final Set<Position> WALLS = Set.of(
            new Position(2, 2),
            new Position(2, 7),
            new Position(4, 1),
            new Position(5, 5),
            new Position(7, 3)
    );

    /**
     * The positions of the step-changing cells.
     */
    public static final Set<Position> SHIFTER_CELLS = Set.of(
            new Position(0, 4),
            new Position(1, 2),
            new Position(1, 6),
            new Position(3, 2),
            new Position(3, 4),
            new Position(4, 0),
            new Position(4, 3),
            new Position(4, 7),
            new Position(5, 3),
            new Position(5, 6),
            new Position(6, 2),
            new Position(6, 7),
            new Position(7, 0)
    );

    private Position currentPosition;
    private int currentStepSize;

    /**
     * Creates a new game state set to the initial position and step size.
     */
    public StrideShifterState() {
        this.currentPosition = START_POSITION;
        this.currentStepSize = INITIAL_STEP_SIZE;
    }

    /**
     * Creates a deep copy of the specified {@code StrideShifterState} object.
     *
     * @param other the state object to be copied
     */
    public StrideShifterState(StrideShifterState other) {
        this.currentPosition = other.currentPosition;
        this.currentStepSize = other.currentStepSize;
    }

    /**
     * Checks if the puzzle is solved.
     *
     * @return true if the token is at the goal position, false otherwise
     */
    @Override
    public boolean isSolved() {
        return currentPosition.equals(GOAL_POSITION);
    }

    /**
     * Checks if the given position is within the boundaries of the board.
     *
     * @param position the position to check
     * @return true if the position is valid, false otherwise
     */
    public boolean isOnBoard(Position position) {
        return position.row() >= 0 && position.row() < BOARD_SIZE
                && position.col() >= 0 && position.col() < BOARD_SIZE;
    }

    /**
     * Checks if moving in the specified direction is legal from the current state.
     * Uses the STRICT_WALLS toggle to determine if jumping over walls is allowed.
     *
     * @param move the direction to move
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isLegalMove(Direction move) {
        if (move == null) {
            return false;
        }

        if (STRICT_WALLS) {
            for (int i = 1; i <= currentStepSize; i++) {
                Position pathPosition = currentPosition.move(move, i);
                if (!isOnBoard(pathPosition) || WALLS.contains(pathPosition)) {
                    return false;
                }
            }
            return true;
        } else {
            Position nextPosition = currentPosition.move(move, currentStepSize);
            return isOnBoard(nextPosition) && !WALLS.contains(nextPosition);
        }
    }

    /**
     * Moves the token in the given direction.
     * Updates the position and toggles the step size if landing on a shifter cell.
     *
     * @param direction the direction to move
     * @throws IllegalArgumentException if the move is not legal
     */
    @Override
    public void makeMove(Direction direction) {
        if (!isLegalMove(direction)) {
            throw new IllegalArgumentException("Cannot move in direction " + direction);
        }

        currentPosition = currentPosition.move(direction, currentStepSize);

        if (SHIFTER_CELLS.contains(currentPosition)) {
            currentStepSize = (currentStepSize == 2) ? 3 : 2;
        }
    }

    /**
     * Returns the set of all legal moves from the current state.
     *
     * @return a set containing all legal directions
     */
    @Override
    public Set<Direction> getLegalMoves() {
        Set<Direction> moves = EnumSet.noneOf(Direction.class);
        for (Direction dir : Direction.values()) {
            if (isLegalMove(dir)) {
                moves.add(dir);
            }
        }
        return moves;
    }

    /**
     * Returns a copy of the current state.
     *
     * @return a new state instance with identical properties
     */
    @Override
    public StrideShifterState copy() {
        return new StrideShifterState(this);
    }

    /**
     * Gets the current position of the token on the board.
     *
     * @return the current position
     */
    public Position getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Gets the current required step size
     *
     * @return the current step size
     */
    public int getCurrentStepSize() {
        return currentStepSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return currentStepSize == ((StrideShifterState) o).currentStepSize && Objects.equals(currentPosition, ((StrideShifterState) o).currentPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPosition, currentStepSize);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StrideShifterState{");
        sb.append("currentPosition=").append(currentPosition);
        sb.append(", currentStepSize=").append(currentStepSize);
        sb.append('}');
        return sb.toString();
    }
}
