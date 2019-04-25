package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
     * Finds list of {@link UsageBatch}es suitable for including in Pre-Service fee funds.
     *
     * @return list of found {@link UsageBatch}es
     */
    List<UsageBatch> getUsageBatchesForPreServiceFeeFunds();

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
     * Deletes given {@link UsageBatch} and all it's usage details.
     *
     * @param usageBatch {@link UsageBatch}
     */
    void deleteUsageBatch(UsageBatch usageBatch);

    /**
     * Gets list of batch names containing usages with unclassified works.
     *
     * @param usageFilter {@link UsageFilter} instance
     * @return list of found batches names
     */
    List<String> getBatchNamesWithUnclassifiedWorks(UsageFilter usageFilter);

    /**
     * Gets map of classification to list of batch names without usages related to STM or NON-STM classification
     * when corresponding Fund Pool amout is greater then 0.
     *
     * @param filter {@link UsageFilter} instance
     * @return map where key - classification, value - list of batch names with invalid state of usages
     */
    Map<String, List<String>> getBatchNamesWithoutUsagesForStmOrNonStmClassification(UsageFilter filter);
}
