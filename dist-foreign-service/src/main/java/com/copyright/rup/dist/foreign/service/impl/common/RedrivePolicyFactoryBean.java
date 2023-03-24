package com.copyright.rup.dist.foreign.service.impl.common;

import com.copyright.rup.common.exception.RupRuntimeException;

import org.apache.camel.component.aws2.sqs.Sqs2Configuration;
import org.apache.camel.component.aws2.sqs.Sqs2Endpoint;
import org.springframework.beans.factory.FactoryBean;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesRequest;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;

/**
 * Factory bean that produces AWS SQS Redrive Policy.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 07/18/2019
 *
 * @author Ihar Suvorau
 */
public class RedrivePolicyFactoryBean implements FactoryBean<String> {

    private static final String REDRIVE_POLICY_TEMPLATE = "{\"maxReceiveCount\":\"%s\",\"deadLetterTargetArn\":\"%s\"}";

    private Sqs2Endpoint sqsEndpoint;
    private int maxReceiveCount;

    @Override
    public String getObject() {
        if (!sqsEndpoint.isStarted()) {
            try {
                sqsEndpoint.start();
            } catch (Exception e) {
                throw new RupRuntimeException("Exception during SQS endpoint startup. Endpoint=" + sqsEndpoint, e);
            }
        }
        Sqs2Configuration configuration = sqsEndpoint.getConfiguration();
        SqsClient sqsClient = configuration.getAmazonSQSClient();
        String queueUrl = configuration.getQueueName();
        return String.format(REDRIVE_POLICY_TEMPLATE, maxReceiveCount, findQueueArn(sqsClient, queueUrl));
    }

    @Override
    public Class<?> getObjectType() {
        return String.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public void setSqsEndpoint(Sqs2Endpoint sqsEndpoint) {
        this.sqsEndpoint = sqsEndpoint;
    }

    public void setMaxReceiveCount(int maxReceiveCount) {
        this.maxReceiveCount = maxReceiveCount;
    }

    private String findQueueArn(SqsClient client, String queueUrl) {
        return client.getQueueAttributes(
            GetQueueAttributesRequest.builder()
                .queueUrl(queueUrl)
                .attributeNames(QueueAttributeName.QUEUE_ARN).build()).attributes().get(QueueAttributeName.QUEUE_ARN);
    }
}
