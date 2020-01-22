package com.copyright.rup.dist.foreign.service.api.aacl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UsageDto;
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
     * Gets list of {@link UsageDto}s based on applied filter.
     *
     * @param filter   instance of {@link UsageFilter}
     * @param pageable instance of {@link Pageable}
     * @param sort     instance of {@link Sort}
     * @return the list of {@link UsageDto}
     */
    List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort);

    /**
     * Gets usages count based on applied filter.
     *
     * @param filter instance of {@link UsageFilter}.
     * @return count of usages
     */
    int getUsagesCount(UsageFilter filter);
}
