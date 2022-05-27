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
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmRightsProcessor}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmRightsProcessorTest {

    private UdmRightsProcessor processor;
    private IProducer<List<UdmUsage>> rightsProducer;
    private IChainProcessor<UdmUsage> successProcessor;
    private IChainProcessor<UdmUsage> failureProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        processor = new UdmRightsProcessor();
        rightsProducer = createMock(IProducer.class);
        successProcessor = createMock(IChainProcessor.class);
        failureProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(processor, "usagesBatchSize", 1000);
        Whitebox.setInternalState(processor, rightsProducer);
        processor.setSuccessProcessor(successProcessor);
        processor.setFailureProcessor(failureProcessor);
        processor.setUsageStatus(UsageStatusEnum.WORK_FOUND);
    }

    @Test
    public void testProcessUsage() {
        List<UdmUsage> udmUsages = buildUdmUsages(UsageStatusEnum.WORK_FOUND);
        rightsProducer.send(udmUsages);
        expectLastCall().once();
        replay(rightsProducer);
        processor.process(udmUsages);
        verify(rightsProducer);
    }

    @Test
    @Ignore
    public void testProcessResultSuccess() {
        List<UdmUsage> udmUsages = buildUdmUsages(UsageStatusEnum.WORK_FOUND);
        successProcessor.process(udmUsages);
        expectLastCall().once();
        replay(successProcessor, failureProcessor);
        processor.executeNextChainProcessor(udmUsages, usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
        verify(successProcessor, failureProcessor);
    }

    @Test
    @Ignore
    public void testProcessResultFailure() {
        List<UdmUsage> udmUsages = buildUdmUsages(UsageStatusEnum.WORK_NOT_FOUND);
        failureProcessor.process(udmUsages);
        expectLastCall().once();
        replay(successProcessor, failureProcessor);
        processor.executeNextChainProcessor(udmUsages, usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
        verify(successProcessor, failureProcessor);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.RIGHTS, processor.getChainProcessorType());
    }

    private List<UdmUsage> buildUdmUsages(UsageStatusEnum status) {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId("14348f5e-cbf5-46b5-8289-d31942f6338e");
        udmUsage.setStatus(status);
        return Collections.singletonList(udmUsage);
    }
}
