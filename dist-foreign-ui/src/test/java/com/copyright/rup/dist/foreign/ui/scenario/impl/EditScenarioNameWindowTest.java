package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link EditScenarioNameWindow}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 01/22/2021
 *
 * @author Ihar Suvorau
 */
public class EditScenarioNameWindowTest {

    private static final String SCENARIO_NAME = "Distribution 2021";
    private EditScenarioNameWindow window;
    private ICommonScenariosController controller;
    private Scenario scenario;

    @Before
    public void setUp() {
        controller = createMock(ICommonScenariosController.class);
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        scenario = new Scenario();
        scenario.setId("d936a589-176c-4efd-a119-9702b057ec58");
        scenario.setName(SCENARIO_NAME);
    }

    @Test
    public void testComponentStructure() {
        replay(controller);
        window = new EditScenarioNameWindow(controller, scenario);
        verify(controller);
        assertEquals("Edit Scenario Name", window.getCaption());
        assertEquals(320, window.getWidth(), 0);
        assertEquals("edit-scenario-name-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(2, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyButtonsLayout(content.getComponent(1));
    }

    @Test
    public void testButtonSaveClick() {
        String newScenarioName = "new scenario name";
        ICommonScenariosWidget widget = createMock(ICommonScenariosWidget.class);
        expect(controller.scenarioExists(newScenarioName)).andReturn(false).times(2);
        expect(controller.getWidget()).andReturn(widget).once();
        controller.editScenarioName(scenario.getId(), newScenarioName);
        expectLastCall().once();
        widget.refresh();
        expectLastCall().once();
        replay(widget, controller);
        window = new EditScenarioNameWindow(controller, scenario);
        VerticalLayout content = (VerticalLayout) window.getContent();
        ((TextField) content.getComponent(0)).setValue(newScenarioName);
        Component component = content.getComponent(1);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        Button saveButton = verifyButton(buttonsLayout.getComponent(0), "Save");
        ClickListener listener = (ClickListener) saveButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(window));
        verify(widget, controller);
    }

    @Test
    public void testScenarioNameExists() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(true).times(3);
        replay(controller);
        window = new EditScenarioNameWindow(controller, scenario);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        Binder<String> binder = Whitebox.getInternalState(window, "binder");
        validateScenarioNameExistence(scenarioNameField, binder, SCENARIO_NAME);
        validateScenarioNameExistence(scenarioNameField, binder, ' ' + SCENARIO_NAME + ' ');
        verify(controller);
    }

    private void validateScenarioNameExistence(TextField scenarioNameField, Binder<String> binder,
                                               String scenarioName) {
        scenarioNameField.setValue(scenarioName);
        List<String> errorMessages = binder.validate()
            .getValidationErrors()
            .stream()
            .map(ValidationResult::getErrorMessage)
            .collect(Collectors.toList());
        assertEquals(1, errorMessages.size());
        assertEquals("Scenario with such name already exists", errorMessages.get(0));
    }

    private void verifyScenarioNameField(Component component) {
        assertNotNull(component);
        TextField scenarioNameField = (TextField) component;
        assertEquals("Scenario name", scenarioNameField.getCaption());
        assertEquals(SCENARIO_NAME, scenarioNameField.getValue());
        assertEquals("scenario-name", scenarioNameField.getId());
    }

    private void verifyButtonsLayout(Component component) {
        assertNotNull(component);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        assertTrue(buttonsLayout.isSpacing());
        assertEquals(2, buttonsLayout.getComponentCount());
        verifyButton(buttonsLayout.getComponent(0), "Save");
        verifyButton(buttonsLayout.getComponent(1), "Cancel");
    }

    private Button verifyButton(Component component, String caption) {
        assertNotNull(component);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        return button;
    }
}