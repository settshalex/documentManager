package com.corona.documentmanager.File;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TokenizerModel {
    private final Pattern wordPattern;

    public TokenizerModel() {
        this.wordPattern = Pattern.compile("\\b[\\p{L}]+\\b");
    }

    public String[] tokenize(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new String[0];
        }

        List<String> tokens = new ArrayList<>();
        java.util.regex.Matcher matcher = wordPattern.matcher(text.toLowerCase());

        while (matcher.find()) {
            String token = matcher.group();
            if (isValidToken(token)) {
                tokens.add(token);
            }
        }

        return tokens.toArray(new String[0]);
    }

    private boolean isValidToken(String token) {
        return token.length() > 2 && token.matches("\\p{L}+");
    }


    // Metodi di utilità per l'analisi del testo
    public List<String> extractKeyPhrases(String text, int maxPhrases) {
        String[] tokens = tokenize(text);
        Map<String, Integer> phraseFrequency = new HashMap<>();

        // Considera frasi di 1-3 parole
        for (int i = 0; i < tokens.length; i++) {
            // Parole singole
            updatePhraseFrequency(tokens[i], phraseFrequency);

            // Frasi di due parole
            if (i < tokens.length - 1) {
                String twoWordPhrase = tokens[i] + " " + tokens[i + 1];
                updatePhraseFrequency(twoWordPhrase, phraseFrequency);
            }

            // Frasi di tre parole
            if (i < tokens.length - 2) {
                String threeWordPhrase = tokens[i] + " " + tokens[i + 1] + " " + tokens[i + 2];
                updatePhraseFrequency(threeWordPhrase, phraseFrequency);
            }
        }

        return phraseFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(maxPhrases)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private void updatePhraseFrequency(String phrase, Map<String, Integer> frequencyMap) {
        frequencyMap.merge(phrase, 1, Integer::sum);
    }


}