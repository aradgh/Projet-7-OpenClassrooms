package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user")
@ActiveProfiles("test")
class RatingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    @DisplayName("GET /rating/list - should return rating list view with ratings")
    void testHome() throws Exception {
        when(ratingService.findAll()).thenReturn(List.of(new Rating()));

        mockMvc.perform(get("/rating/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("rating/list"))
            .andExpect(model().attributeExists("ratings"));
    }

    @Test
    @DisplayName("GET /rating/add - should show add rating form")
    void testAddRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("rating/add"))
            .andExpect(model().attributeExists("rating"));
    }

    @Test
    @DisplayName("POST /rating/validate - should validate and redirect when valid")
    void testValidateValid() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .param("moodysRating", "Aaa")
                .param("sandPRating", "AAA")
                .param("fitchRating", "AAA")
                .param("orderNumber", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).save(any(Rating.class));
    }

    @Test
    @DisplayName("POST /rating/validate - should return to form when invalid")
    void testValidateInvalid() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .param("moodysRating", "") // Invalid: blank
                .param("sandPRating", "")
                .param("fitchRating", "")
                .param("orderNumber", "-1")) // Invalid: negative
            .andExpect(status().isOk())
            .andExpect(view().name("rating/add"));
    }

    @Test
    @DisplayName("GET /rating/update/{id} - should show update form")
    void testShowUpdateForm() throws Exception {
        Rating rating = new Rating("Aaa", "AAA", "AAA", 1);
        rating.setId(1);
        when(ratingService.findById(1)).thenReturn(rating);

        mockMvc.perform(get("/rating/update/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("rating/update"))
            .andExpect(model().attributeExists("rating"));
    }

    @Test
    @DisplayName("POST /rating/update/{id} - should update and redirect when valid")
    void testUpdateValid() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                .with(csrf())
                .param("moodysRating", "Baa")
                .param("sandPRating", "BBB")
                .param("fitchRating", "BBB")
                .param("orderNumber", "2"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).save(any(Rating.class));
    }

    @Test
    @DisplayName("POST /rating/update/{id} - should return to form when invalid")
    void testUpdateInvalid() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                .with(csrf())
                .param("moodysRating", "")
                .param("sandPRating", "")
                .param("fitchRating", "")
                .param("orderNumber", "-2"))
            .andExpect(status().isOk())
            .andExpect(view().name("rating/update"));
    }

    @Test
    @DisplayName("GET /rating/delete/{id} - should delete rating and redirect")
    void testDelete() throws Exception {
        mockMvc.perform(get("/rating/delete/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService, times(1)).delete(1);
    }
}
