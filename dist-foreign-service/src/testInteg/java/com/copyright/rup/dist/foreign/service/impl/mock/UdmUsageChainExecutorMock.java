package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.service.impl.chain.executor.UdmUsageChainChunkExecutor;

import org.apache.camel.util.concurrent.SynchronousExecutorService;

import java.util.concurrent.ExecutorService;

/**
 * Mock for {@link UdmUsageChainChunkExecutor} with synchronous executor service.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Ihar Suvorau
 */
public class UdmUsageChainExecutorMock extends UdmUsageChainChunkExecutor {

    @Override
    protected ExecutorService getExecutorService() {
        return new SynchronousExecutorService();
    }
}
