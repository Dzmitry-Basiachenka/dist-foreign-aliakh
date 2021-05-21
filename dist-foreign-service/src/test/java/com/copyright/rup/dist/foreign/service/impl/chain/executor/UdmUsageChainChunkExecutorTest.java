package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.service.api.processor.IChainProcessor;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    private UdmUsageChainChunkExecutor chunkExecutor;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        chunkExecutor = new UdmUsageChainChunkExecutor();
        IChainProcessor<List<UdmUsage>> udmProcessor = createMock(IChainProcessor.class);
        Whitebox.setInternalState(chunkExecutor, "chunkSize", 1);
        Whitebox.setInternalState(chunkExecutor, "udmProcessor", udmProcessor);
        chunkExecutor.postConstruct();
    }

    @Test
    public void testGetProductFamilyFunction() {
        assertEquals("ACL", chunkExecutor.getProductFamilyFunction().apply(new UdmUsage()));
        assertEquals("ACL", chunkExecutor.getProductFamilyFunction().apply(null));
    }
}
