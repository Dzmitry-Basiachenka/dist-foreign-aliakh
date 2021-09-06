package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class UdmUsageBaselinePublishWindowTest {

    private IUdmUsageController controller;
    private UdmUsageBaselinePublishWindow window;

    @Before
    public void setUp() {
        controller = createMock(IUdmUsageController.class);
        mockStatic(Windows.class);
    }

    @Test
    public void testConstructor() {
        expect(controller.getPeriods()).andReturn(Collections.emptyList()).once();
        replay(controller);
        window = new UdmUsageBaselinePublishWindow(controller);
        verify(controller);
        assertEquals("Publish to Baseline", window.getCaption());
        assertEquals(280, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(120, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testClickPublishButton() {
        Pair<Integer, Integer> expectedPair = Pair.of(10, 5);
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202106)).once();
        expect(controller.publishUdmUsagesToBaseline(202106)).andReturn(expectedPair).once();
        Windows.showNotificationWindow(
            "Publish completed: 10 record(s) were published, 5 record(s) were deleted from baseline");
        expectLastCall().once();
        replay(controller);
        window = new UdmUsageBaselinePublishWindow(controller);
        ComboBox<Integer> comboBox = (ComboBox<Integer>) ((VerticalLayout) window.getContent()).getComponent(0);
        comboBox.setSelectedItem(202106);
        Button publishButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1)).getComponent(0);
        publishButton.click();
        verify(controller);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyPeriodComponent(verticalLayout.getComponent(0));
        verifyButtonsLayout(verticalLayout.getComponent(1));
    }

    private void verifyPeriodComponent(Component component) {
        ComboBox<Integer> comboBox = (ComboBox<Integer>) component;
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
