package io.github.tavim26.hangman.model;

public class Player
{
    private final int playerID;
    private final String username;
    private final String email;
    private final String passwordHash;

    public Player(int playerID, String username, String email, String passwordHash)
    {
        this.playerID = playerID;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /** A new player that has not been saved to the database yet. */
    public Player(String username, String email, String passwordHash)
    {
        this(0, username, email, passwordHash);
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }
}