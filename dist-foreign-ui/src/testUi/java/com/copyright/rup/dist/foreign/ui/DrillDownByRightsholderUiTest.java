package com.copyright.rup.dist.foreign.ui;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import com.google.common.collect.Lists;

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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Ui test for drill down by rightsholder widget.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/13/17
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class DrillDownByRightsholderUiTest extends ForeignCommonUiTest {

    private static final String BATCH_NAME = "AccessCopyright_10Dec17";
    private static final String FISCAL_YEAR = "FY2016";
    private static final String ROR_ACCOUNT_NUMBER = "2000017004";
    private static final String ROR_NAME = "Access Copyright, The Canadian Copyright Agency";
    private static final String PAYMENT_DATE = "09/10/2015";
    private static final String PUB_DATE = "09/10/2013";
    private static final String ZERO_AMOUNT = "0.00";
    private static final String MIN_REPORTED_VALUE = "500.00";
    private static final String MAX_REPORTED_VALUE = "6,810.00";
    private static final BigDecimal BATCH_TOTAL_AMOUNT = new BigDecimal("12850.00");
    private static final String SERVICE_FEE = "0.32";
    private static final String[] TABLE_HEADER = {"Detail ID", "Usage Batch Name", "Fiscal Year", "RRO Account #",
        "RRO Name", "Payment Date", "Title", "Article", "Standard Number", "Wr Wrk Inst", "Publisher", "Pub Date",
        "Number of Copies", "Reported value", "Gross Amt in USD", "Service Fee Amount", "Net Amt in USD",
        "Service Fee %", "Market", "Market Period From", "Market Period To", "Author"};
    private static final String[] USAGE_1 = {"3982472103", BATCH_NAME, FISCAL_YEAR, ROR_ACCOUNT_NUMBER,
        ROR_NAME, PAYMENT_DATE, "40 model essays : a portable anthology", "The ritual of fast food", "12345XX-01",
        "122235139", "American Society for Nutritional Science", PUB_DATE, "370", MAX_REPORTED_VALUE,
        MAX_REPORTED_VALUE, ZERO_AMOUNT, ZERO_AMOUNT, ZERO_AMOUNT, "Univ", "2009", "2010",
        "Aaron1088.89 Jane E."};
    private static final String[] USAGE_2 = {"5248153472", BATCH_NAME, FISCAL_YEAR, ROR_ACCOUNT_NUMBER,
        ROR_NAME, PAYMENT_DATE, "Managing brand equity : capitalizing on the value of a brand name",
        "The Measurement of Brand Associations", "12345XX-89173", "122235138", "Simon & Schuster US", PUB_DATE,
        "150", "5,540.00", "5,540.00", ZERO_AMOUNT, ZERO_AMOUNT, ZERO_AMOUNT, "Bus1088.89 Sch1088.89", "2011",
        "2012", "Aall1088.89 Pamela R.;Hampson1088.89 Fen Osler.;Crocker1088.89 Chester A."};
    private static final String[] USAGE_3 = {"5347181578", BATCH_NAME, FISCAL_YEAR, ROR_ACCOUNT_NUMBER,
        ROR_NAME, PAYMENT_DATE, "(En)gendering the war on terror : war stories and camouflaged politics",
        "between orientalism and fundamentalism", "12345XX-79073", "122235137", "IEEE", PUB_DATE, "20",
        MIN_REPORTED_VALUE, MIN_REPORTED_VALUE, ZERO_AMOUNT, ZERO_AMOUNT, ZERO_AMOUNT, "Univ", "2013", "2017",
        "Aarseth, Espen J."};
    private Scenario scenario;
    private UsageBatch batch;
    private WebElement drillDownWindow;

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    @Autowired
    private IScenarioRepository scenarioRepository;

    @Override
    public void setUp() {
        super.setUp();
        insertTestData();
        loginAsViewOnly();
        drillDownWindow = openDrillDownWindow();
    }

    @After
    public void tearDown() {
        usageRepository.deleteFromScenario(scenario.getId(), "user@copyright.com");
        scenarioRepository.remove(scenario.getId());
        usageRepository.deleteUsages(batch.getId());
        usageBatchRepository.deleteUsageBatch(batch.getId());
        scenario = null;
        batch = null;
    }

    @Test
    // Test case IDs: '5de1a768-0080-4853-ae22-b2eeff84e7df', '076209f7-f209-4dbb-a98d-0f669fb7ee65'
    public void testViewDrillDownWindow() {
        assertSearchToolbar(drillDownWindow, "Enter Detail ID or Standard Number or Wr Wrk Inst or RRO Name/Account #");
        verifyTable(assertWebElement(drillDownWindow, "drill-down-by-rightsholder-table"));
    }

    @Test
    // Test case ID: '8a7780c3-34a7-4f3d-9196-3018e8aaf008'
    public void testSortingOnDrillDownWindow() {
        assertTableSorting(assertWebElement(drillDownWindow, "drill-down-by-rightsholder-table"),
            getExpectedColumnValuesForSorting());
    }

    @Test
    public void testSearch() {
        WebElement searchToolbar = assertWebElement(drillDownWindow, By.className("search-toolbar"));
        WebElement table = assertWebElement(drillDownWindow, By.className("v-table"));
        Map<String, List<String[]>> searchMap = new HashMap<>();
        searchMap.put("   5248153472   ", Collections.singletonList(USAGE_2));
        searchMap.put("Access Copyright", Lists.newArrayList(USAGE_1, USAGE_2, USAGE_3));
        searchMap.put("2000017004", Lists.newArrayList(USAGE_1, USAGE_2, USAGE_3));
        searchMap.put("    12345XX-79  ", Collections.singletonList(USAGE_3));
        searchMap.put("122235139", Collections.singletonList(USAGE_1));
        assertSearch(searchToolbar, table, searchMap);
    }

    private WebElement openDrillDownWindow() {
        WebElement scenarioTab = selectScenariosTab();
        assertWebElement(scenarioTab, HTML_DIV_TAG_NAME, "Scenario for drill down window");
        clickElementAndWait(assertWebElement(scenarioTab, "View"));
        clickElementAndWait(waitAndFindElementByText(assertWebElement(By.id("view-scenario-widget")),
            HTML_SPAN_TAG_NAME, "1000005413"));
        return assertWebElement(By.id("drill-down-by-rightsholder-widget"));
    }

    private void verifyTable(WebElement table) {
        assertTableHeaderElements(table, TABLE_HEADER);
        List<WebElement> rows = assertTableRowElements(table, 3);
        assertTableRowElements(rows.get(0), USAGE_1);
        assertTableRowElements(rows.get(1), USAGE_2);
        assertTableRowElements(rows.get(2), USAGE_3);
    }

    private ExpectedColumnValues[] getExpectedColumnValuesForSorting() {
        return new ExpectedColumnValues[]{
            new ExpectedColumnValues("Detail ID", "3982472103", "5347181578"),
            new ExpectedColumnValues("Usage Batch Name", BATCH_NAME, BATCH_NAME),
            new ExpectedColumnValues("Fiscal Year", FISCAL_YEAR, FISCAL_YEAR),
            new ExpectedColumnValues("RRO Account #", ROR_ACCOUNT_NUMBER, ROR_ACCOUNT_NUMBER),
            new ExpectedColumnValues("RRO Name", ROR_NAME, ROR_NAME),
            new ExpectedColumnValues("Payment Date", PAYMENT_DATE, PAYMENT_DATE),
            new ExpectedColumnValues("Title", "40 model essays : a portable anthology",
                "Managing brand equity : capitalizing on the value of a brand name"),
            new ExpectedColumnValues("Article", "between orientalism and fundamentalism",
                "The ritual of fast food"),
            new ExpectedColumnValues("Standard Number", "12345XX-01", "12345XX-89173"),
            new ExpectedColumnValues("Wr Wrk Inst", "122235137", "122235139"),
            new ExpectedColumnValues("Publisher", "American Society for Nutritional Science", "Simon & Schuster US"),
            new ExpectedColumnValues("Pub Date", PUB_DATE, PUB_DATE),
            new ExpectedColumnValues("Number of Copies", "20", "370"),
            new ExpectedColumnValues("Reported value", MIN_REPORTED_VALUE, MAX_REPORTED_VALUE),
            new ExpectedColumnValues("Gross Amt in USD", MIN_REPORTED_VALUE, MAX_REPORTED_VALUE),
            new ExpectedColumnValues("Service Fee Amount", ZERO_AMOUNT, ZERO_AMOUNT),
            new ExpectedColumnValues("Net Amt in USD", ZERO_AMOUNT, ZERO_AMOUNT),
            new ExpectedColumnValues("Service Fee %", SERVICE_FEE, SERVICE_FEE),
            new ExpectedColumnValues("Market", "Bus1088.89 Sch1088.89", "Univ"),
            new ExpectedColumnValues("Market Period From", "2009", "2013"),
            new ExpectedColumnValues("Market Period To", "2010", "2017"),
            new ExpectedColumnValues("Author",
                "Aall1088.89 Pamela R.;Hampson1088.89 Fen Osler.;Crocker1088.89 Chester A.", "Aarseth, Espen J.")
        };
    }

    private void insertTestData() {
        initBatch();
        initScenario();
        usageBatchRepository.insert(batch);
        scenarioRepository.insert(scenario);
        usageRepository.insert(buildUsage(USAGE_1));
        usageRepository.insert(buildUsage(USAGE_2));
        usageRepository.insert(buildUsage(USAGE_3));
    }

    private void initScenario() {
        scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        scenario.setName("Scenario for drill down window");
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setGrossTotal(BATCH_TOTAL_AMOUNT);
        scenario.setReportedTotal(BATCH_TOTAL_AMOUNT);
    }

    private void initBatch() {
        batch = new UsageBatch();
        batch.setId(RupPersistUtils.generateUuid());
        batch.setName(BATCH_NAME);
        batch.setRro(buildRightsholder(2000017004L));
        batch.setFiscalYear(2016);
        batch.setGrossAmount(BATCH_TOTAL_AMOUNT);
        batch.setPaymentDate(LocalDate.of(2015, 9, 10));
    }

    private Usage buildUsage(String... fields) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setDetailId(Long.valueOf(fields[0]));
        usage.setBatchId(batch.getId());
        usage.setScenarioId(scenario.getId());
        usage.setRightsholder(buildRightsholder(1000005413L));
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setWorkTitle(fields[6]);
        usage.setArticle(fields[7]);
        usage.setStandardNumber(fields[8]);
        usage.setWrWrkInst(Long.valueOf(fields[9]));
        usage.setPublisher(fields[10]);
        usage.setPublicationDate(LocalDate.parse(fields[11], DateTimeFormatter.ofPattern("M/d/uuuu", Locale.US)));
        usage.setNumberOfCopies(Integer.valueOf(fields[12]));
        usage.setReportedValue(new BigDecimal(fields[13].replaceAll(",", StringUtils.EMPTY)));
        usage.setGrossAmount(new BigDecimal(fields[14].replaceAll(",", StringUtils.EMPTY)));
        usage.setMarket(fields[18]);
        usage.setMarketPeriodFrom(Integer.valueOf(fields[19]));
        usage.setMarketPeriodTo(Integer.valueOf(fields[20]));
        usage.setAuthor(fields[21]);
        return usage;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }
}
