package com.nnk.springboot.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user")
@ActiveProfiles("test")
class LoginControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /app/login doit retourner la vue 'login'")
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/app/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"));
    }

    @Test
    @DisplayName("GET /app/secure/article-details doit retourner la vue 'user/list' avec les utilisateurs")
    void testGetAllUserArticles() throws Exception {
        mockMvc.perform(get("/app/secure/article-details"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/list"))
            .andExpect(model().attributeExists("users"));
    }

    @Test
    @DisplayName("GET /app/error doit retourner la vue '403' avec un message d'erreur")
    void testErrorPage() throws Exception {
        mockMvc.perform(get("/app/error"))
            .andExpect(status().isOk())
            .andExpect(view().name("403"))
            .andExpect(model().attributeExists("errorMsg"))
            .andExpect(model().attribute("errorMsg", containsString("You are not authorized for the requested data.")));
    }
}
