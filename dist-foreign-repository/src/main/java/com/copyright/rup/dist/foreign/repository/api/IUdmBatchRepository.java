package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Represents interface of repository for UDM usage batches.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 04/28/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmBatchRepository extends Serializable {

    /**
     * Inserts UDM usage batch.
     *
     * @param udmBatch instance of {@link UdmBatch}
     */
    void insert(UdmBatch udmBatch);

    /**
     * Finds {@link UdmBatch} by its id.
     *
     * @param udmBatchId id of the {@link UdmBatch}
     * @return {@link UdmBatch} with the given id or {@code null} if none exists
     */
    UdmBatch findById(String udmBatchId);

    /**
     * Finds list of {@link UdmBatch}es.
     *
     * @return list of {@link UdmBatch}es
     */
    List<UdmBatch> findAll();

    /**
     * Checks whether {@link UdmBatch} with the name already exists.
     *
     * @param name UDM batch name
     * @return {@code true} - if batch exists, {@code false} - otherwise
     */
    boolean udmBatchExists(String name);

    /**
     * Verifies that UDM batch processing is completed for specific statuses.
     *
     * @param udmBatchId batch identifier
     * @param statuses   intermediate statuses
     * @return {@code true} - if UDM batch status is completed, {@code false} - otherwise
     */
    boolean isUdmBatchProcessingCompleted(String udmBatchId, Set<UsageStatusEnum> statuses);

    /**
     * Deletes {@link UdmBatch} with given id from database.
     *
     * @param udmBatchId id of the {@link UdmBatch} to be deleted
     */
    void deleteById(String udmBatchId);

    /**
     * Returns true if selected UDM batch doesn't have usages in baseline.
     *
     * @param udmBatchId batch identifier
     * @return {@code true} - if UDM batch doesn't have usages in baseline, {@code false} - otherwise
     */
    boolean isUdmBatchContainsBaselineUsages(String udmBatchId);

    /**
     * Finds list of periods from UDM batches.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();
}
