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
 */
public interface IUsageBatchRepository {

    /**
     * Inserts {@link UsageBatch}.
     *
     * @param usageBatch instance of {@link UsageBatch}
     * @return count of inserted records
     */
    int insert(UsageBatch usageBatch);

    /**
     * @return list of all fiscal years presented in DB.
     */
    List<Integer> findFiscalYears();
}
