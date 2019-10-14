package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.Usage;

/**
 * Service to process STM RHs.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Stanislau Rudak
 */
public interface IStmRhService {

    /**
     * Updates {@link Usage} status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NON_STM_RH} if RH is
     * non-STM.
     *
     * @param usage NTS {@link Usage} to process
     */
    void processStmRh(Usage usage);
}
