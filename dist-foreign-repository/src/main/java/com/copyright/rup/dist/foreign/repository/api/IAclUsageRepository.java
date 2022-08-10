package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
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
     * Finds list of detail licensee class ids for specified batch.
     *
     * @param batchId          batch identifier
     * @param grantSetId       grant set identifier
     * @param licenseeClassIds set of detail licensee class mapping
     * @param typeOfUse        type of use
     * @return {@code true} if detail exists, {@code false} - otherwise
     */
    boolean usageExistForLicenseeClassesAndTypeOfUse(String batchId, String grantSetId, Set<Integer> licenseeClassIds,
                                                     String typeOfUse);

    /**
     * Finds list of {@link UsageAge}.
     *
     * @return list of usage age wights
     */
    List<UsageAge> findDefaultUsageAgesWeights();

    /**
     * Finds count of {@link AclUsageDto}s by ACL batch id where publication type or content unit price is null.
     * Check only eligible, granted, with usage age weight > 0 and quantity < 2000 usages
     *
     * @param batchId            ACL batch id
     * @param grantSetId         ACL grant set id
     * @param periodPriors       list of period priors
     * @param distributionPeriod distribution period
     * @return count of {@link AclUsageDto}s
     */
    int findCountInvalidUsages(String batchId, String grantSetId, Integer distributionPeriod,
                               List<Integer> periodPriors);

    /**
     * Copies ACL usages by ACL usage batch id.
     *
     * @param sourceUsageBatchId source ACL usage batch id
     * @param targetUsageBatchId target ACL usage batch id
     * @param userName           user name
     * @return ids of inserted ACL usages
     */
    List<String> copyAclUsages(String sourceUsageBatchId, String targetUsageBatchId, String userName);
}
