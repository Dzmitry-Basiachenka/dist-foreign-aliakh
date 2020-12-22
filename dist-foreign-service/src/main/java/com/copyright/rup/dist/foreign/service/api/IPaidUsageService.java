package com.copyright.rup.dist.foreign.service.api;

import com.copyright.rup.dist.foreign.domain.PaidUsage;

import java.util.List;

/**
 * Represents common interface of service to update paid usages.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/18/20
 *
 * @author Anton Azarenka
 */
public interface IPaidUsageService {

    /**
     * Updates paid information for {@link PaidUsage}s
     * and status to {@link com.copyright.rup.dist.foreign.domain.UsageStatusEnum#PAID}.
     *
     * @param paidUsages list of {@link PaidUsage}s to update
     */
    void updatePaidInfo(List<PaidUsage> paidUsages);
}
