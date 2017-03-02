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

    private static final String FY_2018 = "FY2018";

    @Test
    public void privateConstructorTest() {
        TestUtils.validatePrivateConstructor(UsageBatchUtils.class);
    }

    @Test
    public void testGetFiscalYear() {
        assertEquals(StringUtils.EMPTY, UsageBatchUtils.getFiscalYear((LocalDate) null));
        assertEquals(StringUtils.EMPTY, UsageBatchUtils.getFiscalYear((Integer) null));
        assertEquals(FY_2018, UsageBatchUtils.getFiscalYear(2018));
    }

    @Test
    public void testCalculateFiscalYear() {
        assertEquals(2017, UsageBatchUtils.calculateFiscalYear(LocalDate.of(2017, 1, 1)));
    }
}
