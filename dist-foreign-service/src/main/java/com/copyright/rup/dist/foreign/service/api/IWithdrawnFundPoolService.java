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
}
