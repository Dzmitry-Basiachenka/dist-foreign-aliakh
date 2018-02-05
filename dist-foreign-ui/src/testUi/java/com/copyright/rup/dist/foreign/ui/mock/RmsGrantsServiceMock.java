package com.copyright.rup.dist.foreign.ui.mock;

import com.copyright.rup.dist.foreign.service.api.IRmsGrantsService;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * Mock implementation of {@link IRmsGrantsService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/31/18
 *
 * @author Ihar Suvorau
 */
public class RmsGrantsServiceMock implements IRmsGrantsService {

    @Override
    public Map<Long, Long> getAccountNumbersByWrWrkInsts(List<Long> wrWrkInsts) {
        return ImmutableMap.of(122235137L, 1000000001L, 122235138L, 1000008666L, 122235139L, 1000000002L);
    }
}
