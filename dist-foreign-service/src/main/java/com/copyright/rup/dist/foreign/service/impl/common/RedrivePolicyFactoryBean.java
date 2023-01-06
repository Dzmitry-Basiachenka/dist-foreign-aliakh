package com.copyright.rup.dist.foreign.service.impl.common;

import com.copyright.rup.common.exception.RupRuntimeException;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;

import org.apache.camel.component.aws.sqs.SqsConfiguration;
import org.apache.camel.component.aws.sqs.SqsEndpoint;
import org.springframework.beans.factory.FactoryBean;

import java.util.List;

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

    private static final String QUEUE_ARN_ATTRIBUTE = "QueueArn";
    private static final String REDRIVE_POLICY_TEMPLATE = "{\"maxReceiveCount\":\"%s\",\"deadLetterTargetArn\":\"%s\"}";

    private SqsEndpoint sqsEndpoint;
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
        SqsConfiguration configuration = sqsEndpoint.getConfiguration();
        AmazonSQS sqsClient = configuration.getAmazonSQSClient();
        String queueUrl = sqsClient.getQueueUrl(configuration.getQueueName()).getQueueUrl();
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

    public void setSqsEndpoint(SqsEndpoint sqsEndpoint) {
        this.sqsEndpoint = sqsEndpoint;
    }

    public void setMaxReceiveCount(int maxReceiveCount) {
        this.maxReceiveCount = maxReceiveCount;
    }

    private String findQueueArn(AmazonSQS client, String queueUrl) {
        GetQueueAttributesResult result = client.getQueueAttributes(queueUrl, List.of(QUEUE_ARN_ATTRIBUTE));
        return result.getAttributes().get(QUEUE_ARN_ATTRIBUTE);
    }
}
