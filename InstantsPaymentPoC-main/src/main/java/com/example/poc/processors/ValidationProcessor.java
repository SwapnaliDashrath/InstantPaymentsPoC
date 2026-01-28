package com.example.poc.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.example.poc.validation.PaymentValidator;
import com.example.poc.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidationProcessor implements Processor {
    private final PaymentValidator validator = new PaymentValidator();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        Payment p = mapper.readValue(body, Payment.class);
        validator.validate(p);
        exchange.setProperty("paymentObj", p);
    }
}
