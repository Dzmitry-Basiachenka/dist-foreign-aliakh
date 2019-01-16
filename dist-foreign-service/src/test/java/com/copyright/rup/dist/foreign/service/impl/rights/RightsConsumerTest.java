package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;
import java.util.function.Predicate;

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

    private RightsConsumer rightsConsumer;
    private IChainProcessor<Usage> ntsRightsProcessorMock;
    private IChainProcessor<Usage> fasRightsProcessorMock;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        rightsConsumer = new RightsConsumer();
        ntsRightsProcessorMock = createMock(IChainProcessor.class);
        fasRightsProcessorMock = createMock(IChainProcessor.class);
        rightsConsumer.setNtsRightsProcessor(ntsRightsProcessorMock);
        rightsConsumer.setFasRightsProcessor(fasRightsProcessorMock);
    }

    @Test
    public void testConsumeNtsUsage() {
        testConsume("NTS", 10000L, ntsRightsProcessorMock, true);
    }

    @Test
    public void testConsumeNtsUsageWithoutRights() {
        testConsume("NTS", null, ntsRightsProcessorMock, false);
    }

    @Test
    public void testConsumeFasUsage() {
        testConsume("FAS", 10000L, fasRightsProcessorMock, true);
    }

    @Test
    public void testConsumeFasUsageWithoutRights() {
        testConsume("FAS", null, fasRightsProcessorMock, false);
    }

    @Test
    public void testConsumeClaUsage() {
        testConsume("FAS2", 10000L, fasRightsProcessorMock, true);
    }

    @Test
    public void testConsumeClaUsageWithoutRights() {
        testConsume("FAS2", null, fasRightsProcessorMock, false);
    }

    private void testConsume(String productFamily, Long foundRhAccountNumber, IChainProcessor<Usage> expectedProcessor,
                            boolean expectedPredicateResult) {
        rightsConsumer.setRightsService(new RightsServiceMock(foundRhAccountNumber));
        Usage usage = buildUsage(productFamily);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        expectedProcessor.executeNextProcessor(eq(usage), capture(predicateCapture));
        expectLastCall().once();
        replay(expectedProcessor);
        rightsConsumer.consume(usage);
        verify(expectedProcessor);
        reset(expectedProcessor);
        assertEquals(expectedPredicateResult, predicateCapture.getValue().test(usage));
    }

    private Usage buildUsage(String productFamily) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setProductFamily(productFamily);
        usage.setStatus(UsageStatusEnum.WORK_FOUND);
        return usage;
    }

    private static class RightsServiceMock extends RightsService {

        private final Long rhAccountNumber;

        RightsServiceMock(Long rhAccountNumber) {
            this.rhAccountNumber = rhAccountNumber;
        }

        @Override
        public void updateRight(Usage usage) {
            if (Objects.nonNull(rhAccountNumber)) {
                usage.getRightsholder().setAccountNumber(rhAccountNumber);
                usage.setStatus(UsageStatusEnum.RH_FOUND);
            } else {
                usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
            }
        }
    }
}
