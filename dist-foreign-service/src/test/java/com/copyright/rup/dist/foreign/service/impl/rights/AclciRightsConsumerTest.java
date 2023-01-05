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
 * Verifies {@link AclciRightsConsumer}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/07/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclciRightsConsumerTest {

    private AclciRightsConsumer aclciRightsConsumer;
    private IChainProcessor<Usage> aclciRightsProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        aclciRightsConsumer = new AclciRightsConsumer();
        aclciRightsProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(aclciRightsConsumer, aclciRightsProcessor);
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
        replay(aclciRightsProcessor);
        aclciRightsConsumer.consume(null);
        verify(aclciRightsProcessor);
    }

    private void testConsume(Long foundRhAccountNumber, boolean expectedPredicateResult) {
        Whitebox.setInternalState(aclciRightsConsumer, new RightsServiceMock(foundRhAccountNumber));
        Usage usage = buildUsage();
        List<Usage> usages = List.of(usage);
        Capture<Predicate<Usage>> predicateCapture = newCapture();
        aclciRightsProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(aclciRightsProcessor);
        aclciRightsConsumer.consume(usages);
        verify(aclciRightsProcessor);
        reset(aclciRightsProcessor);
        assertEquals(expectedPredicateResult, predicateCapture.getValue().test(usage));
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setProductFamily("ACLCI");
        usage.setStatus(UsageStatusEnum.WORK_FOUND);
        return usage;
    }

    private static class RightsServiceMock extends RightsService {

        private final Long rhAccountNumber;

        RightsServiceMock(Long rhAccountNumber) {
            this.rhAccountNumber = rhAccountNumber;
        }

        @Override
        public void updateAclciRights(List<Usage> usages) {
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
