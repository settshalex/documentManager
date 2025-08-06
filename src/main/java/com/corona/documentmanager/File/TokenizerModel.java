package com.corona.documentmanager.File;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TokenizerModel {
    private final Pattern wordPattern;
    private final Set<String> vocabulary;

    public TokenizerModel() throws IOException {
        this.wordPattern = Pattern.compile("\\b\\w+\\b");
        this.vocabulary = loadVocabulary();
    }

    public TokenizerModel(InputStream modelIn) throws IOException {
        this();
    }

    private Set<String> loadVocabulary() throws IOException {
        Set<String> vocab = new HashSet<>();
        try (InputStream is = getClass().getResourceAsStream("/vocabulary/italian_words.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                vocab.add(line.trim().toLowerCase());
            }
        }
        return vocab;
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
        return token.length() > 1 && vocabulary.contains(token.toLowerCase());
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

    public double calculateSimilarity(String text1, String text2) {
        Set<String> tokens1 = new HashSet<>(Arrays.asList(tokenize(text1)));
        Set<String> tokens2 = new HashSet<>(Arrays.asList(tokenize(text2)));

        Set<String> union = new HashSet<>(tokens1);
        union.addAll(tokens2);

        Set<String> intersection = new HashSet<>(tokens1);
        intersection.retainAll(tokens2);

        return union.isEmpty() ? 0 : (double) intersection.size() / union.size();
    }

    public Map<String, Integer> getWordFrequencies(String text) {
        String[] tokens = tokenize(text);
        Map<String, Integer> frequencies = new HashMap<>();

        for (String token : tokens) {
            frequencies.merge(token, 1, Integer::sum);
        }

        return frequencies;
    }

    public List<String> getMostFrequentWords(String text, int limit) {
        Map<String, Integer> frequencies = getWordFrequencies(text);

        return frequencies.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}