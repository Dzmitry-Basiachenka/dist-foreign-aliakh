package com.copyright.rup.dist.foreign.domain.common.util;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link UsageBatchUtils}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/01/17
 *
 * @author Aliaksei Pchelnikau
 */
@RunWith(Parameterized.class)
public class UsageBatchUtilsGetFiscalYearTest {

    private static final String FY_2017 = "FY2017";
    private static final String FY_2018 = "FY2018";

    private final String expectedFiscalYear;
    private final LocalDate paymentDate;

    /**
     * Constructor.
     *
     * @param expectedFiscalYear expected fiscal year
     * @param paymentDate        payment date
     */
    public UsageBatchUtilsGetFiscalYearTest(String expectedFiscalYear, LocalDate paymentDate) {
        this.expectedFiscalYear = expectedFiscalYear;
        this.paymentDate = paymentDate;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {StringUtils.EMPTY, null},
            {FY_2017, LocalDate.of(2017, 1, 1)},
            {FY_2017, LocalDate.of(2017, 2, 1)},
            {FY_2017, LocalDate.of(2017, 3, 1)},
            {FY_2017, LocalDate.of(2017, 4, 1)},
            {FY_2017, LocalDate.of(2017, 5, 1)},
            {FY_2017, LocalDate.of(2017, 6, 1)},
            {FY_2017, LocalDate.of(2017, 6, 30)},
            {FY_2018, LocalDate.of(2017, 7, 1)},
            {FY_2018, LocalDate.of(2017, 8, 1)},
            {FY_2018, LocalDate.of(2017, 9, 1)},
            {FY_2018, LocalDate.of(2017, 10, 1)},
            {FY_2018, LocalDate.of(2017, 11, 1)},
            {FY_2018, LocalDate.of(2017, 12, 1)},
            {FY_2018, LocalDate.of(2017, 12, 31)},
        });
    }

    @Test
    public void testGetFiscalYear() {
        assertEquals(expectedFiscalYear, UsageBatchUtils.getFiscalYear(paymentDate));
    }
}
