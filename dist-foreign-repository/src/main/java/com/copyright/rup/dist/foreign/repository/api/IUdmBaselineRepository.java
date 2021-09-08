package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;

import java.util.List;
import java.util.Set;

/**
 * Interface for Baseline usage repository.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/02/21
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineRepository {

    /**
     * Finds list of {@link UdmBaselineDto}s by UDM baseline filter.
     *
     * @param filter   instance of {@link UdmBaselineFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmBaselineDto}
     */
    List<UdmBaselineDto> findDtosByFilter(UdmBaselineFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds UDM baseline usages count based on applied filter.
     *
     * @param filter instance of {@link UdmBaselineFilter}
     * @return the count of usages
     */
    int findCountByFilter(UdmBaselineFilter filter);

    /**
     * Removes UDM usages from baseline. Sets is_baseline_flag {@code false}.
     *
     * @param period   usage period
     * @param userName name of user
     * @return set of UDM usage ids
     */
    Set<String> removeUmdUsagesFromBaseline(Integer period, String userName);
}
