package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.PreServiceFeeFund;

import java.util.List;

/**
 * Represents interface of repository for fund pools.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/26/2019
 *
 * @author Aliaksandr Liakh
 */
public interface IFundPoolRepository {

    /**
     * Inserts Pre-Service fee fund.
     *
     * @param fundPool instance of {@link PreServiceFeeFund}
     */
    void insert(PreServiceFeeFund fundPool);

    /**
     * Finds Pre-Service fee fund by id.
     *
     * @param fundPoolId fund pool id
     * @return instance of {@link PreServiceFeeFund} or null if nothing found
     */
    PreServiceFeeFund findById(String fundPoolId);

    /**
     * Deletes Pre-Service fee fund by id.
     *
     * @param fundId fund id
     * @return number of deleted records
     */
    int delete(String fundId);

    /**
     * Finds all {@link PreServiceFeeFund}s.
     *
     * @return list of {@link PreServiceFeeFund}s
     */
    List<PreServiceFeeFund> findAll();

    /**
     * Finds {@link PreServiceFeeFund}s not attached to scenario.
     *
     * @return list of {@link PreServiceFeeFund}s
     */
    List<PreServiceFeeFund> findNotAttachedToScenario();

    /**
     * Find Pre-Service fee fund names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of Pre-Service fee fund names
     */
    List<String> findNamesByUsageBatchId(String batchId);

    /**
     * Finds {@link PreServiceFeeFund}s count by the name.
     *
     * @param fundPoolName {@link PreServiceFeeFund} name
     * @return count of found {@link PreServiceFeeFund}s
     */
    int findCountByName(String fundPoolName);
}
