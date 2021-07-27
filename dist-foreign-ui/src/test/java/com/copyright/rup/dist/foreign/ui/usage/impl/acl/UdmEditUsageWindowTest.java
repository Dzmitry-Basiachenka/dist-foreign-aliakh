package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.CompanyInformation;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
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
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link UdmEditUsageWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/30/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class UdmEditUsageWindowTest {

    private static final String UDM_USAGE_UID = "75b110ff-c6c9-45e6-baac-34041ff62081";
    private static final String UDM_USAGE_ORIGINAL_DETAIL_UID = "OGN674GHHSB0025";
    private static final String REPORTED_STANDARD_NUMBER = "0927-7765";
    private static final String STANDARD_NUMBER = "2192-3558";
    private static final String REPORTED_TYPE_OF_USE = "COPY_FOR_MYSELF";
    private static final String REPORTED_TITLE = "Colloids and surfaces. B, Biointerfaces";
    private static final String SYSTEM_TITLE = "Brain surgery";
    private static final String PUB_TYPE = "Journal";
    private static final String PUBLICATION_FORMAT = "Digital";
    private static final String ARTICLE = "Green chemistry";
    private static final String LANGUAGE = "English";
    private static final String SURVEY_COUNTRY = "United States";
    private static final LocalDate USAGE_DATE = LocalDate.of(2020, 12, 12);
    private static final LocalDate SURVEY_START_DATE = LocalDate.of(2019, 12, 12);
    private static final LocalDate SURVEY_END_DATE = LocalDate.of(2022, 12, 12);
    private static final String SURVEY_RESPONDENT = "fa0276c3-55d6-42cd-8ffe-e9124acae02f";
    private static final Long WR_WRK_INST = 122825347L;
    private static final Long RH_ACCOUNT_NUMBER = 1000023041L;
    private static final String RH_NAME = "American College of Physicians - Journals";
    private static final Long COMPANY_ID = 454984566L;
    private static final String COMPANY_NAME = "Skadden, Arps, Slate, Meagher & Flom LLP";
    private static final Integer DET_LC_ID = 26;
    private static final String DET_LC_NAME = "Law Firms";
    private static final Long QUANTITY = 10L;
    private static final Integer ANNUAL_MULTIPLIER = 1;
    private static final BigDecimal STATISTICAL_MULTIPLIER = new BigDecimal("1.00000");
    private static final BigDecimal ANNUALIZED_COPIES = new BigDecimal("10.00000");
    private static final String IP_ADDRESS = "ip24.12.119.203";
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final String USER_NAME = "user@copyright.com";
    private static final String COMMENT = "Should be reviewed by Specialist";
    private static final String RESEARCH_URL = "google.com";
    private static final DetailLicenseeClass LICENSEE_CLASS = new DetailLicenseeClass();
    private static final UdmActionReason ACTION_REASON =
        new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content");
    private static final UdmIneligibleReason INELIGIBLE_REASON =
        new UdmIneligibleReason("b60a726a-39e8-4303-abe1-6816da05b858", "Invalid survey");
    private static final String BINDER_NAME = "binder";
    private static final String VALID_INTEGER = "25";
    private static final String VALID_DECIMAL = "0.1";
    private static final String INVALID_NUMBER = "12a";
    private static final String INTEGER_WITH_SPACES_STRING = " 1 ";
    private static final String SPACES_STRING = "   ";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";

    private UdmEditUsageWindow window;
    private Binder<UdmUsageDto> binder;
    private IUdmUsageController controller;
    private UdmUsageDto udmUsage;
    private ClickListener saveButtonClickListener;

    @Before
    public void setUp() {
        buildUdmUsageDto();
        controller = createMock(IUdmUsageController.class);
        saveButtonClickListener = createMock(ClickListener.class);
        expect(controller.getAllActionReasons()).andReturn(Collections.singletonList(ACTION_REASON)).once();
        LICENSEE_CLASS.setId(DET_LC_ID);
        LICENSEE_CLASS.setDescription(DET_LC_NAME);
        expect(controller.getDetailLicenseeClasses()).andReturn(Collections.singletonList(LICENSEE_CLASS)).once();
        expect(controller.getAllIneligibleReasons()).andReturn(Collections.singletonList(INELIGIBLE_REASON)).once();
    }

    @Test
    public void testConstructor() {
        initEditWindow();
        assertEquals("Edit UDM Usage", window.getCaption());
        assertEquals(650, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(700, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testUdmUsageDataBinding() {
        initEditWindow();
        VerticalLayout verticalLayout = getPanelContent();
        assertTextFieldValue(verticalLayout.getComponent(0), UDM_USAGE_UID);
        assertTextFieldValue(verticalLayout.getComponent(1), "202012");
        assertTextFieldValue(verticalLayout.getComponent(2), "SS");
        assertTextFieldValue(verticalLayout.getComponent(3), UDM_USAGE_ORIGINAL_DETAIL_UID);
        assertComboBoxFieldValue(verticalLayout.getComponent(4), UsageStatusEnum.RH_FOUND);
        assertTextFieldValue(verticalLayout.getComponent(5), ASSIGNEE);
        assertTextFieldValue(verticalLayout.getComponent(6), "1000023041");
        assertTextFieldValue(verticalLayout.getComponent(7), RH_NAME);
        assertTextFieldValue(verticalLayout.getComponent(8), "122825347");
        assertTextFieldValue(verticalLayout.getComponent(9), REPORTED_TITLE);
        assertTextFieldValue(verticalLayout.getComponent(10), SYSTEM_TITLE);
        assertTextFieldValue(verticalLayout.getComponent(11), REPORTED_STANDARD_NUMBER);
        assertTextFieldValue(verticalLayout.getComponent(12), STANDARD_NUMBER);
        assertTextFieldValue(verticalLayout.getComponent(13), PUB_TYPE);
        assertTextFieldValue(verticalLayout.getComponent(14), PUBLICATION_FORMAT);
        assertTextFieldValue(verticalLayout.getComponent(15), ARTICLE);
        assertTextFieldValue(verticalLayout.getComponent(16), LANGUAGE);
        assertComboBoxFieldValue(verticalLayout.getComponent(17), ACTION_REASON);
        assertTextFieldValue(verticalLayout.getComponent(18), COMMENT);
        assertTextFieldValue(verticalLayout.getComponent(19), RESEARCH_URL);
        assertTextFieldValue(verticalLayout.getComponent(20), "454984566");
        assertTextFieldValue(verticalLayout.getComponent(21), COMPANY_NAME);
        assertComboBoxFieldValue(verticalLayout.getComponent(22), LICENSEE_CLASS);
        assertTextFieldValue(verticalLayout.getComponent(23), SURVEY_RESPONDENT);
        assertTextFieldValue(verticalLayout.getComponent(24), IP_ADDRESS);
        assertTextFieldValue(verticalLayout.getComponent(25), SURVEY_COUNTRY);
        assertTextFieldValue(verticalLayout.getComponent(26), "CCC");
        assertTextFieldValue(verticalLayout.getComponent(27), "12/12/2020");
        assertTextFieldValue(verticalLayout.getComponent(28), "12/12/2019");
        assertTextFieldValue(verticalLayout.getComponent(29), "12/12/2022");
        assertTextFieldValue(verticalLayout.getComponent(30), "1");
        assertTextFieldValue(verticalLayout.getComponent(31), "1.00000");
        assertTextFieldValue(verticalLayout.getComponent(32), REPORTED_TYPE_OF_USE);
        assertTextFieldValue(verticalLayout.getComponent(33), "10");
        assertTextFieldValue(verticalLayout.getComponent(34), "10.00000");
        assertComboBoxFieldValue(verticalLayout.getComponent(35), INELIGIBLE_REASON);
        assertTextFieldValue(verticalLayout.getComponent(36), "01/01/2016");
        assertTextFieldValue(verticalLayout.getComponent(37), USER_NAME);
        assertTextFieldValue(verticalLayout.getComponent(38), "12/12/2020");
    }

    @Test
    public void testWrWrkInstValidation() {
        initEditWindow();
        TextField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInstField");
        verifyTextFieldValidationMessage(wrWrkInstField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, "1234567890",
            "Field value should not exceed 9 digits", false);
        verifyTextFieldValidationMessage(wrWrkInstField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(wrWrkInstField, SPACES_STRING, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(wrWrkInstField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
    }

    @Test
    public void testCompanyIdValidation() {
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setId(1136L);
        companyInformation.setName("Albany International Corp.");
        companyInformation.setDetailLicenseeClassId(DET_LC_ID);
        expect(controller.getCompanyInformation(anyLong())).andReturn(companyInformation).anyTimes();
        replay(controller);
        window = new UdmEditUsageWindow(controller, udmUsage, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, BINDER_NAME);
        TextField companyIdField = Whitebox.getInternalState(window, "companyIdField");
        Button verifyButton = (Button) ((HorizontalLayout) getPanelContent().getComponent(20)).getComponent(2);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, INTEGER_WITH_SPACES_STRING,
            StringUtils.EMPTY, true);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, StringUtils.EMPTY,
            EMPTY_FIELD_VALIDATION_MESSAGE, false);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, "12345678901",
            "Field value should not exceed 10 digits", false);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE,
            false);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, SPACES_STRING,
            EMPTY_FIELD_VALIDATION_MESSAGE, false);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE,
            false);
        verify(controller);
    }

    @Test
    public void testQuantityValidation() {
        initEditWindow();
        TextField quantityField = Whitebox.getInternalState(window, "quantityField");
        verifyIntegerValidations(quantityField, NUMBER_VALIDATION_MESSAGE);
        verifyTextFieldValidationMessage(quantityField, "1234567890", "Field value should not exceed 9 digits", false);
        verifyTextFieldValidationMessage(quantityField, "0", NUMBER_VALIDATION_MESSAGE, false);
    }

    @Test
    public void testAnnualMultiplierValidation() {
        initEditWindow();
        TextField annualMultiplierField = Whitebox.getInternalState(window, "annualMultiplierField");
        String numberValidationMessage = "Field value should be positive number between 1 and 25";
        verifyIntegerValidations(annualMultiplierField, numberValidationMessage);
        verifyTextFieldValidationMessage(annualMultiplierField, "1000", numberValidationMessage, false);
        verifyTextFieldValidationMessage(annualMultiplierField, "26", numberValidationMessage, false);
        verifyTextFieldValidationMessage(annualMultiplierField, "0", numberValidationMessage, false);
    }

    @Test
    public void testStatisticalMultiplierValidation() {
        initEditWindow();
        TextField statisticalMultiplierField = Whitebox.getInternalState(window, "statisticalMultiplierField");
        String decimalValidationMessage = "Field value should be positive number between 0.00001 and 1.00000";
        verifyCommonNumberValidations(statisticalMultiplierField, decimalValidationMessage);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "0.00001", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "0.000001", decimalValidationMessage, false);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "1.00001", decimalValidationMessage, false);
    }

    @Test
    public void testTextFieldsLengthValidation() {
        initEditWindow();
        verifyLengthValidation(Whitebox.getInternalState(window, "reportedTitleField"), 1000);
        verifyLengthValidation(Whitebox.getInternalState(window, "reportedStandardNumberField"), 100);
        verifyLengthValidation(Whitebox.getInternalState(window, "reportedPubTypeField"), 100);
        verifyLengthValidation(Whitebox.getInternalState(window, "commentField"), 4000);
        verifyLengthValidation(Whitebox.getInternalState(window, "researchUrlField"), 1000);
    }

    @Test
    public void testVerifyButtonClickListener() {
        expect(controller.calculateAnnualizedCopies(eq(REPORTED_TYPE_OF_USE), anyLong(), anyInt(),
            anyObject(BigDecimal.class))).andReturn(BigDecimal.ONE).anyTimes();
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setId(1136L);
        companyInformation.setName("Albany International Corp.");
        companyInformation.setDetailLicenseeClassId(DET_LC_ID);
        expect(controller.getCompanyInformation(1136L)).andReturn(companyInformation).once();
        replay(controller);
        window = new UdmEditUsageWindow(controller, udmUsage, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, BINDER_NAME);
        VerticalLayout panelContent = getPanelContent();
        HorizontalLayout companyLayout = (HorizontalLayout) panelContent.getComponent(20);
        TextField companyIdField = (TextField) companyLayout.getComponent(1);
        Button verifyButton = (Button) companyLayout.getComponent(2);
        companyIdField.setValue("1136");
        verifyButton.click();
        TextField companyNameField = (TextField) ((HorizontalLayout) panelContent.getComponent(21)).getComponent(1);
        ComboBox<DetailLicenseeClass> detailLicenseeClassComboBox =
            (ComboBox) ((HorizontalLayout) panelContent.getComponent(22)).getComponent(1);
        assertEquals("Albany International Corp.", companyNameField.getValue());
        assertEquals(DET_LC_ID, detailLicenseeClassComboBox.getValue().getId());
        assertEquals(DET_LC_NAME, detailLicenseeClassComboBox.getValue().getDescription());
        verify(controller);
    }

    @Test
    public void testSaveButtonClickListener() throws Exception {
        expect(controller.calculateAnnualizedCopies(eq(REPORTED_TYPE_OF_USE), anyLong(), anyInt(),
            anyObject(BigDecimal.class))).andReturn(BigDecimal.ONE).anyTimes();
        binder = createMock(Binder.class);
        binder.writeBean(udmUsage);
        expectLastCall().once();
        controller.updateUsage(udmUsage);
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, binder, saveButtonClickListener);
        window = new UdmEditUsageWindow(controller, udmUsage, saveButtonClickListener);
        Whitebox.setInternalState(window, binder);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
        verify(controller, binder, saveButtonClickListener);
    }

    @Test
    public void testIsUsageProcessingCompleted() {
        mockStatic(Windows.class);
        udmUsage.setStatus(UsageStatusEnum.NEW);
        Windows.showNotificationWindow("Please wait while usage processing is completed");
        expectLastCall().once();
        replay(controller, Windows.class);
        window = new UdmEditUsageWindow(controller, udmUsage, saveButtonClickListener);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
        verify(controller, Windows.class);
    }

    @Test
    public void testDiscardButtonClickListener() {
        expect(controller.calculateAnnualizedCopies(eq(REPORTED_TYPE_OF_USE), anyLong(), anyInt(),
            anyObject(BigDecimal.class))).andReturn(BigDecimal.ONE).anyTimes();
        binder = createMock(Binder.class);
        binder.readBean(udmUsage);
        expectLastCall().once();
        replay(controller, binder);
        window = new UdmEditUsageWindow(controller, udmUsage, saveButtonClickListener);
        Whitebox.setInternalState(window, binder);
        HorizontalLayout buttonsLayout = getButtonsLayout();
        ((Button) buttonsLayout.getComponent(1)).click();
        verify(controller, binder);
    }

    @Test
    public void testAnnualizedCopiesCalculation() {
        expect(controller.calculateAnnualizedCopies(REPORTED_TYPE_OF_USE, 10L, 2, BigDecimal.ONE.setScale(5)))
            .andReturn(new BigDecimal("20.00000")).once();
        expect(controller.calculateAnnualizedCopies(REPORTED_TYPE_OF_USE, 20L, 2, BigDecimal.ONE.setScale(5)))
            .andReturn(new BigDecimal("40.00000")).once();
        expect(controller.calculateAnnualizedCopies(REPORTED_TYPE_OF_USE, 20L, 2, new BigDecimal("0.20000")))
            .andReturn(new BigDecimal("8.00000")).once();
        replay(controller);
        window = new UdmEditUsageWindow(controller, udmUsage, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, BINDER_NAME);
        String annualizedCopiesErrorMessage =
            "Field value cannot be calculated. Please specify correct values for calculation";
        TextField quantityField = Whitebox.getInternalState(window, "quantityField");
        TextField annualizedCopiesField = Whitebox.getInternalState(window, "annualizedCopiesField");
        TextField annualMultiplierField = Whitebox.getInternalState(window, "annualMultiplierField");
        TextField statisticalMultiplierField = Whitebox.getInternalState(window, "statisticalMultiplierField");
        assertEquals("10.00000", annualizedCopiesField.getValue());
        annualMultiplierField.setValue(INVALID_NUMBER);
        verifyBinderStatusAndValidationMessage(annualizedCopiesErrorMessage, false);
        annualMultiplierField.setValue("2");
        assertEquals("20.00000", annualizedCopiesField.getValue());
        quantityField.setValue(INVALID_NUMBER);
        verifyBinderStatusAndValidationMessage(annualizedCopiesErrorMessage, false);
        quantityField.setValue("20");
        assertEquals("40.00000", annualizedCopiesField.getValue());
        statisticalMultiplierField.setValue(INVALID_NUMBER);
        verifyBinderStatusAndValidationMessage(annualizedCopiesErrorMessage, false);
        statisticalMultiplierField.setValue("0.20000");
        assertEquals("8.00000", annualizedCopiesField.getValue());
        verify(controller);
    }

    private void verifyLengthValidation(TextField textField, int maxSize) {
        verifyTextFieldValidationMessage(textField, buildStringWithExpectedLength(maxSize),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(textField, buildStringWithExpectedLength(maxSize + 1),
            String.format("Field value should not exceed %s characters", maxSize), false);
        verifyTextFieldValidationMessage(textField, StringUtils.EMPTY, StringUtils.EMPTY, true);
    }

    private void verifyIntegerValidations(TextField textField, String numberValidationMessage) {
        verifyCommonNumberValidations(textField, numberValidationMessage);
        verifyTextFieldValidationMessage(textField, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(textField, VALID_DECIMAL, numberValidationMessage, false);
    }

    private void verifyCommonNumberValidations(TextField textField, String numberValidationMessage) {
        verifyTextFieldValidationMessage(textField, INVALID_NUMBER, numberValidationMessage, false);
        verifyTextFieldValidationMessage(textField, StringUtils.EMPTY, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(textField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(textField, SPACES_STRING, EMPTY_FIELD_VALIDATION_MESSAGE, false);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyPanel(verticalLayout.getComponent(0));
        verifyButtonsLayout(verticalLayout.getComponent(1));
    }

    private void verifyPanel(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(39, verticalLayout.getComponentCount());
        verifyTextFieldLayout(verticalLayout.getComponent(0), "Detail ID", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(1), "Period", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(2), "Usage Origin", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(3), "Usage Detail ID", true, false);
        verifyComboBoxLayout(verticalLayout.getComponent(4), "Detail Status", false);
        verifyTextFieldLayout(verticalLayout.getComponent(5), "Assignee", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(6), "RH Account #", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(7), "RH Name", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(8), "Wr Wrk Inst", false, true);
        verifyTextFieldLayout(verticalLayout.getComponent(9), "Reported Title", false, true);
        verifyTextFieldLayout(verticalLayout.getComponent(10), "System Title", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(11), "Reported Standard Number", false, true);
        verifyTextFieldLayout(verticalLayout.getComponent(12), "Standard Number", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(13), "Reported Pub Type", false, true);
        verifyTextFieldLayout(verticalLayout.getComponent(14), "Publication Format", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(15), "Article", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(16), "Language", true, false);
        verifyComboBoxLayout(verticalLayout.getComponent(17), "Action Reason", false);
        verifyTextFieldLayout(verticalLayout.getComponent(18), "Comment", false, true);
        verifyTextFieldLayout(verticalLayout.getComponent(19), "Research URL", false, true);
        verifyCompanyIdLayout(verticalLayout.getComponent(20));
        verifyTextFieldLayout(verticalLayout.getComponent(21), "Company Name", true, true);
        verifyComboBoxLayout(verticalLayout.getComponent(22), "Detail Licensee Class", true);
        verifyTextFieldLayout(verticalLayout.getComponent(23), "Survey Respondent", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(24), "IP Address", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(25), "Survey Country", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(26), "Channel", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(27), "Usage Date", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(28), "Survey Start Date", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(29), "Survey End Date", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(30), "Annual Multiplier", false, true);
        verifyTextFieldLayout(verticalLayout.getComponent(31), "Statistical Multiplier", false, true);
        verifyTextFieldLayout(verticalLayout.getComponent(32), "Reported TOU", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(33), "Quantity", false, true);
        verifyTextFieldLayout(verticalLayout.getComponent(34), "Annualized Copies", true, true);
        verifyComboBoxLayout(verticalLayout.getComponent(35), "Ineligible Reason", false);
        verifyTextFieldLayout(verticalLayout.getComponent(36), "Load Date", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(37), "Updated By", true, false);
        verifyTextFieldLayout(verticalLayout.getComponent(38), "Updated Date", true, false);
    }

    private void verifyTextFieldLayout(Component component, String caption, boolean isReadOnly, boolean isValidated) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption);
        verifyTextField(layout.getComponent(1), isValidated ? caption : null, isReadOnly);
    }

    private void assertTextFieldValue(Component component, String expectedValue) {
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(expectedValue, ((TextField) layout.getComponent(1)).getValue());
    }

    private void assertComboBoxFieldValue(Component component, Object expectedValue) {
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(expectedValue, ((ComboBox) layout.getComponent(1)).getValue());
    }

    private void verifyComboBoxLayout(Component component, String caption, boolean isValidated) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption);
        verifyComboBoxField(layout.getComponent(1), isValidated ? caption : null);
    }

    private void verifyCompanyIdLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), "Company ID");
        verifyTextField(layout.getComponent(1), "Company ID", false);
        verifyButton(layout.getComponent(2), "Verify");
    }

    private void verifyLabel(Component component, String caption) {
        assertTrue(component instanceof Label);
        assertEquals(165, component.getWidth(), 0);
        assertEquals(Unit.PIXELS, component.getWidthUnits());
        assertEquals(caption, ((Label) component).getValue());
    }

    private void verifyTextField(Component component, String caption, boolean isReadOnly) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(caption, component.getCaption());
        assertEquals(isReadOnly, ((TextField) component).isReadOnly());
    }

    private void verifyComboBoxField(Component component, String caption) {
        assertTrue(component instanceof ComboBox);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(caption, component.getCaption());
        assertFalse(((ComboBox) component).isReadOnly());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Discard");
        verifyButton(layout.getComponent(2), "Close");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }

    private void buildUdmUsageDto() {
        udmUsage = new UdmUsageDto();
        udmUsage.setId(UDM_USAGE_UID);
        udmUsage.setPeriod(202012);
        udmUsage.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmUsage.setOriginalDetailId(UDM_USAGE_ORIGINAL_DETAIL_UID);
        udmUsage.setStatus(UsageStatusEnum.RH_FOUND);
        udmUsage.setAssignee(ASSIGNEE);
        udmUsage.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        udmUsage.setRhName(RH_NAME);
        udmUsage.setWrWrkInst(WR_WRK_INST);
        udmUsage.setReportedTitle(REPORTED_TITLE);
        udmUsage.setSystemTitle(SYSTEM_TITLE);
        udmUsage.setReportedStandardNumber(REPORTED_STANDARD_NUMBER);
        udmUsage.setStandardNumber(STANDARD_NUMBER);
        udmUsage.setReportedPubType(PUB_TYPE);
        udmUsage.setPubFormat(PUBLICATION_FORMAT);
        udmUsage.setArticle(ARTICLE);
        udmUsage.setLanguage(LANGUAGE);
        udmUsage.setActionReason(ACTION_REASON);
        udmUsage.setComment(COMMENT);
        udmUsage.setResearchUrl(RESEARCH_URL);
        LICENSEE_CLASS.setId(DET_LC_ID);
        LICENSEE_CLASS.setDescription(DET_LC_NAME);
        udmUsage.setDetailLicenseeClass(LICENSEE_CLASS);
        udmUsage.setCompanyId(COMPANY_ID);
        udmUsage.setCompanyName(COMPANY_NAME);
        udmUsage.setSurveyRespondent(SURVEY_RESPONDENT);
        udmUsage.setIpAddress(IP_ADDRESS);
        udmUsage.setSurveyCountry(SURVEY_COUNTRY);
        udmUsage.setChannel(UdmChannelEnum.CCC);
        udmUsage.setUsageDate(USAGE_DATE);
        udmUsage.setSurveyStartDate(SURVEY_START_DATE);
        udmUsage.setSurveyEndDate(SURVEY_END_DATE);
        udmUsage.setAnnualMultiplier(ANNUAL_MULTIPLIER);
        udmUsage.setStatisticalMultiplier(STATISTICAL_MULTIPLIER);
        udmUsage.setReportedTypeOfUse(REPORTED_TYPE_OF_USE);
        udmUsage.setQuantity(QUANTITY);
        udmUsage.setAnnualizedCopies(ANNUALIZED_COPIES);
        udmUsage.setIneligibleReason(INELIGIBLE_REASON);
        udmUsage.setCreateDate(Date.from(LocalDate.of(2016, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        udmUsage.setUpdateUser(USER_NAME);
        udmUsage.setUpdateDate(Date.from(LocalDate.of(2020, 12, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    private void verifyTextFieldValidationMessage(TextField field, String value, String message, boolean isValid) {
        field.setValue(value);
        verifyBinderStatusAndValidationMessage(message, isValid);
    }

    private void verifyCompanyTextFieldValidationMessage(TextField field, Button verifyButton, String value,
                                                         String message, boolean isValid) {
        field.setValue(value);
        verifyButton.click();
        verifyBinderStatusAndValidationMessage(message, isValid);
    }

    private void verifyBinderStatusAndValidationMessage(String message, boolean isValid) {
        BinderValidationStatus<UdmUsageDto> binderStatus = binder.validate();
        assertEquals(isValid, binderStatus.isOk());
        if (!isValid) {
            List<ValidationResult> errors = binderStatus.getValidationErrors();
            List<String> errorMessages =
                errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
            assertTrue(errorMessages.contains(message));
        }
    }

    private HorizontalLayout getButtonsLayout() {
        return (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
    }

    private VerticalLayout getPanelContent() {
        return (VerticalLayout) ((Panel) ((VerticalLayout) window.getContent()).getComponent(0)).getContent();
    }

    private String buildStringWithExpectedLength(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append('a');
        }
        return result.toString();
    }

    private void initEditWindow() {
        expect(controller.calculateAnnualizedCopies(eq(REPORTED_TYPE_OF_USE), anyLong(), anyInt(),
            anyObject(BigDecimal.class))).andReturn(BigDecimal.ONE).anyTimes();
        replay(controller);
        window = new UdmEditUsageWindow(controller, udmUsage, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, BINDER_NAME);
        verify(controller);
    }
}
