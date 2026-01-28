package com.example.poc.processors;

import com.example.poc.model.Payment;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class JsonToXmlProcessor implements Processor {

    private final ObjectMapper json = new ObjectMapper();
    private final XmlMapper xml = new XmlMapper();

    @Override
    public void process(Exchange exchange) throws Exception {
        Payment p = (Payment) exchange.getProperty("paymentObj");
        if (p == null) {
            String body = exchange.getIn().getBody(String.class);
            p = json.readValue(body, Payment.class);
        }
        var wrapper = new java.util.LinkedHashMap<String, Object>();
        var req = new java.util.LinkedHashMap<String, Object>();
        req.put("transactionId", p.getTransactionId());
        req.put("payer", p.getPayer());
        req.put("payee", p.getPayee());
        req.put("paymentInstruction", p.getPaymentInstruction());
        wrapper.put("fraudCheckRequest", req);
        String xmlStr = xml.writeValueAsString(wrapper);
        exchange.getIn().setBody(xmlStr);
    }
}
