package com.copyright.rup.dist.foreign.ui.common.utils;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
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

    private static final LocalDate LOCAL_DATE = LocalDate.of(2021, 6, 1);
    private static final Date DATE = Date.from(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant());
    private static final String SHORT_FORMATTED_DATE = "06/01/2021";

    private final IDateFormatter dateFormatter = new IDateFormatter() {};

    @Test
    public void testToShortFormatLocalDate() {
        assertEquals(SHORT_FORMATTED_DATE, dateFormatter.toShortFormat(LOCAL_DATE));
        assertEquals(StringUtils.EMPTY, dateFormatter.toShortFormat((LocalDate) null));
    }

    @Test
    public void testToShortFormatLocalDateGetter() {
        UdmUsageDto bean = new UdmUsageDto();
        bean.setUsageDate(LOCAL_DATE);
        assertEquals(SHORT_FORMATTED_DATE, dateFormatter.toShortFormat(UdmUsageDto::getUsageDate).apply(bean));
        bean.setUsageDate(null);
        assertEquals(StringUtils.EMPTY, dateFormatter.toShortFormat(UdmUsageDto::getUsageDate).apply(bean));
    }

    @Test
    public void testToShortFormatDate() {
        assertEquals(SHORT_FORMATTED_DATE, dateFormatter.toShortFormat(DATE));
        assertEquals(StringUtils.EMPTY, dateFormatter.toShortFormat((Date) null));
    }

    @Test
    public void testToShortFormatDateGetter() {
        UdmUsageDto bean = new UdmUsageDto();
        bean.setCreateDate(DATE);
        assertEquals(SHORT_FORMATTED_DATE, dateFormatter.toShortFormat(UdmUsageDto::getCreateDate).apply(bean));
        bean.setCreateDate(null);
        assertEquals(StringUtils.EMPTY, dateFormatter.toShortFormat(UdmUsageDto::getCreateDate).apply(bean));
    }

    @Test
    public void testToLongFormat() {
        assertEquals("06/01/2021 10:20 AM", dateFormatter.toLongFormat(
            Date.from(LocalDateTime.of(2021, 6, 1, 10, 20, 30)
                .atZone(ZoneId.systemDefault()).toInstant())));
        assertEquals(StringUtils.EMPTY, dateFormatter.toLongFormat((Date) null));
    }
}
