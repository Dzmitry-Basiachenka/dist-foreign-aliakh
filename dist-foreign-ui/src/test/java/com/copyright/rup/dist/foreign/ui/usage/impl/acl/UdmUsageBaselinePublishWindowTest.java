package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * Verifies {@link UdmUsageBaselinePublishWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/21
 *
 * @author Anton Azarenka
 */
public class UdmUsageBaselinePublishWindowTest {

    private IUdmUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IUdmUsageController.class);
    }

    @Test
    public void testConstructor() {
        expect(controller.getPeriods()).andReturn(Collections.emptyList()).once();
        replay(controller);
        UdmUsageBaselinePublishWindow window = new UdmUsageBaselinePublishWindow(controller);
        verify(controller);
        assertEquals("Publish to Baseline", window.getCaption());
        assertEquals(280, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(120, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyPeriodComponent(verticalLayout.getComponent(0));
        verifyButtonsLayout(verticalLayout.getComponent(1));
    }

    private void verifyPeriodComponent(Component component) {
        ComboBox<String> comboBox = (ComboBox<String>) component;
        assertEquals("Period", comboBox.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Publish");
        verifyButton(layout.getComponent(1), "Close");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }
}
