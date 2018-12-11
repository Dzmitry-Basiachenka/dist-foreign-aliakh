package com.copyright.rup.dist.foreign.service.api;

/**
 * Interface for chain executor.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public interface IChainExecutor {

    /**
     * Executes all chains of processors starting with {@link JobProcessorTypeEnum}.
     *
     * @param processorEnum processor in chain from which execution will be started by job
     */
    void execute(JobProcessorTypeEnum processorEnum);

    /**
     * Executes chain of processors for specified product family.
     *
     * @param productFamily product family
     */
    void execute(String productFamily);
}
