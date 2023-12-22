package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;

import java.util.List;
import java.util.Set;

/**
 * Interface for ACL scenario repository.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/24/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclScenarioRepository {

    /**
     * Finds list of {@link AclScenario}s based on applied filter.
     *
     * @param filter instance of {@link AclScenarioFilter}
     * @return list of {@link AclScenario}s
     */
    List<AclScenario> findByFilter(AclScenarioFilter filter);

    /**
     * Finds list of {@link AclScenario}s for specified period.
     *
     * @param period selected period
     * @return list of {@link AclScenario}s
     */
    List<AclScenario> findByPeriod(Integer period);

    /**
     * Finds list of scenario periods.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();

    /**
     * Finds {@link AclScenario}s count with specified name.
     *
     * @param name {@link AclScenario} name
     * @return count of found {@link AclScenario}s
     */
    int findCountByName(String name);

    /**
     * Inserts ACL scenario.
     *
     * @param aclScenario instance of {@link AclScenario}
     */
    void insertAclScenario(AclScenario aclScenario);

    /**
     * Inserts usage age weight of ACL scenario.
     *
     * @param usageAge   instance of {@link UsageAge}
     * @param scenarioId scenario uid
     * @param userName   username
     */
    void insertAclScenarioUsageAgeWeight(UsageAge usageAge, String scenarioId, String userName);

    /**
     * Inserts licensee class of ACL scenario.
     *
     * @param licenseeClass instance of {@link DetailLicenseeClass}
     * @param scenarioId    scenario uid
     * @param userName      username
     */
    void insertAclScenarioLicenseeClass(DetailLicenseeClass licenseeClass, String scenarioId, String userName);

    /**
     * Inserts publication type weight of ACL scenario.
     *
     * @param publicationType instance of {@link AclPublicationType}
     * @param scenarioId      scenario uid
     * @param userName        username
     */
    void insertAclScenarioPubTypeWeight(AclPublicationType publicationType, String scenarioId, String userName);

    /**
     * Finds of {@link AclScenario}s by its identifier.
     *
     * @param scenarioId scenario id
     * @return list of {@link AclScenario}s
     */
    AclScenario findById(String scenarioId);

    /**
     * Finds names of {@link AclScenario} associated with usage batch.
     *
     * @param usageBatchId usage batch id
     * @return list of {@link AclScenario} names associated with usage batch
     */
    List<String> findScenarioNamesByUsageBatchId(String usageBatchId);

    /**
     * Finds names of {@link AclScenario} associated with fund pool.
     *
     * @param fundPoolId fund pool id
     * @return list of {@link AclScenario} names associated with fund pool
     */
    List<String> findScenarioNamesByFundPoolId(String fundPoolId);

    /**
     * Finds names of {@link AclScenario} associated with grant set.
     *
     * @param grantSetId grant set id
     * @return list of {@link AclScenario} names associated with grant set
     */
    List<String> findScenarioNamesByGrantSetId(String grantSetId);

    /**
     * Finds list of {@link UsageAge}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link UsageAge}es
     */
    List<UsageAge> findUsageAgeWeightsByScenarioId(String scenarioId);

    /**
     * Finds list of {@link AclPublicationType}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link AclPublicationType}es
     */
    List<AclPublicationType> findAclPublicationTypesByScenarioId(String scenarioId);

    /**
     * Finds list of {@link DetailLicenseeClass}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link DetailLicenseeClass}es
     */
    List<DetailLicenseeClass> findDetailLicenseeClassesByScenarioId(String scenarioId);

    /**
     * Deletes {@link AclScenario} by given identifier.
     *
     * @param scenarioId {@link AclScenario} identifier
     */
    void deleteScenario(String scenarioId);

    /**
     * Deletes {@link AclPublicationType}s, {@link UsageAge}s, {@link DetailLicenseeClass}s and scenario details
     * and shares related to the scenario by given identifier.
     *
     * @param scenarioId {@link AclScenario} identifier
     */
    void deleteScenarioData(String scenarioId);

    /**
     * Finds list of ACl scenarios by statuses.
     *
     * @param statuses set of statuses
     * @return list of {@link Scenario}s
     */
    List<Scenario> findAclScenariosByStatuses(Set<ScenarioStatusEnum> statuses);

    /**
     * Finds scenarios with license type, period and
     * {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#SUBMITTED},
     * {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#APPROVED},
     * {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#ARCHIVED} statuses are not exists.
     *
     * @param licenseType scenario license type
     * @param period      period
     * @return <code>true</code> if scenario is exists with license type, period and status
     * otherwise <code>false</code>
     */
    boolean submittedScenarioExistWithLicenseTypeAndPeriod(String licenseType, Integer period);

    /**
     * Updates {@link AclScenario} status.
     *
     * @param scenario {@link Scenario} to update
     */
    void updateStatus(AclScenario scenario);

    /**
     * Updates {@link AclScenario} name by provided id.
     *
     * @param scenarioId {@link AclScenario} identifier
     * @param name       new scenario name
     * @param userName   user name
     */
    void updateNameById(String scenarioId, String name, String userName);
}
