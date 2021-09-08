package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;

import java.util.List;

/**
 * Represents interface of service for UDM baseline business logic.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/03/21
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineService {

    /**
     * Gets list of {@link UdmBaselineDto}s based on applied filter.
     *
     * @param filter   instance of {@link UdmBaselineFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UdmBaselineDto}s
     */
    List<UdmBaselineDto> getBaselineUsageDtos(UdmBaselineFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets UDM baseline usages count based on applied filter.
     *
     * @param filter instance of {@link UdmBaselineFilter}.
     * @return count of usages
     */
    int getBaselineUsagesCount(UdmBaselineFilter filter);

    /**
     * Removes usages from baseline.
     *
     * @param period baseline period
     * @return count of removed from baselines
     */
    int removeFromBaseline(Integer period);
}
