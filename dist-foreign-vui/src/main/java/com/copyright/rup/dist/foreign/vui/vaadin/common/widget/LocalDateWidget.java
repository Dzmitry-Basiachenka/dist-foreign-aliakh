package com.copyright.rup.dist.foreign.vui.vaadin.common.widget;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A UI component providing ability to work with {@link LocalDate}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 05/03/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 * @author Anton Azarenka
 * @see DateWidget
 */
public class LocalDateWidget extends CustomField<LocalDate> {

    private final DateWidget popupDateWidget = new DateWidget();

    /**
     * Constructor.
     *
     * @param caption widget caption
     */
    public LocalDateWidget(String caption) {
        popupDateWidget.setLabel(caption);
        add(popupDateWidget);
        setWidthFull();
    }

    @Override
    public LocalDate getValue() {
        return popupDateWidget.getValue();
    }

    @Override
    public boolean isEmpty() {
        return Objects.isNull(getValue());
    }

    @Override
    public String getLabel() {
        return popupDateWidget.getLabel();
    }

    @Override
    protected LocalDate generateModelValue() {
        return popupDateWidget.getValue();
    }

    @Override
    protected void setPresentationValue(LocalDate newPresentationValue) {
        popupDateWidget.setValue(newPresentationValue);
    }

    @Override
    public Registration addValueChangeListener(
        ValueChangeListener<? super ComponentValueChangeEvent<CustomField<LocalDate>, LocalDate>> listener) {
        return popupDateWidget.addValueChangeListener(
            (ValueChangeListener<? super ComponentValueChangeEvent<DatePicker, LocalDate>>) listener);
    }

    /**
     * @return placeholder.
     */
    public String getPlaceholder() {
        return popupDateWidget.getPlaceholder();
    }

    /**
     * Sets format for date.
     *
     * @param format format
     */
    public void setDateFormat(String format) {
        popupDateWidget.setDateFormat(format);
    }
}
