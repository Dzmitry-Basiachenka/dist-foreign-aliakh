package com.copyright.rup.dist.foreign.service.api.aclci;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.util.List;

/**
 * Interface for ACLCI usage service.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/12/2022
 *
 * @author Aliaksandr Liakh
 */
public interface IAclciUsageService {

    /**
     * Inserts ACLCI usages.
     *
     * @param usageBatch usage batch
     * @param usages     list of {@link Usage}s
     */
    void insertUsages(UsageBatch usageBatch, List<Usage> usages);
}
