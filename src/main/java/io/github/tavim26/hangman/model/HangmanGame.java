package io.github.tavim26.hangman.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The state and rules of a single round of Hangman.
 * It has no knowledge of the user interface or the database.
 */
public class HangmanGame
{
    public enum GuessResult { CORRECT, WRONG, ALREADY_GUESSED }

    private final String word;
    private final Difficulty difficulty;
    private final Category category;
    private final Set<Character> guessedLetters = new LinkedHashSet<>();
    private int remainingTries;

    public HangmanGame(String word, Difficulty difficulty, Category category)
    {
        this.word = word.toLowerCase();
        this.difficulty = difficulty;
        this.category = category;
        this.remainingTries = difficulty.getMaxWrongGuesses();
    }

    public GuessResult guess(char letter)
    {
        char normalized = Character.toLowerCase(letter);

        if (!guessedLetters.add(normalized))
        {
            return GuessResult.ALREADY_GUESSED;
        }

        if (word.indexOf(normalized) >= 0)
        {
            return GuessResult.CORRECT;
        }

        remainingTries--;
        return GuessResult.WRONG;
    }

    /** The word with unguessed letters shown as '_', e.g. "t _ y _ t a". */
    public String getMaskedWord()
    {
        StringBuilder masked = new StringBuilder();

        for (char letter : word.toCharArray())
        {
            if (!masked.isEmpty())
            {
                masked.append(' ');
            }
            masked.append(guessedLetters.contains(letter) ? letter : '_');
        }
        return masked.toString();
    }

    public boolean isWon()
    {
        for (char letter : word.toCharArray())
        {
            if (!guessedLetters.contains(letter))
            {
                return false;
            }
        }
        return true;
    }

    public boolean isLost()
    {
        return remainingTries <= 0;
    }

    public boolean isOver()
    {
        return isWon() || isLost();
    }

    public String getWord()
    {
        return word;
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    public Category getCategory()
    {
        return category;
    }

    public int getRemainingTries()
    {
        return remainingTries;
    }

    public Set<Character> getGuessedLetters()
    {
        return Collections.unmodifiableSet(guessedLetters);
    }
}