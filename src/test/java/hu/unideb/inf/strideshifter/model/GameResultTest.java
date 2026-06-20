package hu.unideb.inf.strideshifter.model;

import org.junit.jupiter.api.Test;
import java.time.ZonedDateTime;
import static org.junit.jupiter.api.Assertions.*;

class GameResultTest {

    @Test
    void testValidGameResult() {
        ZonedDateTime now = ZonedDateTime.now();
        GameResult result = new GameResult("TestPlayer", 15, now);

        assertEquals("TestPlayer", result.playerName());
        assertEquals(15, result.moveCount());
        assertEquals(now, result.created());
    }

    @Test
    void testBlankPlayerNameThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameResult("", 10, ZonedDateTime.now()));

        assertThrows(IllegalArgumentException.class,
                () -> new GameResult("   ", 10, ZonedDateTime.now()));

        assertThrows(IllegalArgumentException.class,
                () -> new GameResult(null, 10, ZonedDateTime.now()));
    }

    @Test
    void testNegativeMoveCountThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameResult("Player", -5, ZonedDateTime.now()));
    }
}