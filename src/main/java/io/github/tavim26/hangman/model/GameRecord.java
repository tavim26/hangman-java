package io.github.tavim26.hangman.model;

/** A finished game, as stored in the database and shown in the games history. */
public class GameRecord
{
    private final int gameID;
    private final int playerID;
    private final String gameMode;
    private final String word;
    private final int remainingAttempts;
    private final String status;

    public GameRecord(int gameID, int playerID, String gameMode, String word, int remainingAttempts, String status)
    {
        this.gameID = gameID;
        this.playerID = playerID;
        this.gameMode = gameMode;
        this.word = word;
        this.remainingAttempts = remainingAttempts;
        this.status = status;
    }

    /** A new record that has not been saved to the database yet. */
    public GameRecord(int playerID, String gameMode, String word, int remainingAttempts, String status)
    {
        this(0, playerID, gameMode, word, remainingAttempts, status);
    }

    public int getGameID()
    {
        return gameID;
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public String getGameMode()
    {
        return gameMode;
    }

    public String getWord()
    {
        return word;
    }

    public int getRemainingAttempts()
    {
        return remainingAttempts;
    }

    public String getStatus()
    {
        return status;
    }
}