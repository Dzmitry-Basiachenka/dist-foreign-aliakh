package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.api.ICommonRightsholderService;

import java.util.List;
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
public interface IRightsholderService extends ICommonRightsholderService {

    /**
     * Get list of RROs presented in DB associated with specified Product Family.
     *
     * @param productFamily Product Family
     * @return list of found RROs
     */
    List<Rightsholder> getRros(String productFamily);

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

    /**
     * Updates RHs information for account numbers absent in database based on PRM information.
     * Updates information in background thread.
     *
     * @param accountNumbers set of RH account numbers
     */
    void updateRighstholdersAsync(Set<Long> accountNumbers);
}
