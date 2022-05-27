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
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link RightsProcessor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class RightsProcessorTest {

    private RightsProcessor processor;
    private IProducer<List<Usage>> rightsProducer;
    private IChainProcessor<Usage> successProcessor;
    private IChainProcessor<Usage> failureProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        processor = new RightsProcessor();
        rightsProducer = createMock(IProducer.class);
        successProcessor = createMock(IChainProcessor.class);
        failureProcessor = createMock(IChainProcessor.class);
        processor.setUsagesBatchSize(1000);
        Whitebox.setInternalState(processor, rightsProducer);
        processor.setSuccessProcessor(successProcessor);
        processor.setFailureProcessor(failureProcessor);
        processor.setUsageStatus(UsageStatusEnum.WORK_FOUND);
    }

    @Test
    public void testProcessUsage() {
        List<Usage> usages = buildUsages(UsageStatusEnum.NEW);
        rightsProducer.send(usages);
        expectLastCall().once();
        replay(rightsProducer);
        processor.process(usages);
        verify(rightsProducer);
    }

    @Test
    @Ignore
    public void testProcessResultSuccess() {
        List<Usage> usages = buildUsages(UsageStatusEnum.WORK_FOUND);
        successProcessor.process(usages);
        expectLastCall().once();
        replay(successProcessor, failureProcessor);
        processor.executeNextChainProcessor(usages, usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus());
        verify(successProcessor, failureProcessor);
    }

    @Test
    @Ignore
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
        assertEquals(ChainProcessorTypeEnum.RIGHTS, processor.getChainProcessorType());
    }

    private List<Usage> buildUsages(UsageStatusEnum status) {
        Usage usage = new Usage();
        usage.setId("50f1c35f-40db-443b-a19e-f5fab1f7362e");
        usage.setProductFamily("NTS");
        usage.setStatus(status);
        return Collections.singletonList(usage);
    }
}
