package com.nnk.springboot.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "user")
class GlobalUserInfoIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Le nom d'utilisateur est injecté dans le modèle pour une route protégée")
    void testUsernameIsInjectedInModel() throws Exception {
        mockMvc.perform(get("/bidList/list")) // remplace cette URL par une route existante dans ton appli
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("username"))
            .andExpect(model().attribute("username", is("user")));
    }
}

