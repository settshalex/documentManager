package com.corona.documentmanager.config;

import com.corona.documentmanager.File.FileFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileFactoryConfig {

    @Bean
    public FileFactory fileFactory() {
        return new FileFactory();
    }
}
