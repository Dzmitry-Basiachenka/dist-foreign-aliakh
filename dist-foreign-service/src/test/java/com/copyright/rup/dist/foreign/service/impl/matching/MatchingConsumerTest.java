package com.copyright.rup.dist.foreign.service.impl.matching;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Objects;

/**
 * Verifies {@link MatchingConsumer}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/21/2018
 *
 * @author Ihar Suvorau
 */
public class MatchingConsumerTest {

    private static final String VALID_TITLE = "VALID TITLE";
    private static final String VALID_STANDARD_NUMBER = "VALID SN";
    private final MatchingConsumer consumer = new MatchingConsumer();
    private IChainProcessor<Usage> matchingProcessor;

    @Before
    public void setUp() {
        matchingProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(consumer, new WorkMatchingServiceMock());
        Whitebox.setInternalState(consumer, matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithoutTitleAndStandardNumber() {
        Usage usage = buildUsage();
        matchingProcessor.executeNextProcessor(eq(usage), anyObject());
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usage);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertNull(usage.getWrWrkInst());
        verify(matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithValidTitle() {
        Usage usage = buildUsageWithTitle(VALID_TITLE);
        matchingProcessor.executeNextProcessor(eq(usage), anyObject());
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(1112L, usage.getWrWrkInst(), 0);
        verify(matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithInvalidTitle() {
        Usage usage = buildUsageWithTitle("INVALID TITLE");
        matchingProcessor.executeNextProcessor(eq(usage), anyObject());
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usage);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertNull(usage.getWrWrkInst());
        verify(matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithValidStandardNumber() {
        Usage usage = buildUsageWithStandardNumber(VALID_STANDARD_NUMBER);
        matchingProcessor.executeNextProcessor(eq(usage), anyObject());
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usage);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(1112L, usage.getWrWrkInst(), 0);
        verify(matchingProcessor);
    }

    @Test
    public void testConsumeUsageWithInvalidStandardNumber() {
        Usage usage = buildUsageWithStandardNumber("INVALID SN");
        matchingProcessor.executeNextProcessor(eq(usage), anyObject());
        expectLastCall().once();
        replay(matchingProcessor);
        consumer.consume(usage);
        assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
        assertNull(usage.getWrWrkInst());
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
                usage.setWrWrkInst(1112L);
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
            } else {
                usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
            }
        }

        @Override
        public void matchByIdno(Usage usage) {
            if (Objects.equals(VALID_STANDARD_NUMBER, usage.getStandardNumber())) {
                usage.setWrWrkInst(1112L);
                usage.setStatus(UsageStatusEnum.WORK_FOUND);
            } else {
                usage.setStatus(UsageStatusEnum.WORK_NOT_FOUND);
            }
        }
    }
}
