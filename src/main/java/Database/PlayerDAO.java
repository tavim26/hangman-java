package Database;

import Model.Player;
import Connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO
{


    public void addPlayer(Player player) throws SQLException
    {
        String query = "INSERT INTO Player (Username, Email, Password) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, player.getUsername());
            stmt.setString(2, player.getEmail());
            stmt.setString(3, player.getPassword());
            stmt.executeUpdate();
        }
    }


    public List<Player> getAllPlayers() throws SQLException
    {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM Player";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next())
            {
                Player player = new Player(
                        rs.getInt("PlayerID"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("Password")
                );
                players.add(player);
            }
        }
        return players;
    }

    public Player getPlayerByUsernameAndPassword(String username, String password) throws SQLException {
        String query = "SELECT * FROM Player WHERE Username = ? AND Password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Player(
                            rs.getInt("PlayerID"),
                            rs.getString("Username"),
                            rs.getString("Email"),
                            rs.getString("Password")
                    );
                }
            }
        }
        return null;
    }





    public Player getPlayerById(int playerID) throws SQLException
    {
        String query = "SELECT * FROM Player WHERE PlayerID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Player(
                        rs.getInt("PlayerID"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("Password")
                );
            }
        }
        return null;
    }


}
