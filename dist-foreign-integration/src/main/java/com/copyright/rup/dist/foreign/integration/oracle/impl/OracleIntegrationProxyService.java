package com.copyright.rup.dist.foreign.integration.oracle.impl;

import com.copyright.rup.common.caching.impl.AbstractCacheService;
import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleIntegrationService;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link IOracleIntegrationService} with caching.
 * See {@link AbstractCacheService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * </p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
@Service("df.integration.oracleIntegrationProxyService")
public class OracleIntegrationProxyService extends AbstractCacheService<Long, String> implements
    IOracleIntegrationService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.integration.oracleIntegrationService")
    private IOracleIntegrationService oracleService;

    @Override
    @Value("$RUP{dist.foreign.integration.rest.oracle.ttl.minutes}")
    public void setExpirationTime(Long expirationTime) {
        super.setExpirationTime(TimeUnit.MINUTES.toSeconds(expirationTime));
    }

    @Override
    public Map<Long, String> getAccountNumbersToCountryCodesMap(List<Long> accountNumbers) {
        Objects.requireNonNull(accountNumbers);
        LOGGER.debug("Get RHs country codes. Started. AccountNumbersCount={}", accountNumbers.size());
        Map<Long, String> result = new HashMap<>(accountNumbers.size());
        accountNumbers
            .forEach(accountNumber -> {
                String countryCode = getFromCache(accountNumber);
                if (StringUtils.isNotEmpty(countryCode)) {
                    result.put(accountNumber, countryCode);
                }
            });
        LOGGER.debug("Get RHs country codes. Finished. AccountNumbersCount={}, AccountNumbersToCountryCodesMapCount={}",
            accountNumbers.size(), result.size());
        return result;
    }

    @Override
    protected String loadData(Long accountNumber) {
        Map<Long, String> result = oracleService.getAccountNumbersToCountryCodesMap(ImmutableList.of(accountNumber));
        return ObjectUtils.defaultIfNull(result.get(accountNumber), StringUtils.EMPTY);
    }

    void setOracleService(IOracleIntegrationService oracleService) {
        this.oracleService = oracleService;
    }
}
