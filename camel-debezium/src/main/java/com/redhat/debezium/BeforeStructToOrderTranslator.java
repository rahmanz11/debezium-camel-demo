package com.redhat.debezium;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.debezium.DebeziumConstants;

public class BeforeStructToOrderTranslator implements Processor {

    public void process(Exchange exchange) throws Exception {
        final Map value = exchange.getMessage().getHeader(DebeziumConstants.HEADER_BEFORE, Map.class);
        int userId = 0;
        int orderId = 0;
        // Convert and set body
        if (value.get("user_id") != null) {
            userId = (int) value.get("user_id");
        }
        if (value.get("order_id") != null) {
            orderId = (int) value.get("order_id");
        }

        exchange.getIn().setHeader("userId", userId);
        exchange.getIn().setHeader("orderId", orderId);
    }
}
