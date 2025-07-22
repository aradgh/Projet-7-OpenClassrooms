package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "user")
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetails userDetails;

    @Test
    @DisplayName("GET /user/list as ADMIN should return user list view")
    public void testUserList_AdminAccess() throws Exception {
        User adminUser = new User();
        adminUser.setUsername("user");
        adminUser.setRole("ADMIN");

        when(userService.findByUsername("user")).thenReturn(adminUser);

        mockMvc.perform(get("/user/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/list"))
            .andExpect(model().attributeExists("users"));
    }

    @Test
    @DisplayName("GET /user/list as non-ADMIN should redirect to error")
    public void testUserList_NonAdminAccess() throws Exception {
        User normalUser = new User();
        normalUser.setUsername("user");
        normalUser.setRole("USER");

        when(userService.findByUsername("user")).thenReturn(normalUser);

        mockMvc.perform(get("/user/list"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/app/error"));
    }

    @Test
    @DisplayName("GET /user/add should return add user form")
    public void testAddUserForm() throws Exception {
        mockMvc.perform(get("/user/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/add"))
            .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("POST /user/validate with valid data should redirect to user list")
    public void testValidateUser_Valid() throws Exception {
        mockMvc.perform(post("/user/validate")
                .with(csrf())
                .param("fullname", "John Doe")
                .param("username", "johndoe")
                .param("password", "Password1!")
                .param("role", "USER"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/user/list"));

        verify(userService).save(any(User.class));
    }

    @Test
    @DisplayName("POST /user/validate with invalid data should return form with errors")
    public void testValidateUser_Invalid() throws Exception {
        mockMvc.perform(post("/user/validate")
                .with(csrf())
                .param("fullname", "")
                .param("username", "")
                .param("password", "pass")
                .param("role", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("user/add"))
            .andExpect(model().attributeHasFieldErrors("user", "fullname", "username", "password", "role"));
    }

    @Test
    @DisplayName("GET /user/update/{id} should return update form")
    public void testShowUpdateForm() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFullname("Jane Doe");

        when(userService.findById(1)).thenReturn(user);

        mockMvc.perform(get("/user/update/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("user/update"))
            .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("POST /user/update/{id} with valid data should redirect to user list")
    public void testUpdateUser_Valid() throws Exception {
        mockMvc.perform(post("/user/update/1")
                .with(csrf())
                .param("fullname", "Updated Name")
                .param("username", "updateduser")
                .param("password", "Password1!")
                .param("role", "USER"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @DisplayName("POST /user/update/{id} with invalid data should return form with errors")
    public void testUpdateUser_Invalid() throws Exception {
        mockMvc.perform(post("/user/update/1")
                .with(csrf())
                .param("fullname", "")
                .param("username", "")
                .param("password", "bad")
                .param("role", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("user/update"))
            .andExpect(model().attributeHasFieldErrors("user", "fullname", "username", "password", "role"));
    }

    @Test
    @DisplayName("GET /user/delete/{id} should delete user and redirect to list")
    public void testDeleteUser() throws Exception {
        mockMvc.perform(get("/user/delete/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/user/list"));

        verify(userService).delete(1);
    }
}
