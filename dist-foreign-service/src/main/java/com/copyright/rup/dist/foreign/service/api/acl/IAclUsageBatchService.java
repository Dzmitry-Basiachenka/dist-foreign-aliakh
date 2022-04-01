package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;

import java.util.List;

/**
 * Represents interface of repository for ACL usage batches.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/01/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclUsageBatchService {

    /**
     * Checks whether ACL usage batch with provided name exists.
     *
     * @param usageBatchName name of the {@link AclUsageBatch}
     * @return {@code true} if ACL usage batch with provided name exists, otherwise {@code false}
     */
    boolean isAclUsageBatchExist(String usageBatchName);

    /**
     * Inserts ACL usage batch with populated ACL usages.
     *
     * @param usageBatch instance of {@link AclUsageBatch}
     * @return count of inserted ACL usages
     */
    int insert(AclUsageBatch usageBatch);

    /**
     * Gets list of all {@link AclUsageBatch}es.
     *
     * @return list of all {@link AclUsageBatch}es
     */
    List<AclUsageBatch> getAll();
}
