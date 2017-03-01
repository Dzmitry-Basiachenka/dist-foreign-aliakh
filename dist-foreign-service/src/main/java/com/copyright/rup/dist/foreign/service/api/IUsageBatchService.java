package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.util.List;

/**
 * Represents interface of service for usage batch business logic.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
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
     * Inserts usage batch, it's usages and RRO.
     *
     * @param usageBatch {@link UsageBatch} instance
     * @param usages     list of {@link Usage}s
     * @param userName   user name
     * @return count of inserted usages
     */
    int insertUsages(UsageBatch usageBatch, List<Usage> usages, String userName);

    /**
     * Deletes {@link UsageBatch} with given id and all it's usage details.
     *
     * @param batchId  {@link UsageBatch} id
     * @param userName user name
     */
    void deleteUsageBatch(String batchId, String userName);
}
