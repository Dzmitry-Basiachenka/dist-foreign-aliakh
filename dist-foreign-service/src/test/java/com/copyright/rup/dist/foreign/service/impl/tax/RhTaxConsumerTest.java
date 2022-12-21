package com.copyright.rup.dist.foreign.service.impl.tax;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.apache.commons.collections4.CollectionUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Verifies {@link RhTaxConsumer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
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
        Whitebox.setInternalState(rhTaxConsumer, rhTaxProcessor);
        Whitebox.setInternalState(rhTaxConsumer, new MockRhTaxService());
    }

    @Test
    public void testConsumeWithUsUsageRh() {
        Usage usage = buildUsage(USAGE_ID_1);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = newCapture();
        rhTaxProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(rhTaxProcessor);
        rhTaxConsumer.consume(usages);
        verify(rhTaxProcessor);
        assertTrue(predicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeWithoutUsUsageRh() {
        Usage usage = buildUsage(USAGE_ID_2);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = newCapture();
        rhTaxProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(rhTaxProcessor);
        rhTaxConsumer.consume(usages);
        verify(rhTaxProcessor);
        assertFalse(predicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeNull() {
        replay(rhTaxProcessor);
        rhTaxConsumer.consume(null);
        verify(rhTaxProcessor);
    }

    private Usage buildUsage(String id) {
        Usage usage = new Usage();
        usage.getRightsholder().setAccountNumber(100009522L);
        usage.setId(id);
        return usage;
    }

    private static class MockRhTaxService extends RhTaxService {

        @Override
        public void processTaxCountryCode(List<Usage> usages) {
            if (1 == CollectionUtils.size(usages) && USAGE_ID_1.equals(usages.get(0).getId())) {
                usages.get(0).setStatus(UsageStatusEnum.US_TAX_COUNTRY);
            }
        }
    }
}
