package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * UI test to verify 'Add to Scenario' functionality.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/17/17
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class CreateScenarioUiTest extends ForeignCommonUiTest {

    private UsageBatchInfo invalidUsageBatch = new UsageBatchInfo("CADRA_11Dec16", "01/11/2017", "FY2017",
        "2000017004 - Access Copyright, The Canadian Copyright Agency");
    private UsageBatchInfo validUsageBatch = new UsageBatchInfo("CADRA_11Dec16", "01/11/2017", "FY2017",
        "7000813806 - CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil");

    @Test
    // Test case ID: 'e4b0a048-51af-4c1c-91bd-2a199747ca34'
    public void testAddToScenarioWithoutSelectedUsages() {
        loginAsSpecialist();
        applyFilters(findElementById(USAGE_FILTER_WIDGET_ID), invalidUsageBatch);
        assertUsagesTableEmpty(waitAndFindElement(By.id(USAGE_TABLE_ID)));
        clickElementAndWait(waitAndFindElement(By.id(ADD_TO_SCENARIO_BUTTON_ID)));
        WebElement notificationWindow = waitAndFindElement(By.id("notification-window"));
        assertNotNull(notificationWindow);
        WebElement label = waitAndFindElement(notificationWindow, By.className("v-label"));
        assertNotNull(label);
        assertEquals("Please select at least one usage", label.getText());
        clickElementAndWait(waitAndFindElement(notificationWindow, By.id(OK_BUTTON_ID)));
    }

    @Test
    // Test case ID: '684bfcfe-5fbb-44e4-a4ea-1464baed35fd'
    public void testAddToScenarioWithSelectedUsages() {
        loginAsSpecialist();
        applyFilters(findElementById(USAGE_FILTER_WIDGET_ID), validUsageBatch);
        assertUsagesTableNotEmpty(waitAndFindElement(By.id(USAGE_TABLE_ID)), 1);
        clickElementAndWait(waitAndFindElement(By.id(ADD_TO_SCENARIO_BUTTON_ID)));
        WebElement createScenarioWindow = verifyFieldsCreateScenarioWindow();
        verifyButtonsCreateScenarioWindow(createScenarioWindow);
        WebElement scenarioTab = waitAndGetTab(findElementById(Cornerstone.MAIN_TABSHEET), "Scenarios");
        assertNotNull(scenarioTab);
        verifyScenariosTable();
        selectUsagesTab();
        assertUsagesFilterEmpty(waitAndFindElement(By.id(USAGE_FILTER_WIDGET_ID)),
            waitAndFindElement(By.id(USAGE_TABLE_ID)));
    }

    private WebElement verifyFieldsCreateScenarioWindow() {
        WebElement createScenarioWindow = waitAndFindElement(By.id("create-scenario-window"));
        assertNotNull(createScenarioWindow);
        assertNotNull(waitAndFindElement(createScenarioWindow, By.className("v-caption-scenario-name")));
        WebElement nameTextField = waitAndFindElement(createScenarioWindow, By.id("scenario-name"));
        assertNotNull(nameTextField);
        String currentDate =
            LocalDate.now().format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
        assertEquals("FAS Distribution " + currentDate, nameTextField.getAttribute("value"));
        assertNotNull(waitAndFindElement(createScenarioWindow, By.className("v-caption-scenario-description")));
        WebElement descriptionTextField = waitAndFindElement(createScenarioWindow, By.id("scenario-description"));
        assertNotNull(descriptionTextField);
        assertEquals(StringUtils.EMPTY, descriptionTextField.getText());
        return createScenarioWindow;
    }

    private void verifyButtonsCreateScenarioWindow(WebElement createScenarioWindow) {
        assertNotNull(waitAndFindElement(createScenarioWindow, By.id(CANCEL_BUTTON_ID)));
        WebElement confirmButton = waitAndFindElement(createScenarioWindow, By.id("Confirm"));
        assertNotNull(confirmButton);
        clickElementAndWait(confirmButton);
    }

    private void verifyScenariosTable() {
        WebElement scenariosTable = waitAndFindElement(By.id("scenarios-table"));
        assertNotNull(scenariosTable);
        WebElement selectedItem = waitAndFindElement(scenariosTable, By.className("v-selected"));
        assertNotNull(selectedItem);
    }

    private void applyFilters(WebElement filterWidget, UsageBatchInfo usageBatchInfo) {
        saveFilter(filterWidget, BATCHES_FILTER_ID, "batches-filter-window", usageBatchInfo.getName());
        saveFilter(filterWidget, RRO_FILTER_ID, "rightsholders-filter-window", usageBatchInfo.getRro());
        WebElement filterButtonsLayout = assertElement(filterWidget, "filter-buttons");
        clickButtonAndWait(filterButtonsLayout, APPLY_BUTTON_ID);
    }
}
