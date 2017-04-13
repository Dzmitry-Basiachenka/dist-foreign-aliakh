package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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

    private RightsholderTotalsHolder rightsholderTotalsHolder1;
    private RightsholderTotalsHolder rightsholderTotalsHolder2;
    private RightsholderTotalsHolder rightsholderTotalsHolder3;

    @Test
    // Test cases IDs: 'ed87b9c8-5880-4bc0-a3aa-6ec733dcf491', 'c3475cfb-eb5d-4dea-96ad-d6a1228f177b',
    // 'b065b13f-2633-4cc0-b1f9-0d735aefae05'
    public void testViewScenarioWindow() {
        loginAsViewOnly();
        WebElement scenarioWindow = openViewScenarioWindow();
        verifySearchToolbar(scenarioWindow);
        verifyTable(scenarioWindow);
        clickElementAndWait(assertElement(scenarioWindow, By.id(CLOSE_BUTTON_ID)));
        assertNull(waitAndFindElement(By.id("upload-error-window")));
    }

    @Test
    // Test case ID: 'b65f8146-c002-4675-9108-1bb8eda93527'
    public void testSearch() {
        loginAsSpecialist();
        WebElement window = openViewScenarioWindow();
        WebElement searchToolbar = assertElement(window, By.id(SEARCH_TOOLBAR_ID));
        WebElement searchField = assertElement(searchToolbar, By.className("v-textfield"));
        WebElement searchButton = assertElement(searchToolbar, By.className(SEARCH_BUTTON_ID));
        WebElement table = getRightsholdersTotalsTable(window);
        buildRightsholderTotalsHolders();
        verifySearchByRightsholderName(searchField, searchButton, table);
        verifySearchByRightsholderAccountNumber(searchField, searchButton, table);
    }

    private void verifySearchByRightsholderName(WebElement searchField, WebElement searchButton, WebElement table) {
        applySearch(searchField, searchButton, "British Film Institute (BFI)");
        verifyTableRows(table, rightsholderTotalsHolder1);
        applySearch(searchField, searchButton, "BriTIsh film Institute (bfi)");
        verifyTableRows(table, rightsholderTotalsHolder1);
        applySearch(searchField, searchButton, "CCH");
        verifyTableRows(table, rightsholderTotalsHolder2);
        applySearch(searchField, searchButton, "T");
        verifyTableRows(table, rightsholderTotalsHolder1, rightsholderTotalsHolder3);
    }

    private void verifySearchByRightsholderAccountNumber(WebElement searchField, WebElement searchButton,
                                                         WebElement table) {
        applySearch(searchField, searchButton, "1000009997");
        verifyTableRows(table, rightsholderTotalsHolder3);
        applySearch(searchField, searchButton, "100000");
        verifyTableRows(table, rightsholderTotalsHolder1, rightsholderTotalsHolder2, rightsholderTotalsHolder3);
        applySearch(searchField, searchButton, "97");
        verifyTableRows(table, rightsholderTotalsHolder1, rightsholderTotalsHolder3);
    }

    private void applySearch(WebElement searchField, WebElement searchButton, String searchValue) {
        sendKeysToInput(searchField, searchValue);
        clickElementAndWait(searchButton);
    }

    private void verifySearchToolbar(WebElement window) {
        WebElement searchToolbar = assertElement(window, By.id(SEARCH_TOOLBAR_ID));
        WebElement prompt = assertElement(searchToolbar, By.className("v-textfield-prompt"));
        assertEquals("Enter Rightsholder Name/Account #", getValueAttribute(prompt));
        assertElement(searchToolbar, By.className(SEARCH_BUTTON_ID));
        assertElement(searchToolbar, By.className("button-clear"));
    }

    private void verifyTable(WebElement window) {
        WebElement table = getRightsholdersTotalsTable(window);
        verifyTableColumns(table, "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Amt in USD",
            "Service Fee Amount", "Net Amt in USD", "Service Fee %");
        buildRightsholderTotalsHolders();
        verifyTableRows(table, rightsholderTotalsHolder1, rightsholderTotalsHolder2, rightsholderTotalsHolder3);
        verifyTableSorting(table, "RH Account #", "RH Name", "Amt in USD", "Service Fee Amount", "Net Amt in USD");
        verifyFooter(table);
    }

    private WebElement getRightsholdersTotalsTable(WebElement window) {
        return assertElement(window, By.id("rightsholders-totals-table"));
    }

    private void verifyTableRows(WebElement table, RightsholderTotalsHolder... rightsholderTotalsHolders) {
        List<WebElement> rows = findElements(assertElement(table, By.className(V_TABLE_BODY_CLASS_NAME)),
            By.tagName(HTML_TR_TAG_NAME));
        assertEquals(rightsholderTotalsHolders.length, CollectionUtils.size(rows));
        for (int i = 0; i < rightsholderTotalsHolders.length; i++) {
            verifyRightsholderTotalsHolder(rows.get(i), rightsholderTotalsHolders[i]);
        }
    }

    private void verifyRightsholderTotalsHolder(WebElement row, RightsholderTotalsHolder rightsholderTotalsHolder) {
        List<WebElement> cells = findElements(row, By.className(V_TABLE_CELL_CONTENT_CLASS_NAME));
        assertEquals(rightsholderTotalsHolder.getRightsholderAccountNumber().toString(), cells.get(0).getText());
        assertEquals(rightsholderTotalsHolder.getRightsholderName(), cells.get(1).getText());
        assertEquals(StringUtils.EMPTY, cells.get(2).getText());
        assertEquals(StringUtils.EMPTY, cells.get(3).getText());
        assertEquals(String.format("%,.2f", rightsholderTotalsHolder.getGrossTotal()), cells.get(4).getText());
        assertEquals(String.format("%,.2f", rightsholderTotalsHolder.getServiceFeeTotal()), cells.get(5).getText());
        assertEquals(String.format("%,.2f", rightsholderTotalsHolder.getNetTotal()), cells.get(6).getText());
        assertEquals(StringUtils.EMPTY, cells.get(7).getText());
    }

    private void verifyFooter(WebElement table) {
        WebElement footer = assertElement(table, By.className("v-table-footer-wrap"));
        List<WebElement> cells = findElements(footer, By.className(V_TABLE_FOOTER_CONTAINER_CLASS_NAME));
        assertEquals("Totals", cells.get(0).getText());
        assertEquals(StringUtils.SPACE, cells.get(1).getText());
        assertEquals(StringUtils.SPACE, cells.get(2).getText());
        assertEquals(StringUtils.SPACE, cells.get(3).getText());
        assertEquals("10,530.00", cells.get(4).getText());
        assertEquals(StringUtils.SPACE, cells.get(5).getText());
        assertEquals("9,000.00", cells.get(6).getText());
        assertEquals(StringUtils.SPACE, cells.get(7).getText());
    }

    private WebElement openViewScenarioWindow() {
        WebElement scenarioTab = selectScenariosTab();
        WebElement selectedScenario = waitAndFindElementByText(scenarioTab, HTML_DIV_TAG_NAME, "Scenario for viewing");
        clickElementAndWait(selectedScenario);
        clickElementAndWait(assertElement(scenarioTab, By.id(VIEW_BUTTON_ID)));
        return assertElement(By.id("view-scenario-widget"));
    }

    private void buildRightsholderTotalsHolders() {
        rightsholderTotalsHolder1 = new RightsholderTotalsHolder();
        rightsholderTotalsHolder1.setRightsholderName("British Film Institute (BFI)");
        rightsholderTotalsHolder1.setRightsholderAccountNumber(1000002797L);
        rightsholderTotalsHolder1.setGrossTotal(BigDecimal.valueOf(480).setScale(2, RoundingMode.HALF_UP));
        rightsholderTotalsHolder1.setNetTotal(BigDecimal.valueOf(1050).setScale(2, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder2 = new RightsholderTotalsHolder();
        rightsholderTotalsHolder2.setRightsholderName("CCH");
        rightsholderTotalsHolder2.setRightsholderAccountNumber(1000008666L);
        rightsholderTotalsHolder2.setGrossTotal(BigDecimal.valueOf(7650).setScale(2, RoundingMode.HALF_UP));
        rightsholderTotalsHolder2.setNetTotal(BigDecimal.valueOf(4550).setScale(2, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder3 = new RightsholderTotalsHolder();
        rightsholderTotalsHolder3.setRightsholderName("IEEE - Inst of Electrical and Electronics Engrs");
        rightsholderTotalsHolder3.setRightsholderAccountNumber(1000009997L);
        rightsholderTotalsHolder3.setGrossTotal(BigDecimal.valueOf(2400).setScale(2, RoundingMode.HALF_UP));
        rightsholderTotalsHolder3.setNetTotal(BigDecimal.valueOf(3400).setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
