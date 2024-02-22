package com.copyright.rup.dist.foreign.vui.common.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Implementation of {@link Converter} to convert a YYYY Integer to LocalDate with specified date.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Ihar Suvorau
 */
public class LocalDateConverter implements Converter<Integer, LocalDate> {

    private static final long serialVersionUID = 7361647157502366222L;

    @Override
    public Result<LocalDate> convertToModel(Integer value, ValueContext context) {
        return Result.ok(LocalDate.of(value, 6, 30));
    }

    @Override
    public Integer convertToPresentation(LocalDate value, ValueContext context) {
        return Objects.nonNull(value) ? value.getYear() : null;
    }
}
