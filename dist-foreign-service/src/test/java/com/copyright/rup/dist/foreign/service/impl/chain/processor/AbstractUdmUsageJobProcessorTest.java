package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Verifies {@link AbstractUdmJobProcessor}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Ihar Suvorau
 */
public class AbstractUdmUsageJobProcessorTest {

    private static final String ACL_PRODUCT_FAMILY = "ACL";

    private JobProcessorMock processor;
    private Consumer<List<UdmUsage>> usageConsumer;
    private IUdmUsageService udmUsageService;
    private IChainProcessor<UdmUsage> successProcessor;
    private IChainProcessor<UdmUsage> failureProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        processor = new JobProcessorMock();
        usageConsumer = createMock(Consumer.class);
        udmUsageService = createMock(IUdmUsageService.class);
        successProcessor = createMock(IChainProcessor.class);
        failureProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(processor, "usagesBatchSize", 1000);
        Whitebox.setInternalState(processor, udmUsageService);
        processor.setSuccessProcessor(successProcessor);
        processor.setFailureProcessor(failureProcessor);
        processor.setUsageStatus(UsageStatusEnum.NEW);
    }

    @Test
    public void testJobProcess() {
        UdmUsage usage1 = buildUsage("cff900a1-8f8a-4a49-8171-d3ae5dba1cac");
        UdmUsage usage2 = buildUsage("896c6aac-87c8-4058-be1d-745b8003712b");
        List<String> usageIds = Arrays.asList(usage1.getId(), usage2.getId());
        expect(udmUsageService.getUdmUsageIdsByStatus(UsageStatusEnum.NEW)).andReturn(usageIds).once();
        expect(udmUsageService.getUdmUsagesByIds(usageIds)).andReturn(Arrays.asList(usage1, usage2)).once();
        usageConsumer.accept(List.of(usage1));
        expectLastCall().once();
        usageConsumer.accept(List.of(usage2));
        expectLastCall().once();
        replay(udmUsageService, usageConsumer, successProcessor, failureProcessor);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=ACL_UDM, UsagesCount=2"),
            processor.jobProcess(ACL_PRODUCT_FAMILY));
        verify(udmUsageService, usageConsumer, successProcessor, failureProcessor);
    }

    @Test
    public void testJobProcessSkipped() {
        expect(udmUsageService.getUdmUsageIdsByStatus(UsageStatusEnum.NEW))
            .andReturn(Collections.emptyList()).once();
        replay(udmUsageService, usageConsumer, successProcessor, failureProcessor);
        assertEquals(new JobInfo(JobStatusEnum.SKIPPED, "ProductFamily=ACL_UDM, Reason=There are no usages"),
            processor.jobProcess(ACL_PRODUCT_FAMILY));
        verify(udmUsageService, usageConsumer, successProcessor, failureProcessor);
    }

    private UdmUsage buildUsage(String usageId) {
        UdmUsage usage = new UdmUsage();
        usage.setId(usageId);
        usage.setStatus(UsageStatusEnum.NEW);
        return usage;
    }

    private class JobProcessorMock extends AbstractUdmJobProcessor {

        @Override
        public void process(List<UdmUsage> usages) {
            usageConsumer.accept(usages);
        }

        @Override
        public ChainProcessorTypeEnum getChainProcessorType() {
            return null;
        }
    }
}
