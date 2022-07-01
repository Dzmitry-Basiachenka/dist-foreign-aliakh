package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;

import java.util.List;
import java.util.Set;

/**
 * Represents interface of repository for ACL usages.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclUsageRepository {

    /**
     * Populates ACL usages from UDM usages and UDM values and inserts them.
     *
     * @param usageBatchId id of the {@link com.copyright.rup.dist.foreign.domain.AclUsageBatch}
     * @param periods      periods
     * @param userName     user name
     * @return ids of inserted ACL usages
     */
    List<String> populateAclUsages(String usageBatchId, Set<Integer> periods, String userName);

    /**
     * Updates ACL usage.
     *
     * @param aclUsageDto instance of {@link AclUsageDto}
     */
    void update(AclUsageDto aclUsageDto);

    /**
     * Finds ACL usages by their ids.
     *
     * @param usageIds list of ids of ACL usages
     * @return list of {@link AclUsageDto}s
     */
    List<AclUsageDto> findByIds(List<String> usageIds);

    /**
     * Finds ACL usages count based on applied filter.
     *
     * @param filter instance of {@link AclUsageFilter}
     * @return the count of usages
     */
    int findCountByFilter(AclUsageFilter filter);

    /**
     * Finds list of {@link AclUsageDto}s by ACL usage filter.
     *
     * @param filter   instance of {@link AclUsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link AclUsageDto}
     */
    List<AclUsageDto> findDtosByFilter(AclUsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds list of periods from ACL usages.
     *
     * @return list of periods
     */
    List<Integer> findPeriods();

    /**
     * Finds list of {@link UsageAge}.
     *
     * @return list of usage age wights
     */
    List<UsageAge> findDefaultUsageAgesWeights();

    /**
     * Finds {@link AclRightsholderTotalsHolder}s based on ACL scenario id.
     *
     * @param scenarioId  scenario id
     * @param searchValue search value
     * @param pageable    instance of {@link Pageable}
     * @param sort        instance of {@link Sort}
     * @return list of {@link AclRightsholderTotalsHolder}s
     */
    List<AclRightsholderTotalsHolder> findAclRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                   String searchValue,
                                                                                   Pageable pageable, Sort sort);

    /**
     * Finds count of {@link AclRightsholderTotalsHolder}s based on ACL scenario id.
     *
     * @param scenarioId  scenario id
     * @param searchValue search value
     * @return count of {@link AclRightsholderTotalsHolder}s
     */
    int findAclRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue);

    /**
     * Attaches usages to scenario.
     *
     * @param aclScenario ACL scenario to add ACL usages to
     * @param userName    user name
     */
    void addToAclScenario(AclScenario aclScenario, String userName);

    /**
     * Finds list of {@link AclScenarioDetail}s by ACL scenario uid.
     *
     * @param scenarioId scenario id
     * @return list of {@link AclScenarioDetail}s
     */
    List<AclScenarioDetail> findScenarioDetailsByScenarioId(String scenarioId);
}
