package hu.unideb.inf.strideshifter.model;

/**
 * Represents the four main directions of movement on the board.
 */
public enum Direction {

    /** Movement up (decreases row index). */
    UP(-1, 0),

    /** Movement right (increases column index). */
    RIGHT(0, 1),

    /** Movement down (increases row index). */
    DOWN(1, 0),

    /** Movement left (decreases column index). */
    LEFT(0, -1);

    private final int rowChange;
    private final int colChange;

    /**
     * Constructs a direction with the specified row and column changes.
     *
     * @param rowChange the change in the row index
     * @param colChange the change in the column index
     */
    Direction(int rowChange, int colChange){
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * Gets the change in the row index for this direction.
     *
     * @return the row change
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * Gets the change in the column index for this direction.
     *
     * @return the column change
     */
    public int getColChange() {
        return colChange;
    }
}
