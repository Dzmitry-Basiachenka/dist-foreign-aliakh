package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.repository.api.Sort.Direction;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Integration test for {@link UsageRepository}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Darya Baraukova
 * @author Mikalai Bezmen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-sql-test-context.xml"})
@TransactionConfiguration
@Transactional
public class UsageRepositoryIntegrationTest {

    private static final String USAGE_BATCH_ID_1 = "56282dbc-2468-48d4-b926-93d3458a656a";
    private static final String USAGE_ID = RupPersistUtils.generateUuid();
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2017, 1, 11);
    private static final Integer FISCAL_YEAR = 2017;
    private static final String RH_ACCOUNT_NAME =
        "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil";
    private static final String RH_ACCOUNT_NAME_1 = "IEEE - Inst of Electrical and Electronics Engrs";
    private static final String RH_ACCOUNT_NAME_2 = "John Wiley & Sons - Books";
    private static final String RH_ACCOUNT_NAME_3 = "Kluwer Academic Publishers - Dordrecht";
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("54.4400000000");
    private static final Long WR_WRK_INST = 123456783L;
    private static final String WORK_TITLE = "Work Title";
    private static final String ARTICLE = "Article";
    private static final String STANDART_NUMBER = "StandardNumber";
    private static final String PUBLISHER = "Publisher";
    private static final String MARKET = "Market";
    private static final Integer MARKED_PERIOD_FROM = 2015;
    private static final Integer MARKED_PERIOD_TO = 2017;
    private static final String AUTHOR = "Author";
    private static final BigDecimal REPORTED_VALUE = new BigDecimal("11.25");
    private static final LocalDate PUBLICATION_DATE = LocalDate.of(2016, 11, 3);
    private static final Long DETAIL_ID = 12345L;
    private static final Long DETAIL_ID_1 = 6997788884L;
    private static final Long DETAIL_ID_2 = 6997788886L;
    private static final Integer NUMBER_OF_COPIES = 155;
    private static final String DETAIL_ID_KEY = "detailId";
    private static final String WORK_TITLE_KEY = "workTitle";
    private static final String ARTICLE_KEY = "article";
    private static final String STANDART_NUMBER_KEY = "standardNumber";
    private static final String WR_WRK_INST_KEY = "wrWrkInst";
    private static final String RH_ACCOUNT_NUMBER_KEY = "rhAccountNumber";
    private static final String PUBLISHER_KEY = "publisher";
    private static final String PUBLICATION_DATE_KEY = "publicationDate";
    private static final String NUMBER_OF_COPIES_KEY = "numberOfCopies";
    private static final String REPORTED_VALUE_KEY = "reportedValue";
    private static final String GROSS_AMOUNT_KEY = "grossAmount";
    private static final String MARKET_KEY = "market";
    private static final String MARKED_PERIOD_FROM_KEY = "marketPeriodFrom";
    private static final String MARKED_PERIOD_TO_KEY = "marketPeriodTo";
    private static final String AUTHOR_KEY = "author";
    private static final String BATCH_NAME_KEY = "batchName";
    private static final String FISCAL_YEAR_KEY = "fiscalYear";
    private static final String RRO_ACCOUNT_NUMBER_KEY = "rroAccountNumber";
    private static final String PAYMENT_DATE_KEY = "paymentDate";
    private static final String USAGE_ID_1 = "111111111";
    private static final String USAGE_ID_2 = "222222222";
    private static final String USAGE_ID_3 = "444444444";
    private static final String SCENARIO_ID = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";
    private static final String USER_NAME = "User Name";
    private static final BigDecimal NET_AMOUNT = new BigDecimal("25.1500000000");

    @Autowired
    private UsageRepository usageRepository;

    @Test
    public void testInsert() {
        usageRepository.insert(buildUsage(USAGE_ID, USAGE_BATCH_ID_1));
        Usage usage = usageRepository.findByDetailId(DETAIL_ID);
        assertNotNull(usage);
        assertEquals(USAGE_ID, usage.getId());
        assertEquals(SCENARIO_ID, usage.getScenarioId());
        assertEquals(WR_WRK_INST, usage.getWrWrkInst());
        assertEquals(WORK_TITLE, usage.getWorkTitle());
        assertEquals(RH_ACCOUNT_NUMBER, usage.getRightsholder().getAccountNumber());
        assertEquals(RH_ACCOUNT_NAME, usage.getRightsholder().getName());
        assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        assertEquals(ARTICLE, usage.getArticle());
        assertEquals(STANDART_NUMBER, usage.getStandardNumber());
        assertEquals(PUBLISHER, usage.getPublisher());
        assertEquals(PUBLICATION_DATE, usage.getPublicationDate());
        assertEquals(MARKET, usage.getMarket());
        assertEquals(MARKED_PERIOD_FROM, usage.getMarketPeriodFrom());
        assertEquals(MARKED_PERIOD_TO, usage.getMarketPeriodTo());
        assertEquals(AUTHOR, usage.getAuthor());
        assertEquals(NUMBER_OF_COPIES, usage.getNumberOfCopies());
        assertEquals(REPORTED_VALUE, usage.getReportedValue());
        assertEquals(GROSS_AMOUNT, usage.getGrossAmount());
    }

    @Test
    public void testGetCountByFilter() {
        assertEquals(1, usageRepository.getCountByFilter(
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR)));
    }

    @Test
    public void testFindByFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 1, USAGE_ID_1);
    }

    @Test
    public void testFindByUsageBatchFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1), null, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 1, USAGE_ID_1);
    }

    @Test
    public void testFindByRhAccountNumberFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.emptySet(), null, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 1, USAGE_ID_1);
    }

    @Test
    public void testFindByStatusFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByPaymentDateFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.emptySet(), null, PAYMENT_DATE, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 2, USAGE_ID_2, USAGE_ID_1);
    }

    @Test
    public void testFindByFiscalYearFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.emptySet(), null, null, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 2, USAGE_ID_2, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByDetailId() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByWorkTitle() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(WORK_TITLE_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByArticle() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(ARTICLE_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByStandartNumber() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(STANDART_NUMBER_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByWrWrkInst() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(WR_WRK_INST_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByRhAccountNumber() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(RH_ACCOUNT_NUMBER_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByPublisher() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(PUBLISHER_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByPublicationDate() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(PUBLICATION_DATE_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByNumberOfCopies() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(NUMBER_OF_COPIES_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByReportedValue() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(REPORTED_VALUE_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByGrossAmount() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(GROSS_AMOUNT_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByMarket() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(MARKET_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByMarketPeriodFrom() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(MARKED_PERIOD_FROM_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByMarketPeriodTo() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(MARKED_PERIOD_TO_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByAuthor() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(AUTHOR_KEY, Sort.Direction.ASC)), 2, USAGE_ID_3, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByBatchName() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(BATCH_NAME_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByFiscalYear() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(FISCAL_YEAR_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByRroAccountNumber() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(RRO_ACCOUNT_NUMBER_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByPaymentDate() {
        verifyUsageDtos(usageRepository.findByFilter(new UsageFilter(), new Pageable(0, 200),
            new Sort(PAYMENT_DATE_KEY, Sort.Direction.ASC)), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testGetRightsholderTotalsHoldersByScenarioIdEmptySearchValue() {
        populateScenario();
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.getRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY,
                new Pageable(0, 200), null);
        assertEquals(3, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 13461.54, 2153.00, 11308.00),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 21061.54, 3369.00, 17692.00),
            rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 6892.30, 1102.00, 5790.00),
            rightsholderTotalsHolders.get(2));
    }

    @Test
    public void testGetRightsholderTotalsHoldersByScenarioIdNotEmptySearchValue() {
        populateScenario();
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.getRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "JoHn", new Pageable(0, 200), null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 21061.54, 3369.00, 17692.00),
            rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageRepository.getRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "IEEE", new Pageable(0, 200), null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 13461.54, 2153.00, 11308.00),
            rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageRepository.getRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "ec", new Pageable(0, 200), null);
        assertEquals(2, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 13461.54, 2153.00, 11308.00),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 6892.30, 1102.00, 5790.00),
            rightsholderTotalsHolders.get(1));
    }

    @Test
    public void testGetRightsholderTotalsHoldersByScenarioIdSortByAccountNumber() {
        populateScenario();
        Sort accountNumberSort = new Sort("rightsholderAccountNumber", Direction.ASC);
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.getRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY,
                new Pageable(0, 200), accountNumberSort);
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 21061.54, 3369.00, 17692.00),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 6892.30, 1102.00, 5790.00),
            rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 13461.54, 2153.00, 11308.00),
            rightsholderTotalsHolders.get(2));
    }

    @Test
    public void testGetRightsholderTotalsHoldersByScenarioIdSortByName() {
        populateScenario();
        Sort accountNumberSort = new Sort("rightsholderName", Direction.DESC);
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.getRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY,
                new Pageable(0, 200), accountNumberSort);
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 6892.30, 1102.00, 5790.00),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 21061.54, 3369.00, 17692.00),
            rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 13461.54, 2153.00, 11308.00),
            rightsholderTotalsHolders.get(2));
    }

    @Test
    public void testGetRightsholderTotalsHolderCountByScenarioId() {
        populateScenario();
        assertEquals(3, usageRepository.getRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY));
        assertEquals(1, usageRepository.getRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, "IEEE"));
    }

    @Test
    public void testWriteUsagesCsvReport() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(USAGE_BATCH_ID_1));
        usageRepository.writeUsagesCsvReport(usageFilter, outputStream);
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        assertEquals("Detail ID,Detail Status,Usage Batch Name,Fiscal Year,RRO Account #,RRO Name,Payment Date,Title," +
                "Article,Standard Number,Wr Wrk Inst,RH Account #,RH Name,Publisher,Pub Date,Number of Copies," +
                "Reported value,Amt in USD,Gross Amt in USD,Market,Market Period From,Market Period To,Author",
            bufferedReader.readLine());
        assertEquals("6997788888,ELIGIBLE,CADRA_11Dec16,FY2017,7000813806," +
            "\"CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil\",01/11/2017," +
            "\"2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA\"," +
            "Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle,1008902112377654XX," +
            "180382914,1000009997,IEEE - Inst of Electrical and Electronics Engrs," +
            "IEEE,09/10/2013,2502232,2500.00,13461.5400000000,35000.00,Doc Del,2013,2017," +
            "\"Íñigo López de Mendoza, marqués de Santillana\"", bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    @Test
    public void testWriteUsagesEmptyCsvReport() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        UsageFilter usageFilter = new UsageFilter();
        usageRepository.writeUsagesCsvReport(usageFilter, outputStream);
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
        assertEquals("Detail ID,Detail Status,Usage Batch Name,Fiscal Year,RRO Account #,RRO Name,Payment Date,Title," +
                "Article,Standard Number,Wr Wrk Inst,RH Account #,RH Name,Publisher,Pub Date,Number of Copies," +
                "Reported value,Amt in USD,Gross Amt in USD,Market,Market Period From,Market Period To,Author",
            bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    @Test
    public void testWriteScenarioUsagesCsvReport() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        usageRepository.writeScenarioUsagesCsvReport(SCENARIO_ID, outputStream);
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, Charset.forName("utf-8")));
        assertEquals("Detail ID,Usage Batch Name,Fiscal Year,RRO Account #,RRO Name,Payment Date,Title,Article," +
            "Standard Number,Wr Wrk Inst,RH Account #,RH Name,Payee Account #,Payee Name,Publisher,Pub Date," +
            "Number of Copies,Reported value,Gross Amt in USD,Service Fee Amount,Net Amt in USD,Service Fee %," +
            "Market,Market Period From,Market Period To,Author", bufferedReader.readLine());
        assertEquals("6997788886,JAACC_11Dec16,FY2019,7001440663,\"JAACC, Japan Academic Association for Copyright" +
            " Clearance [T]\",08/16/2018,100 ROAD MOVIES,DIN EN 779:2012,1008902112377654XX,243904752,1000002859," +
            "John Wiley & Sons - Books,,,IEEE,09/10/2013,250232,9900.00,11461.5400000000,1833.0000000000," +
            "9628.0000000000,0.0,Doc Del,2013,2017,Philippe de Mézières", bufferedReader.readLine());
        assertEquals("6213788886,JAACC_11Dec16,FY2019,7001440663,\"JAACC, Japan Academic Association for Copyright" +
            " Clearance [T]\",08/16/2018,100 ROAD MOVIES,DIN EN 779:2012,1008902112317622XX,243904752,1000002859," +
            "John Wiley & Sons - Books,,,IEEE,09/10/2013,100,9900.00,1200.0000000000,192.0000000000,1008.0000000000," +
            "0.0,Doc Del,2013,2017,Philippe de Mézières", bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    @Test
    public void testDeleteUsages() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Sets.newHashSet(USAGE_BATCH_ID_1));
        Pageable pageable = new Pageable(0, 100);
        Sort sort = new Sort(DETAIL_ID_KEY, Direction.ASC);
        assertEquals(1, usageRepository.findByFilter(filter, pageable, sort).size());
        usageRepository.deleteUsages(USAGE_BATCH_ID_1);
        assertEquals(0, usageRepository.findByFilter(filter, pageable, sort).size());
    }

    @Test
    public void testFindWithAmounts() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsages(usageRepository.findWithAmounts(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testFindWithAmountsByUsageBatchFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1), null, null, null);
        verifyUsages(usageRepository.findWithAmounts(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testFindWithAmountsByRhAccountNumberFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.emptySet(), null, null, null);
        verifyUsages(usageRepository.findWithAmounts(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testFindWithAmountsByStatusFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmounts(usageFilter), 2, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindWithAmountsByPaymentDateFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.emptySet(), null, PAYMENT_DATE, null);
        verifyUsages(usageRepository.findWithAmounts(usageFilter), 2, USAGE_ID_1, USAGE_ID_2);
    }

    @Test
    public void testFindWithAmountsByFiscalYearFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.emptySet(), null, null, FISCAL_YEAR);
        verifyUsages(usageRepository.findWithAmounts(usageFilter), 2, USAGE_ID_1, USAGE_ID_2);
    }

    @Test
    public void testAddToScenario() {
        verifyUsage(usageRepository.findByDetailId(DETAIL_ID_1), UsageStatusEnum.ELIGIBLE, null,
            StoredEntity.DEFAULT_USER);
        usageRepository.addToScenario(Collections.singletonList(USAGE_ID_3), SCENARIO_ID, USER_NAME);
        verifyUsage(usageRepository.findByDetailId(DETAIL_ID_1), UsageStatusEnum.LOCKED, SCENARIO_ID, USER_NAME);
    }

    @Test
    public void testDeleteFromScenario() {
        verifyUsage(usageRepository.findByDetailId(DETAIL_ID_2), UsageStatusEnum.LOCKED, SCENARIO_ID,
            StoredEntity.DEFAULT_USER);
        usageRepository.deleteFromScenario(SCENARIO_ID, USER_NAME);
        verifyUsage(usageRepository.findByDetailId(DETAIL_ID_2), UsageStatusEnum.ELIGIBLE, null, USER_NAME);
    }

    @Test
    public void testGetDuplicateDetailIds() {
        assertTrue(CollectionUtils.containsAny(Sets.newHashSet(DETAIL_ID_1, DETAIL_ID_2),
            usageRepository.getDuplicateDetailIds(Lists.newArrayList(DETAIL_ID_1, DETAIL_ID_2, -1L))));
    }

    private void verifyUsage(Usage usage, UsageStatusEnum status, String scenarioId, String defaultUser) {
        assertNotNull(usage);
        assertEquals(status, usage.getStatus());
        assertEquals(scenarioId, usage.getScenarioId());
        assertEquals(defaultUser, usage.getUpdateUser());
    }

    private UsageFilter buildUsageFilter(Set<Long> accountNumbers, Set<String> usageBatchIds, UsageStatusEnum status,
                                         LocalDate paymentDate, Integer fiscalYear) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setRhAccountNumbers(accountNumbers);
        usageFilter.setUsageBatchesIds(usageBatchIds);
        usageFilter.setUsageStatus(status);
        usageFilter.setPaymentDate(paymentDate);
        usageFilter.setFiscalYear(fiscalYear);
        return usageFilter;
    }

    private Usage buildUsage(String usageId, String usageBatchId) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setBatchId(usageBatchId);
        usage.setScenarioId(SCENARIO_ID);
        usage.setDetailId(DETAIL_ID);
        usage.setWrWrkInst(WR_WRK_INST);
        usage.setWorkTitle(WORK_TITLE);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(RH_ACCOUNT_NUMBER);
        rightsholder.setName(RH_ACCOUNT_NAME);
        usage.setRightsholder(rightsholder);
        usage.setStatus(UsageStatusEnum.ELIGIBLE);
        usage.setArticle(ARTICLE);
        usage.setStandardNumber(STANDART_NUMBER);
        usage.setPublisher(PUBLISHER);
        usage.setPublicationDate(PUBLICATION_DATE);
        usage.setMarket(MARKET);
        usage.setMarketPeriodFrom(MARKED_PERIOD_FROM);
        usage.setMarketPeriodTo(MARKED_PERIOD_TO);
        usage.setAuthor(AUTHOR);
        usage.setNumberOfCopies(NUMBER_OF_COPIES);
        usage.setReportedValue(REPORTED_VALUE);
        usage.setGrossAmount(GROSS_AMOUNT);
        usage.setNetAmount(NET_AMOUNT);
        return usage;
    }

    private RightsholderTotalsHolder buildRightsholderTotalsHolder(String rhName, Long rhAccountNumber,
                                                                   Double grossTotal, Double serviceFeeTotal,
                                                                   Double netTotal) {
        RightsholderTotalsHolder rightsholderTotalsHolder = new RightsholderTotalsHolder();
        rightsholderTotalsHolder.setRightsholderName(rhName);
        rightsholderTotalsHolder.setRightsholderAccountNumber(rhAccountNumber);
        rightsholderTotalsHolder.setGrossTotal(BigDecimal.valueOf(grossTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setServiceFeeTotal(
            BigDecimal.valueOf(serviceFeeTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setNetTotal(BigDecimal.valueOf(netTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        return rightsholderTotalsHolder;
    }

    private void populateScenario() {
        usageRepository.addToScenario(Collections.singletonList(USAGE_ID_1), SCENARIO_ID, USER_NAME);
        usageRepository.addToScenario(Collections.singletonList(USAGE_ID_2), SCENARIO_ID, USER_NAME);
        usageRepository.addToScenario(Collections.singletonList(USAGE_ID_3), SCENARIO_ID, USER_NAME);
    }

    private void verifyUsageDtos(List<UsageDto> usageDtos, int count, String... usageIds) {
        assertNotNull(usageDtos);
        assertEquals(count, usageDtos.size());
        for (int i = 0; i < count; i++) {
            assertEquals(usageIds[i], usageDtos.get(i).getId());
        }
    }

    private void verifyUsages(List<Usage> usages, int count, String... usageIds) {
        assertNotNull(usages);
        assertEquals(count, usages.size());
        for (int i = 0; i < count; i++) {
            assertEquals(usageIds[i], usages.get(i).getId());
        }
    }
}
