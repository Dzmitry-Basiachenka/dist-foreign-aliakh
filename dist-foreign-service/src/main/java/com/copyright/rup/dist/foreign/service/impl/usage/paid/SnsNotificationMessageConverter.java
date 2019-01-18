package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import com.copyright.rup.dist.common.integration.util.JsonUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import javax.annotation.PostConstruct;

/**
 * Converts SNS notification into original SQS message.
 *
 * @see <a href="https://docs.aws.amazon.com/sns/latest/dg/sns-message-and-json-formats.html">SNS Message format</a>
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/17/19
 * @author Pavel Liakh
 */
@Component("df.service.snsNotificationMessageConverter")
public class SnsNotificationMessageConverter {

    private ObjectMapper objectMapper;

    /**
     * Initializes object mapper.
     */
    @PostConstruct
    @SuppressWarnings("unused")
    public void initObjectMapper() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Parses SNS notification message, finds original SQS message and setups Exchange.out body and headers.
     *
     * @param exchange exchange expected to contain SNS notification message
     * @throws IOException in case of invalid SNS message body
     * @see <a href="https://docs.aws.amazon.com/sns/latest/dg/sns-message-and-json-formats.html">SNS Message format</a>
     */
    @SuppressWarnings("unused")
    public void convert(Exchange exchange) throws IOException {
        JsonNode snsNotificationRootNode = JsonUtils.readJsonTree(objectMapper, (String) exchange.getIn().getBody());
        Message message = exchange.getIn();
        message.setBody(Objects.requireNonNull(snsNotificationRootNode.get("Message")).toString());
        JsonNode messageAttributesNode = Objects.requireNonNull(snsNotificationRootNode.get("MessageAttributes"));
        messageAttributesNode.fieldNames().forEachRemaining(messageAttributeName ->
            message.setHeader(messageAttributeName,
                JsonUtils.getStringValue(messageAttributesNode.get(messageAttributeName).get("Value"))));
        exchange.setOut(message);
    }
}
