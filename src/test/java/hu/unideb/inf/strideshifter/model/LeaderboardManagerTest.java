package hu.unideb.inf.strideshifter.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.ZonedDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LeaderboardManagerTest {

    private static final File ACTUAL_FILE = new File("leaderboard.json");
    private static final File BACKUP_FILE = new File("leaderboard_backup.json");

    @BeforeEach
    void setUp() throws IOException {
        if (ACTUAL_FILE.exists()) {
            Files.copy(ACTUAL_FILE.toPath(), BACKUP_FILE.toPath(), StandardCopyOption.REPLACE_EXISTING);
            ACTUAL_FILE.delete();
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        if (BACKUP_FILE.exists()) {
            Files.copy(BACKUP_FILE.toPath(), ACTUAL_FILE.toPath(), StandardCopyOption.REPLACE_EXISTING);
            BACKUP_FILE.delete();
        } else {
            ACTUAL_FILE.delete();
        }
    }

    @Test
    void testEmptyLoad() throws IOException {
        List<GameResult> results = LeaderboardManager.loadResults();
        assertTrue(results.isEmpty(), "Must be empty if the leaderboard's file does not exist.");
    }

    @Test
    void testAddAndLoadResults() throws IOException {
        ZonedDateTime baseTime = ZonedDateTime.now();

        GameResult result1 = new GameResult("Player1", 10, baseTime);
        GameResult result2 = new GameResult("Player2", 5, baseTime.plusMinutes(1));
        GameResult result3 = new GameResult("Player3", 10, baseTime.minusMinutes(1));

        LeaderboardManager.addResult(result1);
        LeaderboardManager.addResult(result2);
        LeaderboardManager.addResult(result3);

        List<GameResult> loadedResults = LeaderboardManager.loadResults();

        assertEquals(3, loadedResults.size());

        assertEquals("Player2", loadedResults.get(0).playerName());
        assertEquals("Player3", loadedResults.get(1).playerName());
        assertEquals("Player1", loadedResults.get(2).playerName());
    }
}