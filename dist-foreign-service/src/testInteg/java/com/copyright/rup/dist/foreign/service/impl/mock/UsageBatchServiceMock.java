package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.service.impl.UsageBatchService;

import org.apache.camel.util.concurrent.SynchronousExecutorService;

import java.util.concurrent.ExecutorService;

/**
 * Mock for {@link UsageBatchService} with synchronous executor service.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/15/2018
 *
 * @author Aliaksandr Liakh
 */
public class UsageBatchServiceMock extends UsageBatchService {

    @Override
    protected ExecutorService getExecutorService() {
        return new SynchronousExecutorService();
    }
}
