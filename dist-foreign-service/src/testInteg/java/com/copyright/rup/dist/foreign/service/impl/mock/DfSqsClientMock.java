package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Mock class for {@link com.amazonaws.services.sns.AmazonSNSClient} to avoid requests to AWS services.
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/21/2019
 *
 * @author Pavel Liakh
 */
public class DfSqsClientMock extends SqsClientMock {

    // https://docs.aws.amazon.com/sns/latest/dg/sns-message-and-json-formats.html
    private static final String SNS_NOTIFICATION_MESSAGE_FORMAT = "{" +
        "  \"Type\" : \"Notification\"," +
        "  \"MessageId\" : \"%s\"," +
        "  \"TopicArn\" : \"arn:aws:sns:us-east-1:10101010101:lm-pdev-sf-detail-paid\"," +
        "  \"Message\" : \"%s\"," +
        "  \"Timestamp\" : \"%s\"," +
        "  \"SignatureVersion\" : \"1\"," +
        "  \"Signature\" : \"signature\"," +
        "  \"SigningCertURL\" : \"signatureUrl\"," +
        "  \"UnsubscribeURL\" : \"unsubscribeUrl\"," +
        "  \"MessageAttributes\" : {" +
        "    \"breadcrumbId\" : {\"Type\":\"String\",\"Value\":\"breadcrumbId\"}," +
        "    \"source\" : {\"Type\":\"String\",\"Value\":\"FDA\"}" +
        "  }" +
        "}";

    @Override
    public void prepareReceivedMessage(String queueName, String body, Map<String, String> headers) {
        super.prepareReceivedMessage(queueName,
            String.format(SNS_NOTIFICATION_MESSAGE_FORMAT, RupPersistUtils.generateUuid(),
                body.replace("\n", "").replace("\"", "\\\""), OffsetDateTime.now().toString()),
            headers);
    }
}
