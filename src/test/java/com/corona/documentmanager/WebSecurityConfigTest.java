package com.corona.documentmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPublicEndpoints() throws Exception {
        // Test degli endpoint pubblici
//        mockMvc.perform(get("/css/style.css"))
//                .andExpect(status().isOk());

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/registration"))
                .andExpect(status().isOk());
    }

    @Test
    public void testProtectedEndpoints() throws Exception {
        // Test degli endpoint protetti
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection());
    }
}