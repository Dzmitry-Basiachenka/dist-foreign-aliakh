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

    private static final String TITLE = "At-risk youth : a comprehensive response : for counselors teachers " +
        "psychologists and human service professionals";

    @Override
    public Work findWorkByIdnoAndTitle(String idno, String title) {
        if (Objects.equals("978-0-271-01750-1", idno)) {
            return new Work(123059057L, "BIOCHEMISTRY (MOSCOW)", "VALISSN");
        } else if (Objects.equals("1906011", idno) && Objects.equals("Solar Cells", title)) {
            return new Work(292891647L, "Solar Cells", "VALISSN");
        } else if (Objects.equals("12345XX-12978", idno) && Objects.equals(TITLE, title)) {
            return new Work(100012905L, TITLE, "VALISBN13");
        }
        return null;
    }

    @Override
    public Work findWorkByTitle(String title) {
        return new Work(123059057L, title, "VALISBN10");
    }

    @Override
    public Work findWorkByWrWrkInst(Long wrWrkInst) {
        return Objects.equals(123456789L, wrWrkInst)
            ? new Work(123456789L, "BIOCHEMISTRY (MOSCOW)", "VALISBN10")
            : new Work();
    }
}
