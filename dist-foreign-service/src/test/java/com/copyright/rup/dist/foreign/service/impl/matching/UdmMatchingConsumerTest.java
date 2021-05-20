package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Verifies {@link UdmMatchingConsumer}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/20/2021
 *
 * @author Ihar Suvorau
 */
public class UdmMatchingConsumerTest {

    private static final String TITLE = "Evolutionary biology";
    private static final String STANDARD_NUMBER = "12345XX-48077";
    private static final Long WR_WRK_INST = 122853920L;

    private UdmMatchingConsumer consumer;

    @Before
    public void setUp() {
        consumer = new UdmMatchingConsumer();
        Whitebox.setInternalState(consumer, new WorkMatchingServiceMock());
    }

    @Test
    public void testConsumeUsageWithValidTitle() {
        UdmUsage usage = buildUsageWithTitle(TITLE);
        List<UdmUsage> usages = Collections.singletonList(usage);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(WR_WRK_INST, usage.getWrWrkInst(), 0);
    }

    @Test
    public void testConsumeUsageWithInvalidTitle() {
        UdmUsage usage = buildUsageWithTitle("Social problems");
        List<UdmUsage> usages = Collections.singletonList(usage);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertNull(usage.getWrWrkInst());
    }

    @Test
    public void testConsumeUsageWithValidStandardNumber() {
        UdmUsage usage = buildUsageWithStandardNumber(STANDARD_NUMBER);
        List<UdmUsage> usages = Collections.singletonList(usage);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(WR_WRK_INST, usage.getWrWrkInst(), 0);
    }

    @Test
    public void testConsumeUsageWithInvalidStandardNumber() {
        UdmUsage usage = buildUsageWithStandardNumber("12345XX-134870");
        List<UdmUsage> usages = Collections.singletonList(usage);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertNull(usage.getWrWrkInst());
    }

    @Test
    public void testConsumeUsageWithValidWrkWrkInst() {
        UdmUsage usage = buildUsageWithWrWrkInst(WR_WRK_INST);
        List<UdmUsage> usages = Collections.singletonList(usage);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
    }

    @Test
    public void testConsumeUsageWithInvalidWrWrkInst() {
        UdmUsage usage = buildUsageWithWrWrkInst(251516721L);
        List<UdmUsage> usages = Collections.singletonList(usage);
        consumer.consume(usages);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
    }

    private UdmUsage buildUsage() {
        UdmUsage usage = new UdmUsage();
        usage.setId("45103131-f5bf-4246-b3eb-12f5702b17b9");
        usage.setStatus(UsageStatusEnum.NEW);
        return usage;
    }

    private UdmUsage buildUsageWithTitle(String title) {
        UdmUsage usage = buildUsage();
        usage.setReportedTitle(title);
        return usage;
    }

    private UdmUsage buildUsageWithStandardNumber(String standardNumber) {
        UdmUsage usage = buildUsage();
        usage.setReportedStandardNumber(standardNumber);
        return usage;
    }

    private UdmUsage buildUsageWithWrWrkInst(Long wrWrkInst) {
        UdmUsage usage = buildUsage();
        usage.setWrWrkInst(wrWrkInst);
        return usage;
    }

    private static class WorkMatchingServiceMock extends WorkMatchingService {

        @Override
        public void matchByTitle(UdmUsage usage) {
            if (Objects.equals(TITLE, usage.getReportedTitle())) {
                usage.setWrWrkInst(WR_WRK_INST);
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
            } else {
                usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
            }
        }

        @Override
        public void matchByStandardNumber(UdmUsage usage) {
            if (Objects.equals(STANDARD_NUMBER, usage.getReportedStandardNumber())) {
                usage.setWrWrkInst(WR_WRK_INST);
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
            } else {
                usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
            }
        }

        @Override
        public void matchByWrWrkInst(UdmUsage usage) {
            if (Objects.equals(WR_WRK_INST, usage.getWrWrkInst())) {
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
            } else {
                usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
            }
        }
    }
}
