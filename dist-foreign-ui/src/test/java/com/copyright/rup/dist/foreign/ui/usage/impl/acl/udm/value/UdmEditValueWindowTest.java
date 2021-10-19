package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.ExchangeRate;
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
    private static final Currency CURRENCY = new Currency("EUR", "Euro");
    private static final String CURRENCY_CODE = CURRENCY.getCode();
    private static final String PRICE_TYPE = "Individual";
    private static final String PRICE_ACCESS_TYPE = "Print";
    private static final Integer PRICE_YEAR = 2021;
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
    private static final String INVALID_NUMBER = "12a";
    private static final String INTEGER_WITH_SPACES_STRING = " 1 ";
    private static final String SPACES_STRING = "   ";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String PRICE_FIELD = "priceField";

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
            ImmutableMap.of(CURRENCY.getCode(), CURRENCY.getDescription())).once();
        expect(controller.getAllPriceTypes()).andReturn(Collections.singletonList(PRICE_TYPE)).once();
        expect(controller.getAllPriceAccessTypes()).andReturn(Collections.singletonList(PRICE_ACCESS_TYPE)).once();
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
        VerticalLayout verticalLayout = verifyRootLayout(window.getContent(), true);
        verifyPanel(verticalLayout.getComponent(0));
    }

    @Test
    public void testConstructorWithoutSaveButton() {
        setSpecialistExpectations();
        replay(controller, ForeignSecurityUtils.class);
        window = new UdmEditValueWindow(controller, udmValue);
        binder = Whitebox.getInternalState(window, "binder");
        verify(controller, ForeignSecurityUtils.class);
        assertEquals("View UDM Value", window.getCaption());
        assertEquals(960, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(700, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        VerticalLayout verticalLayout = verifyRootLayout(window.getContent(), false);
        verifyPanel(verticalLayout.getComponent(0));
    }

    @Test
    public void testUdmValueDataBinding() {
        setSpecialistExpectations();
        initEditWindow();
        VerticalLayout verticalLayout = getPanelContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        VerticalLayout row1 = (VerticalLayout) horizontalLayout.getComponent(0);
        assertEquals(2, row1.getComponentCount());
        Panel panel1 = (Panel) row1.getComponent(0);
        VerticalLayout content1 = (VerticalLayout) panel1.getContent();
        assertEquals(6, content1.getComponentCount());
        assertTextFieldValue(content1.getComponent(0), SYSTEM_TITLE);
        assertTextFieldValue(content1.getComponent(1), WR_WRK_INST.toString());
        assertTextFieldValue(content1.getComponent(2), SYSTEM_STANDARD_NUMBER);
        assertTextFieldValue(content1.getComponent(3), STANDARD_NUMBER_TYPE);
        assertTextFieldValue(content1.getComponent(4), RH_NAME);
        assertTextFieldValue(content1.getComponent(5), RH_ACCOUNT_NUMBER.toString());
        Panel panel2 = (Panel) row1.getComponent(1);
        VerticalLayout content2 = (VerticalLayout) panel2.getContent();
        assertEquals(15, content2.getComponentCount());
        assertTextFieldValue(content2.getComponent(0), PRICE.toString());
        assertComboBoxFieldValue(content2.getComponent(1), CURRENCY);
        assertTextFieldValue(content2.getComponent(2), CURRENCY_EXCHANGE_RATE.toString());
        assertTextFieldValue(content2.getComponent(3), "12/31/2020");
        assertTextFieldValue(content2.getComponent(4), PRICE_IN_USD.toString());
        assertComboBoxFieldValue(content2.getComponent(5), PRICE_TYPE);
        assertComboBoxFieldValue(content2.getComponent(6), PRICE_ACCESS_TYPE);
        assertTextFieldValue(content2.getComponent(7), PRICE_YEAR.toString());
        assertTextFieldValue(content2.getComponent(8), PRICE_SOURCE);
        assertTextFieldValue(content2.getComponent(9), PRICE_COMMENT);
        assertTextFieldValue(content2.getComponent(10), "Y");
        assertTextFieldValue(content2.getComponent(11), LAST_PRICE_IN_USD.toString());
        assertTextFieldValue(content2.getComponent(12), LAST_PRICE_SOURCE);
        assertTextFieldValue(content2.getComponent(13), LAST_PRICE_COMMENT);
        assertTextFieldValue(content2.getComponent(14), "N");
        VerticalLayout row2 = (VerticalLayout) horizontalLayout.getComponent(1);
        assertEquals(5, row2.getComponentCount());
        Panel panel3 = (Panel) row2.getComponent(0);
        VerticalLayout content3 = (VerticalLayout) panel3.getContent();
        assertEquals(4, content3.getComponentCount());
        assertTextFieldValue(content3.getComponent(0), VALUE_PERIOD.toString());
        assertTextFieldValue(content3.getComponent(1), LAST_VALUE_PERIOD.toString());
        assertTextFieldValue(content3.getComponent(2), ASSIGNEE);
        assertComboBoxFieldValue(content3.getComponent(3), STATUS);
        Panel panel4 = (Panel) row2.getComponent(1);
        VerticalLayout content4 = (VerticalLayout) panel4.getContent();
        assertEquals(2, content4.getComponentCount());
        assertComboBoxFieldValue(content4.getComponent(0), PUBLICATION_TYPE);
        assertTextFieldValue(content4.getComponent(1), LAST_PUB_TYPE);
        Panel panel5 = (Panel) row2.getComponent(2);
        VerticalLayout content5 = (VerticalLayout) panel5.getContent();
        assertEquals(9, content5.getComponentCount());
        assertTextFieldValue(content5.getComponent(0), CONTENT.toString());
        assertTextFieldValue(content5.getComponent(1), CONTENT_SOURCE);
        assertTextFieldValue(content5.getComponent(2), CONTENT_COMMENT);
        assertTextFieldValue(content5.getComponent(3), "N");
        assertTextFieldValue(content5.getComponent(4), LAST_CONTENT.toString());
        assertTextFieldValue(content5.getComponent(5), LAST_CONTENT_SOURCE);
        assertTextFieldValue(content5.getComponent(6), LAST_CONTENT_COMMENT);
        assertTextFieldValue(content5.getComponent(7), "Y");
        assertTextFieldValue(content5.getComponent(8), CONTENT_UNIT_PRICE.toString());
        Panel panel6 = (Panel) row2.getComponent(3);
        VerticalLayout content6 = (VerticalLayout) panel6.getContent();
        assertEquals(1, content6.getComponentCount());
        assertTextFieldValue(content6.getComponent(0), COMMENT);
        Panel panel7 = (Panel) row2.getComponent(4);
        VerticalLayout content7 = (VerticalLayout) panel7.getContent();
        assertEquals(2, content7.getComponentCount());
        assertTextFieldValue(content7.getComponent(0), USER_NAME);
        assertTextFieldValue(content7.getComponent(1), "12/31/2020");
    }

    @Test
    public void testPriceFieldValidation() {
        initEditWindow();
        TextField priceField = Whitebox.getInternalState(window, PRICE_FIELD);
        String numberValidationMessage = "Field value should contain numeric values only";
        String nonNegativeValidationMessage = "Field value should be positive number or zero";
        String scaleValidationMessage = "Field value should not exceed 10 digits after the decimal point";
        verifyTextFieldValidationMessage(priceField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, SPACES_STRING, numberValidationMessage, false);
        verifyTextFieldValidationMessage(priceField, INVALID_NUMBER, numberValidationMessage, false);
        verifyTextFieldValidationMessage(priceField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "-1", nonNegativeValidationMessage, false);
        verifyTextFieldValidationMessage(priceField, "0", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.1", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.12", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.123", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.1234", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.12345", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.123456", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.1234567", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.12345678", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.123456789", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.1234567890", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceField, "0.12345678901", scaleValidationMessage, false);
    }

    @Test
    public void testCurrencyComboBoxValidation() {
        initEditWindow();
        TextField priceField = Whitebox.getInternalState(window, PRICE_FIELD);
        ComboBox<Currency> currencyComboBox = Whitebox.getInternalState(window, "currencyComboBox");
        Currency currency = new Currency("USD", "US Dollar");
        priceField.setValue(StringUtils.EMPTY);
        currencyComboBox.setValue(null);
        verifyBinderStatusAndValidationMessage(StringUtils.EMPTY, true);
        priceField.setValue(StringUtils.EMPTY);
        currencyComboBox.setValue(currency);
        verifyBinderStatusAndValidationMessage(StringUtils.EMPTY, true);
        priceField.setValue("1");
        currencyComboBox.setValue(null);
        verifyBinderStatusAndValidationMessage("Field value cannot be empty if Price is specified", false);
        priceField.setValue("1");
        currencyComboBox.setValue(currency);
        verifyBinderStatusAndValidationMessage(StringUtils.EMPTY, true);
    }

    @Test
    public void testPriceInUsdRecalculation() {
        initEditWindow();
        reset(controller);
        Currency currency = new Currency("GBP", "Pound Sterling");
        LocalDate date = LocalDate.now();
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setExchangeRateValue(new BigDecimal("0.7243"));
        exchangeRate.setExchangeRateUpdateDate(date);
        expect(controller.getExchangeRate(currency.getCode(), date)).andReturn(exchangeRate).once();
        replay(controller);
        TextField priceField = Whitebox.getInternalState(window, PRICE_FIELD);
        priceField.setValue("100");
        ComboBox<Currency> currencyComboBox = Whitebox.getInternalState(window, "currencyComboBox");
        currencyComboBox.setValue(currency);
        window.recalculatePriceInUsd();
        TextField priceInUsdField = Whitebox.getInternalState(window, "priceInUsdField");
        assertEquals("72.4300000000", priceInUsdField.getValue());
        verify(controller);
    }

    @Test
    public void testPriceYearFieldValidation() {
        initEditWindow();
        TextField priceYearField = Whitebox.getInternalState(window, "priceYearField");
        String yearValidationMessage = "Field value should be in range from 1950 to 2099";
        verifyTextFieldValidationMessage(priceYearField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceYearField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(priceYearField, "1949", yearValidationMessage, false);
        verifyTextFieldValidationMessage(priceYearField, " 1949 ", yearValidationMessage, false);
        verifyTextFieldValidationMessage(priceYearField, "1950", yearValidationMessage, true);
        verifyTextFieldValidationMessage(priceYearField, " 1950 ", yearValidationMessage, true);
        verifyTextFieldValidationMessage(priceYearField, "2099", yearValidationMessage, true);
        verifyTextFieldValidationMessage(priceYearField, " 2099 ", yearValidationMessage, true);
        verifyTextFieldValidationMessage(priceYearField, "2100", yearValidationMessage, false);
        verifyTextFieldValidationMessage(priceYearField, " 2100 ", yearValidationMessage, false);
        verifyTextFieldValidationMessage(priceYearField, "20211", yearValidationMessage, false);
        verifyTextFieldValidationMessage(priceYearField, "202", yearValidationMessage, false);
    }

    @Test
    public void testPriceSourceFieldValidation() {
        initEditWindow();
        TextField priceField = Whitebox.getInternalState(window, PRICE_FIELD);
        TextField priceSourceField = Whitebox.getInternalState(window, "priceSourceField");
        int maxSize = 1000;
        priceField.setValue(StringUtils.EMPTY);
        verifyLengthValidation(priceSourceField, maxSize);
        priceField.setValue("1");
        verifyTextFieldValidationMessage(priceSourceField, StringUtils.EMPTY,
            "Field value cannot be empty if Price is specified", false);
        verifyTextFieldValidationMessage(priceSourceField, buildStringWithExpectedLength(maxSize),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(priceSourceField, buildStringWithExpectedLength(maxSize + 1),
            String.format("Field value should not exceed %s characters", maxSize), false);
    }

    @Test
    public void testPriceCommentFieldValidation() {
        initEditWindow();
        verifyLengthValidation(Whitebox.getInternalState(window, "priceCommentField"), 1000);
    }

    @Test
    public void testContentFieldValidation() {
        initEditWindow();
        TextField contentField = Whitebox.getInternalState(window, "contentField");
        String numberValidationMessage = "Field value should contain numeric values only";
        String nonNegativeValidationMessage = "Field value should be positive number or zero";
        String scaleValidationMessage = "Field value should not exceed 10 digits after the decimal point";
        verifyTextFieldValidationMessage(contentField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, SPACES_STRING, numberValidationMessage, false);
        verifyTextFieldValidationMessage(contentField, INVALID_NUMBER, numberValidationMessage, false);
        verifyTextFieldValidationMessage(contentField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "-1", nonNegativeValidationMessage, false);
        verifyTextFieldValidationMessage(contentField, "0", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.1", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.12", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.123", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.1234", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.12345", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.123456", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.1234567", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.12345678", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.123456789", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.1234567890", StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentField, "0.12345678901", scaleValidationMessage, false);
    }

    @Test
    public void testContentSourceFieldValidation() {
        initEditWindow();
        TextField contentField = Whitebox.getInternalState(window, "contentField");
        TextField contentSourceField = Whitebox.getInternalState(window, "contentSourceField");
        int maxSize = 1000;
        contentField.setValue(StringUtils.EMPTY);
        verifyLengthValidation(contentSourceField, maxSize);
        contentField.setValue("1");
        verifyTextFieldValidationMessage(contentSourceField, StringUtils.EMPTY,
            "Field value cannot be empty if Content is specified", false);
        verifyTextFieldValidationMessage(contentSourceField, buildStringWithExpectedLength(maxSize),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(contentSourceField, buildStringWithExpectedLength(maxSize + 1),
            String.format("Field value should not exceed %s characters", maxSize), false);
    }

    @Test
    public void testContentCommentFieldValidation() {
        initEditWindow();
        verifyLengthValidation(Whitebox.getInternalState(window, "contentCommentField"), 1000);
    }

    @Test
    public void testCommentFieldValidation() {
        initEditWindow();
        verifyLengthValidation(Whitebox.getInternalState(window, "commentField"), 1000);
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

    private void verifyLengthValidation(TextField textField, int maxSize) {
        verifyTextFieldValidationMessage(textField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(textField, buildStringWithExpectedLength(maxSize),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(textField, buildStringWithExpectedLength(maxSize + 1),
            String.format("Field value should not exceed %s characters", maxSize), false);
    }

    private VerticalLayout verifyRootLayout(Component component, boolean isVisible) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(1), isVisible);
        return verticalLayout;
    }

    private void verifyPanel(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(1, verticalLayout.getComponentCount());
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        verifyRow1(horizontalLayout);
        verifyRow2(horizontalLayout);
    }

    private void verifyRow1(HorizontalLayout horizontalLayout) {
        VerticalLayout row1 = (VerticalLayout) horizontalLayout.getComponent(0);
        Panel panel1 = (Panel) row1.getComponent(0);
        assertEquals("Work Information", panel1.getCaption());
        VerticalLayout content1 = (VerticalLayout) panel1.getContent();
        assertEquals(6, content1.getComponentCount());
        verifyTextFieldLayout(content1.getComponent(0), "System Title", true, false);
        verifyTextFieldLayout(content1.getComponent(1), "Wr Wrk Inst", true, false);
        verifyTextFieldLayout(content1.getComponent(2), "System Standard Number", true, false);
        verifyTextFieldLayout(content1.getComponent(3), "Standard Number Type", true, false);
        verifyTextFieldLayout(content1.getComponent(4), "RH Name", true, false);
        verifyTextFieldLayout(content1.getComponent(5), "RH Account #", true, false);
        Panel panel2 = (Panel) row1.getComponent(1);
        assertEquals("Price", panel2.getCaption());
        VerticalLayout content2 = (VerticalLayout) panel2.getContent();
        assertEquals(15, content2.getComponentCount());
        verifyTextFieldLayout(content2.getComponent(0), "Price", false, true);
        verifyComboBoxLayout(content2.getComponent(1), "Currency", true, Collections.singletonList(CURRENCY));
        verifyTextFieldLayout(content2.getComponent(2), "Currency Exchange Rate", true, false);
        verifyTextFieldLayout(content2.getComponent(3), "Currency Exchange Rate Date", true, false);
        verifyTextFieldLayout(content2.getComponent(4), "Price in USD", true, false);
        verifyComboBoxLayout(content2.getComponent(5), "Price Type", true, Collections.singletonList(PRICE_TYPE));
        verifyComboBoxLayout(content2.getComponent(6), "Price Access Type", true,
            Collections.singletonList(PRICE_ACCESS_TYPE));
        verifyTextFieldLayout(content2.getComponent(7), "Price Year", false, true);
        verifyTextFieldLayout(content2.getComponent(8), "Price Source", false, true);
        verifyTextFieldLayout(content2.getComponent(9), "Price Comment", false, true);
        verifyTextFieldLayout(content2.getComponent(10), "Price Flag", true, false);
        verifyTextFieldLayout(content2.getComponent(11), "Last Price in USD", true, false);
        verifyTextFieldLayout(content2.getComponent(12), "Last Price Source", true, false);
        verifyTextFieldLayout(content2.getComponent(13), "Last Price Comment", true, false);
        verifyTextFieldLayout(content2.getComponent(14), "Last Price Flag", true, false);
    }

    private void verifyRow2(HorizontalLayout horizontalLayout) {
        VerticalLayout row2 = (VerticalLayout) horizontalLayout.getComponent(1);
        assertEquals(5, row2.getComponentCount());
        Panel panel3 = (Panel) row2.getComponent(0);
        assertEquals("General", panel3.getCaption());
        VerticalLayout content3 = (VerticalLayout) panel3.getContent();
        assertEquals(4, content3.getComponentCount());
        verifyTextFieldLayout(content3.getComponent(0), "Value Period", true, false);
        verifyTextFieldLayout(content3.getComponent(1), "Last Value Period", true, false);
        verifyTextFieldLayout(content3.getComponent(2), "Assignee", true, false);
        verifyComboBoxLayout(content3.getComponent(3), "Value Status", true,
            Arrays.asList(UdmValueStatusEnum.NEW, UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD,
                UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE, UdmValueStatusEnum.NEEDS_FURTHER_REVIEW,
                UdmValueStatusEnum.RESEARCH_COMPLETE));
        Panel panel4 = (Panel) row2.getComponent(1);
        assertEquals("Publication Type", panel4.getCaption());
        VerticalLayout content4 = (VerticalLayout) panel4.getContent();
        assertEquals(2, content4.getComponentCount());
        verifyComboBoxLayout(content4.getComponent(0), "Pub Type", true,
            Collections.singletonList(buildPublicationType("Book", "1.00")));
        verifyTextFieldLayout(content4.getComponent(1), "Last Pub Type", true, false);
        Panel panel5 = (Panel) row2.getComponent(2);
        assertEquals("Content", panel5.getCaption());
        VerticalLayout content5 = (VerticalLayout) panel5.getContent();
        assertEquals(9, content5.getComponentCount());
        verifyTextFieldLayout(content5.getComponent(0), "Content", false, true);
        verifyTextFieldLayout(content5.getComponent(1), "Content Source", false, true);
        verifyTextFieldLayout(content5.getComponent(2), "Content Comment", false, true);
        verifyTextFieldLayout(content5.getComponent(3), "Content Flag", true, false);
        verifyTextFieldLayout(content5.getComponent(4), "Last Content", true, false);
        verifyTextFieldLayout(content5.getComponent(5), "Last Content Source", true, false);
        verifyTextFieldLayout(content5.getComponent(6), "Last Content Comment", true, false);
        verifyTextFieldLayout(content5.getComponent(7), "Last Content Flag", true, false);
        verifyTextFieldLayout(content5.getComponent(8), "Content Unit Price", true, false);
        Panel panel6 = (Panel) row2.getComponent(3);
        assertEquals("Comment", panel6.getCaption());
        VerticalLayout content6 = (VerticalLayout) panel6.getContent();
        assertEquals(1, content6.getComponentCount());
        verifyTextFieldLayout(content6.getComponent(0), "Comment", false, true);
        Panel panel7 = (Panel) row2.getComponent(4);
        assertNull(panel7.getCaption());
        VerticalLayout content7 = (VerticalLayout) panel7.getContent();
        assertEquals(2, content7.getComponentCount());
        verifyTextFieldLayout(content7.getComponent(0), "Updated By", true, false);
        verifyTextFieldLayout(content7.getComponent(1), "Updated Date", true, false);
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

    private void verifyButtonsLayout(Component component, boolean isVisible) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Discard");
        verifyButton(layout.getComponent(2), "Close");
        Button button = (Button) layout.getComponent(0);
        assertEquals(isVisible, button.isVisible());
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
        udmValue.setCurrency(CURRENCY_CODE);
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

    private String buildStringWithExpectedLength(int length) {
        return StringUtils.repeat('a', length);
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
