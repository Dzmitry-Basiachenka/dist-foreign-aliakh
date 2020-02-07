package com.copyright.rup.dist.foreign.service.api.aacl;

import com.copyright.rup.dist.foreign.domain.AaclFundPool;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;

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
     * Checks whether AACL fund pool with provided name already exists or not.
     *
     * @param name AACL fund pool name
     * @return {@code true} - if AACL fund pool exists, {@code false} - otherwise
     */
    boolean aaclFundPoolExists(String name);

    /**
     * @return list of all existing {@link AaclFundPool}s.
     */
    List<AaclFundPool> getFundPools();

    /**
     * Gets {@link AaclFundPoolDetail}s by {@link AaclFundPool} id.
     *
     * @param fundPoolId {@link AaclFundPool} id
     * @return list of {@link AaclFundPoolDetail}s
     */
    List<AaclFundPoolDetail> getDetailsByFundPoolId(String fundPoolId);
}
