package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
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
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.EventObject;

/**
 * Verifies {@link CreateScenarioWindow}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/15/2017
 *
 * @author Mikalai Bezmen
 */
public class CreateScenarioWindowTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String SCENARIO_NAME = "FAS Distribution " + DATE;
    private static final String FAS_PRODUCT_FAMILY = "FAS";

    private CreateScenarioWindow createScenarioWindow;
    private IFasUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IFasUsageController.class);
    }

    @Test
    public void testComponentStructure() {
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        createScenarioWindow = new CreateScenarioWindow(controller);
        verify(controller);
        verifyWindow(createScenarioWindow, "Create Scenario", "650px", null, Unit.PIXELS, false);
        assertEquals("create-scenario-window", createScenarioWindow.getId().get());
        var content = (VerticalLayout) getDialogContent(createScenarioWindow);
        assertEquals(3, content.getComponentCount());
        verifyScenarioNameField(content.getComponentAt(0));
        verifyDescriptionArea(content.getComponentAt(1));
        verifyButtonsLayout(content.getComponentAt(2), true, "Confirm", "Cancel");
    }

    @Test
    public void testButtonConfirmClick() {
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        var scenario = new Scenario();
        expect(controller.createScenario(SCENARIO_NAME, "")).andReturn(scenario).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        replay(controller, scenarioService);
        var window = new TestCreateScenarioWindow(controller);
        var content = (VerticalLayout) getDialogContent(window);
        var component = content.getComponentAt(2);
        var buttonsLayout = (HorizontalLayout) component;
        var confirmButton = verifyButton(buttonsLayout.getComponentAt(0), "Confirm", true);
        confirmButton.click();
        var event = window.getComponentEvent();
        assertThat(event, instanceOf(ScenarioCreateEvent.class));
        var scenarioCreateEvent = (ScenarioCreateEvent) event;
        assertEquals(scenario, scenarioCreateEvent.getScenarioId());
        assertEquals(window, scenarioCreateEvent.getSource());
        verify(controller, scenarioService);
    }

    @Test
    public void testButtonCloseClick() {
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller, scenarioService);
        var window = new TestCreateScenarioWindow(controller);
        assertFalse(window.isClosed());
        var content = (VerticalLayout) getDialogContent(window);
        var component = content.getComponentAt(2);
        var buttonsLayout = (HorizontalLayout) component;
        var cancelButton = verifyButton(buttonsLayout.getComponentAt(1), "Cancel", true);
        cancelButton.click();
        assertTrue(window.isClosed());
        verify(controller, scenarioService);
    }

    @Test
    public void testScenarioNameFieldValidation() {
        String existingScenarioName = "Scenario 09/17/2021";
        expect(controller.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(3);
        expect(controller.scenarioExists(existingScenarioName)).andReturn(true).times(4);
        replay(controller);
        createScenarioWindow = new CreateScenarioWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(createScenarioWindow, "binder");
        TextField scenarioNameField = Whitebox.getInternalState(createScenarioWindow, "scenarioNameField");
        assertFieldValidationMessage(scenarioNameField, StringUtils.EMPTY, binder,
            "Field value should be specified", false);
        assertFieldValidationMessage(scenarioNameField, "    ", binder,
            "Field value should be specified", false);
        assertFieldValidationMessage(scenarioNameField, StringUtils.repeat('a', 51), binder,
            "Field value should not exceed 50 characters", false);
        assertFieldValidationMessage(scenarioNameField, existingScenarioName, binder,
            "Scenario with such name already exists", false);
        assertFieldValidationMessage(scenarioNameField, ' ' + existingScenarioName + ' ', binder,
            "Scenario with such name already exists", false);
        assertFieldValidationMessage(scenarioNameField, SCENARIO_NAME, binder, null, true);
        verify(controller);
    }

    private void verifyScenarioNameField(Component component) {
        assertNotNull(component);
        TextField scenarioNameField = (TextField) component;
        assertEquals("Scenario Name", scenarioNameField.getLabel());
        assertEquals(SCENARIO_NAME, scenarioNameField.getValue());
        assertEquals("scenario-name", scenarioNameField.getId().get());
    }

    private void verifyDescriptionArea(Component component) {
        assertNotNull(component);
        TextArea descriptionArea = (TextArea) component;
        assertEquals("Description", descriptionArea.getLabel());
        assertEquals("scenario-description", descriptionArea.getId().get());
    }

    private static class TestCreateScenarioWindow extends CreateScenarioWindow {

        private ComponentEvent<?> componentEvent;
        private boolean closed;

        TestCreateScenarioWindow(IFasUsageController controller) {
            super(controller);
        }

        EventObject getComponentEvent() {
            return componentEvent;
        }

        boolean isClosed() {
            return closed;
        }

        @Override
        protected void fireEvent(ComponentEvent<?> event) {
            this.componentEvent = event;
        }

        @Override
        public void close() {
            this.closed = true;
        }
    }
}
