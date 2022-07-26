package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;

import java.util.List;

/**
 * Interface for ACL scenario usage service.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 07/13/2022
 *
 * @author Ihar Suvorau
 */
public interface IAclScenarioUsageService {

    /**
     * Attaches usages to scenario.
     *
     * @param aclScenario ACL scenario to add ACL usages to
     * @param userName    username
     */
    void addUsagesToAclScenario(AclScenario aclScenario, String userName);

    /**
     * Creates scenario shares for each usage in scenario.
     *
     * @param aclScenario ACL scenario to create shares
     * @param userName    username
     */
    void addScenarioShares(AclScenario aclScenario, String userName);

    /**
     * Populates Pub Type Weights for all scenario usages.
     *
     * @param scenarioId scenario identifier
     * @param userName   username
     */
    void populatePubTypeWeights(String scenarioId, String userName);

    /**
     * Calculates and updates volume, value and detail shares for specified scenario.
     *
     * @param scenarioId scenario identifier
     * @param userName   username
     */
    void calculateScenarioShares(String scenarioId, String userName);

    /**
     * Calculates and updates gross, net and service_fee amounts for specified scenario.
     *
     * @param scenarioId scenario identifier
     * @param userName   username
     */
    void calculateScenarioAmounts(String scenarioId, String userName);

    /**
     * Deletes usages and shares with zero amounts from scenario for specified scenario.
     *
     * @param scenarioId scenario identifier
     */
    void deleteZeroAmountUsages(String scenarioId);

    /**
     * Gets {@link AclRightsholderTotalsHolder}s based on ACL scenario id.
     *
     * @param scenarioId  scenario id
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link AclRightsholderTotalsHolder}s
     */
    List<AclRightsholderTotalsHolder> getAclRightsholderTotalsHoldersByScenarioId(String scenarioId, String searchValue,
                                                                                  Pageable pageable, Sort sort);

    /**
     * Gets count of {@link AclRightsholderTotalsHolder}s based on ACL scenario id.
     *
     * @param scenarioId  scenario id
     * @param searchValue search value
     * @return count of {@link AclRightsholderTotalsHolder}s
     */
    int getAclRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue);
}
