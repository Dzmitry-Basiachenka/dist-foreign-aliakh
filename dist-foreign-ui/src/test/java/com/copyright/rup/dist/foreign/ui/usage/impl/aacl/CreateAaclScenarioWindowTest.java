package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static org.easymock.EasyMock.expect;
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
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
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
@PrepareForTest({Windows.class})
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
        expect(controller.getFundPoolsNotAttachedToScenario()).andReturn(Collections.singletonList(fundPool)).once();
        expect(controller.getUsageAges()).andReturn(Collections.singletonList(usageAge)).once();
        expect(controller.getPublicationTypes()).andReturn(Collections.singletonList(publicationType)).once();
        expect(controller.getDetailLicenseeClasses()).andReturn(Collections.singletonList(detailLicenseeClass)).once();
    }

    @Test
    public void testComponentStructure() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        window = new CreateAaclScenarioWindow(controller);
        verify(controller);
        assertEquals("Create Scenario", window.getCaption());
        assertEquals(300, window.getWidth(), 0);
        assertEquals("create-scenario-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(7, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        verifyFundPoolComboBox(content.getComponent(1), fundPool);
        verifyScenarioParameterWidget(content.getComponent(2), "Usage Age Weights");
        verifyScenarioParameterWidget(content.getComponent(3), "Pub Type Weights");
        verifyScenarioParameterWidget(content.getComponent(4), "Licensee Class Mapping");
        verifyDescriptionArea(content.getComponent(5));
        verifyButtonsLayout(content.getComponent(6));
    }

    @Test
    public void testConfirmButtonClickListener() {
        List<UsageAge> usageAges = Collections.singletonList(usageAge);
        List<PublicationType> publicationTypes = Collections.singletonList(publicationType);
        List<DetailLicenseeClass> detailLicenseeClasses = Collections.singletonList(detailLicenseeClass);
        AaclFields expectedAaclFields = new AaclFields();
        expectedAaclFields.setFundPoolId(FUND_POOL_ID);
        expectedAaclFields.setUsageAges(usageAges);
        expectedAaclFields.setPublicationTypes(publicationTypes);
        expectedAaclFields.setDetailLicenseeClasses(detailLicenseeClasses);
        Scenario scenario = new Scenario();
        expect(controller.createAaclScenario(SCENARIO_NAME, expectedAaclFields, "")).andReturn(scenario).once();
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        expect(controller.getAggregateClassesNotToBeDistributed(FUND_POOL_ID, detailLicenseeClasses))
            .andReturn(Collections.emptyList()).once();
        replay(controller);
        TestCreateAaclScenarioWindow createScenarioWindow = new TestCreateAaclScenarioWindow(controller);
        VerticalLayout content = (VerticalLayout) createScenarioWindow.getContent();
        ComboBox<FundPool> fundPoolComboBox = (ComboBox<FundPool>) content.getComponent(1);
        fundPoolComboBox.setSelectedItem(fundPool);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(6);
        Button confirmButton = (Button) buttonsLayout.getComponent(0);
        ClickListener listener = (ClickListener) confirmButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(createScenarioWindow));
        EventObject event = createScenarioWindow.getEventObject();
        assertNotNull(event);
        assertTrue(event instanceof ScenarioCreateEvent);
        ScenarioCreateEvent scenarioCreateEvent = (ScenarioCreateEvent) event;
        assertEquals(scenario, scenarioCreateEvent.getScenarioId());
        assertEquals(createScenarioWindow, scenarioCreateEvent.getSource());
        assertTrue(createScenarioWindow.isClosed());
        verify(controller);
    }

    @Test
    public void testConfirmButtonClickListenerWithInvalidMapping() {
        mockStatic(Windows.class);
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        expect(controller.getAggregateClassesNotToBeDistributed(FUND_POOL_ID,
            Collections.singletonList(detailLicenseeClass)))
            .andReturn(Collections.singletonList(aggregateLicenseeClass)).once();
        Windows.showNotificationWindow(
            "Scenario cannot be created. There are no usages for the following Aggregate Licensee Class(es):" +
                "<ul><li><i><b>143 (EXGP - Life Sciences)</b></i></ul>");
        expectLastCall().once();
        replay(controller, Windows.class);
        TestCreateAaclScenarioWindow createScenarioWindow = new TestCreateAaclScenarioWindow(controller);
        VerticalLayout content = (VerticalLayout) createScenarioWindow.getContent();
        ComboBox<FundPool> fundPoolComboBox = (ComboBox<FundPool>) content.getComponent(1);
        fundPoolComboBox.setSelectedItem(fundPool);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(6);
        Button confirmButton = (Button) buttonsLayout.getComponent(0);
        ClickListener listener = (ClickListener) confirmButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(createScenarioWindow));
        assertFalse(createScenarioWindow.isClosed());
        verify(controller, Windows.class);
    }

    @Test
    public void testButtonCloseClick() {
        expect(controller.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(controller);
        TestCreateAaclScenarioWindow createScenarioWindow = new TestCreateAaclScenarioWindow(controller);
        assertFalse(createScenarioWindow.isClosed());
        VerticalLayout content = (VerticalLayout) createScenarioWindow.getContent();
        Component component = content.getComponent(6);
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        Button cancelButton = (Button) buttonsLayout.getComponent(1);
        ClickListener listener = (ClickListener) cancelButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(createScenarioWindow));
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

    private void verifyFundPoolComboBox(Component component, FundPool expectedFundPool) {
        assertNotNull(component);
        ComboBox<FundPool> fundPoolComboBox = (ComboBox<FundPool>) component;
        assertEquals("Fund Pool", fundPoolComboBox.getCaption());
        ListDataProvider<FundPool> listDataProvider = (ListDataProvider<FundPool>) fundPoolComboBox.getDataProvider();
        Collection<?> actualFundPool = listDataProvider.getItems();
        assertEquals(1, actualFundPool.size());
        assertEquals(expectedFundPool.getName(), fundPoolComboBox.getItemCaptionGenerator().apply(expectedFundPool));
    }

    private void verifyScenarioParameterWidget(Component component, String expectedCaption) {
        assertNotNull(component);
        AaclScenarioParameterWidget widget = (AaclScenarioParameterWidget) component;
        assertEquals(expectedCaption, widget.getComponent(0).getCaption());
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
        protected void fireEvent(EventObject event) {
            this.eventObject = event;
        }

        @Override
        public void close() {
            this.closed = true;
        }
    }
}
