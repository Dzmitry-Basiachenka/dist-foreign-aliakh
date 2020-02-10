package com.copyright.rup.dist.foreign.service.api.aacl;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;

import java.util.Collection;
import java.util.List;

/**
 * Interface for AACL fund pool service.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
public interface IAaclFundPoolService {

    /**
     * @return list of all existing {@link FundPool}s.
     */
    List<FundPool> getFundPools();

    /**
     * Gets {@link FundPoolDetail}s by {@link FundPool} id.
     *
     * @param fundPoolId {@link FundPool} id
     * @return list of {@link FundPoolDetail}s
     */
    List<FundPoolDetail> getDetailsByFundPoolId(String fundPoolId);

    /**
     * Deletes {@link FundPool} by id.
     *
     * @param fundPoolId {@link FundPool} id
     */
    void deleteFundPoolById(String fundPoolId);

    /**
     * Inserts AACL fund pool and its details.
     *
     * @param fundPool instance of {@link FundPool}
     * @param details  list of {@link FundPoolDetail}s
     * @return count of inserted details
     */
    int insertFundPool(FundPool fundPool, Collection<FundPoolDetail> details);
}
