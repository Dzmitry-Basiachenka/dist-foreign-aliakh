package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.WithdrawnFundPool;

import java.util.List;

/**
 * Represents interface of service for NTS withdrawn funds business logic.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
public interface IWithdrawnFundPoolService {

    /**
     * Creates NTS withdrawn fund pool.
     *
     * @param fundPool instance of {@link WithdrawnFundPool}
     * @param batchIds list of ids of usage batches
     */
    void create(WithdrawnFundPool fundPool, List<String> batchIds);

    /**
     * Gets all additional funds.
     *
     * @return list of {@link WithdrawnFundPool}s
     */
    List<WithdrawnFundPool> getAdditionalFunds();

    /**
     * Deletes additional fund pool.
     *
     * @param fundPool {@link WithdrawnFundPool} to delete
     */
    void deleteAdditionalFund(WithdrawnFundPool fundPool);

    /**
     * Gets additional fund names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of names
     */
    List<String> getAdditionalFundNamesByUsageBatchId(String batchId);

    /**
     * Checks whether {@link WithdrawnFundPool} with the name already exists.
     *
     * @param fundPoolName name of {@link WithdrawnFundPool} to check
     * @return {@code true} if {@link WithdrawnFundPool} with the name already exists, {@code false} otherwise
     */
    boolean fundPoolNameExists(String fundPoolName);
}
