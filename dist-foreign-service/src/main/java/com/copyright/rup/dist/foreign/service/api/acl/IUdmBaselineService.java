package com.copyright.rup.dist.foreign.service.api.acl;

/**
 * Represents interface of service for UDM baseline business logic.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/03/21
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineService {
    /**
     * Removes usages from baseline.
     *
     * @param period baseline period
     * @return count of removed from baselines
     */
    int removeFromBaseline(Integer period);
}
