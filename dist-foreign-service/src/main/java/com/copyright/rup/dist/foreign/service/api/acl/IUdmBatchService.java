package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;

import java.util.List;

/**
 * Represents interface of service for UDM batch business logic.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/30/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmBatchService {

    /**
     * Inserts UDM batch with usages.
     *
     * @param udmBatch  {@link UdmBatch} instance
     * @param udmUsages list of {@link UdmUsage}s
     */
    void insertUdmBatch(UdmBatch udmBatch, List<UdmUsage> udmUsages);

    /**
     * Gets list of {@link UdmBatch}es.
     *
     * @return list of {@link UdmBatch}es
     */
    List<UdmBatch> getUdmBatches();

    /**
     * Checks whether {@link UdmBatch} with the name already exists.
     *
     * @param name UDM batch name
     * @return {@code true} - if batch exists, {@code false} - otherwise
     */
    boolean udmBatchExists(String name);

    /**
     * Verifies that UDM batch processing is completed for intermediate statuses.
     *
     * @param udmBatchId batch identifier
     * @return {@code true} - if UDM batch processing is completed, {@code false} - otherwise
     */
    boolean isUdmBatchProcessingCompleted(String udmBatchId);

    /**
     * Deletes given {@link UdmBatch} and all it's UDM usage details.
     *
     * @param udmBatch {@link UdmBatch}
     */
    void deleteUdmBatch(UdmBatch udmBatch);
}
