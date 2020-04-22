package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.nts.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;
import com.copyright.rup.dist.foreign.service.impl.chain.executor.IPerformanceLogger;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link ClassificationProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/2019
 *
 * @author Pavel Liakh
 */
public class ClassificationProcessorTest {

    private static final Long WR_WRK_INST = 11233251L;

    private ClassificationProcessor classificationProcessor;
    private IWorkClassificationService workClassificationServiceMock;
    private IChainProcessor<Usage> nonBelletristicProcessorMock;
    private IChainProcessor<Usage> unclassifiedStatusProcessorMock;

    @Before
    public void setUp() {
        classificationProcessor = new ClassificationProcessor();
        workClassificationServiceMock = createMock(IWorkClassificationService.class);
        nonBelletristicProcessorMock = createMock(IChainProcessor.class);
        unclassifiedStatusProcessorMock = createMock(IChainProcessor.class);
        classificationProcessor.setWorkClassificationService(workClassificationServiceMock);
        classificationProcessor.setSuccessProcessor(nonBelletristicProcessorMock);
        classificationProcessor.setFailureProcessor(unclassifiedStatusProcessorMock);
        Whitebox.setInternalState(classificationProcessor.getSuccessProcessor(), createMock(IPerformanceLogger.class));
        Whitebox.setInternalState(classificationProcessor.getFailureProcessor(), createMock(IPerformanceLogger.class));
    }

    @Test
    public void testProcessWithStm() {
        Usage usage = buildUsage();
        expect(workClassificationServiceMock.getClassification(WR_WRK_INST)).andReturn("STM").once();
        nonBelletristicProcessorMock.process(usage);
        expectLastCall().once();
        expect(nonBelletristicProcessorMock.getChainProcessorType()).andReturn(ChainProcessorTypeEnum.CLASSIFICATION)
            .once();
        replay(workClassificationServiceMock, nonBelletristicProcessorMock);
        classificationProcessor.process(usage);
        verify(workClassificationServiceMock, nonBelletristicProcessorMock);
    }

    @Test
    public void testProcessWithAbsentClassification() {
        Usage usage = buildUsage();
        expect(workClassificationServiceMock.getClassification(WR_WRK_INST)).andReturn(null).once();
        unclassifiedStatusProcessorMock.process(usage);
        expectLastCall().once();
        expect(unclassifiedStatusProcessorMock.getChainProcessorType()).andReturn(ChainProcessorTypeEnum.DELETE)
            .once();
        replay(workClassificationServiceMock, unclassifiedStatusProcessorMock);
        classificationProcessor.process(usage);
        verify(workClassificationServiceMock, unclassifiedStatusProcessorMock);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(WR_WRK_INST);
        return usage;
    }
}
