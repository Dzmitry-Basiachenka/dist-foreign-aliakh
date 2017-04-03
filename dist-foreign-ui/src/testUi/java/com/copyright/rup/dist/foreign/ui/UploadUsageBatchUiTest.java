package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * UI test for Upload Usage Batch window.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/01/17
 *
 * @author Mikalai Bezmen
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
    private static final String GROSS_AMOUT_FIELD = "Gross Amount in USD";
    private static final String USAGE_BATCH_NAME = "TEST_Dec16";
    private static final Long RRO_ACCOUNT_NUMBER = 2000017004L;
    private static final String GROSS_AMOUNT = "10000.00";
    private static final Long DETAIL_ID_234 = 234L;
    private static final Long DETAIL_ID_235 = 235L;
    private static final String DETAIL_ID_KEY = "detailId";
    private static final String USAGE_BATCH_NAME_FIELD_ID = "usage-batch-name-field";
    private static final String GROSS_AMOUNT_FIELD_ID = "gross-amount-field";
    private UsageBatch usageBatch;

    @Autowired
    private IUsageBatchRepository usageBatchRepository;

    @Autowired
    private IUsageRepository usageRepository;

    @After
    public void tearDown() {
        if (null != usageBatch) {
            deleteUploadedUsageBatch(usageBatch.getId());
            usageBatch = null;
        }
    }

    @Test
    // Test case ID: '1b30b405-0431-4f96-81ca-9c9ff65a32f1'
    public void testUsageBatchNameFieldValidation() {
        WebElement uploadWindow = openUploadUsageBatchWindow();
        populateValidValuesForUploadWindowFields(uploadWindow);
        populateField(uploadWindow, USAGE_BATCH_NAME_FIELD_ID, StringUtils.EMPTY);
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(ImmutableMap.of(USAGE_BATCH_NAME_FIELD, COMMON_EMPTY_FIELD_MESSAGE));
        populateField(uploadWindow, USAGE_BATCH_NAME_FIELD_ID, "JAACC_11Dec16");
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(ImmutableMap.of(USAGE_BATCH_NAME_FIELD, "Usage Batch with such name already exists"));
        populateField(uploadWindow, USAGE_BATCH_NAME_FIELD_ID,
            "Usage Batch with name that exceeds more than 50 characters");
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(ImmutableMap.of(USAGE_BATCH_NAME_FIELD, "Field value should not exceed 50 characters"));
    }

    @Test
    // Test case ID: 'ddfeaeaa-2ffa-4c06-9bf9-6ff446296175'
    public void testVerifyUploadUsageWindowEmptyFields() {
        WebElement uploadWindow = openUploadUsageBatchWindow();
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
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
        populateValidValuesForUploadWindowFields(uploadWindow);
        populateRroAccountNumberField(uploadWindow, StringUtils.EMPTY);
        verifyRroAccountNameField(uploadWindow, StringUtils.EMPTY);
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(ImmutableMap.of(RRO_ACCOUNT_NUMBER_FIELD, COMMON_EMPTY_FIELD_MESSAGE, RRO_ACCOUNT_NAME_FIELD,
            RRO_ACCOUNT_NAME_EMPTY_FIELD_MESSAGE));
        populateRroAccountNumberField(uploadWindow, "symbols");
        verifyRroAccountNameField(uploadWindow, StringUtils.EMPTY);
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(ImmutableMap.of(RRO_ACCOUNT_NUMBER_FIELD, "Field value should contain numeric values only",
            RRO_ACCOUNT_NAME_FIELD, RRO_ACCOUNT_NAME_EMPTY_FIELD_MESSAGE));
        populateRroAccountNumberField(uploadWindow, "555");
        verifyRroAccountNameField(uploadWindow, StringUtils.EMPTY);
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(ImmutableMap.of(RRO_ACCOUNT_NAME_FIELD, RRO_ACCOUNT_NAME_EMPTY_FIELD_MESSAGE));
    }

    @Test
    // Test case ID: '44201b49-bbf5-438a-85a7-11559ec43a2b'
    public void testVerifyGrossAmountField() {
        WebElement uploadWindow = openUploadUsageBatchWindow();
        populateValidValuesForUploadWindowFields(uploadWindow);
        populateField(uploadWindow, GROSS_AMOUNT_FIELD_ID, "symbols");
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        Map<String, String> errors =
            ImmutableMap.of(GROSS_AMOUT_FIELD, "Field should be greater than 0 and contain 2 decimals");
        verifyErrorWindow(errors);
        populateField(uploadWindow, GROSS_AMOUNT_FIELD_ID, "-555");
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(errors);
        populateField(uploadWindow, GROSS_AMOUNT_FIELD_ID, "0");
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(errors);
        populateField(uploadWindow, GROSS_AMOUNT_FIELD_ID, "555");
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(errors);
        populateField(uploadWindow, GROSS_AMOUNT_FIELD_ID, "555.5");
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(errors);
    }

    @Test
    // Test case ID: '0dcb3d62-62f8-4249-bb03-9f40328b57bf'
    public void testVerifyUploadFileField() {
        WebElement uploadWindow = openUploadUsageBatchWindow();
        populateValidValuesForUploadWindowFields(uploadWindow);
        populateFileNameField(uploadWindow, "invalid_extension.txt");
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindow(ImmutableMap.of(FILE_TO_UPLOAD_FIELD, "File extension is incorrect"));
    }

    @Test
    // Test case ID: '81989dc2-329b-4ce7-b5d6-97330d02ccd1'
    public void testUploadValidFile() {
        List<UsageBatch> usageBatches = usageBatchRepository.findAll();
        assertEquals(3, CollectionUtils.size(usageBatches));
        WebElement uploadWindow = openUploadUsageBatchWindow();
        populateValidValuesForUploadWindowFields(uploadWindow);
        LocalDate paymentDate = LocalDate.now();
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        WebElement successfullyUploadedWindow = assertElement(By.className("v-window-contents"));
        verifyLabelMessage(successfullyUploadedWindow, "Upload completed: 2 records were stored successfully");
        clickElementAndWait(assertElement(successfullyUploadedWindow, By.id(OK_BUTTON_ID)));
        List<UsageBatch> uploadedUsageBatches = usageBatchRepository.findAll().stream()
            .filter(usageBatch -> USAGE_BATCH_NAME.equals(usageBatch.getName())).collect(Collectors.toList());
        assertEquals(1, CollectionUtils.size(uploadedUsageBatches));
        usageBatch = uploadedUsageBatches.get(0);
        verifyUploadedUsageBatch(usageBatch, paymentDate);
        verifyUploadedUsages(usageBatch.getId());
    }

    @Test
    // Test case ID: 'fd4ee0a0-72a7-4e26-bf84-9d5905b58b86'
    public void testUploadFileWithInvalidData() {
        WebElement uploadWindow = openUploadUsageBatchWindow();
        populateValidValuesForUploadWindowFields(uploadWindow);
        populateFileNameField(uploadWindow, "invalid_usage_data_file.csv");
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorWindowComponents();
    }

    @Test
    // Test case ID: '66a5ac82-8933-4814-8c71-153a6226d507'
    public void testUploadFileWithInvalidHeader() {
        WebElement uploadWindow = openUploadUsageBatchWindow();
        populateValidValuesForUploadWindowFields(uploadWindow);
        populateFileNameField(uploadWindow, "invalid_header_usage_data_file.csv");
        clickElementAndWait(assertElement(uploadWindow, By.id(UPLOAD_BUTTON_ID)));
        verifyErrorNotificationWindow();
    }

    private void verifyErrorNotificationWindow() {
        WebElement errorWindow = assertElement(By.id("notification-window"));
        assertEquals("Error", assertElement(errorWindow, By.className(V_WINDOW_HEADER_CLASS_NAME)).getText());
        verifyLabelMessage(errorWindow, "Columns headers are incorrect. Expected columns headers are:\n" +
            "Detail ID\nTitle\nArticle\nStandard Number\nWr Wrk Inst\nRH Acct Number\nPublisher\nPub Date\n" +
            "Number of Copies\nReported Value\nMarket\nMarket Period From\nMarket Period To\nAuthor");
        clickElementAndWait(assertElement(errorWindow, By.id(OK_BUTTON_ID)));
        assertNull(waitAndFindElement(By.id("notification-window")));
    }

    private void verifyErrorWindowComponents() {
        WebElement errorWindow = assertElement(By.id("upload-error-window"));
        assertEquals("Error", assertElement(errorWindow, By.className(V_WINDOW_HEADER_CLASS_NAME)).getText());
        verifyLabelMessage(errorWindow,
            "The file could not be uploaded.\nPress Download button to see detailed list of errors");
        assertTrue(assertElement(errorWindow, By.id("Download")).isEnabled());
        clickElementAndWait(assertElement(errorWindow, By.id(CLOSE_BUTTON_ID)));
        assertNull(waitAndFindElement(By.id("upload-error-window")));
    }

    private void verifyUploadedUsageBatch(UsageBatch uploadedUsageBatch, LocalDate date) {
        assertEquals(RRO_ACCOUNT_NUMBER, uploadedUsageBatch.getRro().getAccountNumber());
        assertEquals(new BigDecimal(GROSS_AMOUNT), uploadedUsageBatch.getGrossAmount());
        assertEquals(date.getYear(), uploadedUsageBatch.getFiscalYear(), 0);
        assertEquals(date, uploadedUsageBatch.getPaymentDate());
    }

    private void verifyUploadedUsages(String usageBatchId) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(usageBatchId));
        List<UsageDto> usages = usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC));
        assertNotNull(usages);
        assertEquals(2, CollectionUtils.size(usages));
        verifyUsageWithDetailId234(usages.stream()
            .filter(usage -> Objects.equals(DETAIL_ID_234, usage.getDetailId())).collect(Collectors.toList()));
        verifyUsageWithDetailId235(usages.stream()
            .filter(usage -> Objects.equals(DETAIL_ID_235, usage.getDetailId())).collect(Collectors.toList()));
    }

    private void verifyUsageWithDetailId235(List<UsageDto> usages) {
        assertNotNull(usages);
        assertEquals(1, CollectionUtils.size(usages));
        UsageDto usage = usages.get(0);
        assertNotNull(usage);
        assertEquals("1984", usage.getWorkTitle());
        assertEquals(null, usage.getArticle());
        assertEquals("9.78015E+12", usage.getStandardNumber());
        assertEquals(Long.valueOf(123456789), usage.getWrWrkInst());
        assertEquals(Long.valueOf(1000009522), usage.getRhAccountNumber());
        assertEquals(null, usage.getPublisher());
        assertEquals(null, usage.getPublicationDate());
        assertEquals(null, usage.getNumberOfCopies());
        assertEquals(new BigDecimal("60.86"), usage.getReportedValue());
        assertEquals("Univ,Bus,Doc,S", usage.getMarket());
        assertEquals(Integer.valueOf(2015), usage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2016), usage.getMarketPeriodTo());
        assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        assertEquals(null, usage.getAuthor());
        assertEquals(new BigDecimal("6635.4121238564"), usage.getGrossAmount());
    }

    private void verifyUsageWithDetailId234(List<UsageDto> usages) {
        assertNotNull(usages);
        assertEquals(1, CollectionUtils.size(usages));
        UsageDto usage = usages.get(0);
        assertNotNull(usage);
        assertEquals("1984", usage.getWorkTitle());
        assertEquals("Appendix: The Principles of Newspeak", usage.getArticle());
        assertEquals("9.78015E+12", usage.getStandardNumber());
        assertEquals(Long.valueOf(123456789), usage.getWrWrkInst());
        assertEquals(Long.valueOf(1000009522), usage.getRhAccountNumber());
        assertEquals("Publisher", usage.getPublisher());
        assertEquals(LocalDate.of(3000, 12, 22), usage.getPublicationDate());
        assertEquals(Integer.valueOf(65), usage.getNumberOfCopies());
        assertEquals(new BigDecimal("30.86"), usage.getReportedValue());
        assertEquals("Univ,Bus,Doc,S", usage.getMarket());
        assertEquals(Integer.valueOf(2015), usage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2016), usage.getMarketPeriodTo());
        assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        assertEquals("Aarseth, Espen J.", usage.getAuthor());
        assertEquals(new BigDecimal("3364.5878761454"), usage.getGrossAmount());
    }

    private void deleteUploadedUsageBatch(String usageBatchId) {
        usageRepository.deleteUsages(usageBatchId);
        usageBatchRepository.deleteUsageBatch(usageBatchId);
    }

    private void verifyRroAccountNameField(WebElement uploadWindow, String value) {
        assertEquals(value, getInnerHtml(assertElement(uploadWindow, By.id("rro-account-name-field"))));
    }

    private void verifyLabelMessage(WebElement webElement, String message) {
        assertEquals(message, assertElement(webElement, By.className("v-label")).getText());
    }

    private void populateValidValuesForUploadWindowFields(WebElement uploadWindow) {
        populateField(uploadWindow, USAGE_BATCH_NAME_FIELD_ID, USAGE_BATCH_NAME);
        populateFileNameField(uploadWindow, "sample_usage_data_file.csv");
        populateRroAccountNumberField(uploadWindow, String.valueOf(RRO_ACCOUNT_NUMBER));
        populatePaymentDateField(uploadWindow);
        populateField(uploadWindow, GROSS_AMOUNT_FIELD_ID, GROSS_AMOUNT);
    }

    private void populateFileNameField(WebElement uploadWindow, String usageFileName) {
        WebElement fileUpload = assertElement(uploadWindow, By.className("gwt-FileUpload"));
        fileUpload.clear();
        fileUpload.sendKeys(new File(FILE_PATH, usageFileName).getAbsolutePath());
    }

    private void populateRroAccountNumberField(WebElement uploadWindow, String value) {
        populateField(uploadWindow, "rro-account-number-field", value);
        clickElementAndWait(assertElement(uploadWindow, By.id(VERIFY_BUTTON_ID)));
    }

    private void populatePaymentDateField(WebElement uploadWindow) {
        WebElement paymentDate = assertElement(uploadWindow, By.id("payment-date-field"));
        applyCurrentDateForDateField(paymentDate);
    }

    private WebElement openUploadUsageBatchWindow() {
        loginAsSpecialist();
        WebElement usagesTab = selectUsagesTab();
        WebElement usagesLayout = assertElement(usagesTab, By.id(USAGE_LAYOUT_ID));
        WebElement buttonsLayout = assertElement(usagesLayout, By.id("usages-buttons"));
        clickElementAndWait(assertElement(buttonsLayout, By.id(LOAD_USAGE_BUTTON_ID)));
        return assertElement(By.id("usage-upload-window"));
    }
}
