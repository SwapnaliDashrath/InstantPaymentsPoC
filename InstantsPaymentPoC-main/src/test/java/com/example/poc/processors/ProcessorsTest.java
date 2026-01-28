package com.example.poc.processors;

import com.example.poc.test.TestData;
import com.example.poc.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProcessorsTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testEndToEndProcessors() throws Exception {
        Payment p = TestData.validPayment();
        String json = mapper.writeValueAsString(p);

        var ctx = new DefaultCamelContext();
        Exchange ex = new DefaultExchange(ctx);
        ex.getIn().setBody(json);

        ValidationProcessor v = new ValidationProcessor();
        v.process(ex);
        assertNotNull(ex.getProperty("paymentObj"));

        JsonToXmlProcessor j2x = new JsonToXmlProcessor();
        j2x.process(ex);
        String xml = ex.getIn().getBody(String.class);
        assertTrue(xml.contains("fraudCheckRequest"));

        DefaultExchange ex2 = new DefaultExchange(ctx);
        ex2.getIn().setBody(xml);
        BlacklistProcessor bp = new BlacklistProcessor();
        bp.process(ex2);
        String respXml = ex2.getIn().getBody(String.class);
        assertTrue(respXml.contains("fraudCheckResult"));
    }
}
