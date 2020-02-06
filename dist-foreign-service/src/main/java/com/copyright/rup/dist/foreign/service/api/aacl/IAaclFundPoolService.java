package com.copyright.rup.dist.foreign.service.api.aacl;

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
     * Checks whether AACL fund pool with provided name already exists or not.
     *
     * @param name AACL fund pool name
     * @return {@code true} - if AACL fund pool exists, {@code false} - otherwise
     */
    boolean aaclFundPoolExists(String name);
}
