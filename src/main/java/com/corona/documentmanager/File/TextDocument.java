package com.corona.documentmanager.File;

import ch.qos.logback.core.subst.Tokenizer;
import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TextDocument extends CommonFile implements File {
    private static final int MAX_KEYWORDS = 10;
    private static final int MIN_WORD_LENGTH = 4;

    private TokenizerModel tokenizer;
    private StopWords stopWords;

    public TextDocument() {
        tokenizer = new TokenizerModel();
        stopWords = new StopWords();
    }

    public Document createNewDocument(MultipartFile file, LoggedUser customUser,
                                      String title, String description, String mime_type,
                                      Optional<DocumentType> docType) throws IOException {
        Document document = super.prepareNewDocument(file, customUser, title, description, mime_type, docType);

        // Estrai il testo dal file
        String text = new String(file.getBytes(), StandardCharsets.UTF_8);
        document.setText(text);

        // Estrai e aggiungi le parole chiave come tag
        Set<String> keywords = extractKeywords(text);
        keywords.forEach(document::addTag);
        System.out.println("keywords " + keywords);

        return document;
    }

    private Set<String> extractKeywords(String text) {
        try {
            // Ottieni le parole tokenizzate
            String[] tokens = tokenizer.tokenize(text);

            // Conta la frequenza delle parole
            Map<String, Integer> wordFrequency = new HashMap<>();
            for (String token : tokens) {
                String word = token.toLowerCase();
                if (isValidKeyword(word)) {
                    wordFrequency.merge(word, 1, Integer::sum);
                }
            }

            // Ordina per frequenza e prendi le prime MAX_KEYWORDS parole
            return wordFrequency.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(MAX_KEYWORDS)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());

        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    private boolean isValidKeyword(String word) {
        return word.length() >= MIN_WORD_LENGTH &&
                word.matches("\\p{L}+") &&
                !stopWords.isStopWord(word);
    }

    @Override
    public boolean isDocument() {
        return true;
    }

    @Override
    public String language() {
        return "it";
    }
}
