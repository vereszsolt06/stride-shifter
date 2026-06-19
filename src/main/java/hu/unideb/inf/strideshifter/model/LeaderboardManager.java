package hu.unideb.inf.strideshifter.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.tinylog.Logger;

/**
 * Utility class for persisting and loading leaderboard results.
 */
public final class LeaderboardManager {

    /**
     * The hidden file in the user's home directory where scores are stored.
     */
    private static final File LEADERBOARD_FILE =
            Path.of(System.getProperty("user.home"), ".strideshifter_scores.json").toFile();

    /**
     * Configured Jackson mapper for handling JSON serialization, including Java 8 Dates.
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private LeaderboardManager() {
    }

    /**
     * Appends a new game result to the leaderboard JSON file.
     *
     * @param result the completed game result to add
     * @throws IOException if a file writing error occurs
     */
    public static void addResult(GameResult result) throws IOException {
        List<GameResult> currentScores = loadResults();
        currentScores.add(result);
        currentScores.sort(Comparator.comparingInt(GameResult::moveCount)
                .thenComparing(GameResult::created));

        JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValue(LEADERBOARD_FILE, currentScores);
        Logger.info("Result successfully written to: {}", LEADERBOARD_FILE.getAbsolutePath());
    }

    /**
     * Retrieves all saved game results from the leaderboard file.
     *
     * @return a list of past game results, or an empty list if the file does not exist
     * @throws IOException if a file reading error occurs
     */
    public static List<GameResult> loadResults() throws IOException {
        if (!LEADERBOARD_FILE.exists()) {
            Logger.debug("Leaderboard file not found, returning an empty list.");
            return new ArrayList<>();
        }

        Logger.debug("Reading leaderboard data from: {}", LEADERBOARD_FILE.getAbsolutePath());

        // A TypeReference egy sokkal modernebb alternatíva a Jackson gyűjtemények beolvasására
        return JSON_MAPPER.readValue(LEADERBOARD_FILE, new TypeReference<List<GameResult>>() {});
    }
}