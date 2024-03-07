package com.copyright.rup.dist.foreign.vui.usage.impl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.button.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link ScenarioParameterWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/11/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class ScenarioParameterWidgetTest {

    private static final String CAPTION = "caption";
    private static final String DEFAULT_VALUE = "default";

    private ScenarioParameterWidget<String> widget;
    private TestCommonScenarioParameterWindow window;

    @Before
    public void setUp() {
        window = new TestCommonScenarioParameterWindow();
        widget = new ScenarioParameterWidget<>(CAPTION, DEFAULT_VALUE, () -> window);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, widget.getComponentCount());
        verifyButton(widget.getComponentAt(0), CAPTION, true, true);
        assertEquals(DEFAULT_VALUE, widget.getAppliedParameters());
    }

    @Test
    public void testButtonClickListener() {
        mockStatic(Windows.class);
        Button button = (Button) widget.getComponentAt(0);
        Windows.showModalWindow(window);
        expectLastCall().once();
        replay(Windows.class);
        button.click();
        window.fireParametersSaveEvent(new ParametersSaveEvent<>(window, "applied"));
        assertNotNull(ComponentUtil.getListeners(window, ParametersSaveEvent.class).iterator().next());
        assertEquals("applied", widget.getAppliedParameters());
        verify(Windows.class);
    }

    private static class TestCommonScenarioParameterWindow extends CommonScenarioParameterWindow<String> {

        @Override
        void setDefault(String params) {
        }

        @Override
        void setAppliedParameters(String params) {
        }

        @Override
        void fireParametersSaveEvent(ParametersSaveEvent parametersSaveEvent) {
            fireEvent(parametersSaveEvent);
        }
    }
}
