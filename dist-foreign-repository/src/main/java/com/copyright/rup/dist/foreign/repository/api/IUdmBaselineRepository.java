package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmValue;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;

import java.util.List;
import java.util.Map;
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
     * Finds list of periods from UDM baseline usages.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();

    /**
     * Finds list of {@link UdmValue}s from usages baseline for given period that are candidates for populating into
     * value batch. The following unique works are included into result list:
     * <ul>
     *     <li>1. works that were not populated into value batch for given period</li>
     *     <li>2. works from previous periods with age weight greater than zero,
     *            that were not populated into value batch for any period</li>
     * </ul>
     *
     * @param period period for value batch populating
     * @return list of {@link UdmValue}s
     */
    List<UdmValue> findNotPopulatedValuesFromBaseline(Integer period);

    /**
     * Populates baseline usages with corresponding values id.
     *
     * @param period                usage period
     * @param wrWrkInstToValueIdMap map of wrWrkInst to value id
     * @param userName              user who updated usages
     * @return count of updated usages
     */
    int populateValueId(Integer period, Map<Long, String> wrWrkInstToValueIdMap, String userName);

    /**
     * Removes UDM usage from baseline by id. Sets is_baseline_flag {@code false}.
     *
     * @param udmUsageId UDM usage id
     */
    void removeUdmUsageFromBaselineById(String udmUsageId);

    /**
     * Finds map of wrWrkInsts to system titles.
     *
     * @param periods set of periods
     * @return map of wrWrkInsts to system titles
     */
    Map<Long, String> findWrWrkInstToSystemTitles(Set<Integer> periods);
}
