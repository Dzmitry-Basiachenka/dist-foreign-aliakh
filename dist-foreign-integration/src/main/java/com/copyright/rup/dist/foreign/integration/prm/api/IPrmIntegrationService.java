package com.copyright.rup.dist.foreign.integration.prm.api;

import com.copyright.rup.dist.common.domain.Currency;
import com.copyright.rup.dist.common.domain.Rightsholder;

import java.util.List;
import java.util.Set;

/**
 * Interface for service to encapsulate logic for PRM system.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 */
public interface IPrmIntegrationService {

    /**
     * @return set of {@link Currency}s found in PRM.
     */
    Set<Currency> getCurrencies();

    /**
     * Retrieves list of {@link Rightsholder} by specified set of account numbers from PRM.
     *
     * @param accountNumbers the set of account numbers
     * @return the list of {@link Rightsholder}
     */
    List<Rightsholder> getRightsholders(Set<Long> accountNumbers);
}
