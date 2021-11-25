package com.copyright.rup.dist.foreign.repository.api;

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
     * Deletes UDM proxy values.
     *
     * @param period period of UDM values
     */
    void deleteProxyValues(Integer period);

    /**
     * Inserts UDM proxy values.
     *
     * @param period   period of UDM values
     * @param userName name of user
     */
    void insertProxyValues(Integer period, String userName);

    /**
     * Applies content unit price of UDM proxy values to UDM proxy values.
     *
     * @param period   period of UDM values
     * @param userName name of user
     * @return count of updated UDM values
     */
    int applyProxyValues(Integer period, String userName);
}
