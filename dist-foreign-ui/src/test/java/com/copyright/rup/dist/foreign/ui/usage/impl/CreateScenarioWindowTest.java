package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.usage.api.IFasUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.EventObject;
import java.util.List;
import java.util.stream.Collectors;

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

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String SCENARIO_NAME = "FAS Distribution " + DATE;
    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private CreateScenarioWindow createScenarioWindow;

    @Test
    public void testComponentStructure() {
        IFasUsageController controller = createMock(IFasUsageController.class);
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        createScenarioWindow = new CreateScenarioWindow(controller);
        verify(controller);
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
    public void testButtonConfirmClick() {
        IFasUsageController controller = createMock(IFasUsageController.class);
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        Scenario scenario = new Scenario();
        expect(controller.createScenario(SCENARIO_NAME, "")).andReturn(scenario).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        replay(controller, scenarioService);
        TestCreateScenarioWindow window = new TestCreateScenarioWindow(controller);
        VerticalLayout content = (VerticalLayout) window.getContent();
        Component component = content.getComponent(2);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        Button confirmButton = verifyButton(buttonsLayout.getComponent(0), "Confirm");
        ClickListener listener = (ClickListener) confirmButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(window));
        EventObject event = window.getEventObject();
        assertNotNull(event);
        assertTrue(event instanceof ScenarioCreateEvent);
        ScenarioCreateEvent scenarioCreateEvent = (ScenarioCreateEvent) event;
        assertEquals(scenario, scenarioCreateEvent.getScenarioId());
        assertEquals(window, scenarioCreateEvent.getSource());
        verify(controller, scenarioService);
    }

    @Test
    public void testButtonCloseClick() {
        IFasUsageController controller = createMock(IFasUsageController.class);
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller, scenarioService);
        TestCreateScenarioWindow window = new TestCreateScenarioWindow(controller);
        assertFalse(window.isClosed());
        VerticalLayout content = (VerticalLayout) window.getContent();
        Component component = content.getComponent(2);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        Button cancelButton = verifyButton(buttonsLayout.getComponent(1), "Cancel");
        ClickListener listener = (ClickListener) cancelButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(window));
        assertTrue(window.isClosed());
        verify(controller, scenarioService);
    }

    @Test
    public void testScenarioNameExists() {
        IFasUsageController controller = createMock(IFasUsageController.class);
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(true).times(4);
        replay(controller, scenarioService);
        createScenarioWindow = new CreateScenarioWindow(controller);
        TextField scenarioNameField = Whitebox.getInternalState(createScenarioWindow, "scenarioNameField");
        Binder binder = Whitebox.getInternalState(createScenarioWindow, "binder");
        validateScenarioNameExistence(scenarioNameField, binder, SCENARIO_NAME);
        validateScenarioNameExistence(scenarioNameField, binder, ' ' + SCENARIO_NAME + ' ');
        verify(controller, scenarioService);
    }

    private void validateScenarioNameExistence(TextField scenarioNameField, Binder binder, String scenarioName) {
        scenarioNameField.setValue(scenarioName);
        List<String> errorMessages = ((List<ValidationResult>) binder.validate().getValidationErrors())
            .stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
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

    private void verifyDescriptionArea(Component component) {
        assertNotNull(component);
        TextArea descriptionArea = (TextArea) component;
        assertEquals("Description", descriptionArea.getCaption());
        assertEquals("scenario-description", descriptionArea.getId());
    }

    private void verifyButtonsLayout(Component component) {
        assertNotNull(component);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        assertTrue(buttonsLayout.isSpacing());
        assertEquals(2, buttonsLayout.getComponentCount());
        verifyButton(buttonsLayout.getComponent(0), "Confirm");
        verifyButton(buttonsLayout.getComponent(1), "Cancel");
    }

    private Button verifyButton(Component component, String caption) {
        assertNotNull(component);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        return button;
    }

    private static class TestCreateScenarioWindow extends CreateScenarioWindow {

        private EventObject eventObject;
        private boolean closed;

        TestCreateScenarioWindow(IFasUsageController controller) {
            super(controller);
        }

        EventObject getEventObject() {
            return eventObject;
        }

        boolean isClosed() {
            return closed;
        }

        @Override
        protected void fireEvent(EventObject event) {
            this.eventObject = event;
        }

        @Override
        public void close() {
            this.closed = true;
        }
    }
}
