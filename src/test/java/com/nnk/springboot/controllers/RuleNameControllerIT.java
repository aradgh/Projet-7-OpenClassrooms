package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user")
@ActiveProfiles("test")
public class RuleNameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @Test
    @DisplayName("GET /ruleName/list - should return ruleName list view")
    void testHome() throws Exception {
        when(ruleNameService.findAll()).thenReturn(Collections.singletonList(new RuleName()));

        mockMvc.perform(get("/ruleName/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("ruleName/list"))
            .andExpect(model().attributeExists("ruleNames"));
    }

    @Test
    @DisplayName("GET /ruleName/add - should show add form")
    void testAddRuleForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("ruleName/add"))
            .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @DisplayName("POST /ruleName/validate - should validate and redirect when valid")
    void testValidateValid() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .param("name", "Rule1")
                .param("description", "Some description")
                .param("json", "{}")
                .param("template", "template")
                .param("sqlStr", "SELECT *")
                .param("sqlPart", "FROM table"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).save(any(RuleName.class));
    }

    @Test
    @DisplayName("POST /ruleName/validate - should return to form when invalid")
    void testValidateInvalid() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .param("name", "")
                .param("description", "")
                .param("json", "")
                .param("template", "")
                .param("sqlStr", "")
                .param("sqlPart", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("ruleName/add"));
    }

    @Test
    @DisplayName("GET /ruleName/update/{id} - should show update form")
    void testShowUpdateForm() throws Exception {
        RuleName ruleName = new RuleName("Rule1", "desc", "{}", "tpl", "sql", "sqlPart");
        ruleName.setId(1);
        when(ruleNameService.findById(1)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("ruleName/update"))
            .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @DisplayName("POST /ruleName/update/{id} - should update and redirect when valid")
    void testUpdateValid() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                .with(csrf())
                .param("name", "RuleUpdated")
                .param("description", "Updated description")
                .param("json", "{}")
                .param("template", "tpl")
                .param("sqlStr", "SELECT 1")
                .param("sqlPart", "FROM dual"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).save(any(RuleName.class));
    }

    @Test
    @DisplayName("POST /ruleName/update/{id} - should return to form when invalid")
    void testUpdateInvalid() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                .with(csrf())
                .param("name", "")
                .param("description", "")
                .param("json", "")
                .param("template", "")
                .param("sqlStr", "")
                .param("sqlPart", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("ruleName/update"));
    }

    @Test
    @DisplayName("GET /ruleName/delete/{id} - should delete and redirect")
    void testDelete() throws Exception {
        mockMvc.perform(get("/ruleName/delete/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/ruleName/list"));

        verify(ruleNameService, times(1)).delete(1);
    }
}
