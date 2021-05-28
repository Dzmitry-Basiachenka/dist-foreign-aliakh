package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Verifies {@link UdmRightsConsumer}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmRightsConsumerTest {

    private UdmRightsConsumer udmRightsConsumer;
    private IChainProcessor<UdmUsage> udmRightsProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        udmRightsConsumer = new UdmRightsConsumer();
        udmRightsProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(udmRightsConsumer, udmRightsProcessor);
    }

    @Test
    public void testConsumeUdmUsageRhFound() {
        Whitebox.setInternalState(udmRightsConsumer, new RightsServiceMock(1000005289L));
        UdmUsage udmUsage = buildUdmUsage();
        List<UdmUsage> udmUsages = Collections.singletonList(udmUsage);
        Capture<Predicate<UdmUsage>> predicateCapture = new Capture<>();
        udmRightsProcessor.executeNextChainProcessor(eq(udmUsages), capture(predicateCapture));
        expectLastCall().once();
        replay(udmRightsProcessor);
        udmRightsConsumer.consume(udmUsages);
        verify(udmRightsProcessor);
        reset(udmRightsProcessor);
        assertTrue(predicateCapture.getValue().test(udmUsage));
    }

    @Test
    public void testConsumeUdmUsageRhNotFound() {
        Whitebox.setInternalState(udmRightsConsumer, new RightsServiceMock(null));
        UdmUsage udmUsage = buildUdmUsage();
        List<UdmUsage> udmUsages = Collections.singletonList(udmUsage);
        Capture<Predicate<UdmUsage>> predicateCapture = new Capture<>();
        udmRightsProcessor.executeNextChainProcessor(eq(udmUsages), capture(predicateCapture));
        expectLastCall().once();
        replay(udmRightsProcessor);
        udmRightsConsumer.consume(udmUsages);
        verify(udmRightsProcessor);
        reset(udmRightsProcessor);
        assertFalse(predicateCapture.getValue().test(udmUsage));
    }

    @Test
    public void testConsumeNull() {
        replay(udmRightsProcessor);
        udmRightsConsumer.consume(null);
        verify(udmRightsProcessor);
    }

    private UdmUsage buildUdmUsage() {
        UdmUsage usage = new UdmUsage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setStatus(UsageStatusEnum.WORK_FOUND);
        return usage;
    }

    private static class RightsServiceMock extends RightsService {

        private final Long rhAccountNumber;

        RightsServiceMock(Long rhAccountNumber) {
            this.rhAccountNumber = rhAccountNumber;
        }

        @Override
        public void updateUdmRights(List<UdmUsage> usages) {
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
