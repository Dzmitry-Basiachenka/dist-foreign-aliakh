package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPool.SalFields;
import com.copyright.rup.dist.foreign.domain.SalGradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageFilterController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link CreateSalScenarioWindow}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 09/23/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class CreateSalScenarioWindowTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String SCENARIO_NAME = "SAL Distribution " + DATE;
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final String FUND_POOL_ID = "8da1b173-fdad-4199-a660-c13e9dbc7257";

    private ISalUsageController controller;
    private ISalUsageFilterController filterController;
    private CreateSalScenarioWindow window;
    private FundPool fundPool;

    @Before
    public void setUp() {
        fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setName("SAL Fund Pool");
        SalFields salFields = new SalFields();
        salFields.setLicenseeAccountNumber(7001293454L);
        salFields.setGrade6to8GrossAmount(BigDecimal.TEN);
        fundPool.setSalFields(salFields);
        controller = createMock(ISalUsageController.class);
        filterController = createMock(ISalUsageFilterController.class);
        expect(controller.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).anyTimes();
        expect(controller.getFundPoolsNotAttachedToScenario()).andReturn(List.of(fundPool)).once();
    }

    @Test
    public void testComponentStructure() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        window = new CreateSalScenarioWindow(controller);
        verify(controller);
        assertEquals("Create Scenario", window.getCaption());
        assertEquals(320, window.getWidth(), 0);
        assertEquals("create-scenario-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(4, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyComboBox(content.getComponent(1), "Fund Pool", true, fundPool);
        verifyDescriptionArea(content.getComponent(2));
        verifyButtonsLayout(content.getComponent(3), "Confirm", "Cancel");
    }

    @Test
    public void testScenarioNameExists() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(true).times(4);
        replay(controller);
        window = new CreateSalScenarioWindow(controller);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        Binder binder = Whitebox.getInternalState(window, "scenarioBinder");
        validateScenarioNameExistence(scenarioNameField, binder, SCENARIO_NAME);
        validateScenarioNameExistence(scenarioNameField, binder, ' ' + SCENARIO_NAME + ' ');
        verify(controller);
    }

    @Test
    public void testConfirmButtonClickListener() {
        Scenario scenario = new Scenario();
        UsageBatch usageBatch = buildUsageBatch(7001293454L);
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        expect(controller.getSelectedUsageBatch()).andReturn(usageBatch).once();
        expect(controller.getUsageDataGradeGroups()).andReturn(List.of(SalGradeGroupEnum.GRADE6_8)).once();
        expect(controller.createSalScenario(SCENARIO_NAME, FUND_POOL_ID, StringUtils.EMPTY)).andReturn(scenario).once();
        replay(controller, filterController);
        TestCreateSalScenarioWindow createScenarioWindow = new TestCreateSalScenarioWindow(controller);
        applyConfirmCreateScenarioAction(createScenarioWindow);
        EventObject event = createScenarioWindow.getEventObject();
        assertNotNull(event);
        assertThat(event, instanceOf(ScenarioCreateEvent.class));
        ScenarioCreateEvent scenarioCreateEvent = (ScenarioCreateEvent) event;
        assertEquals(scenario, scenarioCreateEvent.getScenarioId());
        assertEquals(createScenarioWindow, scenarioCreateEvent.getSource());
        assertTrue(createScenarioWindow.isClosed());
        verify(controller, filterController);
    }

    @Test
    public void testConfirmButtonClickListenerInvalidLicensee() {
        mockStatic(Windows.class);
        UsageBatch usageBatch = buildUsageBatch(3333L);
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        expect(controller.getSelectedUsageBatch()).andReturn(usageBatch).once();
        Windows.showNotificationWindow("Licensee Account # of usage batch and selected fund pool should match:" +
            "<ul><li><i><b>Usage Batch Licensee: 3333</b></i></ul>" +
            "<ul><li><i><b>Fund Pool Licensee: 7001293454</b></i></ul>");
        expectLastCall().once();
        replay(controller, filterController, Windows.class);
        applyConfirmCreateScenarioAction(new TestCreateSalScenarioWindow(controller));
        verify(controller, filterController, Windows.class);
    }

    @Test
    public void testConfirmButtonClickListenerInvalidGradeDetails() {
        mockStatic(Windows.class);
        UsageBatch usageBatch = buildUsageBatch(7001293454L);
        fundPool.getSalFields().setGradeKto5GrossAmount(BigDecimal.TEN);
        fundPool.getSalFields().setGrade9to12GrossAmount(BigDecimal.TEN);
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        expect(controller.getSelectedUsageBatch()).andReturn(usageBatch).once();
        expect(controller.getUsageDataGradeGroups()).andReturn(List.of(SalGradeGroupEnum.GRADE6_8)).once();
        Windows.showNotificationWindow("There are no usage details for GRADEK_5, GRADE9_12 grade group(s)");
        expectLastCall().once();
        replay(controller, filterController, Windows.class);
        applyConfirmCreateScenarioAction(new TestCreateSalScenarioWindow(controller));
        verify(controller, filterController, Windows.class);
    }

    @Test
    public void testConfirmButtonClickListenerInvalidGradeAmounts() {
        mockStatic(Windows.class);
        UsageBatch usageBatch = buildUsageBatch(7001293454L);
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        expect(controller.getSelectedUsageBatch()).andReturn(usageBatch).once();
        expect(controller.getUsageDataGradeGroups()).andReturn(
            Arrays.asList(SalGradeGroupEnum.GRADEK_5, SalGradeGroupEnum.GRADE6_8, SalGradeGroupEnum.GRADE9_12)).once();
        Windows.showNotificationWindow("Gross amount for GRADEK_5, GRADE9_12 grade group(s) should be greater than 0");
        expectLastCall().once();
        replay(controller, filterController, Windows.class);
        applyConfirmCreateScenarioAction(new TestCreateSalScenarioWindow(controller));
        verify(controller, filterController, Windows.class);
    }

    @Test
    public void testScenarioNameFieldValidation() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(3);
        replay(controller);
        window = new CreateSalScenarioWindow(controller);
        TextField scenarioName = Whitebox.getInternalState(window, "scenarioNameField");
        Binder binder = Whitebox.getInternalState(window, "scenarioBinder");
        String emptyFieldValidationMessage = "Field value should be specified";
        validateFieldAndVerifyErrorMessage(scenarioName, StringUtils.EMPTY, binder, emptyFieldValidationMessage, false);
        validateFieldAndVerifyErrorMessage(scenarioName, "   ", binder, emptyFieldValidationMessage, false);
        validateFieldAndVerifyErrorMessage(scenarioName, StringUtils.repeat('a', 51), binder,
            "Field value should not exceed 50 characters", false);
        validateFieldAndVerifyErrorMessage(scenarioName, SCENARIO_NAME, binder, null, true);
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
        assertEquals("Scenario Name", scenarioNameField.getCaption());
        assertEquals(SCENARIO_NAME, scenarioNameField.getValue());
        assertEquals("scenario-name", scenarioNameField.getId());
    }

    private void verifyDescriptionArea(Component component) {
        assertNotNull(component);
        TextArea descriptionArea = (TextArea) component;
        assertEquals("Description", descriptionArea.getCaption());
        assertEquals("scenario-description", descriptionArea.getId());
    }

    private UsageBatch buildUsageBatch(Long licenseeAccountNumber) {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId("b01931c9-2d76-44d5-a1a8-603a4d3bba0d");
        UsageBatch.SalFields salFields = new UsageBatch.SalFields();
        salFields.setLicenseeAccountNumber(licenseeAccountNumber);
        usageBatch.setSalFields(salFields);
        return usageBatch;
    }

    private void applyConfirmCreateScenarioAction(TestCreateSalScenarioWindow createScenarioWindow) {
        VerticalLayout content = (VerticalLayout) createScenarioWindow.getContent();
        ComboBox<FundPool> fundPoolComboBox = (ComboBox<FundPool>) content.getComponent(1);
        fundPoolComboBox.setSelectedItem(fundPool);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(3);
        Button confirmButton = (Button) buttonsLayout.getComponent(0);
        ClickListener listener = (ClickListener) confirmButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(createScenarioWindow));
    }

    private static class TestCreateSalScenarioWindow extends CreateSalScenarioWindow {

        private EventObject eventObject;
        private boolean closed;

        TestCreateSalScenarioWindow(ISalUsageController controller) {
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
