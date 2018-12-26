package com.copyright.rup.dist.foreign.service.api;

import java.util.List;

/**
 * Interface for chain executor.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @param <T> type of items to process
 * @author Uladzislau Shalamitski
 */
public interface IChainExecutor<T> {

    /**
     * Executes all chains of processors starting with {@link ChainProcessorTypeEnum}.
     * Method is used by jobs.
     *
     * @param type processor in chain from which execution will be started by job
     */
    void execute(ChainProcessorTypeEnum type);

    /**
     * Executes chain of processors for list of items starting with {@link ChainProcessorTypeEnum}.
     *
     * @param items items to process
     * @param type  processor in chain from which execution will be started
     */
    void execute(List<T> items, ChainProcessorTypeEnum type);
}
