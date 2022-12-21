package com.copyright.rup.dist.foreign.service.impl.eligibility;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Verifies {@link RhEligibilityConsumer}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/11/2019
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class RhEligibilityConsumerTest {

    private static final String RH_ID = "ba54c138-398f-4444-92fb-9e3aa991ea1d";

    private RhEligibilityConsumer rhEligibilityConsumer;
    private IChainProcessor<Usage> rhEligibilityProcessor;
    private IPrmIntegrationService prmIntegrationService;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rhEligibilityConsumer = new RhEligibilityConsumer();
        rhEligibilityProcessor = createMock(IChainProcessor.class);
        prmIntegrationService = createMock(IPrmIntegrationService.class);
        Whitebox.setInternalState(rhEligibilityConsumer, rhEligibilityProcessor);
        Whitebox.setInternalState(rhEligibilityConsumer, prmIntegrationService);
    }

    @Test
    public void testConsumeUsageWithEligibleRightsholder() {
        Usage usage = buildUsage();
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = newCapture();
        expect(prmIntegrationService.isRightsholderEligibleForNtsDistribution(RH_ID)).andReturn(true).once();
        rhEligibilityProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(rhEligibilityProcessor, prmIntegrationService);
        rhEligibilityConsumer.consume(usages);
        assertTrue(predicateCapture.getValue().test(usage));
        verify(rhEligibilityProcessor, prmIntegrationService);
    }

    @Test
    public void testConsumeUsageWithIneligibleRightsholder() {
        Usage usage = buildUsage();
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = newCapture();
        expect(prmIntegrationService.isRightsholderEligibleForNtsDistribution(RH_ID)).andReturn(false).once();
        rhEligibilityProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(rhEligibilityProcessor, prmIntegrationService);
        rhEligibilityConsumer.consume(usages);
        assertFalse(predicateCapture.getValue().test(usage));
        verify(rhEligibilityProcessor, prmIntegrationService);
    }

    @Test
    public void testConsumeNull() {
        replay(rhEligibilityProcessor);
        rhEligibilityConsumer.consume(null);
        verify(rhEligibilityProcessor);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.getRightsholder().setId(RH_ID);
        return usage;
    }
}
