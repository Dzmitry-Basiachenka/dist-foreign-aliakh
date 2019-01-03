package com.copyright.rup.dist.foreign.service.impl.tax;

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
import com.copyright.rup.dist.foreign.service.api.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Predicate;

/**
 * Verifies {@link RhTaxConsumer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Uladzislau Shalamitski
 */
public class RhTaxConsumerTest {

    private RhTaxConsumer rhTaxConsumer;
    private IRhTaxService rhTaxService;
    private IChainProcessor<Usage> rhTaxProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rhTaxConsumer = new RhTaxConsumer();
        rhTaxService = createMock(IRhTaxService.class);
        rhTaxProcessor = createMock(IChainProcessor.class);
        rhTaxConsumer.setRhTaxProcessor(rhTaxProcessor);
        rhTaxConsumer.setRhTaxService(rhTaxService);
    }

    @Test
    public void testConsume() {
        Usage usage = new Usage();
        expect(rhTaxService.isUsCountryCodeUsage(usage)).andReturn(true).once();
        Capture<Predicate<Usage>> successPredicate = new Capture<>();
        rhTaxProcessor.processResult(eq(usage), capture(successPredicate));
        expectLastCall().once();
        replay(rhTaxService, rhTaxProcessor);
        rhTaxConsumer.consume(usage);
        verify(rhTaxService, rhTaxProcessor);
        assertTrue(successPredicate.getValue().test(usage));
    }

    @Test
    public void testConsumeWithNonUsUsageRh() {
        Usage usage = new Usage();
        expect(rhTaxService.isUsCountryCodeUsage(usage)).andReturn(false).once();
        Capture<Predicate<Usage>> successPredicateCapture = new Capture<>();
        rhTaxProcessor.processResult(eq(usage), capture(successPredicateCapture));
        expectLastCall().once();
        replay(rhTaxService, rhTaxProcessor);
        rhTaxConsumer.consume(usage);
        verify(rhTaxService, rhTaxProcessor);
        assertFalse(successPredicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeNull() {
        replay(rhTaxService);
        rhTaxConsumer.consume(null);
        verify(rhTaxService);
    }
}
