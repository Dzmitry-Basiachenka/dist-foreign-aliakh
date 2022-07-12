package com.copyright.rup.dist.foreign.ui.common.utils;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Class for conversions between dates and {@link String}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/18/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IDateFormatter {

    /**
     * Formats instance of {@link LocalDate} to {@link String} by pattern "MM/dd/yyyy".
     *
     * @param date instance of {@link LocalDate}
     * @return formatted date string if date is not {@code null}, otherwise empty string
     */
    default String toShortFormat(LocalDate date) {
        return CommonDateUtils.format(date, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    }

    /**
     * Formats instance of {@link Date} to {@link String} by pattern "MM/dd/yyyy".
     *
     * @param date instance of {@link Date}
     * @return formatted date string if date is not {@code null}, otherwise empty string
     */
    default String toShortFormat(Date date) {
        return Objects.nonNull(date)
            ? FastDateFormat.getInstance(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT).format(date)
            : StringUtils.EMPTY;
    }

    /**
     * Formats instance of {@link Date} to {@link String} by pattern "MM/dd/yyyy h:mm a".
     *
     * @param date instance of {@link Date}
     * @return formatted date string if date is not {@code null}, otherwise empty string
     */
    default String toLongFormat(Date date) {
        return Objects.nonNull(date)
            ? new SimpleDateFormat(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG, Locale.getDefault()).format(date)
            : StringUtils.EMPTY;
    }
}
