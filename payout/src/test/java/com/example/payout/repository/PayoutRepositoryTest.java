package com.example.payout.repository;

import com.example.payout.model.Payout;
import com.example.payout.model.PayoutStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
public class PayoutRepositoryTest {
    @Autowired
    private PayoutRepository payoutRepository;

    @Test
    public void testSaveAndFindByStatus() {
        Payout payout1 = new Payout();
        payout1.setReference("TEST");
        payout1.setAmount(new BigDecimal("100.0"));
        payout1.setStatus(PayoutStatus.PENDING);
        payout1.setReference("REF1");
        payout1.setCurrency("EUR");
        Payout payout2 = new Payout();
        payout2.setReference("TEST");
        payout2.setAmount(new BigDecimal("100.0"));
        payout2.setStatus(PayoutStatus.PENDING);
        payout2.setReference("REF2");
        payout2.setCurrency("EUR");
        payoutRepository.save(payout1);
        payoutRepository.save(payout2);

        // When
        Optional<Payout> pendingPayouts = payoutRepository.findByReference("REF1");

        // Then
        Assertions.assertThat(pendingPayouts).isPresent();
        Payout found = pendingPayouts.get();
        Assertions.assertThat(found.getAmount()).isEqualTo(new BigDecimal("100.0"));
        Assertions.assertThat(found.getStatus()).isEqualTo(PayoutStatus.PENDING);
    }

}
