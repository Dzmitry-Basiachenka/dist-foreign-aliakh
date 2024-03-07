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
import com.vaadin.flow.dom.Element;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
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
    static Component findComponent(Component parent, String identifier) {
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
        if (parent instanceof Dialog) {
            getDialogFooter((Dialog) parent)
                .ifPresent(footer -> {
                    footer.getChildren().forEach(child -> {
                        if (Objects.isNull(component.get())) {
                            component.set(findComponent(child, identifier));
                        }
                    });
                });
        }
        return component.get();
    }

    /**
     * Gets checkbox.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link Checkbox}
     * @return instance of {@link Checkbox}
     */
    static Button getButton(Component component, String identifier) {
        return (Button) findComponent(component, identifier);
    }

    /**
     * Gets checkbox.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link Checkbox}
     * @return instance of {@link Checkbox}
     */
    static Checkbox getCheckbox(Component component, String identifier) {
        return (Checkbox) findComponent(component, identifier);
    }

    /**
     * Gets text field.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link TextField}
     * @return instance of {@link TextField}
     */
    static TextField getTextField(Component component, String identifier) {
        return (TextField) findComponent(component, identifier);
    }

    /**
     * Gets Integer field.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link IntegerField}
     * @return instance of {@link IntegerField}
     */
    static IntegerField getIntegerField(Component component, String identifier) {
        return (IntegerField) findComponent(component, identifier);
    }

    /**
     * Gets Long field.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link LongField}
     * @return instance of {@link LongField}
     */
    static LongField getLongField(Component component, String identifier) {
        return (LongField) findComponent(component, identifier);
    }

    /**
     * Gets BigDecimal field.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link BigDecimalField}
     * @return instance of {@link BigDecimalField}
     */
    static BigDecimalField getBigDecimalField(Component component, String identifier) {
        return (BigDecimalField) findComponent(component, identifier);
    }

    /**
     * Sets checkbox value.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link Checkbox}
     * @param value      value
     */
    static void setCheckboxValue(Component component, String identifier, Boolean value) {
        getCheckbox(component, identifier).setValue(value);
    }

    /**
     * Sets text field value.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link TextField}
     * @param value      value
     */
    static void setTextFieldValue(Component component, String identifier, String value) {
        getTextField(component, identifier).setValue(value);
    }

    /**
     * Sets Integer field value.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link IntegerField}
     * @param value      value
     */
    static void setIntegerFieldValue(Component component, String identifier, Integer value) {
        getIntegerField(component, identifier).setValue(value);
    }

    /**
     * Sets Long field value.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link LongField}
     * @param value      value
     */
    static void setLongFieldValue(Component component, String identifier, Long value) {
        getLongField(component, identifier).setValue(value);
    }

    /**
     * Sets BigDecimal field value.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link BigDecimalField}
     * @param value      value
     */
    static void setBigDecimalFieldValue(Component component, String identifier, String value) {
        getBigDecimalField(component, identifier).setValue(new BigDecimal(value));
    }

    /**
     * Sets text area value.
     *
     * @param component  instance of {@link Component}
     * @param identifier label or text of the {@link TextArea}
     * @param value      value
     */
    static void setTextAreaValue(Component component, String identifier, String value) {
        ((TextArea) findComponent(component, identifier)).setValue(value);
    }
}
