package com.copyright.rup.dist.foreign.service.impl.common.chunk;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Verifies {@link ChunkUriFormatter}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/29/2020
 *
 * @author Aliaksandr Liakh
 */
public class ChunkUriFormatterTest {

    @Test
    public void testDirectEndpointUri() {
        assertEquals("direct:df.tax-chunk", new ChunkUriFormatter().format("direct:df.tax"));
    }

    @Test
    public void testAwsSqsEndpointUri() {
        assertEquals("aws-sqs://fda-pdev-df-tax-chunk?amazonSQSClient=#dist.common.sqs.client",
            new ChunkUriFormatter().format("aws-sqs://fda-pdev-df-tax?amazonSQSClient=#dist.common.sqs.client"));
    }
}
