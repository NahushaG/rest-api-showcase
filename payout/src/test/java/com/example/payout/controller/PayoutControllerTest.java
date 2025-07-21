package com.example.payout.controller;

import com.example.payout.dto.PayoutRequestDTO;
import com.example.payout.dto.PayoutResponseDto;
import com.example.payout.model.PayoutStatus;
import com.example.payout.service.PayoutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PayoutController.class)
//@AutoConfigureMockMvc(addFilters = false)
public class PayoutControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean  // Still valid for Spring Boot 3.5.3
    private PayoutService payoutService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreatePayout() throws Exception {
        PayoutRequestDTO payoutRequestDTO = new PayoutRequestDTO();
        payoutRequestDTO.setAmount(new BigDecimal("100.0"));
        payoutRequestDTO.setCurrency("EUR");
        payoutRequestDTO.setReference("Test");

        PayoutResponseDto payoutResponseDto = new PayoutResponseDto();
        payoutResponseDto.setId(UUID.randomUUID());
        payoutResponseDto.setCurrency("EUR");
        payoutResponseDto.setReference("Test");
        payoutResponseDto.setAmount(new BigDecimal("100.0"));
        payoutResponseDto.setStatus(PayoutStatus.PENDING);

        Mockito.when(payoutService.createPayout(Mockito.any(PayoutRequestDTO.class))).thenReturn(payoutResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payout")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())  // if CSRF is enabled
                        .header("X-API-KEY", "my-secret-key")  // <-- Add this header
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payoutRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(payoutService, times(1)).createPayout(any(PayoutRequestDTO.class));

    }

}
