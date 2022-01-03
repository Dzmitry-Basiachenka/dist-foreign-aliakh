package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;

/**
 * Verifies {@link UdmPublishToBaselineWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/23/21
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class UdmPublishToBaselineWindowTest {

    private static final String PUBLISHING_NOT_ALLOWED = "Unable to publish values to baseline because some records " +
        "are missing the required attributes (Pub Type or Content Unit Price) or are in the following statuses: " +
        "NEW, RSCHD_IN_THE_PREV_PERIOD";

    private IUdmValueController controller;
    private UdmPublishToBaselineWindow window;
    private ClickListener publishButtonClickListener;

    @Before
    public void setUp() {
        mockStatic(Windows.class);
        controller = createMock(IUdmValueController.class);
        publishButtonClickListener = createMock(ClickListener.class);
    }

    @Test
    public void testConstructor() {
        expect(controller.getPeriods()).andReturn(Collections.emptyList()).once();
        replay(controller);
        window = new UdmPublishToBaselineWindow(controller, publishButtonClickListener);
        assertEquals("Publish to Baseline", window.getCaption());
        assertEquals(280, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(120, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
        verify(controller);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testClickContinueButton() {
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202106)).once();
        expect(controller.isAllowedForPublishing(202106)).andReturn(true).once();
        expect(controller.publishToBaseline(202106)).andReturn(5).once();
        Windows.showNotificationWindow("Value batch publishing completed: 5 record(s) were published");
        expectLastCall().once();
        publishButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(Windows.class, controller, publishButtonClickListener);
        window = new UdmPublishToBaselineWindow(controller, publishButtonClickListener);
        ComboBox<Integer> comboBox = (ComboBox<Integer>) ((VerticalLayout) window.getContent()).getComponent(0);
        comboBox.setSelectedItem(202106);
        Button continueButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1)).getComponent(0);
        continueButton.click();
        verify(Windows.class, controller, publishButtonClickListener);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testClickContinueButtonWhenPublishingNotAllowed() {
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202106)).once();
        expect(controller.isAllowedForPublishing(202106)).andReturn(false).once();
        Windows.showNotificationWindow(PUBLISHING_NOT_ALLOWED);
        expectLastCall().once();
        replay(controller);
        window = new UdmPublishToBaselineWindow(controller, publishButtonClickListener);
        ComboBox<Integer> comboBox = (ComboBox<Integer>) ((VerticalLayout) window.getContent()).getComponent(0);
        comboBox.setSelectedItem(202106);
        Button continueButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1)).getComponent(0);
        continueButton.click();
        verify(controller);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyPeriodComponent(verticalLayout.getComponent(0));
        verifyButtonsLayout(verticalLayout.getComponent(1), "Continue", "Cancel");
    }

    @SuppressWarnings("unchecked")
    private void verifyPeriodComponent(Component component) {
        ComboBox<Integer> comboBox = (ComboBox<Integer>) component;
        assertEquals("Period", comboBox.getCaption());
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
    }
}
