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

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link NonBelletristicProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/22/2019
 *
 * @author Pavel Liakh
 * @author Aliaksandr Liakh
 */
public class NonBelletristicProcessorTest {

    private static final Long WR_WRK_INST_1 = 11233251L;
    private static final Long WR_WRK_INST_2 = 16367895L;

    private NonBelletristicProcessor nonBelletristicProcessor;
    private IWorkClassificationService workClassificationService;
    private IChainProcessor<Usage> eligibilityProcessor;
    private IChainProcessor<Usage> deleteProcessor;

    @Before
    public void setUp() {
        nonBelletristicProcessor = new NonBelletristicProcessor();
        workClassificationService = createMock(IWorkClassificationService.class);
        eligibilityProcessor = createMock(IChainProcessor.class);
        deleteProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(nonBelletristicProcessor, workClassificationService);
        nonBelletristicProcessor.setSuccessProcessor(eligibilityProcessor);
        nonBelletristicProcessor.setFailureProcessor(deleteProcessor);
    }

    @Test
    public void testProcess() {
        Usage usage1 = buildUsage("a96266d0-0e7b-4f9a-960e-355e4e6fca80", WR_WRK_INST_1);
        Usage usage2 = buildUsage("ac261ab4-2447-4304-9a10-b6c584490208", WR_WRK_INST_2);
        expect(workClassificationService.getClassification(WR_WRK_INST_1)).andReturn("STM").once();
        expect(workClassificationService.getClassification(WR_WRK_INST_2)).andReturn("BELLETRISTIC").once();
        eligibilityProcessor.process(Collections.singletonList(usage1));
        expectLastCall().once();
        deleteProcessor.process(Collections.singletonList(usage2));
        expectLastCall().once();
        replay(workClassificationService, eligibilityProcessor, deleteProcessor);
        nonBelletristicProcessor.process(Lists.newArrayList(usage1, usage2));
        verify(workClassificationService, eligibilityProcessor, deleteProcessor);
    }

    @Test
    public void testGetChainProcessorType() {
        assertEquals(ChainProcessorTypeEnum.CLASSIFICATION, nonBelletristicProcessor.getChainProcessorType());
    }

    private Usage buildUsage(String usageId, Long wrWrkInst) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(wrWrkInst);
        return usage;
    }
}
