package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UdmBatch;

/**
 * Represents interface of repository for UDM usage batches.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 04/28/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmBatchRepository {

    /**
     * Inserts UDM usage batch.
     *
     * @param udmBatch instance of {@link UdmBatch}
     */
    void insert(UdmBatch udmBatch);

    /**
     * Gets {@link UdmBatch} by its id.
     *
     * @param udmBatchId id of the {@link UdmBatch}
     * @return {@link UdmBatch} with the given id or {@code null} if none exists
     */
    UdmBatch findById(String udmBatchId);
}
