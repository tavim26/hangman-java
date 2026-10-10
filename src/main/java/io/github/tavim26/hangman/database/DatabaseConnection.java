package io.github.tavim26.hangman.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection
{
    private static final String URL = "jdbc:mysql://localhost:3306/hangman";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private DatabaseConnection()
    {
    }

    /** Opens a new connection. The caller is responsible for closing it. */
    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}