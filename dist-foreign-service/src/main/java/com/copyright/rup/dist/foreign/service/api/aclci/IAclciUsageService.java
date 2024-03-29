package com.copyright.rup.dist.foreign.service.api.aclci;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;
import java.util.Set;

/**
 * Interface for ACLCI usage service.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/12/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclciUsageService {

    /**
     * Inserts ACLCI usages.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     */
    void insertUsages(UsageBatch usageBatch, List<Usage> usages);

    /**
     * Finds ACLCI usages by their ids and sends them on queue for PI matching process.
     *
     * @param usageIds  list of usages ids
     * @param batchName batch name
     */
    void sendForMatching(List<String> usageIds, String batchName);

    /**
     * Gets list of {@link Usage}s by specified {@link Usage} ids.
     *
     * @param usageIds list of {@link Usage} ids
     * @return list of {@link Usage}s
     */
    List<Usage> getUsagesByIds(List<String> usageIds);

    /**
     * Gets usages count by usage filter.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the count of usages
     */
    int getUsagesCount(UsageFilter filter);

    /**
     * Gets list of {@link UsageDto}s by usage filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Updates usages to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, sets new RH Account #
     * and Wr Wrk Inst by provided identifiers.
     *
     * @param usageIds        usage ids to update
     * @param rhAccountNumber rh account number to set
     * @param wrWrkInst       wr wrk inst to set (can be {@code null}
     * @param reason          action reason
     */
    void updateToEligibleByIds(Set<String> usageIds, Long rhAccountNumber, Long wrWrkInst, String reason);

    /**
     * Deletes all {@link Usage}s associated with the given ACLCI {@link UsageBatch}.
     *
     * @param usageBatch {@link UsageBatch} to delete usages from
     */
    void deleteUsageBatchDetails(UsageBatch usageBatch);
}
