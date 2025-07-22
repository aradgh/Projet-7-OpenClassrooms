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
@ActiveProfiles("test")
@WithMockUser(username = "user")
class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / doit retourner la vue 'home'")
    void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("home"))
        .andExpect(content().string(containsString("<a href=\"/app/login\">Login</a> or create one <a href=\"/user/list\">User management</a>")));
    }

    @Test
    @DisplayName("GET /admin/home doit rediriger vers /bidList/list")
    void testAdminHomeRedirection() throws Exception {
        mockMvc.perform(get("/admin/home"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/bidList/list"));
    }
}
