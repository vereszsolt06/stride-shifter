package hu.unideb.inf.strideshifter.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testDirectionValues() {
        assertEquals(-1, Direction.UP.getRowChange());
        assertEquals(0, Direction.UP.getColChange());

        assertEquals(1, Direction.DOWN.getRowChange());

        assertEquals(4, Direction.values().length);
        assertEquals(Direction.LEFT, Direction.valueOf("LEFT"));
    }
}