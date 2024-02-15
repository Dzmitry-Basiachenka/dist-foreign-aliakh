package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import com.vaadin.flow.data.binder.ValidationResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
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
    private static final String SCENARIO_BINDER = "scenarioBinder";
    private static final String SPACES_STRING = "    ";
    private static final String STRING_50_CHARACTERS = StringUtils.repeat('a', 50);
    private static final String STRING_51_CHARACTERS = StringUtils.repeat('a', 51);
    private static final String EMPTY_FIELD_MESSAGE = "Field value should be specified";
    private static final String SCENARIO_EXISTS_MESSAGE = "Scenario with such name already exists";
    private static final String INVALID_AMOUNT_MESSAGE =
        "Field value should be positive number and should not exceed 10 digits";
    private static final String VALUE_EXCEED_50_CHARACTERS_MESSAGE = "Field value should not exceed 50 characters";

    private INtsUsageController controller;
    private FundPool fundPool;

    @Before
    public void setUp() {
        controller = createMock(INtsUsageController.class);
        fundPool = new FundPool();
        fundPool.setName("Pre-Service Fee Fund");
        expect(controller.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).anyTimes();
        expect(controller.getAdditionalFundsNotAttachedToScenario()).andReturn(List.of(fundPool)).once();
    }

    @Test
    public void testComponentStructure() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        var window = new CreateNtsScenarioWindow(controller);
        verify(controller);
        verifyWindow(window, "Create Scenario", "320px", null, Unit.PIXELS, false);
        assertEquals("create-scenario-window", window.getId().get());
        var content = (VerticalLayout) getDialogContent(window);
        assertEquals(6, content.getComponentCount());
        verifyScenarioNameField(content.getComponentAt(0));
        verifyRhMinimumAmountField(content.getComponentAt(1));
        verifyPreServiceFeeAmountField(content.getComponentAt(2));
        verifyPostServiceFeeAmountField(content.getComponentAt(3));
        verifyComboBox(content.getComponentAt(4), "Pre-Service Fee Funds", true, fundPool);
        verifyDescriptionArea(content.getComponentAt(5));
        verifyButtonsLayout(getFooterLayout(window), true, "Confirm", "Cancel");
    }

    @Test
    public void testButtonConfirmClick() {
        var ntsFields = new Scenario.NtsFields();
        ntsFields.setRhMinimumAmount(new BigDecimal("300"));
        ntsFields.setPreServiceFeeAmount(BigDecimal.ZERO);
        ntsFields.setPostServiceFeeAmount(BigDecimal.ZERO);
        var scenario = new Scenario();
        expect(controller.createNtsScenario(SCENARIO_NAME, ntsFields, "")).andReturn(scenario).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        replay(controller);
        var window = new TestCreateNtsScenarioWindow(controller);
        var buttonsLayout = getFooterLayout(window);
        var confirmButton = verifyButton(buttonsLayout.getComponentAt(0), "Confirm", true);
        confirmButton.click();
        EventObject event = window.getEventObject();
        assertNotNull(event);
        assertThat(event, instanceOf(ScenarioCreateEvent.class));
        var scenarioCreateEvent = (ScenarioCreateEvent) event;
        assertEquals(scenario, scenarioCreateEvent.getScenarioId());
        assertEquals(window, scenarioCreateEvent.getSource());
        verify(controller);
    }

    @Test
    public void testButtonCloseClick() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        var window = new TestCreateNtsScenarioWindow(controller);
        assertFalse(window.isClosed());
        var buttonsLayout = getFooterLayout(window);
        var cancelButton = verifyButton(buttonsLayout.getComponentAt(1), "Cancel", true);
        cancelButton.click();
        assertTrue(window.isClosed());
        verify(controller);
    }

    @Test
    public void testScenarioNameExists() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(true).times(4);
        replay(controller);
        var window = new CreateNtsScenarioWindow(controller);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        Binder<?> binder = Whitebox.getInternalState(window, SCENARIO_BINDER);
        validateScenarioNameExistence(scenarioNameField, binder, SCENARIO_NAME);
        validateScenarioNameExistence(scenarioNameField, binder, ' ' + SCENARIO_NAME + ' ');
        verify(controller);
    }

    @Test
    public void testScenarioNameFieldValidation0() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(3);
        expect(controller.scenarioExists(STRING_50_CHARACTERS)).andReturn(false).times(2);
        replay(controller);
        var window = new CreateNtsScenarioWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(window, SCENARIO_BINDER);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        assertFieldValidationMessage(scenarioNameField, StringUtils.EMPTY, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(scenarioNameField, SPACES_STRING, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(scenarioNameField, STRING_50_CHARACTERS, binder, null, true);
        assertFieldValidationMessage(scenarioNameField, STRING_51_CHARACTERS, binder,
            VALUE_EXCEED_50_CHARACTERS_MESSAGE, false);
        assertFieldValidationMessage(scenarioNameField, SCENARIO_NAME, binder, null, true);
        verify(controller);
    }

    @Test
    public void testScenarioNameFieldValidation() {
        String existingScenarioName = "Scenario 09/17/2021";
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(3);
        expect(controller.scenarioExists(STRING_50_CHARACTERS)).andReturn(false).times(2);
        expect(controller.scenarioExists(existingScenarioName)).andReturn(true).times(4);
        replay(controller);
        var window = new CreateNtsScenarioWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(window, SCENARIO_BINDER);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        assertFieldValidationMessage(scenarioNameField, StringUtils.EMPTY, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(scenarioNameField, SPACES_STRING, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(scenarioNameField, STRING_50_CHARACTERS, binder, null, true);
        assertFieldValidationMessage(scenarioNameField, STRING_51_CHARACTERS, binder,
            VALUE_EXCEED_50_CHARACTERS_MESSAGE, false);
        assertFieldValidationMessage(scenarioNameField, existingScenarioName, binder,
            SCENARIO_EXISTS_MESSAGE, false);
        assertFieldValidationMessage(scenarioNameField, ' ' + existingScenarioName + ' ', binder,
            SCENARIO_EXISTS_MESSAGE, false);
        assertFieldValidationMessage(scenarioNameField, SCENARIO_NAME, binder, null, true);
        verify(controller);
    }

    @Test
    public void testRhMinimumAmountFieldValidation() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).anyTimes();
        replay(controller);
        var window = new CreateNtsScenarioWindow(controller);
        verifyAmountField(Whitebox.getInternalState(window, SCENARIO_BINDER),
            Whitebox.getInternalState(window, "rhMinimumAmountField"));
        verify(controller);
    }

    @Test
    public void testPreServiceFeeAmountFieldValidation() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        var window = new CreateNtsScenarioWindow(controller);
        verifyAmountField(Whitebox.getInternalState(window, "fundBinder"),
            Whitebox.getInternalState(window, "preServiceFeeAmountField"));
        verify(controller);
    }

    @Test
    public void testPostServiceFeeAmountFieldValidation() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        var window = new CreateNtsScenarioWindow(controller);
        verifyAmountField(Whitebox.getInternalState(window, "fundBinder"),
            Whitebox.getInternalState(window, "postServiceFeeAmountField"));
        verify(controller);
    }

    private void validateScenarioNameExistence(TextField scenarioNameField, Binder<?> binder, String scenarioName) {
        scenarioNameField.setValue(scenarioName);
        List<String> errorMessages = binder.validate().getValidationErrors()
            .stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(1, errorMessages.size());
        assertEquals("Scenario with such name already exists", errorMessages.get(0));
    }

    private void verifyScenarioNameField(Component component) {
        assertNotNull(component);
        var scenarioNameField = (TextField) component;
        assertEquals("Scenario Name", scenarioNameField.getLabel());
        assertEquals(SCENARIO_NAME, scenarioNameField.getValue());
        assertEquals("scenario-name", scenarioNameField.getId().get());
    }

    private void verifyRhMinimumAmountField(Component component) {
        assertNotNull(component);
        BigDecimalField rhMinimumAmountField = (BigDecimalField) component;
        assertEquals("RH Minimum Amount", rhMinimumAmountField.getLabel());
        assertEquals(new BigDecimal("300"), rhMinimumAmountField.getValue());
        assertEquals("rh-minimum-amount-field", rhMinimumAmountField.getId().get());
    }

    private void verifyPreServiceFeeAmountField(Component component) {
        assertNotNull(component);
        var preServiceFeeAmountField = (BigDecimalField) component;
        assertEquals("Pre-Service Fee Amount", preServiceFeeAmountField.getLabel());
        assertEquals(BigDecimal.ZERO, preServiceFeeAmountField.getValue());
        assertEquals("pre-service-fee-amount-field", preServiceFeeAmountField.getId().get());
    }

    private void verifyPostServiceFeeAmountField(Component component) {
        assertNotNull(component);
        var postServiceFeeAmountField = (BigDecimalField) component;
        assertEquals("Post-Service Fee Amount", postServiceFeeAmountField.getLabel());
        assertEquals(BigDecimal.ZERO, postServiceFeeAmountField.getValue());
        assertEquals("post-service-fee-amount-field", postServiceFeeAmountField.getId().get());
    }

    private void verifyDescriptionArea(Component component) {
        assertNotNull(component);
        var descriptionArea = (TextArea) component;
        assertEquals("Description", descriptionArea.getLabel());
        assertEquals(StringUtils.EMPTY, descriptionArea.getValue());
        assertEquals("scenario-description", descriptionArea.getId().get());
    }

    private void verifyAmountField(Binder<?> binder, BigDecimalField field) {
        assertFieldValidationMessage(field, new BigDecimal("-1"), binder, INVALID_AMOUNT_MESSAGE, false);
        assertFieldValidationMessage(field, BigDecimal.ZERO, binder, null, true);
        assertFieldValidationMessage(field, new BigDecimal(".01"), binder, null, true);
        assertFieldValidationMessage(field, new BigDecimal("0.004"), binder, null, true);
        assertFieldValidationMessage(field, new BigDecimal("0.005"), binder, null, true);
        assertFieldValidationMessage(field, new BigDecimal("1.00"), binder, null, true);
        assertFieldValidationMessage(field, new BigDecimal("01.01"), binder, null, true);
        assertFieldValidationMessage(field, new BigDecimal("99"), binder, null, true);
        assertFieldValidationMessage(field, new BigDecimal("1000000000"), binder, null, true);
        assertFieldValidationMessage(field, new BigDecimal("9999999999.99"), binder, null, true);
        assertFieldValidationMessage(field, new BigDecimal("10000000000"), binder, INVALID_AMOUNT_MESSAGE, false);
    }

    private static class TestCreateNtsScenarioWindow extends CreateNtsScenarioWindow {

        private ComponentEvent<?> eventObject;
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
        protected void fireEvent(ComponentEvent<?> event) {
            this.eventObject = event;
        }

        @Override
        public void close() {
            this.closed = true;
        }
    }
}
