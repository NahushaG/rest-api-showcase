package com.example.payout.dto;

import com.example.payout.model.PayoutStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayoutResponseDto {

    private UUID id;
    private String reference;
    private BigDecimal amount;
    private String currency;
    private PayoutStatus status;

}
