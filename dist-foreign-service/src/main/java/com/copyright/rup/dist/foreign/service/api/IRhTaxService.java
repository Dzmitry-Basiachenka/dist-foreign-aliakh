package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Usage;

import java.util.List;

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
     * Verifies whether RH tax country for NTS {@link Usage} is US or not. In positive case updates status to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#US_TAX_COUNTRY} and adds log message.
     *
     * @param usage NTS {@link Usage} to check RH tax country
     */
    void processTaxCountryCode(Usage usage);

    /**
     * Verifies whether RH tax country for NTS {@link Usage}s is US or not. For US usages updates status of to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#US_TAX_COUNTRY} and adds log message.
     *
     * @param usages list of NTS {@link Usage}s to check RH tax country
     */
    void processTaxCountryCode(List<Usage> usages);
}
