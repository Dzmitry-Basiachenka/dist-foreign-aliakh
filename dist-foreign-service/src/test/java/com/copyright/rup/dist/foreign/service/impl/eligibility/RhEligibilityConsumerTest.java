package com.copyright.rup.dist.foreign.service.impl.eligibility;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.service.api.IChainProcessor;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Predicate;

/**
 * Verifies {@link RhEligibilityConsumer}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/11/19
 *
 * @author Uladzislau Shalamitski
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
        rhEligibilityConsumer.setRhEligibilityProcessor(rhEligibilityProcessor);
        rhEligibilityConsumer.setPrmIntegrationService(prmIntegrationService);
    }

    @Test
    public void testConsumeEligibleRightsholder() {
        Usage usage = new Usage();
        usage.getRightsholder().setId(RH_ID);
        Capture<Predicate<Usage>> successPredicate = new Capture<>();
        expect(prmIntegrationService.isRightsholderEligibleForNtsDistribution(RH_ID))
            .andReturn(true)
            .once();
        rhEligibilityProcessor.processResult(eq(usage), capture(successPredicate));
        expectLastCall().once();
        replay(rhEligibilityProcessor, prmIntegrationService);
        rhEligibilityConsumer.consume(usage);
        verify(rhEligibilityProcessor, prmIntegrationService);
        assertTrue(successPredicate.getValue().test(usage));
    }

    @Test
    public void testConsumeIneligibleRightsholder() {
        Usage usage = new Usage();
        usage.getRightsholder().setId(RH_ID);
        Capture<Predicate<Usage>> successPredicate = new Capture<>();
        expect(prmIntegrationService.isRightsholderEligibleForNtsDistribution(RH_ID))
            .andReturn(false)
            .once();
        rhEligibilityProcessor.processResult(eq(usage), capture(successPredicate));
        expectLastCall().once();
        replay(rhEligibilityProcessor, prmIntegrationService);
        rhEligibilityConsumer.consume(usage);
        verify(rhEligibilityProcessor, prmIntegrationService);
        assertFalse(successPredicate.getValue().test(usage));
    }

    @Test
    public void testConsumeNull() {
        replay(rhEligibilityProcessor);
        rhEligibilityConsumer.consume(null);
        verify(rhEligibilityProcessor);
    }
}
