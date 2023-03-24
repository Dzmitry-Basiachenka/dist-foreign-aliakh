package com.copyright.rup.dist.foreign.integration.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.JsonMatcher;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.ListTopicsRequest;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.Topic;

/**
 * Mock class for {@link SnsClient} to avoid requests to AWS services.
 * See com.amazonaws.services.sns.AmazonSNS
 * See org.apache.camel.component.aws.sns.SnsEndpoint
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/11/2019
 *
 * @author Pavel Liakh
 */
public class SnsClientMock implements SnsClient {

    // https://docs.aws.amazon.com/sns/latest/dg/sns-message-and-json-formats.html
    private static final String SNS_NOTIFICATION_MESSAGE_FORMAT = "{" +
        "\"Type\":\"Notification\"," +
        "\"MessageId\":\"%s\"," +
        "\"TopicArn\":\"arn:aws:sns:us-east-1:10101010101:lm-test-dm-liability-paid\"," +
        "\"Message\":\"%s\"," +
        "\"Timestamp\":\"%s\"," +
        "\"SignatureVersion\":\"1\"," +
        "\"Signature\":\"signature\"," +
        "\"SigningCertURL\":\"signatureUrl\"," +
        "\"UnsubscribeURL\":\"unsubscribeUrl\"," +
        "\"MessageAttributes\":" +
        "{" +
        "\"breadcrumbId\":{\"Type\":\"String\",\"Value\":\"breadcrumbId\"}," +
        "\"source\":{\"Type\":\"String\",\"Value\":\"FDA\"}" +
        "}" +
        "}";

    private final List<PublishRequest> actualPublishedMessages = new ArrayList<>();
    private final List<Topic> existingTopics = new ArrayList<>();

    public static String wrapBody(String body) {
        return String.format(SNS_NOTIFICATION_MESSAGE_FORMAT,
            RupPersistUtils.generateUuid(),
            body.replace("\n", StringUtils.EMPTY).replace("\"", "\\\""),
            OffsetDateTime.now());
    }

    @Override
    public String serviceName() {
        return SERVICE_NAME;
    }

    @Override
    public void close() {
    }

    @Override
    public ListTopicsResponse listTopics(ListTopicsRequest listTopicsRequest) {
        return ListTopicsResponse.builder().topics(existingTopics).build();
    }

    public CreateTopicResponse createTopic(String name) {
        String topicArn = "arn:aws:iam::1010101010101:user:" + name;
        existingTopics.add(Topic.builder().topicArn(topicArn).build());
        return CreateTopicResponse.builder().topicArn(topicArn).build();
    }

    @Override
    public CreateTopicResponse createTopic(CreateTopicRequest request) {
        return createTopic(request.name());
    }

    @Override
    public PublishResponse publish(PublishRequest request) {
        actualPublishedMessages.add(request);
        return PublishResponse.builder().build();
    }

    public void reset() {
        actualPublishedMessages.clear();
    }

    /**
     * Asserts actual published messages as JSON.
     *
     * @param topicName        topic name
     * @param expectedMessages expected messages
     * @param excludedFields   excluded fields
     * @param expectedHeaders  expected headers
     */
    public void assertPublishedMessages(String topicName, List<String> expectedMessages,
                                        List<String> excludedFields, Map<String, String> expectedHeaders) {
        assertPublishedMessages(topicName, expectedMessages, null, expectedHeaders,
            json -> new JsonMatcher(json, excludedFields),
            "Expected sent messages not found. " +
                "ExpectedMessage=%s, ActualSendMessagesRequests=%s, ExcludedFields=" + excludedFields);
    }

    /**
     * Asserts actual published messages as text.
     *
     * @param topicName        topic name
     * @param expectedMessages expected messages
     * @param expectedSubject  expected subject
     * @param expectedHeaders  expected headers
     */
    public void assertPublishedMessagesAsText(String topicName, List<String> expectedMessages,
                                              String expectedSubject, Map<String, String> expectedHeaders) {
        assertPublishedMessages(topicName, expectedMessages, expectedSubject, expectedHeaders,
            IsEqual::new,
            "Expected sent messages not found. ExpectedMessage=%s, ActualSendMessagesRequests=%s");
    }

    private void assertPublishedMessages(String topicName, List<String> expectedMessages,
                                         String expectedSubject, Map<String, String> expectedHeaders,
                                         Function<String, Matcher<String>> matcherBuilder,
                                         String assertionMessageFormat) {
        String topicArn = createTopic(topicName).topicArn();
        List<PublishRequest> publishRequests = actualPublishedMessages.stream()
            .filter(message -> topicArn.equals(message.topicArn()))
            .collect(Collectors.toList());
        assertEquals(expectedMessages.size(), publishRequests.size());
        List<Matcher<String>> actualMessagesMatchers = new ArrayList<>();
        publishRequests.forEach(publishRequest -> {
            assertEquals(expectedSubject, publishRequest.subject());
            expectedHeaders.forEach((headerName, headerValue) -> {
                assertTrue(publishRequest.messageAttributes().containsKey(headerName));
                assertEquals(headerValue, publishRequest.messageAttributes().get(headerName).stringValue());
            });
            actualMessagesMatchers.add(matcherBuilder.apply(publishRequest.message()));
        });
        expectedMessages.forEach(expectedMessage -> assertTrue(
            String.format(assertionMessageFormat, expectedMessage, actualMessagesMatchers),
            actualMessagesMatchers.stream().anyMatch(matcher -> matcher.matches(expectedMessage))));
    }
}
