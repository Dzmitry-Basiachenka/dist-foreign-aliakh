package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.executor.IPerformanceLogger;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Predicate;

/**
 * Verifies {@link PerformanceLoggerChainProcessorWrapper}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 21/04/2020
 *
 * @author Aliaksandr Liakh
 */
public class PerformanceLoggerChainProcessorWrapperTest {

    private PerformanceLoggerChainProcessorWrapper<Usage> processorWrapper;
    private IChainProcessor<Usage> processor;
    private IPerformanceLogger logger;

    @Before
    public void setUp() {
        processor = createMock(IChainProcessor.class);
        logger = createMock(IPerformanceLogger.class);
        processorWrapper = new PerformanceLoggerChainProcessorWrapper<>(processor, logger, u -> 1);
    }

    @Test
    public void testProcess() {
        Usage usage = new Usage();
        ChainProcessorTypeEnum chainProcessorTypeEnum = ChainProcessorTypeEnum.RIGHTS;
        processor.process(usage);
        expectLastCall().once();
        expect(processor.getChainProcessorType()).andReturn(chainProcessorTypeEnum).once();
        logger.log(chainProcessorTypeEnum, 1);
        expectLastCall().once();
        replay(processor, logger);
        processorWrapper.process(usage);
        verify(processor, logger);
    }

    @Test
    public void testGetSuccessProcessor() {
        IChainProcessor<Usage> successProcessor = createMock(IChainProcessor.class);
        expect(processor.getSuccessProcessor()).andReturn(successProcessor).once();
        replay(processor);
        assertSame(successProcessor, processorWrapper.getSuccessProcessor());
        verify(processor);
    }

    @Test
    public void testSetSuccessProcessor() {
        IChainProcessor<Usage> successProcessor = createMock(IChainProcessor.class);
        processor.setSuccessProcessor(successProcessor);
        expectLastCall().once();
        replay(processor);
        processorWrapper.setSuccessProcessor(successProcessor);
        verify(processor);
    }

    @Test
    public void testGetFailureProcessor() {
        IChainProcessor<Usage> failureProcessor = createMock(IChainProcessor.class);
        expect(processor.getFailureProcessor()).andReturn(failureProcessor).once();
        replay(processor);
        assertSame(failureProcessor, processorWrapper.getFailureProcessor());
        verify(processor);
    }

    @Test
    public void testSetFailureProcessor() {
        IChainProcessor<Usage> failureProcessor = createMock(IChainProcessor.class);
        processor.setFailureProcessor(failureProcessor);
        expectLastCall().once();
        replay(processor);
        processorWrapper.setFailureProcessor(failureProcessor);
        verify(processor);
    }

    @Test
    public void testExecuteNextProcessor() {
        Usage usage = new Usage();
        Predicate<Usage> successPredicate = u -> true;
        processor.executeNextProcessor(usage, successPredicate);
        expectLastCall().once();
        replay(processor);
        processorWrapper.executeNextProcessor(usage, successPredicate);
        verify(processor);
    }
}
