package com.example.poc.processors;

import com.example.poc.fraud.BlacklistEngine;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;

public class BlacklistProcessor implements Processor {

    private final BlacklistEngine engine = new BlacklistEngine();
    private final XmlMapper xml = new XmlMapper();

    @Override
    public void process(Exchange exchange) throws Exception {
        String xmlBody = exchange.getIn().getBody(String.class);
        Map<?,?> wrapper = xml.readValue(xmlBody, Map.class);
        Map<?,?> req = (Map<?,?>) wrapper.get("fraudCheckRequest");
        Map<?,?> payer = (Map<?,?>) req.get("payer");
        Map<?,?> payee = (Map<?,?>) req.get("payee");
        String instruction = (String) req.get("paymentInstruction");
        String txId = (String) req.get("transactionId");

        var payerParty = new com.example.poc.model.Party(
                (String)payer.get("name"),
                (String)payer.get("bank"),
                (String)payer.get("country"),
                (String)payer.get("account")
        );
        var payeeParty = new com.example.poc.model.Party(
                (String)payee.get("name"),
                (String)payee.get("bank"),
                (String)payee.get("country"),
                (String)payee.get("account")
        );

        var checks = engine.runChecks(payerParty, payeeParty, instruction);
        boolean any = checks.values().stream().anyMatch(Boolean::booleanValue);
        String decision = any ? "REJECT" : "APPROVE";
        String message = any ? "Suspicious payment" : "Nothing found, all okay";

        var result = new java.util.LinkedHashMap<String,Object>();
        result.put("transactionId", txId);
        result.put("decision", decision);
        result.put("message", message);
        result.put("checks", checks);

        var wrapperOut = new java.util.LinkedHashMap<String,Object>();
        wrapperOut.put("fraudCheckResult", result);

        String xmlOut = xml.writeValueAsString(wrapperOut);
        exchange.getIn().setBody(xmlOut);
    }
}
