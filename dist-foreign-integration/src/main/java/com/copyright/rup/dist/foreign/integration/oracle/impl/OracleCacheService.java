package com.copyright.rup.dist.foreign.integration.oracle.impl;

import com.copyright.rup.common.caching.impl.AbstractCacheService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleService;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class OracleCacheService extends AbstractCacheService<Long, String> implements IOracleService {

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
    public Map<Long, String> getAccountNumbersToCountryCodesMap(List<Long> accountNumbers) {
        Objects.requireNonNull(accountNumbers);
        Map<Long, String> result = new HashMap<>(accountNumbers.size());
        accountNumbers
            .forEach(accountNumber -> {
                String countryCode = getFromCache(accountNumber);
                if (StringUtils.isNotEmpty(countryCode)) {
                    result.put(accountNumber, countryCode);
                }
            });
        return result;
    }

    @Override
    protected String loadData(Long accountNumber) {
        Map<Long, String> result = oracleService.getAccountNumbersToCountryCodesMap(ImmutableList.of(accountNumber));
        return ObjectUtils.defaultIfNull(result.get(accountNumber), StringUtils.EMPTY);
    }

    void setOracleService(IOracleService oracleService) {
        this.oracleService = oracleService;
    }
}
