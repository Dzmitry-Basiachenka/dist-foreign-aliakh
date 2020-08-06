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
     * Inserts AACL usages.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     */
    void insertUsages(UsageBatch usageBatch, List<Usage> usages);

    /**
     * Checks whether provided Work Portion ID already exists in the system.
     *
     * @param workPortionId work portion id
     * @return {@code true} - if Work Portion ID exists, {@code false} - otherwise
     */
    boolean workPortionIdExists(String workPortionId);

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
}
