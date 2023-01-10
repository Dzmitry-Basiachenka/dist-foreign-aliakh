package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

/**
 * Verifies {@link UdmPopulateValueBatchWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/21
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class UdmPopulateValueBatchWindowTest {

    private IUdmValueController controller;
    private UdmPopulateValueBatchWindow window;

    @Before
    public void setUp() {
        mockStatic(Windows.class);
        controller = createMock(IUdmValueController.class);
    }

    @Test
    public void testConstructor() {
        expect(controller.getBaselinePeriods()).andReturn(List.of()).once();
        replay(controller);
        window = new UdmPopulateValueBatchWindow(controller);
        verify(controller);
        verifyWindow(window, "Populate Value Batch", 280, 120, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testClickContinueButton() {
        expect(controller.getBaselinePeriods()).andReturn(List.of(202106)).once();
        expect(controller.populatesValueBatch(202106)).andReturn(5).once();
        Windows.showNotificationWindow("Value batch populating completed: 5 record(s) were populated");
        expectLastCall().once();
        replay(Windows.class, controller);
        window = new UdmPopulateValueBatchWindow(controller);
        ComboBox<Integer> comboBox = (ComboBox<Integer>) ((VerticalLayout) window.getContent()).getComponent(0);
        comboBox.setSelectedItem(202106);
        Button continueButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1)).getComponent(0);
        continueButton.click();
        verify(Windows.class, controller);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyComboBox(verticalLayout.getComponent(0), "Period", false);
        verifyButtonsLayout(verticalLayout.getComponent(1), "Continue", "Cancel");
    }
}
