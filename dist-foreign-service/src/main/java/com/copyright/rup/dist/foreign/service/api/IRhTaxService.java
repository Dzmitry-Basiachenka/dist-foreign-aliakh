package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Usage;

/**
 * Service to process RH taxes.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/05/18
 *
 * @author Uladzislau Shalamitski
 */
public interface IRhTaxService {

    /**
     * Verifies whether RH tax country for NTS {@link Usage} is US or not.
     * Removes this {@link Usage} in case of non-US country.
     *
     * @param usage NTS {@link Usage} to check RH tax country
     */
    void processRhTaxCountry(Usage usage);
}
