package com.example.poc;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

public class PaymentValidator {
    public void validate(Payment payment) {
        if (payment.getTransactionId() == null || !isUUID(payment.getTransactionId())) {
            throw new IllegalArgumentException("Invalid Transaction ID");
        }
        if (!isISO3Country(payment.getPayer().getCountry()) ||
            !isISO3Country(payment.getPayee().getCountry())) {
            throw new IllegalArgumentException("Invalid country code");
        }
        if (!isISOCurrency(payment.getCurrency())) {
            throw new IllegalArgumentException("Invalid currency code");
        }
        if (payment.getExecutionDate() == null || LocalDate.parse(payment.getExecutionDate()).isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid execution date");
        }
        if (payment.getCreationTimestamp() == null || Instant.parse(payment.getCreationTimestamp()).isAfter(Instant.now())) {
            throw new IllegalArgumentException("Invalid creation timestamp");
        }
    }
    private boolean isUUID(String value) {
        try { UUID.fromString(value); return true; } catch (Exception e) { return false; }
    }
    private boolean isISO3Country(String code) {
        return code != null && code.matches("[A-Z]{3}");
    }
    private boolean isISOCurrency(String code) {
        try { Currency.getInstance(code); return true; } catch (Exception e) { return false; }
    }
}
