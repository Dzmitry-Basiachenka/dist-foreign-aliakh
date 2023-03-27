package com.copyright.rup.dist.foreign.service.impl.usage.paid;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.logging.RupLogUtils;

import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.conditions.ConditionFactory;

import org.apache.camel.Endpoint;
import org.apache.camel.component.aws2.sns.Sns2Configuration;
import org.apache.camel.component.aws2.sns.Sns2Endpoint;
import org.apache.camel.component.aws2.sqs.Sqs2Configuration;
import org.apache.camel.component.aws2.sqs.Sqs2Endpoint;
import org.slf4j.Logger;

import java.util.Map;

import javax.annotation.PostConstruct;

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
 * Subscribes paid usages SQS queue to paid information SNS topic.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/17/2019
 *
 * @author Ihar Suvorau
 */
public class PaidUsageSubscriber {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    private final Sns2Endpoint topicEndpoint;
    private final Sqs2Endpoint queueEndpoint;
    private final String source;

    /**
     * Constructor.
     *
     * @param topicEndpoint  topic endpoint to subscribe
     * @param queueEndpoint  queue endpoint that will be subscribed
     * @param filteredSource source to filter messages
     */
    public PaidUsageSubscriber(Sns2Endpoint topicEndpoint, Sqs2Endpoint queueEndpoint, String filteredSource) {
        this.topicEndpoint = topicEndpoint;
        this.queueEndpoint = queueEndpoint;
        this.source = filteredSource;
    }

    /**
     * Subscribes SQS queue to SNS topic.
     */
    @PostConstruct
    public void subscribeQueue() {
        LOGGER.info("Subscribe SQS queue to SNS topic. Started. TopicEndpoint={}, QueueEndpoint={}, Source={}",
            topicEndpoint, queueEndpoint, source);
        startEndpoint(queueEndpoint);
        startEndpoint(topicEndpoint);
        Sqs2Configuration sqsConfiguration = queueEndpoint.getConfiguration();
        SqsClient sqsClient = sqsConfiguration.getAmazonSQSClient();
        Sns2Configuration snsConfiguration = topicEndpoint.getConfiguration();
        SnsClient snsClient = snsConfiguration.getAmazonSNSClient();
        String queueUrl = sqsConfiguration.getQueueName();
        LOGGER.info("Queue URL: {}", queueUrl);
        GetQueueAttributesResponse queueAttrResponse = getQueueAttributes(sqsClient, queueUrl);
        String queueArn = queueAttrResponse.attributes().get(QueueAttributeName.QUEUE_ARN);
        LOGGER.info("Queue ARN: {}", queueArn);
        String topicArn = snsConfiguration.getTopicArn();
        LOGGER.info("Topic ARN: {}", topicArn);
        SubscribeResponse subscribeResponse = subscribeQueueToTopic(snsClient, queueArn, topicArn);
        String subscriptionArn = subscribeResponse.subscriptionArn();
        LOGGER.info("Subscription ARN: {}", subscriptionArn);
        setSubscriptionAttributes(snsClient, subscriptionArn);
        setQueueAttributes(sqsClient, queueUrl, queueArn, topicArn);
        LOGGER.info("Subscribe SQS queue to SNS topic. Finished. TopicEndpoint={}, QueueEndpoint={}, Source={}",
            topicEndpoint, queueEndpoint, source);
    }

    private void startEndpoint(Endpoint endpoint) {
        try {
            endpoint.start();
        } catch (Exception e) {
            throw new RupRuntimeException("Exception on endpoint startup. Endpoint=" + endpoint, e);
        }
    }

    private GetQueueAttributesResponse getQueueAttributes(SqsClient sqsClient, String queueUrl) {
        GetQueueAttributesRequest queueAttrRequest = GetQueueAttributesRequest.builder()
            .queueUrl(queueUrl)
            .attributeNames(QueueAttributeName.QUEUE_ARN, QueueAttributeName.POLICY)
            .build();
        LOGGER.info("Get queue attributes request: {}", queueAttrRequest);
        GetQueueAttributesResponse queueAttrResponse = sqsClient.getQueueAttributes(queueAttrRequest);
        LOGGER.info("Get queue attributes response: {}", queueAttrResponse);
        return queueAttrResponse;
    }

    private SubscribeResponse subscribeQueueToTopic(SnsClient snsClient, String queueArn, String topicArn) {
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
            .topicArn(topicArn)
            .endpoint(queueArn)
            .protocol("sqs")
            .build();
        LOGGER.info("Subscribe request: {}", subscribeRequest);
        SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);
        LOGGER.info("Subscribe response: {}", subscribeResponse);
        return subscribeResponse;
    }

    private void setSubscriptionAttributes(SnsClient snsClient, String subscriptionArn) {
        SetSubscriptionAttributesRequest subscriptionAttrRequest = SetSubscriptionAttributesRequest.builder()
            .subscriptionArn(subscriptionArn)
            .attributeName("FilterPolicy")
            .attributeValue(String.format("{\"source\":[\"%s\"]}", source))
            .build();
        LOGGER.info("Set subscription attributes request: {}", subscriptionAttrRequest);
        SetSubscriptionAttributesResponse subscriptionAttrResponse =
            snsClient.setSubscriptionAttributes(subscriptionAttrRequest);
        LOGGER.info("Set subscription attributes response: {}", subscriptionAttrResponse);
    }

    private void setQueueAttributes(SqsClient sqsClient, String queueUrl, String queueArn, String topicArn) {
        Policy policy = new Policy();
        policy.getStatements().add(new Statement(Statement.Effect.Allow)
            .withId("topic-subscription-" + topicArn)
            .withPrincipals(Principal.AllUsers)
            .withActions(() -> "sqs:SendMessage")
            .withResources(new Resource(queueArn))
            .withConditions(ConditionFactory.newSourceArnCondition(topicArn)));
        String policyJson = policy.toJson();
        LOGGER.info("Queue policy: {}", policyJson);
        SetQueueAttributesRequest setQueueAttrRequest = SetQueueAttributesRequest.builder()
            .queueUrl(queueUrl)
            .attributes(Map.of(QueueAttributeName.POLICY, policyJson))
            .build();
        LOGGER.info("Set queue attributes request: {}", setQueueAttrRequest);
        SetQueueAttributesResponse setQueueAttrResponse = sqsClient.setQueueAttributes(setQueueAttrRequest);
        LOGGER.info("Set queue attributes response: {}", setQueueAttrResponse);
    }
}
