package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;

import org.apache.camel.component.aws.sns.SnsConfiguration;
import org.apache.camel.component.aws.sns.SnsEndpoint;
import org.apache.camel.component.aws.sqs.SqsConfiguration;
import org.apache.camel.component.aws.sqs.SqsEndpoint;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;

/**
 * Subscribes paid usages SQS queue to paid information SNS topic.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/17/2019
 *
 * @author Ihar Suvorau
 */
public class PaidUsageSubscriber {

    private static final String FILTER_POLICY_ATTRIBUTE = "FilterPolicy";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    private final SnsEndpoint topicEndpoint;
    private final SqsEndpoint queueEndpoint;

    /**
     * Constructor.
     *
     * @param topicEndpoint topic endpoint to subscribe
     * @param queueEndpoint queue endpoint that will be subscribed
     */
    public PaidUsageSubscriber(SnsEndpoint topicEndpoint, SqsEndpoint queueEndpoint) {
        this.topicEndpoint = topicEndpoint;
        this.queueEndpoint = queueEndpoint;
    }

    /**
     * Subscribes SQS queue to SNS topic.
     */
    @PostConstruct
    public void subscribeQueue() {
        LOGGER.info("Subscribe paid usage queue to topic. Started.");
        try {
            queueEndpoint.start();
        } catch (Exception e) {
            throw new RupRuntimeException("Exception on queue endpoint startup. QueueEndpoint=" + queueEndpoint, e);
        }
        try {
            topicEndpoint.start();
        } catch (Exception e) {
            throw new RupRuntimeException("Exception on topic endpoint startup. TopicEndpoint=" + topicEndpoint, e);
        }
        SqsConfiguration sqsConfiguration = queueEndpoint.getConfiguration();
        AmazonSQS sqsClient = sqsConfiguration.getAmazonSQSClient();
        SnsConfiguration snsConfiguration = topicEndpoint.getConfiguration();
        AmazonSNS snsClient = snsConfiguration.getAmazonSNSClient();
        String topicArn = snsConfiguration.getTopicArn();
        String queueUrl = sqsClient.getQueueUrl(sqsConfiguration.getQueueName()).getQueueUrl();
        String subscriptionArn = Topics.subscribeQueue(snsClient, sqsClient, topicArn, queueUrl);
        snsClient.setSubscriptionAttributes(subscriptionArn, FILTER_POLICY_ATTRIBUTE, "{\"source\":[\"FDA\"]}");
        LOGGER.info("Subscribe paid usage queue to topic. Finished. QueueUrl={}, TopicArn={}, SubscriptionArn={}",
            queueUrl, topicArn, subscriptionArn);
    }
}
