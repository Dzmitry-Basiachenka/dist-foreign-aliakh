package com.copyright.rup.dist.foreign.service.api.aacl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;

/**
 * Represents service interface for AACL specific usages business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/22/2020
 *
 * @author Ihar Suvorau
 */
public interface IAaclUsageService {

    /**
     * Inserts AACL usages.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     */
    void insertUsages(UsageBatch usageBatch, List<Usage> usages);

    /**
     * Inserts AACL usages from baseline.
     *
     * @param usageBatch usage batch
     * @return list of ids of inserted usages
     */
    List<String> insertUsagesFromBaseline(UsageBatch usageBatch);

    /**
     * Updates classified AACL usages.
     *
     * @param usages list of {@link AaclClassifiedUsage}s
     * @return count of updated usages
     */
    int updateClassifiedUsages(List<AaclClassifiedUsage> usages);

    /**
     * Gets list of {@link Usage}s by specified {@link Usage} ids.
     *
     * @param usageIds list of {@link Usage} ids
     * @return list of {@link Usage}s
     */
    List<Usage> getUsagesByIds(List<String> usageIds);

    /**
     * Gets list of AACL {@link UsageDto}s based on applied filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Updates AACL {@link Usage} and verifies that version of {@link Usage} is the same as in database.
     * Throws an {@link com.copyright.rup.dist.foreign.service.impl.InconsistentUsageStateException} in case of
     * there are {@link Usage} version discrepancies.
     *
     * @param usage {@link Usage} to update
     */
    void updateProcessedUsage(Usage usage);

    /**
     * Gets AACL usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}.
     * @return count of usages
     */
    int getUsagesCount(UsageFilter filter);

    /**
     * Gets list of AACL usage periods.
     *
     * @return list of AACL usage periods
     */
    List<Integer> getUsagePeriods();

    /**
     * Gets list of {@link UsageAge}s by {@link UsageFilter}.
     *
     * @param filter {@link UsageFilter} instance
     * @return list of found {@link UsageAge}s
     */
    List<UsageAge> getUsageAges(UsageFilter filter);

    /**
     * Deletes AACL {@link Usage} with given id.
     *
     * @param usageId usage identifier
     */
    void deleteById(String usageId);

    /**
     * Verifies whether all usages found by defined {@link UsageFilter} have specified status or not.
     *
     * @param filter {@link UsageFilter} instance
     * @param status {@link UsageStatusEnum} instance
     * @return {@code true} - if all filtered usages have specified {@link UsageStatusEnum},
     * {@code false} - otherwise
     */
    boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status);

    /**
     * Deletes all {@link Usage}s associated with the given AACL {@link UsageBatch}.
     *
     * @param usageBatch {@link UsageBatch} to delete usages from
     */
    void deleteUsageBatchDetails(UsageBatch usageBatch);

    /**
     * Finds AACL usages by their ids and sends them on queue for PI matching process.
     *
     * @param usageIds  list of usages ids
     * @param batchName batch name
     */
    void sendForMatching(List<String> usageIds, String batchName);

    /**
     * Gets list of {@link UsageDto}s by filter.
     *
     * @param filter   {@link AuditFilter}
     * @param pageable {@link Pageable}
     * @param sort     {@link Sort}
     * @return list of {@link UsageDto}s
     */
    List<UsageDto> getForAudit(AuditFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets count of items by filter.
     *
     * @param filter {@link AuditFilter}
     * @return count of items by filter
     */
    int getCountForAudit(AuditFilter filter);

    /**
     * Adds usages to the scenario.
     *
     * @param scenario {@link Scenario} to add usages to
     * @param filter   {@link UsageFilter} instance
     */
    void addUsagesToScenario(Scenario scenario, UsageFilter filter);
}
