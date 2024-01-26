package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component;

import com.vaadin.flow.component.textfield.AbstractNumberField;
import com.vaadin.flow.function.SerializableFunction;

import org.apache.commons.lang3.StringUtils;


/**
 * LongField is an extension of Text Field that only accepts Long numbers.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/25/2024
 *
 * @author Ihar Suvorau
 */
public class LongField extends AbstractNumberField<LongField, Long> {

    private static final SerializableFunction<String, Long> PARSER = value -> {
        try {
            return StringUtils.isNotEmpty(value) ? Long.valueOf(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    };

    private static final SerializableFunction<Long, String> FORMATTER = value -> value == null
        ? StringUtils.EMPTY
        : value.toString();

    /**
     * Constructs an empty {@code LongField}.
     */
    public LongField() {
        super(PARSER, FORMATTER, Long.MIN_VALUE, Long.MAX_VALUE, true);
    }

    /**
     * Constructs an empty {@code LongField} with the given label.
     *
     * @param label the text to set as the label
     */
    public LongField(String label) {
        this();
        setLabel(label);
    }
}
