package com.copyright.rup.dist.foreign.service.impl.mock.chunk;

import com.copyright.rup.dist.foreign.service.impl.chain.executor.chunk.UsageChainChunkExecutor;

import org.apache.camel.util.concurrent.SynchronousExecutorService;

import java.util.concurrent.ExecutorService;

/**
 * Mock for {@link UsageChainChunkExecutor} with synchronous executor service.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/30/2020
 *
 * @author Aliaksandr Liakh
 */
public class UsageChainChunkExecutorMock extends UsageChainChunkExecutor {

    @Override
    protected ExecutorService getExecutorService() {
        return new SynchronousExecutorService();
    }
}
