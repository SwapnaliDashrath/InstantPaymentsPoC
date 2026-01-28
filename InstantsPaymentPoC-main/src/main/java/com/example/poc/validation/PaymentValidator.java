package com.example.poc.validation;

import com.example.poc.model.Payment;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

public class PaymentValidator {

    public void validate(Payment payment) {
        if (payment == null) throw new IllegalArgumentException("Payment is null");
        if (payment.getTransactionId() == null || !isUUID(payment.getTransactionId())) {
            throw new IllegalArgumentException("Invalid Transaction ID");
        }
        if (payment.getPayer() == null || payment.getPayee() == null) {
            throw new IllegalArgumentException("Payer/Payee missing");
        }
        if (!isISO3Country(payment.getPayer().getCountry()) ||
            !isISO3Country(payment.getPayee().getCountry())) {
            throw new IllegalArgumentException("Invalid country code");
        }
        if (!isISOCurrency(payment.getCurrency())) {
            throw new IllegalArgumentException("Invalid currency code");
        }
        if (payment.getExecutionDate() == null) {
            throw new IllegalArgumentException("Execution date missing");
        }
        if (payment.getCreationTimestamp() == null || !isISOInstant(payment.getCreationTimestamp())) {
            throw new IllegalArgumentException("Invalid creation timestamp");
        }
    }

    private boolean isUUID(String value) {
        try { UUID.fromString(value); return true; } catch (Exception e) { return false; }
    }

    private boolean isISO3Country(String code) {
        if (code == null || code.length() != 3) return false;
        for (String iso : Locale.getISOCountries()) {
            Locale loc = new Locale("", iso);
            try {
                if (loc.getISO3Country().equalsIgnoreCase(code)) return true;
            } catch (Exception e) {}
        }
        return false;
    }

    private boolean isISOCurrency(String code) {
        if (code == null) return false;
        try { Currency.getInstance(code); return true; } catch (Exception e) { return false; }
    }

    private boolean isISOInstant(String ts) {
        try { Instant.parse(ts); return true; } catch (Exception e) { return false; }
    }
}
