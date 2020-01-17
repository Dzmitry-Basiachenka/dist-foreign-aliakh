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
 * Verifies {@link AaclRightsConsumer}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/17/2020
 *
 * @author Ihar Suvorau
 */
public class AaclRightsConsumerTest {

    private AaclRightsConsumer aaclRightsConsumer;
    private IChainProcessor<Usage> aaclRightsProcessorMock;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        aaclRightsConsumer = new AaclRightsConsumer();
        aaclRightsProcessorMock = createMock(IChainProcessor.class);
        aaclRightsConsumer.setAaclRightsProcessor(aaclRightsProcessorMock);
    }

    @Test
    public void testConsume() {
        testConsume(1000009522L, true);
    }

    @Test
    public void testConsumeUsageWithoutRights() {
        testConsume(null, false);
    }

    private void testConsume(Long foundRhAccountNumber, boolean expectedPredicateResult) {
        aaclRightsConsumer.setRightsService(new RightsServiceMock(foundRhAccountNumber));
        Usage usage = buildUsage();
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        aaclRightsProcessorMock.executeNextProcessor(eq(usage), capture(predicateCapture));
        expectLastCall().once();
        replay(aaclRightsProcessorMock);
        aaclRightsConsumer.consume(usage);
        verify(aaclRightsProcessorMock);
        reset(aaclRightsProcessorMock);
        assertEquals(expectedPredicateResult, predicateCapture.getValue().test(usage));
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setProductFamily("AACL");
        usage.setStatus(UsageStatusEnum.WORK_FOUND);
        return usage;
    }

    private static class RightsServiceMock extends RightsService {

        private final Long rhAccountNumber;

        RightsServiceMock(Long rhAccountNumber) {
            this.rhAccountNumber = rhAccountNumber;
        }

        @Override
        public void updateAaclRight(Usage usage) {
            if (Objects.nonNull(rhAccountNumber)) {
                usage.getRightsholder().setAccountNumber(rhAccountNumber);
                usage.setStatus(UsageStatusEnum.RH_FOUND);
            } else {
                usage.setStatus(UsageStatusEnum.RH_NOT_FOUND);
            }
        }
    }
}
