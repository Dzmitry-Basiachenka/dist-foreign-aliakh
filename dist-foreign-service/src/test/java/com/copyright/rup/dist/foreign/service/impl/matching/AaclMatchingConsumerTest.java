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
 * Verifies {@link AaclMatchingConsumer}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/15/2020
 *
 * @author Stanislau Rudak
 * @author Aliaksandr Liakh
 */
public class AaclMatchingConsumerTest {

    private static final Long VALID_WR_WRK_INST = 123456789L;
    private static final Long INVALID_WR_WRK_INST = 987654321L;
    private static final String TITLE = "Evolutionary biology";
    private static final String STANDARD_NUMBER = "12345XX-48077";
    private static final String STANDARD_NUMBER_TYPE = "VALISBN13";

    private final AaclMatchingConsumer consumer = new AaclMatchingConsumer();
    private IChainProcessor<Usage> matchingProcessor;

    @Before
    public void setUp() {
        matchingProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(consumer, new WorkMatchingServiceMock());
        Whitebox.setInternalState(consumer, matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithWorkFound() {
        Usage usage = buildUsage(VALID_WR_WRK_INST);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usages);
        verify(matchingProcessor);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(VALID_WR_WRK_INST, usage.getWrWrkInst());
        assertEquals(TITLE, usage.getSystemTitle());
        assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
        assertEquals(STANDARD_NUMBER_TYPE, usage.getStandardNumberType());
        assertTrue(predicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeUsageWithWorkNotFound() {
        Usage usage = buildUsage(INVALID_WR_WRK_INST);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        matchingProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usages);
        verify(matchingProcessor);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertEquals(INVALID_WR_WRK_INST, usage.getWrWrkInst());
        assertNull(usage.getSystemTitle());
        assertNull(usage.getStandardNumber());
        assertNull(usage.getStandardNumberType());
        assertFalse(predicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeNull() {
        replay(matchingProcessor);
        consumer.consume(null);
        verify(matchingProcessor);
    }

    private Usage buildUsage(Long wrWrkInst) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
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
