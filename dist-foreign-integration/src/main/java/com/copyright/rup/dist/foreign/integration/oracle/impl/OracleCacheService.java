package com.copyright.rup.dist.foreign.integration.oracle.impl;

import com.copyright.rup.common.caching.impl.AbstractCacheService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link IOracleService} with caching.
 * See {@link AbstractCacheService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * </p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleCacheService extends AbstractCacheService<Long, Boolean> implements IOracleService {

    @Autowired
    @Qualifier("df.integration.oracleService")
    private IOracleService oracleService;

    /**
     * Constructor.
     *
     * @param timeToLive cache expiration time, minutes
     */
    public OracleCacheService(int timeToLive) {
        super.setExpirationTime(TimeUnit.MINUTES.toSeconds(timeToLive));
    }

    @Override
    public Boolean isUsTaxCountry(Long accountNumber) {
        return getFromCache(Objects.requireNonNull(accountNumber));
    }

    @Override
    protected Boolean loadData(Long accountNumber) {
        return oracleService.isUsTaxCountry(accountNumber);
    }

    void setOracleService(IOracleService oracleService) {
        this.oracleService = oracleService;
    }
}
