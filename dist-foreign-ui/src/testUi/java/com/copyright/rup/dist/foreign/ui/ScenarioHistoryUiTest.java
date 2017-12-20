package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Ui test for scenario history widget.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/19/2017
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class ScenarioHistoryUiTest extends ForeignCommonUiTest {

    private WebElement scenarioHistoryWigdet;

    @Override
    public void setUp() {
        super.setUp();
        loginAsViewOnly();
        scenarioHistoryWigdet = openScenarioHistoryWindow();
    }

    @Test
    // Test case ID: 8600d2bb-95bf-4049-bd6c-05e457fce50d
    public void testScenarioHistoryWindow() {
        assertEquals("History for Scenario 03/16/2017 scenario",
            assertWebElement(scenarioHistoryWigdet, By.className("v-window-header")).getText());
        verifyTable(assertWebElement(scenarioHistoryWigdet, By.className("scenario-history-table")));
    }

    private WebElement openScenarioHistoryWindow() {
        WebElement lastActionLayout =
            assertWebElement(selectScenariosTab(), By.className("v-slot-scenario-last-action"));
        clickElementAndWait(
            assertWebElement(assertWebElement(lastActionLayout, By.className("v-button-link")), By.tagName("span")));
        return assertWebElement(By.id("scenario-history-widget"));
    }

    private void verifyTable(WebElement table) {
        assertTableHeaderElements(table, "Type", "User", "Date", "Reason");
        assertTableSorting(table, new String[]{});
        List<WebElement> rows = assertTableRowElements(table, 3);
        assertTableRowElements(rows.get(0), "REJECTED", "manager@copyright.com", "03/18/2017 12:00 AM", "rejected");
        assertTableRowElements(rows.get(1), "SUBMITTED", "spec@copyright.com", "03/17/2017 12:00 AM", "submitted");
        assertTableRowElements(rows.get(2), "ADDED_USAGES", "spec@copyright.com", "03/16/2017 12:00 AM", "");
    }
}
