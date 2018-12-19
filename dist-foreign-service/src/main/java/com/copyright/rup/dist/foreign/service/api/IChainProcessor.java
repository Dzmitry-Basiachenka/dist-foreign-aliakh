package com.copyright.rup.dist.foreign.service.api;

/**
 * Interface for chain processor.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @param <T> type of items to process
 * @author Uladzislau Shalamitski
 */
public interface IChainProcessor<T> {

    /**
     * Processes specific item.
     *
     * @param item to process
     */
    void process(T item);

    /**
     * @return success processor.
     */
    IChainProcessor<T> getSuccessProcessor();

    /**
     * Sets success processor.
     *
     * @param successProcessor success processor
     */
    void setSuccessProcessor(IChainProcessor<T> successProcessor);

    /**
     * @return failure processor.
     */
    IChainProcessor<T> getFailureProcessor();

    /**
     * Sets failure processor.
     *
     * @param failureProcessor failure processor
     */
    void setFailureProcessor(IChainProcessor<T> failureProcessor);
}
