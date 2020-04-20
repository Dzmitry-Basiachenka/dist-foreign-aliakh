package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

/**
 * Performance logger for {@link com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 20/04/2020
 *
 * @author Aliaksandr Liakh
 */
public interface IPerformanceLogger {

    /**
     * Logs execution of {@link com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor}.
     *
     * @param chainProcessorTypeEnum instance of of {@link ChainProcessorTypeEnum}
     */
    void log(ChainProcessorTypeEnum chainProcessorTypeEnum);
}
