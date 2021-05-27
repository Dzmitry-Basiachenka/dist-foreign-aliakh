package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
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
 * Verifies {@link FasMatchingConsumer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/21/2018
 *
 * @author Ihar Suvorau
 * @author Aliaksandr Liakh
 */
public class FasMatchingConsumerTest {

    private static final String VALID_TITLE = "Evolutionary biology";
    private static final String INVALID_TITLE = "Social problems";
    private static final String VALID_STANDARD_NUMBER = "12345XX-48077";
    private static final String INVALID_STANDARD_NUMBER = "12345XX-134870";
    private static final Long WR_WRK_INST = 122853920L;

    private FasMatchingConsumer consumer;
    private IChainProcessor<Usage> matchingProcessor;

    @Before
    public void setUp() {
        consumer = new FasMatchingConsumer();
        matchingProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(consumer, new WorkMatchingServiceMock());
        Whitebox.setInternalState(consumer, matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithoutTitleAndStandardNumber() {
        Usage usage = buildUsage();
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertNull(usage.getWrWrkInst());
        assertFalse(predicateCapture.getValue().test(usage));
        verify(matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithValidTitle() {
        Usage usage = buildUsageWithTitle(VALID_TITLE);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(WR_WRK_INST, usage.getWrWrkInst(), 0);
        assertTrue(predicateCapture.getValue().test(usage));
        verify(matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithInvalidTitle() {
        Usage usage = buildUsageWithTitle(INVALID_TITLE);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertNull(usage.getWrWrkInst());
        assertFalse(predicateCapture.getValue().test(usage));
        verify(matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithValidStandardNumber() {
        Usage usage = buildUsageWithStandardNumber(VALID_STANDARD_NUMBER);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(WR_WRK_INST, usage.getWrWrkInst(), 0);
        assertTrue(predicateCapture.getValue().test(usage));
        verify(matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithInvalidStandardNumber() {
        Usage usage = buildUsageWithStandardNumber(INVALID_STANDARD_NUMBER);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertNull(usage.getWrWrkInst());
        assertFalse(predicateCapture.getValue().test(usage));
        verify(matchingProcessor);
    }

    @Test
    public void testConsumeNull() {
        replay(matchingProcessor);
        consumer.consume(null);
        verify(matchingProcessor);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setStatus(UsageStatusEnum.NEW);
        return usage;
    }

    private Usage buildUsageWithTitle(String title) {
        Usage usage = buildUsage();
        usage.setWorkTitle(title);
        return usage;
    }

    private Usage buildUsageWithStandardNumber(String standardNumber) {
        Usage usage = buildUsage();
        usage.setStandardNumber(standardNumber);
        return usage;
    }

    private static class WorkMatchingServiceMock extends WorkMatchingService {

        @Override
        public void updateStatusForUsageWithoutStandardNumberAndTitle(Usage usage) {
            usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
        }

        @Override
        public void matchByTitle(Usage usage) {
            if (Objects.equals(VALID_TITLE, usage.getWorkTitle())) {
                usage.setWrWrkInst(WR_WRK_INST);
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
            } else {
                usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
            }
        }

        @Override
        public void matchByStandardNumber(Usage usage) {
            if (Objects.equals(VALID_STANDARD_NUMBER, usage.getStandardNumber())) {
                usage.setWrWrkInst(WR_WRK_INST);
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
            } else {
                usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
            }
        }
    }
}
