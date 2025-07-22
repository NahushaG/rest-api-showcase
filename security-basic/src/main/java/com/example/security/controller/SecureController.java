package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
public class SecureController {
    @GetMapping("/admin/service")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminGreeting() {
        return "Welcome to the Admin page";
    }

    @GetMapping("/user/service")
    @PreAuthorize("hasRole('USER')")
    public String userGreeting() {
        return "Welcome to the User page";
    }
}
