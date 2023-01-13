package com.copyright.rup.dist.foreign.ui.common.converter;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Class for conversion between the String and the BigDecimal type.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/18/2020
 *
 * @author Uladzislau_Shalamitski
 */
public class BigDecimalConverter implements Converter<String, BigDecimal> {

    @Override
    public Result<BigDecimal> convertToModel(String value, ValueContext context) {
        return Result.ok(new BigDecimal(StringUtils.trimToEmpty(value)).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Override
    public String convertToPresentation(BigDecimal value, ValueContext context) {
        return String.valueOf(value);
    }
}
