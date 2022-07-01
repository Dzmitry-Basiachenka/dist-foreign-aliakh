package com.copyright.rup.dist.foreign.ui.common.utils;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Verifies {@link IDateFormatter}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/18/2021
 *
 * @author Aliaksandr Liakh
 */
public class IDateFormatterTest {

    private final IDateFormatter dateFormatter = new IDateFormatter() {};

    @Test
    public void testToShortFormatLocalDate() {
        assertEquals("06/01/2021", dateFormatter.toShortFormat(LocalDate.of(2021, 6, 1)));
        assertEquals(StringUtils.EMPTY, dateFormatter.toShortFormat((LocalDate) null));
    }

    @Test
    public void testToShortFormatDate() {
        assertEquals("06/01/2021", dateFormatter.toShortFormat(
            Date.from(LocalDate.of(2021, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())));
        assertEquals(StringUtils.EMPTY, dateFormatter.toShortFormat((LocalDate) null));
    }

    @Test
    public void testToLongFormat() {
        assertEquals("06/01/2021 10:20 AM", dateFormatter.toLongFormat(
            Date.from(LocalDateTime.of(2021, 6, 1, 10, 20, 30)
                .atZone(ZoneId.systemDefault()).toInstant())));
        assertEquals(StringUtils.EMPTY, dateFormatter.toLongFormat((Date) null));
    }
}
