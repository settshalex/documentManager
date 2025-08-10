package com.corona.documentmanager.File;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import java.io.*;
import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AudioDocumentTest {

    AudioDocument audioDocument;

    @BeforeEach
    void setUp() {

        audioDocument = new AudioDocument();
    }
    @Test
    void createNewDocument() throws IOException {
        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);

        byte[] audioData = new byte[44100 * 2]; // 1 sec * 44100 samples * 2 bytes (16-bit)
        ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, audioData.length / format.getFrameSize());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, baos);
        byte[] wavBytes = baos.toByteArray();

        MockMultipartFile audioFile = new MockMultipartFile(
                "file",
                "audio.wav",
                "audio/wav",
                wavBytes
        );
        User u = User.builder()
                .id(1L)
                .username("testuser")
                .password("pwd")
                .role("USER")
                .build();
        LoggedUser principal= new LoggedUser(u);
        DocumentType docType = new DocumentType();
        AudioDocument audioDocument = new AudioDocument();
        Document audioDocumentNew = audioDocument.createNewDocument(audioFile, principal, "test", "descrizione", "audio/wav", Optional.of(docType));
        assertNotNull(audioDocumentNew);
        assertEquals(Document.class, audioDocumentNew.getClass());
       System.out.println(audioDocumentNew.getDescription());
    }

    @Test
    void isDocument() {
        assertFalse(audioDocument.isDocument());
    }

    @Test
    void language() {
        assertNull(audioDocument.language());
    }

}