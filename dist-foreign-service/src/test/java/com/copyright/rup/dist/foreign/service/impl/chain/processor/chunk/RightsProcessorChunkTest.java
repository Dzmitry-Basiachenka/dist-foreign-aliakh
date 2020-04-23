package com.copyright.rup.dist.foreign.service.impl.chain.processor.chunk;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

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

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link RightsChunkProcessor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class RightsProcessorChunkTest {

    private RightsChunkProcessor processor;
    private IProducer<List<Usage>> rightsProducer;
    private IChainProcessor<List<Usage>> successProcessor;
    private IChainProcessor<List<Usage>> failureProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        processor = new RightsChunkProcessor();
        rightsProducer = createMock(IProducer.class);
        successProcessor = createMock(IChainProcessor.class);
        failureProcessor = createMock(IChainProcessor.class);
        processor.setUsagesBatchSize(1000);
        Whitebox.setInternalState(processor, rightsProducer);
        processor.setSuccessProcessor(successProcessor);
        processor.setFailureProcessor(failureProcessor);
        processor.setUsageStatus(UsageStatusEnum.WORK_FOUND);
        Whitebox.setInternalState(processor.getSuccessProcessor(), createMock(IPerformanceLogger.class));
        Whitebox.setInternalState(processor.getFailureProcessor(), createMock(IPerformanceLogger.class));
    }

    @Test
    public void testProcessUsage() {
        List<Usage> usages = buildUsage(UsageStatusEnum.NEW);
        rightsProducer.send(usages);
        expectLastCall().once();
        replay(rightsProducer);
        processor.process(usages);
        verify(rightsProducer);
    }

    @Test
    public void testProcessResultSuccess() {
        List<Usage> usages = buildUsage(UsageStatusEnum.WORK_FOUND);
        successProcessor.process(usages);
        expectLastCall().once();
        expect(successProcessor.getChainProcessorType()).andReturn(ChainProcessorTypeEnum.RH_TAX).once();
        replay(successProcessor, failureProcessor);
        processor.executeNextChainProcessor(usages, usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
        verify(successProcessor, failureProcessor);
    }

    @Test
    public void testProcessResultFailure() {
        List<Usage> usages = buildUsage(UsageStatusEnum.WORK_NOT_FOUND);
        failureProcessor.process(usages);
        expectLastCall().once();
        expect(failureProcessor.getChainProcessorType()).andReturn(ChainProcessorTypeEnum.DELETE).once();
        replay(successProcessor, failureProcessor);
        processor.executeNextChainProcessor(usages, usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
        verify(successProcessor, failureProcessor);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.RIGHTS, processor.getChainProcessorType());
    }

    private List<Usage> buildUsage(UsageStatusEnum status) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setProductFamily("NTS");
        usage.setStatus(status);
        return Collections.singletonList(usage);
    }
}
