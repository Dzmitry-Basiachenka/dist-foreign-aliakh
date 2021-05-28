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
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.stm.IStmRhService;

import org.apache.commons.collections4.CollectionUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Verifies {@link StmRhConsumer}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/2019
 *
 * @author Stanislau Rudak
 * @author Aliaksandr Liakh
 */
public class StmRhConsumerTest {

    private static final String USAGE_ID_1 = RupPersistUtils.generateUuid();
    private static final String USAGE_ID_2 = RupPersistUtils.generateUuid();
    private static final String BATCH_ID = RupPersistUtils.generateUuid();
    private static final String RH_ID = RupPersistUtils.generateUuid();
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private IUsageBatchService batchService;
    private IStmRhService stmRhService;
    private IChainProcessor<Usage> stmRhProcessor;
    private StmRhConsumer consumer;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        stmRhService = createMock(IStmRhService.class);
        batchService = createMock(IUsageBatchService.class);
        stmRhProcessor = createMock(IChainProcessor.class);
        consumer = new StmRhConsumer();
        Whitebox.setInternalState(consumer, stmRhService);
        Whitebox.setInternalState(consumer, stmRhProcessor);
        Whitebox.setInternalState(consumer, batchService);
    }

    @Test
    public void testConsumeNotExcludingStm() {
        Usage usage = buildUsage(USAGE_ID_1);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        expect(batchService.getUsageBatchById(BATCH_ID)).andReturn(buildUsageBatch(buildNtsFields(false))).once();
        stmRhProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(batchService, stmRhService, stmRhProcessor);
        consumer.consume(usages);
        verify(batchService, stmRhService, stmRhProcessor);
        assertTrue(predicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeExcludingStmWithNonStmRightsholder() {
        Usage usage = buildUsage(USAGE_ID_1);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        expect(batchService.getUsageBatchById(BATCH_ID)).andReturn(buildUsageBatch(buildNtsFields(true))).once();
        stmRhService.processStmRhs(usages, NTS_PRODUCT_FAMILY);
        expectLastCall().andDelegateTo(new MockStmRhService()).once();
        stmRhProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(batchService, stmRhService, stmRhProcessor);
        consumer.consume(usages);
        verify(batchService, stmRhService, stmRhProcessor);
        assertTrue(predicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeExcludingStmWithStmRightsholder() {
        Usage usage = buildUsage(USAGE_ID_2);
        List<Usage> usages = Collections.singletonList(usage);
        Capture<Predicate<Usage>> predicateCapture = new Capture<>();
        expect(batchService.getUsageBatchById(BATCH_ID)).andReturn(buildUsageBatch(buildNtsFields(true))).once();
        stmRhService.processStmRhs(usages, NTS_PRODUCT_FAMILY);
        expectLastCall().andDelegateTo(new MockStmRhService()).once();
        stmRhProcessor.executeNextChainProcessor(eq(usages), capture(predicateCapture));
        expectLastCall().once();
        replay(batchService, stmRhService, stmRhProcessor);
        consumer.consume(usages);
        verify(batchService, stmRhService, stmRhProcessor);
        assertFalse(predicateCapture.getValue().test(usage));
    }

    @Test
    public void testConsumeNullUsage() {
        replay(stmRhProcessor);
        consumer.consume(null);
        verify(stmRhProcessor);
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
        rightsholder.setName("Metals handbook");
        return rightsholder;
    }

    private UsageBatch buildUsageBatch(NtsFields ntsFields) {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_ID);
        usageBatch.setProductFamily(NTS_PRODUCT_FAMILY);
        usageBatch.setNtsFields(ntsFields);
        return usageBatch;
    }

    private NtsFields buildNtsFields(boolean excludingStm) {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setExcludingStm(excludingStm);
        return ntsFields;
    }

    private static class MockStmRhService implements IStmRhService {

        @Override
        public void processStmRhs(List<Usage> usages, String productFamily) {
            if (1 == CollectionUtils.size(usages) && USAGE_ID_1.equals(usages.get(0).getId())) {
                usages.get(0).setStatus(UsageStatusEnum.NON_STM_RH);
            }
        }
    }
}
