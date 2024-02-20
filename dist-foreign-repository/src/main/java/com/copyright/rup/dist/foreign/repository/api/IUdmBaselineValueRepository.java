package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for baseline value repository.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineValueRepository extends Serializable {

    /**
     * Finds list of periods from UDM baseline value.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();

    /**
     * Finds list of {@link UdmValueBaselineDto}s by UDM value filter.
     *
     * @param filter   instance of {@link UdmBaselineValueFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmValueBaselineDto}
     */
    List<UdmValueBaselineDto> findDtosByFilter(UdmBaselineValueFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds baseline values count based on applied filter.
     *
     * @param filter instance of {@link UdmBaselineValueFilter}
     * @return the count of baseline values
     */
    int findCountByFilter(UdmBaselineValueFilter filter);
}
