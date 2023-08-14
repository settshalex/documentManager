package com.corona.documentmanager.File;

public class FileFactory extends AbstractFactory{
    @Override
    public File getFile(String type) {
        if (type.startsWith("text/")){
            return new TextDocument();
        }
        if (type.startsWith("application/")){
            return new ApplicationDocument();
        }
        if (type.startsWith("image/")){
            return new ImageDocument();
        }
        if (type.startsWith("audio/")){
            return new AudioDocument();
        }
        throw new RuntimeException("not supported type");
    }
}
