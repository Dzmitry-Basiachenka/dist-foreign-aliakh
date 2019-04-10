package com.copyright.rup.dist.foreign.integration.pi.impl;

import com.copyright.rup.common.caching.impl.AbstractCacheService;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

import org.apache.commons.lang3.tuple.Triple;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link IPiIntegrationService} with caching.
 * Extends {@link AbstractCacheService} class. The key element in cache is Triple<WrWrkInst, StandardNumber, Title>.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/19/2018
 *
 * @author Ihar Suvorau
 */
public class PiIntegrationCacheService extends AbstractCacheService<Triple<Long, String, String>, Work>
    implements IPiIntegrationService {

    private final IPiIntegrationService piIntegrationService;

    /**
     * Constructor.
     *
     * @param piIntegrationService instance of {@link IPiIntegrationService}
     * @param timeToLive           cache expiration time, minutes
     */
    public PiIntegrationCacheService(IPiIntegrationService piIntegrationService, int timeToLive) {
        this.piIntegrationService = piIntegrationService;
        super.setExpirationTime(TimeUnit.MINUTES.toSeconds(timeToLive));
    }

    @Override
    public Work findWorkByWrWrkInst(Long wrWrkInst) {
        return getFromCache(Triple.of(wrWrkInst, null, null));
    }

    @Override
    public Work findWorkByIdnoAndTitle(String idno, String title) {
        return getFromCache(Triple.of(null, idno, title));
    }

    @Override
    public Work findWorkByTitle(String title) {
        return getFromCache(Triple.of(null, null, title));
    }

    @Override
    protected Work loadData(Triple<Long, String, String> key) {
        Work result;
        Long wrWrkInst = key.getLeft();
        String standardNumber = key.getMiddle();
        String title = key.getRight();
        if (Objects.nonNull(wrWrkInst)) {
            result = piIntegrationService.findWorkByWrWrkInst(wrWrkInst);
        } else if (Objects.nonNull(standardNumber)) {
            result = piIntegrationService.findWorkByIdnoAndTitle(standardNumber, title);
        } else {
            result = piIntegrationService.findWorkByTitle(title);
        }
        return result;
    }
}
