package com.copyright.rup.dist.foreign.repository.api;

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
}
