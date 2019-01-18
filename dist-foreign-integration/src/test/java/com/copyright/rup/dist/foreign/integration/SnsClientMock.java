package com.copyright.rup.dist.foreign.integration;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.ListTopicsResult;
import com.amazonaws.services.sns.model.Topic;

import java.util.Collections;
import java.util.List;

/**
 * Mock class for {@link com.amazonaws.services.sns.AmazonSNSClient} to avoid requests to AWS services.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/11/2019
 *
 * @author Pavel Liakh
 */
public class SnsClientMock extends AmazonSNSClient {

    private static final String AWS_SNS_ARN = "arn:aws:iam::1010101010101:user:";
    private static final List<Topic> EXISTING_TOPICS =
        Collections.singletonList(new Topic().withTopicArn(AWS_SNS_ARN + "fda-test-sf-detail-paid"));

    @Override
    public ListTopicsResult listTopics(String nextToken) {
        return new ListTopicsResult().withTopics(EXISTING_TOPICS);
    }
}
