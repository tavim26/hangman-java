package io.github.tavim26.hangman.service;

import io.github.tavim26.hangman.database.PlayerDao;
import io.github.tavim26.hangman.model.Player;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class PlayerService
{
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MIN_PASSWORD_LENGTH = 6;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final PlayerDao playerDao = new PlayerDao();

    public boolean isValidUsername(String username)
    {
        return username != null && !username.isBlank() && username.length() <= MAX_USERNAME_LENGTH;
    }

    public boolean isValidEmail(String email)
    {
        return email != null && email.length() <= MAX_EMAIL_LENGTH && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isValidPassword(String password)
    {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
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