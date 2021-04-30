package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UdmUsage;

import java.util.List;

/**
 * Represents interface of repository for UDM usages.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 04/28/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmUsageRepository {

    /**
     * Inserts UDM usage batch.
     *
     * @param udmUsage instance of {@link UdmUsage}
     */
    void insert(UdmUsage udmUsage);

    /**
     * Checks whether UDM usage with provided original detail id exists.
     *
     * @param originalDetailId original detail id
     * @return {@code true} if UDM usage with provided original detail id exists, otherwise {@code false}
     */
    boolean isOriginalDetailIdExist(String originalDetailId);

    /**
     * Gets {@link UdmUsage}s by their ids.
     *
     * @param udmBatchIds list of ids of the {@link UdmUsage}
     * @return list of {@link UdmUsage} with the given id or {@code null} if none exists
     */
    List<UdmUsage> findByIds(List<String> udmBatchIds);
}
