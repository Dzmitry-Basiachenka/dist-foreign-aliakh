package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmUsageChainChunkExecutor}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/19/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmUsageChainChunkExecutorTest {

    private UdmUsageChainChunkExecutor executor;
    private IChainProcessor<List<UdmUsage>> udmMatchingProcessor;
    private IChainProcessor<List<UdmUsage>> udmRightsProcessor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        executor = new UdmUsageChainChunkExecutor();
        udmMatchingProcessor = createMock(IChainProcessor.class);
        udmRightsProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(executor, "chunkSize", 1);
        Whitebox.setInternalState(executor, "udmProcessor", udmMatchingProcessor);
        executor.postConstruct();
    }

    @Test
    public void testExecuteProcessor() {
        List<UdmUsage> udmUsages = Collections.singletonList(buildUdmUsage());
        expect(udmMatchingProcessor.getChainProcessorType())
            .andReturn(ChainProcessorTypeEnum.MATCHING)
            .once();
        expect(udmMatchingProcessor.getSuccessProcessor())
            .andReturn(udmRightsProcessor)
            .once();
        expect(udmMatchingProcessor.getFailureProcessor())
            .andReturn(null)
            .once();
        expect(udmRightsProcessor.getChainProcessorType())
            .andReturn(ChainProcessorTypeEnum.RIGHTS)
            .once();
        udmRightsProcessor.process(udmUsages);
        replay(udmRightsProcessor, udmMatchingProcessor);
        executor.execute(udmUsages, ChainProcessorTypeEnum.RIGHTS);
        verify(udmRightsProcessor, udmMatchingProcessor);
    }

    @Test
    public void testGetProductFamilyFunction() {
        assertEquals("ACL", executor.getProductFamilyFunction().apply(new UdmUsage()));
        assertEquals("ACL", executor.getProductFamilyFunction().apply(null));
    }

    private UdmUsage buildUdmUsage() {
        UdmUsage udmUsage = new UdmUsage();
        udmUsage.setId(RupPersistUtils.generateUuid());
        return udmUsage;
    }
}
