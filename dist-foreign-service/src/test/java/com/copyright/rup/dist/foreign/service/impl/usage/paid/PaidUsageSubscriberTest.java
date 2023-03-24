package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.apache.camel.component.aws2.sns.Sns2Configuration;
import org.apache.camel.component.aws2.sns.Sns2Endpoint;
import org.apache.camel.component.aws2.sqs.Sqs2Configuration;
import org.apache.camel.component.aws2.sqs.Sqs2Endpoint;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SetSubscriptionAttributesRequest;
import software.amazon.awssdk.services.sns.model.SetSubscriptionAttributesResponse;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesResponse;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;
import software.amazon.awssdk.services.sqs.model.SetQueueAttributesRequest;
import software.amazon.awssdk.services.sqs.model.SetQueueAttributesResponse;

/**
 * Verifies {@link PaidUsageSubscriber}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/17/2019
 *
 * @author Ihar Suvorau
 */
public class PaidUsageSubscriberTest {

    private static final String SOURCE = "FDA";
    private static final String QUEUE_URL = "queue";
    private static final String TOPIC_ARN = "arn:aws:sns:us-east-1:084467978520:topic";
    private static final String QUEUE_ARN = "arn:aws:sqs:us-east-1:084467978520:queue";
    private static final String SUBSCRIPTION_ARN =
        "arn:aws:sns:us-east-1:084467978520:topic:f88a155f-5b3f-4017-b458-4048a40c5ae8";
    private static final String POLICY_JSON = "{" +
        "\"Version\":\"2012-10-17\"," +
        "\"Statement\":" +
        "[" +
        "{\"Sid\":\"topic-subscription-arn:aws:sns:us-east-1:084467978520:topic\"," +
        "\"Effect\":\"Allow\"," +
        "\"Principal\":{\"AWS\":\"*\"}," +
        "\"Action\":[\"sqs:SendMessage\"]," +
        "\"Resource\":[\"arn:aws:sqs:us-east-1:084467978520:queue\"]," +
        "\"Condition\":{\"ArnLike\":{\"aws:SourceArn\":[\"arn:aws:sns:us-east-1:084467978520:topic\"]}}}" +
        "]" +
        "}";

    private SqsClient sqsClient;
    private SnsClient snsClient;
    private Sqs2Endpoint sqs2Endpoint;
    private Sns2Endpoint sns2Endpoint;
    private PaidUsageSubscriber subscriber;

    @Before
    public void setUp() {
        sqsClient = createMock(SqsClient.class);
        snsClient = createMock(SnsClient.class);
        sqs2Endpoint = createMock(Sqs2Endpoint.class);
        sns2Endpoint = createMock(Sns2Endpoint.class);
        subscriber = new PaidUsageSubscriber(sns2Endpoint, sqs2Endpoint, SOURCE);
    }

    @Test
    public void testSubscribeQueue() {
        sqs2Endpoint.start();
        expectLastCall().once();
        sns2Endpoint.start();
        expectLastCall().once();
        Sqs2Configuration sqsConfiguration = new Sqs2Configuration();
        sqsConfiguration.setQueueName(QUEUE_URL);
        sqsConfiguration.setAmazonSQSClient(sqsClient);
        expect(sqs2Endpoint.getConfiguration()).andReturn(sqsConfiguration).once();
        Sns2Configuration snsConfiguration = new Sns2Configuration();
        snsConfiguration.setTopicArn(TOPIC_ARN);
        snsConfiguration.setAmazonSNSClient(snsClient);
        expect(sns2Endpoint.getConfiguration()).andReturn(snsConfiguration).once();
        GetQueueAttributesRequest getQueueAttrRequest = GetQueueAttributesRequest.builder()
            .queueUrl(sqsConfiguration.getQueueName())
            .attributeNames(QueueAttributeName.QUEUE_ARN, QueueAttributeName.POLICY)
            .build();
        GetQueueAttributesResponse getQueueAttrResponse = GetQueueAttributesResponse.builder()
            .attributes(Map.of(QueueAttributeName.QUEUE_ARN, QUEUE_ARN, QueueAttributeName.POLICY, "{}"))
            .build();
        expect(sqsClient.getQueueAttributes(getQueueAttrRequest)).andReturn(getQueueAttrResponse).once();
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
            .topicArn(snsConfiguration.getTopicArn())
            .endpoint(QUEUE_ARN)
            .protocol("sqs")
            .build();
        SubscribeResponse subscribeResponse = SubscribeResponse.builder()
            .subscriptionArn(SUBSCRIPTION_ARN)
            .build();
        expect(snsClient.subscribe(subscribeRequest)).andReturn(subscribeResponse).once();
        SetSubscriptionAttributesRequest setSubscriptionAttrRequest = SetSubscriptionAttributesRequest.builder()
            .subscriptionArn(subscribeResponse.subscriptionArn())
            .attributeName("FilterPolicy")
            .attributeValue(String.format("{\"source\":[\"%s\"]}", SOURCE))
            .build();
        SetSubscriptionAttributesResponse setSubscriptionAttrResponse = SetSubscriptionAttributesResponse.builder()
            .build();
        expect(snsClient.setSubscriptionAttributes(setSubscriptionAttrRequest))
            .andReturn(setSubscriptionAttrResponse).once();
        SetQueueAttributesRequest setQueueAttrRequest = SetQueueAttributesRequest.builder()
            .queueUrl(sqsConfiguration.getQueueName())
            .attributes(Map.of(QueueAttributeName.POLICY, POLICY_JSON))
            .build();
        SetQueueAttributesResponse setQueueAttrResponse = SetQueueAttributesResponse.builder().build();
        expect(sqsClient.setQueueAttributes(setQueueAttrRequest)).andReturn(setQueueAttrResponse).once();
        replay(sqs2Endpoint, sns2Endpoint, sqsClient, snsClient);
        subscriber.subscribeQueue();
        verify(sqs2Endpoint, sns2Endpoint, sqsClient, snsClient);
    }
}
