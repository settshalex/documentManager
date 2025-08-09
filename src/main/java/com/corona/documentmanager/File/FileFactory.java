package com.corona.documentmanager.File;
import java.util.List;

public class FileFactory extends AbstractFactory{
    private final List<File> strategies;

    public FileFactory(List<File> strategies) {
        this.strategies = strategies;
    }

    @Override
    public File getFileManager(String mime) {
        if (mime == null) {
            throw new IllegalArgumentException("MIME nullo non supportato");
        }
        return strategies.stream()
                .filter(s -> s instanceof SupportsMime)
                .filter(s -> ((SupportsMime) s).supports(mime))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("MIME non supportato: " + mime));
    }
}

