package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UdmUsage;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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
    public void setUp() {
        chunkExecutor = new UdmUsageChainChunkExecutor();
        Whitebox.setInternalState(chunkExecutor, "chunkSize", 1);
        chunkExecutor.postConstruct();
    }

    @Test
    public void testGetProductFamilyFunction() {
        assertEquals("ACL", chunkExecutor.getProductFamilyFunction().apply(new UdmUsage()));
        assertEquals("ACL", chunkExecutor.getProductFamilyFunction().apply(null));
    }
}
