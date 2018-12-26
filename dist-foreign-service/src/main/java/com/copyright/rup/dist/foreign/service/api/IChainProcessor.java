package com.copyright.rup.dist.foreign.service.api;

import java.util.Objects;
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

    /**
     * @return instance of {@link ChainProcessorTypeEnum}.
     */
    ChainProcessorTypeEnum getChainProcessorType();

    /**
     * Passes processed item to the next processor.
     *
     * @param processedItem    item to pass to the next processor
     * @param successPredicate predicate to decide whether item was processed successfully or not
     */
    default void processResult(T processedItem, Predicate<T> successPredicate) {
        if (successPredicate.test(processedItem)) {
            if (Objects.nonNull(getSuccessProcessor())) {
                getSuccessProcessor().process(processedItem);
            }
        } else if (Objects.nonNull(getFailureProcessor())) {
            getFailureProcessor().process(processedItem);
        }
    }
}
