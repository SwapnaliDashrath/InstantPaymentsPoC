package com.example.poc.test;

import com.example.poc.model.Payment;
import com.example.poc.model.Party;
import java.math.BigDecimal;

public class TestData {
    public static Payment validPayment() {
        Payment p = new Payment();
        p.setTransactionId("11111111-2222-3333-4444-555555555555");
        p.setPayer(new Party("Munster Muller","Bank of America","USA","12345"));
        p.setPayee(new Party("Alice Example","BNP Paribas","DEU","54321"));
        p.setPaymentInstruction("Loan Repayment");
        p.setExecutionDate("2025-08-15");
        p.setAmount(new BigDecimal("17.45"));
        p.setCurrency("USD");
        p.setCreationTimestamp("2025-08-10T10:12:00Z");
        return p;
    }
}
