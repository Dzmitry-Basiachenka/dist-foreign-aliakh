package com.copyright.rup.dist.foreign.repository.api;

import com.copyright.rup.dist.foreign.domain.UsageBatch;

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
     * @return list of all fiscal years presented in DB.
     */
    List<Integer> findFiscalYears();

    /**
     * @return list of all {@link UsageBatch} presented in DB.
     */
    List<UsageBatch> findAll();

    /**
     * Gets usage batches count with specified name.
     *
     * @param name batch name
     * @return count of found batches
     */
    int getCountByName(String name);

    /**
     * Deletes {@link UsageBatch} with given id from database.
     *
     * @param batchId id of the {@link UsageBatch} to be deleted
     */
    void deleteUsageBatch(String batchId);
}
