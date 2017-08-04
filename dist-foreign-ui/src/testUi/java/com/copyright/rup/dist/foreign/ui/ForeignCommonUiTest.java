package com.copyright.rup.dist.foreign.ui;

import static com.google.common.base.Preconditions.checkArgument;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static java.util.Objects.requireNonNull;

import com.copyright.rup.vaadin.test.CommonUiTest;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.google.common.collect.Sets;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Common class for UI tests.
 * Provides functionality for logging in/out, setting up tests, taking screenshots if test fails.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/23/17
 *
 * @author Darya Baraukova
 */
public class ForeignCommonUiTest extends CommonUiTest {
    /**
     * Rule takes screenshot in case of test failure and calls driver.quit() at the end of the test.
     */
    @Rule
    public TestWatcher onFailureWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            super.failed(e, description);
            takeScreenshot(description.getClassName() + '_' + description.getMethodName());
        }

        @Override
        protected void finished(Description description) {
            super.finished(description);
            quitDriver();
        }
    };

    @Before
    public void setUp() {
        initDriver();
        getDriver().manage().window().setSize(new Dimension(1024, 768));
    }

    /**
     * Gets list of footer elements from given table.
     *
     * @param table           table element
     * @param expectedFooters expected footers
     * @return list of footer elements
     * @throws IllegalArgumentException no footer elements in the table or expected footers don't match actual
     */
    //TODO {nlevyankov} move to rup-vaadin
    List<WebElement> assertTableFooterElements(WebElement table, String... expectedFooters) {
        WebElement footer = assertWebElement(table, By.className("v-table-footer-wrap"));
        List<WebElement> elements = findElements(footer, By.className(V_TABLE_FOOTER_CONTAINER_CLASS_NAME));
        int actualSize = elements.size();
        checkArgument(Objects.equals(expectedFooters.length, actualSize),
            String.format("The expected columns count is %s, but actual %s", expectedFooters.length, actualSize));
        String[] elementsArray = elements.stream().map(WebElement::getText).toArray(String[]::new);
        checkArgument(Arrays.equals(expectedFooters, elementsArray),
            String.format("Expected footers %s, but actual %s", Arrays.toString(expectedFooters),
                Arrays.toString(elementsArray)));
        return elements;
    }


    /**
     * Verifies text field element to have concrete id and caption.
     *
     * @param parentElement parent element
     * @param id            id of text field
     * @param caption       caption
     * @return {@link WebElement} instance
     * @see CommonUiTest#assertWebElement(By)
     */
    //TODO {nlevyankov} move to rup-vaadin
    WebElement assertTextFieldElement(WebElement parentElement, String id, String caption) {
        WebElement webElement = assertWebElement(parentElement, id);
        WebElement elementContainer = getParentElement(webElement);
        assertWebElement(elementContainer, HTML_SPAN_TAG_NAME, caption);
        return webElement;
    }

    /**
     * Verifies combobox element to have concrete id and caption.
     *
     * @param parentElement parent element
     * @param id            id of combobox
     * @param caption       caption
     * @return {@link WebElement} instance
     * @see CommonUiTest#assertWebElement(By)
     */
    //TODO {nlevyankov} move to rup-vaadin
    WebElement assertComboboxElement(WebElement parentElement, String id, String caption) {
        WebElement webElement = assertWebElement(parentElement, id);
        assertWebElement(webElement, HTML_SPAN_TAG_NAME, caption);
        assertWebElement(webElement, By.tagName(HTML_INPUT_TAG_NAME));
        assertWebElement(webElement, By.className(V_FILTER_SELECT_BUTTON_CLASS_NAME));
        return webElement;
    }

    /**
     * Verifies that combobox element has expected items.
     *
     * @param element       combobox element
     * @param expectedItems expected items
     */
    void assertComboboxElement(WebElement element, String... expectedItems) {
        assertWebElement(element, By.tagName(HTML_INPUT_TAG_NAME));
        clickElementAndWait(assertWebElement(element, By.className(V_FILTER_SELECT_BUTTON_CLASS_NAME)));
        WebElement optionList = assertWebElement(By.id("VAADIN_COMBOBOX_OPTIONLIST"));
        List<WebElement> elements = findElements(optionList, By.tagName(HTML_TR_TAG_NAME));
        assertEquals(expectedItems.length, CollectionUtils.size(elements));
        IntStream.range(0, expectedItems.length)
            .forEach(i -> assertEquals(expectedItems[i], elements.get(i).getText()));
    }

    /**
     * Verifies search toolbar element.
     * System verifies presence of toolbar, prompt message, presence of textfield, search, and clear buttons.
     *
     * @param parentElement parent element
     * @param promptMessage prompt message
     * @return {@link WebElement} instance
     * @see CommonUiTest#assertWebElement(By)
     */
    //TODO {nlevyankov} move to rup-vaadin
    WebElement assertSearchToolbar(WebElement parentElement, String promptMessage) {
        WebElement searchToolbar = assertWebElement(parentElement, "search-toolbar");
        WebElement prompt = assertWebElement(searchToolbar, By.className("v-textfield-prompt"));
        assertEquals(promptMessage, prompt.getAttribute("value"));
        assertWebElement(searchToolbar, By.className("v-textfield"));
        assertWebElement(searchToolbar, By.className("button-search"));
        assertWebElement(searchToolbar, By.className("button-clear"));
        return searchToolbar;
    }

    @SuppressWarnings("unchecked")
    void assertButtonsToolbar(WebElement buttonsLayout, Set<String> allButtonsIds,
                              Set<String> expectedButtonsIds) {
        assertTrue(String.format("Expected buttons %s couldn't be found", expectedButtonsIds),
            allButtonsIds.containsAll(expectedButtonsIds));
        Set<String> invisibleButtons =
            Sets.<String>newHashSet(CollectionUtils.subtract(expectedButtonsIds, allButtonsIds));
        invisibleButtons.forEach(id -> assertNull(waitAndFindElement(buttonsLayout, By.id(id))));
    }

    /**
     * Finds label class under given element and verifies it's text.
     *
     * @param webElement   given element
     * @param expectedText expected text
     */
    //TODO {nlevyankov} move to rup-vaadin
    void assertWebElementText(WebElement webElement, String expectedText) {
        assertEquals(expectedText, assertWebElement(webElement, By.className("v-label")).getText());
    }

    void loginAsManager() {
        openAppPage("fda_manager@copyright.com");
    }

    void loginAsSpecialist() {
        openAppPage("fda_spec@copyright.com");
    }

    void loginAsViewOnly() {
        openAppPage("fda_view@copyright.com");
    }

    WebElement selectUsagesTab() {
        return selectTab("Usages");
    }

    WebElement selectScenariosTab() {
        return selectTab("Scenarios");
    }

    void assertUsagesFilterEmpty(WebElement filterWidget) {
        assertBatchesFilterEmpty(filterWidget);
        assertRroFilterEmpty(filterWidget);
        assertPaymentDateFilterEmpty(filterWidget);
        assertFiscalYearFilterEmpty(filterWidget);
        assertFalse(isElementEnabled(assertWebElement(filterWidget, "Apply")));
    }

    void verifyErrorWindow(Map<String, String> fieldNameToErrorMessageMap) {
        WebElement errorWindow = assertWebElement(By.className("validation-error-window"));
        WebElement errorContent = assertWebElement(By.className("validation-error-content"));
        List<WebElement> errorFields = findElements(errorContent, By.tagName("b"));
        List<WebElement> errorMessages = findElements(errorContent, By.tagName(HTML_SPAN_TAG_NAME));
        int errorsSize = CollectionUtils.size(fieldNameToErrorMessageMap);
        assertEquals(errorsSize, CollectionUtils.size(errorFields));
        assertEquals(errorsSize, CollectionUtils.size(errorMessages));
        assertTrue(errorFields.stream()
            .map(WebElement::getText)
            .collect(Collectors.toList())
            .containsAll(fieldNameToErrorMessageMap.keySet()));
        assertTrue(errorMessages.stream()
            .map(WebElement::getText)
            .collect(Collectors.toList())
            .containsAll(fieldNameToErrorMessageMap.values()));
        clickButtonAndWait(errorWindow, "Ok");
    }

    void assertTableSorting(WebElement table, String... sortableColumns) {
        WebElement header = assertWebElement(table, By.className(V_TABLE_HEADER_CLASS_NAME));
        List<WebElement> columns = findElements(header, By.className("v-table-header-sortable"));
        assertEquals(sortableColumns.length, CollectionUtils.size(columns));
        columns.forEach(column -> {
            clickElementAndWait(column);
            assertWebElement(header, By.className("v-table-header-cell-asc"));
            clickElementAndWait(column);
            assertWebElement(header, By.className("v-table-header-cell-desc"));
            clickElementAndWait(column);
            ArrayUtils.contains(sortableColumns, column.getText());
        });
    }

    void applyCurrentDateForDateField(WebElement dateElement) {
        clickElementAndWait(assertWebElement(dateElement, By.className("v-datefield-button")));
        WebElement calendarPanel = assertWebElement(By.className("v-datefield-calendarpanel"));
        WebElement currentDateSlot =
            assertWebElement(calendarPanel, By.className("v-datefield-calendarpanel-day-today"));
        clickElementAndWait(currentDateSlot);
    }


    void applyFilters(WebElement filterWidget, UsageBatchInfo usageBatchInfo) {
        applyBatchesFilter(filterWidget, usageBatchInfo.getName());
        applyRrosFilter(filterWidget, usageBatchInfo.getRro());
        WebElement filterButtonsLayout = assertWebElement(filterWidget, "filter-buttons");
        clickButtonAndWait(filterButtonsLayout, "Apply");
    }

    void applyBatchesFilter(WebElement filterWidget, String selectItem) {
        applyItemFilter(filterWidget, "Batches", "batches-filter-window", "Batches filter", selectItem);
    }

    void applyRrosFilter(WebElement filterWidget, String selectItem) {
        applyItemFilter(filterWidget, "RROs", "rightsholders-filter-window", "RROs filter", selectItem);
    }

    private void applyItemFilter(WebElement filterWidget, String buttonId, String windowId, String windowCaption,
                                 String selectItem) {
        WebElement filterElement = assertWebElement(filterWidget, buttonId);
        clickElement(filterElement, HTML_SPAN_TAG_NAME, buttonId);
        WebElement filterWindow = assertWebElement(By.id(windowId));
        assertWebElement(filterWindow, HTML_DIV_TAG_NAME, windowCaption);
        WebElement item = assertWebElement(filterWindow, By.className(V_CHECKBOX_CLASS_NAME));
        clickElement(item, HTML_LABEL_TAG_NAME, selectItem);
        assertWebElement(filterWindow, "Close");
        assertWebElement(filterWindow, "Clear");
        clickButton(filterWindow, "Save");
    }

    private void assertFiscalYearFilterEmpty(WebElement filterWidget) {
        WebElement fiscalYearFilterInput = assertWebElement(filterWidget, By.className("v-filterselect-input"));
        assertEquals(StringUtils.EMPTY, fiscalYearFilterInput.getText());
    }

    private void assertPaymentDateFilterEmpty(WebElement filterWidget) {
        WebElement paymentDateFilter = assertWebElement(filterWidget, "payment-date-filter");
        assertEquals(StringUtils.EMPTY, assertWebElement(paymentDateFilter, By.className("v-textfield")).getText());
    }

    private void assertRroFilterEmpty(WebElement filterWidget) {
        WebElement rightsholdersFilter = assertWebElement(filterWidget, "rightsholders-filter");
        assertWebElement(rightsholdersFilter, HTML_DIV_TAG_NAME, "(0)");
    }

    private void assertBatchesFilterEmpty(WebElement filterWidget) {
        WebElement batchesFilter = assertWebElement(filterWidget, "batches-filter");
        assertWebElement(batchesFilter, HTML_DIV_TAG_NAME, "(0)");
    }

    private WebElement selectTab(String caption) {
        WebElement tabContainer = assertWebElement(By.id(Cornerstone.MAIN_TABSHEET));
        WebElement tab = getTab(tabContainer, caption);
        requireNonNull(tab, String.format("%s tab was not found on UI", caption));
        waitWhileInteractionWillFinished();
        return tab;
    }

    private void openAppPage(String userName) {
        openPage("http://localhost:22000/dist-foreign-ui/", userName, "`123qwer");
    }
}
