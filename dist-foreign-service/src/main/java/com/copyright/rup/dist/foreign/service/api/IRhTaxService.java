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
     *
     * @param usage NTS {@link Usage} to check RH tax country
     * @return {@code true} if usage RH is US, {@code false} otherwise
     */
    boolean isUsCountryCodeUsage(Usage usage);
}
