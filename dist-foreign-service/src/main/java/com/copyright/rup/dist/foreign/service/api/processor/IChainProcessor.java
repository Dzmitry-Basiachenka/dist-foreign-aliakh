package com.copyright.rup.dist.foreign.service.api.processor;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

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
public interface IChainProcessor<T> extends Serializable {

    /**
     * Processes specific items.
     *
     * @param item to process
     */
    void process(List<T> item);

    /**
     * Passes processed items to the next processor.
     *
     * @param processedItems   items to pass to the next processor
     * @param successPredicate predicate to decide whether item was processed successfully or not
     */
    void executeNextChainProcessor(List<T> processedItems, Predicate<T> successPredicate);

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

    /**
     * @return instance of {@link ChainProcessorTypeEnum}.
     */
    ChainProcessorTypeEnum getChainProcessorType();
}
