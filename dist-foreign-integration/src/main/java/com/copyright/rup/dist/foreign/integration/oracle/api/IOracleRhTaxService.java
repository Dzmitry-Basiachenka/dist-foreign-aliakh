package com.copyright.rup.dist.foreign.integration.oracle.api;

/**
 * Interface for working with Oracle AP REST for getting RH tax info.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public interface IOracleRhTaxService {

    /**
     * Checks whether the tax residency country is US or not.
     *
     * @param accountNumber RH account number
     * @return {@code true} if the tax residency country is US, otherwise {@code false}
     */
    boolean isUsTaxCountry(Long accountNumber);
}
