package com.copyright.rup.dist.foreign.vui;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.LongField;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class for finding Vaadin components.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/17/2024
 *
 * @author Aliaksandr Liakh
 */
public interface IVaadinComponentFinder {

    /**
     * Finds a child component from the parent component tree by unique identifier.
     *
     * @param parent     the parent component
     * @param identifier label or text of the child component
     * @return instance of found child component of {@code null}
     */
    default Component findComponent(Component parent, String identifier) {
        if (parent instanceof HasLabel && identifier.equals(((HasLabel) parent).getLabel())) {
            return parent;
        }
        if (parent instanceof HasText && identifier.equals(((HasText) parent).getText())) {
            return parent;
        }
        AtomicReference<Component> component = new AtomicReference<>();
        parent.getChildren().forEach(child -> {
            if (Objects.isNull(component.get())) {
                component.set(findComponent(child, identifier));
            }
        });
        return component.get();
    }

    /**
     * Gets checkbox.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link Checkbox}
     * @return instance of {@link Checkbox}
     */
    default Button getButton(Dialog dialog, String identifier) {
        return (Button) findComponent(dialog, identifier);
    }

    /**
     * Gets checkbox.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link Checkbox}
     * @return instance of {@link Checkbox}
     */
    default Checkbox getCheckbox(Dialog dialog, String identifier) {
        return (Checkbox) findComponent(dialog, identifier);
    }

    /**
     * Gets text field.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link TextField}
     * @return instance of {@link TextField}
     */
    default TextField getTextField(Dialog dialog, String identifier) {
        return (TextField) findComponent(dialog, identifier);
    }

    /**
     * Gets Integer field.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link IntegerField}
     * @return instance of {@link IntegerField}
     */
    default IntegerField getIntegerField(Dialog dialog, String identifier) {
        return (IntegerField) findComponent(dialog, identifier);
    }

    /**
     * Gets Long field.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link LongField}
     * @return instance of {@link LongField}
     */
    default LongField getLongField(Dialog dialog, String identifier) {
        return (LongField) findComponent(dialog, identifier);
    }

    /**
     * Gets BigDecimal field.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link BigDecimalField}
     * @return instance of {@link BigDecimalField}
     */
    default BigDecimalField getBigDecimalField(Dialog dialog, String identifier) {
        return (BigDecimalField) findComponent(dialog, identifier);
    }

    /**
     * Sets checkbox value.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link Checkbox}
     * @param value      value
     */
    default void setCheckboxValue(Dialog dialog, String identifier, Boolean value) {
        getCheckbox(dialog, identifier).setValue(value);
    }

    /**
     * Sets text field value.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link TextField}
     * @param value      value
     */
    default void setTextFieldValue(Dialog dialog, String identifier, String value) {
        getTextField(dialog, identifier).setValue(value);
    }

    /**
     * Sets Integer field value.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link IntegerField}
     * @param value      value
     */
    default void setIntegerFieldValue(Dialog dialog, String identifier, Integer value) {
        getIntegerField(dialog, identifier).setValue(value);
    }

    /**
     * Sets Long field value.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link LongField}
     * @param value      value
     */
    default void setLongFieldValue(Dialog dialog, String identifier, Long value) {
        getLongField(dialog, identifier).setValue(value);
    }

    /**
     * Sets BigDecimal field value.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link BigDecimalField}
     * @param value      value
     */
    default void setBigDecimalFieldValue(Dialog dialog, String identifier, String value) {
        getBigDecimalField(dialog, identifier).setValue(new BigDecimal(value));
    }

    /**
     * Sets text area value.
     *
     * @param dialog     instance of {@link Dialog}
     * @param identifier label or text of the {@link TextArea}
     * @param value      value
     */
    default void setTextAreaValue(Dialog dialog, String identifier, String value) {
        ((TextArea) findComponent(dialog, identifier)).setValue(value);
    }
}
