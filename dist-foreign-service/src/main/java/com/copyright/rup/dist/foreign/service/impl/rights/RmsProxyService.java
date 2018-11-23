package com.copyright.rup.dist.foreign.service.impl.rights;

import com.copyright.rup.common.caching.impl.AbstractCacheService;
import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link IRmsService}. Uses cache to avoid redundant calls to RMS.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 10/04/2018
 *
 * @author Uladzislau Shalamitski
 */
public class RmsProxyService extends AbstractCacheService<Long, Set<RmsGrant>> implements IRmsService {

    private final IRmsService rmsService;

    /**
     * Constructor.
     *
     * @param rmsService instance of {@link IRmsService}
     * @param timeToLive time to keep cached rollups in minutes
     */
    RmsProxyService(IRmsService rmsService, int timeToLive) {
        this.rmsService = rmsService;
        super.setExpirationTime(TimeUnit.MINUTES.toSeconds(timeToLive));
    }

    @Override
    public Set<RmsGrant> getGrants(List<Long> wrWrkInsts, LocalDate periodEndDate, String... licenseType) {
        return rmsService.getGrants(wrWrkInsts, periodEndDate, licenseType);
    }

    @Override
    public Set<RmsGrant> getGrants(List<Long> wrWrkInsts, LocalDate periodEndDate, List<String> typeOfUses,
                                   String... licenseTypes) {
        return rmsService.getGrants(wrWrkInsts, periodEndDate, typeOfUses, licenseTypes);
    }

    @Override
    public Set<RmsGrant> getAllRmsGrants(List<Long> wrWrkInsts, LocalDate periodEndDate) {
        Set<RmsGrant> result = new HashSet<>();
        wrWrkInsts.forEach(wrWrkInst -> result.addAll(getFromCache(wrWrkInst)));
        return result;
    }

    @Override
    protected Set<RmsGrant> loadData(Long wrWrkInsts) {
        return rmsService.getAllRmsGrants(Collections.singletonList(wrWrkInsts), LocalDate.now());
    }
}
