package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.FundPool;

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
     * @param fundPool instance of {@link FundPool}
     */
    void insert(FundPool fundPool);

    /**
     * Finds Pre-Service fee fund by id.
     *
     * @param fundPoolId fund pool id
     * @return instance of {@link FundPool} or null if nothing found
     */
    FundPool findById(String fundPoolId);

    /**
     * Deletes Pre-Service fee fund by id.
     *
     * @param fundId fund id
     * @return number of deleted records
     */
    int delete(String fundId);

    /**
     * Finds list of {@link FundPool} by specified product family.
     *
     * @param productFamily product family
     * @return list of found {@link FundPool}s
     */
    List<FundPool> findByProductFamily(String productFamily);

    /**
     * Finds NTS {@link FundPool}s not attached to scenario.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> findNtsNotAttachedToScenario();

    /**
     * Find Pre-Service fee fund names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of Pre-Service fee fund names
     */
    List<String> findNamesByUsageBatchId(String batchId);

    /**
     * Checks whether {@link FundPool} with the name already exists.
     *
     * @param productFamily fund pool product family
     * @param name          fund pool name
     * @return {@code true} - if fund pool exists, {@code false} - otherwise
     */
    boolean fundPoolExists(String productFamily, String name);
}
