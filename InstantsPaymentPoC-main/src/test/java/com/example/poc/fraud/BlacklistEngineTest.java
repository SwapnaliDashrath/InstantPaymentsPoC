package com.example.poc.fraud;

import com.example.poc.model.Payment;
import com.example.poc.test.TestData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlacklistEngineTest {

    @Test
    public void testBlockedByName() {
        BlacklistEngine engine = new BlacklistEngine();
        Payment p = TestData.validPayment();
        p.getPayer().setName("Mark Imaginary");
        var checks = engine.runChecks(p.getPayer(), p.getPayee(), p.getPaymentInstruction());
        assertTrue(checks.get("blacklistedPayerName"));
    }
}
