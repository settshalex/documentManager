package com.corona.documentmanager.documentType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentTypeRepositoryTest {

    @Mock
    private DocumentTypeRepository documentTypeRepository;

    private DocumentType documentType;

    @BeforeEach
    void setUp() {
        documentType = new DocumentType();
        documentType.setId(1L);
        documentType.setType("TEST_TYPE");
    }

    @Test
    void findById() {
        when(documentTypeRepository.findById(1L)).thenReturn(Optional.of(documentType));

        Optional<DocumentType> result = documentTypeRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(documentType.getId(), result.get().getId());
        verify(documentTypeRepository).findById(1L);
    }

    @Test
    void findByType() {
        when(documentTypeRepository.findByType("TEST_TYPE")).thenReturn(Optional.of(documentType));

        Optional<DocumentType> result = documentTypeRepository.findByType("TEST_TYPE");

        assertTrue(result.isPresent());
        assertEquals(documentType.getType(), result.get().getType());
        verify(documentTypeRepository).findByType("TEST_TYPE");
    }
}