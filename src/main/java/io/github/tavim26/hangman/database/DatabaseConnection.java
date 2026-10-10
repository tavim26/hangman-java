package io.github.tavim26.hangman.database;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection
{
    private static final Path CONFIG_FILE = Path.of("database", "connection.properties");

    private static Properties config;

    private DatabaseConnection()
    {
    }

    /** Opens a new connection. The caller is responsible for closing it. */
    public static Connection getConnection() throws SQLException
    {
        Properties settings = loadConfig();

        return DriverManager.getConnection(
                settings.getProperty("db.url"),
                settings.getProperty("db.user"),
                settings.getProperty("db.password")
        );
    }

    private static synchronized Properties loadConfig() throws SQLException
    {
        if (config == null)
        {
            Properties settings = new Properties();

            try (Reader reader = Files.newBufferedReader(CONFIG_FILE))
            {
                settings.load(reader);
            }
            catch (IOException e)
            {
                throw new SQLException("Could not read " + CONFIG_FILE.toAbsolutePath()
                        + ". Copy database/connection.properties.example to database/connection.properties"
                        + " and fill in your database credentials.", e);
            }
            config = settings;
        }
        return config;
    }
}