package com.copyright.rup.dist.foreign.integration.rms.impl;

import com.copyright.rup.common.caching.impl.AbstractCacheService;
import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IRmsRightsService}. Uses cache to avoid redundant calls to RMS.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 10/04/2018
 *
 * @author Uladzislau Shalamitski
 */
public class RmsRightsCacheService extends AbstractCacheService<Long, Set<RmsGrant>>
    implements IRmsRightsService {

    private final IRmsRightsService rmsRightsService;

    /**
     * Constructor.
     *
     * @param rmsRightsService instance of {@link IRmsRightsService}
     * @param timeToLive       time to keep cached grants in minutes
     */
    RmsRightsCacheService(IRmsRightsService rmsRightsService, int timeToLive) {
        this.rmsRightsService = rmsRightsService;
        super.setExpirationTime(TimeUnit.MINUTES.toSeconds(timeToLive));
    }

    @Override
    public Set<RmsGrant> getGrants(List<Long> wrWrkInsts, LocalDate periodEndDate, List<String> typeOfUses,
                                   String... licenseTypes) {
        return wrWrkInsts.stream().flatMap(wrWrkInst -> getFromCache(wrWrkInst).stream()).collect(Collectors.toSet());
    }

    @Override
    //TODO {isuvorau} adjust logic to have ability to use cache for AACL
    protected Set<RmsGrant> loadData(Long wrWrkInsts) {
        return rmsRightsService.getGrants(Collections.singletonList(wrWrkInsts), LocalDate.now(),
            Collections.emptyList());
    }
}
