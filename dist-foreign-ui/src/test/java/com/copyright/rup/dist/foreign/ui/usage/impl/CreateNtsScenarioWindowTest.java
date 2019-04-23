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
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link CreateNtsScenarioWindow}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/15/2019
 *
 * @author Aliaksandr Liakh
 */
public class CreateNtsScenarioWindowTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String SCENARIO_NAME = "NTS Distribution " + DATE;
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private CreateNtsScenarioWindow window;

    @Test
    public void testComponentStructure() {
        IUsagesController controller = createMock(IUsagesController.class);
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getScenarioService()).andReturn(scenarioService).once();
        expect(scenarioService.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller, scenarioService);
        window = new CreateNtsScenarioWindow(controller);
        verify(controller, scenarioService);
        assertEquals("Create Scenario", window.getCaption());
        assertEquals(320, window.getWidth(), 0);
        assertEquals("create-scenario-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(4, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyRhMinimumAmountField(content.getComponent(1));
        verifyDescriptionArea(content.getComponent(2));
        verifyButtonsLayout(content.getComponent(3));
    }

    @Test
    public void testButtonConfirmClick() {
        IUsagesController controller = createMock(IUsagesController.class);
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getScenarioService()).andReturn(scenarioService).times(2);
        Scenario scenario = new Scenario();
        expect(controller.createNtsScenario(SCENARIO_NAME, new BigDecimal("300"), "")).andReturn(scenario).once();
        expect(scenarioService.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        replay(controller, scenarioService);
        TestCreateNtsScenarioWindow createScenarioWindow = new TestCreateNtsScenarioWindow(controller);
        VerticalLayout content = (VerticalLayout) createScenarioWindow.getContent();
        Component component = content.getComponent(3);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        Button confirmButton = verifyButton(buttonsLayout.getComponent(0), "Confirm");
        ClickListener listener = (ClickListener) confirmButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(createScenarioWindow));
        EventObject event = createScenarioWindow.getEventObject();
        assertNotNull(event);
        assertTrue(event instanceof ScenarioCreateEvent);
        ScenarioCreateEvent scenarioCreateEvent = (ScenarioCreateEvent) event;
        assertEquals(scenario, scenarioCreateEvent.getScenarioId());
        assertEquals(createScenarioWindow, scenarioCreateEvent.getSource());
        verify(controller, scenarioService);
    }

    @Test
    public void testButtonCloseClick() {
        IUsagesController controller = createMock(IUsagesController.class);
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getScenarioService()).andReturn(scenarioService).once();
        expect(scenarioService.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller, scenarioService);
        TestCreateNtsScenarioWindow createScenarioWindow = new TestCreateNtsScenarioWindow(controller);
        assertFalse(createScenarioWindow.isClosed());
        VerticalLayout content = (VerticalLayout) createScenarioWindow.getContent();
        Component component = content.getComponent(3);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        Button cancelButton = verifyButton(buttonsLayout.getComponent(1), "Cancel");
        ClickListener listener = (ClickListener) cancelButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(createScenarioWindow));
        assertTrue(createScenarioWindow.isClosed());
        verify(controller, scenarioService);
    }

    @Test
    public void testScenarioNameExists() {
        IUsagesController controller = createMock(IUsagesController.class);
        IScenarioService scenarioService = createMock(IScenarioService.class);
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(controller.getScenarioService()).andReturn(scenarioService).times(4);
        expect(scenarioService.scenarioExists(SCENARIO_NAME)).andReturn(true).times(4);
        replay(controller, scenarioService);
        window = new CreateNtsScenarioWindow(controller);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        Binder binder = Whitebox.getInternalState(window, "binder");
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

    private void verifyRhMinimumAmountField(Component component) {
        assertNotNull(component);
        TextField rhMinimumAmountField = (TextField) component;
        assertEquals("RH Minimum Amount", rhMinimumAmountField.getCaption());
        assertEquals("300", rhMinimumAmountField.getValue());
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

    private static class TestCreateNtsScenarioWindow extends CreateNtsScenarioWindow {

        private EventObject eventObject;
        private boolean closed;

        TestCreateNtsScenarioWindow(IUsagesController controller) {
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
