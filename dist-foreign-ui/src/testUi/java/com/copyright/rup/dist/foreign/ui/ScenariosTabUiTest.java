package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Selenium test to verify UI of 'Scenarios' tab.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 3/16/17
 *
 * @author Aliaksandr Radkevich
 */
public class ScenariosTabUiTest extends ForeignCommonUiTest {

    private ScenarioInfo scenario1 = new ScenarioInfo("Scenario 03/16/2017", "03/16/2017");
    private ScenarioInfo scenario2 = new ScenarioInfo("Scenario 03/15/2017", "03/15/2017");
    private ScenarioInfo scenario3 = new ScenarioInfo("Scenario 02/15/2017", "02/15/2017");
    private ScenarioInfo scenario4 = new ScenarioInfo("Scenario name", "01/01/2017");

    @Test
    public void testVerifyScenariosTabSpecialist() {
        loginAsSpecialist();
        verifyScenariosTabSpecialist();
    }

    @Test
    public void testVerifyScenariosTabManager() {
        loginAsManager();
        verifyScenariosTabManagerAndViewOnly();
    }

    @Test
    public void testVerifyScenariosTabViewOnly() {
        loginAsViewOnly();
        verifyScenariosTabManagerAndViewOnly();
    }

    private WebElement getScenarioTab() {
        WebElement scenariosTab = waitAndGetTab(findElementById(Cornerstone.MAIN_TABSHEET), "Scenario");
        clickElementAndWait(scenariosTab);
        return scenariosTab;
    }

    private void verifyScenariosTabManagerAndViewOnly() {
        WebElement scenarioTab = getScenarioTab();
        assertNotNull(scenarioTab);
        WebElement buttonsLayout = assertElement(scenarioTab, "scenarios-buttons");
        List<WebElement> buttons = findElements(buttonsLayout, By.className("v-button"));
        assertTrue(CollectionUtils.isEmpty(buttons));
        verifyScenariosTable(assertElement(scenarioTab, "scenarios-table"));
        verifyMetadataPanel(assertElement(scenarioTab, "scenarios-metadata"));
    }

    private void verifyScenariosTabSpecialist() {
        WebElement scenarioTab = getScenarioTab();
        assertNotNull(scenarioTab);
        WebElement buttonsLayout = assertElement(scenarioTab, "scenarios-buttons");
        List<WebElement> buttons = findElements(buttonsLayout, By.className("v-button"));
        assertEquals(1, buttons.size());
        assertElement(buttonsLayout, "Delete");
        WebElement table = assertElement(scenarioTab, "scenarios-table");
        verifyScenariosTable(table);
        verifyMetadataPanel(assertElement(scenarioTab, "scenarios-metadata"));
        verifyEmptyMetadataPanel(scenarioTab, table);
    }

    private void verifyScenariosTable(WebElement table) {
        verifyTableColumns(table, "Name", "Create Date", "Status");
        verifyTableRows(table, scenario1, scenario2, scenario3, scenario4);
    }

    private void verifyEmptyMetadataPanel(WebElement scenarioTab, WebElement table) {
        WebElement selectedRow = findElement(table, By.className("v-selected"));
        assertNotNull(selectedRow);
        clickElementAndWait(selectedRow);
        assertNull(findElement(table, By.className("v-selected")));
        WebElement metadataPanel = assertElement(scenarioTab, "scenarios-metadata");
        List<WebElement> labels = findElements(metadataPanel, By.className("v-label"));
        assertEquals(1, labels.size());
        assertEquals(StringUtils.EMPTY, labels.get(0).getText());
    }

    private void verifyMetadataPanel(WebElement metadataPanel) {
        List<WebElement> labels = findElements(metadataPanel, By.className("v-label"));
        assertEquals(5, labels.size());
        assertEquals("Owner: SYSTEM", labels.get(0).getText());
        assertEquals("Distribution Total: 90.00", labels.get(1).getText());
        assertEquals("Gross Total: 100.00", labels.get(2).getText());
        assertEquals("Reported Total: 110.00", labels.get(3).getText());
        assertEquals("Description: Scenario description", labels.get(4).getText());
    }

    private void verifyTableRows(WebElement table, ScenarioInfo... scenariosInfo) {
        List<WebElement> rows = findElements(findElement(table, By.className(V_TABLE_BODY_CLASS_NAME)),
            By.tagName(HTML_TR_TAG_NAME));
        assertEquals(scenariosInfo.length, rows.size());
        for (int i = 0; i < scenariosInfo.length; i++) {
            verifyScenario(rows.get(i), scenariosInfo[i]);
        }
    }

    private void verifyScenario(WebElement row, ScenarioInfo scenarioInfo) {
        List<WebElement> cells = findElements(row, By.className(V_TABLE_CELL_CONTENT_CLASS_NAME));
        assertEquals(scenarioInfo.name, cells.get(0).getText());
        assertEquals(scenarioInfo.createDate, cells.get(1).getText());
        assertEquals("IN_PROGRESS", cells.get(2).getText());
    }

    private static class ScenarioInfo {

        private String name;
        private String createDate;

        private ScenarioInfo(String name, String createDate) {
            this.name = name;
            this.createDate = createDate;
        }
    }
}
