package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;

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

    private static final String USAGES_FILTER_ID = "usages-filter-widget";
    private static final String USAGES_TABLE_ID = "usages-table";
    private static final String SCENARIO_NAME = "Scenario";
    private static final String DESCRIPTION = "Description";
    private static final String CURRENT_DATE =
        LocalDate.now().format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
    private static final String CONFIRM_BUTTON_ID = "Confirm";
    private static final String SCENARIO_NAME_FIELD = "Scenario name";
    private static final String APPLY_ELIGIBLE_MESSAGE = "Please apply ELIGIBLE status filter to create scenario";

    private UsageBatchInfo validUsageBatch = new UsageBatchInfo("CADRA_11Dec16", "01/11/2017", "FY2017",
        "7000813806 - CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil");
    private String scenarioId;

    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IUsageRepository usageRepository;

    @After
    public void tearDown() {
        if (null != scenarioId) {
            usageRepository.deleteFromScenario(scenarioId, StoredEntity.DEFAULT_USER);
            scenarioAuditService.deleteActions(scenarioId);
            scenarioRepository.remove(scenarioId);
            scenarioId = null;
        }
    }

    @Test
    // Test case IDs: 'e4b0a048-51af-4c1c-91bd-2a199747ca34', 'de74d602-db65-4ee0-a347-5f729f00a2ab',
    // '6f5e7868-d9fd-49fa-be83-ef7a6061e313'
    public void testAddUsagesToScenarioInDifferentStatuses() {
        loginAsSpecialist();
        WebElement usagesTab = selectUsagesTab();
        UsageBatchInfo nonexistentBatch = new UsageBatchInfo("CADRA_11Dec16", null, null,
            "2000017004 - Access Copyright, The Canadian Copyright Agency");
        UsageBatchInfo batch = new UsageBatchInfo("Batch with usages in different statuses", null, null,
            "1000005413 - Kluwer Academic Publishers - Dordrecht");
        applyFiltersAndVerifyNotification(usagesTab, nonexistentBatch, UsageStatusEnum.ELIGIBLE, 0,
            "Scenario cannot be created. Please select ELIGIBLE usages");
        applyFiltersAndVerifyNotification(usagesTab, batch, UsageStatusEnum.NEW, 1, APPLY_ELIGIBLE_MESSAGE);
        applyFiltersAndVerifyNotification(usagesTab, batch, UsageStatusEnum.WORK_FOUND, 1, APPLY_ELIGIBLE_MESSAGE);
        applyFiltersAndVerifyNotification(usagesTab, batch, UsageStatusEnum.RH_NOT_FOUND, 1, APPLY_ELIGIBLE_MESSAGE);
        applyFiltersAndVerifyNotification(usagesTab, batch, UsageStatusEnum.SENT_FOR_RA, 1, APPLY_ELIGIBLE_MESSAGE);
        applyFiltersAndVerifyCreateScenarioWindow(usagesTab, batch, UsageStatusEnum.ELIGIBLE, 1);
    }

    @Test
    // Test case ID: '684bfcfe-5fbb-44e4-a4ea-1464baed35fd'
    public void testAddToScenarioWithSelectedUsages() {
        loginAsSpecialist();
        WebElement usagesTab = selectUsagesTab();
        WebElement createScenarioWindow = assertCreateScenarioWindow(usagesTab, validUsageBatch, 1);
        WebElement nameTextField = assertWebElement(createScenarioWindow, "scenario-name");
        sendKeysToInput(nameTextField, SCENARIO_NAME);
        WebElement descriptionTextField = assertWebElement(createScenarioWindow, "scenario-description");
        sendKeysToInput(descriptionTextField, DESCRIPTION);
        clickButtonAndWait(createScenarioWindow, CONFIRM_BUTTON_ID);
        WebElement scenarioTab = selectScenariosTab();
        verifyCreatedScenario(scenarioTab);
        selectUsagesTab();
        assertTableRowElements(assertWebElement(By.id(USAGES_TABLE_ID)), 0);
        assertUsagesFilterEmpty(assertWebElement(By.id(USAGES_FILTER_ID)));
    }

    @Test
    // Test case ID: 'c2db5bb5-f04b-442f-98e1-9403762d63c2'
    public void testVerifyScenarioNameFieldValidators() {
        loginAsSpecialist();
        WebElement usagesTab = selectUsagesTab();
        WebElement createScenarioWindow = assertCreateScenarioWindow(usagesTab, validUsageBatch, 1);
        WebElement confirmButton = assertWebElement(createScenarioWindow, CONFIRM_BUTTON_ID);
        WebElement scenarioNameElement = assertWebElement(createScenarioWindow, "scenario-name");
        sendKeysToInput(scenarioNameElement, StringUtils.EMPTY);
        clickElementAndWait(confirmButton);
        verifyErrorWindow(ImmutableMap.of(SCENARIO_NAME_FIELD, "Field value should be specified"));

        sendKeysToInput(scenarioNameElement, RandomStringUtils.randomAlphanumeric(51));
        clickElementAndWait(confirmButton);
        verifyErrorWindow(ImmutableMap.of(SCENARIO_NAME_FIELD, "Field value should not exceed 50 characters"));

        sendKeysToInput(scenarioNameElement, "Scenario name");
        clickElementAndWait(confirmButton);
        verifyErrorWindow(ImmutableMap.of(SCENARIO_NAME_FIELD, "Scenario with such name already exists"));
    }

    @Test
    // Test case ID: 'bd5bdf6f-1edf-4a01-b229-756dc253a98c'
    public void testVerifyDescriptionField() {
        loginAsSpecialist();
        WebElement usagesTab = selectUsagesTab();
        WebElement createScenarioWindow = assertCreateScenarioWindow(usagesTab, validUsageBatch, 1);
        WebElement descriptionTextField = assertWebElement(createScenarioWindow, "scenario-description");
        sendKeysToInput(descriptionTextField, RandomStringUtils.randomAlphanumeric(2001));
        clickButtonAndWait(createScenarioWindow, CONFIRM_BUTTON_ID);
        verifyErrorWindow(ImmutableMap.of("Description", "Field value should not exceed 2000 characters"));
    }

    private WebElement assertCreateScenarioWindow(WebElement usagesTab, UsageBatchInfo filteredBatch, int rowCount) {
        applyStatusFilter(assertWebElement(By.id(USAGES_FILTER_ID)), UsageStatusEnum.ELIGIBLE.name());
        applyFilters(assertWebElement(By.id(USAGES_FILTER_ID)), filteredBatch);
        assertTableRowElements(assertWebElement(By.id(USAGES_TABLE_ID)), rowCount);
        clickButtonAndWait(usagesTab, "Add_To_Scenario");
        WebElement createScenarioWindow = assertWebElement(By.id("create-scenario-window"));
        verifyCreateScenarioWindow(createScenarioWindow);
        return createScenarioWindow;
    }

    private void verifyCreatedScenario(WebElement scenarioTab) {
        assertTableRowElements(assertWebElement(scenarioTab, "scenarios-table"), SCENARIO_NAME, CURRENT_DATE,
            "IN_PROGRESS");
        List<Scenario> scenarios = scenarioRepository.findAll()
            .stream()
            .filter(scenario -> SCENARIO_NAME.equals(scenario.getName()))
            .map(scenario -> scenarioRepository.findWithAmountsAndLastAction(scenario.getId()))
            .collect(Collectors.toList());
        assertEquals(1, CollectionUtils.size(scenarios));
        scenarioId = scenarios.get(0).getId();
        verifyScenario(scenarios.get(0));
    }

    private void verifyScenario(Scenario scenario) {
        assertEquals(SCENARIO_NAME, scenario.getName());
        assertEquals(DESCRIPTION, scenario.getDescription());
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        assertEquals(new BigDecimal("35000").setScale(10, BigDecimal.ROUND_HALF_UP), scenario.getGrossTotal());
        assertEquals(new BigDecimal("23800").setScale(10, BigDecimal.ROUND_HALF_UP), scenario.getNetTotal());
        assertEquals(new BigDecimal("2500").setScale(2, BigDecimal.ROUND_HALF_UP), scenario.getReportedTotal());
        assertEquals(new BigDecimal("11200").setScale(10, BigDecimal.ROUND_HALF_UP), scenario.getServiceFeeTotal());
    }

    private void verifyCreateScenarioWindow(WebElement createScenarioWindow) {
        assertWebElement(createScenarioWindow, HTML_DIV_TAG_NAME, "Create Scenario");
        WebElement nameCaption = assertWebElement(createScenarioWindow, By.className("v-caption-scenario-name"));
        assertEquals("Scenario name*", nameCaption.getText());
        WebElement nameTextField = assertWebElement(createScenarioWindow, "scenario-name");
        assertEquals("FAS Distribution " + CURRENT_DATE, nameTextField.getAttribute("value"));
        WebElement descriptionCaption =
            assertWebElement(createScenarioWindow, By.className("v-caption-scenario-description"));
        assertEquals("Description", descriptionCaption.getText());
        WebElement descriptionTextField = assertWebElement(createScenarioWindow, "scenario-description");
        assertEquals(StringUtils.EMPTY, descriptionTextField.getText());
        assertWebElement(createScenarioWindow, "Cancel");
        assertWebElement(createScenarioWindow, CONFIRM_BUTTON_ID);
    }

    private void verifyNotificationWindow(String errorMessage) {
        WebElement notificationWindow = assertWebElement(By.id("notification-window"));
        assertWebElement(notificationWindow, HTML_DIV_TAG_NAME, errorMessage);
        clickButtonAndWait(notificationWindow, "Ok");
    }

    private void applyStatusFilter(WebElement filterWidget, String selectItem) {
        clickButton(filterWidget, "Clear");
        WebElement statusFilter = assertWebElement(filterWidget, By.id("status-filter"));
        clickElementAndWait(assertWebElement(statusFilter, By.className(V_FILTER_SELECT_BUTTON_CLASS_NAME)));
        clickElementAndWait(assertWebElement(statusFilter, HTML_SPAN_TAG_NAME, selectItem));
    }

    private void applyFiltersAndVerifyNotification(WebElement usagesTab, UsageBatchInfo batchInfo,
                                                   UsageStatusEnum status, int count, String message) {
        applyFiltersAndVerifyTable(batchInfo, status, count);
        clickButtonAndWait(usagesTab, "Add_To_Scenario");
        verifyNotificationWindow(message);
    }

    private void applyFiltersAndVerifyCreateScenarioWindow(WebElement usagesTab, UsageBatchInfo batchInfo,
                                                           UsageStatusEnum status, int count) {
        applyFiltersAndVerifyTable(batchInfo, status, count);
        clickButtonAndWait(usagesTab, "Add_To_Scenario");
        verifyCreateScenarioWindow(assertWebElement(By.id("create-scenario-window")));
    }

    private void applyFiltersAndVerifyTable(UsageBatchInfo batchInfo, UsageStatusEnum status, int count) {
        applyStatusFilter(assertWebElement(By.id(USAGES_FILTER_ID)), status.name());
        applyFilters(assertWebElement(By.id(USAGES_FILTER_ID)), batchInfo);
        assertTableRowElements(assertWebElement(By.id(USAGES_TABLE_ID)), count);
    }
}
