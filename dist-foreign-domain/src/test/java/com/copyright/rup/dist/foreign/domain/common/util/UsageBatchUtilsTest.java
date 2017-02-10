package com.copyright.rup.dist.foreign.domain.common.util;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Verifies {@link UsageBatchUtils}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/21/2017
 *
 * @author Mikita Hladkikh
 */
public class UsageBatchUtilsTest {

    private static final String FY_2017 = "FY2017";
    private static final String FY_2018 = "FY2018";

    @Test
    public void privateConstructorTest() {
        TestUtils.validatePrivateConstructor(UsageBatchUtils.class);
    }

    @Test
    public void testGetFiscalYear() {
        assertEquals(StringUtils.EMPTY, UsageBatchUtils.getFiscalYear((LocalDate) null));
        assertEquals(StringUtils.EMPTY, UsageBatchUtils.getFiscalYear((Integer) null));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 1, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 2, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 3, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 4, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 5, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 6, 1)));
        assertEquals(FY_2017, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 6, 30)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 7, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 8, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 9, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 10, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 11, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 12, 1)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(LocalDate.of(2017, 12, 31)));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(2018));
    }
}
