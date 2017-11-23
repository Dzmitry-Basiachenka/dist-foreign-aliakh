package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.api.ICommonRightsholderService;

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
public interface IRightsholderService extends ICommonRightsholderService {

    /**
     * @return list of all RROs presented in DB.
     */
    List<Rightsholder> getRros();

    /**
     * Inserts specified rightsholder into database if it does not exist.
     *
     * @param rightsholder {@link Rightsholder} to insert
     */
    void updateRightsholder(Rightsholder rightsholder);
}
