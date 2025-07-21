package com.example.payout.dto;

import com.example.payout.model.PayoutStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayoutStatusUpdateDTO {
    @NotNull(message = "Status is required")
    private PayoutStatus status;
}
