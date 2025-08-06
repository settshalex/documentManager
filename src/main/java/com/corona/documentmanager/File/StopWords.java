package com.corona.documentmanager.File;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class StopWords {
    private Set<String> stopWords;

    public StopWords() {
        stopWords = new HashSet<>(Arrays.asList(
                "alla", "allo", "agli", "delle", "della", "dello", "degli",
                "che", "chi", "gli", "nei", "delle", "della", "dello",
                "con", "era", "tra", "per", "dal", "del", "non",
                "una", "uno", "questo", "quello", "come", "dove", "quale",
                "sono", "siamo", "sei", "essere", "fare", "fare", "fatto",
                "gli", "le", "lo", "la", "li", "quelli", "quelle"
        ));
    }

    public boolean isStopWord(String word) {
        return stopWords.contains(word.toLowerCase());
    }
}
