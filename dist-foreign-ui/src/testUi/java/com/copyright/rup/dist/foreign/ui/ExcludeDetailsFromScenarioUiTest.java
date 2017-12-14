package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

/**
 * UI test to verify exclusion of details functionality.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/23/17
 *
 * @author Aliaksandra Bayanouskaya
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class ExcludeDetailsFromScenarioUiTest extends ForeignCommonUiTest {

    private static final String EXCLUDE_BUTTON_ID = "Exclude";
    private static final String EXCLUDE_TABLE_ID = "exclude-rightsholders-table";
    private static final String BATCH_ID = RupPersistUtils.generateUuid();
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String USAGE_ID_1 = RupPersistUtils.generateUuid();
    private static final String USAGE_ID_2 = RupPersistUtils.generateUuid();
    private static final String USAGE_ID_3 = RupPersistUtils.generateUuid();
    private static final String USAGE_ID_4 = RupPersistUtils.generateUuid();
    private RightsholderInfo rightsholder1 = new RightsholderInfo(StringUtils.EMPTY, "1000000001",
        "Rothchild Consultants", "1000000001", "Rothchild Consultants");
    private RightsholderInfo rightsholder2 = new RightsholderInfo(StringUtils.EMPTY, "1000000002",
        "Royal Society of Victoria", "1000000002", "Royal Society of Victoria");
    private RightsholderInfo rightsholder3 = new RightsholderInfo(StringUtils.EMPTY, "1000000003",
        "South African Institute of Mining and Metallurgy", "1000000003",
        "South African Institute of Mining and Metallurgy");
    private RightsholderInfo rightsholder4 = new RightsholderInfo(StringUtils.EMPTY, "1000000004",
        "Computers for Design and Construction", "1000000004", "Computers for Design and Construction");

    @Autowired
    private IRightsholderRepository rightsholderRepository;
    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditRepository usageAuditRepository;

    @Before
    public void init() {
        initRightsholders();
        usageBatchRepository.insert(buildUsageBatch());
        scenarioRepository.insert(buildScenario());
        usageRepository.addToScenario(initUsages());
    }

    @After
    public void tearDown() {
        usageAuditRepository.deleteByBatchId(BATCH_ID);
        usageRepository.deleteUsages(BATCH_ID);
        usageBatchRepository.deleteUsageBatch(BATCH_ID);
        scenarioRepository.remove(SCENARIO_ID);
        Lists.newArrayList(1000000000L, 1000000001L, 1000000002L, 1000000003L, 1000000004L)
            .forEach(accountNumber -> rightsholderRepository.deleteByAccountNumber(accountNumber));
    }

    @Test
    // Test case ID: '51f2c3cd-664c-4500-b4e1-092f60cbaa11'
    public void testVerifyElementsForExclude() {
        loginAsSpecialist();
        WebElement viewScenarioWidget = openViewScenarioWidget();
        clickElementAndWait(assertWebElement(viewScenarioWidget, HTML_SPAN_TAG_NAME, "Exclude Details"));
        WebElement excludeSourceRROWindow = assertWebElement(By.id("exclude-source-rro-window"));
        verifyExcludeDetailsBySourceRROWindow(excludeSourceRROWindow);
        clickElementAndWait(assertWebElement(excludeSourceRROWindow, HTML_SPAN_TAG_NAME, EXCLUDE_BUTTON_ID));
        WebElement excludeRightsholdersWindow = assertWebElement(By.id("exclude-rightsholders-window"));
        verifyExcludeRHDetailsForSourceRROWindow(excludeRightsholdersWindow);
        WebElement excludeRightsholderTable = assertWebElement(excludeRightsholdersWindow, EXCLUDE_TABLE_ID);
        verifySelectedAndClear(excludeRightsholdersWindow, excludeRightsholderTable);
        clickCheckbox(excludeRightsholderTable, 0);
        clickElementAndWait(assertWebElement(excludeRightsholdersWindow, "Confirm"));
        WebElement confirmAction = assertWebElement(By.id("confirm-action-dialog-window"));
        verifyConfirmActionWindow(confirmAction);
    }

    @Test
    // Test case ID: 'd46146d6-8e04-4530-9fd8-1ea668c208f4'
    public void testVerifyExcludeDetailsFromScenario() {
        loginAsSpecialist();
        WebElement viewScenarioWidget = openViewScenarioWidget();
        clickElementAndWait(assertWebElement(viewScenarioWidget, HTML_SPAN_TAG_NAME, "Exclude Details"));
        WebElement excludeSourceRROWindow = assertWebElement(By.id("exclude-source-rro-window"));
        verifyExcludeDetailsBySourceRROTable(excludeSourceRROWindow);
        clickElementAndWait(assertWebElement(excludeSourceRROWindow, HTML_SPAN_TAG_NAME, EXCLUDE_BUTTON_ID));
        WebElement excludeRightsholdersWindow = assertWebElement(By.id("exclude-rightsholders-window"));
        WebElement excludeRightsholderTable = assertWebElement(excludeRightsholdersWindow,
            EXCLUDE_TABLE_ID);
        verifyTableRows(excludeRightsholderTable, rightsholder4, rightsholder1, rightsholder2, rightsholder3);
        clickCheckbox(excludeRightsholderTable, 0, 2);
        clickElementAndWait(assertWebElement(excludeRightsholdersWindow, "Confirm"));
        WebElement confirmAction = assertWebElement(By.id("confirm-action-dialog-window"));
        sendKeysToInput(assertWebElement(confirmAction, By.className("v-textfield")), "Reason for exclude");
        clickElementAndWait(assertWebElement(confirmAction, HTML_SPAN_TAG_NAME, "Yes"));
        clickElementAndWait(assertWebElement(viewScenarioWidget, HTML_SPAN_TAG_NAME, "Exclude Details"));
        excludeSourceRROWindow = assertWebElement(By.id("exclude-source-rro-window"));
        clickElementAndWait(assertWebElement(excludeSourceRROWindow, HTML_SPAN_TAG_NAME, EXCLUDE_BUTTON_ID));
        excludeRightsholdersWindow = assertWebElement(By.id("exclude-rightsholders-window"));
        excludeRightsholderTable = assertWebElement(excludeRightsholdersWindow,
            EXCLUDE_TABLE_ID);
        verifyTableRows(excludeRightsholderTable, rightsholder1, rightsholder3);
        verifyExcludeUsages();
    }

    private WebElement openViewScenarioWidget() {
        WebElement scenariosTab = selectScenariosTab();
        assertWebElement(scenariosTab, HTML_DIV_TAG_NAME, "Scenario for exclude");
        clickElementAndWait(assertWebElement(scenariosTab, "View"));
        return assertWebElement(By.id("view-scenario-widget"));
    }

    private void verifyExcludeDetailsBySourceRROWindow(WebElement excludeSourceRROwindow) {
        assertEquals("Exclude Details by Source RRO", getWindowCaption(excludeSourceRROwindow));
        WebElement table = assertWebElement(excludeSourceRROwindow, "exclude-details-by-rro-table");
        assertTableHeaderElements(table, "Source RRO Account #", "Source RRO Name", "");
        assertWebElement(table, HTML_SPAN_TAG_NAME, EXCLUDE_BUTTON_ID);
        assertWebElement(excludeSourceRROwindow, "Cancel");
    }

    private void verifyExcludeDetailsBySourceRROTable(WebElement excludeSourceRROwindow) {
        WebElement table = assertWebElement(excludeSourceRROwindow, "exclude-details-by-rro-table");
        assertTableRowElements(table, 1);
        assertTableRowElements(table, "1000000000", "CLA, The Copyright Licensing Agency Ltd.", EXCLUDE_BUTTON_ID);
    }

    private void verifyExcludeRHDetailsForSourceRROWindow(WebElement excludeRightsholdersWindow) {
        assertEquals("Exclude RH Details for Source RRO #: 1000000000", getWindowCaption(excludeRightsholdersWindow));
        WebElement table = assertWebElement(excludeRightsholdersWindow, EXCLUDE_TABLE_ID);
        assertTableHeaderElements(table, "<p></p>", "Payee Account #", "Payee Name", "RH Account #", "RH Name");
        assertWebElement(excludeRightsholdersWindow, "Confirm");
        assertWebElement(excludeRightsholdersWindow, "Clear");
        assertWebElement(excludeRightsholdersWindow, "Close");
    }

    private void verifyConfirmActionWindow(WebElement confirmAction) {
        assertEquals("Confirm action", getWindowCaption(confirmAction));
        assertWebElement(confirmAction, HTML_DIV_TAG_NAME, "Are you sure you want to exclude selected Rightsholders?");
        assertWebElement(confirmAction, HTML_SPAN_TAG_NAME, "Reason");
        assertWebElement(confirmAction, HTML_SPAN_TAG_NAME, "Yes");
        assertWebElement(confirmAction, HTML_SPAN_TAG_NAME, "Cancel");
    }

    private void verifySelectedAndClear(WebElement excludeRightsholdersWindow, WebElement excludeRightsholderTable) {
        clickCheckbox(excludeRightsholderTable, 1);
        assertWebElement(By.className("indeterminate"));
        clickElementAndWait(assertWebElement(excludeRightsholdersWindow, "Clear"));
        assertNull(findElement(By.className("indeterminate")));
    }

    private void clickCheckbox(WebElement table, Integer... rows) {
        List<WebElement> rowElements = assertTableRowElements(table, 4);
        Sets.newHashSet(rows).forEach(row -> clickElementAndWait(assertWebElement(rowElements.get(row),
            By.tagName(HTML_INPUT_TAG_NAME))));
    }

    private void verifyTableRows(WebElement table, RightsholderInfo... rightsholders) {
        List<WebElement> rowElements = assertTableRowElements(table, rightsholders.length);
        IntStream.range(0, rightsholders.length)
            .forEach(i -> assertTableRowElements(rowElements.get(i),
                rightsholders[i].selected,
                rightsholders[i].payeeAccountNumber,
                rightsholders[i].payeeAccountName,
                rightsholders[i].rhAccountNumber,
                rightsholders[i].rhAccountName));
    }

    private void verifyExcludeUsages() {
        verifyExcludeUsage(USAGE_ID_2, 100000002L, usageRepository.findByDetailId(100000002L));
        verifyExcludeUsage(USAGE_ID_4, 100000004L, usageRepository.findByDetailId(100000004L));
    }

    private void verifyExcludeUsage(String id, Long detailId, Usage usage) {
        assertEquals(id, usage.getId());
        assertNull(usage.getScenarioId());
        assertEquals(detailId, usage.getDetailId());
        assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
    }

    private List<Usage> initUsages() {
        List<Usage> usages = Lists.newArrayList(
            buildUsage(USAGE_ID_1, 100000001L, 1000000001L, "9900.00", "26776.51", "8568.48", "18208.03"),
            buildUsage(USAGE_ID_2, 100000002L, 1000000002L, "5000.00", "13523.49", "4327.52", "9195.97"),
            buildUsage(USAGE_ID_3, 100000003L, 1000000003L, "15000.00", "6509.31", "2082.98", "4426.33"),
            buildUsage(USAGE_ID_4, 100000004L, 1000000004L, "3000.00", "1301.86", "416.60", "885.26")
        );
        usages.forEach(usage -> usageRepository.insert(usage));
        return usages;
    }

    private void initRightsholders() {
        Lists.newArrayList(
            buildRightsholder(1000000000L, "CLA, The Copyright Licensing Agency Ltd."),
            buildRightsholder(1000000001L, "Rothchild Consultants"),
            buildRightsholder(1000000002L, "Royal Society of Victoria"),
            buildRightsholder(1000000003L, "South African Institute of Mining and Metallurgy"),
            buildRightsholder(1000000004L, "Computers for Design and Construction")
        ).forEach(rightsholder -> rightsholderRepository.insert(rightsholder));
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setId(RupPersistUtils.generateUuid());
        rightsholder.setName(name);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_ID);
        usageBatch.setName("Test Batch");
        usageBatch.setRro(buildRightsholder(1000000000L, "CLA, The Copyright Licensing Agency Ltd."));
        usageBatch.setPaymentDate(LocalDate.of(2017, 1, 11));
        usageBatch.setFiscalYear(2017);
        usageBatch.setGrossAmount(new BigDecimal(40300));
        return usageBatch;
    }

    private Usage buildUsage(String id, Long detailId, Long accountNumber, String reportedValue, String grossAmount,
                             String serviceFeeAmount, String netAmount) {
        Usage usage = new Usage();
        usage.setId(id);
        usage.setBatchId(BATCH_ID);
        usage.setDetailId(detailId);
        usage.setScenarioId(SCENARIO_ID);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        usage.setRightsholder(rightsholder);
        usage.setPayee(rightsholder);
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setStandardNumber("1003324112314587XX");
        usage.setPublisher("IEEE");
        usage.setPublicationDate(LocalDate.of(2013, 9, 10));
        usage.setMarket("Doc Del");
        usage.setMarketPeriodFrom(2013);
        usage.setMarketPeriodTo(2017);
        usage.setNumberOfCopies(25);
        usage.setRhParticipating(false);
        usage.setReportedValue(new BigDecimal(reportedValue));
        usage.setGrossAmount(new BigDecimal(grossAmount));
        usage.setServiceFee(new BigDecimal("0.32"));
        usage.setServiceFeeAmount(new BigDecimal(serviceFeeAmount));
        usage.setNetAmount(new BigDecimal(netAmount));
        return usage;
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName("Scenario for exclude");
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setDescription("Scenario description");
        return scenario;
    }

    private static class RightsholderInfo {

        private String selected;
        private String payeeAccountNumber;
        private String payeeAccountName;
        private String rhAccountNumber;
        private String rhAccountName;

        private RightsholderInfo(String selected, String payeeAccountNumber, String payeeAccountName,
                                 String rhAccountNumber, String rhAccountName) {
            this.selected = selected;
            this.payeeAccountNumber = payeeAccountNumber;
            this.payeeAccountName = payeeAccountName;
            this.rhAccountNumber = rhAccountNumber;
            this.rhAccountName = rhAccountName;
        }
    }
}
