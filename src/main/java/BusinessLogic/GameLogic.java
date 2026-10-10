package BusinessLogic;

import Database.GameDAO;
import Model.Game;
import Model.Player;

import java.sql.SQLException;
import java.util.*;

public class GameLogic
{

    private final GameDAO gameDAO;
    private final Map<String, List<String>> categories;
    private String currentWord;
    private String category;
    private Player player;
    private String difficulty;
    private int remainingTries;
    private StringBuilder guessedLetters;



    public GameLogic()
    {
        gameDAO = new GameDAO();
        categories = new HashMap<>();

        categories.put("cars", Arrays.asList("toyota", "ford", "bmw", "honda", "audi"));
        categories.put("plants", Arrays.asList("rose", "sunflower", "tulip", "daisy", "orchid"));
        categories.put("animals", Arrays.asList("elephant", "tiger", "giraffe", "lion", "zebra"));
        categories.put("countries", Arrays.asList("romania", "germany", "france", "italy", "canada"));
        categories.put("jobs", Arrays.asList("doctor", "engineer", "teacher", "nurse", "artist"));
        categories.put("videogames", Arrays.asList("zelda", "mario", "sonic", "fortnite", "minecraft"));
    }

    public void startGame(String category, String difficulty, Player currentPlayer)
    {
        this.player = currentPlayer;
        this.category = category;
        this.difficulty = difficulty;
        this.currentWord = selectRandomWord(category);
        this.remainingTries = getTriesBasedOnDifficulty(difficulty);
        this.guessedLetters = new StringBuilder();
    }




    public String getCurrentWord()
    {
        return currentWord;
    }

    public String getCategory()
    {
        return category;
    }

    public int getRemainingTries()
    {
        return remainingTries;
    }

    public StringBuilder getGuessedLetters()
    {
        return guessedLetters;
    }

    private String selectRandomWord(String category)
    {
        List<String> words = categories.get(category);

        if (words != null && !words.isEmpty())
        {
            Random random = new Random();
            return words.get(random.nextInt(words.size()));
        }
        return null;
    }

    public boolean guessLetter(String letter)
    {
        if (guessedLetters.toString().contains(letter))
        {
            return false;
        }
        guessedLetters.append(letter);
        guessedLetters.append(",");

        if (!currentWord.contains(letter))
        {
            remainingTries--;
        }
        return true;
    }

    public boolean isGameOver() {
        boolean isGameOver = remainingTries <= 0 || getWordTemplate().replace(" ", "").equals(currentWord);

        if (isGameOver)
        {
            String gameStatus = remainingTries <= 0 ? "Lost" : "Won";

            Game game = new Game(player.getPlayerID(), difficulty, currentWord, remainingTries, gameStatus);

            try {
                gameDAO.addGame(game);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isGameOver;
    }



    public String getWordTemplate()
    {
        StringBuilder template = new StringBuilder();

        for (char letter : currentWord.toCharArray())
        {
            if (guessedLetters.toString().contains(String.valueOf(letter)))
            {
                template.append(letter).append(" ");

            }
            else
            {
                template.append("_ ");
            }
        }
        return template.toString();
    }

    private int getTriesBasedOnDifficulty(String difficulty)
    {
        switch (difficulty)
        {
            case "easy":
                return 10;
            case "medium":
                return 7;
            case "hard":
                return 5;
            default:
                return 6;
        }
    }
}
