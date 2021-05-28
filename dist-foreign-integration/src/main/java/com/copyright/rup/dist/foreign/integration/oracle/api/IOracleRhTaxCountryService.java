package com.copyright.rup.dist.foreign.integration.oracle.api;

import java.util.Map;
import java.util.Set;

/**
 * Interface for working with Oracle AP REST for getting RH tax country.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 05/04/2020
 *
 * @author Uladzislau Shalamitski
 */
public interface IOracleRhTaxCountryService {

    /**
     * Checks whether the tax residency country is US or not for given account numbers.
     *
     * @param accountNumbers set of RH account numbers
     * @return map where key is account number and value is {@code true} if the tax residency country is US,
     * otherwise {@code false}
     */
    Map<Long, Boolean> isUsTaxCountry(Set<Long> accountNumbers);
}
