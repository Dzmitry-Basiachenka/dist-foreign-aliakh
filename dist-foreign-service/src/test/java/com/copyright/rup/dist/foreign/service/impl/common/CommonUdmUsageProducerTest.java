package com.copyright.rup.dist.foreign.service.impl.common;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.UdmUsage;

import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link CommonUdmUsageProducer}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Ihar Suvorau
 */
public class CommonUdmUsageProducerTest {

    private static final String END_POINT = "test.endpoint";

    @Test
    public void testSendMessage() {
        ProducerTemplate template = createMock(ProducerTemplate.class);
        CommonUdmUsageProducer producer = new CommonUdmUsageProducer();
        producer.setEndPoint(END_POINT);
        Whitebox.setInternalState(producer, template);
        List<UdmUsage> usages = List.of(new UdmUsage());
        template.sendBody(END_POINT, usages);
        expectLastCall().once();
        replay(template);
        producer.send(usages);
        verify(template);
    }
}
