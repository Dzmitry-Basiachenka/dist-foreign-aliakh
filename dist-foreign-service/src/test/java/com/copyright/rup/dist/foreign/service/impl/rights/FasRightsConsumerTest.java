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

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Verifies {@link FasRightsConsumer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/21/2018
 *
 * @author Uladzislau Shalamitski
 */
public class FasRightsConsumerTest {

    private FasRightsConsumer fasRightsConsumer;
    private IChainProcessor<Usage> fasRightsProcessorMock;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        fasRightsConsumer = new FasRightsConsumer();
        fasRightsProcessorMock = createMock(IChainProcessor.class);
        fasRightsConsumer.setFasRightsProcessor(fasRightsProcessorMock);
    }

    @Test
    public void testConsumeFasUsage() {
        testConsume("FAS", 1000009522L, true);
    }

    @Test
    public void testConsumeFasUsageWithoutRights() {
        testConsume("FAS", null, false);
    }

    @Test
    public void testConsumeFas2Usage() {
        testConsume("FAS2", 1000009522L, true);
    }

    @Test
    public void testConsumeFas2UsageWithoutRights() {
        testConsume("FAS2", null, false);
    }

    private void testConsume(String productFamily, Long foundRhAccountNumber, boolean expectedPredicateResult) {
        fasRightsConsumer.setRightsService(new RightsServiceMock(foundRhAccountNumber));
        Usage usage = buildUsage(productFamily);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        fasRightsProcessorMock.executeNextProcessor(eq(usage), capture(predicateCapture));
        expectLastCall().once();
        replay(fasRightsProcessorMock);
        fasRightsConsumer.consume(usage);
        verify(fasRightsProcessorMock);
        reset(fasRightsProcessorMock);
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
        public void updateRights(List<Usage> usages, boolean logAction) {
            usages.forEach(usage -> {
                if (Objects.nonNull(rhAccountNumber)) {
                    usage.getRightsholder().setAccountNumber(rhAccountNumber);
                    usage.setStatus(UsageStatusEnum.RH_FOUND);
                } else {
                    usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
                }
            });
        }
    }
}
