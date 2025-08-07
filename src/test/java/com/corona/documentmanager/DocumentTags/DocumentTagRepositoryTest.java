package com.corona.documentmanager.DocumentTags;

import com.corona.documentmanager.document.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentTagRepositoryTest {

    @Mock
    private DocumentTagRepository documentTagRepository;

    private DocumentTag documentTag1;
    private DocumentTag documentTag2;

    @BeforeEach
    void setUp() {
        Document document = new Document();
        document.setId(1L);
        document.setTitle("Test Document");

        documentTag1 = new DocumentTag();
        documentTag1.setId(1L);
        documentTag1.setTag("test1");
        documentTag1.setDocument(document);

        documentTag2 = new DocumentTag();
        documentTag2.setId(2L);
        documentTag2.setTag("test2");
        documentTag2.setDocument(document);
    }

    @Test
    void findByTag() {
        List<DocumentTag> expectedTags = Arrays.asList(documentTag1);
        when(documentTagRepository.findByTag("test1")).thenReturn(expectedTags);

        List<DocumentTag> result = documentTagRepository.findByTag("test1");

        assertEquals(expectedTags.size(), result.size());
        verify(documentTagRepository).findByTag("test1");
    }

    @Test
    void findByDocumentId() {
        List<DocumentTag> expectedTags = Arrays.asList(documentTag1, documentTag2);
        when(documentTagRepository.findByDocumentId(1L)).thenReturn(expectedTags);

        List<DocumentTag> result = documentTagRepository.findByDocumentId(1L);

        assertEquals(expectedTags.size(), result.size());
        verify(documentTagRepository).findByDocumentId(1L);
    }
}