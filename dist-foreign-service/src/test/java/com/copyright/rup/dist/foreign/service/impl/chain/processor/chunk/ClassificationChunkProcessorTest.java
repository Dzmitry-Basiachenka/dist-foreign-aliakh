package com.copyright.rup.dist.foreign.service.impl.chain.processor.chunk;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.nts.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.executor.IPerformanceLogger;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link ClassificationChunkProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/2019
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class ClassificationChunkProcessorTest {

    private static final Long WR_WRK_INST_1 = 11233251L;
    private static final Long WR_WRK_INST_2 = 17367895L;

    private ClassificationChunkProcessor classificationProcessor;
    private IWorkClassificationService workClassificationService;
    private IChainProcessor<List<Usage>> nonBelletristicProcessor;
    private IChainProcessor<List<Usage>> unclassifiedStatusProcessor;

    @Before
    public void setUp() {
        classificationProcessor = new ClassificationChunkProcessor();
        workClassificationService = createMock(IWorkClassificationService.class);
        nonBelletristicProcessor = createMock(IChainProcessor.class);
        unclassifiedStatusProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(classificationProcessor, workClassificationService);
        classificationProcessor.setSuccessProcessor(nonBelletristicProcessor);
        classificationProcessor.setFailureProcessor(unclassifiedStatusProcessor);
        Whitebox.setInternalState(classificationProcessor.getSuccessProcessor(), createMock(IPerformanceLogger.class));
        Whitebox.setInternalState(classificationProcessor.getFailureProcessor(), createMock(IPerformanceLogger.class));
    }

    @Test
    public void testProcess() {
        Usage usage1 = buildUsage(WR_WRK_INST_1);
        Usage usage2 = buildUsage(WR_WRK_INST_2);
        expect(workClassificationService.getClassification(WR_WRK_INST_1)).andReturn("STM").once();
        expect(workClassificationService.getClassification(WR_WRK_INST_2)).andReturn(null).once();
        nonBelletristicProcessor.process(Collections.singletonList(usage1));
        expectLastCall().once();
        expect(nonBelletristicProcessor.getChainProcessorType()).andReturn(ChainProcessorTypeEnum.CLASSIFICATION)
            .once();
        unclassifiedStatusProcessor.process(Collections.singletonList(usage2));
        expectLastCall().once();
        expect(unclassifiedStatusProcessor.getChainProcessorType()).andReturn(ChainProcessorTypeEnum.DELETE).once();
        replay(workClassificationService, nonBelletristicProcessor, unclassifiedStatusProcessor);
        classificationProcessor.process(Lists.newArrayList(usage1, usage2));
        verify(workClassificationService, nonBelletristicProcessor, unclassifiedStatusProcessor);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.CLASSIFICATION, classificationProcessor.getChainProcessorType());
    }

    private Usage buildUsage(Long wrWrkInst) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(wrWrkInst);
        return usage;
    }
}
