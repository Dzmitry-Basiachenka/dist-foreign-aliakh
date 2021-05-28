package com.copyright.rup.dist.foreign.service.api.stm;

import com.copyright.rup.dist.foreign.domain.Usage;

import java.util.List;

/**
 * Service to process STM RHs.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/2019
 *
 * @author Stanislau Rudak
 * @author Aliaksandr Liakh
 */
public interface IStmRhService {

    /**
     * Updates {@link Usage}s status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#NON_STM_RH}
     * if RH are non-STM.
     *
     * @param usages        NTS {@link Usage}s to process
     * @param productFamily product family
     */
    void processStmRhs(List<Usage> usages, String productFamily);
}
