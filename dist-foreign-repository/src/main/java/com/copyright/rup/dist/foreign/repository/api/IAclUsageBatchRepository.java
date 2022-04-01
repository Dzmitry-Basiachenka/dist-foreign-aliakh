package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;

import java.util.List;

/**
 * Represents interface of repository for ACL usage batches.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclUsageBatchRepository {

    /**
     * Checks whether ACL usage batch with provided name exists.
     *
     * @param usageBatchName name of the {@link AclUsageBatch}
     * @return {@code true} if ACL usage batch with provided name exists, otherwise {@code false}
     */
    boolean isAclUsageBatchExist(String usageBatchName);

    /**
     * Inserts ACL usage batch.
     *
     * @param usageBatch instance of {@link AclUsageBatch}
     */
    void insert(AclUsageBatch usageBatch);

    /**
     * Gets ACL usage batch by its id.
     *
     * @param usageBatchId id of the {@link AclUsageBatch}
     * @return {@link AclUsageBatch} with the given id or {@code null} if none exists
     */
    AclUsageBatch findById(String usageBatchId);

    /**
     * Finds list of {@link AclUsageBatch}es.
     *
     * @return list of {@link AclUsageBatch}es
     */
    List<AclUsageBatch> findAll();
}
