package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.PreServiceFeeFund;

import java.util.List;

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
     * @param fundPool instance of {@link PreServiceFeeFund}
     * @param batchIds list of ids of usage batches
     */
    void create(PreServiceFeeFund fundPool, List<String> batchIds);

    /**
     * Gets all Pre-Service fee funds.
     *
     * @return list of {@link PreServiceFeeFund}s
     */
    List<PreServiceFeeFund> getPreServiceFeeFunds();

    /**
     * Deletes Pre-Service fee fund.
     *
     * @param preServiceFeeFund {@link PreServiceFeeFund} to delete
     */
    void deletePreServiceFeeFund(PreServiceFeeFund preServiceFeeFund);

    /**
     * Gets Pre-Service fee fund names associated with batch identifier.
     *
     * @param batchId batch identifier
     * @return list of names
     */
    List<String> getPreServiceFeeFundNamesByUsageBatchId(String batchId);

    /**
     * Checks whether {@link PreServiceFeeFund} with the name already exists.
     *
     * @param fundPoolName name of {@link PreServiceFeeFund} to check
     * @return {@code true} if {@link PreServiceFeeFund} with the name already exists, {@code false} otherwise
     */
    boolean fundPoolNameExists(String fundPoolName);
}
