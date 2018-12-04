package com.copyright.rup.dist.foreign.service.impl.tax;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link RhTaxProducer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Uladzislau Shalamitski
 */
public class RhTaxProducerTest {

    private static final String END_POINT = "test";

    private ProducerTemplate template;
    private RhTaxProducer producer;

    @Before
    public void setUp() {
        template = createMock(ProducerTemplate.class);
        producer = new RhTaxProducer();
        producer.setProducerTemplate(template);
        producer.setEndPoint(END_POINT);
    }

    @Test
    public void testSendMessage() {
        Usage usage = new Usage();
        template.sendBody(END_POINT, usage);
        expectLastCall().once();
        replay(template);
        producer.send(usage);
        verify(template);
    }
}
