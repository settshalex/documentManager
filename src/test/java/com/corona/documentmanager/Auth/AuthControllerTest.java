package com.corona.documentmanager.Auth;

import com.corona.documentmanager.DocumentShare.DocumentShareController;
import com.corona.documentmanager.document.DocumentController;
import com.corona.documentmanager.document.DocumentService;
import com.corona.documentmanager.user.LoggedUser;
import com.corona.documentmanager.user.User;
import com.corona.documentmanager.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import jakarta.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest()
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @MockBean
    private UserService userService;

    @MockBean
    private RegistrationController registrationController;

    @MockBean
    private DocumentShareController documentShareController;

    @MockBean
    private DocumentController documentController;

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
    void showLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void showUploadForm() throws Exception {
        mockMvc.perform(get("/upload"))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"));
    }

    @Test
    void logout() throws Exception {
        HttpSession session = mock(HttpSession.class);
        mockMvc.perform(get("/logout").sessionAttr("session", session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }

}