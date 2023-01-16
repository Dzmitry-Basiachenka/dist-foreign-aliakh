package com.copyright.rup.dist.foreign.ui.common.converter;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

/**
 * Convertor between {@link String} and {@link Long}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 01/13/2023
 *
 * @author Aliaksandr Liakh
 */
public class LongConverter implements Converter<String, Long> {

    private final String errorMessage;

    /**
     * Constructor.
     *
     * @param errorMessage error message
     */
    public LongConverter(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public Result<Long> convertToModel(String value, ValueContext context) {
        return StringUtils.isBlank(value) || StringUtils.isNumeric(StringUtils.trim(value))
            ? Result.ok(NumberUtils.createLong(StringUtils.trimToNull(value)))
            : Result.error(errorMessage);
    }

    @Override
    public String convertToPresentation(Long value, ValueContext context) {
        return Objects.toString(value, StringUtils.EMPTY);
    }
}
