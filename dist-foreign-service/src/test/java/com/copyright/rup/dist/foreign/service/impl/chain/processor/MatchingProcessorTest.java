package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.camel.IProducer;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
    private IUsageService usageService;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        processor = new MatchingProcessor();
        matchingProducer = createMock(IProducer.class);
        successProcessor = createMock(IChainProcessor.class);
        failureProcessor = createMock(IChainProcessor.class);
        usageService = createMock(IUsageService.class);
        processor.setUsageService(usageService);
        processor.setUsagesBatchSize(1000);
        processor.setMatchingProducer(matchingProducer);
        processor.setSuccessProcessor(successProcessor);
        processor.setFailureProcessor(failureProcessor);
        processor.setUsageStatus(UsageStatusEnum.NEW);
    }

    @Test
    public void testProcess() {
        Usage usage1 = buildUsage(UsageStatusEnum.NEW);
        Usage usage2 = buildUsage(UsageStatusEnum.NEW);
        List<String> usageIds = Arrays.asList(usage1.getId(), usage2.getId());
        expect(usageService.getUsageIdsByStatusAndProductFamily(eq(UsageStatusEnum.NEW), eq("FAS")))
            .andReturn(usageIds)
            .once();
        expect(usageService.getUsagesByIds(eq(usageIds)))
            .andReturn(Arrays.asList(usage1, usage2))
            .once();
        matchingProducer.send(usage1);
        expectLastCall().once();
        matchingProducer.send(usage2);
        expectLastCall().once();
        replay(usageService, matchingProducer);
        processor.jobProcess("FAS");
        verify(usageService, matchingProducer);
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
