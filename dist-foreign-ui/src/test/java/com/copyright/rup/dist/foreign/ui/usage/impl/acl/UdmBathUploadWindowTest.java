package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;

/**
 * Verifies {@link UdmBathUploadWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/28/2021
 *
 * @author Anton Azarenka
 */
public class UdmBathUploadWindowTest {

    @Test
    public void testConstructor() {
        UdmBathUploadWindow window = new UdmBathUploadWindow();
        assertEquals("Upload UDM Usage Batch", window.getCaption());
        assertEquals(400, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(211, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(4, verticalLayout.getComponentCount());
        verifyUploadComponent(verticalLayout.getComponent(0));
        verifyPeriodYearAndPeriodMonthComponents(verticalLayout.getComponent(1));
        verifyChannelAndUsageOriginComponents(verticalLayout.getComponent(2));
        verifyButtonsLayout(verticalLayout.getComponent(3));
    }

    private void verifyUploadComponent(Component component) {
        assertTrue(component instanceof UploadField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void verifyPeriodYearAndPeriodMonthComponents(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        assertTrue(horizontalLayout.getComponent(0) instanceof TextField);
        assertTrue(horizontalLayout.getComponent(1) instanceof ComboBox);
        TextField textField = (TextField) horizontalLayout.getComponent(0);
        assertEquals("Period Year", textField.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, textField.getWidthUnits());
        ComboBox<String> comboBox = (ComboBox<String>) horizontalLayout.getComponent(1);
        assertEquals("Period Month", comboBox.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
    }

    private void verifyChannelAndUsageOriginComponents(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        assertTrue(horizontalLayout.getComponent(0) instanceof ComboBox);
        assertTrue(horizontalLayout.getComponent(1) instanceof ComboBox);
        ComboBox<UdmChannelEnum> channelComboBox = (ComboBox<UdmChannelEnum>) horizontalLayout.getComponent(0);
        assertEquals("Channel", channelComboBox.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, channelComboBox.getWidthUnits());
        ComboBox<UdmUsageOriginEnum> originComboBox = (ComboBox<UdmUsageOriginEnum>) horizontalLayout.getComponent(1);
        assertEquals("Usage Origin", originComboBox.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, originComboBox.getWidthUnits());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        Button loadButton = verifyButton(layout.getComponent(0), "Upload");
        verifyButton(layout.getComponent(1), "Close");
        assertEquals(1, loadButton.getListeners(ClickEvent.class).size());
    }

    private Button verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }
}
