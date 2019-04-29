package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.List;

/**
 * Represents interface of repository for usage batches.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/02/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
public interface IUsageBatchRepository {

    /**
     * Inserts {@link UsageBatch}.
     *
     * @param usageBatch instance of {@link UsageBatch}
     */
    void insert(UsageBatch usageBatch);

    /**
     * Gets list of fiscal years from Usage Batches presented in DB and associated with specified Product Family.
     *
     * @param productFamily Product Family
     * @return list of fiscal years
     */
    List<Integer> findFiscalYearsByProductFamily(String productFamily);

    /**
     * @return list of all {@link UsageBatch} presented in DB.
     */
    List<UsageBatch> findAll();

    /**
     * Finds list of {@link UsageBatch}es by specified Product Family.
     *
     * @param productFamily Product Family
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> findByProductFamily(String productFamily);

    /**
     * Finds list of FAS/FAS2 {@link UsageBatch}es suitable for pre-service fee funds.
     *
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> findUsageBatchesForPreServiceFeeFunds();

    /**
     * Gets usage batches count with specified name.
     *
     * @param name batch name
     * @return count of found batches
     */
    int findCountByName(String name);

    /**
     * Deletes {@link UsageBatch} with given id from database.
     *
     * @param batchId id of the {@link UsageBatch} to be deleted
     */
    void deleteUsageBatch(String batchId);

    /**
     * Finds list of batches names that have Fund Pool Amount greater than zero
     * and don't have usages related to specified classification.
     * For case when classification is defined as null returns list of batches names
     * that contain usages without classification.
     *
     * @param filter         {@link UsageFilter} instance
     * @param classification classification
     * @return list of found batch names
     */
    List<String> findBatchNamesWithoutUsagesForClassification(UsageFilter filter, String classification);
}
