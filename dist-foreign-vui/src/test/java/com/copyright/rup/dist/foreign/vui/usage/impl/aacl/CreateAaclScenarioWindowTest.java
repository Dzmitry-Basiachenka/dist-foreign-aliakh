package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.ScenarioParameterWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import com.vaadin.flow.data.binder.ValidationResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link CreateAaclScenarioWindow}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 03/11/2020
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*" })
public class CreateAaclScenarioWindowTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String SCENARIO_NAME = "AACL Distribution " + DATE;
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String FUND_POOL_ID = "eb9ce57e-af7e-4f57-b7cb-66db7e24b553";

    private FundPool fundPool;
    private UsageAge usageAge;
    private PublicationType publicationType;
    private DetailLicenseeClass detailLicenseeClass;
    private AggregateLicenseeClass aggregateLicenseeClass;
    private IAaclUsageController controller;
    private CreateAaclScenarioWindow window;

    @Before
    public void setUp() {
        controller = createMock(IAaclUsageController.class);
        fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        fundPool.setName("Fund Pool 1");
        usageAge = new UsageAge();
        usageAge.setPeriod(2019);
        usageAge.setWeight(BigDecimal.ONE);
        publicationType = new PublicationType();
        publicationType.setId("a6694d41-e2d5-4f4d-8dd5-3a0581d1d73e");
        publicationType.setWeight(BigDecimal.ONE);
        detailLicenseeClass = new DetailLicenseeClass();
        aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(143);
        aggregateLicenseeClass.setEnrollmentProfile("EXGP");
        aggregateLicenseeClass.setDiscipline("Life Sciences");
        detailLicenseeClass.setId(108);
        aggregateLicenseeClass.setEnrollmentProfile("EXGP");
        aggregateLicenseeClass.setDiscipline("Life Sciences");
        detailLicenseeClass.setAggregateLicenseeClass(aggregateLicenseeClass);
        expect(controller.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).anyTimes();
        expect(controller.getFundPoolsNotAttachedToScenario()).andReturn(List.of(fundPool)).once();
        expect(controller.getUsageAges()).andReturn(List.of(usageAge)).once();
        expect(controller.getPublicationTypes()).andReturn(List.of(publicationType)).once();
        expect(controller.getDetailLicenseeClasses()).andReturn(List.of(detailLicenseeClass)).once();
    }

    @Test
    public void testComponentStructure() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        window = new CreateAaclScenarioWindow(controller);
        verify(controller);
        verifyWindow(window, "Create Scenario", "500px", null, Unit.PIXELS, false);
        var content = (VerticalLayout) getDialogContent(window);
        assertEquals(6, content.getComponentCount());
        verifyScenarioNameField(content.getComponentAt(0));
        verifyComboBox(content.getComponentAt(1), "Fund Pool", false, fundPool);
        verifyScenarioParameterWidget(content.getComponentAt(2), "Usage Age Weights");
        verifyScenarioParameterWidget(content.getComponentAt(3), "Pub Type Weights");
        verifyScenarioParameterWidget(content.getComponentAt(4), "Licensee Class Mapping");
        verifyDescriptionArea(content.getComponentAt(5));
        verifyButtonsLayout(getFooterLayout(window), true, "Confirm", "Cancel");
    }

    @Test
    public void testConfirmButtonClickListener() {
        var usageAges = List.of(usageAge);
        var publicationTypes = List.of(publicationType);
        var detailLicenseeClasses = List.of(detailLicenseeClass);
        var expectedAaclFields = new Scenario.AaclFields();
        expectedAaclFields.setFundPoolId(FUND_POOL_ID);
        expectedAaclFields.setUsageAges(usageAges);
        expectedAaclFields.setPublicationTypes(publicationTypes);
        expectedAaclFields.setDetailLicenseeClasses(detailLicenseeClasses);
        var scenario = new Scenario();
        expect(controller.createAaclScenario(SCENARIO_NAME, expectedAaclFields, "")).andReturn(scenario).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        expect(controller.getAggregateClassesNotToBeDistributed(FUND_POOL_ID, detailLicenseeClasses))
            .andReturn(List.of()).once();
        replay(controller);
        var createScenarioWindow = new TestCreateAaclScenarioWindow(controller);
        var content = (VerticalLayout) getDialogContent(createScenarioWindow);
        var fundPoolComboBox = (ComboBox<FundPool>) content.getComponentAt(1);
        fundPoolComboBox.setValue(fundPool);
        var buttonsLayout = getFooterLayout(createScenarioWindow);
        var confirmButton = (Button) buttonsLayout.getComponentAt(0);
        confirmButton.click();
        var event = createScenarioWindow.getEventObject();
        assertNotNull(event);
        assertThat(event, instanceOf(ScenarioCreateEvent.class));
        var scenarioCreateEvent = (ScenarioCreateEvent) event;
        assertEquals(scenario, scenarioCreateEvent.getScenarioId());
        assertEquals(createScenarioWindow, scenarioCreateEvent.getSource());
        assertTrue(createScenarioWindow.isClosed());
        verify(controller);
    }

    @Test
    public void testConfirmButtonClickListenerWithInvalidMapping() {
        mockStatic(Windows.class);
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        expect(controller.getAggregateClassesNotToBeDistributed(FUND_POOL_ID, List.of(detailLicenseeClass)))
            .andReturn(List.of(aggregateLicenseeClass)).once();
        Windows.showNotificationWindow(
            "Scenario cannot be created. There are no usages for the following Aggregate Licensee Class(es):" +
                "<ul><li><i><b>143 (EXGP - Life Sciences)</b></i></ul>");
        expectLastCall().once();
        replay(controller, Windows.class);
        var createScenarioWindow = new TestCreateAaclScenarioWindow(controller);
        var content = (VerticalLayout) getDialogContent(createScenarioWindow);
        var fundPoolComboBox = (ComboBox<FundPool>) content.getComponentAt(1);
        fundPoolComboBox.setValue(fundPool);
        var buttonsLayout = getFooterLayout(createScenarioWindow);
        var confirmButton = (Button) buttonsLayout.getComponentAt(0);
        confirmButton.click();
        assertFalse(createScenarioWindow.isClosed());
        verify(controller, Windows.class);
    }

    @Test
    public void testButtonCloseClick() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        var createScenarioWindow = new TestCreateAaclScenarioWindow(controller);
        assertFalse(createScenarioWindow.isClosed());
        var buttonsLayout = getFooterLayout(createScenarioWindow);
        var cancelButton = (Button) buttonsLayout.getComponentAt(1);
        cancelButton.click();
        assertTrue(createScenarioWindow.isClosed());
        verify(controller);
    }

    @Test
    public void testScenarioNameExists() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(true).times(4);
        replay(controller);
        window = new CreateAaclScenarioWindow(controller);
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
        window = new CreateAaclScenarioWindow(controller);
        TextField scenarioName = Whitebox.getInternalState(window, "scenarioNameField");
        Binder binder = Whitebox.getInternalState(window, "scenarioBinder");
        var emptyFieldValidationMessage = "Field value should be specified";
        assertFieldValidationMessage(scenarioName, StringUtils.EMPTY, binder, emptyFieldValidationMessage, false);
        assertFieldValidationMessage(scenarioName, "   ", binder, emptyFieldValidationMessage, false);
        assertFieldValidationMessage(scenarioName, StringUtils.repeat('a', 51), binder,
            "Field value should not exceed 50 characters", false);
        assertFieldValidationMessage(scenarioName, SCENARIO_NAME, binder, null, true);
        verify(controller);
    }

    private void validateScenarioNameExistence(TextField scenarioNameField, Binder binder, String scenarioName) {
        scenarioNameField.setValue(scenarioName);
        var errorMessages = ((List<ValidationResult>) binder.validate().getValidationErrors())
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

    private void verifyScenarioParameterWidget(Component component, String expectedCaption) {
        assertNotNull(component);
        var widget = (ScenarioParameterWidget) component;
        assertEquals(expectedCaption, ((Button) widget.getComponentAt(0)).getText());
    }

    private void verifyDescriptionArea(Component component) {
        assertNotNull(component);
        var descriptionArea = (TextArea) component;
        assertEquals("Description", descriptionArea.getLabel());
        assertEquals("scenario-description", descriptionArea.getId().get());
    }

    private static class TestCreateAaclScenarioWindow extends CreateAaclScenarioWindow {

        private EventObject eventObject;
        private boolean closed;

        TestCreateAaclScenarioWindow(IAaclUsageController controller) {
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
