package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.UsageFilter;

import java.io.OutputStream;

/**
 * Service for researching works.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/23/2018
 *
 * @author Nikita Levyankov
 */
public interface IResearchService {

    /**
     * Writes usages found by filter into CSV output stream and
     * change status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#WORK_RESEARCH}.
     *
     * @param filter       instance of {@link UsageFilter}
     * @param outputStream instance of {@link OutputStream}
     */
    void sendForResearch(UsageFilter filter, OutputStream outputStream);
}
