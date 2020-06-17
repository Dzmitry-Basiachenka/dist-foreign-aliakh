package com.copyright.rup.dist.foreign.integration.oracle.impl;

import com.copyright.rup.common.caching.impl.AbstractCacheService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link IOracleRhTaxCountryService} with caching.
 * See {@link AbstractCacheService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * </p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleRhTaxCountryCacheService extends AbstractCacheService<Long, Boolean>
    implements IOracleRhTaxCountryService {

    @Autowired
    @Qualifier("df.integration.oracleRhTaxCountryService")
    private IOracleRhTaxCountryService oracleRhTaxCountryService;

    /**
     * Constructor.
     *
     * @param timeToLive cache expiration time, minutes
     */
    public OracleRhTaxCountryCacheService(int timeToLive) {
        super.setExpirationTime(TimeUnit.MINUTES.toSeconds(timeToLive));
    }

    @Override
    public boolean isUsTaxCountry(Long accountNumber) {
        return getFromCache(Objects.requireNonNull(accountNumber));
    }

    @Override
    protected Boolean loadData(Long accountNumber) {
        return oracleRhTaxCountryService.isUsTaxCountry(accountNumber);
    }
}
