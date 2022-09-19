package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
 * Verifies {@link UdmCalculateProxyValuesWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/25/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class UdmCalculateProxyValuesWindowTest {

    private IUdmValueController controller;
    private ClickListener continueButtonClickListener;
    private UdmCalculateProxyValuesWindow window;

    @Before
    public void setUp() {
        mockStatic(Windows.class);
        controller = createMock(IUdmValueController.class);
        continueButtonClickListener = createMock(Button.ClickListener.class);
    }

    @Test
    public void testConstructor() {
        expect(controller.getPeriods()).andReturn(Collections.emptyList()).once();
        replay(controller);
        window = new UdmCalculateProxyValuesWindow(controller, continueButtonClickListener);
        verify(controller);
        verifyWindow(window, "Calculate Proxies", 280, 120, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testClickContinueButton() {
        expect(controller.getPeriods()).andReturn(Collections.singletonList(202106)).once();
        expect(controller.calculateProxyValues(202106)).andReturn(2).once();
        Windows.showNotificationWindow("Proxy values calculating completed: 2 record(s) were updated");
        expectLastCall().once();
        continueButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(Windows.class, controller, continueButtonClickListener);
        window = new UdmCalculateProxyValuesWindow(controller, continueButtonClickListener);
        ComboBox<Integer> comboBox = (ComboBox<Integer>) ((VerticalLayout) window.getContent()).getComponent(0);
        comboBox.setSelectedItem(202106);
        Button continueButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1)).getComponent(0);
        continueButton.click();
        verify(Windows.class, controller, continueButtonClickListener);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
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
