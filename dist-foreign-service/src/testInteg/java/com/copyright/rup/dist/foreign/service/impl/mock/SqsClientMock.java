package com.copyright.rup.dist.foreign.service.impl.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.JsonMatcher;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final List<String> EXISTING_QUEUES = Arrays.asList(AWS_SQS_URL + "fda-test-sf-detail.fifo");
    private ExpectedSendMessage expectedSendMessage;
    private SendMessage actualSendMessage;

    @Override
    public ListQueuesResult listQueues() {
        ListQueuesResult listQueuesResult = new ListQueuesResult();
        listQueuesResult.withQueueUrls(EXISTING_QUEUES);
        return listQueuesResult;
    }

    @Override
    public SendMessageResult sendMessage(SendMessageRequest request) {
        actualSendMessage = new SendMessage();
        actualSendMessage.queueUrl = request.getQueueUrl();
        actualSendMessage.body = request.getMessageBody();
        request.getMessageAttributes()
            .forEach((attributeName, attributeValueObject) -> actualSendMessage.headers.put(attributeName,
                attributeValueObject.getStringValue()));
        return new SendMessageResult();
    }

    public void reset() {
        expectedSendMessage = null;
        actualSendMessage = null;
    }

    public void prepareSendMessageExpectations(String queueName, String body, List<String> excludedFields,
                                               Map<String, String> headers) {
        expectedSendMessage = new ExpectedSendMessage();
        expectedSendMessage.setQueueUrl(AWS_SQS_URL + queueName);
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

    private static class SendMessage {

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

    private static class ExpectedSendMessage extends SendMessage {

        private List<String> excludedFields;

        List<String> getExcludedFields() {
            return excludedFields;
        }

        void setExcludedFields(List<String> excludedFields) {
            this.excludedFields = excludedFields;
        }
    }
}
