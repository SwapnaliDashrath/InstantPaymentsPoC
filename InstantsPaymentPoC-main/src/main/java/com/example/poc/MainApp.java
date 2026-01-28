package com.example.poc;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import com.example.poc.processors.*;

public class MainApp {

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new DemoRoutes());
        main.run(args);
    }

    public static class DemoRoutes extends RouteBuilder {
        @Override
        public void configure() throws Exception {

            // PPS REST endpoint (simulate via direct:start for simplicity)
            from("direct:pps")
                .routeId("PPS-Route")
                .log("Received payment in PPS")
                .process(new ValidationProcessor())
                // send JSON to BS via JMS in Solution 1, here we simulate by direct:bs
                .to("direct:bs");

            // BS route receives JSON, converts to XML and sends to FCS
            from("direct:bs")
                .routeId("BS-Route")
                .log("BS received request")
                .process(new JsonToXmlProcessor())
                // send to FCS queue (simulate with direct:fcs)
                .to("direct:fcs");

            // FCS route: blacklist check and reply
            from("direct:fcs")
                .routeId("FCS-Route")
                .log("FCS processing")
                .process(new BlacklistProcessor())
                // convert result XML back to JSON for BS consumer
                .process(new XmlToJsonProcessor())
                // return to PPS (in real JMS there'd be queues; here we log)
                .log("Final fraud result: ${body}");
        }
    }
}
