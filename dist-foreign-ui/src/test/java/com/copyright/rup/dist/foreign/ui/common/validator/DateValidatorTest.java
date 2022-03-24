package com.copyright.rup.dist.foreign.ui.common.validator;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;

import org.junit.Test;

import java.time.LocalDate;

/**
 * Verifies {@link DateValidator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/23/2022
 *
 * @author Mikita Maistrenka
 */
public class DateValidatorTest {

    private static final LocalDate PREVIOUS_DATE = LocalDate.of(2022, 3, 22);
    private static final LocalDate NEXT_DATE = LocalDate.of(2022, 3, 23);
    private static final String ERROR_MESSAGE = "Field value should be greater or equal to Date From";

    @Test
    public void testDateValidator() {
        ValueContext context = createMock(ValueContext.class);
        LocalDateWidget localDateWidgetFrom = new LocalDateWidget("test");
        LocalDateWidget localDateWidgetTo = new LocalDateWidget("test");
        LocalDateWidget localDateWidgetNull = new LocalDateWidget("test");
        localDateWidgetFrom.setValue(PREVIOUS_DATE);
        localDateWidgetTo.setValue(NEXT_DATE);
        localDateWidgetNull.setValue(null);
        ValidationResult result = new DateValidator(ERROR_MESSAGE, localDateWidgetNull, localDateWidgetTo)
            .apply(null, context);
        assertTrue(result.isError());
        result = new DateValidator(ERROR_MESSAGE, localDateWidgetFrom, localDateWidgetNull)
            .apply(PREVIOUS_DATE, context);
        assertTrue(result.isError());
        result = new DateValidator(ERROR_MESSAGE, localDateWidgetNull, localDateWidgetNull)
            .apply(null, context);
        assertFalse(result.isError());
        result = new DateValidator(ERROR_MESSAGE, localDateWidgetFrom, localDateWidgetFrom)
            .apply(PREVIOUS_DATE, context);
        assertFalse(result.isError());
        result = new DateValidator(ERROR_MESSAGE, localDateWidgetFrom, localDateWidgetTo)
            .apply(PREVIOUS_DATE, context);
        assertFalse(result.isError());
        result = new DateValidator(ERROR_MESSAGE, localDateWidgetTo, localDateWidgetFrom)
            .apply(NEXT_DATE, context);
        assertTrue(result.isError());
    }
}
