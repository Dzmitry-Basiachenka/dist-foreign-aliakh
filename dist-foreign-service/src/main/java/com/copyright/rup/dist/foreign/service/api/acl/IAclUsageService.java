package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;

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
}
