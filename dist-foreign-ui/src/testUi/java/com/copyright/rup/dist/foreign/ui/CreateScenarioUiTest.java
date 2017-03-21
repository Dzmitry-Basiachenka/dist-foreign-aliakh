package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

    private static final String SCENARIO_NAME = "Scenario";
    private static final String DESCRIPTION = "Description";
    private static final BigDecimal GROSS_TOTAL = new BigDecimal("13461.54").setScale(10, RoundingMode.HALF_UP);
    private static final BigDecimal REPORTED_TOTAL = new BigDecimal("2500").setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal NET_TOTAL = BigDecimal.ZERO.setScale(10, RoundingMode.HALF_UP);
    private UsageBatchInfo invalidUsageBatch = new UsageBatchInfo("CADRA_11Dec16", "01/11/2017", "FY2017",
        "2000017004 - Access Copyright, The Canadian Copyright Agency");
    private UsageBatchInfo validUsageBatch = new UsageBatchInfo("CADRA_11Dec16", "01/11/2017", "FY2017",
        "7000813806 - CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil");
    private Scenario scenario;

    @Autowired
    private IScenarioService scenarioService;

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
        WebElement createScenarioWindow = waitAndFindElement(By.id("create-scenario-window"));
        assertNotNull(createScenarioWindow);
        verifyCreateScenarioWindowFields(createScenarioWindow);
        verifyCreateScenarioWindowButtons(createScenarioWindow);
        WebElement nameTextField = assertElement(createScenarioWindow, "scenario-name");
        sendKeysToInput(nameTextField, SCENARIO_NAME);
        WebElement descriptionTextField = assertElement(createScenarioWindow, "scenario-description");
        sendKeysToInput(descriptionTextField, DESCRIPTION);
        clickButtonAndWait(createScenarioWindow, "Confirm");
        WebElement scenarioTab = waitAndGetTab(findElementById(Cornerstone.MAIN_TABSHEET), "Scenarios");
        assertNotNull(scenarioTab);
        verifyScenariosTable();
        selectUsagesTab();
        assertUsagesFilterEmpty(waitAndFindElement(By.id(USAGE_FILTER_WIDGET_ID)),
            waitAndFindElement(By.id(USAGE_TABLE_ID)));
        List<Scenario> scenarios = scenarioService.getScenarios().stream()
            .filter(scenario -> SCENARIO_NAME.equals(scenario.getName())).collect(Collectors.toList());
        assertEquals(1, CollectionUtils.size(scenarios));
        scenario = scenarios.get(0);
        verifyScenario();
    }

    @After
    public void tearDown() {
        if (null != scenario) {
            scenarioService.deleteScenario(scenario.getId());
        }
    }

    private void verifyScenario() {
        assertEquals(SCENARIO_NAME, scenario.getName());
        assertEquals(DESCRIPTION, scenario.getDescription());
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        assertEquals(GROSS_TOTAL, scenario.getGrossTotal());
        assertEquals(NET_TOTAL, scenario.getNetTotal());
        assertEquals(REPORTED_TOTAL, scenario.getReportedTotal());
    }

    private WebElement verifyCreateScenarioWindowFields(WebElement createScenarioWindow) {
        assertNotNull(waitAndFindElement(createScenarioWindow, By.className("v-caption-scenario-name")));
        WebElement nameTextField = assertElement(createScenarioWindow, "scenario-name");
        String currentDate =
            LocalDate.now().format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
        assertEquals("FAS Distribution " + currentDate, nameTextField.getAttribute("value"));
        assertNotNull(waitAndFindElement(createScenarioWindow, By.className("v-caption-scenario-description")));
        WebElement descriptionTextField = assertElement(createScenarioWindow, "scenario-description");
        assertEquals(StringUtils.EMPTY, descriptionTextField.getText());
        return createScenarioWindow;
    }

    private void verifyCreateScenarioWindowButtons(WebElement createScenarioWindow) {
        assertElement(createScenarioWindow, CANCEL_BUTTON_ID);
        assertElement(createScenarioWindow, "Confirm");
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
