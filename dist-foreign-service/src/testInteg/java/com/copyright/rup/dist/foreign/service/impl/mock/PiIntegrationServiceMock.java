package com.copyright.rup.dist.foreign.service.impl.mock;

import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.impl.PiIntegrationService;

import java.util.Objects;

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
    public Work findWorkByIdnoAndTitle(String idno, String title) {
        if (Objects.equals("978-0-271-01750-1", idno)) {
            return new Work(123059057L, "BIOCHEMISTRY (MOSCOW)");
        } else if (Objects.equals("1906011", idno) && Objects.equals("Solar Cells", title)) {
            return new Work(292891647L, "Solar Cells");
        }
        return null;
    }

    @Override
    public Long findWrWrkInstByTitle(String title) {
        return 123059057L;
    }
}
