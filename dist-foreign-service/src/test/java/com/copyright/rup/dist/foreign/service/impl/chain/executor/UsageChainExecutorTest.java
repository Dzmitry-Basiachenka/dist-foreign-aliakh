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
import com.copyright.rup.dist.foreign.service.impl.chain.processor.AbstractUsageChainProcessor;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link UsageChainExecutor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/19/2018
 *
 * @author Uladzislau Shalamitski
 */
public class UsageChainExecutorTest {

    private UsageChainExecutor executor;
    private IChainProcessor<Usage> fasProcessor;
    private IChainProcessor<Usage> ntsProcessor;
    private IChainProcessor<Usage> fasEligibilityProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        executor = new UsageChainExecutor();
        fasProcessor = createMock(IChainProcessor.class);
        ntsProcessor = createMock(IChainProcessor.class);
        fasEligibilityProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(executor, "fasProcessor", fasProcessor);
        Whitebox.setInternalState(executor, "ntsProcessor", ntsProcessor);
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
        expect(fasProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .times(2);
        expect(ntsProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .once();
        replay(fasProcessor, ntsProcessor);
        assertEquals(new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=FAS, UsagesCount=2; " +
                "ProductFamily=FAS2, UsagesCount=5; ProductFamily=NTS, Reason=There are no usages"),
            executor.execute(ChainProcessorTypeEnum.RIGHTS));
        verify(fasProcessor, ntsProcessor);
    }

    @Test
    public void testExecuteProcessor() {
        Usage usage = buildUsage();
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
        fasEligibilityProcessor.process(usage);
        replay(fasProcessor, ntsProcessor, fasEligibilityProcessor);
        executor.execute(Collections.singletonList(usage), ChainProcessorTypeEnum.ELIGIBILITY);
        verify(fasProcessor, ntsProcessor, fasEligibilityProcessor);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setProductFamily("FAS");
        return usage;
    }

    private static class MockProcessor extends AbstractUsageChainProcessor implements IUsageJobProcessor {

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
        public void process(Usage item) {
            // Empty method
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
