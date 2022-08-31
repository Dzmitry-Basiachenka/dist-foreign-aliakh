package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UsageAge;

import java.util.List;

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
     * Finds list of {@link AclScenario}s.
     *
     * @return list of {@link AclScenario}s
     */
    List<AclScenario> findAll();

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
     * Removes {@link AclScenario} by given identifier.
     *
     * @param scenarioId {@link AclScenario} identifier
     */
    void remove(String scenarioId);

    /**
     * Removes {@link AclPublicationType}s, {@link UsageAge}s, {@link DetailLicenseeClass}s and scenario details and shares
     * related to the scenario by given identifier.
     *
     * @param scenarioId {@link AclScenario} identifier
     */
    void removeScenarioData(String scenarioId);
}
