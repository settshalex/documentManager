package com.corona.documentmanager.File;

import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class FileParserTest {

    @Test
    void parse() throws TikaException, IOException, SAXException {
        String input = "Hello\nWorld!";

        // Simula un InputStream da una stringa
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        FileParser fileParser = new FileParser();
        String a = fileParser.parse(inputStream);
        inputStream.close();
        assertEquals(a, "text/plain");

    }
}