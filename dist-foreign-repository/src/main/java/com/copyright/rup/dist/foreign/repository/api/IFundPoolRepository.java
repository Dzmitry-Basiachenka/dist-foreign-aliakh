package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;

import java.util.List;

/**
 * Interface for {@link FundPool} repository .
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/26/2019
 *
 * @author Aliaksandr Liakh
 */
public interface IFundPoolRepository {

    /**
     * Inserts {@link FundPool}.
     *
     * @param fundPool instance of {@link FundPool}
     */
    void insert(FundPool fundPool);

    /**
     * Finds {@link FundPool} by id.
     *
     * @param fundPoolId fund pool id
     * @return instance of {@link FundPool} or null if nothing found
     */
    FundPool findById(String fundPoolId);

    /**
     * Deletes {@link FundPool} by id.
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
     * Finds AACL {@link FundPool}s not attached to scenario.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> findAaclNotAttachedToScenario();

    /**
     * Finds SAL {@link FundPool}s not attached to scenario.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> findSalNotAttachedToScenario();

    /**
     * Find {@link FundPool}s' names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of {@link FundPool}s' names
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

    /**
     * Inserts {@link FundPoolDetail}.
     *
     * @param detail instance of {@link FundPoolDetail}
     */
    void insertDetail(FundPoolDetail detail);

    /**
     * Finds {@link FundPoolDetail}s by {@link FundPool} id.
     *
     * @param fundPoolId {@link FundPool} id
     * @return list of {@link FundPoolDetail}s
     */
    List<FundPoolDetail> findDetailsByFundPoolId(String fundPoolId);

    /**
     * Deletes {@link FundPoolDetail}s by {@link FundPool} id.
     *
     * @param fundPoolId {@link FundPool} id
     */
    void deleteDetailsByFundPoolId(String fundPoolId);
}
