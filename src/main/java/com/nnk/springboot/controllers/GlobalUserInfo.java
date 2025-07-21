package com.nnk.springboot.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUserInfo {
    @ModelAttribute("username")
    public String addUsernameToModel(@AuthenticationPrincipal UserDetails user) {
        return (user != null) ? user.getUsername() : null;
    }
}
