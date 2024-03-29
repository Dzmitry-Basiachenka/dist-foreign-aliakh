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

    /**
     * Gets list of all ACl usage batches by period.
     *
     * @param period       period end date
     * @param editableFlag editable flag
     * @return list of {@link AclUsageBatch}s
     */
    List<AclUsageBatch> getUsageBatchesByPeriod(Integer period, boolean editableFlag);

    /**
     * Gets all available periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Gets {@link AclUsageBatch} by its id.
     *
     * @param usageBatchId ACl usage batch id
     * @return instance of {@link AclUsageBatch} or {@code null} if none exists
     */
    AclUsageBatch getById(String usageBatchId);

    /**
     * Copies ACL usage batch with ACL usages.
     *
     * @param sourceUsageBatchId source usage batch id
     * @param aclUsageBatch      {@link AclUsageBatch} instance
     * @return count of copied ACL usages
     */
    int copyUsageBatch(String sourceUsageBatchId, AclUsageBatch aclUsageBatch);

    /**
     * Deletes ACL usage batch.
     *
     * @param usageBatch instance of {@link AclUsageBatch}
     */
    void deleteAclUsageBatch(AclUsageBatch usageBatch);
}
