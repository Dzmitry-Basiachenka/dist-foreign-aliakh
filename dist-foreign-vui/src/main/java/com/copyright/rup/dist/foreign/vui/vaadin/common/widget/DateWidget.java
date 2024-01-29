package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

/**
 * Widget represents of {@link DatePicker} with clears date field button.
 * By default inner date field displays on UI date in format mm/dd/yyyy,
 * but user is able to change date format using {@link #setDateFormat(String)} method.
 * Also by default text field, which is displaying the value, disabled and user should use popup date picker for
 * inputting date.
 * <p/>
 * Copyright (C) 2015 copyright.com
 * <p/>
 * Date: 03/23/15
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 * @author Anton Azarenka
 */
public class DateWidget extends HorizontalLayout {

    private static final long serialVersionUID = 1482958776010451795L;

    private final DatePicker datePicker;

    /**
     * Default constructor.
     */
    public DateWidget() {
        this(null);
    }

    /**
     * Constructor.
     *
     * @param label label
     */
    public DateWidget(String label) {
        datePicker = new DatePicker(label);
        init();
        VaadinUtils.addComponentStyle(this, label);
    }

    /**
     * Add value change listener.
     *
     * @param listener instance of {@link HasValue.ValueChangeListener}
     * @return instance of {@link Registration}
     */
    public Registration addValueChangeListener(
        HasValue.ValueChangeListener<? super ComponentValueChangeEvent<DatePicker, LocalDate>> listener) {
        return datePicker.addValueChangeListener(listener);
    }

    /**
     * @return label.
     */
    public String getLabel() {
        return datePicker.getLabel();
    }

    /**
     * Sets label.
     *
     * @param label label
     */
    public void setLabel(String label) {
        datePicker.setLabel(label);
    }

    /**
     * @return date format string from the inner date field.
     */
    public String getDateFormat() {
        return datePicker.getI18n().getDateFormats().stream()
            .findFirst()
            .orElse(StringUtils.EMPTY);
    }

    /**
     * Sets specified date format for the date field.
     *
     * @param dateFormat format for the date displaying in text field
     */
    public void setDateFormat(String dateFormat) {
        DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
        i18n.setDateFormat(dateFormat);
        datePicker.setPlaceholder(dateFormat);
    }

    /**
     * @return the value from the date field.
     */
    public LocalDate getValue() {
        return datePicker.getValue();
    }

    /**
     * Sets date to the widget.
     *
     * @param value instance of {@link LocalDate}
     */
    public void setValue(LocalDate value) {
        datePicker.setValue(value);
    }

    /**
     * @return placeholder from date picker.
     */
    public String getPlaceholder() {
        return datePicker.getPlaceholder();
    }

    private void init() {
        setDateFormat("mm/dd/yyyy");
        add(datePicker);
        datePicker.setClearButtonVisible(true);
        datePicker.setWidthFull();
        setSpacing(false);
        setMargin(false);
        setWidthFull();
        setClassName("popup-date-widget-layout");
    }
}
