package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AclUsageDto;

import java.util.List;
import java.util.Set;

/**
 * Represents interface of repository for ACL usages.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclUsageRepository {

    /**
     * Populates ACL usages from UDM usages and UDM values and inserts them.
     *
     * @param usageBatchId id of the {@link com.copyright.rup.dist.foreign.domain.AclUsageBatch}
     * @param periods      periods
     * @param userName     user name
     * @return ids of inserted ACL usages
     */
    List<String> populateAclUsages(String usageBatchId, Set<Integer> periods, String userName);

    /**
     * Finds ACL usages by their ids.
     *
     * @param usageIds list of ids of ACL usages
     * @return list of {@link AclUsageDto}s
     */
    List<AclUsageDto> findByIds(List<String> usageIds);
}
