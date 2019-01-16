package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
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
            .andReturn(new MockProcessor())
            .times(2);
        expect(ntsProcessor.getSuccessProcessor())
            .andReturn(new MockProcessor())
            .once();
        expect(fasProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .times(2);
        expect(ntsProcessor.getFailureProcessor())
            .andReturn(new MockProcessor())
            .once();
        replay(fasProcessor, ntsProcessor);
        executor.execute(ChainProcessorTypeEnum.RIGHTS);
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
            .times(1);
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

        @Override
        public void process(Usage item) {
            // Empty method
        }

        @Override
        public void jobProcess(String productFamily) {
            // Empty method
        }

        @Override
        public ChainProcessorTypeEnum getChainProcessorType() {
            return ChainProcessorTypeEnum.ELIGIBILITY;
        }
    }
}
