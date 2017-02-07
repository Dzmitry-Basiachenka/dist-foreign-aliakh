package com.copyright.rup.dist.foreign.service.api;

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
}
