package com.copyright.rup.dist.foreign.integration.oracle.api;

/**
 * Interface for working with Oracle AP REST.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public interface IOracleService {

    /**
     * Checks whether the tax residency country is US or not.
     *
     * @param accountNumber RH account number
     * @return {@code true} if the tax residency country is US, otherwise {@code false}
     */
    Boolean isUsTaxCountry(Long accountNumber);
}
