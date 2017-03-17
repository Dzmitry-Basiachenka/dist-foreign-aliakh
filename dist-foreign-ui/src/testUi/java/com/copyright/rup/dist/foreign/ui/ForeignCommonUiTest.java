package com.copyright.rup.dist.foreign.ui;

import static com.google.common.base.Preconditions.checkNotNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.test.CommonUiTest;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Common class for UI tests.
 * Provides functionality for logging in/out, setting up tests, taking screenshots if test fails.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/23/17
 *
 * @author Darya Baraukova
 */
public class ForeignCommonUiTest extends CommonUiTest {

    /**
     * Identifier for "Load" usage button.
     */
    protected static final String LOAD_USAGE_BUTTON_ID = "Load";
    /**
     * Identifier for usage layout.
     */
    protected static final String USAGE_LAYOUT_ID = "usages-layout";
    /**
     * Identifier for usage table.
     */
    protected static final String USAGE_TABLE_ID = "usages-table";
    /**
     * &lt;b&gt; tag name.
     */
    protected static final String HTML_B_TAG_NAME = "b";
    /**
     * Identifier for "Upload" button.
     */
    protected static final String UPLOAD_BUTTON_ID = "Upload";
    /**
     * Identifier for "Add to scenario" button.
     */
    protected static final String ADD_TO_SCENARIO_BUTTON_ID = "Add_To_Scenario";
    /**
     * Identifier for "Ok" button.
     */
    protected static final String OK_BUTTON_ID = "Ok";
    /**
     * Identifier for "Verify" button.
     */
    protected static final String VERIFY_BUTTON_ID = "Verify";
    /**
     * Identifier for 'Apply' button.
     */
    protected static final String APPLY_BUTTON_ID = "Apply";
    /**
     * Identifier for 'Close' button.
     */
    protected static final String CLOSE_BUTTON_ID = "Close";
    /**
     * Identifier for 'Cancel' button.
     */
    protected static final String CANCEL_BUTTON_ID = "Cancel";
    /**
     * Identifier for 'Save' button.
     */
    protected static final String SAVE_BUTTON_ID = "Save";
    /**
     * Identifier for usage filter widget.
     */
    protected static final String USAGE_FILTER_WIDGET_ID = "usages-filter-widget";
    /**
     * Identifier for batches filter.
     */
    protected static final String BATCHES_FILTER_ID = "batches-filter";
    /**
     * Identifier for RRO filter.
     */
    protected static final String RRO_FILTER_ID = "rightsholders-filter";
    /**
     * Identifier for payment date filter.
     */
    protected static final String PAYMENT_DATE_FILTER_ID = "payment-date-filter";

