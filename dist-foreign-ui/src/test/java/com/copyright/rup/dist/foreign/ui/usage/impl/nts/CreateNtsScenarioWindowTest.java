package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;
import java.util.Objects;
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
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";
    private static final String POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE =
        "Field value should be positive number or zero and should not exceed 10 digits";
    private FundPool preServiceFeeFund;

    private INtsUsageController controller;
    private CreateNtsScenarioWindow window;

    @Before
    public void setUp() {
        controller = createMock(INtsUsageController.class);
        preServiceFeeFund = new FundPool();
        preServiceFeeFund.setName("Pre-Service Fee Fund 1");
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).anyTimes();
        expect(controller.getAdditionalFundsNotAttachedToScenario())
            .andReturn(Collections.singletonList(preServiceFeeFund)).once();
    }

    @Test
    public void testComponentStructure() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        window = new CreateNtsScenarioWindow(controller);
        verify(controller);
        verifyWindow(window, "Create Scenario", 320, -1, Unit.PIXELS);
        assertEquals("create-scenario-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(7, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyRhMinimumAmountField(content.getComponent(1));
        verifyPreServiceFeeAmountField(content.getComponent(2));
        verifyPostServiceFeeAmountField(content.getComponent(3));
        verifyComboBox(content.getComponent(4), "Pre-Service Fee Funds", true, preServiceFeeFund);
        verifyDescriptionArea(content.getComponent(5));
        verifyButtonsLayout(content.getComponent(6), "Confirm", "Cancel");
    }

    @Test
    public void testButtonConfirmClick() {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setRhMinimumAmount(new BigDecimal("300"));
        ntsFields.setPreServiceFeeAmount(BigDecimal.ZERO);
        ntsFields.setPostServiceFeeAmount(BigDecimal.ZERO);
        Scenario scenario = new Scenario();
        expect(controller.createNtsScenario(SCENARIO_NAME, ntsFields, "")).andReturn(scenario).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        replay(controller);
        TestCreateNtsScenarioWindow createScenarioWindow = new TestCreateNtsScenarioWindow(controller);
        VerticalLayout content = (VerticalLayout) createScenarioWindow.getContent();
        Component component = content.getComponent(6);
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
        verify(controller);
    }

    @Test
    public void testButtonCloseClick() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        TestCreateNtsScenarioWindow createScenarioWindow = new TestCreateNtsScenarioWindow(controller);
        assertFalse(createScenarioWindow.isClosed());
        VerticalLayout content = (VerticalLayout) createScenarioWindow.getContent();
        Component component = content.getComponent(6);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        Button cancelButton = verifyButton(buttonsLayout.getComponent(1), "Cancel");
        ClickListener listener = (ClickListener) cancelButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(createScenarioWindow));
        assertTrue(createScenarioWindow.isClosed());
        verify(controller);
    }

    @Test
    public void testScenarioNameExists() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(true).times(4);
        replay(controller);
        window = new CreateNtsScenarioWindow(controller);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        Binder binder = Whitebox.getInternalState(window, "scenarioBinder");
        validateScenarioNameExistence(scenarioNameField, binder, SCENARIO_NAME);
        validateScenarioNameExistence(scenarioNameField, binder, ' ' + SCENARIO_NAME + ' ');
        verify(controller);
    }

    @Test
    public void testScenarioNameFieldValidation() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(3);
        replay(controller);
        window = new CreateNtsScenarioWindow(controller);
        Binder binder = Whitebox.getInternalState(window, "scenarioBinder");
        TextField scenarioName = Whitebox.getInternalState(window, "scenarioNameField");
        verifyField(scenarioName, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        verifyField(scenarioName, "   ", binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        verifyField(scenarioName, StringUtils.repeat('a', 51), binder,
            "Field value should not exceed 50 characters", false);
        verifyField(scenarioName, SCENARIO_NAME, binder, null, true);
        verify(controller);
    }

    @Test
    public void testRhMinimumAmountFieldValidation() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).anyTimes();
        replay(controller);
        window = new CreateNtsScenarioWindow(controller);
        verifyAmountField(Whitebox.getInternalState(window, "scenarioBinder"),
            Whitebox.getInternalState(window, "rhMinimumAmountField"));
        verify(controller);
    }

    @Test
    public void testPreServiceFeeAmountFieldValidation() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        window = new CreateNtsScenarioWindow(controller);
        verifyAmountField(Whitebox.getInternalState(window, "fundBinder"),
            Whitebox.getInternalState(window, "preServiceFeeAmountField"));
        verify(controller);
    }

    @Test
    public void testPostServiceFeeAmountFieldValidation() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        window = new CreateNtsScenarioWindow(controller);
        verifyAmountField(Whitebox.getInternalState(window, "fundBinder"),
            Whitebox.getInternalState(window, "postServiceFeeAmountField"));
        verify(controller);
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

    private void verifyPreServiceFeeAmountField(Component component) {
        assertNotNull(component);
        TextField preServiceFeeAmountField = (TextField) component;
        assertEquals("Pre-Service Fee Amount", preServiceFeeAmountField.getCaption());
        assertEquals("0", preServiceFeeAmountField.getValue());
    }

    private void verifyPostServiceFeeAmountField(Component component) {
        assertNotNull(component);
        TextField postServiceFeeAmountField = (TextField) component;
        assertEquals("Post-Service Fee Amount", postServiceFeeAmountField.getCaption());
        assertEquals("0", postServiceFeeAmountField.getValue());
    }

    private void verifyDescriptionArea(Component component) {
        assertNotNull(component);
        TextArea descriptionArea = (TextArea) component;
        assertEquals("Description", descriptionArea.getCaption());
        assertEquals("scenario-description", descriptionArea.getId());
    }

    private Button verifyButton(Component component, String caption) {
        assertNotNull(component);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        return button;
    }

    private void verifyAmountField(Binder binder, TextField field) {
        verifyField(field, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        verifyField(field, "   ", binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        verifyField(field, "value", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        verifyField(field, ".01", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        verifyField(field, "01.01", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        verifyField(field, "01.01.", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        verifyField(field, " -1 ", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        verifyField(field, "10000000000", binder, POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        verifyField(field, "  0  ", binder, null, true);
        verifyField(field, "  0.004  ", binder, null, true);
        verifyField(field, "  0.005  ", binder, null, true);
        verifyField(field, "  1.00  ", binder, null, true);
        verifyField(field, "  99  ", binder, null, true);
        verifyField(field, "  9999999999.99  ", binder, null, true);
        verifyField(field, "1000000000",binder, null, true);
    }

    @SuppressWarnings("unchecked")
    private void verifyField(TextField field, String value, Binder binder, String errorMessage, boolean isValid) {
        field.setValue(value);
        binder.validate();
        List<TextField> fields = (List<TextField>) binder.getFields()
            .filter(actualField -> actualField.equals(field))
            .collect(Collectors.toList());
        assertEquals(1 , fields.size());
        TextField actualField = fields.get(0);
        assertNotNull(actualField);
        String actualErrorMessage = Objects.nonNull(actualField.getErrorMessage())
            ? actualField.getErrorMessage().toString()
            : null;
        assertEquals(value, actualField.getValue());
        assertEquals(errorMessage, actualErrorMessage);
        assertEquals(isValid, Objects.isNull(actualErrorMessage));
    }

    private static class TestCreateNtsScenarioWindow extends CreateNtsScenarioWindow {

        private EventObject eventObject;
        private boolean closed;

        TestCreateNtsScenarioWindow(INtsUsageController controller) {
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
