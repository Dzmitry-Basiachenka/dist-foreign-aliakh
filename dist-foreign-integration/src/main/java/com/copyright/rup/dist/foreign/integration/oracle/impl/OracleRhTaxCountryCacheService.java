package com.copyright.rup.dist.foreign.integration.oracle.impl;

import com.copyright.rup.dist.common.integration.rest.prm.AbstractMultipleCacheService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link IOracleRhTaxCountryService} with caching.
 * See {@link AbstractMultipleCacheService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 05/04/2020
 *
 * @author Uladzislau Shalamitski
 */
public class OracleRhTaxCountryCacheService extends AbstractMultipleCacheService<Long, Boolean>
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
    public Map<Long, Boolean> isUsTaxCountry(Set<Long> accountNumbers) {
        return getDataFromCache(accountNumbers);
    }

    @Override
    public Map<Long, Boolean> loadData(Set<Long> accountNumbers) {
        return oracleRhTaxCountryService.isUsTaxCountry(accountNumbers);
    }

    @Override
    protected Boolean loadData(Long key) {
        // Do not use getFromCache method as it will always return false
        return Boolean.FALSE;
    }
}
