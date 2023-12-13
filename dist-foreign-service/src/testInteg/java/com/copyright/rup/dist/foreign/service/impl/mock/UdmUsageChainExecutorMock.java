package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.service.impl.chain.executor.UdmUsageChainExecutor;

import org.apache.camel.util.concurrent.SynchronousExecutorService;
import org.powermock.reflect.Whitebox;

/**
 * Mock for {@link UdmUsageChainExecutor} with synchronous executor service.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/21/2021
 *
 * @author Ihar Suvorau
 */
public class UdmUsageChainExecutorMock extends UdmUsageChainExecutor {

    public UdmUsageChainExecutorMock() {
        Whitebox.setInternalState(this, "executorService", new SynchronousExecutorService());
    }
}
