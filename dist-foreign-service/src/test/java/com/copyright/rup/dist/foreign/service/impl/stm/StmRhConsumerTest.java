package com.copyright.rup.dist.foreign.service.impl.stm;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IStmRhService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.function.Predicate;

/**
 * Verifies {@link StmRhConsumer}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Stanislau Rudak
 */
public class StmRhConsumerTest {

    private static final String USAGE_ID_1 = RupPersistUtils.generateUuid();
    private static final String USAGE_ID_2 = RupPersistUtils.generateUuid();
    private static final String BATCH_ID = RupPersistUtils.generateUuid();
    private static final String RH_ID = RupPersistUtils.generateUuid();
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private IUsageBatchService batchServiceMock;
    private IStmRhService stmRhServiceMock;
    private IChainProcessor<Usage> stmRhProcessorMock;
    private StmRhConsumer consumer;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        stmRhServiceMock = createMock(IStmRhService.class);
        batchServiceMock = createMock(IUsageBatchService.class);
        stmRhProcessorMock = createMock(IChainProcessor.class);
        consumer = new StmRhConsumer();
        Whitebox.setInternalState(consumer, stmRhServiceMock);
        Whitebox.setInternalState(consumer, stmRhProcessorMock);
        Whitebox.setInternalState(consumer, batchServiceMock);
    }

    @Test
    public void testConsumeNotExcludingStm() {
        Usage usage = buildUsage(USAGE_ID_1);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        expect(batchServiceMock.getUsageBatchById(BATCH_ID)).andReturn(buildUsageBatch(buildFundPool(false))).once();
        stmRhProcessorMock.executeNextProcessor(eq(usage), capture(predicateCapture));
        expectLastCall().once();
        replay(batchServiceMock, stmRhServiceMock, stmRhProcessorMock);
        consumer.consume(usage);
        verify(batchServiceMock, stmRhServiceMock, stmRhProcessorMock);
        assertTrue(predicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeExcludingStmWithNonStmRh() {
        Usage usage = buildUsage(USAGE_ID_1);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        expect(batchServiceMock.getUsageBatchById(BATCH_ID)).andReturn(buildUsageBatch(buildFundPool(true))).once();
        stmRhServiceMock.processStmRh(usage);
        expectLastCall().andDelegateTo(new MockStmRhService()).once();
        stmRhProcessorMock.executeNextProcessor(eq(usage), capture(predicateCapture));
        expectLastCall().once();
        replay(batchServiceMock, stmRhServiceMock, stmRhProcessorMock);
        consumer.consume(usage);
        verify(batchServiceMock, stmRhServiceMock, stmRhProcessorMock);
        assertTrue(predicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeExcludingStmWithStmRh() {
        Usage usage = buildUsage(USAGE_ID_2);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        expect(batchServiceMock.getUsageBatchById(BATCH_ID)).andReturn(buildUsageBatch(buildFundPool(true))).once();
        stmRhServiceMock.processStmRh(usage);
        expectLastCall().andDelegateTo(new MockStmRhService()).once();
        stmRhProcessorMock.executeNextProcessor(eq(usage), capture(predicateCapture));
        expectLastCall().once();
        replay(batchServiceMock, stmRhServiceMock, stmRhProcessorMock);
        consumer.consume(usage);
        verify(batchServiceMock, stmRhServiceMock, stmRhProcessorMock);
        assertFalse(predicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeNullUsage() {
        replay(stmRhProcessorMock);
        consumer.consume(null);
        verify(stmRhProcessorMock);
    }

    private Usage buildUsage(String usageId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setProductFamily(NTS_PRODUCT_FAMILY);
        usage.setStatus(UsageStatusEnum.US_TAX_COUNTRY);
        usage.setRightsholder(buildRightsholder());
        usage.setBatchId(BATCH_ID);
        return usage;
    }

    private Rightsholder buildRightsholder() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(RH_ID);
        rightsholder.setAccountNumber(1000023401L);
        rightsholder.setName("American College of Physicians - Journals");
        return rightsholder;
    }

    private UsageBatch buildUsageBatch(FundPool fundPool) {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_ID);
        usageBatch.setProductFamily(NTS_PRODUCT_FAMILY);
        usageBatch.setFundPool(fundPool);
        return usageBatch;
    }

    private FundPool buildFundPool(boolean excludingStm) {
        FundPool fundPool = new FundPool();
        fundPool.setExcludingStm(excludingStm);
        return fundPool;
    }

    private static class MockStmRhService implements IStmRhService {

        @Override
        public void processStmRh(Usage usage) {
            if (USAGE_ID_1.equals(usage.getId())) {
                usage.setStatus(UsageStatusEnum.NON_STM_RH);
            }
        }
    }
}
