package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;

/**
 * Interface for SAL usages repository.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
public interface ISalUsageRepository {

    /**
     * Inserts SAL item bank detail into database.
     *
     * @param usage {@link Usage} instance
     */
    void insertItemBankDetail(Usage usage);

    /**
     * Inserts SAL usage data detail into database.
     *
     * @param usage {@link Usage} instance
     */
    void insertUsageDataDetail(Usage usage);

    /**
     * Finds list of SAL {@link Usage}s by their ids.
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
     * Checks whether provided Work Portion ID already exists in the system.
     *
     * @param workPortionId work portion id
     * @return {@code true} - if Work Portion ID exists, {@code false} - otherwise
     */
    boolean workPortionIdExists(String workPortionId);

    /**
     * Checks whether provided Work Portion ID exists in the given batch.
     *
     * @param workPortionId work portion id
     * @param batchId       batch id
     * @return {@code true} - if Work Portion ID exists, {@code false} - otherwise
     */
    boolean workPortionIdExists(String workPortionId, String batchId);

    /**
     * Finds {@link com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum#IB} detail grade by work portion id.
     *
     * @param workPortionId work portion id
     * @return {@link com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum#IB} detail grade
     */
    String findItemBankDetailGradeByWorkPortionId(String workPortionId);

    /**
     * Checks whether usage details exists in the given batch.
     *
     * @param batchId batch id
     * @return {@code true} - if usage details exists, {@code false} - otherwise
     */
    boolean usageDetailsExistsInItemBank(String batchId);
}
