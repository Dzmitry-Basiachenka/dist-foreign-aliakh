package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioAuditRepository;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.repository.api.Sort.Direction;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Selenium test to verify UI of 'Scenarios' tab.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/16/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class ScenariosTabUiTest extends ForeignCommonUiTest {

    private static final String DELETE_BUTTON_ID = "Delete";
    private static final String VIEW_BUTTON_ID = "View";
    private static final String YES_BUTTON_ID = "Yes";
    private static final String SCENARIOS_TABLE_ID = "scenarios-table";
    private static final String SCENARIOS_METADATA_ID = "scenarios-metadata";
    private static final String V_LABEL_CLASS_NAME = "v-label";
    private static final String MANAGER = "fda_manager@copyright.com";
    private static final String SPECIALIST = "fda_spec@copyright.com";
    private static final String SUBMITTED = "SUBMITTED";
    private static final String SUBMIT_REASON = "To Submit";
    private ScenarioInfo scenario1 = new ScenarioInfo("Scenario 03/16/2017", "03/16/2017");
    private ScenarioInfo scenario2 = new ScenarioInfo("Scenario for viewing", "03/17/2017");
    private ScenarioInfo scenario3 = new ScenarioInfo("Scenario 03/15/2017", "03/15/2017");
    private ScenarioInfo scenario4 = new ScenarioInfo("Scenario 02/15/2017", "02/15/2017");
    private ScenarioInfo scenario5 = new ScenarioInfo("Scenario name", "01/01/2017");
    private Scenario newScenario;

    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IScenarioAuditRepository scenarioAuditRepository;
    @Autowired
    private IUsageRepository usageRepository;

    @After
    public void tearDown() {
        if (null != newScenario) {
            String id = newScenario.getId();
            usageRepository.deleteFromScenario(id, StoredEntity.DEFAULT_USER);
            scenarioAuditRepository.deleteByScenarioId(id);
            scenarioRepository.remove(id);
            newScenario = null;
        }
    }

    @Test
    // Test cases IDs: 'f030a29a-f482-4ec7-869a-5ab6f0f3e655', '8afa9dfb-e4e2-47a5-8fa6-fc88241fb591',
    // 'f77fa41d-0a2f-4ea5-b120-5e01156e7a1e', 'cb18c089-7ee7-4c70-8729-b5be293bb72b',
    // '0ec5c344-7a37-4b29-b501-1af30ab63f8f'
    public void testVerifyScenariosTabSpecialist() {
        loginAsSpecialist();
        verifyScenariosTab(Sets.newHashSet(VIEW_BUTTON_ID, DELETE_BUTTON_ID, "Submit for Approval", "Send to LM"));
    }

    @Test
    // Test cases IDs: 'dd64f8ba-1016-43a6-90b7-e605b126fae8', 'f77fa41d-0a2f-4ea5-b120-5e01156e7a1e',
    // '0ec5c344-7a37-4b29-b501-1af30ab63f8f'
    public void testVerifyScenariosTabManager() {
        loginAsManager();
        verifyScenariosTab(Sets.newHashSet(VIEW_BUTTON_ID, "Apply", "Reject"));
    }

    @Test
    // Test cases IDs: 'dd64f8ba-1016-43a6-90b7-e605b126fae8', 'f77fa41d-0a2f-4ea5-b120-5e01156e7a1e',
    // '0ec5c344-7a37-4b29-b501-1af30ab63f8f'
    public void testVerifyScenariosTabViewOnly() {
        loginAsViewOnly();
        verifyScenariosTab(Collections.singleton(VIEW_BUTTON_ID));
    }

    @Test
    // Test cases IDs: '5d5d0ab7-c54f-42c9-85a4-556c8c642f2b', 'a7bcccac-d762-4110-ba04-4a13f4919064'
    public void testDeleteScenarioCanceled() {
        loginAsSpecialist();
        WebElement scenariosTab = selectScenariosTab();
        WebElement table = assertWebElement(scenariosTab, SCENARIOS_TABLE_ID);
        verifyTableRows(table, scenario1, scenario2, scenario3, scenario4, scenario5);
        clickButtonAndWait(scenariosTab, DELETE_BUTTON_ID);
        WebElement confirmDialog = assertWebElement(By.id("confirm-dialog-window"));
        assertWebElementText(confirmDialog, "Are you sure you want to delete 'Scenario 03/16/2017' scenario?");
        clickButtonAndWait(confirmDialog, "Cancel");
        verifyTableRows(table, scenario1, scenario2, scenario3, scenario4, scenario5);
    }

    @Test
    // Test cases IDs: '1f7f7358-a6b5-41af-bd92-7038edbbb59f', 'a7bcccac-d762-4110-ba04-4a13f4919064'
    public void testDeleteScenario() {
        loginAsSpecialist();
        buildAndPopulateScenario();
        ScenarioInfo scenarioInfo = new ScenarioInfo("NEW scenario",
            LocalDate.now().format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
        WebElement scenariosTab = selectScenariosTab();
        WebElement table = assertWebElement(scenariosTab, SCENARIOS_TABLE_ID);
        verifyTableRows(table, scenarioInfo, scenario1, scenario2, scenario3, scenario4, scenario5);
        clickButtonAndWait(scenariosTab, DELETE_BUTTON_ID);
        WebElement confirmDialog = assertWebElement(By.id("confirm-dialog-window"));
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Sets.newHashSet("d2b9c16d-230a-414f-9ffb-acdb676fac0c"));
        Pageable pageable = new Pageable(0, 100);
        Sort sort = new Sort("status", Direction.ASC);
        assertEquals(0, CollectionUtils.size(usageRepository.findByFilter(filter, pageable, sort)));
        assertWebElementText(confirmDialog, "Are you sure you want to delete 'NEW scenario' scenario?");
        clickButtonAndWait(confirmDialog, "Yes");
        verifyTableRows(table, scenario1, scenario2, scenario3, scenario4, scenario5);
        assertEquals(5, CollectionUtils.size(scenarioRepository.findAll()));
        assertEquals(1, CollectionUtils.size(usageRepository.findByFilter(filter, pageable, sort)));
        newScenario = null;
    }

    @Test
    // Test cases ID: '384a8036-6989-4491-9560-8dce46f916ea'
    public void testScenarioWorkflowWithAudit() {
        loginAsSpecialist();
        buildAndPopulateScenario();
        applyScenarioAction("Submit_for_Approval", SUBMIT_REASON,
            Lists.newArrayList(SUBMITTED, SPECIALIST, SUBMIT_REASON));
        switchUser(MANAGER);
        applyScenarioAction("Reject", "To Reject", Lists.newArrayList("REJECTED", MANAGER, "To Reject"));
        switchUser(SPECIALIST);
        applyScenarioAction("Submit_for_Approval", SUBMIT_REASON,
            Lists.newArrayList(SUBMITTED, SPECIALIST, SUBMIT_REASON));
        switchUser(MANAGER);
        applyScenarioAction("Approve", "To Approve", Lists.newArrayList("APPROVED", MANAGER, "To Approve"));
        clickElementAndWait(assertWebElement(By.className("v-button-link")));
        verifyScenarioHistory();
    }

    private void verifyScenariosTab(Set<String> buttons) {
        WebElement scenariosTab = selectScenariosTab();
        WebElement buttonsLayout = assertWebElement(scenariosTab, "scenarios-buttons");
        verifyScenariosLayoutButton(buttonsLayout, buttons);
        WebElement table = assertWebElement(scenariosTab, SCENARIOS_TABLE_ID);
        verifyScenariosTable(table);
        verifyMetadataPanel(assertWebElement(scenariosTab, SCENARIOS_METADATA_ID),
            Lists.newArrayList("SYSTEM", "214.91", "255.85", "100.00", "Scenario description", "REJECTED", MANAGER,
                "rejected"));
        verifyEmptyMetadataPanel(scenariosTab, table);
        assertEquals(buttons.size(), findElements(buttonsLayout, By.className(V_DISABLED_CLASS_NAME)).size());
    }

    private void verifyScenariosLayoutButton(WebElement buttonsLayout, Set<String> buttonsIds) {
        Set<String> scenariosButton =
            Sets.newHashSet(VIEW_BUTTON_ID, DELETE_BUTTON_ID, "Submit for Approval", "Apply", "Reject", "Send to LM");
        assertButtonsToolbar(buttonsLayout, scenariosButton, buttonsIds);
    }

    private void verifyScenariosTable(WebElement table) {
        assertTableHeaderElements(table, "Name", "Create Date", "Status");
        verifyTableRows(table, scenario1, scenario2, scenario3, scenario4, scenario5);
        assertTableSorting(table, "name", "createDate", "status");
    }

    private void verifyEmptyMetadataPanel(WebElement scenarioTab, WebElement table) {
        clickElementAndWait(assertWebElement(table, By.className("v-selected")));
        assertNull(waitAndFindElement(table, By.className("v-selected")));
        WebElement metadataPanel = assertWebElement(scenarioTab, SCENARIOS_METADATA_ID);
        List<WebElement> labels = findElements(metadataPanel, By.className(V_LABEL_CLASS_NAME));
        assertEquals(1, CollectionUtils.size(labels));
        assertEquals(StringUtils.EMPTY, labels.get(0).getText());
    }

    private void verifyMetadataPanel(WebElement metadataPanel, List<String> metadata) {
        List<WebElement> labels = findElements(metadataPanel, By.className(V_LABEL_CLASS_NAME));
        assertEquals(9, CollectionUtils.size(labels));
        assertEquals("Owner: " + metadata.get(0), labels.get(0).getText());
        assertEquals("Net Amt in USD: " + metadata.get(1), labels.get(1).getText());
        assertEquals("Gross Amt in USD: " + metadata.get(2), labels.get(2).getText());
        assertEquals("Reported Value Total: " + metadata.get(3), labels.get(3).getText());
        assertEquals("Description: " + metadata.get(4), labels.get(4).getText());
        assertEquals("Type: " + metadata.get(5), labels.get(5).getText());
        assertEquals("User: " + metadata.get(6), labels.get(6).getText());
        assertNotNull(labels.get(7).getText());
        assertEquals("Reason: " + metadata.get(7), labels.get(8).getText());
    }

    private void verifyTableRows(WebElement table, ScenarioInfo... scenariosInfo) {
        List<WebElement> rowElements = assertTableRowElements(table, scenariosInfo.length);
        IntStream.range(0, scenariosInfo.length)
            .forEach(i -> assertTableRowElements(rowElements.get(i), scenariosInfo[i].name, scenariosInfo[i].createDate,
                "IN_PROGRESS"));
    }

    private void applyScenarioAction(String actionButton, String reason, List<String> additionalInfo) {
        WebElement scenariosTab = selectScenariosTab();
        clickButtonAndWait(scenariosTab, actionButton);
        WebElement confirmDialog = assertWebElement(By.id("confirm-action-dialog-window"));
        sendKeysToInput(assertWebElement(confirmDialog, By.className("v-textfield")), reason);
        clickElementAndWait(assertWebElement(confirmDialog, By.id(YES_BUTTON_ID)));
        additionalInfo.addAll(0, Lists.newArrayList("SYSTEM", "29,400.00", "35,000.00", "2,500.00", "NEW description"));
        verifyMetadataPanel(assertWebElement(scenariosTab, SCENARIOS_METADATA_ID), additionalInfo);
    }

    private void verifyScenarioHistory() {
        WebElement historyWindow = assertWebElement(By.id("scenario-history-widget"));
        WebElement table = assertWebElement(historyWindow, "scenario-history-table");
        List<ScenarioAction> actions = Lists.newArrayList(new ScenarioAction("APPROVED", MANAGER, "To Approve"),
            new ScenarioAction(SUBMITTED, SPECIALIST, SUBMIT_REASON),
            new ScenarioAction("REJECTED", MANAGER, "To Reject"),
            new ScenarioAction(SUBMITTED, SPECIALIST, SUBMIT_REASON));
        List<WebElement> rowElements = assertTableRowElements(table, actions.size());
        IntStream.range(0, actions.size()).forEach(i -> {
            List<WebElement> cellElements = findElements(rowElements.get(i), By.className("v-table-cell-wrapper"));
            assertEquals(actions.get(i).type, cellElements.get(0).getText());
            assertEquals(actions.get(i).user, cellElements.get(1).getText());
            assertNotNull(cellElements.get(2).getText());
            assertEquals(actions.get(i).reason, cellElements.get(3).getText());
        });
    }

    private void switchUser(String userName) {
        logOut();
        loginIfNeeded(userName, "`123qwer");
    }

    private void buildAndPopulateScenario() {
        newScenario = new Scenario();
        newScenario.setId(RupPersistUtils.generateUuid());
        newScenario.setName("NEW scenario");
        newScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        newScenario.setDescription("NEW description");
        scenarioRepository.insert(newScenario);
        assertEquals(6, CollectionUtils.size(scenarioRepository.findAll()));
        Usage usage = new Usage();
        usage.setId("366f0fa6-b4c5-11e7-abc4-cec278b6b50a");
        usage.setScenarioId(newScenario.getId());
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.getPayee().setAccountNumber(2000017004L);
        usage.setNetAmount(new BigDecimal("29400.00"));
        usage.setUpdateUser("user@copyright.com");
        usageRepository.addToScenario(Collections.singletonList(usage));
    }

    private static class ScenarioInfo {

        private String name;
        private String createDate;

        private ScenarioInfo(String name, String createDate) {
            this.name = name;
            this.createDate = createDate;
        }
    }

    private static class ScenarioAction {

        private String type;
        private String user;
        private String reason;

        private ScenarioAction(String type, String user, String reason) {
            this.type = type;
            this.user = user;
            this.reason = reason;
        }
    }
}
