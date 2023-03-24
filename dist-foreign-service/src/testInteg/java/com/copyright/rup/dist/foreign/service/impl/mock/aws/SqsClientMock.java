package com.copyright.rup.dist.foreign.service.impl.mock.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.JsonMatcher;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageResponse;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.QueueDoesNotExistException;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

/**
 * Mock class for {@link SqsClient} to avoid requests to AWS services.
 * <p>
 * See com.amazonaws.services.sqs.AmazonSQS
 * See org.apache.camel.component.aws.sqs.SqsComponent
 * <p>
 * Copyright (C) 2019 copyright.com
 * </p>
 * Date: 01/21/2019
 *
 * @author Pavel Liakh
 */
public class SqsClientMock implements SqsClient {

    private final Map<String, List<Message>> queueUrlsToMessages = new HashMap<>();
    private final List<SendMessageRequest> actualSendMessageRequests = new ArrayList<>();

    @Override
    public String serviceName() {
        return SERVICE_NAME;
    }

    @Override
    public void close() {
    }

    @Override
    public ListQueuesResponse listQueues(ListQueuesRequest request) {
        return ListQueuesResponse.builder().queueUrls(queueUrlsToMessages.keySet()).build();
    }

    @Override
    public CreateQueueResponse createQueue(CreateQueueRequest request) {
        String queueUrl = buildQueueUrl(request.queueName());
        queueUrlsToMessages.put(queueUrl, new ArrayList<>());
        return CreateQueueResponse.builder().queueUrl(queueUrl).build();
    }

    @Override
    public GetQueueUrlResponse getQueueUrl(GetQueueUrlRequest getQueueUrlRequest) {
        if (!queueUrlsToMessages.containsKey(getQueueUrlRequest.queueName())) {
            throw QueueDoesNotExistException.builder().message(getQueueUrlRequest.queueName()).build();
        }
        return GetQueueUrlResponse.builder().queueUrl(buildQueueUrl(getQueueUrlRequest.queueName())).build();
    }

    @Override
    public SendMessageResponse sendMessage(SendMessageRequest request) {
        String messageId = RupPersistUtils.generateUuid();
        Message message = Message.builder()
            .body(request.messageBody())
            .messageAttributes(request.messageAttributes())
            .messageId(messageId)
            .build();
        synchronized (queueUrlsToMessages) {
            queueUrlsToMessages.get(request.queueUrl()).add(message);
            actualSendMessageRequests.add(request);
        }
        return SendMessageResponse.builder().messageId(messageId).build();
    }

    /**
     * The client is a poll based consumer, so messages will be returned the next time the specified queue is polled.
     * If successful, the client deletes the message with DeleteMessage so that the message will be returned once.
     *
     * @link http://camel.apache.org/polling-consumer.html
     * @link https://docs.aws.amazon.com/AWSSimpleQueueService/latest/APIReference/API_DeleteMessage.html
     * @see org.apache.camel.support.ScheduledPollConsumer
     */
    @Override
    public ReceiveMessageResponse receiveMessage(ReceiveMessageRequest request) {
        int maxNumberOfMessages = Objects.nonNull(request.maxNumberOfMessages())
            ? request.maxNumberOfMessages()
            : Integer.MAX_VALUE;
        return ReceiveMessageResponse.builder()
            .messages(getMessages(request, maxNumberOfMessages))
            .build();
    }

    @Override
    public DeleteMessageResponse deleteMessage(DeleteMessageRequest request) {
        return DeleteMessageResponse.builder().build();
    }

    public void sendMessage(String queueName, String body, Map<String, MessageAttributeValue> attributes) {
        Message message = Message.builder()
            .body(body)
            .messageAttributes(attributes)
            .messageId(RupPersistUtils.generateUuid())
            .build();
        synchronized (queueUrlsToMessages) {
            queueUrlsToMessages.get(buildQueueUrl(queueName)).add(message);
        }
    }

    public void reset() {
        queueUrlsToMessages.values().forEach(List::clear);
        actualSendMessageRequests.clear();
    }

    /**
     * Asserts that messages have been sent to the specified queue.
     *
     * @param queueName        queue name
     * @param expectedMessages expected messages
     * @param excludedFields   excluded fields
     * @param expectedHeaders  expected headers
     */
    public void assertSentMessages(String queueName, List<String> expectedMessages, List<String> excludedFields,
                                   Map<String, String> expectedHeaders) {
        List<SendMessageRequest> sendMessageRequests = actualSendMessageRequests.stream()
            .filter(request -> request.queueUrl().equals(buildQueueUrl(queueName)))
            .collect(Collectors.toList());
        assertEquals(expectedMessages.size(), sendMessageRequests.size());
        List<JsonMatcher> actualMessageMatchers = new ArrayList<>(expectedMessages.size());
        sendMessageRequests.forEach(sendMessageRequest -> {
            expectedHeaders.forEach((headerName, headerValue) -> {
                assertTrue(sendMessageRequest.messageAttributes().containsKey(headerName));
                assertEquals(headerValue, sendMessageRequest.messageAttributes().get(headerName).stringValue());
            });
            actualMessageMatchers.add(new JsonMatcher(sendMessageRequest.messageBody(), excludedFields));
        });
        expectedMessages.forEach(expectedMessage -> assertTrue(String.format("Expected sent message not found. " +
                    "ExpectedMessage=%s, SendMessageRequests=%s, ExcludedFields=%s",
                expectedMessage, sendMessageRequests, excludedFields),
            actualMessageMatchers.stream().anyMatch(matcher -> matcher.matches(expectedMessage)))
        );
    }

    /**
     * Asserts that messages have been received and removed from the specified queue.
     *
     * @param queueName queue name
     */
    public void assertReceivedMessages(String queueName) {
        assertEquals("Expected queue is empty", 0, CollectionUtils.size(queueUrlsToMessages.get(queueName)));
    }

    public Map<String, List<Message>> getQueueUrlsToMessages() {
        return queueUrlsToMessages;
    }

    public List<SendMessageRequest> getActualSendMessageRequests() {
        return actualSendMessageRequests;
    }

    private String buildQueueUrl(String queueName) {
        return "mock.sqs.aws.com/" + queueName;
    }

    private Collection<Message> getMessages(ReceiveMessageRequest request, int maxNumberOfMessages) {
        Collection<Message> resultMessages = new ArrayList<>();
        synchronized (queueUrlsToMessages) {
            int fetchSize = 0;
            List<Message> messages = queueUrlsToMessages.get(request.queueUrl());
            for (Iterator<Message> iterator = messages.iterator();
                 iterator.hasNext() && fetchSize < maxNumberOfMessages;
                 fetchSize++) {
                resultMessages.add(iterator.next());
                iterator.remove();
            }
        }
        return resultMessages;
    }
}
