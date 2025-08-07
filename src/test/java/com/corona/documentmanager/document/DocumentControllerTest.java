package com.corona.documentmanager.document;

import com.corona.documentmanager.DocumentTags.DocumentTag;
import com.corona.documentmanager.DocumentTags.DocumentTagRepository;
import com.corona.documentmanager.documentType.DocumentType;
import com.corona.documentmanager.documentType.DocumentTypeRepository;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DocumentController.class)
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @MockBean
    private DocumentTypeRepository documentTypeRepository;

    @MockBean
    private DocumentRepository documentRepository;

    private LoggedUser principal() {
        User u = User.builder()
                .id(1L)
                .username("testuser")
                .password("pwd")
                .role("USER")
                .build();
        return new LoggedUser(u);
    }

    private User owner() {
        return User.builder()
                .id(1L)
                .username("owner")
                .password("pwd")
                .role("USER")
                .build();
    }



    @Test
    void downloadDocument() throws Exception {
        DocumentType testDocumentType = new DocumentType();
        testDocumentType.setType("TEST/TYPE");
        Document document = new Document();
        document.setId(1L);
        document.setDocumentType(testDocumentType);
        document.setTitle("Test Document");
        document.setMimeType("TEST/TYPE");
        document.setData("Test Data".getBytes());
        document.setDescription("Test Description");
        document.addTag("testTag");
        document.setCreatedBy(owner());
        when(documentService.findDocumentById(1L)).thenReturn(document);

        mockMvc.perform(get("/documents/download/1")
                .with(user(principal())))
                .andExpect(status().isOk());
    }

    @Test
    void newDocument() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "file",                // NOME PARTE: deve coincidere con @RequestPart MultipartFile file
                "test.txt",
                "text/plain",
                "contenuto del file".getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile title = new MockMultipartFile(
                "title",               // NOME PARTE: deve coincidere con @RequestPart String title
                "",                    // filename vuoto per parte testuale
                "text/plain",
                "Titolo di prova".getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile description = new MockMultipartFile(
                "description",         // NOME PARTE: deve coincidere con @RequestPart String description
                "",
                "text/plain",
                "Descrizione di prova".getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/api/document/")
                        .file(file)
                        .file(title)
                        .file(description).with(user(principal()))
                        .with(csrf()))             // aggiungi il CSRF se la sicurezza è attiva
                .andExpect(status().isOk());

    }

    @Test
    void uploadMultipleFiles() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "files", "test1.txt", "text/plain", "content1".getBytes()
        );
        MockMultipartFile file2 = new MockMultipartFile(
                "files", "test2.txt", "text/plain", "content2".getBytes()
        );

        mockMvc.perform(multipart("/api/upload/multiple")
                        .file(file1)
                        .file(file2).with(user(principal())).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void handleException() throws Exception {
        when(documentService.findDocumentById(1L))
                .thenThrow(new RuntimeException("Document not found"));

        mockMvc.perform(get("/api/documents/999/download").with(user(principal())))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void addTag() throws Exception {

        Document document = new Document();
        document.setId(1L);
        document.setTitle("Test Document");
        document.setDescription("Test Description");
        document.addTag("testTag");
        document.setCreatedBy(owner());

        when(documentService.findDocumentById(1L)).thenReturn(document);

        mockMvc.perform(post("/api/document/1/tags").with(user(principal())).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tag\": \"newTag\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void removeTag() throws Exception {
        Document document = new Document();
        document.setId(1L);
        document.setTitle("Test Document");
        document.setDescription("Test Description");
        document.addTag("existingTag");
        document.setCreatedBy(owner());

        when(documentService.findDocumentById(1L)).thenReturn(document);
        mockMvc.perform(delete("/api/document/1/tags/existingTag")
                        .content("").with(user(principal())).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getTags() throws Exception {
        Document document = new Document();
        document.setId(1L);
        document.setTitle("Test Document");
        document.setDescription("Test Description");
        document.addTag("testTag");
        document.setCreatedBy(owner());

        when(documentService.findDocumentById(1L)).thenReturn(document);

        mockMvc.perform(get("/api/document/1/tags").with(user(principal())))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void updateDescription() throws Exception {
        Document document = new Document();
        document.setId(1L);
        document.setTitle("Test Document");
        document.setDescription("Test Description");
        document.addTag("testTag");
        document.setCreatedBy(owner());

        when(documentService.findDocumentById(1L)).thenReturn(document);
        mockMvc.perform(put("/api/document/1/description").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"Updated description\"").with(user(principal())))
                .andDo(print())
                .andExpect(status().isOk());
    }

}