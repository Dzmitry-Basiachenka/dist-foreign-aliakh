package com.copyright.rup.dist.foreign.service.impl.chain.executor;

import com.copyright.rup.dist.foreign.service.api.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.JobProcessorTypeEnum;

/**
 * Implementation of {@link IChainExecutor}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public class ChainExecutor implements IChainExecutor {

    @Override
    public void execute(JobProcessorTypeEnum processorEnum) {
        /*
            1. Find job processors with specified type in all chains
            2. Execute them
         */
    }

    @Override
    public void execute(String productFamily) {
        /*
            1. Get chain by product family
            2. Execute first processor
         */
    }
}
