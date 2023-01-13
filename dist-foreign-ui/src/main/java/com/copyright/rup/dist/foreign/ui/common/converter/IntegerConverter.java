package com.copyright.rup.dist.foreign.ui.common.converter;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

/**
 * Convertor between {@link String} and {@link Integer}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 01/13/2023
 *
 * @author Aliaksandr Liakh
 */
public class IntegerConverter implements Converter<String, Integer> {

    private final String errorMessage;

    /**
     * Constructor.
     *
     * @param errorMessage error message
     */
    public IntegerConverter(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public Result<Integer> convertToModel(String value, ValueContext context) {
        return StringUtils.isBlank(value) || StringUtils.isNumeric(StringUtils.trim(value))
            ? Result.ok(NumberUtils.createInteger(StringUtils.trimToNull(value)))
            : Result.error(errorMessage);
    }

    @Override
    public String convertToPresentation(Integer value, ValueContext context) {
        return Objects.toString(value, StringUtils.EMPTY);
    }
}
