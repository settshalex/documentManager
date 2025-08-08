package com.corona.documentmanager.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerModelTest {

    private TokenizerModel tokenizerModel;
    @BeforeEach
    void setUp() {
        tokenizerModel = new TokenizerModel();

    }
    @Test
    void tokenize() {
        String[] a = tokenizerModel.tokenize("This is a test");
        assertArrayEquals(new String[]{"this", "test"}, a);
    }

    @Test
    void extractKeyPhrases() {
        List<String> a = tokenizerModel.extractKeyPhrases("L'Italia è una Repubblica democratica, fondata sul lavoro. La sovranità appartiene al popolo, che la esercita nelle forme e nei limiti della Costituzione", 3);
        List<String> parole = Arrays.asList(
                "popolo che esercita",
                "della costituzione",
                "sul lavoro sovranit"
        );
        assertArrayEquals(a.toArray(), parole.toArray());
    }

}