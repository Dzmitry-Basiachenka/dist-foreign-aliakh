package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.integration.pi.impl.PiDeletedWorkIntegrationService;

import java.util.Set;

/**
 * Mock for {@link com.copyright.rup.dist.foreign.integration.pi.impl.PiDeletedWorkIntegrationService}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/09/23
 *
 * @author Mikita Maistrenka
 */
public class PiDeletedWorkIntegrationServiceMock extends PiDeletedWorkIntegrationService {

    @Override
    public boolean isDeletedWork(Long wrWrkInst) {
        Set<Long> deletedWorks = Set.of(229793810L, 460670283L, 473655509L, 363079690L);
        return deletedWorks.contains(wrWrkInst);
    }
}
