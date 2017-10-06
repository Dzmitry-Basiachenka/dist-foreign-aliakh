package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.Rightsholder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents interface of service for rightsholder business logic.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 */
public interface IRightsholderService {

    /**
     * @return list of all RROs presented in DB.
     */
    List<Rightsholder> getRros();

    /**
     * Updates infromation about rightsholders. Gets set of RRO and RH account numbers from usages, archived usages and
     * usage batches tables, make call to PRM service to get infromation about rightsholders, remove all data
     * from df_rightsholder table, and insert data from PRM to this table.
     */
    void updateRightsholders();

    /**
     * Updates and gets a map of rightsholders by their account numbers. If there is no information about rightsholder
     * with some account number it will be retrieved from PRM using REST call.
     *
     * @param accountNumbers set of rightsholder account numbers
     * @return map of rightsholders by their account number
     */
    Map<Long, Rightsholder> updateAndGetRightsholders(Set<Long> accountNumbers);
}
