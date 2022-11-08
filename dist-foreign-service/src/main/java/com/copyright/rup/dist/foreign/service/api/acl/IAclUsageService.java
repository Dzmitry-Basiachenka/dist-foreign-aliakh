package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Represents interface of repository for ACL usages.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/01/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclUsageService {

    /**
     * Populates ACL usages from UDM usages and UDM values and inserts them.
     *
     * @param usageBatchId id of the {@link com.copyright.rup.dist.foreign.domain.AclUsageBatch}
     * @param periods      periods
     * @param userName     user name
     * @return count of inserted ACL usages
     */
    int populateAclUsages(String usageBatchId, Set<Integer> periods, String userName);

    /**
     * Updates ACL usages.
     *
     * @param aclUsageDtos collection of {@link AclUsageDto}s
     */
    void updateUsages(Collection<AclUsageDto> aclUsageDtos);

    /**
     * Gets count of ACL usages based on applied filter.
     *
     * @param filter instance of {@link AclUsageFilter}
     * @return the count of ACL usages
     */
    int getCount(AclUsageFilter filter);

    /**
     * Gets list of {@link AclUsageDto}s based on applied filter.
     *
     * @param filter   instance of {@link AclUsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link AclUsageDto}s
     */
    List<AclUsageDto> getDtos(AclUsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets all available periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Checks whether details in specified batch exist for at least on licensee class and type of use.
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
     * @return threshold value for size of ACL records.
     */
    int getRecordThreshold();

    /**
     * Gets list of {@link UsageAge}s.
     *
     * @return list of usage age wights
     */
    List<UsageAge> getDefaultUsageAgesWeights();

    /**
     * Gets count of invalid ACL usages (publication type or content unit price is null).
     * Checks only ACL usages that are eligible, granted, with usage age weight > 0 and quantity < 2000.
     *
     * @param batchId            ACL batch id
     * @param grantSetId         ACL grant set id
     * @param periodPriors       list of period priors
     * @param distributionPeriod distribution period
     * @return count of invalid ACL usages
     */
    int getCountInvalidUsages(String batchId, String grantSetId, Integer distributionPeriod,
                              List<Integer> periodPriors);

    /**
     * Writes invalid ACL usages (publication type or content unit price is null) into CSV output stream.
     * Checks only ACL usages that are eligible, granted, with usage age weight > 0 and quantity < 2000.
     *
     * @param batchId            ACL batch id
     * @param grantSetId         ACL grant set id
     * @param periodPriors       list of period priors
     * @param distributionPeriod distribution period
     * @param outputStream       instance of {@link OutputStream}
     */
    void writeInvalidUsagesCsvReport(String batchId, String grantSetId, Integer distributionPeriod,
                                     List<Integer> periodPriors, OutputStream outputStream);

    /**
     * Copies ACL usages by ACL usage batch id.
     *
     * @param sourceUsageBatchId source ACL usage batch id
     * @param targetUsageBatchId target ACL usage batch id
     * @param userName           username
     * @return count of copied grant details
     */
    int copyAclUsages(String sourceUsageBatchId, String targetUsageBatchId, String userName);

    /**
     * Deletes ACL usages by ACL usage id.
     *
     * @param usageBatchId id of the {@link com.copyright.rup.dist.foreign.domain.AclUsageBatch}
     */
    void deleteUsages(String usageBatchId);
}
