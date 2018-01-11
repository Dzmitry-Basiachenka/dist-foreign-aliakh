package com.copyright.rup.dist.foreign.integration.lm.impl.producer;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.LiabilityDetail;
import com.copyright.rup.dist.foreign.integration.lm.impl.domain.LiabilityDetailMessage;

import com.google.common.collect.ImmutableMap;

import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Verifies {@link LiabilityDetailProducer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
public class LiabilityDetailProducerTest {

    private static final String END_POINT = "test";
    private static final Map<String, Object> HEADERS = ImmutableMap.of("source", "FDA");

    private ProducerTemplate template;
    private LiabilityDetailProducer producer;

    @Before
    public void setUp() {
        template = createMock(ProducerTemplate.class);
        producer = new LiabilityDetailProducer();
        producer.setProducerTemplate(template);
        producer.setEndPoint(END_POINT);
    }

    @Test
    public void testSendMessage() {
        List<LiabilityDetail> liabilityDetails = Collections.singletonList(new LiabilityDetail());
        template.sendBodyAndHeaders(END_POINT, liabilityDetails, HEADERS);
        expectLastCall().once();
        replay(template);
        producer.send(new LiabilityDetailMessage(HEADERS, liabilityDetails));
        verify(template);
    }
}
