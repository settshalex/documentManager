package com.corona.documentmanager.config;

import com.corona.documentmanager.File.FileManager;
import com.corona.documentmanager.File.FileFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FileFactoryConfig {

    @Bean
    public FileFactory fileFactory(List<FileManager> strategies) {
        return new FileFactory(strategies);
    }
}