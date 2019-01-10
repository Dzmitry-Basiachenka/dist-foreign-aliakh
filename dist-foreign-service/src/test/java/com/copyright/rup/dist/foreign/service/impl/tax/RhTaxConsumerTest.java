package com.copyright.rup.dist.foreign.service.impl.tax;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IChainProcessor;

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

    private static final String USAGE_ID_1 = "ba54c138-398f-4444-92fb-9e3aa991ea1d";
    private static final String USAGE_ID_2 = "47e115e6-497f-4cb6-99f7-b5536722b769";

    private RhTaxConsumer rhTaxConsumer;
    private IChainProcessor<Usage> rhTaxProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rhTaxConsumer = new RhTaxConsumer();
        rhTaxProcessor = createMock(IChainProcessor.class);
        rhTaxConsumer.setRhTaxProcessor(rhTaxProcessor);
        rhTaxConsumer.setRhTaxService(new MockRhTaxService());
    }

    @Test
    public void testConsume() {
        Usage usage = new Usage();
        usage.setId(USAGE_ID_1);
        Capture<Predicate<Usage>> successPredicate = new Capture<>();
        rhTaxProcessor.processResult(eq(usage), capture(successPredicate));
        expectLastCall().once();
        replay(rhTaxProcessor);
        rhTaxConsumer.consume(usage);
        verify(rhTaxProcessor);
        assertTrue(successPredicate.getValue().test(usage));
    }

    @Test
    public void testConsumeWithNonUsUsageRh() {
        Usage usage = new Usage();
        usage.setId(USAGE_ID_2);
        Capture<Predicate<Usage>> successPredicateCapture = new Capture<>();
        rhTaxProcessor.processResult(eq(usage), capture(successPredicateCapture));
        expectLastCall().once();
        replay(rhTaxProcessor);
        rhTaxConsumer.consume(usage);
        verify(rhTaxProcessor);
        assertFalse(successPredicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeNull() {
        replay(rhTaxProcessor);
        rhTaxConsumer.consume(null);
        verify(rhTaxProcessor);
    }

    private static class MockRhTaxService extends RhTaxService {
        @Override
        public void processTaxCountryCode(Usage usage) {
            if (USAGE_ID_1.equals(usage.getId())) {
                usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
            }
        }
    }
}
