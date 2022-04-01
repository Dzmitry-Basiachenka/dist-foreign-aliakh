package com.copyright.rup.dist.foreign.service.api.acl;

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
}
