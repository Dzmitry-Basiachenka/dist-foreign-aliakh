package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Iterator;

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
        window.setUdmUsageFilter(new UdmUsageFilter());
        assertEquals("UDM additional filters", window.getCaption());
        assertEquals(550, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(560, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(13, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "Assignees", "Detail Licensee Classes");
        verifyItemsFilterLayout(verticalLayout.getComponent(1), "Reported Pub Types", "Type of Uses");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "Publication Formats");
        verifyDateFieldComponent(verticalLayout.getComponent(3), "Usage Date From", "Usage Date To");
        verifyDateFieldComponent(verticalLayout.getComponent(4), "Survey Start Date From", "Survey Start Date To");
        verifyChannelWrWkrInstLayout(verticalLayout.getComponent(5));
        verifyTextFieldLayout(verticalLayout.getComponent(6), "Company Id", "Company Name");
        verifyTextFieldLayout(verticalLayout.getComponent(7), "Survey Country", "Language");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(8), "Annual Multiplier From",
            "Annual Multiplier To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(9), "Annualized Copies From",
            "Annualized Copies To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(10), "Statistical Multiplier From",
            "Statistical Multiplier To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(11), "Quantity From", "Quantity To");
        verifyButtonsLayout(verticalLayout.getComponent(12));
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
    }

    private void verifyItemsFilterWidget(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals(caption, button.getCaption());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
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

    @SuppressWarnings("unchecked")
    private void verifyChannelWrWkrInstLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        ComboBox<UdmChannelEnum> channelComboBox = (ComboBox<UdmChannelEnum>) layout.getComponent(0);
        assertEquals(100, channelComboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, channelComboBox.getWidthUnits());
        assertEquals(channelComboBox.getCaption(), "Channel");
        verifyTextFieldComponent(layout.getComponent(1), "Wr Wrk Inst");
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

    private void verifyTextFieldLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyTextFieldComponent(layout.getComponent(0), firstCaption);
        verifyTextFieldComponent(layout.getComponent(1), secondCaption);
    }

    private void verifyTextFieldComponent(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Clear");
        verifyButton(layout.getComponent(2), "Close");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }
}
