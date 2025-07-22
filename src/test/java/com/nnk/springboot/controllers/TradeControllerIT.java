package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "user")
public class TradeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeRepository tradeRepository;

    @Test
    @DisplayName("GET /trade/list should return trade list view")
    public void testHome() throws Exception {
        mockMvc.perform(get("/trade/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("trade/list"))
            .andExpect(model().attributeExists("trades"));
    }

    @Test
    @DisplayName("GET /trade/add should return add trade form")
    public void testAddTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("trade/add"))
            .andExpect(model().attributeExists("trade"));
    }

    @Test
    @DisplayName("POST /trade/validate with valid data should redirect to list")
    public void testValidateTrade_Valid() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .with(csrf())
                .param("account", "Account1")
                .param("type", "Type1")
                .param("buyQuantity", "123.45"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @DisplayName("POST /trade/validate with invalid data should return form with errors")
    public void testValidateTrade_Invalid() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .with(csrf())
                .param("account", "")
                .param("type", "")
                .param("buyQuantity", "abc"))
            .andExpect(status().isOk())
            .andExpect(view().name("trade/add"))
            .andExpect(model().attributeHasFieldErrors("trade", "account", "type", "buyQuantity"));
    }

    @Test
    @DisplayName("GET /trade/update/{id} should return update form")
    public void testShowUpdateForm() throws Exception {
        Trade trade = new Trade("Account1", "Type1");
        trade.setTradeId(1);
        trade.setBuyQuantity(new BigDecimal("100.00"));
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        mockMvc.perform(get("/trade/update/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("trade/update"))
            .andExpect(model().attributeExists("trade"));
    }

    @Test
    @DisplayName("POST /trade/update/{id} with valid data should redirect to list")
    public void testUpdateTrade_Valid() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                .with(csrf())
                .param("account", "UpdatedAccount")
                .param("type", "UpdatedType")
                .param("buyQuantity", "999.99"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @DisplayName("POST /trade/update/{id} with invalid data should return update form with errors")
    public void testUpdateTrade_Invalid() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                .with(csrf())
                .param("account", "")
                .param("type", "")
                .param("buyQuantity", "invalid"))
            .andExpect(status().isOk())
            .andExpect(view().name("trade/update"))
            .andExpect(model().attributeHasFieldErrors("trade", "account", "type", "buyQuantity"));
    }

    @Test
    @DisplayName("GET /trade/delete/{id} should delete trade and redirect to list")
    public void testDeleteTrade() throws Exception {
        Trade trade = new Trade("Account1", "Type1");
        trade.setTradeId(1);
        trade.setBuyQuantity(new BigDecimal("100.00"));
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        mockMvc.perform(get("/trade/delete/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/trade/list"));
    }

}
