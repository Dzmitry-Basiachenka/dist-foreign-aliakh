package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyCheckBox;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.AclPublicationTypeWeightsParameterWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link CreateAclScenarioWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/24/2022
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class CreateAclScenarioWindowTest {

    private static final String DATE =
        CommonDateUtils.format(LocalDate.now(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String SCENARIO_ID = "36ea5499-85f0-48b8-ba50-52a4ed76574f";
    private static final String SCENARIO_NAME = "ACL Distribution " + DATE;
    private static final String LICENSE_TYPE = "ACL";
    private static final String ACL_BATCH_UID = "df36a701-8630-45aa-994d-35e3f019192a";
    private static final String DESCRIPTION = "Description";
    private static final String EDITABLE_CHECKBOX_FIELD_NAME = "editableCheckBox";

    private final List<UsageAge> usageAges = Collections.singletonList(buildUsageAge());
    private final List<AclPublicationType> publicationTypes =
        Collections.singletonList(buildAclPublicationType(201506));
    private final List<DetailLicenseeClass> detailLicenseeClasses =
        Collections.singletonList(buildAclDetailLicenseeClass());

    private IAclScenariosController controller;
    private CreateAclScenarioWindow window;
    private ClickListener createButtonClickListener;

    @Before
    public void setUp() {
        controller = createMock(IAclScenariosController.class);
        createButtonClickListener = createMock(ClickListener.class);
        mockStatic(ForeignSecurityUtils.class);
        expect(controller.getAllPeriods()).andReturn(Collections.singletonList(202206));
        expect(controller.getAclHistoricalPublicationTypes()).andReturn(publicationTypes).once();
        expect(controller.getDetailLicenseeClasses()).andReturn(detailLicenseeClasses).once();
        expect(controller.getUsageAgeWeights()).andReturn(usageAges).once();
        expect(controller.getScenarios()).andReturn(Collections.singletonList(buildAclScenario())).once();
    }

    @Test
    public void testComponentStructure() {
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true);
        replay(controller, ForeignSecurityUtils.class);
        window = new CreateAclScenarioWindow(controller, createButtonClickListener);
        assertEquals("Create Scenario", window.getCaption());
        assertEquals(400, window.getWidth(), 0);
        assertEquals("create-acl-scenario-window", window.getId());
        VerticalLayout content = (VerticalLayout) window.getContent();
        assertNotNull(content);
        assertTrue(content.isSpacing());
        assertEquals(13, content.getComponentCount());
        verifyScenarioNameField(content.getComponent(0));
        UiTestHelper.verifyComboBox(content.getComponent(1), "Period", true, 202206);
        UiTestHelper.verifyComboBox(content.getComponent(2), "License Type", true,
            ImmutableSet.of("ACL", "MACL", "VGW", "JACDCL"));
        verifyComboBox(content.getComponent(3), "Usage Batch");
        verifyComboBox(content.getComponent(4), "Fund Pool");
        verifyComboBox(content.getComponent(5), "Grant Set");
        verifyComboBox(content.getComponent(6), "Copy From");
        verifyAclScenarioParameterWidget(content.getComponent(7), "Usage Age Weights");
        verifyAclPublicationTypeWeightsParameterWidget(content.getComponent(8), "Pub Type Weights");
        verifyAclScenarioParameterWidget(content.getComponent(9), "Licensee Class Mapping");
        verifyCheckBox(content.getComponent(10), "Editable", "acl-scenario-editable-check-box");
        verifyDescriptionArea(content.getComponent(11));
        verifyButtonsLayout(content.getComponent(12), "Confirm", "Cancel");
        verify(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testScenarioNameFieldValidation() {
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).times(3);
        replay(controller);
        window = new CreateAclScenarioWindow(controller, createButtonClickListener);
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

    @Test
    public void testScenarioNameExists() {
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(true).times(4);
        replay(controller);
        window = new CreateAclScenarioWindow(controller, createButtonClickListener);
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        Binder binder = Whitebox.getInternalState(window, "scenarioBinder");
        validateScenarioNameExistence(scenarioNameField, binder, SCENARIO_NAME);
        validateScenarioNameExistence(scenarioNameField, binder, ' ' + SCENARIO_NAME + ' ');
        verify(controller);
    }

    @Test
    public void testConfirmButtonClickListener() {
        AclUsageBatch aclUsageBatch = buildAclUsageBatch();
        AclFundPool aclFundPool = buildAclFundPool();
        AclGrantSet aclGrantSet = buildAclGrantSet();
        AclScenario expectedScenario = new AclScenario();
        expectedScenario.setUsageBatchId(aclUsageBatch.getId());
        expectedScenario.setFundPoolId(aclFundPool.getId());
        expectedScenario.setGrantSetId(aclGrantSet.getId());
        expectedScenario.setName(SCENARIO_NAME);
        expectedScenario.setPeriodEndDate(202206);
        expectedScenario.setEditableFlag(true);
        expectedScenario.setDescription(DESCRIPTION);
        expectedScenario.setLicenseType(LICENSE_TYPE);
        expectedScenario.setPublicationTypes(publicationTypes);
        expectedScenario.setDetailLicenseeClasses(detailLicenseeClasses);
        expectedScenario.setUsageAges(usageAges);
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).times(4);
        expect(controller.getUsageBatchesByPeriod(202206, true)).andReturn(Collections.singletonList(aclUsageBatch))
            .once();
        expect(controller.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206))
            .andReturn(Collections.singletonList(aclFundPool));
        expect(controller.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206, true))
            .andReturn(Collections.singletonList(aclGrantSet));
        expect(controller.isValidUsageBatch(ACL_BATCH_UID, aclGrantSet.getId(), 202206,
            Collections.singletonList(1))).andReturn(true);
        expect(controller.getFundPoolDetailsNotToBeDistributed(ACL_BATCH_UID, aclFundPool.getId(), aclGrantSet.getId(),
            detailLicenseeClasses)).andReturn(Collections.emptySet()).once();
        controller.createAclScenario(expectedScenario);
        expectLastCall().once();
        replay(controller);
        populateCreateScenarioWindowAndClickConfirmButton(aclUsageBatch, aclFundPool, aclGrantSet);
        verify(controller);
    }

    @Test
    public void testNotificationMessageOnConfirmButtonClick() {
        mockStatic(Windows.class);
        AclUsageBatch aclUsageBatch = buildAclUsageBatch();
        AclFundPool aclFundPool = buildAclFundPool();
        AclGrantSet aclGrantSet = buildAclGrantSet();
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).times(2);
        expect(controller.getUsageBatchesByPeriod(202206, true)).andReturn(Collections.singletonList(aclUsageBatch))
            .once();
        expect(controller.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206))
            .andReturn(Collections.singletonList(aclFundPool));
        expect(controller.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206, true))
            .andReturn(Collections.singletonList(aclGrantSet));
        expect(controller.isValidUsageBatch(ACL_BATCH_UID, "f5e558ce-2261-4998-8434-fc04d432c1a5", 202206,
            Collections.singletonList(1))).andReturn(false);
        Windows.showNotificationWindow(
            "System found usages missing Pub Type and/or CUP. Please update the missing data");
        expectLastCall().once();
        replay(controller, Windows.class);
        populateCreateScenarioWindowAndClickConfirmButton(aclUsageBatch, aclFundPool, aclGrantSet);
        verify(controller, Windows.class);
    }

    @Test
    public void testConfirmButtonClickListenerWithInvalidFundPoolDetails() {
        mockStatic(Windows.class);
        AclUsageBatch aclUsageBatch = buildAclUsageBatch();
        AclFundPool aclFundPool = buildAclFundPool();
        AclGrantSet aclGrantSet = buildAclGrantSet();
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).times(4);
        expect(controller.getUsageBatchesByPeriod(202206, true)).andReturn(Collections.singletonList(aclUsageBatch))
            .once();
        expect(controller.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206))
            .andReturn(Collections.singletonList(aclFundPool));
        expect(controller.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206, true))
            .andReturn(Collections.singletonList(aclGrantSet));
        expect(controller.isValidUsageBatch(ACL_BATCH_UID, aclGrantSet.getId(), 202206,
            Collections.singletonList(1))).andReturn(true);
        LinkedHashSet<AclFundPoolDetailDto> invalidDetails = Sets.newLinkedHashSet();
        invalidDetails.add(buildAclFundPoolDetailDto(1, 1, "Food and Tobacco", "PRINT"));
        invalidDetails.add(buildAclFundPoolDetailDto(2, 51, "Materials", "DIGITAL"));
        expect(controller.getFundPoolDetailsNotToBeDistributed(ACL_BATCH_UID, aclFundPool.getId(), aclGrantSet.getId(),
            detailLicenseeClasses)).andReturn(invalidDetails).once();
        Windows.showNotificationWindow(
            "Scenario cannot be created. There are no usages for the following Aggregate Licensee Class(es):" +
                "<ul><li><i><b>1 - Food and Tobacco (PRINT)<br><li>51 - Materials (DIGITAL)</b></i></ul>");
        expectLastCall().once();
        replay(controller, Windows.class);
        populateCreateScenarioWindowAndClickConfirmButton(aclUsageBatch, aclFundPool, aclGrantSet);
        verify(controller, Windows.class);
    }

    @Test
    public void testEditableCheckBoxUnchecked() {
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).once();
        expect(controller.getAclHistoricalPublicationTypes()).andReturn(publicationTypes).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true);
        replay(controller, ForeignSecurityUtils.class);
        window = new CreateAclScenarioWindow(controller, createButtonClickListener);
        CheckBox editableCheckBox = Whitebox.getInternalState(window, EDITABLE_CHECKBOX_FIELD_NAME);
        AclPublicationTypeWeightsParameterWidget pubTypeWeightWidget =
            Whitebox.getInternalState(window, "publicationTypeWeightWidget");
        assertTrue(editableCheckBox.getValue());
        assertEquals(publicationTypes, pubTypeWeightWidget.getAppliedParameters());
        List<AclPublicationType> pubTypes = new ArrayList<>(pubTypeWeightWidget.getAppliedParameters());
        pubTypes.add(buildAclPublicationType(202206));
        pubTypeWeightWidget.setAppliedParameters(pubTypes);
        assertEquals(Arrays.asList(buildAclPublicationType(201506), buildAclPublicationType(202206)),
            pubTypeWeightWidget.getAppliedParameters());
        editableCheckBox.setValue(false);
        assertEquals(publicationTypes, pubTypeWeightWidget.getAppliedParameters());
        verify(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testEditableCheckBoxEnabledForSpecialist() {
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true);
        expect(controller.getUsageBatchesByPeriod(202206, true)).andReturn(new ArrayList<>()).times(2);
        expect(controller.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206)).andReturn(new ArrayList<>())
            .times(2);
        expect(controller.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206, true)).andReturn(new ArrayList<>())
            .times(2);
        expect(controller.getScenarioById(SCENARIO_ID)).andReturn(buildAclScenario()).once();
        replay(controller, ForeignSecurityUtils.class);
        window = new CreateAclScenarioWindow(controller, createButtonClickListener);
        CheckBox editableCheckBox = Whitebox.getInternalState(window, EDITABLE_CHECKBOX_FIELD_NAME);
        ComboBox<AclScenario> copyFromComboBox = Whitebox.getInternalState(window, "aclCopyFromScenarioComboBox");
        copyFromComboBox.setSelectedItem(buildAclScenario());
        assertFalse(editableCheckBox.isEnabled());
        copyFromComboBox.setSelectedItem(null);
        assertTrue(editableCheckBox.isEnabled());
        verify(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testEditableCheckBoxDisabledForManager() {
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(false);
        expect(controller.getUsageBatchesByPeriod(202206, true)).andReturn(new ArrayList<>()).times(2);
        expect(controller.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206)).andReturn(new ArrayList<>())
            .times(2);
        expect(controller.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206, true)).andReturn(new ArrayList<>())
            .times(2);
        expect(controller.getScenarioById(SCENARIO_ID)).andReturn(buildAclScenario()).once();
        replay(controller, ForeignSecurityUtils.class);
        window = new CreateAclScenarioWindow(controller, createButtonClickListener);
        CheckBox editableCheckBox = Whitebox.getInternalState(window, EDITABLE_CHECKBOX_FIELD_NAME);
        ComboBox<Integer> periodComboBox = Whitebox.getInternalState(window, "periodComboBox");
        periodComboBox.setSelectedItem(202206);
        assertFalse(editableCheckBox.isEnabled());
        ComboBox<String> licenseTypeComboBox = Whitebox.getInternalState(window, "licenseTypeComboBox");
        licenseTypeComboBox.setSelectedItem("ACL");
        assertFalse(editableCheckBox.isEnabled());
        ComboBox<AclScenario> copyFromComboBox = Whitebox.getInternalState(window, "aclCopyFromScenarioComboBox");
        copyFromComboBox.setSelectedItem(buildAclScenario());
        assertFalse(editableCheckBox.isEnabled());
        copyFromComboBox.setSelectedItem(null);
        assertFalse(editableCheckBox.isEnabled());
        verify(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testFillInFieldsFromCopyScenario() {
        AclScenario aclScenario = buildAclScenario();
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).once();
        expect(controller.getUsageBatchesByPeriod(202206, true))
            .andReturn(Collections.singletonList(buildAclUsageBatch())).times(2);
        expect(controller.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206))
            .andReturn(Collections.singletonList(buildAclFundPool())).times(2);
        expect(controller.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206, true))
            .andReturn(Collections.singletonList(buildAclGrantSet())).times(2);
        expect(controller.getScenarioById(SCENARIO_ID)).andReturn(aclScenario).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true);
        replay(controller, ForeignSecurityUtils.class);
        window = new CreateAclScenarioWindow(controller, createButtonClickListener);
        ComboBox<AclScenario> copyFromComboBox = Whitebox.getInternalState(window, "aclCopyFromScenarioComboBox");
        copyFromComboBox.setValue(aclScenario);
        verifyCreateScenarioWindowFields();
        verify(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testResetUsageBatchFundPoolGrantSetAfterChangeEditableBox() {
        expect(controller.aclScenarioExists(SCENARIO_NAME)).andReturn(false).once();
        expect(controller.getAclHistoricalPublicationTypes()).andReturn(publicationTypes).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(false);
        expect(controller.getUsageBatchesByPeriod(202206, true))
            .andReturn(Collections.singletonList(buildAclUsageBatch())).times(2);
        expect(controller.getUsageBatchesByPeriod(202206, false))
            .andReturn(Collections.singletonList(buildAclUsageBatch())).once();
        expect(controller.getFundPoolsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206))
            .andReturn(Collections.singletonList(buildAclFundPool())).times(3);
        expect(controller.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206, true))
            .andReturn(Collections.singletonList(buildAclGrantSet())).times(2);
        expect(controller.getGrantSetsByLicenseTypeAndPeriod(LICENSE_TYPE, 202206, false))
            .andReturn(Collections.singletonList(buildAclGrantSet())).once();
        replay(controller, ForeignSecurityUtils.class);
        window = new CreateAclScenarioWindow(controller, createButtonClickListener);
        ComboBox<Integer> periodComboBox = Whitebox.getInternalState(window, "periodComboBox");
        ComboBox<String> licenseTypeComboBox = Whitebox.getInternalState(window, "licenseTypeComboBox");
        CheckBox editableCheckBox = Whitebox.getInternalState(window, EDITABLE_CHECKBOX_FIELD_NAME);
        ComboBox<AclUsageBatch> usageBatchComboBox = Whitebox.getInternalState(window, "usageBatchComboBox");
        ComboBox<AclFundPool> fundPoolComboBox = Whitebox.getInternalState(window, "fundPoolComboBox");
        ComboBox<AclGrantSet> grantSetComboBox = Whitebox.getInternalState(window, "grantSetComboBox");
        periodComboBox.setSelectedItem(202206);
        licenseTypeComboBox.setSelectedItem(LICENSE_TYPE);
        assertTrue(editableCheckBox.getValue());
        usageBatchComboBox.setSelectedItem(buildAclUsageBatch());
        fundPoolComboBox.setSelectedItem(buildAclFundPool());
        grantSetComboBox.setSelectedItem(buildAclGrantSet());
        editableCheckBox.setValue(false);
        assertNull(usageBatchComboBox.getValue());
        assertNull(fundPoolComboBox.getValue());
        assertNull(grantSetComboBox.getValue());
        usageBatchComboBox.setSelectedItem(buildAclUsageBatch());
        fundPoolComboBox.setSelectedItem(buildAclFundPool());
        grantSetComboBox.setSelectedItem(buildAclGrantSet());
        editableCheckBox.setValue(true);
        assertNull(usageBatchComboBox.getValue());
        assertNull(fundPoolComboBox.getValue());
        assertNull(grantSetComboBox.getValue());
        verify(controller, ForeignSecurityUtils.class);
    }

    private void verifyCreateScenarioWindowFields() {
        TextField scenarioNameField = Whitebox.getInternalState(window, "scenarioNameField");
        assertEquals(SCENARIO_NAME, scenarioNameField.getValue());
        ComboBox<Integer> periodComboBox = Whitebox.getInternalState(window, "periodComboBox");
        assertEquals(Integer.valueOf(202206), periodComboBox.getValue());
        assertFalse(periodComboBox.isEnabled());
        ComboBox<String> licenseTypeComboBox = Whitebox.getInternalState(window, "licenseTypeComboBox");
        assertEquals(LICENSE_TYPE, licenseTypeComboBox.getValue());
        assertFalse(licenseTypeComboBox.isEnabled());
        ComboBox<AclUsageBatch> usageBatchComboBox = Whitebox.getInternalState(window, "usageBatchComboBox");
        assertEquals(buildAclUsageBatch(), usageBatchComboBox.getValue());
        ComboBox<AclFundPool> fundPoolComboBox = Whitebox.getInternalState(window, "fundPoolComboBox");
        assertEquals(buildAclFundPool(), fundPoolComboBox.getValue());
        ComboBox<AclGrantSet> grantSetComboBox = Whitebox.getInternalState(window, "grantSetComboBox");
        assertEquals(buildAclGrantSet(), grantSetComboBox.getValue());
        CheckBox editableCheckBox = Whitebox.getInternalState(window, EDITABLE_CHECKBOX_FIELD_NAME);
        assertTrue(editableCheckBox.getValue());
        assertFalse(editableCheckBox.isEnabled());
        TextArea descriptionArea = Whitebox.getInternalState(window, "descriptionArea");
        assertEquals(DESCRIPTION, descriptionArea.getValue());
        AclPublicationTypeWeightsParameterWidget pubTypeWeightWidget =
            Whitebox.getInternalState(window, "publicationTypeWeightWidget");
        assertEquals(publicationTypes, pubTypeWeightWidget.getAppliedParameters());
        ScenarioParameterWidget licenseeClassMappingWidget =
            Whitebox.getInternalState(window, "licenseeClassMappingWidget");
        assertEquals(detailLicenseeClasses, licenseeClassMappingWidget.getAppliedParameters());
        ScenarioParameterWidget usageAgeWeightWidget = Whitebox.getInternalState(window, "usageAgeWeightWidget");
        assertEquals(usageAges, usageAgeWeightWidget.getAppliedParameters());
    }

    private void verifyScenarioNameField(Component component) {
        assertNotNull(component);
        TextField scenarioNameField = (TextField) component;
        assertEquals("Scenario Name", scenarioNameField.getCaption());
        assertEquals(SCENARIO_NAME, scenarioNameField.getValue());
        assertEquals("acl-scenario-name", scenarioNameField.getId());
    }

    private <T> void verifyComboBox(Component component, String caption) {
        assertThat(component, instanceOf(ComboBox.class));
        ComboBox<T> comboBox = (ComboBox<T>) component;
        assertFalse(comboBox.isReadOnly());
        assertTrue(comboBox.isTextInputAllowed());
        assertEquals(caption, comboBox.getCaption());
        assertEquals(100, comboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, comboBox.getWidthUnits());
    }

    private void verifyAclPublicationTypeWeightsParameterWidget(Component component, String expectedCaption) {
        assertNotNull(component);
        AclPublicationTypeWeightsParameterWidget widget = (AclPublicationTypeWeightsParameterWidget) component;
        assertEquals(expectedCaption, widget.getComponent(0).getCaption());
    }

    private void verifyAclScenarioParameterWidget(Component component, String expectedCaption) {
        assertNotNull(component);
        ScenarioParameterWidget widget = (ScenarioParameterWidget) component;
        assertEquals(expectedCaption, widget.getComponent(0).getCaption());
    }

    private void verifyDescriptionArea(Component component) {
        assertNotNull(component);
        TextArea descriptionArea = (TextArea) component;
        assertEquals(DESCRIPTION, descriptionArea.getCaption());
        assertEquals("scenario-description", descriptionArea.getId());
    }

    private void validateScenarioNameExistence(TextField scenarioNameField, Binder binder, String scenarioName) {
        scenarioNameField.setValue(scenarioName);
        List<String> errorMessages = ((List<ValidationResult>) binder.validate().getValidationErrors())
            .stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(3, errorMessages.size());
        assertEquals("ACL Scenario with such name already exists", errorMessages.get(0));
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setId(ACL_BATCH_UID);
        usageBatch.setName("Usage Batch");
        return usageBatch;
    }

    private AclFundPool buildAclFundPool() {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setId("304795bf-9bd1-4377-9b5f-ce247d88f8b2");
        aclFundPool.setName("Fund Pool");
        return aclFundPool;
    }

    private AclFundPoolDetailDto buildAclFundPoolDetailDto(Integer detailClassId, Integer aggregateClassId,
                                                           String aggregateClassName, String typeOfUse) {
        AclFundPoolDetailDto detail = new AclFundPoolDetailDto();
        detail.getDetailLicenseeClass().setId(detailClassId);
        detail.getAggregateLicenseeClass().setId(aggregateClassId);
        detail.getAggregateLicenseeClass().setDescription(aggregateClassName);
        detail.setTypeOfUse(typeOfUse);
        return detail;
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setId("f5e558ce-2261-4998-8434-fc04d432c1a5");
        aclGrantSet.setName("Grant Set");
        return aclGrantSet;
    }

    private UsageAge buildUsageAge() {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(1);
        usageAge.setWeight(new BigDecimal("0.75"));
        return usageAge;
    }

    private AclPublicationType buildAclPublicationType(int period) {
        AclPublicationType publicationType = new AclPublicationType();
        publicationType.setId("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e");
        publicationType.setName("Book");
        publicationType.setWeight(new BigDecimal("1.00"));
        publicationType.setPeriod(period);
        return publicationType;
    }

    private DetailLicenseeClass buildAclDetailLicenseeClass() {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        detailLicenseeClass.setAggregateLicenseeClass(aggregateLicenseeClass);
        return detailLicenseeClass;
    }

    @SuppressWarnings("unchecked")
    private void populateCreateScenarioWindowAndClickConfirmButton(AclUsageBatch aclUsageBatch,
                                                                   AclFundPool aclFundPool,
                                                                   AclGrantSet aclGrantSet) {
        window = new CreateAclScenarioWindow(controller, createButtonClickListener);
        VerticalLayout content = (VerticalLayout) window.getContent();
        ComboBox<Integer> periodComboBox = (ComboBox<Integer>) content.getComponent(1);
        periodComboBox.setSelectedItem(202206);
        ComboBox<String> licenseTypeComboBox = (ComboBox<String>) content.getComponent(2);
        licenseTypeComboBox.setSelectedItem(LICENSE_TYPE);
        ComboBox<AclUsageBatch> usageBatchComboBox = (ComboBox<AclUsageBatch>) content.getComponent(3);
        usageBatchComboBox.setSelectedItem(aclUsageBatch);
        ComboBox<AclFundPool> fundPoolComboBox = (ComboBox<AclFundPool>) content.getComponent(4);
        fundPoolComboBox.setSelectedItem(aclFundPool);
        ComboBox<AclGrantSet> grantSetComboBox = (ComboBox<AclGrantSet>) content.getComponent(5);
        grantSetComboBox.setSelectedItem(aclGrantSet);
        TextArea textField = (TextArea) content.getComponent(11);
        textField.setValue(DESCRIPTION);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(12);
        Button confirmButton = (Button) buttonsLayout.getComponent(0);
        ClickListener listener = (ClickListener) confirmButton.getListeners(ClickEvent.class).iterator().next();
        listener.buttonClick(new ClickEvent(window));
    }

    private AclScenario buildAclScenario() {
        AclScenario scenario = new AclScenario();
        scenario.setId(SCENARIO_ID);
        scenario.setFundPoolId("304795bf-9bd1-4377-9b5f-ce247d88f8b2");
        scenario.setUsageBatchId(ACL_BATCH_UID);
        scenario.setGrantSetId("f5e558ce-2261-4998-8434-fc04d432c1a5");
        scenario.setName("ACL Scenario 202206");
        scenario.setDescription(DESCRIPTION);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setEditableFlag(true);
        scenario.setPeriodEndDate(202206);
        scenario.setLicenseType(LICENSE_TYPE);
        scenario.setCreateUser("username");
        scenario.setUpdateUser("username");
        scenario.setDetailLicenseeClasses(Collections.singletonList(buildAclDetailLicenseeClass()));
        scenario.setPublicationTypes(Collections.singletonList(
            buildAclPublicationType(201506)));
        scenario.setUsageAges(Collections.singletonList(buildUsageAge()));
        return scenario;
    }
}
