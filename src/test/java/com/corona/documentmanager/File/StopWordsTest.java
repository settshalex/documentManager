package com.corona.documentmanager.File;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StopWordsTest {

    @Test
    void isStopWord() {
        StopWords stopWords = new StopWords();
        assertTrue(stopWords.isStopWord("allo"));
        assertTrue(stopWords.isStopWord("questo"));
        assertTrue(stopWords.isStopWord("li"));
    }
}