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
 * Verifies {@link SalMatchingConsumer}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/19/2020
 *
 * @author Anton Azarenka
 */
public class SalMatchingConsumerTest {

    private static final Long VALID_WR_WRK_INST = 123456789L;
    private static final Long MISSING_WR_WRK_INST = 987654321L;
    private static final String TITLE = "Red Riding Hood's maths adventure";
    private static final String STANDARD_NUMBER = "978-0-7112-1567-2";
    private static final String STANDARD_NUMBER_TYPE = "VALISBN13";
    private static final String USAGE_ID = "49ead60e-97f4-47c8-8ebe-d19d07457cca";

    private final SalMatchingConsumer consumer = new SalMatchingConsumer();
    private IChainProcessor<Usage> matchingProcessor;

    @Before
    public void setUp() {
        matchingProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(consumer, new WorkMatchingServiceMock());
        Whitebox.setInternalState(consumer, matchingProcessor);
    }

    @Test
    public void testConsumeWithWorkFound() {
        Usage usage = buildUsage(VALID_WR_WRK_INST);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> successPredicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(successPredicateCapture));
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(VALID_WR_WRK_INST, usage.getWrWrkInst());
        assertEquals(TITLE, usage.getSystemTitle());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        assertEquals(STANDARD_NUMBER_TYPE, usage.getStandardNumberType());
        assertTrue(successPredicateCapture.getValue().test(usage));
        verify(matchingProcessor);
    }

    @Test
    public void testConsumeWithWorkNotFound() {
        Usage usage = buildUsage(MISSING_WR_WRK_INST);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> successPredicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(successPredicateCapture));
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertEquals(MISSING_WR_WRK_INST, usage.getWrWrkInst());
        assertNull(usage.getSystemTitle());
        assertNull(usage.getStandardNumber());
        assertNull(usage.getStandardNumberType());
        assertFalse(successPredicateCapture.getValue().test(usage));
        verify(matchingProcessor);
    }

    private Usage buildUsage(Long wrWrkInst) {
        Usage usage = new Usage();
        usage.setId(USAGE_ID);
        usage.setWrWrkInst(wrWrkInst);
        usage.setStatus(UsageStatusEnum.NEW);
        return usage;
    }

    private static class WorkMatchingServiceMock extends WorkMatchingService {
        @Override
        public void matchByWrWrkInst(Usage usage) {
            if (Objects.equals(VALID_WR_WRK_INST, usage.getWrWrkInst())) {
                usage.setSystemTitle(TITLE);
                usage.setStandardNumber(STANDARD_NUMBER);
                usage.setStandardNumberType(STANDARD_NUMBER_TYPE);
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
            } else {
                usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
            }
        }
    }
}
