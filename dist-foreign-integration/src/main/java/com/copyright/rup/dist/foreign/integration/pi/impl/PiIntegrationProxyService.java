package com.copyright.rup.dist.foreign.integration.pi.impl;

import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link IPiIntegrationService} with caching.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/19/2018
 *
 * @author Ihar Suvorau
 */
public class PiIntegrationProxyService implements IPiIntegrationService {

    private final IPiIntegrationService piIntegrationService;

    private final Cache<Pair<String, String>, Work> worksCache;

    /**
     * Constructor.
     *
     * @param piIntegrationService an instance of {@link IPiIntegrationService}
     * @param timeToLive           time to keep cached works in minutes
     */
    public PiIntegrationProxyService(IPiIntegrationService piIntegrationService, int timeToLive) {
        this.piIntegrationService = piIntegrationService;
        worksCache = CacheBuilder.newBuilder().expireAfterWrite(timeToLive, TimeUnit.MINUTES).build();
    }

    @Override
    public Work findWorkByIdnoAndTitle(String idno, String title) {
        Work result = worksCache.getIfPresent(Pair.of(idno, title));
        if (Objects.isNull(result)) {
            result = piIntegrationService.findWorkByIdnoAndTitle(idno, title);
            synchronized (worksCache) {
                worksCache.put(Pair.of(idno, title), ObjectUtils.defaultIfNull(result, new Work()));
            }
        }
        return result;
    }

    @Override
    public Map<String, Long> findWrWrkInstsByTitles(Set<String> titles) {
        return piIntegrationService.findWrWrkInstsByTitles(titles);
    }

    @Override
    public Long findWrWrkInstByTitle(String title) {
        Work work = worksCache.getIfPresent(Pair.of(null, title));
        Long result;
        if (Objects.isNull(work)) {
            result = piIntegrationService.findWrWrkInstByTitle(title);
            synchronized (worksCache) {
                worksCache.put(Pair.of(null, title), new Work(result, title));
            }
        } else {
            result = work.getWrWrkInst();
        }
        return result;
    }
}
