package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;

import java.util.List;

/**
 * Represents interface of repository for UDM proxy values.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/24/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmProxyValueRepository {

    /**
     * Finds list of periods from UDM proxy values.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();

    /**
     * Deletes UDM proxy values.
     *
     * @param period period of UDM values
     */
    void deleteProxyValues(Integer period);

    /**
     * Clears content unit price of UDM proxy values to UDM values.
     * @param period   period of UDM values
     * @param userName name of user
     */
    void clearProxyValues(Integer period, String userName);

    /**
     * Inserts UDM proxy values.
     *
     * @param period   period of UDM values
     * @param userName name of user
     */
    void insertProxyValues(Integer period, String userName);

    /**
     * Applies content unit price of UDM proxy values to UDM values.
     *
     * @param period   period of UDM values
     * @param userName name of user
     * @return count of updated UDM values
     */
    int applyProxyValues(Integer period, String userName);

    /**
     * Finds list of {@link UdmProxyValueDto}s by UDM proxy value filter.
     *
     * @param filter instance of {@link UdmProxyValueFilter}
     * @return the list of {@link UdmProxyValueDto}
     */
    List<UdmProxyValueDto> findDtosByFilter(UdmProxyValueFilter filter);
}
