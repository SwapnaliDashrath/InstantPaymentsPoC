package com.example.poc.validation;

import com.example.poc.model.Payment;
import com.example.poc.test.TestData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentValidatorTest {

    @Test
    public void testValidPayment() {
        Payment p = TestData.validPayment();
        new PaymentValidator().validate(p);
    }

    @Test
    public void testInvalidUUID() {
        Payment p = TestData.validPayment();
        p.setTransactionId("bad-id");
        assertThrows(IllegalArgumentException.class, () -> new PaymentValidator().validate(p));
    }
}
