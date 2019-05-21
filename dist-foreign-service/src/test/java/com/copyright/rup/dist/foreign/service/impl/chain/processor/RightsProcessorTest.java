package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
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
 */
public class RightsProcessorTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private RightsProcessor processor;
    private IProducer<Usage> rightsProducer;
    private IUsageService usageService;
    private IChainProcessor<Usage> successProcessor;
    private IChainProcessor<Usage> failureProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        processor = new RightsProcessor();
        rightsProducer = createMock(IProducer.class);
        usageService = createMock(IUsageService.class);
        successProcessor = createMock(IChainProcessor.class);
        failureProcessor = createMock(IChainProcessor.class);
        processor.setUsageService(usageService);
        processor.setUsagesBatchSize(1000);
        processor.setProducer(rightsProducer);
        processor.setSuccessProcessor(successProcessor);
        processor.setFailureProcessor(failureProcessor);
        processor.setUsageStatus(UsageStatusEnum.WORK_FOUND);
    }

    @Test
    public void testJobProcess() {
        Usage usage1 = buildUsage(UsageStatusEnum.WORK_FOUND);
        Usage usage2 = buildUsage(UsageStatusEnum.WORK_FOUND);
        List<String> usageIds = Arrays.asList(usage1.getId(), usage2.getId());
        expect(usageService.getUsageIdsByStatusAndProductFamily(
            eq(UsageStatusEnum.WORK_FOUND), eq(FAS_PRODUCT_FAMILY)))
            .andReturn(usageIds).once();
        expect(usageService.getUsagesByIds(eq(usageIds))).andReturn(Arrays.asList(usage1, usage2)).once();
        rightsProducer.send(usage1);
        expectLastCall().once();
        rightsProducer.send(usage2);
        expectLastCall().once();
        replay(usageService, rightsProducer);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=FAS, UsagesCount=2"),
            processor.jobProcess(FAS_PRODUCT_FAMILY));
        verify(usageService, rightsProducer);
    }

    @Test
    public void testJobProcessSkipped() {
        expect(usageService.getUsageIdsByStatusAndProductFamily(eq(UsageStatusEnum.WORK_FOUND), eq(FAS_PRODUCT_FAMILY)))
            .andReturn(Collections.emptyList()).once();
        replay(usageService, rightsProducer);
        assertEquals(new JobInfo(JobStatusEnum.SKIPPED, "ProductFamily=FAS, Reason=There are no usages"),
            processor.jobProcess(FAS_PRODUCT_FAMILY));
        verify(usageService, rightsProducer);
    }

    @Test
    public void testProcessUsage() {
        Usage usage = buildUsage(UsageStatusEnum.NEW);
        rightsProducer.send(usage);
        expectLastCall().once();
        replay(rightsProducer);
        processor.process(usage);
        verify(rightsProducer);
    }

    @Test
    public void testProcessResultSuccess() {
        Usage usage = buildUsage(UsageStatusEnum.WORK_FOUND);
        successProcessor.process(usage);
        expectLastCall().once();
        replay(successProcessor, failureProcessor);
        processor.executeNextProcessor(usage, usage1 -> UsageStatusEnum.WORK_FOUND == usage1.getStatus());
        verify(successProcessor, failureProcessor);
    }

    @Test
    public void testProcessResultFailure() {
        Usage usage = buildUsage(UsageStatusEnum.WORK_NOT_FOUND);
        failureProcessor.process(usage);
        expectLastCall().once();
        replay(successProcessor, failureProcessor);
        processor.executeNextProcessor(usage, usage1 -> UsageStatusEnum.WORK_FOUND == usage1.getStatus());
        verify(successProcessor, failureProcessor);
    }

    private Usage buildUsage(UsageStatusEnum status) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setStatus(status);
        return usage;
    }
}
