package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@PrepareForTest({Windows.class})
public class ScenarioParameterWidgetTest {

    private static final String CAPTION = "caption";
    private static final String DEFAULT_VALUE = "default";
    private ScenarioParameterWidget<String> widget;
    private CommonScenarioParameterWindowMock window;

    @Before
    public void setUp() {
        window = new CommonScenarioParameterWindowMock();
        widget = new ScenarioParameterWidget<>(CAPTION, DEFAULT_VALUE, () -> window);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, widget.getComponentCount());
        verifyButton(widget.getComponent(0));
        assertEquals(DEFAULT_VALUE, widget.getAppliedParameters());
    }

    @Test
    public void testButtonClickListener() {
        mockStatic(Windows.class);
        Button button = (Button) widget.getComponent(0);
        Windows.showModalWindow(window);
        expectLastCall().once();
        replay(Windows.class);
        button.click();
        window.fireParametersSaveEvent(new ParametersSaveEvent<>(window, "applied"));
        assertNotNull(window.getListeners(ParametersSaveEvent.class).iterator().next());
        assertEquals("applied", widget.getAppliedParameters());
        verify(Windows.class);
    }

    private void verifyButton(Component component) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals(CAPTION, button.getCaption());
        assertTrue(button.getStyleName().contains("link"));
        assertTrue(button.isDisableOnClick());
    }

    private static class CommonScenarioParameterWindowMock extends CommonScenarioParameterWindow<String> {
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
