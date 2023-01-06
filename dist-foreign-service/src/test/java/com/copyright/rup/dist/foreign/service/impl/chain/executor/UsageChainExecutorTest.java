package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.processor.IJobProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.processor.AbstractChainProcessor;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Map;

/**
 * Verifies {@link UsageChainExecutor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class UsageChainExecutorTest {

    private UsageChainExecutor executor;
    private IChainProcessor<Usage> fasProcessor;
    private IChainProcessor<Usage> ntsProcessor;
    private IChainProcessor<Usage> aaclProcessor;
    private IChainProcessor<Usage> salProcessor;
    private IChainProcessor<Usage> aclciProcessor;
    private IChainProcessor<Usage> fasEligibilityProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        executor = new UsageChainExecutor();
        fasProcessor = createMock(IChainProcessor.class);
        ntsProcessor = createMock(IChainProcessor.class);
        aaclProcessor = createMock(IChainProcessor.class);
        salProcessor = createMock(IChainProcessor.class);
        aclciProcessor = createMock(IChainProcessor.class);
        fasEligibilityProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(executor, "fasProcessor", fasProcessor);
        Whitebox.setInternalState(executor, "ntsProcessor", ntsProcessor);
        Whitebox.setInternalState(executor, "aaclProcessor", aaclProcessor);
        Whitebox.setInternalState(executor, "salProcessor", salProcessor);
        Whitebox.setInternalState(executor, "aclciProcessor", aclciProcessor);
        Whitebox.setInternalState(executor, "chunkSize", 1);
        executor.postConstruct();
    }

    @Test
    public void testGetProductFamilyFunction() {
        assertEquals("FAS2", executor.getProductFamilyFunction().apply(buildUsage("FAS2")));
        assertEquals("SAL", executor.getProductFamilyFunction().apply(buildUsage("SAL")));
        assertEquals("NTS", executor.getProductFamilyFunction().apply(buildUsage("NTS")));
        assertEquals("ACLCI", executor.getProductFamilyFunction().apply(buildUsage("ACLCI")));
        assertEquals(StringUtils.EMPTY, executor.getProductFamilyFunction().apply(buildUsage(StringUtils.EMPTY)));
        assertNull(executor.getProductFamilyFunction().apply(buildUsage(null)));
    }

    @Test
    public void testGetProductFamilyToProcessorMap() {
        Map<String, IChainProcessor<Usage>> expectedProductFamilyToProcessorMap =
            ImmutableMap.<String, IChainProcessor<Usage>>builder()
                .put("FAS", fasProcessor)
                .put("FAS2", fasProcessor)
                .put("NTS", ntsProcessor)
                .put("AACL", aaclProcessor)
                .put("SAL", salProcessor)
                .put("ACLCI", aclciProcessor)
                .build();
        Map<String, IChainProcessor<Usage>> actualProductFamilyToProcessorMap =
            executor.getProductFamilyToProcessorMap();
        expectedProductFamilyToProcessorMap.entrySet().forEach((entry) ->
            assertTrue(actualProductFamilyToProcessorMap.entrySet().contains(entry)));
    }

    @Test
    public void testExecuteJobProcessor() {
        expect(fasProcessor.getSuccessProcessor())
            .andReturn(new MockProcessor(JobStatusEnum.FINISHED, "ProductFamily=FAS, UsagesCount=2"))
            .once();
        expect(fasProcessor.getSuccessProcessor())
            .andReturn(new MockProcessor(JobStatusEnum.FINISHED, "ProductFamily=FAS2, UsagesCount=5"))
            .once();
        expect(ntsProcessor.getSuccessProcessor())
            .andReturn(new MockProcessor(JobStatusEnum.SKIPPED, "ProductFamily=NTS, Reason=There are no usages"))
            .once();
        expect(aaclProcessor.getSuccessProcessor())
            .andReturn(new MockProcessor(JobStatusEnum.SKIPPED, "ProductFamily=AACL, Reason=There are no usages"))
            .once();
        expect(salProcessor.getSuccessProcessor())
            .andReturn(new MockProcessor(JobStatusEnum.SKIPPED, "ProductFamily=SAL, Reason=There are no usages"))
            .once();
        expect(aclciProcessor.getSuccessProcessor())
            .andReturn(new MockProcessor(JobStatusEnum.SKIPPED, "ProductFamily=ACLCI, Reason=There are no usages"))
            .once();
        expect(fasProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .times(2);
        expect(ntsProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .once();
        expect(aaclProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .once();
        expect(salProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .once();
        expect(aclciProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .once();
        replay(fasProcessor, ntsProcessor, aaclProcessor, salProcessor, aclciProcessor);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED,
                "ProductFamily=FAS, UsagesCount=2; ProductFamily=FAS2, UsagesCount=5; " +
                    "ProductFamily=NTS, Reason=There are no usages; ProductFamily=AACL, Reason=There are no usages; " +
                    "ProductFamily=SAL, Reason=There are no usages; ProductFamily=ACLCI, Reason=There are no usages"),
            executor.execute(ChainProcessorTypeEnum.RIGHTS));
        verify(fasProcessor, ntsProcessor, aaclProcessor, salProcessor, aclciProcessor);
    }

    @Test
    public void testExecuteProcessor() {
        List<Usage> usages = List.of(buildUsage("FAS"));
        expect(fasProcessor.getChainProcessorType())
            .andReturn(ChainProcessorTypeEnum.RIGHTS)
            .once();
        expect(fasProcessor.getSuccessProcessor())
            .andReturn(fasEligibilityProcessor)
            .once();
        expect(fasProcessor.getFailureProcessor())
            .andReturn(null)
            .once();
        expect(fasEligibilityProcessor.getChainProcessorType())
            .andReturn(ChainProcessorTypeEnum.ELIGIBILITY)
            .once();
        fasEligibilityProcessor.process(usages);
        replay(fasProcessor, ntsProcessor, fasEligibilityProcessor, aaclProcessor);
        executor.execute(usages, ChainProcessorTypeEnum.ELIGIBILITY);
        verify(fasProcessor, ntsProcessor, fasEligibilityProcessor, aaclProcessor);
    }

    private Usage buildUsage(String productFamily) {
        Usage usage = new Usage();
        usage.setProductFamily(productFamily);
        return usage;
    }

    private static class MockProcessor extends AbstractChainProcessor<Usage> implements IJobProcessor {

        private final JobInfo jobInfo;
        private final ChainProcessorTypeEnum chainProcessorType;

        MockProcessor() {
            jobInfo = new JobInfo();
            chainProcessorType = ChainProcessorTypeEnum.ELIGIBILITY;
        }

        MockProcessor(JobStatusEnum statusEnum, String reason) {
            jobInfo = new JobInfo(statusEnum, reason);
            chainProcessorType = ChainProcessorTypeEnum.RIGHTS;
        }

        @Override
        public void process(List<Usage> usages) {
            // empty method
        }

        @Override
        public JobInfo jobProcess(String productFamily) {
            return jobInfo;
        }

        @Override
        public ChainProcessorTypeEnum getChainProcessorType() {
            return chainProcessorType;
        }
    }
}
