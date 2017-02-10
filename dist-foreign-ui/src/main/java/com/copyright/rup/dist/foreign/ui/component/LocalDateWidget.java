package com.copyright.rup.dist.foreign.ui.component;

import com.copyright.rup.vaadin.widget.PopupDateWidget;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Widget using {@link PopupDateWidget} and {@link LocalDate} as source for date.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/12/2017
 *
 * @author Mikita Hladkikh
 */
// TODO {mhladkikh} analyze moving to rup-vaadin
public class LocalDateWidget extends CustomField<LocalDate> {

    private Property<Date> dateProperty;
    private PopupDateWidget popupDateWidget;

    /**
     * Constructor.
     *
     * @param caption  widget caption
     */
    public LocalDateWidget(String caption) {
        dateProperty = new ObjectProperty<>(null, Date.class);
        this.popupDateWidget = new PopupDateWidget(dateProperty);
        popupDateWidget.setImmediate(true);
        setCaption(caption);
    }

    @Override
    public LocalDate getValue() {
        Date value = popupDateWidget.getValue();
        return null != value ? value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    @Override
    public Component initContent() {
        return popupDateWidget;
    }

    @Override
    public void setInternalValue(LocalDate newValue) {
        super.setInternalValue(newValue);
        dateProperty.setValue(null != newValue
            ? Date.from(newValue.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
            : null);
    }

    @Override
    public Class<LocalDate> getType() {
        return LocalDate.class;
    }

    @Override
    public void addValueChangeListener(ValueChangeListener listener) {
        popupDateWidget.addValueChangeListener(listener);
    }

    @Override
    public boolean isEmpty() {
        return null == getValue();
    }
}
