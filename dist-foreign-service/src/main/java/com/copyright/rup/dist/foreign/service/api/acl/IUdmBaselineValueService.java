package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;

import java.util.List;

/**
 * Interface for baseline value service.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/29/2021
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineValueService {

    /**
     * Gets all available periods for baseline value.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Gets list of {@link UdmValueBaselineDto}s based on applied filter.
     *
     * @param filter   instance of {@link UdmBaselineValueFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmValueBaselineDto}s
     */
    List<UdmValueBaselineDto> getValueDtos(UdmBaselineValueFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets UDM baseline values count based on applied filter.
     *
     * @param filter instance of {@link UdmBaselineValueFilter}.
     * @return count of baseline values
     */
    int getBaselineValueCount(UdmBaselineValueFilter filter);
}
