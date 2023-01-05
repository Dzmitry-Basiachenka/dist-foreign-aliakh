package com.copyright.rup.dist.foreign.service.impl.common;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;

import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Verifies {@link CommonUsageProducer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class CommonUsageProducerTest {

    private static final String END_POINT = "test";
    private static final String PRODUCT_FAMILY = "FAS";

    private ProducerTemplate template;
    private CommonUsageProducer producer;

    @Before
    public void setUp() {
        template = createMock(ProducerTemplate.class);
        producer = new CommonUsageProducer();
        producer.setEndPoint(END_POINT);
        Whitebox.setInternalState(producer, template);
    }

    @Test
    public void testSendMessage() {
        Usage usage = new Usage();
        usage.setProductFamily(PRODUCT_FAMILY);
        List<Usage> usages = List.of(usage);
        Map<String, Object> headers = new HashMap<>();
        headers.put("productFamily", PRODUCT_FAMILY);
        template.sendBodyAndHeaders(END_POINT, usages, headers);
        expectLastCall().once();
        replay(template);
        producer.send(usages);
        verify(template);
    }
}
