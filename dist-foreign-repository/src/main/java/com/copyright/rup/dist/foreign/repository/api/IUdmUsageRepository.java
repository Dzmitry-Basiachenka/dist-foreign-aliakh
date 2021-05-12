package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;

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
     * Inserts UDM usage.
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
     * Finds list of {@link UdmUsageDto}s by UDM usage filter.
     *
     * @param filter   instance of {@link UdmUsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmUsageDto}
     */
    List<UdmUsageDto> findDtosByFilter(UdmUsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds UDM usages count based on applied filter.
     *
     * @param filter instance of {@link UdmUsageFilter}
     * @return the count of usages
     */
    int findCountByFilter(UdmUsageFilter filter);

    /**
     * Finds {@link UdmUsage}s by their ids.
     *
     * @param udmUsagesIds list of ids of the {@link UdmUsage}
     * @return list of {@link UdmUsage} with the given id or {@code null} if none exists
     */
    List<UdmUsage> findByIds(List<String> udmUsagesIds);
}
