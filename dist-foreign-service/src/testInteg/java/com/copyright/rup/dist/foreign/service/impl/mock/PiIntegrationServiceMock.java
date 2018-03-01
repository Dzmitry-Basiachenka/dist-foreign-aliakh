package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.integration.pi.impl.PiIntegrationService;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;

/**
 * Mock for {@link PiIntegrationService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 2/27/18
 *
 * @author Aliaksandr Radkevich
 */
public class PiIntegrationServiceMock extends PiIntegrationService {

    @Override
    public Map<String, Long> findWrWrkInstsByIdnos(Set<String> idnos) {
        return ImmutableMap.of("9780271017501", 123059057L);
    }

    @Override
    public Map<String, Long> findWrWrkInstsByTitles(Set<String> titles) {
        return ImmutableMap.of("Forbidden rites", 123059057L);
    }
}
