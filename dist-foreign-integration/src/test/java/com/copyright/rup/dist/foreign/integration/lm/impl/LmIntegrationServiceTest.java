package com.copyright.rup.dist.foreign.integration.lm.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.LiabilityDetail;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.impl.domain.LiabilityDetailMessage;
import com.copyright.rup.dist.foreign.integration.lm.impl.producer.LiabilityDetailProducer;

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
    private LiabilityDetailProducer liabilityDetailProducer;

    @Before
    public void setUp() {
        lmIntegrationService = new LmIntegrationService();
        liabilityDetailProducer = createMock(LiabilityDetailProducer.class);
        Whitebox.setInternalState(lmIntegrationService, "batchSize", 2);
        Whitebox.setInternalState(lmIntegrationService, "liabilityDetailProducer", liabilityDetailProducer);
    }

    @Test
    public void testSendToLmSingleMessage() {
        liabilityDetailProducer.send(
            new LiabilityDetailMessage(HEADER, Lists.newArrayList(new LiabilityDetail(), new LiabilityDetail())));
        expectLastCall().once();
        replay(liabilityDetailProducer);
        lmIntegrationService.sendToLm(
            Lists.newArrayList(new LiabilityDetail(), new LiabilityDetail()));
        verify(liabilityDetailProducer);
    }

    @Test
    public void testSendToLmNotSingleMessages() {
        liabilityDetailProducer.send(
            new LiabilityDetailMessage(HEADER, Lists.newArrayList(new LiabilityDetail(), new LiabilityDetail())));
        expectLastCall().once();
        liabilityDetailProducer.send(
            new LiabilityDetailMessage(HEADER, Lists.newArrayList(new LiabilityDetail())));
        expectLastCall().once();
        replay(liabilityDetailProducer);
        lmIntegrationService.sendToLm(
            Lists.newArrayList(new LiabilityDetail(), new LiabilityDetail(), new LiabilityDetail()));
        verify(liabilityDetailProducer);
    }
}
