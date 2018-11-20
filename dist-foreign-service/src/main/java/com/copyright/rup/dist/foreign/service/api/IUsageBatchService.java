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
     * Inserts usage batch, it's usages and RRO.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @param usages     list of {@link Usage}s
     * @return count of inserted usages
     */
    int insertUsageBatch(UsageBatch usageBatch, Collection<Usage> usages);

    /**
     * Deletes given {@link UsageBatch} and all it's usage details.
     *
     * @param usageBatch {@link UsageBatch}
     */
    void deleteUsageBatch(UsageBatch usageBatch);
}
