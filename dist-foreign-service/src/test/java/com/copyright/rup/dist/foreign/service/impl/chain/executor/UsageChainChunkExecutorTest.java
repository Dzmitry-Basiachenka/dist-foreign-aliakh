package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.api.processor.IUsageJobProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.processor.AbstractUsageChainChunkProcessor;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsageChainChunkExecutor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
 * @author Aliaksandr Liakh
 */
public class UsageChainChunkExecutorTest {

    private UsageChainChunkExecutor executor;
    private IChainProcessor<List<Usage>> fasProcessor;
    private IChainProcessor<List<Usage>> ntsProcessor;
    private IChainProcessor<List<Usage>> aaclProcessor;
    private IChainProcessor<List<Usage>> fasEligibilityProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        executor = new UsageChainChunkExecutor();
        fasProcessor = createMock(IChainProcessor.class);
        ntsProcessor = createMock(IChainProcessor.class);
        aaclProcessor = createMock(IChainProcessor.class);
        fasEligibilityProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(executor, "fasProcessor", fasProcessor);
        Whitebox.setInternalState(executor, "ntsProcessor", ntsProcessor);
        Whitebox.setInternalState(executor, "aaclProcessor", aaclProcessor);
        Whitebox.setInternalState(executor, "chunkSize", 1);
        executor.init();
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
        expect(fasProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .times(2);
        expect(ntsProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .once();
        expect(aaclProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .once();
        replay(fasProcessor, ntsProcessor, aaclProcessor);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED,
                "ProductFamily=FAS, UsagesCount=2; ProductFamily=FAS2, UsagesCount=5; " +
                    "ProductFamily=NTS, Reason=There are no usages; ProductFamily=AACL, Reason=There are no usages"),
            executor.execute(ChainProcessorTypeEnum.RIGHTS));
        verify(fasProcessor, ntsProcessor, aaclProcessor);
    }

    @Test
    public void testExecuteProcessor() {
        List<Usage> usages = buildUsages();
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

    private List<Usage> buildUsages() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setProductFamily("FAS");
        return Collections.singletonList(usage);
    }

    private static class MockProcessor extends AbstractUsageChainChunkProcessor implements IUsageJobProcessor {

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
