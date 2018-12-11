package com.copyright.rup.dist.foreign.service.api;

import java.util.List;

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
     * Processes specific items.
     *
     * @param items to process
     */
    void process(List<T> items);

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
