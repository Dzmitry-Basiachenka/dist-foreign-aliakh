package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

/**
 * Verifies {@link UdmFiltersWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Ihar Suvorau
 */
public class UdmFiltersWindowTest {

    @Test
    public void testConstructor() {
        UdmFiltersWindow window = new UdmFiltersWindow();
        assertEquals("UDM additional filters", window.getCaption());
        assertEquals(550, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(500, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(18, verticalLayout.getComponentCount());
        verifyLabelFieldComponent(verticalLayout.getComponent(0), "Assignee");
        verifyLabelFieldComponent(verticalLayout.getComponent(1), "Reported Pub Type");
        verifyLabelFieldComponent(verticalLayout.getComponent(2), "Publication Format");
        verifyLabelFieldComponent(verticalLayout.getComponent(3), "Det LC Name");
        verifyLabelFieldComponent(verticalLayout.getComponent(4), "Channel");
        verifyDateFieldComponent(verticalLayout.getComponent(5), "Usage Date From", "Usage Date To");
        verifyDateFieldComponent(verticalLayout.getComponent(6), "Survey Start From", "Survey Start To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(7), "Annual Multiplier From",
            "Annual Multiplier To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(8), "Annualized Copies From",
            "Annualized Copies To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(9), "Statistical Multiplier From",
            "Statistical Multiplier To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(10), "Quantity From", "Quantity To");
        verifyComboBoxComponent(verticalLayout.getComponent(11), "Type Of Use");
        verifyTextFieldComponent(verticalLayout.getComponent(12), "Survey Country");
        verifyTextFieldComponent(verticalLayout.getComponent(13), "Language");
        verifyTextFieldComponent(verticalLayout.getComponent(14), "Company Name");
        verifyTextFieldComponent(verticalLayout.getComponent(15), "Company Id");
        verifyTextFieldComponent(verticalLayout.getComponent(16), "Wr Wrk Inst");
        verifyButtonsLayout(verticalLayout.getComponent(17));
    }

    private void verifyTextFieldComponent(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(50, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
    }

    private void verifyComboBoxComponent(Component component, String caption) {
        assertTrue(component instanceof ComboBox);
        assertEquals(50, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
    }

    private void verifyLabelFieldComponent(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof Label);
        assertEquals("(0)", ((Label) layout.getComponent(0)).getValue());
        assertTrue(layout.getComponent(1) instanceof Button);
        assertEquals(caption, layout.getComponent(1).getCaption());
    }

    private void verifyDateFieldComponent(Component component, String captionFrom, String captionTo) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof LocalDateWidget);
        assertEquals(captionFrom, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof LocalDateWidget);
        assertEquals(captionTo, layout.getComponent(1).getCaption());
    }

    private void verifyFieldWithOperatorComponent(Component component, String captionFrom, String captionTo) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof TextField);
        assertEquals(captionFrom, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof TextField);
        assertEquals(captionTo, layout.getComponent(1).getCaption());
        assertTrue(layout.getComponent(2) instanceof ComboBox);
        assertEquals("Operator", layout.getComponent(2).getCaption());

    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Close");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }
}
