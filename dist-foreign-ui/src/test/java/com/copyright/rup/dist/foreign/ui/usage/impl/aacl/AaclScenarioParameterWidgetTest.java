package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Verifies {@link AaclScenarioParameterWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/11/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AaclScenarioParameterWidgetTest {

    private static final String CAPTION = "caption";
    private static final String DEFAULT_VALUE = "default";
    private AaclScenarioParameterWidget<String> widget;
    private AaclCommonScenarioParameterWindowMock window;

    @Before
    public void setUp() {
        window = new AaclCommonScenarioParameterWindowMock();
        widget = new AaclScenarioParameterWidget<>(CAPTION, () -> DEFAULT_VALUE, () -> window);
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
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(CAPTION, button.getCaption());
        assertTrue(button.getStyleName().contains("link"));
        assertTrue(button.isDisableOnClick());
    }

    private static class AaclCommonScenarioParameterWindowMock extends AaclCommonScenarioParameterWindow<String> {
        @Override
        void setDefaultParameters(String params) {
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
