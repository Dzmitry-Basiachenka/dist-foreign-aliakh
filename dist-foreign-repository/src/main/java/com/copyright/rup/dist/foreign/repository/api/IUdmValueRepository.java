package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;

import java.util.List;

/**
 * Represents interface of repository for UDM values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Anton Azarenka
 */
public interface IUdmValueRepository {

    /**
     * Finds values count based on applied filter.
     *
     * @param filter instance of {@link UdmValueFilter}
     * @return the count of values
     */
    int findCountByFilter(UdmValueFilter filter);

    /**
     * Finds list of {@link UdmValueDto}s by UDM value filter.
     *
     * @param filter   instance of {@link UdmValueFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmValueDto}
     */
    List<UdmValueDto> findDtosByFilter(UdmValueFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds list of periods from UDM value.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();
}
