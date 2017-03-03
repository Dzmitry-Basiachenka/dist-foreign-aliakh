package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UI test for Upload Usage Batch window.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/01/2017
 *
 * @author Mikalai Bezmen
 * @author Ihar Suvorau
 */
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class UploadUsageBatchUiTest extends ForeignCommonUiTest {

    private static final String COMMON_EMPTY_FIELD_MESSAGE = "Field value should be specified";
    private static final String RRO_ACCOUNT_NAME_EMPTY_FIELD_MESSAGE =
        COMMON_EMPTY_FIELD_MESSAGE + ". Please press 'Verify' to ensure that RRO is present in PRM";
    private static final String FILE_PATH = "src/testUi/resources/com/copyright/rup/dist/foreign/ui/sample";
    private static final String USAGE_BATCH_NAME_FIELD = "Usage Batch Name";
    private static final String FILE_TO_UPLOAD_FIELD = "File to Upload";
    private static final String RRO_ACCOUNT_NUMBER_FIELD = "RRO Account #";
    private static final String RRO_ACCOUNT_NAME_FIELD = "RRO Account Name";
    private static final String PAYMENT_DATE_FIELD = "Payment Date";
    private static final String GROSS_AMOUT_FIELD = "Gross Amount (USD)";


    @Test
    public void testUsageBatchNameFieldValidation() {
        openUploadUsageBatchWindow();
        // TODO {mbezmen, dbaraukova} Implement test
    }

    @Test
    // Test case ID: 'ddfeaeaa-2ffa-4c06-9bf9-6ff446296175'
    public void testVerifyUploadUsageWindowEmptyFields() {
        WebElement uploadWindow = openUploadUsageBatchWindow();
        clickElementAndWait(assertElement(uploadWindow, UPLOAD_BUTTON_ID));
        Map<String, String> errors = Maps.newHashMap();
        errors.put(USAGE_BATCH_NAME_FIELD, COMMON_EMPTY_FIELD_MESSAGE);
        errors.put(FILE_TO_UPLOAD_FIELD, COMMON_EMPTY_FIELD_MESSAGE);
        errors.put(RRO_ACCOUNT_NUMBER_FIELD, COMMON_EMPTY_FIELD_MESSAGE);
        errors.put(RRO_ACCOUNT_NAME_FIELD, RRO_ACCOUNT_NAME_EMPTY_FIELD_MESSAGE);
        errors.put(PAYMENT_DATE_FIELD, COMMON_EMPTY_FIELD_MESSAGE);
        errors.put(GROSS_AMOUT_FIELD, COMMON_EMPTY_FIELD_MESSAGE);
        verifyErrorWindow(errors);
    }

    @Test
    // Test case ID: 'ce4b4a45-1da0-4321-a9d2-cf1c7c894786'
    public void testVerifyRroAccountNumberField() {
        WebElement uploadWindow = openUploadUsageBatchWindow();
        fillValidValuesForUploadWindowFields(uploadWindow);
        fillRroAccountNumberField(uploadWindow, StringUtils.EMPTY);
        verifyRroAccountNameField(uploadWindow, StringUtils.EMPTY);
        clickElementAndWait(assertElement(uploadWindow, UPLOAD_BUTTON_ID));
        Map<String, String> errors = Maps.newHashMap();
        errors.put(RRO_ACCOUNT_NUMBER_FIELD, COMMON_EMPTY_FIELD_MESSAGE);
        errors.put(RRO_ACCOUNT_NAME_FIELD, RRO_ACCOUNT_NAME_EMPTY_FIELD_MESSAGE);
        verifyErrorWindow(errors);
        fillRroAccountNumberField(uploadWindow, "symbols");
        verifyRroAccountNameField(uploadWindow, StringUtils.EMPTY);
        clickElementAndWait(assertElement(uploadWindow, UPLOAD_BUTTON_ID));
        errors.replace(RRO_ACCOUNT_NUMBER_FIELD, "Field value should contain numeric values only");
        verifyErrorWindow(errors);
        fillRroAccountNumberField(uploadWindow, "555");
        verifyRroAccountNameField(uploadWindow, StringUtils.EMPTY);
        clickElementAndWait(assertElement(uploadWindow, UPLOAD_BUTTON_ID));
        errors.remove(RRO_ACCOUNT_NUMBER_FIELD);
        verifyErrorWindow(errors);
    }

    @Test
    // Test case ID: '44201b49-bbf5-438a-85a7-11559ec43a2b'
    public void testVerifyGrossAmountField() {
        WebElement uploadWindow = openUploadUsageBatchWindow();
        fillValidValuesForUploadWindowFields(uploadWindow);
        fillGrossAmountField(uploadWindow, "symbols");
        clickElementAndWait(assertElement(uploadWindow, UPLOAD_BUTTON_ID));
        Map<String, String> errors = Maps.newHashMap();
        errors.put(GROSS_AMOUT_FIELD, "Field should be greater than 0 and contain 2 decimals");
        verifyErrorWindow(errors);
        fillGrossAmountField(uploadWindow, "-555");
        clickElementAndWait(assertElement(uploadWindow, UPLOAD_BUTTON_ID));
        verifyErrorWindow(errors);
        fillGrossAmountField(uploadWindow, "0");
        clickElementAndWait(assertElement(uploadWindow, UPLOAD_BUTTON_ID));
        verifyErrorWindow(errors);
        fillGrossAmountField(uploadWindow, "555");
        clickElementAndWait(assertElement(uploadWindow, UPLOAD_BUTTON_ID));
        verifyErrorWindow(errors);
        fillGrossAmountField(uploadWindow, "555.5");
        clickElementAndWait(assertElement(uploadWindow, UPLOAD_BUTTON_ID));
        verifyErrorWindow(errors);
    }

    private void verifyRroAccountNameField(WebElement uploadWindow, String value) {
        clickElementAndWait(assertElement(uploadWindow, VERIFY_BUTTON_ID));
        WebElement rroAccountNameField = waitAndFindElement(uploadWindow, By.id("rro-account-name-field"));
        assertNotNull(rroAccountNameField);
        assertEquals(value, getInnerHtml(rroAccountNameField));
    }

    private void fillValidValuesForUploadWindowFields(WebElement uploadWindow) {
        fillUsageBatchNameField(uploadWindow, "CADRA11Dec16");
        fillFileNameField(uploadWindow);
        fillRroAccountNumberField(uploadWindow, "2000017004");
        clickElementAndWait(assertElement(uploadWindow, VERIFY_BUTTON_ID));
        fillPaymentDateField(uploadWindow);
        fillGrossAmountField(uploadWindow, "10000.00");
    }

    private void fillUsageBatchNameField(WebElement uploadWindow, String value) {
        WebElement usageBatchNameField = waitAndFindElement(uploadWindow, By.id("usage-batch-name-field"));
        assertNotNull(usageBatchNameField);
        usageBatchNameField.clear();
        usageBatchNameField.sendKeys(value);
    }

    private void fillFileNameField(WebElement uploadWindow) {
        WebElement fileUpload = waitAndFindElement(uploadWindow, By.className("gwt-FileUpload"));
        assertNotNull(fileUpload);
        fileUpload.clear();
        fileUpload.sendKeys(new File(FILE_PATH, "sample_usage_data_file.csv").getAbsolutePath());
    }

    private void fillRroAccountNumberField(WebElement uploadWindow, String value) {
        WebElement rroAccountNumberField = waitAndFindElement(uploadWindow, By.id("rro-account-number-field"));
        assertNotNull(rroAccountNumberField);
        rroAccountNumberField.clear();
        rroAccountNumberField.sendKeys(value);
    }

    private void fillPaymentDateField(WebElement uploadWindow) {
        WebElement paymentDate = waitAndFindElement(uploadWindow, By.id("payment-date-field"));
        applyCurrentDateForDateField(paymentDate);
    }

    private void fillGrossAmountField(WebElement uploadWindow, String value) {
        WebElement grossAmountField = waitAndFindElement(uploadWindow, By.id("gross-amount-field"));
        assertNotNull(grossAmountField);
        grossAmountField.clear();
        grossAmountField.sendKeys(value);
    }

    private void verifyErrorWindow(Map<String, String> fieldNameToErrorMessageMap) {
        WebElement errorWindow = findErrorWindow();
        WebElement errorContent = waitAndFindElement(By.className("validation-error-content"));
        assertNotNull(errorContent);
        List<WebElement> errorFields = findElements(errorContent, By.tagName(HTML_B_TAG_NAME));
        List<WebElement> errorMessages = findElements(errorContent, By.tagName(HTML_SPAN_TAG_NAME));
        int errorsSize = fieldNameToErrorMessageMap.size();
        assertEquals(errorsSize, errorFields.size());
        assertEquals(errorsSize, errorMessages.size());
        assertTrue(errorFields.stream()
            .map(WebElement::getText)
            .collect(Collectors.toList())
            .containsAll(fieldNameToErrorMessageMap.keySet()));
        assertTrue(errorMessages.stream()
            .map(WebElement::getText)
            .collect(Collectors.toList())
            .containsAll(fieldNameToErrorMessageMap.values()));
        clickElementAndWait(findElementByText(errorWindow, HTML_SPAN_TAG_NAME, OK_BUTTON_ID));
    }

    private WebElement findErrorWindow() {
        WebElement errorWindow = waitAndFindElement(By.className("validation-error-window"));
        assertNotNull(errorWindow);
        return errorWindow;
    }

    private WebElement openUploadUsageBatchWindow() {
        loginAsSpecialist();
        WebElement usagesTab = getUsagesTab();
        WebElement usagesLayout = assertElement(usagesTab, USAGE_LAYOUT_ID);
        WebElement buttonsLayout = assertElement(usagesLayout, "usages-buttons");
        clickElementAndWait(findElement(buttonsLayout, By.id(LOAD_USAGE_BUTTON_ID)));
        return waitAndFindElement(By.id("usage-upload-window"));
    }
}
