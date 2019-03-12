package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.util.Collection;
import java.util.List;

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
     * @return list of fiscal years presented in DB.
     */
    List<Integer> getFiscalYears();

    /**
     * @return list of {@link UsageBatch}.
     */
    List<UsageBatch> getUsageBatches();

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
     * @param usages collection of {@link Usage} to be sent
     */
    void sendForGettingRights(Collection<Usage> usages);

    /**
     * Finds usages by their ids and sends them to queue for getting Rights process.
     *
     * @param usageIds collection of {@link Usage} ids
     */
    void getAndSendForGettingRights(List<String> usageIds);

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
}
