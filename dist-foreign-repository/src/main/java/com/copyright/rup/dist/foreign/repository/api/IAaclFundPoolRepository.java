package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AaclFundPool;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;

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
     * Checks whether AACL fund pool with provided name already exists or not.
     *
     * @param name AACL fund pool name
     * @return {@code true} - if AACL fund pool exists, {@code false} - otherwise
     */
    boolean aaclFundPoolExists(String name);

    /**
     * Finds Aggregate Licensee Class ids.
     *
     * @return set of Aggregate Licensee Class ids
     */
    Set<Integer> findAggregateLicenseeClassIds();

    /**
     * Finds all {@link AaclFundPool}s.
     *
     * @return list of {@link AaclFundPool}s
     */
    List<AaclFundPool> findAll();

    /**
     * Finds {@link AaclFundPoolDetail}s by {@link AaclFundPool} id.
     *
     * @param fundPoolId {@link AaclFundPool} id
     * @return list of {@link AaclFundPoolDetail}s
     */
    List<AaclFundPoolDetail> findDetailsByFundPoolId(String fundPoolId);

    /**
     * Deletes {@link AaclFundPool} by id.
     *
     * @param fundPoolId {@link AaclFundPool} id
     */
    void deleteById(String fundPoolId);

    /**
     * Deletes {@link AaclFundPoolDetail}s by {@link AaclFundPool} id.
     *
     * @param fundPoolId {@link AaclFundPool} id
     */
    void deleteDetailsByFundPoolId(String fundPoolId);
}
