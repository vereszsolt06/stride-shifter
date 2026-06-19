package hu.unideb.inf.strideshifter.model;

import java.time.ZonedDateTime;

/**
 * Represents the result of a completed game, used for the leaderboard.
 *
 * @param playerName the name of the player
 * @param moveCount the total number of moves taken to solve the puzzle
 * @param created the timestamp when the game was completed
 */
public record GameResult(String playerName, int moveCount, ZonedDateTime created) {

    /**
     * Constructs a {@code GameResult} and validates that the player name is not null or empty,
     * and move count is non-negative.
     *
     * @throws IllegalArgumentException if the name is blank or move count is negative
     */
    public GameResult {
        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be blank");
        }
        if (moveCount < 0) {
            throw new IllegalArgumentException("Move count cannot be negative");
        }
    }
}