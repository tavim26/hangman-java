package BusinessLogic;

import Database.PlayerDAO;
import Model.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerLogic {

    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    PlayerDAO playerDAO;

    public PlayerLogic()
    {
        playerDAO = new PlayerDAO();
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean usernameValidator(String username)
    {
        if(username!=null)
        {
            return true;
        }
        return false;
    }

    public boolean passwordValidator(String password)
    {
        if(password!=null)
        {
            return true;
        }

        return false;
    }

    public int alreadyExists(Player player) throws SQLException
    {

        List<Player> players = playerDAO.getAllPlayers();

        for(Player p : players)
        {

            if(p.getUsername().equals(player.getUsername()))
            {
                return 1;
            }

            if(p.getEmail().equals(player.getEmail()))
            {
                return 2;
            }

        }

        return 0;
    }


    public boolean playerValidator(Player player)
    {
        if(emailValidator(player.getEmail()) && usernameValidator(player.getUsername()) && passwordValidator(player.getPassword()))
        {
            return true;
        }
        return false;
    }


    public Player gePlayerLogin(String username,String password) throws SQLException
    {
       Player confirm = playerDAO.getPlayerByUsernameAndPassword(username,password);

       if(confirm!=null)
       {
           return confirm;
       }
       return null;
    }


    public void addPlayer(Player newPlayer) throws SQLException
    {
        playerDAO.addPlayer(newPlayer);
    }

}
