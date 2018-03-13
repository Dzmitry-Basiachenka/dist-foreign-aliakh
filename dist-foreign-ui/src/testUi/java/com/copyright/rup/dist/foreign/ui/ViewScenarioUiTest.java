package com.copyright.rup.dist.foreign.ui;

import com.copyright.rup.common.test.integ.db.embedded.UpdateDatabaseForClassTestExecutionListener;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@TestExecutionListeners(value = UpdateDatabaseForClassTestExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class ViewScenarioUiTest extends ForeignCommonUiTestProvider {

    private final String[] rightsholder1 = {"1000002797", "British Film Institute (BFI)", "1000002797",
        "British Film Institute (BFI)", "1,279.24", "409.36", "869.88", "32.0"};
    private final String[] rightsholder2 = {"1000008666", "CCH", "1000008666", "CCH", "20,467.84",
        "6,549.71", "13,918.13", "32.0"};
    private final String[] rightsholder3 = {"1000009997", "IEEE - Inst of Electrical and Electronics Engrs",
        "1000009997", "IEEE - Inst of Electrical and Electronics Engrs", "6,396.19", "2,046.78", "4,349.41", "32.0"};

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
        WebElement table = assertWebElement(window, "rightsholders-totals-table");
        Map<String, List<String[]>> searchMap = new HashMap<>();
        searchMap.put("British Film Institute (BFI)", Collections.singletonList(rightsholder1));
        searchMap.put("BriTIsh film Institute (bfi)", Collections.singletonList(rightsholder1));
        searchMap.put("CCH", Collections.singletonList(rightsholder2));
        searchMap.put("T", Lists.newArrayList(rightsholder1, rightsholder3));
        searchMap.put("1000009997", Collections.singletonList(rightsholder3));
        searchMap.put("100000", Lists.newArrayList(rightsholder1, rightsholder2, rightsholder3));
        searchMap.put("97", Lists.newArrayList(rightsholder1, rightsholder3));
        assertSearch(searchToolbar, table, searchMap);
    }

    private void assertButtonLayout(WebElement buttonLayout) {
        Set<String> actualButtons = buttonLayout.findElements(By.className("v-button")).stream()
            .map(webElement -> webElement.getAttribute("id"))
            .collect(Collectors.toSet());
        assertButtonsToolbar(buttonLayout, actualButtons, Sets.newHashSet("Export", "Close"));
    }

    private void verifyTable(WebElement table) {
        assertTableHeaderElements(table, "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Amt in USD",
            "Service Fee Amount", "Net Amt in USD", "Service Fee %");
        List<WebElement> rows = assertTableRowElements(table, 3);
        assertTableRowElements(rows.get(0), rightsholder1);
        assertTableRowElements(rows.get(1), rightsholder2);
        assertTableRowElements(rows.get(2), rightsholder3);
        assertTableSorting(table, "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Amt in USD",
            "Service Fee Amount", "Net Amt in USD", "Service Fee %");
        assertTableFooterElements(table, "Totals", StringUtils.SPACE, StringUtils.SPACE, StringUtils.SPACE, "28,143.27",
            "9,005.85", "19,137.42", StringUtils.SPACE);
    }

    private WebElement openViewScenarioWindow() {
        WebElement scenarioTab = selectScenariosTab();
        WebElement selectedScenario = waitAndFindElementByText(scenarioTab, HTML_DIV_TAG_NAME, "Scenario for viewing");
        clickElementAndWait(selectedScenario);
        clickElementAndWait(assertWebElement(scenarioTab, "View"));
        return assertWebElement(By.id("view-scenario-widget"));
    }
}
