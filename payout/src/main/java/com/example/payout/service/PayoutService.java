package com.example.payout.service;

import com.example.payout.dto.PayoutRequestDTO;
import com.example.payout.dto.PayoutResponseDto;
import com.example.payout.dto.PayoutStatusUpdateDTO;
import com.example.payout.exception.PayoutNotFoundException;
import com.example.payout.model.Payout;
import com.example.payout.model.PayoutStatus;
import com.example.payout.repository.PayoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayoutService {

    private final PayoutRepository payoutRepository;

    public PayoutResponseDto createPayout(PayoutRequestDTO request) {
        return payoutRepository.findByReference(request.getReference())
                .map(this::mapToDto)
                .orElseGet(() -> {
                    Payout payout = Payout.builder()
                            .reference(request.getReference())
                            .amount(request.getAmount())
                            .currency(request.getCurrency())
                            .status(PayoutStatus.PENDING)
                            .build();
                    Payout saved = payoutRepository.save(payout);
                    return mapToDto(saved);
                });
    }

    private PayoutResponseDto mapToDto(Payout payout) {
        return PayoutResponseDto.builder()
                .id(payout.getId())
                .reference(payout.getReference())
                .amount(payout.getAmount())
                .currency(payout.getCurrency())
                .status(payout.getStatus())
                .build();
    }

    public PayoutResponseDto getPayoutById(UUID id) {
        Payout payout = payoutRepository.findById(id)
                .orElseThrow(() -> new PayoutNotFoundException("Payout not found" + id));
        return mapToDto(payout);
    }

    public PayoutResponseDto updatePayoutStatus(UUID id, PayoutStatusUpdateDTO statusUpdate) {
        Payout payout = payoutRepository.findById(id)
                .orElseThrow(() -> new PayoutNotFoundException("Payout not found" + id));
        payout.setStatus(statusUpdate.getStatus());
        Payout saved = payoutRepository.save(payout);
        return mapToDto(saved);
    }

    // List all payouts
    public List<PayoutResponseDto> listPayouts() {
        return payoutRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<PayoutResponseDto> getAllPayouts() {
        return payoutRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
