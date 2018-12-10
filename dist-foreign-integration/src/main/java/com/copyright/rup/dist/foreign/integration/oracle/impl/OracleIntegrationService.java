package com.copyright.rup.dist.foreign.integration.oracle.impl;

import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleIntegrationService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * Implementation of {@link IOracleIntegrationService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/27/2018
 *
 * @author Uladzislau Shalamitski
 */
@Service
public class OracleIntegrationService implements IOracleIntegrationService {

    @Autowired
    @Qualifier("df.integration.oracleCacheService")
    private IOracleService oracleService;

    @Override
    public boolean isUsCountryCode(Long accountNumber) {
        Map<Long, String> accountNumberToCountryCodeMap =
            oracleService.getAccountNumbersToCountryCodesMap(Collections.singletonList(accountNumber));
        return "US".equals(accountNumberToCountryCodeMap.get(accountNumber));
    }
}
