package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;

import java.util.List;

/**
 * Represents interface of service for UDM proxy values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/24/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmProxyValueService {

    /**
     * Finds list of periods from UDM proxy values.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();

    /**
     * Calculates and applies content unit price of UDM proxy values to UDM proxy values.
     *
     * @param period period of usage
     * @return count of updated UDM values
     */
    int calculateProxyValues(Integer period);

    /**
     * Gets list of {@link UdmProxyValueDto}s by UDM proxy value filter.
     *
     * @param filter instance of {@link UdmProxyValueFilter}
     * @return the list of {@link UdmProxyValueDto}
     */
    List<UdmProxyValueDto> getDtosByFilter(UdmProxyValueFilter filter);
}
