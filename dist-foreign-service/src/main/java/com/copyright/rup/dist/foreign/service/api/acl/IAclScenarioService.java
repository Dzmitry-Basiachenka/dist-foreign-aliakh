package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;

import java.util.List;
import java.util.Set;

/**
 * Interface for ACL scenario service.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 06/23/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclScenarioService {

    /**
     * Inserts {@link AclScenario}.
     *
     * @param aclScenario instance of {@link AclScenario}
     */
    void insertScenario(AclScenario aclScenario);

    /**
     * Inserts usage age weights of ACL scenario.
     *
     * @param usageAges  list of {@link UsageAge}s
     * @param scenarioId scenario uid
     * @param userName   username
     */
    void insertAclScenarioUsageAgeWeights(List<UsageAge> usageAges, String scenarioId, String userName);

    /**
     * Inserts licensee classes of ACL scenario.
     *
     * @param licenseeClasses list of {@link DetailLicenseeClass}s
     * @param scenarioId      scenario uid
     * @param userName        username
     */
    void insertAclScenarioLicenseeClasses(List<DetailLicenseeClass> licenseeClasses, String scenarioId,
                                          String userName);

    /**
     * Inserts publication type weight of ACL scenario.
     *
     * @param publicationTypes list of {@link AclPublicationType}s
     * @param scenarioId       scenario uid
     * @param userName         username
     */
    void insertAclScenarioPubTypeWeights(List<AclPublicationType> publicationTypes, String scenarioId, String userName);

    /**
     * Gets list of {@link AclScenario}s.
     *
     * @param filter applied {@link AclScenarioFilter}
     * @return list of {@link AclScenario}s
     */
    List<AclScenario> getScenarios(AclScenarioFilter filter);

    /**
     * Gets list of {@link AclScenario}s for specified period.
     *
     * @param period selected period
     * @return list of {@link AclScenario}s
     */
    List<AclScenario> getScenariosByPeriod(Integer period);

    /**
     * Gets list of scenario periods.
     *
     * @return list of periods
     */
    List<Integer> getScenarioPeriods();

    /**
     * Checks whether {@link AclScenario} with specified name already exists in database.
     *
     * @param scenarioName name of {@link AclScenario} to check
     * @return {@code true} if {@link AclScenario} with specified name already exists in database,
     * otherwise {@code false}
     */
    boolean aclScenarioExists(String scenarioName);

    /**
     * Gets {@link AclScenario} names associated with usage batch id.
     *
     * @param usageBatchId usage batch id
     * @return {@link AclScenario}s names associated with usage batch id
     */
    List<String> getScenarioNamesByUsageBatchId(String usageBatchId);

    /**
     * Gets {@link AclScenario} names associated with fund pool id.
     *
     * @param fundPoolId fund pool id
     * @return {@link AclScenario} names associated with fund pool id
     */
    List<String> getScenarioNamesByFundPoolId(String fundPoolId);

    /**
     * Gets {@link AclScenario} names associated with grant set id.
     *
     * @param grantSetId grant set id
     * @return {@link AclScenario}s names associated with grant set id
     */
    List<String> getScenarioNamesByGrantSetId(String grantSetId);

    /**
     * Gets fund pool details with amounts that can't be distributed for selected batch and fund pool.
     *
     * @param batchId    usage batch id
     * @param fundPoolId fund pool id
     * @param grantSetId grant set id
     * @param mapping    {@link DetailLicenseeClass}es mapping
     * @return list of {@link AclFundPoolDetailDto}es
     */
    Set<AclFundPoolDetailDto> getFundPoolDetailsNotToBeDistributed(String batchId, String fundPoolId,
                                                                   String grantSetId,
                                                                   List<DetailLicenseeClass> mapping);

    /**
     * Gets ACl scenario by unique identifier.
     *
     * @param scenarioId scenario uid
     * @return instance of {@link AclScenario}s
     */
    AclScenario getScenarioById(String scenarioId);

    /**
     * Gets list of {@link UsageAge}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link UsageAge}es
     */
    List<UsageAge> getUsageAgeWeightsByScenarioId(String scenarioId);

    /**
     * Gets list of {@link AclPublicationType}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link AclPublicationType}es
     */
    List<AclPublicationType> getAclPublicationTypesByScenarioId(String scenarioId);

    /**
     * Gets list of {@link DetailLicenseeClass}es by scenario id.
     *
     * @param scenarioId scenario id
     * @return list of {@link DetailLicenseeClass}es
     */
    List<DetailLicenseeClass> getDetailLicenseeClassesByScenarioId(String scenarioId);

    /**
     * Deletes {@link AclScenario} by given identifier.
     *
     * @param aclScenario instance of {@link AclScenario}
     */
    void deleteAclScenario(AclScenario aclScenario);

    /**
     * Gets list of ACl scenarios by statuses.
     *
     * @param statuses set of statuses
     * @return list of {@link Scenario}s
     */
    List<Scenario> getAclScenariosByStatuses(Set<ScenarioStatusEnum> statuses);

    /**
     * Validate that the system is not contains a scenario with period and license type in
     * {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#SUBMITTED},
     * {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#APPROVED},
     * {@link com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum#ARCHIVED} statuses.
     *
     * @param scenario instance of {@link AclScenario}
     * @return <code>true</code> if scenario is not exists with license type, period and
     * SUBMITTED, APPROVED, ARCHIVED statuses otherwise <code>false</code>
     */
    boolean isNotExistsSubmittedScenario(AclScenario scenario);

    /**
     * Change scenario state {@link AclScenario}.
     *
     * @param scenario {@link AclScenario} instance
     * @param status   new {@link ScenarioStatusEnum} status
     * @param action   action
     * @param reason   reason specified by user
     */
    void changeScenarioState(AclScenario scenario, ScenarioStatusEnum status, ScenarioActionTypeEnum action,
                             String reason);

    /**
     * Sends given {@link AclScenario} to LM.
     *
     * @param scenario {@link AclScenario} instance
     */
    void sendToLm(AclScenario scenario);
}
