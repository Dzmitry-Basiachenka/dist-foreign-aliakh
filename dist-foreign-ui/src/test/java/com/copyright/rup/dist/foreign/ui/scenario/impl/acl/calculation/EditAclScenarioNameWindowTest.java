package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link EditAclScenarioNameWindow}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p/>
 * Date: 12/21/2023
 *
 * @author Dzmitry Basiachenka
 */
public class EditAclScenarioNameWindowTest {

    private static final String SCENARIO_NAME = "Distribution 2023";
    private EditAclScenarioNameWindow window;
    private IAclScenariosController controller;
    private AclScenario scenario;

    @Before
    public void setUp() {
        controller = createMock(IAclScenariosController.class);
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).once();
        scenario = new AclScenario();
        scenario.setId("13c07d54-f786-4404-8e1d-e49bf12193f6");
        scenario.setName(SCENARIO_NAME);
    }

    @Test
    public void testComponentStructure() {
        replay(controller);
        window = new EditAclScenarioNameWindow(controller, scenario);
        verifyWindow(window, "Edit Scenario Name", 320, -1, Unit.PIXELS);
        assertEquals("edit-acl-scenario-name-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(2, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyButtonsLayout(content.getComponent(1), "Save", "Cancel");
        verify(controller);
    }

    @Test
    public void testSaveButtonClick() {
        String newScenarioName = "new scenario name";
        IAclScenariosWidget widget = createMock(IAclScenariosWidget.class);
        expect(controller.aclScenarioExists(newScenarioName)).andReturn(false).times(2);
        expect(controller.getWidget()).andReturn(widget).once();
        controller.editAclScenarioName(scenario.getId(), newScenarioName);
        expectLastCall().once();
        widget.refresh();
        expectLastCall().once();
        replay(widget, controller);
        window = new EditAclScenarioNameWindow(controller, scenario);
        VerticalLayout content = (VerticalLayout) window.getContent();
        ((TextField) content.getComponent(0)).setValue(newScenarioName);
        Component component = content.getComponent(1);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        ClickListener listener = (ClickListener) saveButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(window));
        verify(widget, controller);
    }

    @Test
    public void testScenarioNameExists() {
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(true).times(3);
        replay(controller);
        window = new EditAclScenarioNameWindow(controller, scenario);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        Binder<String> binder = Whitebox.getInternalState(window, "binder");
        validateScenarioNameExistence(scenarioNameField, binder, SCENARIO_NAME);
        validateScenarioNameExistence(scenarioNameField, binder, ' ' + SCENARIO_NAME + ' ');
        verify(controller);
    }

    @Test
    public void testScenarioNameFieldValidation() {
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        window = new EditAclScenarioNameWindow(controller, scenario);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField scenarioName = Whitebox.getInternalState(window, "scenarioNameField");
        String emptyFieldValidationMessage = "Field value should be specified";
        validateFieldAndVerifyErrorMessage(scenarioName, SCENARIO_NAME, binder, null, true);
        validateFieldAndVerifyErrorMessage(scenarioName, StringUtils.EMPTY, binder, emptyFieldValidationMessage, false);
        validateFieldAndVerifyErrorMessage(scenarioName, "   ", binder, emptyFieldValidationMessage, false);
        validateFieldAndVerifyErrorMessage(scenarioName, StringUtils.repeat('a', 51), binder,
            "Field value should not exceed 50 characters", false);
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
        assertEquals("Scenario Name", scenarioNameField.getCaption());
        assertEquals(SCENARIO_NAME, scenarioNameField.getValue());
        assertEquals("scenario-name", scenarioNameField.getId());
    }
}
