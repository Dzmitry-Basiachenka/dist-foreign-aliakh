package com.copyright.rup.dist.foreign.service.impl.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.JsonMatcher;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.SetQueueAttributesResult;
import com.google.common.collect.ImmutableMap;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Mock class for {@link AmazonSQSClient} to avoid requests to AWS services.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/11/2019
 *
 * @author Pavel Liakh
 */
public class SqsClientMock extends AmazonSQSClient {

    private static final String AWS_SQS_URL = "https://queue.amazonaws.com/queue/";
    private static final String AWS_ENV_PREFIX = "fda-test-";
    private static final Function<String, String> BUILD_QUEUE_URL =
        queueName -> AWS_SQS_URL + AWS_ENV_PREFIX + queueName;
    private static final List<String> EXISTING_QUEUES = Arrays.asList(
        BUILD_QUEUE_URL.apply("sf-detail.fifo"),
        BUILD_QUEUE_URL.apply("df-consumer-sf-detail-paid"));
    private static final String SNS_NOTIFICATION_MESSAGE_FORMAT = "{" +
        "  \"Type\" : \"Notification\"," +
        "  \"MessageId\" : \"%s\"," +
        "  \"TopicArn\" : \"arn:aws:sns:us-east-1:10101010101:lm-pdev-sf-detail-paid\"," +
        "  \"Message\" : \"%s\"," +
        "  \"Timestamp\" : \"%s\"," +
        "  \"SignatureVersion\" : \"1\"," +
        "  \"Signature\" : \"signature\"," +
        "  \"SigningCertURL\" : \"signatureUrl\"," +
        "  \"UnsubscribeURL\" : \"unsubscribeUrl\"," +
        "  \"MessageAttributes\" : {" +
        "    \"breadcrumbId\" : {\"Type\":\"String\",\"Value\":\"breadcrumbId\"}," +
        "    \"source\" : {\"Type\":\"String\",\"Value\":\"FDA\"}" +
        "  }" +
        "}";
    private ExpectedSqsMessage expectedSendMessage;
    private SqsMessage actualSendMessage;
    private SqsMessage preparedReceivedMessage;

    public void reset() {
        expectedSendMessage = null;
        actualSendMessage = null;
        preparedReceivedMessage = null;
    }

    @Override
    public GetQueueUrlResult getQueueUrl(String queueName) {
        return new GetQueueUrlResult().withQueueUrl(AWS_SQS_URL + queueName);
    }

    @Override
    public GetQueueAttributesResult getQueueAttributes(String queueUrl, List<String> attributeNames) {
        return new GetQueueAttributesResult().withAttributes(
            ImmutableMap.of("QueueArn", "arn:aws:iam::1010101010101:user:"));
    }

    @Override
    public SetQueueAttributesResult setQueueAttributes(SetQueueAttributesRequest request) {
        return new SetQueueAttributesResult();
    }

    @Override
    public ListQueuesResult listQueues() {
        ListQueuesResult listQueuesResult = new ListQueuesResult();
        listQueuesResult.withQueueUrls(EXISTING_QUEUES);
        return listQueuesResult;
    }

    @Override
    public SendMessageResult sendMessage(SendMessageRequest request) {
        actualSendMessage = new SqsMessage();
        actualSendMessage.queueUrl = request.getQueueUrl();
        actualSendMessage.body = request.getMessageBody();
        request.getMessageAttributes()
            .forEach((attributeName, attributeValueObject) -> actualSendMessage.headers.put(attributeName,
                attributeValueObject.getStringValue()));
        return new SendMessageResult();
    }


    public void prepareSendMessageExpectations(String queueName, String body, List<String> excludedFields,
                                               Map<String, String> headers) {
        expectedSendMessage = new ExpectedSqsMessage();
        expectedSendMessage.setQueueUrl(AWS_SQS_URL + AWS_ENV_PREFIX + queueName);
        expectedSendMessage.setBody(body);
        expectedSendMessage.setExcludedFields(excludedFields);
        expectedSendMessage.setHeaders(headers);
    }

    public void assertSendMessage() {
        assertNotNull(actualSendMessage);
        assertNotNull(expectedSendMessage);
        assertEquals(expectedSendMessage.getQueueUrl(), actualSendMessage.getQueueUrl());
        assertTrue(String.format("ActualMessage=%s, ExpectedMessage=%s, ExcludedFields=%s",
            actualSendMessage.body, expectedSendMessage.getBody(), expectedSendMessage.getExcludedFields()),
            new JsonMatcher(expectedSendMessage.getBody(), expectedSendMessage.excludedFields)
                .matches(actualSendMessage.body));
        expectedSendMessage.getHeaders().forEach((expectedHeader, expectedValue) -> {
            assertTrue(actualSendMessage.headers.containsKey(expectedHeader));
            assertEquals(actualSendMessage.headers.get(expectedHeader), expectedValue);
        });
    }

    public void prepareReceivedMessage(String body, Map<String, String> attributes, String queueName) {
        preparedReceivedMessage = new SqsMessage();
        preparedReceivedMessage.setBody(body);
        preparedReceivedMessage.setHeaders(attributes);
        preparedReceivedMessage.setQueueUrl(BUILD_QUEUE_URL.apply(queueName));
    }

    /* SQS is polling-based consumer, so that prepared message will be returned on next polling of specified queue.
     - See http://camel.apache.org/polling-consumer.html
     - See org.apache.camel.implScheduledPollConsumer.
    On success consuming SQS confirms receiving by calling DeleteMessage, so that prepared message will be returned once
    */
    @Override
    public ReceiveMessageResult receiveMessage(ReceiveMessageRequest request) {
        ReceiveMessageResult receiveMessageResult = new ReceiveMessageResult();
        if (Objects.nonNull(preparedReceivedMessage) &&
            request.getQueueUrl().equals(preparedReceivedMessage.getQueueUrl())) {
            Message message = new Message();
            message.setBody(buildSnsNotificationMessage(preparedReceivedMessage.getBody()));
            message.setAttributes(preparedReceivedMessage.getHeaders());
            message.setMessageId(RupPersistUtils.generateUuid());
            receiveMessageResult.setMessages(Collections.singletonList(message));
        } else {
            receiveMessageResult.setMessages(Collections.emptyList());
        }
        return receiveMessageResult;
    }

    @Override
    public DeleteMessageResult deleteMessage(DeleteMessageRequest request) {
        preparedReceivedMessage = null;
        return new DeleteMessageResult();
    }


    private String buildSnsNotificationMessage(String body) {
        return String.format(SNS_NOTIFICATION_MESSAGE_FORMAT, RupPersistUtils.generateUuid(),
            body.replace("\n", "").replace("\"", "\\\""), OffsetDateTime.now().toString());
    }

    private static class SqsMessage {

        private String queueUrl;
        private String body;
        private Map<String, String> headers = new HashMap<>();

        String getQueueUrl() {
            return queueUrl;
        }

        void setQueueUrl(String queueUrl) {
            this.queueUrl = queueUrl;
        }

        String getBody() {
            return body;
        }

        void setBody(String body) {
            this.body = body;
        }

        Map<String, String> getHeaders() {
            return headers;
        }

        void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }
    }

    private static class ExpectedSqsMessage extends SqsMessage {

        private List<String> excludedFields;

        List<String> getExcludedFields() {
            return excludedFields;
        }

        void setExcludedFields(List<String> excludedFields) {
            this.excludedFields = excludedFields;
        }
    }
}
