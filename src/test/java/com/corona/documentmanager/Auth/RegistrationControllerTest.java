package com.corona.documentmanager.Auth;

import com.corona.documentmanager.exception.UserAlreadyExistsException;
import com.corona.documentmanager.user.User;
import com.corona.documentmanager.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationController registrationController;

    @Test
    void showRegistrationForm() throws Exception {
        mockMvc.perform(get("/user/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void registerUser_validationErrors() throws Exception {
        mockMvc.perform(post("/user/registration")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("username", "non-una-email")
                        .param("password", "short")
                        .param("confirmPassword", "diversa"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }


    @Test
    void registerUser() throws Exception {
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";

        // Configura il comportamento del passwordEncoder mock
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        mockMvc.perform(post("/user/registration")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf())
                        .param("username", "test@example.com")
                        .param("password", rawPassword)
                        .param("confirmPassword", rawPassword))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));


        // Verifica che userService.createUser sia stato chiamato con i parametri corretti
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).createUser(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertEquals("test@example.com", capturedUser.getUsername());
        assertEquals(encodedPassword, capturedUser.getPassword());
    }

    @Test
    void registerUserPasswordMismatch() throws Exception {
        mockMvc.perform(post("/user/registration").with(csrf())
                        .param("username", "testuser")
                        .param("password", "password123")
                        .param("confirmPassword", "differentPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("user"));
    }

}
