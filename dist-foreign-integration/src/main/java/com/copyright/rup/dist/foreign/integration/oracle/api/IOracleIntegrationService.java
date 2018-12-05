package com.copyright.rup.dist.foreign.integration.oracle.api;

/**
 * Interface for working with Oracle AP REST.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 11/27/2018
 *
 * @author Uladzislau Shalamitski
 */
public interface IOracleIntegrationService {

    /**
     * Returns {@code true} if country code is US, otherwise {@code false}.
     *
     * @param accountNumber rh account number
     * @return {@code true} if country code is US
     */
    boolean isUsCountryCode(Long accountNumber);
}
