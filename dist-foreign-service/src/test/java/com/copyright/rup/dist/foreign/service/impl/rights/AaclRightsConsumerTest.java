package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
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
import org.powermock.reflect.Whitebox;

import java.util.List;
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
 * @author Aliaksandr Liakh
 */
public class AaclRightsConsumerTest {

    private AaclRightsConsumer aaclRightsConsumer;
    private IChainProcessor<Usage> aaclRightsProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        aaclRightsConsumer = new AaclRightsConsumer();
        aaclRightsProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(aaclRightsConsumer, aaclRightsProcessor);
    }

    @Test
    public void testConsume() {
        testConsume(1000009522L, true);
    }

    @Test
    public void testConsumeUsageWithoutRights() {
        testConsume(null, false);
    }

    @Test
    public void testConsumeNull() {
        replay(aaclRightsProcessor);
        aaclRightsConsumer.consume(null);
        verify(aaclRightsProcessor);
    }

    private void testConsume(Long foundRhAccountNumber, boolean expectedPredicateResult) {
        Whitebox.setInternalState(aaclRightsConsumer, new RightsServiceMock(foundRhAccountNumber));
        Usage usage = buildUsage();
        List<Usage> usages = List.of(usage);
        Capture<Predicate<Usage>> predicateCapture = newCapture();
        aaclRightsProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(aaclRightsProcessor);
        aaclRightsConsumer.consume(usages);
        verify(aaclRightsProcessor);
        reset(aaclRightsProcessor);
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
        public void updateAaclRights(List<Usage> usages) {
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
