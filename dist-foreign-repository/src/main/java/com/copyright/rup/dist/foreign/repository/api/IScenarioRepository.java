package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;
import java.util.Set;

/**
 * Interface for Scenario repository.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public interface IScenarioRepository {

    /**
     * Inserts {@link Scenario} into database.
     *
     * @param scenario {@link Scenario} instance
     */
    void insert(Scenario scenario);

    /**
     * Inserts NTS scenario and adds usages to it.
     *
     * @param scenario {@link Scenario} instance
     * @param filter   instance of {@link UsageFilter} for usages to be added
     */
    void insertNtsScenarioAndAddUsages(Scenario scenario, UsageFilter filter);

    /**
     * Refreshes {@link Scenario} in database.
     *
     * @param scenario {@link Scenario} instance
     */
    void refresh(Scenario scenario);

    /**
     * Finds {@link Scenario}s count with specified name.
     *
     * @param name {@link Scenario} name
     * @return count of found {@link Scenario}s
     */
    int findCountByName(String name);

    /**
     * Finds list of {@link Scenario}s by product family.
     *
     * @param productFamily product family
     * @return list of {@link Scenario}s
     */
    List<Scenario> findByProductFamily(String productFamily);

    /**
     * Finds list of {@link Scenario}s by product families and statuses.
     *
     * @param productFamilies set of product families
     * @param statuses        set of statuses
     * @return list of {@link Scenario}s
     */
    List<Scenario> findByProductFamiliesAndStatuses(Set<String> productFamilies, Set<ScenarioStatusEnum> statuses);

    /**
     * Finds {@link Scenario}s names associated with any of usages from selected usage batch.
     *
     * @param usageBatchId identifier of usage batch
     * @return found list of {@link Scenario}s names
     */
    List<String> findNamesByUsageBatchId(String usageBatchId);

    /**
     * Finds {@link Scenario} name associated with NTS {@link com.copyright.rup.dist.foreign.domain.FundPool}.
     *
     * @param fundPoolId fund pool id
     * @return {@link Scenario} name or {@code null} if none found
     */
    String findNameByNtsFundPoolId(String fundPoolId);

    /**
     * Finds {@link Scenario} name associated with AACL {@link com.copyright.rup.dist.foreign.domain.FundPool}.
     *
     * @param fundPoolId fund pool id
     * @return {@link Scenario} name or {@code null} if none found
     */
    String findNameByAaclFundPoolId(String fundPoolId);

    /**
     * Finds {@link Scenario} name associated with SAL {@link com.copyright.rup.dist.foreign.domain.FundPool}.
     *
     * @param fundPoolId fund pool id
     * @return {@link Scenario} name or {@code null} if none found
     */
    String findNameBySalFundPoolId(String fundPoolId);

    /**
     * Removes {@link Scenario} by given identifier.
     *
     * @param scenarioId {@link Scenario} identifier
     */
    void remove(String scenarioId);

    /**
     * Updates {@link Scenario} status.
     *
     * @param scenario {@link Scenario} to update
     */
    void updateStatus(Scenario scenario);

    /**
     * Updates {@link Scenario} name by provided id.
     *
     * @param scenarioId {@link Scenario} identifier
     * @param name       new scenario name
     * @param userName   user name
     */
    void updateNameById(String scenarioId, String name, String userName);

    /**
     * Updates scenarios status by provided ids.
     *
     * @param scenarioIds list of scenarios ids
     * @param status      {@link ScenarioStatusEnum} instance
     */
    void updateStatus(List<String> scenarioIds, ScenarioStatusEnum status);

    /**
     * Finds all source RROs belonging to the {@link Scenario} with given id.
     *
     * @param scenarioId {@link Scenario} id
     * @return list of source RROs for given scenario
     */
    List<Rightsholder> findSourceRros(String scenarioId);

    /**
     * Finds reported total, gross amount, service fee amount, net amount and last audited action
     * for selected {@link Scenario}.
     *
     * @param scenarioId {@link Scenario} id
     * @return {@link Scenario} with amounts
     */
    Scenario findWithAmountsAndLastAction(String scenarioId);

    /**
     * Finds reported total, gross amount, service fee amount, net amount and last audited action
     * for selected {@link Scenario} base on archived usages.
     *
     * @param scenarioId {@link Scenario} id
     * @return {@link Scenario} with amounts
     */
    Scenario findArchivedWithAmountsAndLastAction(String scenarioId);

    /**
     * Finds all {@link RightsholderPayeePair}s belonging to the source RRO with given account number within the
     * scenario with given id.
     *
     * @param scenarioId       {@link Scenario} id
     * @param rroAccountNumber RRO account number
     * @return list of {@link RightsholderPayeePair}s
     */
    List<RightsholderPayeePair> findRightsholdersByScenarioIdAndSourceRro(String scenarioId, Long rroAccountNumber);

    /**
     * Finds scenarios ids in {@link ScenarioStatusEnum#SENT_TO_LM}
     * containing only usages in {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ARCHIVED} status.
     *
     * @return list of found scenarios ids
     */
    List<String> findIdsForArchiving();

    /**
     * Finds {@link Scenario} by its id.
     *
     * @param scenarioId scenario id
     * @return instance of {@link Scenario} or {@code null} if none exists
     */
    Scenario findById(String scenarioId);
}
