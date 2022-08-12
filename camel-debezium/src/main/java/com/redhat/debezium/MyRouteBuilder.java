package com.redhat.debezium;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.debezium.DebeziumConstants;
import org.apache.camel.component.kafka.KafkaConstants;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    public void configure() {
        from("debezium-sqlserver:dbz-test-1?"
                + "databaseHostname=localhost"
                + "&databaseDbname=debezium"
                + "&databaseUser=debezium"
                + "&databasePassword=dbz"
                + "&databaseServerName=my-app-connector"
                + "&databaseHistoryFileFilename=/tmp/dbhistory.dat"
                + "&tableIncludeList=dbo._order,dbo.users"
                + "&offsetStorageFileName=/tmp/offset.dat")
                .choice()
                .when(header(DebeziumConstants.HEADER_OPERATION).isEqualTo("c"))
                .process(new AfterStructToOrderTranslator())
//                .setBody(constant("${body}")) //constant("Message from Camel")          // Message to send
//                .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
                .to("kafka:test?brokers=localhost:29092")
                .to("rest-swagger:http://localhost:8082/v2/api-docs#addOrderUsingPOST")
                .endChoice()
                .when(header(DebeziumConstants.HEADER_OPERATION).isEqualTo("d"))
                .process(new BeforeStructToOrderTranslator())
                .to("rest-swagger:http://localhost:8082/v2/api-docs#deleteOrderUsingDELETE")
//                .setBody(constant("${body}")) //constant("Message from Camel")          // Message to send
//                .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
                .to("kafka:test?brokers=localhost:29092")
                .otherwise()
                .log("Header : ${headers}")
                .log("Body: ${body}");
        /*
        from("debezium-mysql:order-connector?"
                + "databaseServerId=1"
                + "&databaseHostname=localhost"
                + "&databaseUser=debezium"
                + "&databasePassword=dbz"
                + "&databaseServerName=my-app-connector"
                + "&databaseHistoryFileFilename=/tmp/dbhistory.dat"
                + "&databaseIncludeList=debezium"
                + "&tableWhitelist=debezium._order"
                + "&offsetStorageFileName=/tmp/offset.dat")
                .choice()
                    .when(header(DebeziumConstants.HEADER_OPERATION).isEqualTo("c"))
                        .process(new AfterStructToOrderTranslator())
                        .to("rest-swagger:http://localhost:8082/v2/api-docs#addOrderUsingPOST")
                    .when(header(DebeziumConstants.HEADER_OPERATION).isEqualTo("d"))
                        .process(new BeforeStructToOrderTranslator())
                        .to("rest-swagger:http://localhost:8082/v2/api-docs#deleteOrderUsingDELETE")
                .log("Response : ${body}");

         */
    }

}
