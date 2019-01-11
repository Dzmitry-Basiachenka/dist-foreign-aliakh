package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link RhEligibilityProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/02/2019
 *
 * @author Uladzislau Shalamitski
 */
public class RhEligibilityProcessorTest {

    private RhEligibilityProcessor rhEligibilityProcessor;
    private IProducer<Usage> rhEligibilityProducerMock;
    private IUsageService usageServiceMock;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rhEligibilityProcessor = new RhEligibilityProcessor();
        rhEligibilityProducerMock = createMock(IProducer.class);
        usageServiceMock = createMock(IUsageService.class);
        rhEligibilityProcessor.setRhEligibilityProducer(rhEligibilityProducerMock);
        rhEligibilityProcessor.setUsageService(usageServiceMock);
    }

    @Test
    public void testProcess() {
        Usage usage = new Usage();
        rhEligibilityProducerMock.send(usage);
        expectLastCall().once();
        replay(rhEligibilityProducerMock);
        rhEligibilityProcessor.process(usage);
        verify(rhEligibilityProducerMock);
    }

    @Test
    public void testProcessByProductFamily() {
        List<Usage> rhFoundUsages = Arrays.asList(buildUsage(), buildUsage());
        expect(usageServiceMock.getUsagesByStatusAndProductFamily(eq(UsageStatusEnum.US_TAX_COUNTRY), eq("NTS")))
            .andReturn(rhFoundUsages)
            .once();
        Capture<Usage> usageCapture1 = new Capture<>();
        rhEligibilityProducerMock.send(capture(usageCapture1));
        expectLastCall().once();
        Capture<Usage> usageCapture2 = new Capture<>();
        rhEligibilityProducerMock.send(capture(usageCapture2));
        expectLastCall().once();
        replay(usageServiceMock, rhEligibilityProducerMock);
        rhEligibilityProcessor.process("NTS");
        verify(usageServiceMock, rhEligibilityProducerMock);
        assertEquals(usageCapture1.getValue(), rhFoundUsages.get(0));
        assertEquals(usageCapture2.getValue(), rhFoundUsages.get(1));
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(854030733L);
        usage.setProductFamily("NTS");
        usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
        return usage;
    }
}
