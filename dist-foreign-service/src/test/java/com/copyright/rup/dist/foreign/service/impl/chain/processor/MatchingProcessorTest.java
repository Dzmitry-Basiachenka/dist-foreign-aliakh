package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link MatchingProcessor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class MatchingProcessorTest {

    private MatchingProcessor processor;
    private IProducer<List<Usage>> matchingProducer;
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
        Whitebox.setInternalState(processor, matchingProducer);
        processor.setSuccessProcessor(successProcessor);
        processor.setFailureProcessor(failureProcessor);
        processor.setUsageStatus(UsageStatusEnum.NEW);
    }

    @Test
    public void testProcess() {
        List<Usage> usages = buildUsages(UsageStatusEnum.NEW);
        matchingProducer.send(usages);
        expectLastCall().once();
        replay(matchingProducer);
        processor.process(usages);
        verify(matchingProducer);
    }

    @Test
    public void testProcessResultSuccess() {
        List<Usage> usages = buildUsages(UsageStatusEnum.WORK_FOUND);
        successProcessor.process(usages);
        expectLastCall().once();
        replay(successProcessor, failureProcessor);
        processor.executeNextChainProcessor(usages, usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
        verify(successProcessor, failureProcessor);
    }

    @Test
    public void testProcessResultFailure() {
        List<Usage> usages = buildUsages(UsageStatusEnum.WORK_NOT_FOUND);
        failureProcessor.process(usages);
        expectLastCall().once();
        replay(successProcessor, failureProcessor);
        processor.executeNextChainProcessor(usages, usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
        verify(successProcessor, failureProcessor);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.MATCHING, processor.getChainProcessorType());
    }

    private List<Usage> buildUsages(UsageStatusEnum status) {
        Usage usage = new Usage();
        usage.setId("0aa431c7-0bb4-414b-a6b8-797b55912da2");
        usage.setStatus(status);
        usage.setProductFamily("FAS");
        return List.of(usage);
    }
}
