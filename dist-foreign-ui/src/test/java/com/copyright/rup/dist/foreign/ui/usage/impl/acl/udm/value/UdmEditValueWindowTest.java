package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import com.copyright.rup.dist.foreign.ui.audit.impl.UdmValueAuditFieldToValuesMap;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
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
    private static final Integer LAST_VALUE_PERIOD = 202012;
    private static final String LAST_PUB_TYPE = "BK2";
    private static final PublicationType PUBLICATION_TYPE;
    private static final BigDecimal LAST_PRICE_IN_USD = new BigDecimal("4519.308297");
    private static final boolean LAST_PRICE_FLAG = false;
    private static final String LAST_PRICE_SOURCE = "last price source";
    private static final String LAST_PRICE_COMMENT = "last price comment";
    private static final BigDecimal PRICE = new BigDecimal("4000.00");
    private static final String PRICE_SOURCE = "price source";
    private static final Currency CURRENCY = new Currency("EUR", "Euro");
    private static final String CURRENCY_CODE = CURRENCY.getCode();
    private static final String PRICE_TYPE = "Individual";
    private static final String PRICE_ACCESS_TYPE = "Print";
    private static final Integer PRICE_YEAR = 2021;
    private static final String PRICE_COMMENT = "price comment";
    private static final BigDecimal PRICE_IN_USD = new BigDecimal("4519.308296");
    private static final boolean PRICE_FLAG = true;
    private static final BigDecimal CURRENCY_EXCHANGE_RATE = new BigDecimal("1.1298270740");
    private static final LocalDate CURRENCY_EXCHANGE_RATE_DATE = LocalDate.of(2020, 12, 31);
    private static final BigDecimal LAST_CONTENT = new BigDecimal("4.00");
    private static final boolean LAST_CONTENT_FLAG = true;
    private static final String LAST_CONTENT_SOURCE = "last content source";
    private static final String LAST_CONTENT_COMMENT = "last content comment";
    private static final BigDecimal CONTENT = new BigDecimal("3.00");
    private static final String CONTENT_SOURCE = "content source";
    private static final String CONTENT_COMMENT = "content comment";
    private static final boolean CONTENT_FLAG = false;
    private static final BigDecimal CONTENT_UNIT_PRICE = new BigDecimal("1550.40");
    private static final String COMMENT = "comment";
    private static final String USER_NAME = "user@copyright.com";
    private static final String VALID_DECIMAL = "0.1";
    private static final String INVALID_NUMBER = "12a";
    private static final String INTEGER_WITH_SPACES_STRING = " 1 ";
    private static final String SPACES_STRING = "   ";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE =
        "Field value should be positive number or zero and should not exceed 10 digits";
    private static final String POSITIVE_AND_LENGTH_ERROR_MESSAGE =
        "Field value should be positive number and should not exceed 10 digits";
    private static final String SCALE_VALIDATION_MESSAGE =
        "Field value should not exceed 10 digits after the decimal point";
    private static final String PRICE_FIELD = "priceField";
    private static final String CONTENT_FIELD = "contentField";

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
        expect(controller.getAllPublicationTypes()).andReturn(Collections.singletonList(PUBLICATION_TYPE)).once();
        expect(controller.getAllCurrencies()).andReturn(Collections.singletonList(CURRENCY)).once();
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
    public void testUdmValueDataBinding() {
        setSpecialistExpectations();
        initEditWindow();
        VerticalLayout verticalLayout = getPanelContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        VerticalLayout row1 = (VerticalLayout) horizontalLayout.getComponent(0);
        assertEquals(2, row1.getComponentCount());
        Panel workPanel = (Panel) row1.getComponent(0);
        VerticalLayout workContent = (VerticalLayout) workPanel.getContent();
        assertEquals(5, workContent.getComponentCount());
        assertTextFieldValue(workContent.getComponent(0), SYSTEM_TITLE);
        assertTextFieldValue(workContent.getComponent(1), WR_WRK_INST.toString());
        assertTextFieldValue(workContent.getComponent(2), SYSTEM_STANDARD_NUMBER);
        assertTextFieldValue(workContent.getComponent(3), RH_NAME);
        assertTextFieldValue(workContent.getComponent(4), RH_ACCOUNT_NUMBER.toString());
        Panel pricePanel = (Panel) row1.getComponent(1);
        VerticalLayout priceContent = (VerticalLayout) pricePanel.getContent();
        assertEquals(15, priceContent.getComponentCount());
        assertTextFieldValue(priceContent.getComponent(0), PRICE.toString());
        assertComboBoxFieldValue(priceContent.getComponent(1), CURRENCY);
        assertTextFieldValue(priceContent.getComponent(2), CURRENCY_EXCHANGE_RATE.toString());
        assertTextFieldValue(priceContent.getComponent(3), "12/31/2020");
        assertTextFieldValue(priceContent.getComponent(4), PRICE_IN_USD.toString());
        assertComboBoxFieldValue(priceContent.getComponent(5), PRICE_TYPE);
        assertComboBoxFieldValue(priceContent.getComponent(6), PRICE_ACCESS_TYPE);
        assertTextFieldValue(priceContent.getComponent(7), PRICE_YEAR.toString());
        assertTextFieldValue(priceContent.getComponent(8), PRICE_SOURCE);
        assertTextFieldValue(priceContent.getComponent(9), PRICE_COMMENT);
        assertTextFieldValue(priceContent.getComponent(10), "Y");
        assertTextFieldValue(priceContent.getComponent(11), LAST_PRICE_IN_USD.toString());
        assertTextFieldValue(priceContent.getComponent(12), LAST_PRICE_SOURCE);
        assertTextFieldValue(priceContent.getComponent(13), LAST_PRICE_COMMENT);
        assertTextFieldValue(priceContent.getComponent(14), "N");
        VerticalLayout row2 = (VerticalLayout) horizontalLayout.getComponent(1);
        assertEquals(5, row2.getComponentCount());
        Panel generalPanel = (Panel) row2.getComponent(0);
        VerticalLayout generalContent = (VerticalLayout) generalPanel.getContent();
        assertEquals(4, generalContent.getComponentCount());
        assertTextFieldValue(generalContent.getComponent(0), VALUE_PERIOD.toString());
        assertTextFieldValue(generalContent.getComponent(1), LAST_VALUE_PERIOD.toString());
        assertTextFieldValue(generalContent.getComponent(2), ASSIGNEE);
        assertComboBoxFieldValue(generalContent.getComponent(3), STATUS);
        Panel pubTypePanel = (Panel) row2.getComponent(1);
        VerticalLayout pubTypeContent = (VerticalLayout) pubTypePanel.getContent();
        assertEquals(2, pubTypeContent.getComponentCount());
        assertComboBoxFieldValue(pubTypeContent.getComponent(0), PUBLICATION_TYPE);
        assertTextFieldValue(pubTypeContent.getComponent(1), LAST_PUB_TYPE);
        Panel contentPanel = (Panel) row2.getComponent(2);
        VerticalLayout contentContent = (VerticalLayout) contentPanel.getContent();
        assertEquals(9, contentContent.getComponentCount());
        assertTextFieldValue(contentContent.getComponent(0), "3.00");
        assertTextFieldValue(contentContent.getComponent(1), CONTENT_SOURCE);
        assertTextFieldValue(contentContent.getComponent(2), CONTENT_COMMENT);
        assertTextFieldValue(contentContent.getComponent(3), "N");
        assertTextFieldValue(contentContent.getComponent(4), LAST_CONTENT.toString());
        assertTextFieldValue(contentContent.getComponent(5), LAST_CONTENT_SOURCE);
        assertTextFieldValue(contentContent.getComponent(6), LAST_CONTENT_COMMENT);
        assertTextFieldValue(contentContent.getComponent(7), "Y");
        assertTextFieldValue(contentContent.getComponent(8), CONTENT_UNIT_PRICE.toString());
        Panel commentPanel = (Panel) row2.getComponent(3);
        VerticalLayout commentContent = (VerticalLayout) commentPanel.getContent();
        assertEquals(1, commentContent.getComponentCount());
        assertTextFieldValue(commentContent.getComponent(0), COMMENT);
        Panel updatePanel = (Panel) row2.getComponent(4);
        VerticalLayout updateContent = (VerticalLayout) updatePanel.getContent();
        assertEquals(2, updateContent.getComponentCount());
        assertTextFieldValue(updateContent.getComponent(0), USER_NAME);
        assertTextFieldValue(updateContent.getComponent(1), "12/31/2020");
    }

    @Test
    public void testPriceFieldValidation() {
        setSpecialistExpectations();
        initEditWindow();
        TextField priceField = Whitebox.getInternalState(window, PRICE_FIELD);
        verifyField(priceField, StringUtils.EMPTY, null, true);
        verifyField(priceField, SPACES_STRING, null, true);
        verifyField(priceField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        verifyField(priceField, INTEGER_WITH_SPACES_STRING, null, true);
        verifyField(priceField, "-1", POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
        verifyField(priceField, "0", null, true);
        verifyField(priceField, "0.1", null, true);
        verifyField(priceField, "0.12", null, true);
        verifyField(priceField, "  0.12  ", null, true);
        verifyField(priceField, "0.123", null, true);
        verifyField(priceField, "0.1234", null, true);
        verifyField(priceField, "0.12345", null, true);
        verifyField(priceField, "0.123456", null, true);
        verifyField(priceField, "0.1234567", null, true);
        verifyField(priceField, "0.12345678", null, true);
        verifyField(priceField, "0.123456789", null, true);
        verifyField(priceField, "0.1234567890", null, true);
        verifyField(priceField, "0.12345678901", SCALE_VALIDATION_MESSAGE, false);
        verifyField(priceField, "1.1234567890", null, true);
        verifyField(priceField, "12.1234567890", null, true);
        verifyField(priceField, "123.1234567890", null, true);
        verifyField(priceField, "1234.1234567890", null, true);
        verifyField(priceField, "12345.1234567890", null, true);
        verifyField(priceField, "123456.1234567890", null, true);
        verifyField(priceField, "1234567.1234567890", null, true);
        verifyField(priceField, "12345678.1234567890", null, true);
        verifyField(priceField, "123456789.1234567890", null, true);
        verifyField(priceField, "1234567890.1234567890", null, true);
        verifyField(priceField, "12345678901.1234567890", POSITIVE_OR_ZERO_AND_LENGTH_ERROR_MESSAGE, false);
    }

    @Test
    public void testCurrencyComboBoxValidation() {
        setSpecialistExpectations();
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
        priceField.setValue(VALID_DECIMAL);
        currencyComboBox.setValue(null);
        verifyBinderStatusAndValidationMessage("Field value cannot be empty if Price is specified", false);
        priceField.setValue(VALID_DECIMAL);
        currencyComboBox.setValue(currency);
        verifyBinderStatusAndValidationMessage(StringUtils.EMPTY, true);
    }

    @Test
    public void testPriceInUsdRecalculation() {
        setSpecialistExpectations();
        initEditWindow();
        TextField priceField = Whitebox.getInternalState(window, PRICE_FIELD);
        ComboBox<Currency> currencyComboBox = Whitebox.getInternalState(window, "currencyComboBox");
        TextField currencyExchangeRateField = Whitebox.getInternalState(window, "currencyExchangeRateField");
        TextField currencyExchangeRateDateField = Whitebox.getInternalState(window, "currencyExchangeRateDateField");
        TextField priceInUsdField = Whitebox.getInternalState(window, "priceInUsdField");
        reset(controller);
        Currency currency = new Currency("GBP", "Pound Sterling");
        LocalDate date = LocalDate.now();
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setInverseExchangeRateValue(new BigDecimal("1.3250680610"));
        exchangeRate.setExchangeRateUpdateDate(date);
        expect(controller.getExchangeRate(currency.getCode(), date)).andReturn(exchangeRate).once();
        replay(controller);
        priceField.setValue(PRICE.toString());
        currencyComboBox.setValue(currency);
        window.recalculatePriceInUsd();
        assertEquals("1.3250680610", currencyExchangeRateField.getValue());
        assertEquals(date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US)),
            currencyExchangeRateDateField.getValue());
        assertEquals("5300.272244", priceInUsdField.getValue());
        verify(controller);
        priceField.setValue(INVALID_NUMBER);
        currencyComboBox.setValue(CURRENCY);
        window.recalculatePriceInUsd();
        assertEquals(StringUtils.EMPTY, currencyExchangeRateField.getValue());
        assertEquals(StringUtils.EMPTY, currencyExchangeRateDateField.getValue());
        assertEquals(StringUtils.EMPTY, priceInUsdField.getValue());
        priceField.setValue(StringUtils.EMPTY);
        currencyComboBox.setValue(CURRENCY);
        window.recalculatePriceInUsd();
        assertEquals(StringUtils.EMPTY, currencyExchangeRateField.getValue());
        assertEquals(StringUtils.EMPTY, currencyExchangeRateDateField.getValue());
        assertEquals(StringUtils.EMPTY, priceInUsdField.getValue());
        priceField.setValue(VALID_DECIMAL);
        currencyComboBox.setValue(null);
        window.recalculatePriceInUsd();
        assertEquals(StringUtils.EMPTY, currencyExchangeRateField.getValue());
        assertEquals(StringUtils.EMPTY, currencyExchangeRateDateField.getValue());
        assertEquals(StringUtils.EMPTY, priceInUsdField.getValue());
    }

    @Test
    public void testPriceYearFieldValidation() {
        setSpecialistExpectations();
        initEditWindow();
        TextField priceYearField = Whitebox.getInternalState(window, "priceYearField");
        String yearValidationMessage = "Field value should be in range from 1950 to 2099";
        verifyField(priceYearField, StringUtils.EMPTY, null, true);
        verifyField(priceYearField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        verifyField(priceYearField, "1949", yearValidationMessage, false);
        verifyField(priceYearField, " 1949 ", yearValidationMessage, false);
        verifyField(priceYearField, "1950", null, true);
        verifyField(priceYearField, " 1950 ", null, true);
        verifyField(priceYearField, "1999", null, true);
        verifyField(priceYearField, " 1999 ", null, true);
        verifyField(priceYearField, "2099", null, true);
        verifyField(priceYearField, " 2099 ", null, true);
        verifyField(priceYearField, "2100", yearValidationMessage, false);
        verifyField(priceYearField, "20211", yearValidationMessage, false);
        verifyField(priceYearField, "202", yearValidationMessage, false);
    }

    @Test
    public void testPriceSourceFieldValidation() {
        setSpecialistExpectations();
        initEditWindow();
        TextField priceField = Whitebox.getInternalState(window, PRICE_FIELD);
        TextField priceSourceField = Whitebox.getInternalState(window, "priceSourceField");
        int maxSize = 1000;
        priceField.setValue(StringUtils.EMPTY);
        verifyLengthValidation(priceSourceField, maxSize);
        priceField.setValue(VALID_DECIMAL);
        verifyField(priceSourceField, StringUtils.EMPTY, "Field value cannot be empty if Price is specified", false);
        verifyField(priceSourceField, buildStringWithExpectedLength(maxSize), null, true);
        verifyField(priceSourceField, buildStringWithExpectedLength(maxSize + 1),
            String.format("Field value should not exceed %s characters", maxSize), false);
    }

    @Test
    public void testPriceCommentFieldValidation() {
        setSpecialistExpectations();
        initEditWindow();
        verifyLengthValidation(Whitebox.getInternalState(window, "priceCommentField"), 1000);
    }

    @Test
    public void testPriceFlagRecalculation() {
        setSpecialistExpectations();
        initEditWindow();
        TextField priceField = Whitebox.getInternalState(window, PRICE_FIELD);
        TextField priceFlagField = Whitebox.getInternalState(window, "priceFlagField");
        priceField.setValue(VALID_DECIMAL);
        window.recalculateFlag(priceField, priceFlagField);
        assertEquals("Y", priceFlagField.getValue());
        priceField.setValue(StringUtils.EMPTY);
        window.recalculateFlag(priceField, priceFlagField);
        assertEquals("N", priceFlagField.getValue());
        priceField.setValue(INVALID_NUMBER);
        window.recalculateFlag(priceField, priceFlagField);
        assertEquals(StringUtils.EMPTY, priceFlagField.getValue());
    }

    @Test
    public void testContentFieldValidation() {
        setSpecialistExpectations();
        initEditWindow();
        TextField contentField = Whitebox.getInternalState(window, CONTENT_FIELD);
        verifyField(contentField, StringUtils.EMPTY, null, true);
        verifyField(contentField, SPACES_STRING, null, true);
        verifyField(contentField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        verifyField(contentField, INTEGER_WITH_SPACES_STRING, null, true);
        verifyField(contentField, "-1", POSITIVE_AND_LENGTH_ERROR_MESSAGE, false);
        verifyField(contentField, "0", POSITIVE_AND_LENGTH_ERROR_MESSAGE, false);
        verifyField(contentField, "0.1", null, true);
        verifyField(contentField, "0.12", null, true);
        verifyField(contentField, "  0.12  ", null, true);
        verifyField(contentField, "0.123", null, true);
        verifyField(contentField, "0.1234", null, true);
        verifyField(contentField, "0.12345", null, true);
        verifyField(contentField, "0.123456", null, true);
        verifyField(contentField, "0.1234567", null, true);
        verifyField(contentField, "0.12345678", null, true);
        verifyField(contentField, "0.123456789", null, true);
        verifyField(contentField, "0.1234567890", null, true);
        verifyField(contentField, "0.12345678901", SCALE_VALIDATION_MESSAGE, false);
        verifyField(contentField, "1.1234567890", null, true);
        verifyField(contentField, "12.1234567890", null, true);
        verifyField(contentField, "123.1234567890", null, true);
        verifyField(contentField, "1234.1234567890", null, true);
        verifyField(contentField, "12345.1234567890", null, true);
        verifyField(contentField, "123456.1234567890", null, true);
        verifyField(contentField, "1234567.1234567890", null, true);
        verifyField(contentField, "12345678.1234567890", null, true);
        verifyField(contentField, "123456789.1234567890", null, true);
        verifyField(contentField, "1234567890.1234567890", null, true);
        verifyField(contentField, "12345678901.1234567890", POSITIVE_AND_LENGTH_ERROR_MESSAGE, false);
    }

    @Test
    public void testContentSourceFieldValidation() {
        setSpecialistExpectations();
        initEditWindow();
        TextField contentField = Whitebox.getInternalState(window, CONTENT_FIELD);
        TextField contentSourceField = Whitebox.getInternalState(window, "contentSourceField");
        int maxSize = 1000;
        contentField.setValue(StringUtils.EMPTY);
        verifyLengthValidation(contentSourceField, maxSize);
        contentField.setValue(VALID_DECIMAL);
        verifyField(contentSourceField, StringUtils.EMPTY, "Field value cannot be empty if Content is specified",
            false);
        verifyField(contentSourceField, buildStringWithExpectedLength(maxSize), null, true);
        verifyField(contentSourceField, buildStringWithExpectedLength(maxSize + 1),
            String.format("Field value should not exceed %s characters", maxSize), false);
    }

    @Test
    public void testContentCommentFieldValidation() {
        setSpecialistExpectations();
        initEditWindow();
        verifyLengthValidation(Whitebox.getInternalState(window, "contentCommentField"), 1000);
    }

    @Test
    public void testContentFlagRecalculation() {
        setSpecialistExpectations();
        initEditWindow();
        TextField contentField = Whitebox.getInternalState(window, CONTENT_FIELD);
        TextField contentFlagField = Whitebox.getInternalState(window, "contentFlagField");
        contentField.setValue(VALID_DECIMAL);
        window.recalculateFlag(contentField, contentFlagField);
        assertEquals("Y", contentFlagField.getValue());
        contentField.setValue(StringUtils.EMPTY);
        window.recalculateFlag(contentField, contentFlagField);
        assertEquals("N", contentFlagField.getValue());
        contentField.setValue(INVALID_NUMBER);
        window.recalculateFlag(contentField, contentFlagField);
        assertEquals(StringUtils.EMPTY, contentFlagField.getValue());
    }

    @Test
    public void testRecalculateContentUnitPrice() {
        setSpecialistExpectations();
        initEditWindow();
        TextField priceInUsdField = Whitebox.getInternalState(window, "priceInUsdField");
        TextField contentField = Whitebox.getInternalState(window, CONTENT_FIELD);
        TextField contentUnitPriceField = Whitebox.getInternalState(window, "contentUnitPriceField");
        priceInUsdField.setValue(PRICE_IN_USD.toString());
        contentField.setValue("2");
        window.recalculateContentUnitPrice();
        assertEquals("2259.654148", contentUnitPriceField.getValue());
        priceInUsdField.setValue(StringUtils.EMPTY);
        contentField.setValue(VALID_DECIMAL);
        window.recalculateContentUnitPrice();
        assertEquals(StringUtils.EMPTY, contentUnitPriceField.getValue());
        priceInUsdField.setValue(VALID_DECIMAL);
        contentField.setValue(INVALID_NUMBER);
        window.recalculateContentUnitPrice();
        assertEquals(StringUtils.EMPTY, contentUnitPriceField.getValue());
        priceInUsdField.setValue(VALID_DECIMAL);
        contentField.setValue(StringUtils.EMPTY);
        window.recalculateContentUnitPrice();
        assertEquals(StringUtils.EMPTY, contentUnitPriceField.getValue());
    }

    @Test
    public void testCommentFieldValidation() {
        setSpecialistExpectations();
        initEditWindow();
        verifyLengthValidation(Whitebox.getInternalState(window, "commentField"), 1000);
    }

    @Test
    public void testSaveButtonClickListener() throws Exception {
        setSpecialistExpectations();
        controller.updateValue(udmValue, new UdmValueAuditFieldToValuesMap(udmValue).getActionReasons());
        expectLastCall().once();
        saveButtonClickListener.buttonClick(anyObject(ClickEvent.class));
        expectLastCall().once();
        replay(controller, saveButtonClickListener, ForeignSecurityUtils.class);
        udmValue.setComment(null);
        udmValue.setContent(null);
        udmValue.setContentSource(null);
        udmValue.setContentComment(null);
        udmValue.setPrice(null);
        udmValue.setPriceSource(null);
        udmValue.setPriceComment(null);
        window = new UdmEditValueWindow(controller, udmValue, saveButtonClickListener);
        Button saveButton = Whitebox.getInternalState(window, "saveButton");
        saveButton.setEnabled(true);
        saveButton.click();
        assertNull(udmValue.getComment());
        assertNull(udmValue.getContentSource());
        assertNull(udmValue.getContentComment());
        assertNull(udmValue.getPriceSource());
        assertNull(udmValue.getPriceComment());
        verify(controller, saveButtonClickListener, ForeignSecurityUtils.class);
    }

    @Test
    public void testInitValueStatusResearcher() {
        setResearcherExpectations();
        initEditWindow();
        ComboBox<UdmValueStatusEnum> valueStatusComboBox = Whitebox.getInternalState(window, "valueStatusComboBox");
        Collection<?> statusValues = ((ListDataProvider<?>) valueStatusComboBox.getDataProvider()).getItems();
        assertEquals(2, statusValues.size());
        assertTrue(statusValues.contains(UdmValueStatusEnum.NEW));
        assertFalse(statusValues.contains(UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD));
        assertTrue(statusValues.contains(UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE));
        assertFalse(statusValues.contains(UdmValueStatusEnum.NEEDS_FURTHER_REVIEW));
        assertFalse(statusValues.contains(UdmValueStatusEnum.RESEARCH_COMPLETE));
        assertFalse(statusValues.contains(UdmValueStatusEnum.PUBLISHED));
    }

    @Test
    public void testInitValueStatusSpecialist() {
        setSpecialistExpectations();
        initEditWindow();
        ComboBox<UdmValueStatusEnum> valueStatusComboBox = Whitebox.getInternalState(window, "valueStatusComboBox");
        Collection<?> statusValues = ((ListDataProvider<?>) valueStatusComboBox.getDataProvider()).getItems();
        assertEquals(5, statusValues.size());
        assertTrue(statusValues.contains(UdmValueStatusEnum.NEW));
        assertTrue(statusValues.contains(UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD));
        assertTrue(statusValues.contains(UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE));
        assertTrue(statusValues.contains(UdmValueStatusEnum.NEEDS_FURTHER_REVIEW));
        assertTrue(statusValues.contains(UdmValueStatusEnum.RESEARCH_COMPLETE));
        assertFalse(statusValues.contains(UdmValueStatusEnum.PUBLISHED));
    }

    private void verifyLengthValidation(TextField textField, int maxSize) {
        verifyField(textField, StringUtils.EMPTY, null, true);
        verifyField(textField, buildStringWithExpectedLength(maxSize), null, true);
        verifyField(textField, buildStringWithExpectedLength(maxSize + 1),
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
        Panel workPanel = (Panel) row1.getComponent(0);
        assertEquals("Work Information", workPanel.getCaption());
        VerticalLayout workContent = (VerticalLayout) workPanel.getContent();
        assertEquals(5, workContent.getComponentCount());
        verifyTextFieldLayout(workContent.getComponent(0), "System Title", true, false);
        verifyTextFieldLayout(workContent.getComponent(1), "Wr Wrk Inst", true, false);
        verifyTextFieldLayout(workContent.getComponent(2), "System Standard Number", true, false);
        verifyTextFieldLayout(workContent.getComponent(3), "RH Name", true, false);
        verifyTextFieldLayout(workContent.getComponent(4), "RH Account #", true, false);
        Panel pricePanel = (Panel) row1.getComponent(1);
        assertEquals("Price", pricePanel.getCaption());
        VerticalLayout priceContent = (VerticalLayout) pricePanel.getContent();
        assertEquals(15, priceContent.getComponentCount());
        verifyTextFieldLayout(priceContent.getComponent(0), "Price", false, true);
        verifyComboBoxLayout(priceContent.getComponent(1), "Currency", true, true, Collections.singletonList(CURRENCY));
        verifyTextFieldLayout(priceContent.getComponent(2), "Currency Exchange Rate", true, false);
        verifyTextFieldLayout(priceContent.getComponent(3), "Currency Exchange Rate Date", true, false);
        verifyTextFieldLayout(priceContent.getComponent(4), "Price in USD", true, false);
        verifyComboBoxLayout(priceContent.getComponent(5), "Price Type", true, true,
            Collections.singletonList(PRICE_TYPE));
        verifyComboBoxLayout(priceContent.getComponent(6), "Price Access Type", true, true,
            Collections.singletonList(PRICE_ACCESS_TYPE));
        verifyTextFieldLayout(priceContent.getComponent(7), "Price Year", false, true);
        verifyTextFieldLayout(priceContent.getComponent(8), "Price Source", false, true);
        verifyTextFieldLayout(priceContent.getComponent(9), "Price Comment", false, true);
        verifyTextFieldLayout(priceContent.getComponent(10), "Price Flag", true, false);
        verifyTextFieldLayout(priceContent.getComponent(11), "Last Price in USD", true, false);
        verifyTextFieldLayout(priceContent.getComponent(12), "Last Price Source", true, false);
        verifyTextFieldLayout(priceContent.getComponent(13), "Last Price Comment", true, false);
        verifyTextFieldLayout(priceContent.getComponent(14), "Last Price Flag", true, false);
    }

    private void verifyRow2(HorizontalLayout horizontalLayout) {
        VerticalLayout row2 = (VerticalLayout) horizontalLayout.getComponent(1);
        assertEquals(5, row2.getComponentCount());
        Panel generalPanel = (Panel) row2.getComponent(0);
        assertEquals("General", generalPanel.getCaption());
        VerticalLayout generalContent = (VerticalLayout) generalPanel.getContent();
        assertEquals(4, generalContent.getComponentCount());
        verifyTextFieldLayout(generalContent.getComponent(0), "Value Period", true, false);
        verifyTextFieldLayout(generalContent.getComponent(1), "Last Value Period", true, false);
        verifyTextFieldLayout(generalContent.getComponent(2), "Assignee", true, false);
        verifyComboBoxLayout(generalContent.getComponent(3), "Value Status", true, false,
            Arrays.asList(UdmValueStatusEnum.NEW, UdmValueStatusEnum.RSCHD_IN_THE_PREV_PERIOD,
                UdmValueStatusEnum.PRELIM_RESEARCH_COMPLETE, UdmValueStatusEnum.NEEDS_FURTHER_REVIEW,
                UdmValueStatusEnum.RESEARCH_COMPLETE));
        Panel pubTypePanel = (Panel) row2.getComponent(1);
        assertEquals("Publication Type", pubTypePanel.getCaption());
        VerticalLayout pubTypeContent = (VerticalLayout) pubTypePanel.getContent();
        assertEquals(2, pubTypeContent.getComponentCount());
        verifyComboBoxLayout(pubTypeContent.getComponent(0), "Pub Type", true, true,
            Collections.singletonList(PUBLICATION_TYPE));
        verifyTextFieldLayout(pubTypeContent.getComponent(1), "Last Pub Type", true, false);
        Panel contentPanel = (Panel) row2.getComponent(2);
        assertEquals("Content", contentPanel.getCaption());
        VerticalLayout contentContent = (VerticalLayout) contentPanel.getContent();
        assertEquals(9, contentContent.getComponentCount());
        verifyTextFieldLayout(contentContent.getComponent(0), "Content", false, true);
        verifyTextFieldLayout(contentContent.getComponent(1), "Content Source", false, true);
        verifyTextFieldLayout(contentContent.getComponent(2), "Content Comment", false, true);
        verifyTextFieldLayout(contentContent.getComponent(3), "Content Flag", true, false);
        verifyTextFieldLayout(contentContent.getComponent(4), "Last Content", true, false);
        verifyTextFieldLayout(contentContent.getComponent(5), "Last Content Source", true, false);
        verifyTextFieldLayout(contentContent.getComponent(6), "Last Content Comment", true, false);
        verifyTextFieldLayout(contentContent.getComponent(7), "Last Content Flag", true, false);
        verifyTextFieldLayout(contentContent.getComponent(8), "Content Unit Price", true, false);
        Panel commentPanel = (Panel) row2.getComponent(3);
        assertEquals("Comment", commentPanel.getCaption());
        VerticalLayout commentContent = (VerticalLayout) commentPanel.getContent();
        assertEquals(1, commentContent.getComponentCount());
        verifyTextFieldLayout(commentContent.getComponent(0), "Comment", false, true);
        Panel updatePanel = (Panel) row2.getComponent(4);
        assertNull(updatePanel.getCaption());
        VerticalLayout updateContent = (VerticalLayout) updatePanel.getContent();
        assertEquals(2, updateContent.getComponentCount());
        verifyTextFieldLayout(updateContent.getComponent(0), "Updated By", true, false);
        verifyTextFieldLayout(updateContent.getComponent(1), "Updated Date", true, false);
    }

    private void verifyTextFieldLayout(Component component, String caption, boolean isReadOnly, boolean isValidated) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption, ContentMode.TEXT, 175);
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

    private <T> void verifyComboBoxLayout(Component component, String caption, boolean isValidated,
                                          boolean emptySelectionAllowed, Collection<T> expectedItems) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), caption, ContentMode.TEXT, 175);
        verifyComboBox(layout.getComponent(1), isValidated ? caption : null, emptySelectionAllowed, expectedItems);
    }

    private void verifyTextField(Component component, String caption, boolean isReadOnly) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(caption, component.getCaption());
        assertEquals(isReadOnly, ((TextField) component).isReadOnly());
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

    private void verifyField(TextField field, String value, String errorMessage, boolean isValid) {
        field.setValue(value);
        binder.validate();
        List fields = binder.getFields()
            .filter(actualField -> actualField.equals(field))
            .collect(Collectors.toList());
        assertEquals(1, fields.size());
        TextField actualField = (TextField) fields.get(0);
        assertNotNull(actualField);
        String actualErrorMessage = Objects.nonNull(actualField.getErrorMessage())
            ? actualField.getErrorMessage().toString()
            : null;
        assertEquals(value, actualField.getValue());
        assertEquals(errorMessage, actualErrorMessage);
        assertEquals(isValid, Objects.isNull(actualErrorMessage));
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

    private void setResearcherExpectations() {
        setPermissionsExpectations(false, false, true);
    }
}
