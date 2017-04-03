package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    private ScenarioInfo scenario1 = new ScenarioInfo("Scenario 03/16/2017", "03/16/2017");
    private ScenarioInfo scenario2 = new ScenarioInfo("Scenario 03/15/2017", "03/15/2017");
    private ScenarioInfo scenario3 = new ScenarioInfo("Scenario 02/15/2017", "02/15/2017");
    private ScenarioInfo scenario4 = new ScenarioInfo("Scenario name", "01/01/2017");

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
        WebElement table = assertScenariosTable(scenariosTab);
        verifyTableRows(table, scenario1, scenario2, scenario3, scenario4);
        clickButtonAndWait(scenariosTab, DELETE_BUTTON_ID);
        verifyConfirmDialogAndDecline("Are you sure you want to delete 'Scenario 03/16/2017' scenario?");
        verifyTableRows(table, scenario1, scenario2, scenario3, scenario4);
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
        assertEquals(5, CollectionUtils.size(scenarioRepository.findAll()));
        usageRepository.addToScenario(Lists.newArrayList("111111111"), scenarioToDelete.getId(),
            StoredEntity.DEFAULT_USER);
        UsageFilter filter = new UsageFilter();
        filter.setUsageStatus(UsageStatusEnum.LOCKED);
        filter.setUsageBatchesIds(Sets.newHashSet("56282dbc-2468-48d4-b926-93d3458a656a"));
        Pageable pageable = new Pageable(0, 100);
        Sort sort = new Sort("status", Direction.ASC);
        List<UsageDto> usages = usageRepository.findByFilter(filter, pageable, sort);
        assertEquals(1, CollectionUtils.size(usages));
        ScenarioInfo scenarioInfo = new ScenarioInfo("Scenario for deleting",
            LocalDate.now().format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
        WebElement scenariosTab = selectScenariosTab();
        WebElement table = assertScenariosTable(scenariosTab);
        verifyTableRows(table, scenarioInfo, scenario1, scenario2, scenario3, scenario4);
        clickButtonAndWait(scenariosTab, DELETE_BUTTON_ID);
        verifyConfirmDialogAndConfirm("Are you sure you want to delete 'Scenario for deleting' scenario?");
        verifyTableRows(table, scenario1, scenario2, scenario3, scenario4);
        assertEquals(4, CollectionUtils.size(scenarioRepository.findAll()));
        assertEquals(0, CollectionUtils.size(usageRepository.findByFilter(filter, pageable, sort)));
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        assertEquals(1, CollectionUtils.size(usageRepository.findByFilter(filter, pageable, sort)));
        scenarioToDelete = null;
    }

    private void verifyScenariosTabManagerAndViewOnly() {
        WebElement scenarioTab = selectScenariosTab();
        WebElement buttonsLayout = assertElement(scenarioTab, By.id("scenarios-buttons"));
        List<WebElement> buttons = findElements(buttonsLayout, By.className("v-button"));
        assertTrue(CollectionUtils.isEmpty(buttons));
        verifyScenariosTable(assertScenariosTable(scenarioTab));
        verifyMetadataPanel(assertElement(scenarioTab, By.id("scenarios-metadata")));
    }

    private void verifyScenariosTabSpecialist() {
        WebElement scenarioTab = selectScenariosTab();
        WebElement buttonsLayout = assertElement(scenarioTab, By.id("scenarios-buttons"));
        List<WebElement> buttons = findElements(buttonsLayout, By.className("v-button"));
        assertEquals(1, CollectionUtils.size(buttons));
        assertElement(buttonsLayout, By.id(DELETE_BUTTON_ID));
        WebElement table = assertScenariosTable(scenarioTab);
        verifyScenariosTable(table);
        verifyMetadataPanel(assertElement(scenarioTab, By.id("scenarios-metadata")));
        verifyEmptyMetadataPanel(scenarioTab, table);
        WebElement deleteButton = assertElement(buttonsLayout, By.className(V_DISABLED_CLASS_NAME));
        assertTrue(deleteButton.getAttribute("class").contains("v-button-Delete"));
    }

    private WebElement assertScenariosTable(WebElement scenarioTab) {
        return assertElement(scenarioTab, By.id("scenarios-table"));
    }

    private void verifyScenariosTable(WebElement table) {
        verifyTableColumns(table, "Name", "Create Date", "Status");
        verifyTableRows(table, scenario1, scenario2, scenario3, scenario4);
        verifyTableSorting(table, "name", "createDate", "status");
    }

    private void verifyEmptyMetadataPanel(WebElement scenarioTab, WebElement table) {
        clickElementAndWait(assertElement(table, By.className("v-selected")));
        assertNull(waitAndFindElement(table, By.className("v-selected")));
        WebElement metadataPanel = assertElement(scenarioTab, By.id("scenarios-metadata"));
        List<WebElement> labels = findElements(metadataPanel, By.className("v-label"));
        assertEquals(1, CollectionUtils.size(labels));
        assertEquals(StringUtils.EMPTY, labels.get(0).getText());
    }

    private void verifyMetadataPanel(WebElement metadataPanel) {
        List<WebElement> labels = findElements(metadataPanel, By.className("v-label"));
        assertEquals(5, CollectionUtils.size(labels));
        assertEquals("Owner: SYSTEM", labels.get(0).getText());
        assertEquals("Distribution Total: 9,000.00", labels.get(1).getText());
        assertEquals("Gross Total: 10,000.00", labels.get(2).getText());
        assertEquals("Reported Total: 11,000.00", labels.get(3).getText());
        assertEquals("Description: Scenario description", labels.get(4).getText());
    }

    private void verifyTableRows(WebElement table, ScenarioInfo... scenariosInfo) {
        List<WebElement> rows = findElements(assertElement(table, By.className(V_TABLE_BODY_CLASS_NAME)),
            By.tagName(HTML_TR_TAG_NAME));
        assertEquals(scenariosInfo.length, CollectionUtils.size(rows));
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
