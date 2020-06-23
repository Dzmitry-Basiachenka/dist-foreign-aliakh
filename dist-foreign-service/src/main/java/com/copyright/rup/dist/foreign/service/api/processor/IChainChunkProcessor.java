package com.copyright.rup.dist.foreign.service.api.processor;

import java.util.function.Predicate;

/**
 * Interface for chain processor.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/24/2020
 *
 * @param <T> type of items to process
 * @param <P> type of predicate
 * @author Aliaksandr Liakh
 */
public interface IChainChunkProcessor<T, P> extends IChainProcessor<T> {

    /**
     * Passes processed item to the next processor.
     *
     * @param processedItems   items to pass to the next processor
     * @param successPredicate predicate to decide whether item was processed successfully or not
     */
    void executeNextChainProcessor(T processedItems, Predicate<P> successPredicate);
}
