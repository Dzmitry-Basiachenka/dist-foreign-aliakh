package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.IRightsService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link RightsConsumer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/21/2018
 *
 * @author Uladzislau Shalamitski
 */
public class RightsConsumerTest {

    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String NTS_USAGE_ID_WITH_RH = "1b44ab08-aea4-4bce-8d1c-f183f0ec595";

    private final RightsConsumer consumer = new RightsConsumer();
    private IProducer<Usage> rhTaxProducer;
    private IChainProcessor<Usage> rightsProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rhTaxProducer = createMock(IProducer.class);
        rightsProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(consumer, new RightsServiceMock());
        Whitebox.setInternalState(consumer, rhTaxProducer);
        Whitebox.setInternalState(consumer, rightsProcessor);
    }

    @Test
    public void testConsumeNtsUsageWithoutRightsholder() {
        Usage usage = buildUsage("05a338d3-eac0-483e-bbf0-a6dfd60ad678", NTS_PRODUCT_FAMILY);
        replay(rhTaxProducer);
        consumer.consume(usage);
        verify(rhTaxProducer);
    }

    @Test
    public void testConsumeNtsUsageWithRightsholder() {
        Usage usage = buildUsage(NTS_USAGE_ID_WITH_RH, NTS_PRODUCT_FAMILY);
        rhTaxProducer.send(usage);
        expectLastCall().once();
        replay(rhTaxProducer);
        consumer.consume(usage);
        verify(rhTaxProducer);
    }

    @Test
    public void testConsumeFasUsage() {
        Usage usage = buildUsage("1861c32e-eb9c-42c6-ae4f-2a4d65f207bc", "FAS");
        rightsProcessor.processResult(eq(usage), anyObject());
        replay(rightsProcessor);
        consumer.consume(usage);
        verify(rightsProcessor);
    }

    private Usage buildUsage(String usageId, String productFamily) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setProductFamily(productFamily);
        return usage;
    }

    private static class RightsServiceMock implements IRightsService {

        @Override
        public void sendForRightsAssignment() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateRights(String productFamily) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateRight(Usage usage) {
            if (NTS_PRODUCT_FAMILY.equals(usage.getProductFamily()) && NTS_USAGE_ID_WITH_RH.equals(usage.getId())) {
                usage.getRightsholder().setAccountNumber(1000009522L);
                usage.setStatus(UsageStatusEnum.RH_FOUND);
            }
        }
    }
}
