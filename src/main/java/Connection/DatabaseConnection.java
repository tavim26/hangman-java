package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hangman";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private static Connection connection;

    public static Connection getConnection() throws SQLException
    {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

            } catch (ClassNotFoundException e)
            {
                throw new SQLException("JDBC driver not found", e);
            }
        }
        return connection;
    }

    public static void closeConnection() throws SQLException
    {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}