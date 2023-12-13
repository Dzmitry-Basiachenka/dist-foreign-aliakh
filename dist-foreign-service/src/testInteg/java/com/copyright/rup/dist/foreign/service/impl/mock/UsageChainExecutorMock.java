package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.service.impl.chain.executor.UsageChainExecutor;

import org.apache.camel.util.concurrent.SynchronousExecutorService;
import org.powermock.reflect.Whitebox;

/**
 * Mock for {@link UsageChainExecutor} with synchronous executor service.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/30/2020
 *
 * @author Aliaksandr Liakh
 */
public class UsageChainExecutorMock extends UsageChainExecutor {

    public UsageChainExecutorMock() {
        Whitebox.setInternalState(this, "executorService", new SynchronousExecutorService());
    }
}
