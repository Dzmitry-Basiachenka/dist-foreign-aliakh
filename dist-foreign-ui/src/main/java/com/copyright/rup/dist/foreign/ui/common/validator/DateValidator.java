package com.copyright.rup.dist.foreign.ui.common.validator;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.AbstractValidator;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class validate date.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/22/2022
 *
 * @author Mikita Maistrenka
 */
public class DateValidator extends AbstractValidator<LocalDate> {

    private static final long serialVersionUID = 8339133126465741929L;

    private final LocalDateWidget dateFromWidget;
    private final LocalDateWidget dateToWidget;

    /**
     * Constructor.
     *
     * @param errorMessage the error message
     * @param dateFromWidget the LocalDate widget
     * @param dateToWidget the LocalDate widget
     */
    public DateValidator(String errorMessage, LocalDateWidget dateFromWidget, LocalDateWidget dateToWidget) {
        super(ForeignUi.getMessage("field.error.greater_or_equal_to", errorMessage));
        this.dateFromWidget = dateFromWidget;
        this.dateToWidget = dateToWidget;
    }

    @Override
    public ValidationResult apply(LocalDate dateFromWidgetValue, ValueContext context) {
        return toResult(dateFromWidgetValue, isValid());
    }

        /**
         * Validate report date filters.
         *
         * @return the result of validation: {@code true} value passed validation otherwise {@code false}
         */
    public boolean isValid() {
        LocalDate dateFromWidgetValue = dateFromWidget.getValue();
        LocalDate dateToWidgetValue = dateToWidget.getValue();
        boolean isPopulated = Objects.nonNull(dateFromWidgetValue) && Objects.nonNull(dateToWidgetValue);
        return isPopulated ? 0 <= dateToWidgetValue.compareTo(dateFromWidgetValue)
            : Objects.isNull(dateFromWidgetValue) && Objects.isNull(dateToWidgetValue);
    }
}
