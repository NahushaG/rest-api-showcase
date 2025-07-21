package com.example.payout;

import com.example.payout.dto.PayoutRequestDTO;
import com.example.payout.model.Payout;
import com.example.payout.repository.PayoutRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PayoutIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PayoutRepository payoutRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String VALID_API_KEY = "my-secret-key";

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testCreatePayout() throws Exception {
        PayoutRequestDTO requestDTO = new PayoutRequestDTO();
        requestDTO.setAmount(new BigDecimal("100.0"));
        requestDTO.setCurrency("EUR");
        requestDTO.setReference("REF123");


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payout")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .header(API_KEY_HEADER, VALID_API_KEY)
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PENDING"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reference").value("REF123"));

        // Verify the payout is persisted in DB
        Optional<Payout> payoutOpt = payoutRepository.findByReference("REF123");
        Assertions.assertThat(payoutOpt).isPresent();

        Payout payout = payoutOpt.get();
        Assertions.assertThat(payout.getAmount().compareTo(new BigDecimal("100.0"))).isZero();
        Assertions.assertThat(payout.getStatus().toString()).isEqualToIgnoringCase("PENDING");
    }


    @Test
    public void testCreatePayout_Unauthorized() throws Exception {
        PayoutRequestDTO request = new PayoutRequestDTO();
        request.setAmount(new BigDecimal("100.0"));
        request.setReference("REF123");

        // Missing API key - expect 401 Unauthorized
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payout")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Unauthorized - Invalid API key"));
    }
}
