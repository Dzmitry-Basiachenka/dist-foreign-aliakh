package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * Updates initial usages count by batch id.
     *
     * @param initialUsagesCount initial usages count
     * @param batchId            batch id
     * @param userName           user name
     */
    void updateInitialUsagesCount(int initialUsagesCount, String batchId, String userName);

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
     * Gets {@link UsageBatch} by its id.
     *
     * @param batchId id of the {@link UsageBatch}
     * @return {@link UsageBatch} with the given id or {@code null} if none exists
     */
    UsageBatch findById(String batchId);

    /**
     * Finds list of {@link UsageBatch}es by specified Product Family.
     *
     * @param productFamily Product Family
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> findByProductFamily(String productFamily);

    /**
     * Finds list of FAS/FAS2 {@link UsageBatch}es suitable for NTS Fund Pool.
     *
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> findUsageBatchesForNtsFundPool();

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
     * @param batchIds       set of batch ids
     * @param classification classification
     * @return list of found batch names
     */
    List<String> findBatchNamesWithoutUsagesForClassification(Set<String> batchIds, String classification);

    /**
     * Finds names of batches, that have usages in statuses, different from the given.
     *
     * @param batchesIds       set of batches ids
     * @param eligibleStatuses set of statuses, usages in which are considered eligible
     * @return list of batches names
     */
    List<String> findIneligibleForScenarioBatchNames(Set<String> batchesIds, Set<UsageStatusEnum> eligibleStatuses);

    /**
     * Finds map of batches names to scenario names associated with the given batches.
     *
     * @param batchesIds set of batches ids
     * @return map of batches names to scenario names
     */
    Map<String, String> findBatchesNamesToScenariosNames(Set<String> batchesIds);

    /**
     * Finds list of FAS/FAS2 batch names that have usages
     * in {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#RH_NOT_FOUND} status.
     *
     * @return list of found batch names
     */
    List<String> findBatchNamesForRightsAssignment();

    /**
     * @return list of SAL {@link UsageBatch}es that are not attached to a scenario.
     */
    List<UsageBatch> findSalNotAttachedToScenario();

    /**
     * Finds list of {@link SalLicensee}.
     *
     * @return list of {@link SalLicensee}
     */
    List<SalLicensee> findSalLicensees();

    /**
     * Finds list of SAL usage periods.
     *
     * @return list of SAL usage periods
     */
    List<Integer> findSalUsagePeriods();
}
