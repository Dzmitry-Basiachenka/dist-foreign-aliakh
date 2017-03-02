package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UI test for Upload Usage Batch window.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/01/2017
 *
 * @author Mikalai Bezmen
 */
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class UploadUsageBatchUiTest extends ForeignCommonUiTest {

    private static final String HTML_B_TAG_NAME = "b";
    private static final String COMMON_EMPTY_FIELD_MESSAGE = "Field value should be specified";
    private static final String RRO_ACCOUNT_NAME_EMPTY_FIELD_MESSAGE =
        COMMON_EMPTY_FIELD_MESSAGE + ". Please press 'Verify' to ensure that RRO is present in PRM";

    @Test
    public void testUsageBatchNameFieldValidation() {
        openUploadUsageBatchWindow();
        // TODO {mbezmen, dbaraukova} Implement test
    }

    @Test
    // Test case ID: 'ddfeaeaa-2ffa-4c06-9bf9-6ff446296175'
    public void testVerifyUploadUsageWindowEmptyFields() {
        WebElement uploadWindow = openUploadUsageBatchWindow();
        clickElementAndWait(assertElement(uploadWindow, "Upload"));
        WebElement errorWindow = findErrorWindow();
        WebElement errorContent = waitAndFindElement(By.className("validation-error-content"));
        List<WebElement> errors = findElements(errorContent, By.tagName(HTML_B_TAG_NAME));
        assertEquals(6, errors.size());
        Set<String> errorFields = Sets.newHashSet("Usage Batch Name", "File to Upload", "RRO Account #",
            "RRO Account Name", "Payment Date", "Gross Amount (USD)");
        assertTrue(errors.stream().map(WebElement::getText).collect(Collectors.toList()).containsAll(errorFields));
        List<WebElement> errorMessages = findElements(errorContent, By.tagName(HTML_SPAN_TAG_NAME));
        assertEquals(6, errorMessages.size());
        Set<String> errorMessageFields = Sets.newHashSet(COMMON_EMPTY_FIELD_MESSAGE, COMMON_EMPTY_FIELD_MESSAGE,
            COMMON_EMPTY_FIELD_MESSAGE, RRO_ACCOUNT_NAME_EMPTY_FIELD_MESSAGE, COMMON_EMPTY_FIELD_MESSAGE,
            COMMON_EMPTY_FIELD_MESSAGE);
        assertTrue(errorMessages.stream().map(WebElement::getText).collect(Collectors.toList())
            .containsAll(errorMessageFields));
        clickElementAndWait(findElementByText(errorWindow, HTML_SPAN_TAG_NAME, "Ok"));
        clickElementAndWait(assertElement(uploadWindow, "Close"));
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
