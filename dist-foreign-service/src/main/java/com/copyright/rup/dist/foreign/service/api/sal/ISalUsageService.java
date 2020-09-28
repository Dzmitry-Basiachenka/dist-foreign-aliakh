package com.copyright.rup.dist.foreign.service.api.sal;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;

/**
 * Interface for SAL usages service.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
public interface ISalUsageService {

    /**
     * Inserts SAL item bank details.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     */
    void insertItemBankDetails(UsageBatch usageBatch, List<Usage> usages);

    /**
     * Inserts SAL usage data details.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     */
    void insertUsageDataDetails(UsageBatch usageBatch, List<Usage> usages);

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
     * Finds SAL usages by their ids and sends them on queue for PI matching process.
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
     * Gets {@link com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum#IB} detail grade by work portion id.
     *
     * @param workPortionId work portion id
     * @return {@link com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum#IB} detail grade
     */
    String getItemBankDetailGradeByWorkPortionId(String workPortionId);
}
