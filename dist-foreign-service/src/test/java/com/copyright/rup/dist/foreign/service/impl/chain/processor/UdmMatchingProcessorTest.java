package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmMatchingProcessor}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Ihar Suvorau
 */
public class UdmMatchingProcessorTest {

    private UdmMatchingProcessor processor;
    private IProducer<List<UdmUsage>> matchingProducer;
    private IChainProcessor<UdmUsage> successProcessor;
    private IChainProcessor<UdmUsage> failureProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        processor = new UdmMatchingProcessor();
        matchingProducer = createMock(IProducer.class);
        successProcessor = createMock(IChainProcessor.class);
        failureProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(processor, "usagesBatchSize", 1000);
        Whitebox.setInternalState(processor, matchingProducer);
        processor.setSuccessProcessor(successProcessor);
        processor.setFailureProcessor(failureProcessor);
        processor.setUsageStatus(UsageStatusEnum.NEW);
    }

    @Test
    public void testProcess() {
        List<UdmUsage> usages = buildUsages(UsageStatusEnum.NEW);
        matchingProducer.send(usages);
        expectLastCall().once();
        replay(matchingProducer);
        processor.process(usages);
        verify(matchingProducer);
    }

    @Test
    public void testProcessResultSuccess() {
        List<UdmUsage> usages = buildUsages(UsageStatusEnum.WORK_FOUND);
        successProcessor.process(usages);
        expectLastCall().once();
        replay(successProcessor, failureProcessor);
        processor.executeNextChainProcessor(usages, usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
        verify(successProcessor, failureProcessor);
    }

    @Test
    public void testProcessResultFailure() {
        List<UdmUsage> usages = buildUsages(UsageStatusEnum.WORK_NOT_FOUND);
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

    private List<UdmUsage> buildUsages(UsageStatusEnum status) {
        UdmUsage usage = new UdmUsage();
        usage.setId("9c61a40f-7980-48d0-9a9d-db1011bf7eaf");
        usage.setStatus(status);
        return Collections.singletonList(usage);
    }
}
