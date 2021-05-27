package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.RhTaxInformation;
import com.copyright.rup.dist.foreign.domain.Usage;

import java.util.List;
import java.util.Set;

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
     * Verifies whether RH tax country for NTS {@link Usage}s is US or not. For US usages updates status of to
     * {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#US_TAX_COUNTRY} and adds log message.
     *
     * @param usages list of NTS {@link Usage}s to check RH tax country
     */
    void processTaxCountryCode(List<Usage> usages);

    /**
     * Gets RH Tax Information for RHs from the given scenarios.
     *
     * @param scenarioIds  set of scenario ids
     * @param numberOfDays TBOs who received notification within last {@code numberOfDays} won't be included in the
     *                     result
     * @return list of {@link RhTaxInformation} items
     */
    List<RhTaxInformation> getRhTaxInformation(Set<String> scenarioIds, int numberOfDays);
}
