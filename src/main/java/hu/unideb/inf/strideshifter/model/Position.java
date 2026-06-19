package hu.unideb.inf.strideshifter.model;

/**
 * Represents a 2D coordinate on the puzzle board.
 *
 * @param row the row index (0-based, top to bottom)
 * @param col the column index (0-based, left to right)
 */
public record Position(int row, int col) {
}
