package io.github.tavim26.hangman.service;

import io.github.tavim26.hangman.database.PlayerDao;
import io.github.tavim26.hangman.model.Player;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class PlayerService
{
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final PlayerDao playerDao = new PlayerDao();

    public boolean isValidEmail(String email)
    {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isValidUsername(String username)
    {
        return username != null;
    }

    public boolean isValidPassword(String password)
    {
        return password != null;
    }

    public boolean isUsernameTaken(String username) throws SQLException
    {
        return playerDao.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) throws SQLException
    {
        return playerDao.existsByEmail(email);
    }

    /** Returns the matching player, or null if the credentials are wrong. */
    public Player login(String username, String password) throws SQLException
    {
        return playerDao.findByUsernameAndPassword(username, password);
    }

    public void register(Player player) throws SQLException
    {
        playerDao.addPlayer(player);
    }
}