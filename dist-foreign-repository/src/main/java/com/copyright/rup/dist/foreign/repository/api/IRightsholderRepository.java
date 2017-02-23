package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.domain.Rightsholder;

import java.util.List;

/**
 * Represents interface of repository for rightsholders.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 * @author Mikalai Bezmen
 */
public interface IRightsholderRepository {

    /**
     * @return list of RROs from all batches stored in DB.
     */
    List<Rightsholder> findRros();

    /**
     * Inserts {@link Rightsholder} into DB.
     *
     * @param rightsholder instance of {@link Rightsholder}
     */
    void insert(Rightsholder rightsholder);
}
