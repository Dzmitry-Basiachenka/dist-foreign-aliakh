package com.copyright.rup.dist.foreign.service.api.nts;

import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.util.List;

/**
 * Represents service interface for NTS specific usages business logic.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
public interface INtsUsageService {

    /**
     * Inserts usages.
     *
     * @param usageBatch instance of {@link UsageBatch}
     * @return list of inserted usages' ids
     */
    List<String> insertUsages(UsageBatch usageBatch);
}
