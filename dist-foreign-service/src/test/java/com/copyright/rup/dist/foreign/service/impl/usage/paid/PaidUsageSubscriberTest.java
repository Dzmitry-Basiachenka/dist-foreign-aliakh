package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.SetSubscriptionAttributesResult;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;

import org.apache.camel.component.aws.sns.SnsConfiguration;
import org.apache.camel.component.aws.sns.SnsEndpoint;
import org.apache.camel.component.aws.sqs.SqsConfiguration;
import org.apache.camel.component.aws.sqs.SqsEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link PaidUsageSubscriber}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/17/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Topics.class)
public class PaidUsageSubscriberTest {

    private static final String QUEUE_URL = "test queue";
    private static final String TOPIC_ARN = "test arn";
    private static final String SUBSCRIPTION_ARN = "subscribe arn";
    private PaidUsageSubscriber subscriber;
    private SqsEndpoint queueEndpoint;
    private SnsEndpoint topicEndpoint;
    private AmazonSQSClient sqsClient;
    private AmazonSNSClient snsClient;

    @Before
    public void setUp() {
        sqsClient = createMock(AmazonSQSClient.class);
        snsClient = createMock(AmazonSNSClient.class);
        queueEndpoint = createMock(SqsEndpoint.class);
        topicEndpoint = createMock(SnsEndpoint.class);
        subscriber = new PaidUsageSubscriber(topicEndpoint, queueEndpoint);
    }

    @Test
    public void testSubscribeQueue() throws Exception {
        mockStatic(Topics.class);
        queueEndpoint.start();
        expectLastCall().once();
        topicEndpoint.start();
        expectLastCall().once();
        SqsConfiguration sqsConfiguration = new SqsConfiguration();
        sqsConfiguration.setQueueName(QUEUE_URL);
        sqsConfiguration.setAmazonSQSClient(sqsClient);
        expect(queueEndpoint.getConfiguration()).andReturn(sqsConfiguration).once();
        SnsConfiguration snsConfiguration = new SnsConfiguration();
        snsConfiguration.setTopicArn(TOPIC_ARN);
        snsConfiguration.setAmazonSNSClient(snsClient);
        expect(topicEndpoint.getConfiguration()).andReturn(snsConfiguration).once();
        expect(sqsClient.getQueueUrl(QUEUE_URL)).andReturn(new GetQueueUrlResult().withQueueUrl(QUEUE_URL)).once();
        expect(Topics.subscribeQueue(snsClient, sqsClient, TOPIC_ARN, QUEUE_URL)).andReturn(SUBSCRIPTION_ARN).once();
        expect(snsClient.setSubscriptionAttributes(SUBSCRIPTION_ARN, "FilterPolicy", "{\"source\":[\"FDA\"]}"))
            .andReturn(new SetSubscriptionAttributesResult()).once();
        replay(queueEndpoint, topicEndpoint, sqsClient, snsClient, Topics.class);
        subscriber.subscribeQueue();
        verify(queueEndpoint, topicEndpoint, sqsClient, snsClient, Topics.class);
    }
}
