package com.copyright.rup.dist.foreign.integration.lm.impl.producer;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageMessage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageWrapper;

import com.google.common.collect.ImmutableMap;

import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Verifies {@link ExternalUsageProducer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
public class ExternalUsageProducerTest {

    private static final String END_POINT = "test";
    private static final Map<String, Object> HEADERS = ImmutableMap.of("source", "FDA");

    private ProducerTemplate template;
    private ExternalUsageProducer producer;

    @Before
    public void setUp() {
        template = createMock(ProducerTemplate.class);
        producer = new ExternalUsageProducer();
        producer.setProducerTemplate(template);
        producer.setEndPoint(END_POINT);
    }

    @Test
    public void testSendMessage() {
        List<ExternalUsage> externalUsages = List.of(new ExternalUsage(new Usage()));
        template.sendBodyAndHeaders(END_POINT, new ExternalUsageWrapper(externalUsages), HEADERS);
        expectLastCall().once();
        replay(template);
        producer.send(new ExternalUsageMessage(HEADERS, externalUsages));
        verify(template);
    }
}
