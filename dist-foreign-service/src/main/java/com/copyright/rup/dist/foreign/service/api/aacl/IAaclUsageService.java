package com.copyright.rup.dist.foreign.service.api.aacl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.Collection;
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
     * @return count of inserted usages
     */
    int insertUsages(UsageBatch usageBatch, Collection<Usage> usages);

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
     * Deletes AACL {@link Usage} with given id.
     *
     * @param usageId usage identifier
     */
    void deleteById(String usageId);
}
