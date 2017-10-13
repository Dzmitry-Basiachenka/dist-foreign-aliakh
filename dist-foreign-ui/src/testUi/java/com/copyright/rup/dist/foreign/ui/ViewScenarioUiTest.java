package com.copyright.rup.dist.foreign.ui;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UI test for scenario widget.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class ViewScenarioUiTest extends ForeignCommonUiTest {

    private String[] rightsholder1 = {"1000002797", "British Film Institute (BFI)", StringUtils.EMPTY,
        StringUtils.EMPTY, "480.00", "0.00", "1,050.00", StringUtils.EMPTY};
    private String[] rightsholder2 = {"1000008666", "CCH", StringUtils.EMPTY, StringUtils.EMPTY, "7,650.00", "0.00",
        "4,550.00", StringUtils.EMPTY};
    private String[] rightsholder3 = {"1000009997", "IEEE - Inst of Electrical and Electronics Engrs",
        StringUtils.EMPTY, StringUtils.EMPTY, "2,400.00", "0.00", "3,400.00", StringUtils.EMPTY};

    @Test
    // Test cases IDs: 'ed87b9c8-5880-4bc0-a3aa-6ec733dcf491', 'c3475cfb-eb5d-4dea-96ad-d6a1228f177b',
    // 'b065b13f-2633-4cc0-b1f9-0d735aefae05'
    public void testViewScenarioWindow() {
        loginAsViewOnly();
        WebElement scenarioWindow = openViewScenarioWindow();
        assertButtonLayout(assertWebElement(scenarioWindow, "scenario-buttons-layout"));
        assertSearchToolbar(scenarioWindow, "Enter Rightsholder Name/Account #");
        WebElement rightsholdersTable = assertWebElement(scenarioWindow, "rightsholders-totals-table");
        verifyTable(rightsholdersTable);
        clickButtonAndWait(scenarioWindow, "Close");
    }

    @Test
    // Test case ID: 'b65f8146-c002-4675-9108-1bb8eda93527'
    public void testSearch() {
        loginAsSpecialist();
        WebElement window = openViewScenarioWindow();
        WebElement searchToolbar = assertSearchToolbar(window, "Enter Rightsholder Name/Account #");
        WebElement searchField = assertWebElement(searchToolbar, By.className("v-textfield"));
        WebElement searchButton = assertWebElement(searchToolbar, By.className("button-search"));
        WebElement table = assertWebElement(window, "rightsholders-totals-table");
        verifySearchByRightsholderName(searchField, searchButton, table);
        verifySearchByRightsholderAccountNumber(searchField, searchButton, table);
    }

    private void assertButtonLayout(WebElement buttonLayout) {
        Set<String> actualButtons = buttonLayout.findElements(By.className("v-button")).stream()
            .map(webElement -> webElement.getAttribute("id"))
            .collect(Collectors.toSet());
        assertButtonsToolbar(buttonLayout, actualButtons, Sets.newHashSet("Export", "Close"));
    }

    private void verifySearchByRightsholderName(WebElement searchField, WebElement searchButton, WebElement table) {
        applySearch(searchField, searchButton, "British Film Institute (BFI)");
        List<WebElement> rows = assertTableRowElements(table, 1);
        assertTableRowElements(rows.get(0), rightsholder1);

        applySearch(searchField, searchButton, "BriTIsh film Institute (bfi)");
        rows = assertTableRowElements(table, 1);
        assertTableRowElements(rows.get(0), rightsholder1);

        applySearch(searchField, searchButton, "CCH");
        rows = assertTableRowElements(table, 1);
        assertTableRowElements(rows.get(0), rightsholder2);

        applySearch(searchField, searchButton, "T");
        rows = assertTableRowElements(table, 2);
        assertTableRowElements(rows.get(0), rightsholder1);
        assertTableRowElements(rows.get(1), rightsholder3);
    }

    private void verifySearchByRightsholderAccountNumber(WebElement searchField, WebElement searchButton,
                                                         WebElement table) {
        applySearch(searchField, searchButton, "1000009997");
        List<WebElement> rows = assertTableRowElements(table, 1);
        assertTableRowElements(rows.get(0), rightsholder3);

        applySearch(searchField, searchButton, "100000");
        rows = assertTableRowElements(table, 3);
        assertTableRowElements(rows.get(0), rightsholder1);
        assertTableRowElements(rows.get(1), rightsholder2);
        assertTableRowElements(rows.get(2), rightsholder3);

        applySearch(searchField, searchButton, "97");
        rows = assertTableRowElements(table, 2);
        assertTableRowElements(rows.get(0), rightsholder1);
        assertTableRowElements(rows.get(1), rightsholder3);
    }

    private void applySearch(WebElement searchField, WebElement searchButton, String searchValue) {
        sendKeysToInput(searchField, searchValue);
        clickElementAndWait(searchButton);
    }

    private void verifyTable(WebElement table) {
        assertTableHeaderElements(table, "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Amt in USD",
            "Service Fee Amount", "Net Amt in USD", "Service Fee %");
        List<WebElement> rows = assertTableRowElements(table, 3);
        assertTableRowElements(rows.get(0), rightsholder1);
        assertTableRowElements(rows.get(1), rightsholder2);
        assertTableRowElements(rows.get(2), rightsholder3);
        assertTableSorting(table, "RH Account #", "RH Name", "Amt in USD", "Service Fee Amount", "Net Amt in USD");
        assertTableFooterElements(table, "Totals", StringUtils.SPACE, StringUtils.SPACE, StringUtils.SPACE, "10,530.00",
            StringUtils.SPACE, "9,000.00", StringUtils.SPACE);
    }

    private WebElement openViewScenarioWindow() {
        WebElement scenarioTab = selectScenariosTab();
        WebElement selectedScenario = waitAndFindElementByText(scenarioTab, HTML_DIV_TAG_NAME, "Scenario for viewing");
        clickElementAndWait(selectedScenario);
        clickElementAndWait(assertWebElement(scenarioTab, "View"));
        return assertWebElement(By.id("view-scenario-widget"));
    }
}
