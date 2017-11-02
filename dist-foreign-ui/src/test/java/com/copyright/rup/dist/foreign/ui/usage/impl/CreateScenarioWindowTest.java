package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.component.validator.ScenarioNameUniqueValidator;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;

import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;

/**
 * Verifies {@link CreateScenarioWindow}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/15/2017
 *
 * @author Mikalai Bezmen
 */
public class CreateScenarioWindowTest {

    private static final String SCENARIO_NAME = "Scenario Name";
    private static final String DESCRIPTION = "Description of Scenario creation";
    private CreateScenarioWindow createScenarioWindow;
    private IScenarioService scenarioService;
    private IUsagesController controller;

    @Before
    public void setUp() {
        controller = createMock(IUsagesController.class);
        scenarioService = createMock(IScenarioService.class);
        expect(controller.getScenarioService()).andReturn(scenarioService).once();
        replay(controller);
        createScenarioWindow = new CreateScenarioWindow(controller);
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals("Create Scenario", createScenarioWindow.getCaption());
        assertEquals(320, createScenarioWindow.getWidth(), 0);
        assertEquals("create-scenario-window", createScenarioWindow.getId());
        VerticalLayout content = (VerticalLayout) createScenarioWindow.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(3, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyDescriptionArea(content.getComponent(1));
        verifyButtonsLayout(content.getComponent(2));
    }

    @Test
    public void testOnConfirmButtonClicked() {
        ClickEvent clickEvent = createMock(ClickEvent.class);
        TextField scenarioNameField = createMock(TextField.class);
        TextArea descriptionArea = createMock(TextArea.class);
        Whitebox.setInternalState(createScenarioWindow, "scenarioNameField", scenarioNameField);
        Whitebox.setInternalState(createScenarioWindow, "descriptionArea", descriptionArea);
        Button confirmButton = (Button) ((HorizontalLayout) ((VerticalLayout) createScenarioWindow.getContent())
            .getComponent(2)).getComponent(0);
        expect(scenarioNameField.isValid()).andReturn(true).once();
        expect(descriptionArea.isValid()).andReturn(true).once();
        expect(scenarioNameField.getValue()).andReturn(SCENARIO_NAME).once();
        expect(descriptionArea.getValue()).andReturn(DESCRIPTION).once();
        expect(controller.createScenario(SCENARIO_NAME, DESCRIPTION)).andReturn(RupPersistUtils.generateUuid()).once();
        replay(clickEvent, scenarioNameField, descriptionArea, controller, scenarioService);
        Collection<?> listeners = confirmButton.getListeners(ClickEvent.class);
        assertEquals(1, listeners.size());
        ClickListener clickListener = (ClickListener) listeners.iterator().next();
        clickListener.buttonClick(clickEvent);
        verify(clickEvent, scenarioNameField, descriptionArea, controller, scenarioService);
    }

    private void verifyScenarioNameField(Component component) {
        assertNotNull(component);
        TextField scenarioNameField = (TextField) component;
        assertEquals("Scenario name", scenarioNameField.getCaption());
        assertEquals(String.format("FAS Distribution %s", LocalDate.now().format(
            DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT))), scenarioNameField.getValue());
        assertFalse(scenarioNameField.isValidationVisible());
        assertEquals("scenario-name", scenarioNameField.getId());
        assertEquals(StringUtils.EMPTY, scenarioNameField.getNullRepresentation());
        assertTrue(scenarioNameField.isRequired());
        assertEquals("Field value should be specified", scenarioNameField.getRequiredError());
        Collection<Validator> validators = scenarioNameField.getValidators();
        assertEquals(2, validators.size());
        Iterator<Validator> iterator = validators.iterator();
        assertTrue(iterator.next() instanceof StringLengthValidator);
        assertTrue(iterator.next() instanceof ScenarioNameUniqueValidator);
    }

    private void verifyDescriptionArea(Component component) {
        assertNotNull(component);
        TextArea descriptionArea = (TextArea) component;
        assertEquals("Description", descriptionArea.getCaption());
        assertEquals("scenario-description", descriptionArea.getId());
        assertFalse(descriptionArea.isValidationVisible());
        Collection<Validator> validators = descriptionArea.getValidators();
        assertEquals(1, validators.size());
        Iterator<Validator> iterator = validators.iterator();
        assertTrue(iterator.next() instanceof StringLengthValidator);
    }

    private void verifyButtonsLayout(Component component) {
        assertNotNull(component);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        assertTrue(buttonsLayout.isSpacing());
        assertEquals(2, buttonsLayout.getComponentCount());
        verifyButton(buttonsLayout.getComponent(0), "Confirm");
        verifyButton(buttonsLayout.getComponent(1), "Cancel");
    }

    private void verifyButton(Component component, String caption) {
        assertNotNull(component);
        Button cancelButton = (Button) component;
        assertEquals(caption, cancelButton.getCaption());
    }
}
