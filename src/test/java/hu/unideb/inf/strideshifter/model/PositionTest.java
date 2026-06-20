package hu.unideb.inf.strideshifter.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testMoveUp() {
        Position start = new Position(3, 3);
        Position end = start.move(Direction.UP, 2);
        assertEquals(new Position(1, 3), end);
    }

    @Test
    void testMoveDown() {
        Position start = new Position(3, 3);
        Position end = start.move(Direction.DOWN, 3);
        assertEquals(new Position(6, 3), end);
    }

    @Test
    void testMoveLeft() {
        Position start = new Position(3, 3);
        Position end = start.move(Direction.LEFT, 1);
        assertEquals(new Position(3, 2), end);
    }

    @Test
    void testMoveRight() {
        Position start = new Position(3, 3);
        Position end = start.move(Direction.RIGHT, 4);
        assertEquals(new Position(3, 7), end);
    }
}