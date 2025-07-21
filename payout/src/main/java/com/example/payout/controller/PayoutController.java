package com.example.payout.controller;

import com.example.payout.dto.PayoutRequestDTO;
import com.example.payout.dto.PayoutResponseDto;
import com.example.payout.dto.PayoutStatusUpdateDTO;
import com.example.payout.service.PayoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payout")
@RequiredArgsConstructor
public class PayoutController {
    private final PayoutService payoutService;

    @PostMapping
    public ResponseEntity<PayoutResponseDto> createPayout(@Valid @RequestBody PayoutRequestDTO request) {
        PayoutResponseDto payoutResponseDto = payoutService.createPayout(request);
        return ResponseEntity.status(201).body(payoutResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayoutResponseDto> getPayoutById(@PathVariable UUID id) {
        PayoutResponseDto response = payoutService.getPayoutById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PayoutResponseDto> updatePayoutStatus(
            @PathVariable UUID id,
            @Valid @RequestBody PayoutStatusUpdateDTO statusUpdate) {
        PayoutResponseDto response = payoutService.updatePayoutStatus(id, statusUpdate);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PayoutResponseDto>> getAllPayouts() {
        List<PayoutResponseDto> responses = payoutService.listPayouts();
        return ResponseEntity.ok(responses);
    }
}
