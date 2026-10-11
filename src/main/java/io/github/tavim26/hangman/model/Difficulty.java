package io.github.tavim26.hangman.model;

public enum Difficulty
{
    EASY("Easy", 9),
    MEDIUM("Medium", 6),
    HARD("Hard", 3);

    private final String label;
    private final int maxWrongGuesses;

    Difficulty(String label, int maxWrongGuesses)
    {
        this.label = label;
        this.maxWrongGuesses = maxWrongGuesses;
    }

    public String getLabel()
    {
        return label;
    }

    public int getMaxWrongGuesses()
    {
        return maxWrongGuesses;
    }
}