package com.copyright.rup.dist.foreign.integration.lm.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsageMessage;
import com.copyright.rup.dist.foreign.integration.lm.impl.producer.ExternalUsageProducer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Map;

/**
 * Verifies {@link LmIntegrationService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
public class LmIntegrationServiceTest {

    private static final Map<String, Object> HEADER = ImmutableMap.of("source", "FDA");
    private ILmIntegrationService lmIntegrationService;
    private ExternalUsageProducer externalUsageProducer;
    private ExternalUsage externalUsage;

    @Before
    public void setUp() {
        lmIntegrationService = new LmIntegrationService();
        externalUsageProducer = createMock(ExternalUsageProducer.class);
        Whitebox.setInternalState(lmIntegrationService, "batchSize", 2);
        Whitebox.setInternalState(lmIntegrationService, "externalUsageProducer", externalUsageProducer);
        externalUsage = new ExternalUsage(new Usage());
    }


    @Test
    public void testSendToLmSingleMessage() {
        externalUsageProducer.send(new ExternalUsageMessage(HEADER, Lists.newArrayList(externalUsage, externalUsage)));
        expectLastCall().once();
        replay(externalUsageProducer);
        lmIntegrationService.sendToLm(Lists.newArrayList(externalUsage, externalUsage));
        verify(externalUsageProducer);
    }

    @Test
    public void testSendToLmNotSingleMessages() {
        externalUsageProducer.send(new ExternalUsageMessage(HEADER, Lists.newArrayList(externalUsage, externalUsage)));
        expectLastCall().once();
        externalUsageProducer.send(new ExternalUsageMessage(HEADER, Lists.newArrayList(externalUsage)));
        expectLastCall().once();
        replay(externalUsageProducer);
        lmIntegrationService.sendToLm(Lists.newArrayList(externalUsage, externalUsage, externalUsage));
        verify(externalUsageProducer);
    }
}
