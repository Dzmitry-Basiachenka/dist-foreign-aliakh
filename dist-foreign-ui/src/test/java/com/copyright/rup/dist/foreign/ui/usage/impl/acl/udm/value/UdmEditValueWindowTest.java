package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.google.common.collect.ImmutableMap;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.provider.ListDataProvider;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link UdmEditValueWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/13/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class UdmEditValueWindowTest {

    private static final String UDM_VALUE_UID = "4bdbe1f2-8d32-4200-8f45-38084a9208d1";
    private static final Integer VALUE_PERIOD = 202106;
    private static final UdmValueStatusEnum STATUS = UdmValueStatusEnum.NEW;
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final Long WR_WRK_INST = 122825347L;
    private static final Long RH_ACCOUNT_NUMBER = 1000023041L;
    private static final String RH_NAME = "American College of Physicians - Journals";
    private static final String SYSTEM_TITLE = "Wissenschaft & Forschung Japan";
    private static final String SYSTEM_STANDARD_NUMBER = "2192-3558";
    private static final String STANDARD_NUMBER_TYPE = "VALISBN13";
    private static final Integer LAST_VALUE_PERIOD = 202012;
    private static final String LAST_PUB_TYPE = "BK2";
    private static final PublicationType PUBLICATION_TYPE;
    private static final BigDecimal LAST_PRICE_IN_USD = new BigDecimal("125.21");
    private static final boolean LAST_PRICE_FLAG = false;
    private static final String LAST_PRICE_SOURCE = "last price source";
    private static final String LAST_PRICE_COMMENT = "last price comment";
    private static final BigDecimal PRICE = new BigDecimal("100.00");
    private static final String PRICE_SOURCE = "price source";
    private static final String CURRENCY = "EUR";
    private static final String PRICE_TYPE = "Individual";
    private static final String PRICE_ACCESS_TYPE = "Print";
    private static final int PRICE_YEAR = 2021;
    private static final String PRICE_COMMENT = "price comment";
    private static final BigDecimal PRICE_IN_USD = new BigDecimal("116.12");
    private static final boolean PRICE_FLAG = true;
    private static final BigDecimal CURRENCY_EXCHANGE_RATE = new BigDecimal("1.1612");
    private static final LocalDate CURRENCY_EXCHANGE_RATE_DATE = LocalDate.of(2020, 12, 31);
    private static final BigDecimal LAST_CONTENT = new BigDecimal("101.00");
    private static final boolean LAST_CONTENT_FLAG = true;
    private static final String LAST_CONTENT_SOURCE = "last content source";
    private static final String LAST_CONTENT_COMMENT = "last content comment";
    private static final BigDecimal CONTENT = new BigDecimal("20");
    private static final String CONTENT_SOURCE = "content source";
    private static final String CONTENT_COMMENT = "content comment";
    private static final boolean CONTENT_FLAG = false;
    private static final BigDecimal CONTENT_UNIT_PRICE = new BigDecimal("5.00");
    private static final String COMMENT = "comment";
    private static final String USER_NAME = "user@copyright.com";
    private static final String VALID_INTEGER = "25";
    private static final String VALID_DECIMAL = "0.1";
    private static final String INVALID_NUMBER = "12a";
    private static final String INTEGER_WITH_SPACES_STRING = " 1 ";
    private static final String SPACES_STRING = "   ";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";

    static {
        PUBLICATION_TYPE = new PublicationType();
        PUBLICATION_TYPE.setName("BK");
        PUBLICATION_TYPE.setDescription("Book");
    }

    private UdmEditValueWindow window;
    private Binder<UdmValueDto> binder;
    private IUdmValueController controller;
    private UdmValueDto udmValue;
    private ClickListener saveButtonClickListener;

    @Before
    public void setUp() {
        buildUdmValueDto();
        mockStatic(ForeignSecurityUtils.class);
        controller = createMock(IUdmValueController.class);
        saveButtonClickListener = createMock(ClickListener.class);
        expect(controller.getPublicationTypes()).andReturn(
            Collections.singletonList(buildPublicationType("Book", "1.00"))).once();
        expect(controller.getCurrencyCodesToCurrencyNamesMap()).andReturn(
            ImmutableMap.of("USD", "US Dollar")).once();
    }

    @Test
    public void testConstructor() {
        setSpecialistExpectations();
        initEditWindow();
        assertEquals("Edit UDM Value", window.getCaption());
        assertEquals(960, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(700, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        VerticalLayout verticalLayout = verifyRootLayout(window.getContent());
        verifyPanel(verticalLayout.getComponent(0));
    }

    // TODO implement tests for the fields when its validators are implemented

    @Test
    public void testUdmValueDataBinding() {
        setSpecialistExpectations();
        initEditWindow();
        VerticalLayout verticalLayout = getPanelContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        VerticalLayout row1 = (VerticalLayout) horizontalLayout.getComponent(0);
        Panel panel1 = (Panel) row1.getComponent(0);
        VerticalLayout content1 = (VerticalLayout) panel1.getContent();
        assertEquals(6, content1.getComponentCount());
        assertTextFieldValue(content1.getComponent(0), SYSTEM_TITLE);
        assertTextFieldValue(content1.getComponent(1), WR_WRK_INST.toString());
        assertTextFieldValue(content1.getComponent(2), SYSTEM_STANDARD_NUMBER);
        assertTextFieldValue(content1.getComponent(3), STANDARD_NUMBER_TYPE);
        assertTextFieldValue(content1.getComponent(4), RH_NAME);
        assertTextFieldValue(content1.getComponent(5), RH_ACCOUNT_NUMBER.toString());
        VerticalLayout row2 = (VerticalLayout) horizontalLayout.getComponent(1);
        Panel panel2 = (Panel) row2.getComponent(0);
        VerticalLayout content2 = (VerticalLayout) panel2.getContent();
        assertEquals(4, content2.getComponentCount());
        assertTextFieldValue(content2.getComponent(0), VALUE_PERIOD.toString());
        assertTextFieldValue(content2.getComponent(1), LAST_VALUE_PERIOD.toString());
        assertTextFieldValue(content2.getComponent(2), ASSIGNEE);
        assertComboBoxFieldValue(content2.getComponent(3), STATUS);
        Panel panel3 = (Panel) row2.getComponent(1);
        VerticalLayout content3 = (VerticalLayout) panel3.getContent();
        assertEquals(2, content3.getComponentCount());
        assertComboBoxFieldValue(content3.getComponent(0), PUBLICATION_TYPE);
        assertTextFieldValue(content3.getComponent(1), LAST_PUB_TYPE);
        // TODO test other fields
    }

    @Test
    public void testWrWrkInstValidation() {
        setSpecialistExpectations();
        initEditWindow();
        TextField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInstField");
        verifyTextFieldValidationMessage(wrWrkInstField, StringUtils.EMPTY, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(wrWrkInstField, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, "1234567890",
            "Field value should not exceed 9 digits", false);
        verifyTextFieldValidationMessage(wrWrkInstField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(wrWrkInstField, SPACES_STRING, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(wrWrkInstField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
    }

    @Test
    public void testValuePeriodFieldValidation() {
        initEditWindow();
        TextField valuePeriodField = Whitebox.getInternalState(window, "valuePeriodField");
        String yearValidationMessage = "Year value should be in range from 1950 to 2099";
        String monthValidationMessage = "Month value should be 06 or 12";
        String lengthValidationMessage = "Period value should contain 6 digits";
        verifyTextFieldValidationMessage(valuePeriodField, StringUtils.EMPTY, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(valuePeriodField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(valuePeriodField, "194912", yearValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "195012", yearValidationMessage, true);
        verifyTextFieldValidationMessage(valuePeriodField, "209912", yearValidationMessage, true);
        verifyTextFieldValidationMessage(valuePeriodField, "210012", yearValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202100", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202101", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202102", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202103", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202104", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202105", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202106", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(valuePeriodField, "202107", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202108", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202109", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202110", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202111", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "202112", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(valuePeriodField, "202113", monthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "2021120", lengthValidationMessage, false);
        verifyTextFieldValidationMessage(valuePeriodField, "20211", lengthValidationMessage, false);
    }

    @Test
    public void testSaveButtonClickListener() throws Exception {
        setSpecialistExpectations();
        binder = createMock(Binder.class);
        binder.writeBean(udmValue);
        expectLastCall().once();
        controller.updateValue(udmValue);
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
        window = new UdmEditValueWindow(controller, udmValue, saveButtonClickListener);
        Whitebox.setInternalState(window, binder);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
        verify(controller, binder, saveButtonClickListener, ForeignSecurityUtils.class);
    }

    private VerticalLayout verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(1));
        return verticalLayout;
    }

    private void verifyPanel(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(1, verticalLayout.getComponentCount());
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        VerticalLayout row1 = (VerticalLayout) horizontalLayout.getComponent(0);
        Panel panel1 = (Panel) row1.getComponent(0);
        assertEquals("Work Information", panel1.getCaption());
        VerticalLayout content1 = (VerticalLayout) panel1.getContent();
        assertEquals(6, content1.getComponentCount());
        verifyTextFieldLayout(content1.getComponent(0), "System Title", true, false);
        verifyTextFieldLayout(content1.getComponent(1), "Wr Wrk Inst", false, true);
        verifyTextFieldLayout(content1.getComponent(2), "System Standard Number", true, false);
        verifyTextFieldLayout(content1.getComponent(3), "Standard Number Type", true, false);
        verifyTextFieldLayout(content1.getComponent(4), "RH Name", true, false);
        verifyTextFieldLayout(content1.getComponent(5), "RH Account #", true, false);
        VerticalLayout row2 = (VerticalLayout) horizontalLayout.getComponent(1);
        Panel panel2 = (Panel) row2.getComponent(0);
        assertEquals("General", panel2.getCaption());
        VerticalLayout content2 = (VerticalLayout) panel2.getContent();
        assertEquals(4, content2.getComponentCount());
        verifyTextFieldLayout(content2.getComponent(0), "Value Period", false, true);
        verifyTextFieldLayout(content2.getComponent(1), "Last Value Period", true, false);
        verifyTextFieldLayout(content2.getComponent(2), "Assignee", true, false);
        verifyComboBoxLayout(content2.getComponent(3), "Value Status", true,
            Arrays.asList(UdmValueStatusEnum.NEW, UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD,
                UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE, UdmValueStatusEnum.NEEDS_FURTHER_REVIEW,
                UdmValueStatusEnum.RESEARCH_COMPLETE));
        Panel panel3 = (Panel) row2.getComponent(1);
        assertEquals("Publication Type", panel3.getCaption());
        VerticalLayout content3 = (VerticalLayout) panel3.getContent();
        assertEquals(2, content3.getComponentCount());
        verifyComboBoxLayout(content3.getComponent(0), "Pub Type", true,
            Collections.singletonList(buildPublicationType("Book", "1.00")));
        verifyTextFieldLayout(content3.getComponent(1), "Last Pub Type", true, false);
        // TODO verify components when implemented
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
        assertEquals(expectedValue, ((ComboBox<?>) layout.getComponent(1)).getValue());
    }

    private <T> ComboBox<T> verifyComboBoxLayout(Component component, String caption, boolean isValidated) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption);
        return verifyComboBoxField(layout.getComponent(1), isValidated ? caption : null);
    }

    private <T> void verifyComboBoxLayout(Component component, String caption, boolean isValidated,
                                          Collection<T> expectedItems) {
        ComboBox<T> comboBox = verifyComboBoxLayout(component, caption, isValidated);
        ListDataProvider<T> listDataProvider = (ListDataProvider<T>) comboBox.getDataProvider();
        Collection<T> actualItems = listDataProvider.getItems();
        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems, actualItems);
    }

    private void verifyLabel(Component component, String caption) {
        assertTrue(component instanceof Label);
        assertEquals(175, component.getWidth(), 0);
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

    private <T> ComboBox<T> verifyComboBoxField(Component component, String caption) {
        assertTrue(component instanceof ComboBox);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(caption, component.getCaption());
        ComboBox<T> comboBox = (ComboBox<T>) component;
        assertFalse(comboBox.isReadOnly());
        return comboBox;
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

    private void buildUdmValueDto() {
        udmValue = new UdmValueDto();
        udmValue.setId(UDM_VALUE_UID);
        udmValue.setValuePeriod(VALUE_PERIOD);
        udmValue.setStatus(STATUS);
        udmValue.setAssignee(ASSIGNEE);
        udmValue.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        udmValue.setRhName(RH_NAME);
        udmValue.setWrWrkInst(WR_WRK_INST);
        udmValue.setSystemTitle(SYSTEM_TITLE);
        udmValue.setSystemStandardNumber(SYSTEM_STANDARD_NUMBER);
        udmValue.setStandardNumberType(STANDARD_NUMBER_TYPE);
        udmValue.setLastValuePeriod(LAST_VALUE_PERIOD);
        udmValue.setLastPubType(LAST_PUB_TYPE);
        udmValue.setPublicationType(PUBLICATION_TYPE);
        udmValue.setLastPriceInUsd(LAST_PRICE_IN_USD);
        udmValue.setLastPriceFlag(LAST_PRICE_FLAG);
        udmValue.setLastPriceSource(LAST_PRICE_SOURCE);
        udmValue.setLastPriceComment(LAST_PRICE_COMMENT);
        udmValue.setPrice(PRICE);
        udmValue.setPriceSource(PRICE_SOURCE);
        udmValue.setCurrency(CURRENCY);
        udmValue.setPriceType(PRICE_TYPE);
        udmValue.setPriceAccessType(PRICE_ACCESS_TYPE);
        udmValue.setPriceYear(PRICE_YEAR);
        udmValue.setPriceComment(PRICE_COMMENT);
        udmValue.setPriceInUsd(PRICE_IN_USD);
        udmValue.setPriceFlag(PRICE_FLAG);
        udmValue.setCurrencyExchangeRate(CURRENCY_EXCHANGE_RATE);
        udmValue.setCurrencyExchangeRateDate(CURRENCY_EXCHANGE_RATE_DATE);
        udmValue.setLastContent(LAST_CONTENT);
        udmValue.setLastContentFlag(LAST_CONTENT_FLAG);
        udmValue.setLastContentSource(LAST_CONTENT_SOURCE);
        udmValue.setLastContentComment(LAST_CONTENT_COMMENT);
        udmValue.setContent(CONTENT);
        udmValue.setContentSource(CONTENT_SOURCE);
        udmValue.setContentComment(CONTENT_COMMENT);
        udmValue.setContentFlag(CONTENT_FLAG);
        udmValue.setContentUnitPrice(CONTENT_UNIT_PRICE);
        udmValue.setComment(COMMENT);
        udmValue.setCreateDate(Date.from(LocalDate.of(2019, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        udmValue.setUpdateUser(USER_NAME);
        udmValue.setUpdateDate(Date.from(LocalDate.of(2020, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    private void verifyTextFieldValidationMessage(TextField field, String value, String message, boolean isValid) {
        field.setValue(value);
        verifyBinderStatusAndValidationMessage(message, isValid);
    }

    private void verifyBinderStatusAndValidationMessage(String message, boolean isValid) {
        BinderValidationStatus<UdmValueDto> binderStatus = binder.validate();
        assertEquals(isValid, binderStatus.isOk());
        if (!isValid) {
            List<ValidationResult> errors = binderStatus.getValidationErrors();
            List<String> errorMessages =
                errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
            assertTrue(errorMessages.contains(message));
        }
    }

    private VerticalLayout getPanelContent() {
        return (VerticalLayout) ((Panel) ((VerticalLayout) window.getContent()).getComponent(0)).getContent();
    }

    private void initEditWindow() {
        replay(controller, ForeignSecurityUtils.class);
        window = new UdmEditValueWindow(controller, udmValue, saveButtonClickListener);
        binder = Whitebox.getInternalState(window, "binder");
        verify(controller, ForeignSecurityUtils.class);
    }

    private void setPermissionsExpectations(boolean isSpecialist, boolean isManager, boolean isResearcher) {
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andStubReturn(isSpecialist);
        expect(ForeignSecurityUtils.hasManagerPermission()).andStubReturn(isManager);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(isResearcher);
    }

    private void setSpecialistExpectations() {
        setPermissionsExpectations(true, false, false);
    }

    private PublicationType buildPublicationType(String name, String weight) {
        PublicationType publicationType = new PublicationType();
        publicationType.setName(name);
        publicationType.setWeight(new BigDecimal(weight));
        return publicationType;
    }
}
