package com.copyright.rup.dist.foreign.ui.common.util;

import com.copyright.rup.vaadin.ui.VaadinUtils;

import com.vaadin.ui.Table;

import org.apache.commons.lang3.StringUtils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Column generator for fields with {@link java.time.OffsetDateTime} type.
 * Formats date into the specified date format. MM/dd/yyyy is used as default pattern.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 02/28/18
 *
 * @author Pavel Liakh
 */
// TODO {pliakh} move to rup-vaadin, generialize class in order to create java.time.* types column generator
public class OffsetDateTimeColumnGenerator implements Table.ColumnGenerator {

    private static final String DEFAULT_PATTERN = "MM/dd/yyyy";

    private DateTimeFormatter patternFormatter;

    /**
     * Constructs generator with default pattern.
     */
    public OffsetDateTimeColumnGenerator() {
        this(DEFAULT_PATTERN);
    }

    /**
     * Constructor with predefined pattern.
     *
     * @param pattern pattern to use for formatting dates.
     */
    public OffsetDateTimeColumnGenerator(String pattern) {
        patternFormatter = DateTimeFormatter.ofPattern(Objects.requireNonNull(pattern));
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        OffsetDateTime date = VaadinUtils.getContainerPropertyValue(source, itemId, columnId, OffsetDateTime.class);
        return null != date ? date.format(patternFormatter) : StringUtils.EMPTY;
    }
}
