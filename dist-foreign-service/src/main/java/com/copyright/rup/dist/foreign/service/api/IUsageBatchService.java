package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents interface of service for usage batch business logic.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
public interface IUsageBatchService {

    /**
     * Gets list of fiscal years from Usage Batches presented in DB and associated with specified Product Family.
     *
     * @param productFamily Product Family
     * @return list of fiscal years
     */
    List<Integer> getFiscalYears(String productFamily);

    /**
     * @return list of {@link UsageBatch}.
     */
    List<UsageBatch> getUsageBatches();

    /**
     * Gets list of {@link UsageBatch}s related to specified Product Family.
     *
     * @param productFamily Product Family
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> getUsageBatches(String productFamily);

    /**
     * Gets {@link UsageBatch} by its id.
     *
     * @param batchId {@link UsageBatch} id
     * @return found {@link UsageBatch} or {@code null} - if none found
     */
    UsageBatch getUsageBatchById(String batchId);

    /**
     * Finds list of {@link UsageBatch}es suitable for including in Additional Funds.
     *
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> getUsageBatchesForAdditionalFunds();

    /**
     * Checks whether Usage Batch with provided name exists in database or not.
     *
     * @param name usage batch name
     * @return {@code true} - if usage batch exists, {@code false} - otherwise
     */
    boolean usageBatchExists(String name);

    /**
     * Sends list of usages on queue for PI matching process.
     *
     * @param usages collection of {@link Usage} to be sent
     */
    void sendForMatching(Collection<Usage> usages);

    /**
     * Sends list of usages on queue for getting Rights process.
     *
     * @param usages    collection of {@link Usage} to be sent
     * @param batchName batch name
     */
    void sendForGettingRights(Collection<Usage> usages, String batchName);

    /**
     * Finds usages by their ids and sends them to queue for getting Rights process.
     *
     * @param usageIds  collection of {@link Usage} ids
     * @param batchName batch name
     */
    void getAndSendForGettingRights(List<String> usageIds, String batchName);

    /**
     * Inserts usage batch, it's usages and RRO.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int insertFasBatch(UsageBatch usageBatch, Collection<Usage> usages);

    /**
     * Inserts NTS batch and assigns eligible for NTS usages to it based on fund pool information.
     *
     * @param usageBatch {@link UsageBatch} to insert
     * @param userName   user name
     * @return list of inserted NTS usages' ids
     */
    List<String> insertNtsBatch(UsageBatch usageBatch, String userName);

    /**
     * Inserts AACL batch and its usages.
     *
     * @param usageBatch {@link UsageBatch} to insert
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int insertAaclBatch(UsageBatch usageBatch, Collection<Usage> usages);

    /**
     * Deletes given {@link UsageBatch} and all it's usage details.
     *
     * @param usageBatch {@link UsageBatch}
     */
    void deleteUsageBatch(UsageBatch usageBatch);

    /**
     * Deletes given AACL {@link UsageBatch} and all it's usage details.
     *
     * @param usageBatch {@link UsageBatch}
     */
    void deleteAaclUsageBatch(UsageBatch usageBatch);

    /**
     * Gets list of batch names containing usages with unclassified works.
     *
     * @param batchIds set of batch ids
     * @return list of found batches names
     */
    List<String> getBatchNamesWithUnclassifiedWorks(Set<String> batchIds);

    /**
     * Gets map of classification to list of batch names without usages related to STM or NON-STM classification
     * when corresponding Fund Pool amout is greater then 0.
     *
     * @param batchIds set of batch ids
     * @return map where key - classification, value - list of batch names with invalid state of usages
     */
    Map<String, List<String>> getClassifcationToBatchNamesWithoutUsagesForStmOrNonStm(Set<String> batchIds);

    /**
     * Gets names of processing batches (with usages in statuses besides ELIGIBLE, UNCLASSIFIED).
     *
     * @param batchesIds set of batches ids
     * @return list of batches names
     */
    List<String> getProcessingBatchesNames(Set<String> batchesIds);

    /**
     * Gets map of batches names to scenario names associated with the given batches.
     *
     * @param batchesIds set of batches ids
     * @return map of batches names to scenario names
     */
    Map<String, String> getBatchesNamesToScenariosNames(Set<String> batchesIds);

    /**
     * Gets list of batch names available for Rights Assignment.
     *
     * @return list of found batch names
     */
    List<String> getBatchNamesAvailableForRightsAssignment();
}
