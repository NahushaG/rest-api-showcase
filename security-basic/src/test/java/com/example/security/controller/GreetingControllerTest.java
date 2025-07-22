package com.example.security.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = GreetingController.class)
@AutoConfigureMockMvc
public class GreetingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "username", roles = {"USER"})
    public void testGreeting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to the User Greeting"));
    }

    @Test
    void testUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/hello"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }
}
