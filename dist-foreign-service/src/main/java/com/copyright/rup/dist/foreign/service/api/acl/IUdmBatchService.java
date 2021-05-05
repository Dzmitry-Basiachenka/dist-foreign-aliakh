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
     * @return count of inserted usages
     */
    int insertUdmBatch(UdmBatch udmBatch, List<UdmUsage> udmUsages);

    /**
     * Gets periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Gets list of {@link UdmBatch}s.
     *
     * @return list of {@link UdmBatch}s
     */
    List<UdmBatch> getUdmBatches();
}
