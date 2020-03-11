package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.IParametersSaveListener;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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

    @Test
    public void testConstructor() {
        AaclScenarioParameterWidget scenarioParameterWidget = new AaclScenarioParameterWidget(CAPTION, Window::new);
        assertEquals(1, scenarioParameterWidget.getComponentCount());
        verifyButton(scenarioParameterWidget.getComponent(0));
    }

    @Test
    public void testAddParameterSaveListener() {
        AaclScenarioParameterWidget<String> scenarioParameterWidget =
            new AaclScenarioParameterWidget<>(CAPTION, Window::new);
        IParametersSaveListener saveListener = createMock(IParametersSaveListener.class);
        assertNotEquals(saveListener, Whitebox.getInternalState(scenarioParameterWidget, "saveListener"));
        scenarioParameterWidget.addParameterSaveListener(saveListener);
        assertEquals(saveListener, Whitebox.getInternalState(scenarioParameterWidget, "saveListener"));
    }

    @Test
    public void testButtonClickListener() {
        mockStatic(Windows.class);
        Window window = new Window();
        IParametersSaveListener<String> listener = event -> { /*stub*/ };
        AaclScenarioParameterWidget<String> scenarioParameterWidget =
            new AaclScenarioParameterWidget<>(CAPTION, () -> window);
        scenarioParameterWidget.addParameterSaveListener(listener);
        Button button = (Button) scenarioParameterWidget.getComponent(0);
        Windows.showModalWindow(window);
        expectLastCall().once();
        replay(Windows.class);
        button.click();
        assertEquals(listener, window.getListeners(ParametersSaveEvent.class).iterator().next());
        verify(Windows.class);
    }

    private void verifyButton(Component component) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(CAPTION, button.getCaption());
        assertTrue(button.getStyleName().contains("link"));
        assertTrue(button.isDisableOnClick());
    }
}
