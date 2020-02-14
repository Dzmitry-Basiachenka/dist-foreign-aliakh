package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;

/**
 * Interface for AACL usage repository.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 01/21/2020
 *
 * @author Ihar Suvorau
 */
public interface IAaclUsageRepository {

    /**
     * Inserts AACL usage into database.
     *
     * @param usage {@link Usage} instance
     */
    void insert(Usage usage);

    /**
     * Updates classified fields for AACL usages in database.
     *
     * @param usages   {@link AaclClassifiedUsage} instance
     * @param userName name of user
     */
    void updateClassifiedUsages(List<AaclClassifiedUsage> usages, String userName);

    /**
     * Deletes AACL {@link Usage} by given id.
     *
     * @param usageId usage identifier
     */
    void deleteById(String usageId);

    /**
     * Updates given AACL {@link Usage} in case of its version is the same as in database.
     *
     * @param usage {@link Usage} to update
     * @return id of updated record, otherwise {@code null}
     */
    String updateProcessedUsage(Usage usage);

    /**
     * Finds list of AACL {@link Usage}s by their ids.
     *
     * @param usageIds list of {@link Usage}s identifiers
     * @return list of {@link Usage}s
     */
    List<Usage> findByIds(List<String> usageIds);

    /**
     * Finds count of referenced usages in the df_usage_aacl table by ids.
     *
     * @param usageIds set of usage ids
     * @return the count of usages
     */
    int findReferencedAaclUsagesCountByIds(String... usageIds);

    /**
     * Finds list of AACL {@link UsageDto}s by usage filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> findDtosByFilter(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Finds AACL usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}
     * @return the count of usages
     */
    int findCountByFilter(UsageFilter filter);

    /**
     * Finds list of AACL usage periods.
     *
     * @return list of AACL usage periods
     */
    List<Integer> findUsagePeriods();

    /**
     * Verifies whether {@link Usage}s found by defined {@link UsageFilter} have specified status or not.
     *
     * @param filter {@link UsageFilter} instance
     * @param status {@link UsageStatusEnum} instance
     * @return {@code true} - if there are no {@link Usage}s found by defined {@link UsageFilter}
     * with status different from specified , {@code false} - otherwise
     */
    boolean isValidFilteredUsageStatus(UsageFilter filter, UsageStatusEnum status);

    /**
     * Deletes all {@link Usage}s from the batch with given id.
     *
     * @param batchId {@link com.copyright.rup.dist.foreign.domain.UsageBatch} id
     */
    void deleteByBatchId(String batchId);
}
