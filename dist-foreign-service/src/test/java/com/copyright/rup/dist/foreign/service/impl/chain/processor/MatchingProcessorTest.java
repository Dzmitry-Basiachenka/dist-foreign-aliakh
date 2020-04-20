package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.executor.IPerformanceLogger;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link MatchingProcessor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
 */
public class MatchingProcessorTest {

    private MatchingProcessor processor;
    private IProducer<Usage> matchingProducer;
    private IChainProcessor<Usage> successProcessor;
    private IChainProcessor<Usage> failureProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        processor = new MatchingProcessor();
        matchingProducer = createMock(IProducer.class);
        successProcessor = createMock(IChainProcessor.class);
        failureProcessor = createMock(IChainProcessor.class);
        processor.setUsagesBatchSize(1000);
        processor.setMatchingProducer(matchingProducer);
        processor.setSuccessProcessor(successProcessor);
        processor.setFailureProcessor(failureProcessor);
        processor.setUsageStatus(UsageStatusEnum.NEW);
        Whitebox.setInternalState(processor.getSuccessProcessor(), createMock(IPerformanceLogger.class));
        Whitebox.setInternalState(processor.getFailureProcessor(), createMock(IPerformanceLogger.class));
    }

    @Test
    public void testProcessUsage() {
        Usage usage = buildUsage(UsageStatusEnum.NEW);
        matchingProducer.send(usage);
        expectLastCall().once();
        replay(matchingProducer);
        processor.process(usage);
        verify(matchingProducer);
    }

    @Test
    public void testProcessResultSuccess() {
        Usage usage = buildUsage(UsageStatusEnum.WORK_FOUND);
        successProcessor.process(usage);
        expectLastCall().once();
        expect(successProcessor.getChainProcessorType()).andReturn(ChainProcessorTypeEnum.RIGHTS).once();
        replay(successProcessor, failureProcessor);
        processor.executeNextProcessor(usage, usage1 -> UsageStatusEnum.WORK_FOUND == usage1.getStatus());
        verify(successProcessor, failureProcessor);
    }

    @Test
    public void testProcessResultFailure() {
        Usage usage = buildUsage(UsageStatusEnum.WORK_NOT_FOUND);
        failureProcessor.process(usage);
        expectLastCall().once();
        expect(failureProcessor.getChainProcessorType()).andReturn(ChainProcessorTypeEnum.DELETE).once();
        replay(successProcessor, failureProcessor);
        processor.executeNextProcessor(usage, usage1 -> UsageStatusEnum.WORK_FOUND == usage1.getStatus());
        verify(successProcessor, failureProcessor);
    }

    private Usage buildUsage(UsageStatusEnum status) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setStatus(status);
        usage.setProductFamily("FAS");
        return usage;
    }
}
