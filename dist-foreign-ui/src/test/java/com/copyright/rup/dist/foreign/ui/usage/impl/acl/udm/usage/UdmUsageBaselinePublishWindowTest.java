package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
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

import java.util.List;

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
    private ClickListener publishButtonClickListener;

    @Before
    public void setUp() {
        controller = createMock(IUdmUsageController.class);
        publishButtonClickListener = createMock(ClickListener.class);
        mockStatic(Windows.class);
    }

    @Test
    public void testConstructor() {
        expect(controller.getPeriods()).andReturn(List.of()).once();
        replay(controller);
        window = new UdmUsageBaselinePublishWindow(controller, publishButtonClickListener);
        verify(controller);
        verifyWindow(window, "Publish to Baseline", 280, 120, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testClickPublishButton() {
        expect(controller.getPeriods()).andReturn(List.of(202106)).once();
        expect(controller.publishUdmUsagesToBaseline(202106)).andReturn(10).once();
        Windows.showNotificationWindow(eq("Publish completed: 10 record(s) were published to baseline"));
        expectLastCall().once();
        publishButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, Windows.class, publishButtonClickListener);
        window = new UdmUsageBaselinePublishWindow(controller, publishButtonClickListener);
        ComboBox<Integer> comboBox = (ComboBox<Integer>) ((VerticalLayout) window.getContent()).getComponent(0);
        comboBox.setSelectedItem(202106);
        Button publishButton =
            (Button) ((HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1)).getComponent(0);
        publishButton.click();
        verify(controller, Windows.class, publishButtonClickListener);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        UiTestHelper.verifyComboBox(verticalLayout.getComponent(0), "Period", false);
        verifyButtonsLayout(verticalLayout.getComponent(1), "Publish", "Close");
    }
}
