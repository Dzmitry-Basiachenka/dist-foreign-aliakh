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

    private static final String VALISSN = "VALISSN";
    private static final String TITLE_1 = "At-risk youth : a comprehensive response : for counselors teachers " +
        "psychologists and human service professionals";
    private static final String TITLE_2 =
        "Journal of human lactation : official journal of International Lactation Consultant Association (1985- )";

    @Override
    public Work findWorkByIdnoAndTitle(String idno, String title) {
        Work work = new Work();
        if (Objects.equals("978-0-7695-2365-2", idno)) {
            work = new Work(876543210L, "Medical Journal", idno, VALISSN);
        } else if (Objects.equals("978-0-7695-2365-1", idno)) {
            work = new Work(987654321L, "Technical Journal", idno, VALISSN);
        } else if (Objects.equals("978-0-271-01750-1", idno)) {
            work = new Work(123059057L, "BIOCHEMISTRY (MOSCOW)", idno, VALISSN);
        } else if (Objects.equals("1906011", idno) && Objects.equals("Solar Cells", title)) {
            work = new Work(292891647L, "Solar Cells", idno, VALISSN);
        } else if (Objects.equals("12345XX-12978", idno) && Objects.equals(TITLE_1, title)) {
            work = new Work(100012905L, TITLE_1, idno, "VALISBN13");
        }
        return work;
    }

    @Override
    public Work findWorkByTitle(String title) {
        return new Work(123059057L, title, null, "VALISBN10");
    }

    @Override
    public Work findWorkByWrWrkInst(Long wrWrkInst) {
        Work work = new Work();
        if (Objects.equals(123456789L, wrWrkInst)) {
            work = new Work(123456789L, "BIOCHEMISTRY (MOSCOW)", null, null);
        } else if (Objects.equals(100009840L, wrWrkInst)) {
            work = new Work(100009840L, TITLE_2, "12345XX-79068", VALISSN);
        } else if (Objects.equals(100010768L, wrWrkInst)) {
            work =
                new Work(100010768L, "Reclaiming youth at risk : our hope for the future", "12345XX-123117", VALISSN);
        } else if (Objects.equals(100011725L, wrWrkInst)) {
            work = new Work(100011725L, TITLE_2, "12345XX-79069", "VALISBN10");
        } else if (Objects.equals(100011821L, wrWrkInst)) {
            work = new Work(100011821L, "True directions : living your sacred instructions", "12345XX-190048", VALISSN);
        } else if (Objects.equals(987654321L, wrWrkInst)) {
            work = new Work(987654321L, "Nouveau guide en Suisse", "12345XX-79031", VALISSN);
        } else if (Objects.equals(876543210L, wrWrkInst)) {
            work = new Work(876543210L, "Paris tonique", "12345XX-79069", VALISSN);
        } else if (Objects.equals(658824345L, wrWrkInst)) {
            work = new Work(658824345L, "Medical Journal", "1008902112377654XX", VALISSN);
        } else if (Objects.equals(854030732L, wrWrkInst)) {
            work = new Work(854030732L, "Technical Journal", "2998622115929154XX", VALISSN);
        }
        return work;
    }
}
