package io.github.tavim26.hangman.model;

import java.util.List;

public enum Category
{
    CARS("Cars", List.of("toyota", "ford", "bmw", "honda", "audi")),
    PLANTS("Plants", List.of("rose", "sunflower", "tulip", "daisy", "orchid")),
    ANIMALS("Animals", List.of("elephant", "tiger", "giraffe", "lion", "zebra")),
    COUNTRIES("Countries", List.of("romania", "germany", "france", "italy", "canada")),
    JOBS("Jobs", List.of("doctor", "engineer", "teacher", "nurse", "artist")),
    VIDEO_GAMES("Video Games", List.of("zelda", "mario", "sonic", "fortnite", "minecraft"));

    private final String label;
    private final List<String> words;

    Category(String label, List<String> words)
    {
        this.label = label;
        this.words = words;
    }

    public String getLabel()
    {
        return label;
    }

    public List<String> getWords()
    {
        return words;
    }
}