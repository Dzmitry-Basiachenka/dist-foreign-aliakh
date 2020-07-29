package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import java.time.LocalDate;

/**
 * Implementation of {@link Converter} to convert a YYYY string to LocalDate with specified date.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Ihar Suvorau
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public Result<LocalDate> convertToModel(String value, ValueContext context) {
        return Result.ok(LocalDate.of(Integer.parseInt(value), 6, 30));
    }

    @Override
    public String convertToPresentation(LocalDate value, ValueContext context) {
        return String.valueOf(value.getYear());
    }
}
