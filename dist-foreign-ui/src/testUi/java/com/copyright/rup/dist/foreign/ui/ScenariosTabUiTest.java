package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.repository.api.Sort.Direction;

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
    private static final String SCENARIOS_TABLE_ID = "scenarios-table";
    private static final String V_LABEL_CLASS_NAME = "v-label";
    private ScenarioInfo scenario1 = new ScenarioInfo("Scenario 03/16/2017", "03/16/2017");
    private ScenarioInfo scenario2 = new ScenarioInfo("Scenario for viewing", "03/17/2017");
    private ScenarioInfo scenario3 = new ScenarioInfo("Scenario 03/15/2017", "03/15/2017");
    private ScenarioInfo scenario4 = new ScenarioInfo("Scenario 02/15/2017", "02/15/2017");
    private ScenarioInfo scenario5 = new ScenarioInfo("Scenario name", "01/01/2017");
    private Scenario scenarioToDelete;

    @Autowired
    private IScenarioRepository scenarioRepository;

    @Autowired
    private IUsageRepository usageRepository;

    @After
    public void tearDown() {
        if (null != scenarioToDelete) {
            scenarioRepository.remove(scenarioToDelete.getId());
            usageRepository.deleteFromScenario(scenarioToDelete.getId(), StoredEntity.DEFAULT_USER);
            scenarioToDelete = null;
        }
    }

    @Test
    // Test cases IDs: 'f030a29a-f482-4ec7-869a-5ab6f0f3e655', '8afa9dfb-e4e2-47a5-8fa6-fc88241fb591',
    // 'f77fa41d-0a2f-4ea5-b120-5e01156e7a1e', 'cb18c089-7ee7-4c70-8729-b5be293bb72b',
    // '0ec5c344-7a37-4b29-b501-1af30ab63f8f'
    public void testVerifyScenariosTabSpecialist() {
        loginAsSpecialist();
        verifyScenariosTabSpecialist();
    }

    @Test
    // Test cases IDs: 'dd64f8ba-1016-43a6-90b7-e605b126fae8', 'f77fa41d-0a2f-4ea5-b120-5e01156e7a1e',
    // '0ec5c344-7a37-4b29-b501-1af30ab63f8f'
    public void testVerifyScenariosTabManager() {
        loginAsManager();
        verifyScenariosTabManagerAndViewOnly();
    }

    @Test
    // Test cases IDs: 'dd64f8ba-1016-43a6-90b7-e605b126fae8', 'f77fa41d-0a2f-4ea5-b120-5e01156e7a1e',
    // '0ec5c344-7a37-4b29-b501-1af30ab63f8f'
    public void testVerifyScenariosTabViewOnly() {
        loginAsViewOnly();
        verifyScenariosTabManagerAndViewOnly();
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
        scenarioToDelete = new Scenario();
        scenarioToDelete.setId(RupPersistUtils.generateUuid());
        scenarioToDelete.setName("Scenario for deleting");
        scenarioToDelete.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenarioRepository.insert(scenarioToDelete);
        assertEquals(6, CollectionUtils.size(scenarioRepository.findAll()));
        Usage usage = new Usage();
        usage.setId("366f0fa6-b4c5-11e7-abc4-cec278b6b50a");
        usage.setScenarioId(scenarioToDelete.getId());
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.getPayee().setAccountNumber(2000017004L);
        usage.setUpdateUser("user@copyright.com");
        usageRepository.addToScenario(Collections.singletonList(usage));
        ScenarioInfo scenarioInfo = new ScenarioInfo("Scenario for deleting",
            LocalDate.now().format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
        WebElement scenariosTab = selectScenariosTab();
        WebElement table = assertWebElement(scenariosTab, SCENARIOS_TABLE_ID);
        verifyTableRows(table, scenarioInfo, scenario1, scenario2, scenario3, scenario4, scenario5);
        clickButtonAndWait(scenariosTab, DELETE_BUTTON_ID);
        WebElement confirmDialog = assertWebElement(By.id("confirm-dialog-window"));
        UsageFilter filter = new UsageFilter();
        filter.setUsageStatuses(Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.ELIGIBLE));
        filter.setUsageBatchesIds(Sets.newHashSet("56282dbc-2468-48d4-b926-93d3458a656a"));
        Pageable pageable = new Pageable(0, 100);
        Sort sort = new Sort("status", Direction.ASC);
        assertEquals(0, CollectionUtils.size(usageRepository.findByFilter(filter, pageable, sort)));
        assertWebElementText(confirmDialog, "Are you sure you want to delete 'Scenario for deleting' scenario?");
        clickButtonAndWait(confirmDialog, "Yes");
        verifyTableRows(table, scenario1, scenario2, scenario3, scenario4, scenario5);
        assertEquals(5, CollectionUtils.size(scenarioRepository.findAll()));
        assertEquals(1, CollectionUtils.size(usageRepository.findByFilter(filter, pageable, sort)));
        scenarioToDelete = null;
    }

    private void verifyScenariosTabManagerAndViewOnly() {
        WebElement scenariosTab = selectScenariosTab();
        WebElement buttonsLayout = assertWebElement(scenariosTab, "scenarios-buttons");
        verifyScenariosLayoutButton(buttonsLayout, Sets.newHashSet("View"));
        WebElement table = assertWebElement(scenariosTab, SCENARIOS_TABLE_ID);
        verifyScenariosTable(table);
        verifyMetadataPanel(assertWebElement(scenariosTab, "scenarios-metadata"));
        verifyEmptyMetadataPanel(scenariosTab, table);
        assertEquals(1, findElements(buttonsLayout, By.className(V_DISABLED_CLASS_NAME)).size());
    }

    private void verifyScenariosTabSpecialist() {
        WebElement scenariosTab = selectScenariosTab();
        WebElement buttonsLayout = assertWebElement(scenariosTab, "scenarios-buttons");
        verifyScenariosLayoutButton(buttonsLayout, Sets.newHashSet("View", DELETE_BUTTON_ID));
        WebElement table = assertWebElement(scenariosTab, SCENARIOS_TABLE_ID);
        verifyScenariosTable(table);
        verifyMetadataPanel(assertWebElement(scenariosTab, "scenarios-metadata"));
        verifyEmptyMetadataPanel(scenariosTab, table);
        assertEquals(2, findElements(buttonsLayout, By.className(V_DISABLED_CLASS_NAME)).size());
    }

    @SuppressWarnings("unchecked")
    private void verifyScenariosLayoutButton(WebElement buttonsLayout, Set<String> buttonsIds) {
        Set<String> scenariosButton = Sets.newHashSet("View", DELETE_BUTTON_ID);
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
        WebElement metadataPanel = assertWebElement(scenarioTab, "scenarios-metadata");
        List<WebElement> labels = findElements(metadataPanel, By.className(V_LABEL_CLASS_NAME));
        assertEquals(1, CollectionUtils.size(labels));
        assertEquals(StringUtils.EMPTY, labels.get(0).getText());
    }

    // TODO {isuvorau} investigate test data 
    private void verifyMetadataPanel(WebElement metadataPanel) {
        List<WebElement> labels = findElements(metadataPanel, By.className(V_LABEL_CLASS_NAME));
        assertEquals(5, CollectionUtils.size(labels));
        assertEquals("Owner: SYSTEM", labels.get(0).getText());
        assertEquals("Distribution Total: 0.00", labels.get(1).getText());
        assertEquals("Gross Total: 100.00", labels.get(2).getText());
        assertEquals("Reported Total: 100.00", labels.get(3).getText());
        assertEquals("Description: Scenario description", labels.get(4).getText());
    }

    private void verifyTableRows(WebElement table, ScenarioInfo... scenariosInfo) {
        List<WebElement> rowElements = assertTableRowElements(table, scenariosInfo.length);
        IntStream.range(0, scenariosInfo.length)
            .forEach(i -> assertTableRowElements(rowElements.get(i), scenariosInfo[i].name, scenariosInfo[i].createDate,
                "IN_PROGRESS"));
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
