package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
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

    /**
     * Gets list of unique {@link Rightsholder}s from all usages base on search value.
     *
     * @param searchValue value to search
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of unique {@link Rightsholder}s from all usages
     */
    List<Rightsholder> getFromUsages(String searchValue, Pageable pageable, Sort sort);

    /**
     * Gets count of unique rightsholders from usages based on search value.
     *
     * @param searchValue value to search
     * @return count of rightsholders
     */
    int getCountFromUsages(String searchValue);
}
