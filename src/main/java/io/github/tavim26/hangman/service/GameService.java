package io.github.tavim26.hangman.service;

import io.github.tavim26.hangman.database.GameDao;
import io.github.tavim26.hangman.model.Category;
import io.github.tavim26.hangman.model.Difficulty;
import io.github.tavim26.hangman.model.GameRecord;
import io.github.tavim26.hangman.model.HangmanGame;
import io.github.tavim26.hangman.model.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameService
{
    private final GameDao gameDao = new GameDao();

    public HangmanGame startGame(Difficulty difficulty, Category category)
    {
        List<String> words = category.getWords();
        String word = words.get(ThreadLocalRandom.current().nextInt(words.size()));

        return new HangmanGame(word, difficulty, category);
    }

    public void saveResult(Player player, HangmanGame game) throws SQLException
    {
        String status = game.isWon() ? "Won" : "Lost";

        gameDao.addGame(new GameRecord(
                player.getPlayerID(),
                game.getDifficulty().getLabel(),
                game.getCategory().getLabel(),
                game.getWord(),
                game.getRemainingTries(),
                status
        ));
    }

    public List<GameRecord> getHistory(Player player) throws SQLException
    {
        return gameDao.findByPlayer(player.getPlayerID());
    }
}