package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.FundPool;

import java.util.List;
import java.util.Set;

/**
 * Represents interface of service for fund pools business logic.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
public interface IFundPoolService {

    /**
     * Gets all {@link FundPool}s related to selected product family.
     *
     * @param productFamily product family
     * @return list of {@link FundPool}s
     */
    List<FundPool> getFundPools(String productFamily);

    /**
     * Checks whether {@link FundPool} with provided name already exists.
     *
     * @param productFamily fund pool product family
     * @param name          fund pool name
     * @return {@code true} - if fund pool exists, {@code false} - otherwise
     */
    boolean fundPoolExists(String productFamily, String name);

    /**
     * Creates NTS fund pool.
     *
     * @param fundPool instance of {@link FundPool}
     * @param batchIds set of ids of usage batches
     */
    void createNtsFundPool(FundPool fundPool, Set<String> batchIds);

    /**
     * Gets NTS {@link FundPool}s not attached to scenario.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> getNtsNotAttachedToScenario();

    /**
     * Deletes NTS {@link FundPool}.
     *
     * @param fundPool {@link FundPool} to delete
     */
    void deleteNtsFundPool(FundPool fundPool);

    /**
     * Gets NTS fund pool names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of names
     */
    List<String> getNtsFundPoolNamesByUsageBatchId(String batchId);
}
