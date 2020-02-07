package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;

import java.util.List;
import java.util.Set;

/**
 * Interface for AACL fund pool repository.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/02/2020
 *
 * @author Aliaksandr Liakh
 */
public interface IAaclFundPoolRepository {

    /**
     * Finds Aggregate Licensee Class ids.
     *
     * @return set of Aggregate Licensee Class ids
     */
    Set<Integer> findAggregateLicenseeClassIds();

    /**
     * Finds all {@link FundPool}s.
     *
     * @return list of {@link FundPool}s
     */
    List<FundPool> findAll();

    /**
     * Finds {@link FundPoolDetail}s by {@link FundPool} id.
     *
     * @param fundPoolId {@link FundPool} id
     * @return list of {@link FundPoolDetail}s
     */
    List<FundPoolDetail> findDetailsByFundPoolId(String fundPoolId);

    /**
     * Deletes {@link FundPool} by id.
     *
     * @param fundPoolId {@link FundPool} id
     */
    void deleteById(String fundPoolId);

    /**
     * Deletes {@link FundPoolDetail}s by {@link FundPool} id.
     *
     * @param fundPoolId {@link FundPool} id
     */
    void deleteDetailsByFundPoolId(String fundPoolId);
}
