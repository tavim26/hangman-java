package io.github.tavim26.hangman.model;

public class Player
{
    private final int playerID;
    private final String username;
    private final String email;
    private final String password;

    public Player(int playerID, String username, String email, String password)
    {
        this.playerID = playerID;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /** A new player that has not been saved to the database yet. */
    public Player(String username, String email, String password)
    {
        this(0, username, email, password);
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

    public String getPassword()
    {
        return password;
    }
}