    private static final String APP_URL = "http://localhost:22000/dist-foreign-ui/";
    private static final Dimension WINDOW_DIMENSION = new Dimension(1280, 800);
    private static final String PASSWORD = "`123qwer";

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
        getDriver().manage().window().setSize(WINDOW_DIMENSION);
    }

    /**
     * Logs into application with view only permissions.
     */
    protected void loginAsViewOnly() {
        openAppPage(ForeignCredentials.VIEW_ONLY);
    }

    /**
     * Logs in into application as Distribution Manager.
     */
    protected void loginAsManager() {
        openAppPage(ForeignCredentials.MANAGER);
    }

    /**
     * Logs in into application as Distribution Specialist.
     */
    protected void loginAsSpecialist() {
        openAppPage(ForeignCredentials.SPECIALIST);
    }

    /**
     * Logs out.
     *
     * @return {@code true} if logout button was clicked, {@code false} if logout button was not found.
     */
    protected boolean logOut() {
        WebElement logOutButton = findElementById(Cornerstone.USER_WIDGET_LOGOUT_BUTTON);
        if (null != logOutButton) {
            logOutButton.click();
            return true;
        }
        return false;
    }

    /**
     * @return "Usages" tab.
     */
    protected WebElement selectUsagesTab() {
        WebElement usagesTab = waitAndGetTab(findElementById(Cornerstone.MAIN_TABSHEET), "Usages");
        clickElementAndWait(usagesTab);
        return usagesTab;
    }

    /**
     * @return "Scenarios" tab.
     */
    protected WebElement selectScenariosTab() {
        WebElement scenariosTab = waitAndGetTab(findElementById(Cornerstone.MAIN_TABSHEET), "Scenarios");
        clickElementAndWait(scenariosTab);
        return scenariosTab;
    }

    /**
     * Finds element by locating mechanism inside parent element and verifies it for {@code null}.
     *
     * @param parentElement parent element
     * @param id            component id
     * @return instance of {@link WebElement}
     */
    protected WebElement assertElement(WebElement parentElement, String id) {
        return checkNotNull(waitAndFindElement(parentElement, By.id(id)));
    }

    /**
     * Verifies columns headers for the given table.
     *
     * @param table           table to verify
     * @param expectedHeaders expected columns headers in the order they appear on UI
     */
    protected void verifyTableColumns(WebElement table, String... expectedHeaders) {
        WebElement tableHeader = findElement(table, By.className(V_TABLE_HEADER_CLASS_NAME));
        List<WebElement> headers = findElements(tableHeader, By.className(V_TABLE_CAPTION_CONTAINER_CLASS_NAME));
        assertEquals(expectedHeaders.length, headers.size());
        for (int i = 0; i < expectedHeaders.length; i++) {
            assertEquals(expectedHeaders[i], getInnerHtml(headers.get(i)));
        }
    }

    /**
     * Applies current date into date element.
     *
     * @param dateElement date element
     */
    protected void applyCurrentDateForDateField(WebElement dateElement) {
        waitAndFindElement(dateElement, By.className("v-datefield-button")).click();
        WebElement calendarPanel = waitAndFindElement(By.className("v-datefield-calendarpanel"));
        WebElement currentDateSlot =
            waitAndFindElement(calendarPanel, By.className("v-datefield-calendarpanel-day-today"));
        clickElementAndWait(currentDateSlot);
    }

    /**
     * Asserts that usage filters are empty, "Apply" button is disabled and usages table is empty.
     *
     * @param filterWidget {@link WebElement} representing filter widget
     * @param usagesTable  {@link WebElement} representing usages table
     */
    protected void assertUsagesFilterEmpty(WebElement filterWidget, WebElement usagesTable) {
        assertBatchesFilterEmpty(filterWidget);
        assertRroFilterEmpty(filterWidget);
        assertPaymentDateFilterEmpty(filterWidget);
        assertFiscalYearFilterEmpty(filterWidget);
        assertApplyButtonDisabled(filterWidget);
        assertUsagesTableEmpty(usagesTable);
    }

    /**
     * Choose item in filter window and click "Save" button.
     *
     * @param filterWidget   {@link WebElement} representing filter widget
     * @param filterId       filter identifier
     * @param filterWindowId filter window identifier
     * @param item           item to choose
     */
    protected void saveFilter(WebElement filterWidget, String filterId, String filterWindowId, String item) {
        WebElement itemsFilter = findElement(filterWidget, By.id(filterId));
        assertNotNull(itemsFilter);
        clickElementAndWait(itemsFilter);
        WebElement filterWindow = findElementById(filterWindowId);
        assertNotNull(filterWindow);
        WebElement label = findElementByText(filterWindow, HTML_LABEL_TAG_NAME, item);
        assertNotNull(label);
        clickElementAndWait(label);
        clickButtonAndWait(filterWindow, SAVE_BUTTON_ID);
    }

    /**
     * Asserts that usages table is empty.
     *
     * @param usagesTable {@link WebElement} representing usages table
     */
    protected void assertUsagesTableEmpty(WebElement usagesTable) {
        String backgroundImage =
            waitAndFindElement(usagesTable, By.className("v-scrollable")).getCssValue("background-image");
        assertTrue(backgroundImage.contains("img/empty_usages_table.png"));
    }

    /**
     * Assert that usages table is not empty.
     *
     * @param usagesTable {@link WebElement} representing usages table
     * @param rowsCount   count of Usages rows in table
     */
    protected void assertUsagesTableNotEmpty(WebElement usagesTable, int rowsCount) {
        assertNotNull(usagesTable);
        WebElement usagesTableBody = waitAndFindElement(usagesTable, By.className(V_TABLE_BODY_CLASS_NAME));
        assertNotNull(usagesTableBody);
        List<WebElement> rows = findElements(usagesTableBody, By.tagName(HTML_TR_TAG_NAME));
        assertEquals(rowsCount, rows.size());
    }

    /**
     * Gets attribute "value" for given element.
     *
     * @param element instance of {@link WebElement}
     * @return string representation of element value
     */
    protected String getValueAttribute(WebElement element) {
        assertNotNull(element);
        return element.getAttribute("value");
    }

    private void openAppPage(ForeignCredentials credentials) {
        openPage(APP_URL, credentials.getUserName(), credentials.getPassword());
    }

    private void assertApplyButtonDisabled(WebElement filterWidget) {
        WebElement applyButton = assertElement(filterWidget, APPLY_BUTTON_ID);
        assertTrue(applyButton.getAttribute("class").contains("v-disabled"));
    }

    private void assertFiscalYearFilterEmpty(WebElement filterWidget) {
        WebElement fiscalYearFilterInput = findElement(filterWidget, By.className("v-filterselect-input"));
        assertEquals(StringUtils.EMPTY, getValueAttribute(fiscalYearFilterInput));
    }

    private void assertPaymentDateFilterEmpty(WebElement filterWidget) {
        WebElement paymentDateFilter = assertElement(filterWidget, PAYMENT_DATE_FILTER_ID);
        WebElement paymentDateField = findElement(paymentDateFilter, By.className("v-textfield"));
        assertNotNull(paymentDateField);
        assertEquals(StringUtils.EMPTY, getValueAttribute(paymentDateField));
    }

    private void assertRroFilterEmpty(WebElement filterWidget) {
        WebElement rightsholdersFilter = assertElement(filterWidget, RRO_FILTER_ID);
        assertNotNull(findElementByText(rightsholdersFilter, HTML_DIV_TAG_NAME, "(0)"));
        WebElement rightsholderFilterButton = findElementByText(rightsholdersFilter, HTML_SPAN_TAG_NAME, "RROs");
        assertNotNull(rightsholderFilterButton);
    }

    private void assertBatchesFilterEmpty(WebElement filterWidget) {
        WebElement batchesFilter = assertElement(filterWidget, BATCHES_FILTER_ID);
        assertNotNull(findElementByText(batchesFilter, HTML_DIV_TAG_NAME, "(0)"));
        WebElement batchesFilterButton = findElementByText(batchesFilter, HTML_SPAN_TAG_NAME, "Batches");
        assertNotNull(batchesFilterButton);
    }

    /**
     * Enum with credentials for logging into application.
     */
    protected enum ForeignCredentials {
        /**
         * Credentials for view only role.
         */
        VIEW_ONLY("fda_view@copyright.com", PASSWORD),
        /**
         * Credentials for manager role.
         */
        MANAGER("fda_manager@copyright.com", PASSWORD),
        /**
         * Credentials for specialist role.
         */
        SPECIALIST("fda_spec@copyright.com", PASSWORD);

        private String userName;
        private String password;

        /**
         * Constructor.
         *
         * @param userName user name
         * @param password password
         */
        ForeignCredentials(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        String getUserName() {
            return userName;
        }

        String getPassword() {
            return password;
        }
    }
}
