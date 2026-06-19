package hu.unideb.inf.strideshifter.model;

/**
 * Represents a 2D coordinate on the puzzle board.
 *
 * @param row the row index (0-based, top to bottom)
 * @param col the column index (0-based, left to right)
 */
public record Position(int row, int col) {

    /**
     * Calculates a new position obtained by moving from this position
     * in the specified direction by a given distance.
     *
     * @param direction the direction of the movement
     * @param distance the number of cells to move
     * @return the new position after the movement
     */
    public Position move(Direction direction, int distance){
        return new Position(
                this.row+direction.getRowChange()*distance,
                this.col+direction.getColChange()*distance
        );
    }
}
