package com.example.poc.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Map;

public class XmlToJsonProcessor implements Processor {

    private final XmlMapper xml = new XmlMapper();
    private final ObjectMapper json = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {
        String xmlBody = exchange.getIn().getBody(String.class);
        Map<?,?> map = xml.readValue(xmlBody, Map.class);
        String jsonStr = json.writeValueAsString(map);
        exchange.getIn().setBody(jsonStr);
    }
}
