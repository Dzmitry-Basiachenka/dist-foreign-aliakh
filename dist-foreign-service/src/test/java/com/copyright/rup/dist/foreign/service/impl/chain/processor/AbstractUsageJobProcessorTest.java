package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Verifies {@link AbstractUsageJobProcessor}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 3/4/2020
 *
 * @author Stanislau Rudak
 * @author Aliaksandr Liakh
 */
public class AbstractUsageJobProcessorTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private UsageJobProcessorMock processor;
    private Consumer<List<Usage>> usageConsumer;
    private IUsageService usageService;
    private IAaclUsageService aaclUsageService;
    private ISalUsageService salUsageService;
    private IChainProcessor<Usage> successProcessor;
    private IChainProcessor<Usage> failureProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        processor = new UsageJobProcessorMock();
        usageConsumer = createMock(Consumer.class);
        usageService = createMock(IUsageService.class);
        aaclUsageService = createMock(IAaclUsageService.class);
        salUsageService = createMock(ISalUsageService.class);
        successProcessor = createMock(IChainProcessor.class);
        failureProcessor = createMock(IChainProcessor.class);
        processor.setUsagesBatchSize(1000);
        Whitebox.setInternalState(processor, usageService);
        Whitebox.setInternalState(processor, aaclUsageService);
        Whitebox.setInternalState(processor, salUsageService);
        processor.setSuccessProcessor(successProcessor);
        processor.setFailureProcessor(failureProcessor);
        processor.setUsageStatus(UsageStatusEnum.NEW);
        processor.init();
    }

    @Test
    public void testJobProcessForFas() {
        Usage usage1 = buildUsage();
        Usage usage2 = buildUsage();
        List<String> usageIds = Arrays.asList(usage1.getId(), usage2.getId());
        expect(usageService.getUsageIdsByStatusAndProductFamily(eq(UsageStatusEnum.NEW), eq(FAS_PRODUCT_FAMILY)))
            .andReturn(usageIds).once();
        expect(usageService.getUsagesByIds(usageIds)).andReturn(Arrays.asList(usage1, usage2)).once();
        usageConsumer.accept(Collections.singletonList(usage1));
        expectLastCall().once();
        usageConsumer.accept(Collections.singletonList(usage2));
        expectLastCall().once();
        replay(usageService, usageConsumer, aaclUsageService, successProcessor, failureProcessor);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=FAS, UsagesCount=2"),
            processor.jobProcess(FAS_PRODUCT_FAMILY));
        verify(usageService, usageConsumer, aaclUsageService, successProcessor, failureProcessor);
    }

    @Test
    public void testJobProcessForFas2() {
        Usage usage1 = buildUsage();
        Usage usage2 = buildUsage();
        List<String> usageIds = Arrays.asList(usage1.getId(), usage2.getId());
        expect(usageService.getUsageIdsByStatusAndProductFamily(UsageStatusEnum.NEW, "FAS2"))
            .andReturn(usageIds).once();
        expect(usageService.getUsagesByIds(eq(usageIds))).andReturn(Arrays.asList(usage1, usage2)).once();
        usageConsumer.accept(Collections.singletonList(usage1));
        expectLastCall().once();
        usageConsumer.accept(Collections.singletonList(usage2));
        expectLastCall().once();
        replay(usageService, usageConsumer, aaclUsageService, successProcessor, failureProcessor);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=FAS2, UsagesCount=2"),
            processor.jobProcess("FAS2"));
        verify(usageService, usageConsumer, aaclUsageService, successProcessor, failureProcessor);
    }

    @Test
    public void testJobProcessForNts() {
        Usage usage1 = buildUsage();
        Usage usage2 = buildUsage();
        List<String> usageIds = Arrays.asList(usage1.getId(), usage2.getId());
        expect(usageService.getUsageIdsByStatusAndProductFamily(UsageStatusEnum.NEW, "NTS"))
            .andReturn(usageIds).once();
        expect(usageService.getUsagesByIds(eq(usageIds))).andReturn(Arrays.asList(usage1, usage2)).once();
        usageConsumer.accept(Collections.singletonList(usage1));
        expectLastCall().once();
        usageConsumer.accept(Collections.singletonList(usage2));
        expectLastCall().once();
        replay(usageService, usageConsumer, aaclUsageService, successProcessor, failureProcessor);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=NTS, UsagesCount=2"),
            processor.jobProcess("NTS"));
        verify(usageService, usageConsumer, aaclUsageService, successProcessor, failureProcessor);
    }

    @Test
    public void testJobProcessForAacl() {
        Usage usage1 = buildUsage();
        Usage usage2 = buildUsage();
        List<String> usageIds = Arrays.asList(usage1.getId(), usage2.getId());
        expect(usageService.getUsageIdsByStatusAndProductFamily(UsageStatusEnum.NEW, "AACL"))
            .andReturn(usageIds).once();
        expect(aaclUsageService.getUsagesByIds(eq(usageIds))).andReturn(Arrays.asList(usage1, usage2)).once();
        usageConsumer.accept(Collections.singletonList(usage1));
        expectLastCall().once();
        usageConsumer.accept(Collections.singletonList(usage2));
        expectLastCall().once();
        replay(usageService, usageConsumer, aaclUsageService, successProcessor, failureProcessor);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=AACL, UsagesCount=2"),
            processor.jobProcess("AACL"));
        verify(usageService, usageConsumer, aaclUsageService, successProcessor, failureProcessor);
    }

    @Test
    public void testJobProcessForSal() {
        Usage usage1 = buildUsage();
        Usage usage2 = buildUsage();
        List<String> usageIds = Arrays.asList(usage1.getId(), usage2.getId());
        expect(usageService.getUsageIdsByStatusAndProductFamily(UsageStatusEnum.NEW, "SAL"))
            .andReturn(usageIds).once();
        expect(salUsageService.getUsagesByIds(eq(usageIds))).andReturn(Arrays.asList(usage1, usage2)).once();
        usageConsumer.accept(Collections.singletonList(usage1));
        expectLastCall().once();
        usageConsumer.accept(Collections.singletonList(usage2));
        expectLastCall().once();
        replay(usageService, usageConsumer, salUsageService);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=SAL, UsagesCount=2"),
            processor.jobProcess("SAL"));
        verify(usageService, usageConsumer, salUsageService);
    }

    @Test
    public void testJobProcessForUnknownProductFamily() {
        replay(usageService, usageConsumer, aaclUsageService, salUsageService, successProcessor, failureProcessor);
        try {
            processor.jobProcess("CNTPUR");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Product family is not registered. ProductFamily=CNTPUR, " +
                "RegisteredProductFamilies=[FAS, FAS2, NTS, AACL, SAL]", e.getMessage());
        }
        verify(usageService, usageConsumer, aaclUsageService, successProcessor, failureProcessor);
    }

    @Test
    public void testJobProcessSkipped() {
        expect(usageService.getUsageIdsByStatusAndProductFamily(UsageStatusEnum.NEW, FAS_PRODUCT_FAMILY))
            .andReturn(Collections.emptyList()).once();
        replay(usageService, usageConsumer, aaclUsageService, salUsageService, successProcessor, failureProcessor);
        assertEquals(new JobInfo(JobStatusEnum.SKIPPED, "ProductFamily=FAS, Reason=There are no usages"),
            processor.jobProcess(FAS_PRODUCT_FAMILY));
        verify(usageService, usageConsumer, aaclUsageService, salUsageService, successProcessor, failureProcessor);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setStatus(UsageStatusEnum.NEW);
        return usage;
    }

    private class UsageJobProcessorMock extends AbstractUsageJobProcessor {

        @Override
        public void process(List<Usage> usages) {
            usageConsumer.accept(usages);
        }

        @Override
        public ChainProcessorTypeEnum getChainProcessorType() {
            return null;
        }
    }
}
