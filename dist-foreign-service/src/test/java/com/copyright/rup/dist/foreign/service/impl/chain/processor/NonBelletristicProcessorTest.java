package com.copyright.rup.dist.foreign.service.impl.chain.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IWorkClassificationService;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link NonBelletristicProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/22/2019
 *
 * @author Pavel Liakh
 */
public class NonBelletristicProcessorTest {

    private static final Long WR_WRK_INST = 11233251L;

    private NonBelletristicProcessor nonBelletristicProcessor;
    private IWorkClassificationService workClassificationServiceMock;
    private IChainProcessor<Usage> eligibilityProcessorMock;
    private IChainProcessor<Usage> deleteProcessorMock;

    @Before
    public void setUp() {
        nonBelletristicProcessor = new NonBelletristicProcessor();
        workClassificationServiceMock = createMock(IWorkClassificationService.class);
        eligibilityProcessorMock = createMock(IChainProcessor.class);
        deleteProcessorMock = createMock(IChainProcessor.class);
        nonBelletristicProcessor.setWorkClassificationService(workClassificationServiceMock);
        nonBelletristicProcessor.setSuccessProcessor(deleteProcessorMock);
        nonBelletristicProcessor.setFailureProcessor(eligibilityProcessorMock);
    }

    @Test
    public void testProcessWithStm() {
        Usage usage = buildUsage();
        expect(workClassificationServiceMock.getClassification(WR_WRK_INST)).andReturn("STM").once();
        deleteProcessorMock.process(usage);
        expectLastCall().once();
        replay(workClassificationServiceMock, deleteProcessorMock);
        nonBelletristicProcessor.process(usage);
        verify(workClassificationServiceMock, deleteProcessorMock);
    }

    @Test
    public void testProcessWithBelletristic() {
        Usage usage = buildUsage();
        expect(workClassificationServiceMock.getClassification(WR_WRK_INST)).andReturn("BELLETRISTIC").once();
        eligibilityProcessorMock.process(usage);
        expectLastCall().once();
        replay(workClassificationServiceMock, eligibilityProcessorMock);
        nonBelletristicProcessor.process(usage);
        verify(workClassificationServiceMock, eligibilityProcessorMock);
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setWrWrkInst(WR_WRK_INST);
        return usage;
    }
}
