package com.copyright.rup.dist.foreign.integration.oracle.api;

import java.util.List;
import java.util.Map;

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
     * Gets map from rightsholders account numbers to country codes.
     *
     * @param accountNumbers list of rightsholders account numbers
     * @return map from rightsholders account numbers to country codes.
     * Map doesn't contains key for account number if country code wasn't found.
     */
    //TODO {ushalamitski} change method signature to process single account number
    Map<Long, String> getAccountNumbersToCountryCodesMap(List<Long> accountNumbers);
}
