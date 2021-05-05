package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;

import java.util.List;

/**
 * Represents interface of service for UDM usages business logic.
 * <p>
 * Copyright (C) 20121 copyright.com
 * <p>
 * Date: 04/30/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmUsageService {

    /**
     * Inserts UDM usages.
     *
     * @param udmBatch  {@link UdmBatch} instance
     * @param udmUsages list of {@link UdmUsage}s
     * @return count of UDM usages
     */
    int insertUdmUsages(UdmBatch udmBatch, List<UdmUsage> udmUsages);

    /**
     * Checks whether UDM usage with provided original detail id exists.
     *
     * @param originalDetailId original detail id
     * @return {@code true} if UDM usage with provided original detail id exists, otherwise {@code false}
     */
    boolean isOriginalDetailIdExist(String originalDetailId);

    /**
     * Gets list of {@link UdmUsageDto}s based on applied filter.
     *
     * @param filter   instance of {@link UdmUsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmUsageDto}s
     */
    List<UdmUsageDto> getUsageDtos(UdmUsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets UDM usages count based on applied filter.
     *
     * @param filter instance of {@link UdmUsageFilter}.
     * @return count of usages
     */
    int getUsagesCount(UdmUsageFilter filter);
}
