package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BidListControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @BeforeEach
    void setUp() {
        bidListRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("When calling GET /bidList/list, should display Bid List page containing the new bid 'Account Test'")
    void shouldDisplayBidListPage() throws Exception {
        BidList bid = new BidList("Account Test", "Type Test", new BigDecimal("10.0"));
        bidListRepository.save(bid);

        mockMvc.perform(get("/bidList/list"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Account Test")));
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("When calling GET /bidList/add, should get Bid Add Form containing 'Add New Bid'")
    void shouldShowAddBidForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("bidList/add"))
            .andExpect(content().string(containsString("Add New Bid")));
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("When calling POST /bidList/validate, should validate and add bid")
    void shouldValidateAndAddBid() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "New Account")
                .param("type", "New Type")
                .param("bidQuantity", "42"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldRejectInvalidBid() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "") // champ vide donc invalide
                .param("type", "")
                .param("bidQuantity", ""))
            .andExpect(status().isOk())
            .andExpect(model().hasErrors())
            .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldShowUpdateForm() throws Exception {
        BidList bid = new BidList("Account", "Type", new BigDecimal("100.0"));
        bid = bidListRepository.save(bid);

        mockMvc.perform(get("/bidList/update/" + bid.getBidListId()))
            .andExpect(status().isOk())
            .andExpect(view().name("bidList/update"))
            .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldUpdateValidBid() throws Exception {
        BidList bid = new BidList("Account", "Type", new BigDecimal("100.0"));
        bid = bidListRepository.save(bid);

        mockMvc.perform(post("/bidList/update/" + bid.getBidListId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("account", "Updated Account")
                .param("type", "Updated Type")
                .param("bidQuantity", "200"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser(username = "user")
    void shouldDeleteBid() throws Exception {
        BidList bid = new BidList("To Delete", "Type", new BigDecimal("50.0"));
        bid = bidListRepository.save(bid);

        mockMvc.perform(get("/bidList/delete/" + bid.getBidListId()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/bidList/list"));
    }
}
