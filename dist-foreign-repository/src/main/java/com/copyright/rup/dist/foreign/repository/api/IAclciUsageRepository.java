package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Interface for ACLCI usage repository.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclciUsageRepository extends Serializable {

    /**
     * Inserts ACLCI usage.
     *
     * @param usage {@link Usage} instance
     */
    void insert(Usage usage);

    /**
     * Finds list of ACLCI usages by their ids.
     *
     * @param usageIds list of {@link Usage}s identifiers
     * @return list of {@link Usage}s
     */
    List<Usage> findByIds(List<String> usageIds);

    /**
     * Finds usages count by usage filter.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the count of usages
     */
    int findCountByFilter(UsageFilter filter);

    /**
     * Finds list of {@link UsageDto}s by usage filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Updates usages to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#ELIGIBLE}, sets new RH Account #
     * and Wr Wrk Inst by provided identifiers.
     *
     * @param usageIds        usage ids to update
     * @param rhAccountNumber rh account number to set
     * @param wrWrkInst       wr wrk inst to set (can be {@code null}
     * @param userName        updated username
     */
    void updateToEligibleByIds(Set<String> usageIds, Long rhAccountNumber, Long wrWrkInst, String userName);

    /**
     * Deletes all {@link Usage}s from the batch with given id.
     *
     * @param batchId {@link com.copyright.rup.dist.foreign.domain.UsageBatch} id
     */
    void deleteByBatchId(String batchId);
}
