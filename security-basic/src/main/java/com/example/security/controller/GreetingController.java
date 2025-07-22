package com.example.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GreetingController {
    @GetMapping("/public/hello")
    public String publicGreeting() {
        return "Welcome to the Public Greeting";
    }

    @GetMapping("/user/hello")
    public String userGreeting() {
        return "Welcome to the User Greeting";
    }

    @GetMapping("/admin/hello")
    public String adminGreeting() {
        return "Welcome to the Admin Greeting";
    }
}
