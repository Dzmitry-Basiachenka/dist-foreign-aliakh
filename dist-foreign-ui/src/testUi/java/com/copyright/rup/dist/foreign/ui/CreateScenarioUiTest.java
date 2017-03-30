package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
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
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
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
    private static final String CONFIRM_BUTTON_ID = "Confirm";
    private static final String SCENARIO_NAME_FIELD = "Scenario name";
    private static final String DESCRIPTION_FIELD = "Description";
    private static final String SCENARIO_NAME_FIELD_ID = "scenario-name";
    private UsageBatchInfo invalidUsageBatch = new UsageBatchInfo("CADRA_11Dec16", "01/11/2017", "FY2017",
        "2000017004 - Access Copyright, The Canadian Copyright Agency");
    private UsageBatchInfo validUsageBatch = new UsageBatchInfo("CADRA_11Dec16", "01/11/2017", "FY2017",
        "7000813806 - CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil");
    private Scenario scenario;

    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageRepository usageRepository;

    @After
    public void tearDown() {
        if (null != scenario) {
            usageRepository.deleteFromScenario(scenario.getId(), StoredEntity.DEFAULT_USER);
            scenarioRepository.deleteScenario(scenario.getId());
            scenario = null;
        }
    }

    @Test
    // Test case ID: 'e4b0a048-51af-4c1c-91bd-2a199747ca34'
    public void testAddToScenarioWithoutSelectedUsages() {
        loginAsSpecialist();
        applyFilters(assertElement(By.id(USAGE_FILTER_WIDGET_ID)), invalidUsageBatch);
        assertUsagesTableEmpty(assertElement(By.id(USAGE_TABLE_ID)));
        clickElementAndWait(assertElement(By.id(ADD_TO_SCENARIO_BUTTON_ID)));
        WebElement notificationWindow = assertElement(By.id("notification-window"));
        assertEquals("Please select at least one usage",
            assertElement(notificationWindow, By.className("v-label")).getText());
        clickElementAndWait(assertElement(notificationWindow, By.id(OK_BUTTON_ID)));
    }

    @Test
    // Test case ID: '684bfcfe-5fbb-44e4-a4ea-1464baed35fd'
    public void testAddToScenarioWithSelectedUsages() {
        WebElement createScenarioWindow = openAddToScenarioWindow();
        verifyCreateScenarioWindowFields(createScenarioWindow);
        verifyCreateScenarioWindowButtons(createScenarioWindow);
        WebElement nameTextField = assertElement(createScenarioWindow, By.id("scenario-name"));
        sendKeysToInput(nameTextField, SCENARIO_NAME);
        WebElement descriptionTextField = assertElement(createScenarioWindow, By.id("scenario-description"));
        sendKeysToInput(descriptionTextField, DESCRIPTION);
        clickButtonAndWait(createScenarioWindow, "Confirm");
        WebElement scenarioTab = waitAndGetTab(assertElement(By.id(Cornerstone.MAIN_TABSHEET)), "Scenarios");
        assertNotNull(scenarioTab);
        verifyScenariosTable();
        selectUsagesTab();
        assertUsagesFilterEmpty(assertElement(By.id(USAGE_FILTER_WIDGET_ID)),
            assertElement(By.id(USAGE_TABLE_ID)));
        List<Scenario> scenarios = scenarioRepository.findAll().stream()
            .filter(scenario -> SCENARIO_NAME.equals(scenario.getName())).collect(Collectors.toList());
        assertEquals(1, CollectionUtils.size(scenarios));
        scenario = scenarios.get(0);
        verifyScenario();
    }

    @Test
    // Test case ID: 'c2db5bb5-f04b-442f-98e1-9403762d63c2'
    public void testVerifyScenarioNameFieldValidators() {
        WebElement createScenarioWindow = openAddToScenarioWindow();
        WebElement confirmButton = assertElement(createScenarioWindow, By.id(CONFIRM_BUTTON_ID));
        populateField(createScenarioWindow, SCENARIO_NAME_FIELD_ID, StringUtils.EMPTY);
        clickElementAndWait(confirmButton);
        verifyErrorWindow(ImmutableMap.of(SCENARIO_NAME_FIELD, "Field value should be specified"));
        populateField(createScenarioWindow, SCENARIO_NAME_FIELD_ID, RandomStringUtils.randomAlphanumeric(51));
        clickElementAndWait(confirmButton);
        verifyErrorWindow(ImmutableMap.of(SCENARIO_NAME_FIELD, "Field value should not exceed 50 characters"));
        populateField(createScenarioWindow, SCENARIO_NAME_FIELD_ID, "Scenario name");
        clickElementAndWait(confirmButton);
        verifyErrorWindow(ImmutableMap.of(SCENARIO_NAME_FIELD, "Scenario with such name already exists"));
    }

    @Test
    // Test case ID: 'bd5bdf6f-1edf-4a01-b229-756dc253a98c'
    public void testVerifyDescriptionField() {
        WebElement createScenarioWindow = openAddToScenarioWindow();
        populateField(createScenarioWindow, "scenario-description", RandomStringUtils.randomAlphanumeric(2001));
        clickElementAndWait(assertElement(createScenarioWindow, By.id(CONFIRM_BUTTON_ID)));
        verifyErrorWindow(ImmutableMap.of(DESCRIPTION_FIELD, "Field value should not exceed 2000 characters"));
    }

    private WebElement openAddToScenarioWindow() {
        loginAsSpecialist();
        applyFilters(assertElement(By.id(USAGE_FILTER_WIDGET_ID)), validUsageBatch);
        assertUsagesTableNotEmpty(assertElement(By.id(USAGE_TABLE_ID)), 1);
        clickElementAndWait(assertElement(By.id(ADD_TO_SCENARIO_BUTTON_ID)));
        return assertElement(By.id("create-scenario-window"));
    }

    private void verifyScenario() {
        assertEquals(SCENARIO_NAME, scenario.getName());
        assertEquals(DESCRIPTION, scenario.getDescription());
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        assertEquals(GROSS_TOTAL, scenario.getGrossTotal());
        assertEquals(NET_TOTAL, scenario.getNetTotal());
        assertEquals(REPORTED_TOTAL, scenario.getReportedTotal());
    }

    private void verifyCreateScenarioWindowFields(WebElement createScenarioWindow) {
        assertElement(createScenarioWindow, By.className("v-caption-scenario-name"));
        WebElement nameTextField = assertElement(createScenarioWindow, By.id("scenario-name"));
        String currentDate =
            LocalDate.now().format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
        assertEquals("FAS Distribution " + currentDate, nameTextField.getAttribute("value"));
        assertElement(createScenarioWindow, By.className("v-caption-scenario-description"));
        WebElement descriptionTextField = assertElement(createScenarioWindow, By.id("scenario-description"));
        assertEquals(StringUtils.EMPTY, descriptionTextField.getText());
    }

    private void verifyCreateScenarioWindowButtons(WebElement createScenarioWindow) {
        assertElement(createScenarioWindow, By.id(CANCEL_BUTTON_ID));
        assertElement(createScenarioWindow, By.id("Confirm"));
    }

    private void verifyScenariosTable() {
        WebElement scenariosTable = assertElement(By.id("scenarios-table"));
        assertElement(scenariosTable, By.className("v-selected"));
    }

    private void applyFilters(WebElement filterWidget, UsageBatchInfo usageBatchInfo) {
        saveFilter(filterWidget, BATCHES_FILTER_ID, "batches-filter-window", usageBatchInfo.getName());
        saveFilter(filterWidget, RRO_FILTER_ID, "rightsholders-filter-window", usageBatchInfo.getRro());
        WebElement filterButtonsLayout = assertElement(filterWidget, By.id("filter-buttons"));
        clickButtonAndWait(filterButtonsLayout, APPLY_BUTTON_ID);
    }
}
