package com.example.payout.service;

import com.example.payout.dto.PayoutRequestDTO;
import com.example.payout.dto.PayoutResponseDto;
import com.example.payout.model.Payout;
import com.example.payout.model.PayoutStatus;
import com.example.payout.repository.PayoutRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PayoutServiceTest {
    @Mock
    private PayoutRepository payoutRepository;
    @InjectMocks
    private PayoutService payoutService;

    @Test
    void testReturnAllPayouts() {
        Payout payout = new Payout();
        payout.setId(UUID.randomUUID());
        payout.setAmount(new BigDecimal("100.00"));
        payout.setCurrency("EUR");
        payout.setReference("REF456");
        List<Payout> payouts = List.of(payout);
        Mockito.when(payoutRepository.findAll()).thenReturn(payouts);
        List<PayoutResponseDto> result = payoutService.getAllPayouts();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("REF456", result.get(0).getReference());
    }

    @Test
    void shouldCreatePayout() {
        // Given
        PayoutRequestDTO payoutRequestDTO = new PayoutRequestDTO();
        payoutRequestDTO.setAmount(new BigDecimal("100.00"));
        payoutRequestDTO.setCurrency("EUR");
        payoutRequestDTO.setReference("REF456");

        // Simulate reference not found
        Mockito.when(payoutRepository.findByReference("REF456")).thenReturn(Optional.empty());

        // Capture the payout object being saved
        ArgumentCaptor<Payout> payoutCaptor = ArgumentCaptor.forClass(Payout.class);
        Mockito.when(payoutRepository.save(payoutCaptor.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        PayoutResponseDto payoutResponseDto = payoutService.createPayout(payoutRequestDTO);

        // Then
        Assertions.assertNotNull(payoutResponseDto);
        Assertions.assertEquals("EUR", payoutResponseDto.getCurrency());
        Assertions.assertEquals(new BigDecimal("100.00"), payoutResponseDto.getAmount());
        Assertions.assertEquals(PayoutStatus.PENDING, payoutCaptor.getValue().getStatus());

        Mockito.verify(payoutRepository).save(Mockito.any(Payout.class));
    }
}
