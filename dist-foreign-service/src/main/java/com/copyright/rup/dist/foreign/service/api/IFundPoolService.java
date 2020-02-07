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
     * Creates Pre-Service fee fund.
     *
     * @param fundPool instance of {@link FundPool}
     * @param batchIds set of ids of usage batches
     */
    void create(FundPool fundPool, Set<String> batchIds);

    /**
     * Gets all {@link FundPool}s related to selected product family.
     *
     * @param productFamily product family
     * @return list of {@link FundPool}s
     */
    List<FundPool> getPreServiceFeeFunds(String productFamily);

    /**
     * Gets {@link FundPool}s not attached to scenario.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> getPreServiceFeeFundsNotAttachedToScenario();

    /**
     * Deletes Pre-Service fee fund.
     *
     * @param preServiceFeeFund {@link FundPool} to delete
     */
    void deletePreServiceFeeFund(FundPool preServiceFeeFund);

    /**
     * Gets Pre-Service fee fund names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of names
     */
    List<String> getPreServiceFeeFundNamesByUsageBatchId(String batchId);

    /**
     * Checks whether {@link FundPool} with the name already exists.
     *
     * @param productFamily fund pool product family
     * @param name          fund pool name
     * @return {@code true} - if fund pool exists, {@code false} - otherwise
     */
    boolean fundPoolExists(String productFamily, String name);
}
