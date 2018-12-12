package com.copyright.rup.dist.foreign.integration.pi.impl;

import com.copyright.rup.common.caching.impl.AbstractCacheService;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;
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
public class PiIntegrationCacheService extends AbstractCacheService<Pair<String, String>, Work>
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
    public Work findWorkByIdnoAndTitle(String idno, String title) {
        return getFromCache(Pair.of(idno, title));
    }

    @Override
    public Work findWorkByTitle(String title) {
        return getFromCache(Pair.of(null, title));
    }

    @Override
    protected Work loadData(Pair<String, String> key) {
        Work result;
        String standardNumber = key.getLeft();
        String title = key.getRight();
        if (Objects.nonNull(standardNumber)) {
            result = ObjectUtils.defaultIfNull(piIntegrationService.findWorkByIdnoAndTitle(standardNumber, title),
                new Work());
        } else {
            result = piIntegrationService.findWorkByTitle(title);
        }
        return result;
    }
}
