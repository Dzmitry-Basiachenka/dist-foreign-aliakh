package com.copyright.rup.dist.foreign.integration.telesales.impl;

import com.copyright.rup.common.caching.impl.AbstractCacheService;
import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.integration.telesales.api.ITelesalesService;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link ITelesalesService} with caching.
 * See {@link AbstractCacheService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * </p>
 * Date: 05/26/2021
 *
 * @author Dzmitry Basiachenka
 */
public class TelesalesCacheService extends AbstractCacheService<Long, CompanyInformation> implements ITelesalesService {

    private final ITelesalesService telesalesService;

    /**
     * Constructor.
     *
     * @param telesalesService instance of {@link ITelesalesService}
     * @param timeToLive cache expiration time, days
     */
    public TelesalesCacheService(ITelesalesService telesalesService, int timeToLive) {
        this.telesalesService = telesalesService;
        super.setExpirationTime(TimeUnit.DAYS.toSeconds(timeToLive));
    }

    @Override
    public String getLicenseeName(Long licenseeAccountNumber) {
        return Objects.requireNonNull(getFromCache(licenseeAccountNumber)).getName();
    }

    @Override
    public CompanyInformation getCompanyInformation(Long companyId) {
        return getFromCache(companyId);
    }

    @Override
    protected CompanyInformation loadData(Long key) {
        return telesalesService.getCompanyInformation(key);
    }
}
