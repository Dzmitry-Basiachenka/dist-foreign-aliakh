package com.copyright.rup.dist.foreign.ui.common.utils;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Verifies {@link DateUtils}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/18/2021
 *
 * @author Aliaksandr Liakh
 */
public class DateUtilsTest {

    @Test
    public void testConstructor() {
        TestUtils.validatePrivateConstructor(DateUtils.class);
    }

    @Test
    public void testFormatLocalDate() {
        assertEquals("06/01/2021", DateUtils.format(LocalDate.of(2021, 6, 1)));
        assertEquals(StringUtils.EMPTY, DateUtils.format((LocalDate) null));
    }

    @Test
    public void testFormatDate() {
        assertEquals("06/01/2021", DateUtils.format(java.sql.Date.valueOf(LocalDate.of(2021, 6, 1))));
        assertEquals(StringUtils.EMPTY, DateUtils.format((LocalDate) null));
    }
}
