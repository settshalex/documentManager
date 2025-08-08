package com.corona.documentmanager.DocumentShare;

import com.corona.documentmanager.document.Document;
import com.corona.documentmanager.document.DocumentController;
import com.corona.documentmanager.document.DocumentRepository;
import com.corona.documentmanager.document.DocumentService;
import com.corona.documentmanager.documentType.DocumentTypeRepository;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentShareController.class)
class DocumentShareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentRepository documentRepository;

    @MockBean
    private DocumentShareService documentShareService;

    @MockBean
    private DocumentShareController documentShareController;



    private LoggedUser principal() {
        User u = User.builder()
                .id(1L)
                .username("testuser")
                .password("pwd")
                .role("USER")
                .build();
        return new LoggedUser(u);
    }

    @Test
    void shareDocument() throws Exception {
        LoggedUser loggedUser = principal();
        Document document = new Document();
        document.setId(1L);
        document.setTitle("Test Document");
        document.setDescription("Test Description");
        document.setCreatedBy(loggedUser.user);
        Long documentId = 1L;
        when(documentRepository.findById(documentId))
                .thenReturn(Optional.of(document));

        mockMvc.perform(post("/api/documents/1/share")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf()).with(user(principal()))
                        .param("username", "test@example.com")
                        .param("permission", "READ")
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shareDocumentNoPermission() throws Exception {
        User u = User.builder()
                .id(2L)
                .username("testuser")
                .password("pwd")
                .role("USER")
                .build();
        LoggedUser loggedUser = principal();
        Document document = new Document();
        document.setId(1L);
        document.setTitle("Test Document");
        document.setDescription("Test Description");
        document.setCreatedBy(u);
        Long documentId = 1L;


        mockMvc.perform(post("/api/documents/1/share")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf()).with(user(loggedUser))
                        .param("username", "test@example.com")
                        .param("permission", "READ")
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void removeShare() throws Exception {
        mockMvc.perform(delete("/api/shares/1")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf()).with(user(principal()))
                )
                .andExpect(status().isOk());
    }
}