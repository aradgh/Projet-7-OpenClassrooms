package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
@WithMockUser(username = "user")
class CurvePointControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    void setUp() {
        curvePointRepository.deleteAll();
    }

    @Test
    @DisplayName("When calling GET /curvePoint/list, should display CurvePoint list page containing saved points")
    void shouldDisplayCurvePointList() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, BigDecimal.valueOf(10.5), BigDecimal.valueOf(20.5));
        curvePointRepository.save(curvePoint);

        mockMvc.perform(get("/curvePoint/list"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("10.5")))
            .andExpect(content().string(containsString("20.5")));
    }

    @Test
    @DisplayName("When calling GET /curvePoint/add, should show add form page")
    void shouldShowAddForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("curvePoint/add"))
            .andExpect(content().string(containsString("Add New Curve Point")));
    }

    @Test
    @DisplayName("When calling POST /curvePoint/validate with valid data, should create curve point and redirect")
    void shouldValidateAndAddCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("term", "10")
                .param("curveValue", "20.5")
                .param("curveId", "30"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @DisplayName("When calling POST /curvePoint/validate with invalid data, should reject and return to add form")
    void shouldRejectInvalidCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("term", "")  // empty => invalid
                .param("curveValue", "")
                .param("curveId", ""))
            .andExpect(status().isOk())
            .andExpect(model().hasErrors())
            .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @DisplayName("When calling GET /curvePoint/update/{id}, should show update form with curve point data")
    void shouldShowUpdateForm() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, BigDecimal.valueOf(10.5), BigDecimal.valueOf(20.5));
        curvePoint = curvePointRepository.save(curvePoint);

        mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name("curvePoint/update"))
            .andExpect(model().attributeExists("curvePoint"))
            .andExpect(content().string(containsString("10.5")));
    }

    @Test
    @DisplayName("When calling POST /curvePoint/update/{id} with valid data, should update curve point and redirect")
    void shouldUpdateValidCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, BigDecimal.valueOf(10.5), BigDecimal.valueOf(20.5));
        curvePoint = curvePointRepository.save(curvePoint);

        mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("term", "15")
                .param("curveValue", "25.5")
                .param("curveId", "35"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @DisplayName("When calling POST /curvePoint/update/{id} with invalid data, should reject and return update form")
    void shouldRejectInvalidUpdate() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, BigDecimal.valueOf(10.5), BigDecimal.valueOf(20.5));
        curvePoint = curvePointRepository.save(curvePoint);

        mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("term", "") // invalid empty field
                .param("curveValue", "")
                .param("curveId", ""))
            .andExpect(status().isOk())
            .andExpect(model().hasErrors())
            .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @DisplayName("When calling GET /curvePoint/delete/{id}, should delete curve point and redirect")
    void shouldDeleteCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint(1, BigDecimal.valueOf(10.5), BigDecimal.valueOf(20.5));
        curvePoint = curvePointRepository.save(curvePoint);

        mockMvc.perform(get("/curvePoint/delete/" + curvePoint.getId()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/curvePoint/list"));
    }
}
