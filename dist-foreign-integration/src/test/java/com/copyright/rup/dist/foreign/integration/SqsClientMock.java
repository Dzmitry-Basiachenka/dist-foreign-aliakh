package com.copyright.rup.dist.foreign.integration;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;

/**
 * Mock class for {@link AmazonSQSClient} to avoid requests to AWS services.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/11/2019
 *
 * @author Pavel Liakh
 */
public class SqsClientMock extends AmazonSQSClient {

    @Override
    public ListQueuesResult listQueues(ListQueuesRequest request) {
        return new ListQueuesResult().withQueueUrls("aws.sqs.com/fda-test-sf-detail.fifo");
    }
}
