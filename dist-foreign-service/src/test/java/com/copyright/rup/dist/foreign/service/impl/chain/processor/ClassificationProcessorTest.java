package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

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
    private IUsageRepository usageRepositoryMock;
    private IWorkClassificationService workClassificationServiceMock;
    private IChainProcessor<Usage> deleteProcessorMock;
    private IChainProcessor<Usage> eligibilityProcessorMock;

    @Before
    public void setUp() {
        classificationProcessor = new ClassificationProcessor();
        usageRepositoryMock = createMock(IUsageRepository.class);
        workClassificationServiceMock = createMock(IWorkClassificationService.class);
        deleteProcessorMock = createMock(IChainProcessor.class);
        eligibilityProcessorMock = createMock(IChainProcessor.class);
        classificationProcessor.setUsageRepository(usageRepositoryMock);
        classificationProcessor.setWorkClassificationService(workClassificationServiceMock);
        classificationProcessor.setSuccessProcessor(deleteProcessorMock);
        classificationProcessor.setFailureProcessor(eligibilityProcessorMock);
    }

    @Test
    public void testProcessWithStm() {
        Usage usage = buildUsage();
        expect(workClassificationServiceMock.getClassification(WR_WRK_INST)).andReturn("STM").once();
        eligibilityProcessorMock.process(usage);
        expectLastCall().once();
        replay(workClassificationServiceMock, eligibilityProcessorMock);
        classificationProcessor.process(usage);
        verify(workClassificationServiceMock, eligibilityProcessorMock);
    }

    @Test
    public void testProcessWithBelletristic() {
        Usage usage = buildUsage();
        expect(workClassificationServiceMock.getClassification(WR_WRK_INST)).andReturn("BELLETRISTIC").once();
        deleteProcessorMock.process(usage);
        expectLastCall().once();
        replay(workClassificationServiceMock, eligibilityProcessorMock);
        classificationProcessor.process(usage);
        verify(workClassificationServiceMock, eligibilityProcessorMock);
    }

    @Test
    public void testProcessWithNoClassification() {
        Usage usage = buildUsage();
        expect(workClassificationServiceMock.getClassification(WR_WRK_INST)).andReturn(null).once();
        usageRepositoryMock.updateStatus(Collections.singleton(usage.getId()), UsageStatusEnum.UNCLASSIFIED);
        expectLastCall().once();
        replay(workClassificationServiceMock, usageRepositoryMock);
        classificationProcessor.process(usage);
        verify(workClassificationServiceMock, usageRepositoryMock);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(WR_WRK_INST);
        return usage;
    }
}
