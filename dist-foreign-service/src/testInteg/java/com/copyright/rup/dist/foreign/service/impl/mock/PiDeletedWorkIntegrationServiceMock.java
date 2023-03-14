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

    private static final Set<Long> DELETED_WR_WRK_INSTS = Set.of(229793810L, 460670283L, 473655509L, 363079690L);

    @Override
    public boolean isDeletedWork(Long wrWrkInst) {
        return DELETED_WR_WRK_INSTS.contains(wrWrkInst);
    }
}
