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
import com.copyright.rup.dist.foreign.domain.UdmAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmIneligibleReason;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableMap;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationException;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Verifies {@link UdmEditMultipleUsagesWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/10/21
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class UdmEditMultipleUsagesWindowTest {

    private static final String UDM_USAGE_UID = "75b110ff-c6c9-45e6-baac-34041ff62081";
    private static final String UDM_USAGE_ORIGINAL_DETAIL_UID = "OGN674GHHSB0025";
    private static final String REPORTED_STANDARD_NUMBER = "0927-7765";
    private static final String NEW_REPORTED_STANDARD_NUMBER = "3465-7765";
    private static final String STANDARD_NUMBER = "2192-3558";
    private static final String REPORTED_TYPE_OF_USE = "COPY_FOR_MYSELF";
    private static final String REPORTED_TITLE = "Colloids and surfaces. B, Biointerfaces";
    private static final String NEW_REPORTED_TITLE = "Medical Journal";
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
    private static final Long NEW_WR_WRK_INST = 1234567L;
    private static final Long RH_ACCOUNT_NUMBER = 1000023041L;
    private static final String RH_NAME = "American College of Physicians - Journals";
    private static final Long COMPANY_ID = 454984566L;
    private static final Long QUANTITY = 10L;
    private static final Long NEW_QUANTITY = 1L;
    private static final Long NEW_COMPANY_ID = 1136L;
    private static final Integer ANNUAL_MULTIPLIER = 1;
    private static final Integer NEW_ANNUAL_MULTIPLIER = 10;
    private static final Integer PERIOD = 202012;
    private static final BigDecimal STATISTICAL_MULTIPLIER = new BigDecimal("1.00000");
    private static final BigDecimal NEW_STATISTICAL_MULTIPLIER = new BigDecimal("0.10000");
    private static final BigDecimal ANNUALIZED_COPIES = new BigDecimal("10.00000");
    private static final String IP_ADDRESS = "ip24.12.119.203";
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final String USER_NAME = "user@copyright.com";
    private static final String COMMENT = "Should be reviewed by Specialist";
    private static final String NEW_COMMENT = "Should be reviewed by Manager";
    private static final String RESEARCH_URL = "google.com";
    private static final Integer DET_LC_ID = 26;
    private static final String DET_LC_NAME = "Law Firms";
    private static final String NEW_COMPANY_NAME = "Albany International Corp.";
    private static final String COMPANY_NAME = "Skadden, Arps, Slate, Meagher & Flom LLP";
    private static final DetailLicenseeClass LICENSEE_CLASS = new DetailLicenseeClass();
    private static final UdmActionReason ACTION_REASON =
        new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content");
    private static final UdmActionReason NEW_ACTION_REASON =
        new UdmActionReason("d658136a-1e6b-4c45-9d6e-d93ccedd36f7", "Misc - See Comments");
    private static final UdmIneligibleReason INELIGIBLE_REASON =
        new UdmIneligibleReason("b60a726a-39e8-4303-abe1-6816da05b858", "Invalid survey");
    private static final UdmIneligibleReason NEW_INELIGIBLE_REASON =
        new UdmIneligibleReason("99efdf2d-8260-45df-a063-e3f455f9ec5e", "Systematic copying");
    private static final String VALID_INTEGER = "25";
    private static final String VALID_DECIMAL = "0.1";
    private static final String INVALID_NUMBER = "12a";
    private static final String INTEGER_WITH_SPACES_STRING = " 1 ";
    private static final String SPACES_STRING = "   ";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";
    private static final String BINDER_NAME = "binder";
    private static final String SELECTED_USAGES = "selectedUdmUsages";
    private UdmEditMultipleUsagesWindow window;
    private Binder<UdmUsageDto> binder;
    private IUdmUsageController controller;
    private Set<UdmUsageDto> udmUsages;
    private ClickListener saveButtonClickListener;

    @Before
    public void setUp() {
        controller = createMock(IUdmUsageController.class);
        saveButtonClickListener = createMock(ClickListener.class);
        expect(controller.getAllActionReasons()).andReturn(Collections.singletonList(ACTION_REASON)).once();
        expect(controller.getDetailLicenseeClasses()).andReturn(Collections.singletonList(LICENSEE_CLASS)).once();
        expect(controller.getAllIneligibleReasons()).andReturn(Collections.singletonList(INELIGIBLE_REASON)).once();
        LICENSEE_CLASS.setId(DET_LC_ID);
        LICENSEE_CLASS.setDescription(DET_LC_NAME);
    }

    @Test
    public void testConstructor() {
        initEditWindow();
        assertEquals("Edit multiple UDM Usages", window.getCaption());
        assertEquals(650, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(530, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
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
    public void testTextFieldsLengthValidation() {
        initEditWindow();
        verifyLengthValidation(Whitebox.getInternalState(window, "reportedTitleField"), 1000);
        verifyLengthValidation(Whitebox.getInternalState(window, "commentField"), 4000);
    }

    @Test
    public void testPeriodFieldValidation() {
        initEditWindow();
        TextField periodField = Whitebox.getInternalState(window, "periodField");
        String yearValidationMessage = "Year value should be in range from 1950 to 2099";
        String monthValidationMessage = "Month value should be 06 or 12";
        String lengthValidationMessage = "Period value should contain 6 digits";
        verifyTextFieldValidationMessage(periodField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(periodField, "125012", yearValidationMessage, false);
        verifyTextFieldValidationMessage(periodField, "300006", yearValidationMessage, false);
        verifyTextFieldValidationMessage(periodField, "202122", monthValidationMessage, false);
        verifyTextFieldValidationMessage(periodField, "202100", monthValidationMessage, false);
        verifyTextFieldValidationMessage(periodField, "202111", monthValidationMessage, false);
        verifyTextFieldValidationMessage(periodField, "202106", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(periodField, "2021013", lengthValidationMessage, false);
        verifyTextFieldValidationMessage(periodField, "123", lengthValidationMessage, false);
    }

    @Test
    public void testCompanyIdValidation() {
        mockStatic(ForeignSecurityUtils.class);
        CompanyInformation companyInformation = buildCompanyInformation();
        expect(controller.getCompanyInformation(anyLong())).andReturn(companyInformation).anyTimes();
        replay(controller);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, BINDER_NAME);
        TextField companyIdField = Whitebox.getInternalState(window, "companyIdField");
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        Button verifyButton = (Button) ((HorizontalLayout) verticalLayout.getComponent(3)).getComponent(2);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, INTEGER_WITH_SPACES_STRING,
            StringUtils.EMPTY, true);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, StringUtils.EMPTY,
            EMPTY_FIELD_VALIDATION_MESSAGE, true);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, "12345678901",
            "Field value should not exceed 10 digits", false);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE,
            false);
        verifyCompanyTextFieldValidationMessage(companyIdField, verifyButton, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE,
            false);
        verify(controller);
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
        String scaleValidationMessage = "Field value should not exceed 5 digits after the decimal point";
        verifyCommonNumberValidations(statisticalMultiplierField, decimalValidationMessage);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "0.000001", decimalValidationMessage, false);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "0.00001", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "0.9", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "0.99", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "0.999", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "0.9999", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "0.99999", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "0.999999", scaleValidationMessage, false);
        verifyTextFieldValidationMessage(statisticalMultiplierField, "1.00001", decimalValidationMessage, false);
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
    public void testVerifyButtonClickListener() {
        CompanyInformation companyInformation = buildCompanyInformation();
        expect(controller.getCompanyInformation(1136L)).andReturn(companyInformation).once();
        replay(controller);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, BINDER_NAME);
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        HorizontalLayout companyLayout = (HorizontalLayout) verticalLayout.getComponent(3);
        TextField companyIdField = (TextField) companyLayout.getComponent(1);
        Button verifyButton = (Button) companyLayout.getComponent(2);
        companyIdField.setValue("1136");
        verifyButton.click();
        TextField companyNameField = (TextField) ((HorizontalLayout) verticalLayout.getComponent(4)).getComponent(1);
        ComboBox<DetailLicenseeClass> detailLicenseeClassComboBox =
            (ComboBox) ((HorizontalLayout) verticalLayout.getComponent(2)).getComponent(1);
        assertEquals(NEW_COMPANY_NAME, companyNameField.getValue());
        assertEquals(DET_LC_ID, detailLicenseeClassComboBox.getValue().getId());
        assertEquals(DET_LC_NAME, detailLicenseeClassComboBox.getValue().getDescription());
        verify(controller);
    }

    @Test
    public void testDiscardButtonClickListener() {
        binder = createMock(Binder.class);
        binder.readBean(null);
        expectLastCall().once();
        replay(controller, binder, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        Whitebox.setInternalState(window, binder);
        HorizontalLayout buttonsLayout = getButtonsLayout();
        ((Button) buttonsLayout.getComponent(1)).click();
        verify(controller, binder, ForeignSecurityUtils.class);
    }

    @Test
    public void testSaveButtonClickListener() throws Exception {
        UdmAuditFieldToValuesMap fieldToValuesMap = new UdmAuditFieldToValuesMap();
        fieldToValuesMap.putFieldWithValues("Detail Status", UsageStatusEnum.RH_FOUND.name(),
            UsageStatusEnum.INELIGIBLE.name());
        fieldToValuesMap.putFieldWithValues("Company ID", COMPANY_ID.toString(), NEW_COMPANY_ID.toString());
        fieldToValuesMap.putFieldWithValues("Company Name", COMPANY_NAME, NEW_COMPANY_NAME);
        fieldToValuesMap.putFieldWithValues("Wr Wrk Inst", WR_WRK_INST.toString(), NEW_WR_WRK_INST.toString());
        fieldToValuesMap.putFieldWithValues("Reported Title", REPORTED_TITLE, NEW_REPORTED_TITLE);
        fieldToValuesMap.putFieldWithValues("Reported Standard Number", REPORTED_STANDARD_NUMBER,
            NEW_REPORTED_STANDARD_NUMBER);
        fieldToValuesMap.putFieldWithValues("Annual Multiplier", ANNUAL_MULTIPLIER.toString(),
            NEW_ANNUAL_MULTIPLIER.toString());
        fieldToValuesMap.putFieldWithValues("Statistical Multiplier", STATISTICAL_MULTIPLIER.toString(),
            NEW_STATISTICAL_MULTIPLIER.toString());
        fieldToValuesMap.putFieldWithValues("Quantity", QUANTITY.toString(), NEW_QUANTITY.toString());
        fieldToValuesMap.putFieldWithValues("Annualized Copies", ANNUALIZED_COPIES.toString(), "1");
        fieldToValuesMap.putFieldWithValues("Action Reason", ACTION_REASON.getReason(), NEW_ACTION_REASON.getReason());
        fieldToValuesMap.putFieldWithValues("Ineligible Reason", INELIGIBLE_REASON.getReason(),
            NEW_INELIGIBLE_REASON.getReason());
        fieldToValuesMap.putFieldWithValues("Comment", COMMENT, NEW_COMMENT);
        UdmUsageDto udmUsageDto = buildActualUdmUsageDto();
        Map<UdmUsageDto, UdmAuditFieldToValuesMap> udmUsageDtoToFieldValuesMap =
            ImmutableMap.of(udmUsageDto, fieldToValuesMap);
        CompanyInformation companyInformation = buildCompanyInformation();
        udmUsages = Collections.singleton(buildUdmUsageDto());
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(false);
        binder = createMock(Binder.class);
        binder.writeBean(buildActualUdmUsageDto());
        expectLastCall();
        expect(controller.getCompanyInformation(1136L)).andReturn(companyInformation).once();
        expect(controller.calculateAnnualizedCopies(eq(REPORTED_TYPE_OF_USE), anyLong(), anyInt(),
            anyObject(BigDecimal.class))).andReturn(BigDecimal.ONE).anyTimes();
        controller.updateUsages(udmUsageDtoToFieldValuesMap, false);
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        Whitebox.setInternalState(window, "bindedUsageDto", udmUsageDto);
        updateFields();
        Whitebox.setInternalState(window, binder);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        Set<UdmUsageDto> usages = Whitebox.getInternalState(window, SELECTED_USAGES);
        usages.forEach(usage -> verifyUpdatedUdmUsages(buildActualUdmUsageDto(), usage));
    }

    @Test
    public void testUpdatePeriod() throws Exception {
        UdmAuditFieldToValuesMap fieldToValuesMap = new UdmAuditFieldToValuesMap();
        fieldToValuesMap.putFieldWithValues("Period", PERIOD.toString(), "206812");
        UdmUsageDto usageToCaptureChanges = new UdmUsageDto();
        usageToCaptureChanges.setPeriod(206812);
        UdmUsageDto udmUsageDto = buildUdmUsageDto();
        udmUsageDto.setPeriodEndDate(LocalDate.of(2068, 12, 31));
        setExpectedData(udmUsageDto, usageToCaptureChanges, fieldToValuesMap);
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        clickSaveButton(usageToCaptureChanges);
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        Set<UdmUsageDto> usages = Whitebox.getInternalState(window, SELECTED_USAGES);
        udmUsageDto.setPeriod(206812);
        udmUsageDto.setPeriodEndDate(LocalDate.of(2068, 12, 31));
        usages.forEach(usage -> verifyUpdatedUdmUsages(udmUsageDto, usage));
    }

    @Test
    public void testUpdateQuantity() throws Exception {
        UdmAuditFieldToValuesMap fieldToValuesMap = new UdmAuditFieldToValuesMap();
        fieldToValuesMap.putFieldWithValues("Quantity", QUANTITY.toString(), NEW_QUANTITY.toString());
        UdmUsageDto usageToCaptureChanges = new UdmUsageDto();
        usageToCaptureChanges.setQuantity(NEW_QUANTITY);
        UdmUsageDto udmUsageDto = buildUdmUsageDto();
        setExpectedData(udmUsageDto, usageToCaptureChanges, fieldToValuesMap);
        expect(controller.calculateAnnualizedCopies(eq(REPORTED_TYPE_OF_USE), anyLong(), anyInt(),
            anyObject(BigDecimal.class))).andReturn(BigDecimal.ONE).anyTimes();
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        clickSaveButton(usageToCaptureChanges);
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        Set<UdmUsageDto> usages = Whitebox.getInternalState(window, SELECTED_USAGES);
        udmUsageDto.setQuantity(NEW_QUANTITY);
        usages.forEach(usage -> verifyUpdatedUdmUsages(udmUsageDto, usage));
    }

    @Test
    public void testUpdateAnnualMultiplier() throws Exception {
        UdmAuditFieldToValuesMap fieldToValuesMap = new UdmAuditFieldToValuesMap();
        fieldToValuesMap.putFieldWithValues("Annual Multiplier", ANNUAL_MULTIPLIER.toString(),
            NEW_ANNUAL_MULTIPLIER.toString());
        UdmUsageDto usageToCaptureChanges = new UdmUsageDto();
        usageToCaptureChanges.setAnnualMultiplier(NEW_ANNUAL_MULTIPLIER);
        UdmUsageDto udmUsageDto = buildUdmUsageDto();
        setExpectedData(udmUsageDto, usageToCaptureChanges, fieldToValuesMap);
        expect(controller.calculateAnnualizedCopies(eq(REPORTED_TYPE_OF_USE), anyLong(), anyInt(),
            anyObject(BigDecimal.class))).andReturn(BigDecimal.ONE).anyTimes();
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        clickSaveButton(usageToCaptureChanges);
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        Set<UdmUsageDto> usages = Whitebox.getInternalState(window, SELECTED_USAGES);
        udmUsageDto.setAnnualMultiplier(NEW_ANNUAL_MULTIPLIER);
        usages.forEach(usage -> verifyUpdatedUdmUsages(udmUsageDto, usage));
    }

    @Test
    public void testUpdateStatisticalMultiplier() throws Exception {
        UdmAuditFieldToValuesMap fieldToValuesMap = new UdmAuditFieldToValuesMap();
        fieldToValuesMap.putFieldWithValues("Statistical Multiplier", STATISTICAL_MULTIPLIER.toString(),
            NEW_STATISTICAL_MULTIPLIER.toString());
        UdmUsageDto usageToCaptureChanges = new UdmUsageDto();
        usageToCaptureChanges.setStatisticalMultiplier(NEW_STATISTICAL_MULTIPLIER);
        UdmUsageDto udmUsageDto = buildUdmUsageDto();
        setExpectedData(udmUsageDto, usageToCaptureChanges, fieldToValuesMap);
        expect(controller.calculateAnnualizedCopies(eq(REPORTED_TYPE_OF_USE), anyLong(), anyInt(),
            anyObject(BigDecimal.class))).andReturn(BigDecimal.ONE).anyTimes();
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        clickSaveButton(usageToCaptureChanges);
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        Set<UdmUsageDto> usages = Whitebox.getInternalState(window, SELECTED_USAGES);
        udmUsageDto.setStatisticalMultiplier(NEW_STATISTICAL_MULTIPLIER);
        usages.forEach(usage -> verifyUpdatedUdmUsages(udmUsageDto, usage));
    }

    @Test
    public void testUpdateReportedTitle() throws Exception {
        UdmAuditFieldToValuesMap fieldToValuesMap = new UdmAuditFieldToValuesMap();
        fieldToValuesMap.putFieldWithValues("Reported Title", REPORTED_TITLE, NEW_REPORTED_TITLE);
        UdmUsageDto usageToCaptureChanges = new UdmUsageDto();
        usageToCaptureChanges.setReportedTitle(NEW_REPORTED_TITLE);
        UdmUsageDto udmUsageDto = buildUdmUsageDto();
        setExpectedData(udmUsageDto, usageToCaptureChanges, fieldToValuesMap);
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        clickSaveButton(usageToCaptureChanges);
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        Set<UdmUsageDto> usages = Whitebox.getInternalState(window, SELECTED_USAGES);
        udmUsageDto.setReportedTitle(NEW_REPORTED_TITLE);
        usages.forEach(usage -> verifyUpdatedUdmUsages(udmUsageDto, usage));
    }

    @Test
    public void testUpdateReportedStandardNumber() throws Exception {
        UdmAuditFieldToValuesMap fieldToValuesMap = new UdmAuditFieldToValuesMap();
        fieldToValuesMap.putFieldWithValues("Reported Standard Number", REPORTED_STANDARD_NUMBER,
            NEW_REPORTED_STANDARD_NUMBER);
        UdmUsageDto usageToCaptureChanges = new UdmUsageDto();
        usageToCaptureChanges.setReportedStandardNumber(NEW_REPORTED_STANDARD_NUMBER);
        UdmUsageDto udmUsageDto = buildUdmUsageDto();
        setExpectedData(udmUsageDto, usageToCaptureChanges, fieldToValuesMap);
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        clickSaveButton(usageToCaptureChanges);
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        Set<UdmUsageDto> usages = Whitebox.getInternalState(window, SELECTED_USAGES);
        udmUsageDto.setReportedStandardNumber(NEW_REPORTED_STANDARD_NUMBER);
        usages.forEach(usage -> verifyUpdatedUdmUsages(udmUsageDto, usage));
    }

    private UdmUsageDto buildActualUdmUsageDto() {
        UdmUsageDto udmUsageDto = new UdmUsageDto();
        udmUsageDto.setId(UDM_USAGE_UID);
        udmUsageDto.setAssignee(ASSIGNEE);
        udmUsageDto.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmUsageDto.setPeriodEndDate(LocalDate.of(2020, 12, 31));
        udmUsageDto.setOriginalDetailId(UDM_USAGE_ORIGINAL_DETAIL_UID);
        udmUsageDto.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        udmUsageDto.setRhName(RH_NAME);
        udmUsageDto.setSystemTitle(SYSTEM_TITLE);
        udmUsageDto.setStandardNumber(STANDARD_NUMBER);
        udmUsageDto.setReportedPubType(PUB_TYPE);
        udmUsageDto.setPubFormat(PUBLICATION_FORMAT);
        udmUsageDto.setArticle(ARTICLE);
        udmUsageDto.setLanguage(LANGUAGE);
        udmUsageDto.setResearchUrl(RESEARCH_URL);
        udmUsageDto.setSurveyRespondent(SURVEY_RESPONDENT);
        udmUsageDto.setIpAddress(IP_ADDRESS);
        udmUsageDto.setSurveyCountry(SURVEY_COUNTRY);
        udmUsageDto.setChannel(UdmChannelEnum.CCC);
        udmUsageDto.setUsageDate(USAGE_DATE);
        udmUsageDto.setSurveyStartDate(SURVEY_START_DATE);
        udmUsageDto.setSurveyEndDate(SURVEY_END_DATE);
        udmUsageDto.setReportedTypeOfUse(REPORTED_TYPE_OF_USE);
        udmUsageDto.setQuantity(QUANTITY);
        udmUsageDto.setAnnualizedCopies(BigDecimal.ONE);
        udmUsageDto.setCreateDate(Date.from(LocalDate.of(2016, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        udmUsageDto.setUpdateUser(USER_NAME);
        udmUsageDto.setUpdateDate(
            Date.from(LocalDate.of(2020, 12, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        udmUsageDto.setStatus(UsageStatusEnum.INELIGIBLE);
        udmUsageDto.setPeriod(PERIOD);
        udmUsageDto.setCompanyId(NEW_COMPANY_ID);
        udmUsageDto.setWrWrkInst(NEW_WR_WRK_INST);
        udmUsageDto.setCompanyName(NEW_COMPANY_NAME);
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(DET_LC_ID);
        detailLicenseeClass.setDescription(DET_LC_NAME);
        udmUsageDto.setDetailLicenseeClass(detailLicenseeClass);
        udmUsageDto.setQuantity(NEW_QUANTITY);
        udmUsageDto.setAnnualMultiplier(NEW_ANNUAL_MULTIPLIER);
        udmUsageDto.setStatisticalMultiplier(NEW_STATISTICAL_MULTIPLIER);
        udmUsageDto.setReportedTitle(NEW_REPORTED_TITLE);
        udmUsageDto.setReportedStandardNumber(NEW_REPORTED_STANDARD_NUMBER);
        udmUsageDto.setComment(NEW_COMMENT);
        udmUsageDto.setIneligibleReason(NEW_INELIGIBLE_REASON);
        udmUsageDto.setActionReason(NEW_ACTION_REASON);
        return udmUsageDto;
    }

    @SuppressWarnings("unchecked")
    private void updateFields() {
        ComboBox<UsageStatusEnum> statusEnumComboBox = (ComboBox<UsageStatusEnum>) getComponent(0).getComponent(1);
        statusEnumComboBox.setValue(UsageStatusEnum.INELIGIBLE);
        TextField periodField = (TextField) getComponent(1).getComponent(1);
        periodField.setValue("202012");
        HorizontalLayout companyLayout = getComponent(3);
        TextField companyIdField = (TextField) companyLayout.getComponent(1);
        Button verifyButton = (Button) companyLayout.getComponent(2);
        companyIdField.setValue("1136");
        verifyButton.click();
        TextField wrWrkInstField = (TextField) getComponent(5).getComponent(1);
        wrWrkInstField.setValue("1234567");
        TextField reportedStandardNumberField = (TextField) getComponent(6).getComponent(1);
        reportedStandardNumberField.setValue("new reported standard number");
        TextField reportedTitle = (TextField) getComponent(7).getComponent(1);
        reportedTitle.setValue("new title");
        TextField annualMultiplierField = (TextField) getComponent(8).getComponent(1);
        annualMultiplierField.setValue("10");
        TextField statisticalMultiplierField = (TextField) getComponent(9).getComponent(1);
        statisticalMultiplierField.setValue("0.10000");
        TextField quantityField = (TextField) getComponent(10).getComponent(1);
        quantityField.setValue("1");
        ComboBox<UdmActionReason> actionReasonComboBox = (ComboBox<UdmActionReason>) getComponent(11).getComponent(1);
        actionReasonComboBox.setValue(NEW_ACTION_REASON);
        ComboBox<UdmIneligibleReason> ineligibleReasonComboBox =
            (ComboBox<UdmIneligibleReason>) getComponent(12).getComponent(1);
        ineligibleReasonComboBox.setValue(NEW_INELIGIBLE_REASON);
        TextField commentField = (TextField) getComponent(13).getComponent(1);
        commentField.setValue("comment");
    }

    private HorizontalLayout getComponent(int number) {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        return (HorizontalLayout) verticalLayout.getComponent(number);
    }

    private void verifyTextFieldValidationMessage(TextField field, String value, String message, boolean isValid) {
        field.setValue(value);
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

    private void verifyLengthValidation(TextField textField, int maxSize) {
        verifyTextFieldValidationMessage(textField, buildStringWithExpectedLength(maxSize),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(textField, buildStringWithExpectedLength(maxSize + 1),
            String.format("Field value should not exceed %s characters", maxSize), false);
        verifyTextFieldValidationMessage(textField, StringUtils.EMPTY, StringUtils.EMPTY, true);
    }

    private void verifyCompanyTextFieldValidationMessage(TextField field, Button verifyButton, String value,
                                                         String message, boolean isValid) {
        field.setValue(value);
        verifyButton.click();
        verifyBinderStatusAndValidationMessage(message, isValid);
    }

    private void verifyRootLayout(Component component) {
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(15, verticalLayout.getComponentCount());
        verifyComboBoxLayout(verticalLayout.getComponent(0), "Detail Status");
        verifyTextFieldLayout(verticalLayout.getComponent(1), "Period (YYYYMM)");
        verifyComboBoxLayout(verticalLayout.getComponent(2), "Detail Licensee Class");
        verifyCompanyIdLayout(verticalLayout.getComponent(3));
        verifyTextFieldLayout(verticalLayout.getComponent(4), "Company Name");
        verifyTextFieldLayout(verticalLayout.getComponent(5), "Wr Wrk Inst");
        verifyTextFieldLayout(verticalLayout.getComponent(6), "Reported Standard Number");
        verifyTextFieldLayout(verticalLayout.getComponent(7), "Reported Title");
        verifyTextFieldLayout(verticalLayout.getComponent(8), "Annual Multiplier");
        verifyTextFieldLayout(verticalLayout.getComponent(9), "Statistical Multiplier");
        verifyTextFieldLayout(verticalLayout.getComponent(10), "Quantity");
        verifyComboBoxLayout(verticalLayout.getComponent(11), "Action Reason");
        verifyComboBoxLayout(verticalLayout.getComponent(12), "Ineligible Reason");
        verifyTextFieldLayout(verticalLayout.getComponent(13), "Comment");
        verifyButtonsLayout(verticalLayout.getComponent(14));
    }

    private void verifyComboBoxLayout(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption);
        verifyComboBoxField(layout.getComponent(1), caption);
    }

    private void verifyTextFieldLayout(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption);
        verifyTextField(layout.getComponent(1), caption);
    }

    private void verifyLabel(Component component, String caption) {
        assertTrue(component instanceof Label);
        assertEquals(165, component.getWidth(), 0);
        assertEquals(Unit.PIXELS, component.getWidthUnits());
        assertEquals(caption, ((Label) component).getValue());
    }

    private void verifyTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(caption, component.getCaption());
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

    private void verifyCompanyIdLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), "Company ID");
        verifyTextField(layout.getComponent(1), "Company ID");
        verifyButton(layout.getComponent(2), "Verify");
    }

    private void verifyIntegerValidations(TextField textField, String numberValidationMessage) {
        verifyCommonNumberValidations(textField, numberValidationMessage);
        verifyTextFieldValidationMessage(textField, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(textField, VALID_DECIMAL, numberValidationMessage, false);
    }

    private void verifyCommonNumberValidations(TextField textField, String numberValidationMessage) {
        verifyTextFieldValidationMessage(textField, INVALID_NUMBER, numberValidationMessage, false);
        verifyTextFieldValidationMessage(textField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
    }

    private HorizontalLayout getButtonsLayout() {
        return (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(14);
    }

    private void verifyUpdatedUdmUsages(UdmUsageDto expectedUdmUsageDto, UdmUsageDto actualUdmUsageDto) {
        assertEquals(expectedUdmUsageDto.getStatus(), actualUdmUsageDto.getStatus());
        assertEquals(expectedUdmUsageDto.getPeriod(), actualUdmUsageDto.getPeriod());
        assertEquals(setPeriodEndDate(expectedUdmUsageDto.getPeriod()), actualUdmUsageDto.getPeriodEndDate());
        assertEquals(expectedUdmUsageDto.getCompanyName(), actualUdmUsageDto.getCompanyName());
        if (Objects.nonNull(expectedUdmUsageDto.getDetailLicenseeClass())) {
            assertEquals(expectedUdmUsageDto.getDetailLicenseeClass().getId(),
                actualUdmUsageDto.getDetailLicenseeClass().getId());
            assertEquals(expectedUdmUsageDto.getDetailLicenseeClass().getDescription(),
                actualUdmUsageDto.getDetailLicenseeClass().getDescription());
        }
        assertEquals(expectedUdmUsageDto.getWrWrkInst(), actualUdmUsageDto.getWrWrkInst());
        assertEquals(expectedUdmUsageDto.getReportedStandardNumber(), actualUdmUsageDto.getReportedStandardNumber());
        assertEquals(expectedUdmUsageDto.getReportedTitle(), actualUdmUsageDto.getReportedTitle());
        assertEquals(expectedUdmUsageDto.getQuantity(), actualUdmUsageDto.getQuantity());
        assertEquals(expectedUdmUsageDto.getStatisticalMultiplier(), actualUdmUsageDto.getStatisticalMultiplier());
        assertEquals(expectedUdmUsageDto.getAnnualMultiplier(), actualUdmUsageDto.getAnnualMultiplier());
        assertEquals(expectedUdmUsageDto.getActionReason(), actualUdmUsageDto.getActionReason());
        assertEquals(expectedUdmUsageDto.getIneligibleReason(), actualUdmUsageDto.getIneligibleReason());
        assertEquals(expectedUdmUsageDto.getComment(), actualUdmUsageDto.getComment());
    }

    private String buildStringWithExpectedLength(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append('a');
        }
        return result.toString();
    }

    private void initEditWindow() {
        replay(controller);
        window = new UdmEditMultipleUsagesWindow(controller, udmUsages, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, BINDER_NAME);
        binder.readBean(null);
        verify(controller);
    }

    private CompanyInformation buildCompanyInformation() {
        CompanyInformation companyInformation = new CompanyInformation();
        companyInformation.setId(NEW_COMPANY_ID);
        companyInformation.setName(NEW_COMPANY_NAME);
        companyInformation.setDetailLicenseeClassId(DET_LC_ID);
        return companyInformation;
    }

    private UdmUsageDto buildUdmUsageDto() {
        UdmUsageDto udmUsage = new UdmUsageDto();
        udmUsage.setId(UDM_USAGE_UID);
        udmUsage.setPeriod(202012);
        udmUsage.setUsageOrigin(UdmUsageOriginEnum.SS);
        udmUsage.setPeriodEndDate(LocalDate.of(2020, 12, 31));
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
        return udmUsage;
    }

    private void setExpectedData(UdmUsageDto actualUsage, UdmUsageDto newUsage,
                                 UdmAuditFieldToValuesMap fieldToValuesMap)
        throws ValidationException {
        udmUsages = Collections.singleton(actualUsage);
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(false);
        binder = createMock(Binder.class);
        binder.writeBean(newUsage);
        expectLastCall();
        controller.updateUsages(ImmutableMap.of(actualUsage, fieldToValuesMap), false);
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
    }

    private void clickSaveButton(UdmUsageDto udmUsageDto) {
        Whitebox.setInternalState(window, udmUsageDto);
        Whitebox.setInternalState(window, binder);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
    }

    private LocalDate setPeriodEndDate(Integer period) {
        String value = String.valueOf(period);
        if (StringUtils.isNotEmpty(value)) {
            int year = Integer.parseInt(value.substring(0, 4));
            int month = Integer.parseInt(value.substring(4, 6));
            return 6 == month ? LocalDate.of(year, month, 30) : LocalDate.of(year, month, 31);
        }
        return null;
    }
}
