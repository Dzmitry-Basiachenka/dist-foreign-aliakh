package com.copyright.rup.dist.foreign.integration.crm.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for services to encapsulate logic for CRM system.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/02/18
 *
 * @author Darya Baraukova
 */
public interface ICrmIntegrationService {

    /**
     * Gets rights distribution by set of cccEventId from the CRM system.
     *
     * @param cccEventIds set of cccEventId
     * @return map of cccEventId to set of usageIds
     */
    Map<String, Set<String>> getRightsDistribution(Set<String> cccEventIds);

    /**
     * Inserts rights distribution by list of {@link InsertRightsDistributionRequest} to the CRM system.
     *
     * @param requests list of {@link InsertRightsDistributionRequest} instances
     * @return instance of {@link InsertRightsDistributionResponse}
     */
    InsertRightsDistributionResponse insertRightsDistribution(List<InsertRightsDistributionRequest> requests);
}
