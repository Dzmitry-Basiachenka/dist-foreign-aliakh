package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.Rightsholder;

import java.util.List;

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
     *
     * @return count of updated records
     */
    int updateRightsholdersInformation();

    /**
     * Updates {@link Rightsholder} information in database table.
     *
     * @param rightsholder instance of {@link Rightsholder} for update
     */
    void updateRightsholder(Rightsholder rightsholder);
}
