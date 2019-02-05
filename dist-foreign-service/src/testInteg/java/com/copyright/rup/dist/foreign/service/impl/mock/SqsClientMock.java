package com.copyright.rup.dist.foreign.service.impl.mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.JsonMatcher;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Mock implementation of {@link AmazonSQSClient} for integration tests.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 02/02/2018
 *
 * @author Aliaksandr Liakh
 */
public class SqsClientMock extends AmazonSQSClient {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    private final Map<String, List<Message>> queueUrlsToMessages = new HashMap<>();
    private final List<SendMessageRequest> sendMessageRequests = new ArrayList<>();

    /**
     * Resets the class between tests.
     */
    public void reset() {
        sendMessageRequests.clear();
    }

    @Override
    public ListQueuesResult listQueues(ListQueuesRequest request) {
        Set<String> queueUrls = queueUrlsToMessages.keySet();
        return new ListQueuesResult().withQueueUrls(queueUrls);
    }

    @Override
    public GetQueueUrlResult getQueueUrl(String queueName) {
        return new GetQueueUrlResult().withQueueUrl(buildQueueUrl(queueName));
    }

    @Override
    public CreateQueueResult createQueue(CreateQueueRequest request) {
        String queueUrl = buildQueueUrl(request.getQueueName());
        queueUrlsToMessages.put(queueUrl, new ArrayList<>());
        return new CreateQueueResult().withQueueUrl(queueUrl);
    }

    @Override
    public SendMessageResult sendMessage(SendMessageRequest request) {
        LOGGER.debug("Send message. SendMessageRequest={}", request);
        String messageId = RupPersistUtils.generateUuid();
        Message message = new Message();
        message.setBody(request.getMessageBody());
//      message.setAttributes(request.getMessageAttributes()); // TODO {aliakh} process String attributes only
        message.setMessageId(messageId);
        synchronized (queueUrlsToMessages) {
            queueUrlsToMessages.get(request.getQueueUrl()).add(message);
            sendMessageRequests.add(request);
        }
        SendMessageResult result = new SendMessageResult();
        result.setMessageId(messageId);
        return result;
    }

    @Override
    public ReceiveMessageResult receiveMessage(ReceiveMessageRequest request) {
        LOGGER.trace("Receive message. ReceiveMessageRequest={}", request);
        Integer maxNumberOfMessages = request.getMaxNumberOfMessages() != null
            ? request.getMaxNumberOfMessages()
            : Integer.MAX_VALUE;
        ReceiveMessageResult result = new ReceiveMessageResult();
        Collection<Message> resultMessages = new ArrayList<Message>();
        synchronized (queueUrlsToMessages) {
            int fetchSize = 0;
            List<Message> messages = queueUrlsToMessages.get(request.getQueueUrl());
            for (Iterator<Message> iterator = messages.iterator();
                 iterator.hasNext() && fetchSize < maxNumberOfMessages;
                 fetchSize++) {
                resultMessages.add(iterator.next());
                iterator.remove();
            }
        }
        result.setMessages(resultMessages);
        return result;
    }

    @Override
    public DeleteMessageResult deleteMessage(DeleteMessageRequest request) {
        LOGGER.debug("Delete message. DeleteMessageRequest={}", request);
        return new DeleteMessageResult();
    }

    /**
     * Sends message to the queue.
     *
     * @param queueName  the queue name
     * @param body       the message body
     * @param attributes the message attributes
     */
    public void sendMessage(String queueName, String body, Map<String, String> attributes) {
        Message message = new Message();
        message.setBody(body);
        message.setAttributes(attributes);
        message.setMessageId(RupPersistUtils.generateUuid());
        synchronized (queueUrlsToMessages) {
            queueUrlsToMessages.get(buildQueueUrl(queueName)).add(message);
        }
    }

    /**
     * Gets list of current {@link Message}s for the queue.
     *
     * @param queueName the queue name
     * @return the list of {@link Message}
     */
    public List<Message> getCurrentMessages(String queueName) {
        synchronized (queueUrlsToMessages) {
            return queueUrlsToMessages.get(buildQueueUrl(queueName));
        }
    }

    /**
     * Gets list of {@link SendMessageRequest}.
     *
     * @return the list of {@link SendMessageRequest}
     */
    public List<SendMessageRequest> getSendMessageRequests() {
        synchronized (queueUrlsToMessages) {
            return sendMessageRequests;
        }
    }

    /**
     * Asserts the content of actual {@link SendMessageRequest} with required values.
     *
     * @param request        the actual {@link SendMessageRequest}
     * @param queueName      the queue name
     * @param messages       the list of messages
     * @param excludedFields the list of excluded JSON fields
     * @param headers        the list of headers
     */
    public void assertSendMessageRequest(SendMessageRequest request,
                                         String queueName, List<String> messages, List<String> excludedFields,
                                         Map<String, String> headers) {
        assertEquals(buildQueueUrl(queueName), request.getQueueUrl());
        headers.forEach((headerName, headerValue) -> {
            assertTrue(request.getMessageAttributes().containsKey(headerName));
            assertEquals(headerValue, request.getMessageAttributes().get(headerName).getStringValue());
        });
        JsonMatcher matcher = new JsonMatcher(request.getMessageBody(), excludedFields);
        messages.forEach(matcher::matches);
    }

    private String buildQueueUrl(String queueName) {
        return "mock.sqs.aws.com/" + queueName;
    }
}
