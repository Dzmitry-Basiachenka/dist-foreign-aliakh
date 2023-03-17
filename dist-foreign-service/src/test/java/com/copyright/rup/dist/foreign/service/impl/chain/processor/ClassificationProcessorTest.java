package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.nts.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link ClassificationProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/2019
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class ClassificationProcessorTest {

    private static final Long WR_WRK_INST_1 = 11233251L;
    private static final Long WR_WRK_INST_2 = 17367895L;

    private ClassificationProcessor classificationProcessor;
    private IWorkClassificationService workClassificationService;
    private IChainProcessor<Usage> nonBelletristicProcessor;
    private IChainProcessor<Usage> unclassifiedStatusProcessor;

    @Before
    public void setUp() {
        classificationProcessor = new ClassificationProcessor();
        workClassificationService = createMock(IWorkClassificationService.class);
        nonBelletristicProcessor = createMock(IChainProcessor.class);
        unclassifiedStatusProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(classificationProcessor, workClassificationService);
        classificationProcessor.setSuccessProcessor(nonBelletristicProcessor);
        classificationProcessor.setFailureProcessor(unclassifiedStatusProcessor);
    }

    @Test
    public void testProcess() {
        Usage usage1 = buildUsage("e3d8d7d5-0c8f-477a-af09-607088c39627", WR_WRK_INST_1);
        Usage usage2 = buildUsage("f3c42fd7-6893-462a-ab12-923db420273b", WR_WRK_INST_2);
        expect(workClassificationService.getClassification(WR_WRK_INST_1)).andReturn("STM").once();
        expect(workClassificationService.getClassification(WR_WRK_INST_2)).andReturn(null).once();
        nonBelletristicProcessor.process(List.of(usage1));
        expectLastCall().once();
        unclassifiedStatusProcessor.process(List.of(usage2));
        expectLastCall().once();
        replay(workClassificationService, nonBelletristicProcessor, unclassifiedStatusProcessor);
        classificationProcessor.process(List.of(usage1, usage2));
        verify(workClassificationService, nonBelletristicProcessor, unclassifiedStatusProcessor);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.CLASSIFICATION, classificationProcessor.getChainProcessorType());
    }

    private Usage buildUsage(String usageId, Long wrWrkInst) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(wrWrkInst);
        return usage;
    }
}
