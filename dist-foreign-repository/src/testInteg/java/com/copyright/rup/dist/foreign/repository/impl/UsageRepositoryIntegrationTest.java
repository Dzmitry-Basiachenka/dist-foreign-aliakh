package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.AuditFilter;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.repository.api.Sort.Direction;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

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
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usage-repository-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class UsageRepositoryIntegrationTest {

    private static final String USAGE_BATCH_ID_1 = "56282dbc-2468-48d4-b926-93d3458a656a";
    private static final String USAGE_ID = RupPersistUtils.generateUuid();
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2018, 12, 11);
    private static final Integer FISCAL_YEAR = 2019;
    private static final String RH_ACCOUNT_NAME =
        "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil";
    private static final String RH_ACCOUNT_NAME_1 = "IEEE - Inst of Electrical and Electronics Engrs";
    private static final String RH_ACCOUNT_NAME_2 = "John Wiley & Sons - Books";
    private static final String RH_ACCOUNT_NAME_3 = "Kluwer Academic Publishers - Dordrecht";
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("54.4400000000");
    private static final BigDecimal ZERO_AMOUNT = BigDecimal.ZERO.setScale(10);
    private static final Long WR_WRK_INST = 123456783L;
    private static final String WORK_TITLE = "Work Title";
    private static final String PRODUCT_FAMILY = "FAS";
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
    private static final String USAGE_ID_1 = "3ab5e80b-89c0-4d78-9675-54c7ab284450";
    private static final String USAGE_ID_2 = "8a06905f-37ae-4e1f-8550-245277f8165c";
    private static final String USAGE_ID_3 = "5c5f8c1c-1418-4cfd-8685-9212f4c421d1";
    private static final String USAGE_ID_4 = "d9ca07b5-8282-4a81-9b9d-e4480f529d34";
    private static final String USAGE_ID_5 = "a71a0544-128e-41c0-b6b0-cfbbea6d2182";
    private static final String USAGE_ID_6 = "0b0f5100-01bd-11e8-8f1a-0800200c9a66";
    private static final String USAGE_ID_7 = "cf38d390-11bb-4af7-9685-e034c9c32fb6";
    private static final String USAGE_ID_8 = "b1f0b236-3ae9-4a60-9fab-61db84199dss";
    private static final String USAGE_ID_9 = "3900eea0-1231-11e8-b566-0800200c9a66";
    private static final String USAGE_ID_10 = "4dd8cdf8-ca10-422e-bdd5-3220105e6379";
    private static final String USAGE_ID_11 = "7db6455e-5249-44db-801a-307f1c239310";
    private static final String USAGE_ID_12 = "593c49c3-eb5b-477b-8556-f7a4725df2b3";
    private static final String SCENARIO_ID = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";
    private static final String USER_NAME = "user@copyright.com";
    private static final BigDecimal NET_AMOUNT = new BigDecimal("25.1500000000");
    private static final BigDecimal SERVICE_FEE = new BigDecimal("0.32000");
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static final String BATCH_ID = "e0af666b-cbb7-4054-9906-12daa1fbd76e";

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
        assertEquals(PRODUCT_FAMILY, usage.getProductFamily());
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
        assertFalse(usage.isRhParticipating());
    }

    @Test
    public void testFindCountByFilter() {
        assertEquals(1, usageRepository.findCountByFilter(
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                Collections.singleton(PRODUCT_FAMILY), UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR)));
    }

    @Test
    public void testFindByFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                Collections.singleton(PRODUCT_FAMILY), UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 1,
            USAGE_ID_1);
    }

    @Test
    public void testFindByUsageBatchFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1),
            Collections.emptySet(), null, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 1,
            USAGE_ID_1);
    }

    @Test
    public void testFindByRhAccountNumberFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.emptySet(),
            Collections.emptySet(), null, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 1,
            USAGE_ID_1);
    }

    @Test
    public void testFindByProductFamiliesFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            Collections.singleton(PRODUCT_FAMILY), null, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 7,
            USAGE_ID_11, USAGE_ID_12, USAGE_ID_6, USAGE_ID_3, USAGE_ID_2, USAGE_ID_1, USAGE_ID_4);
    }

    @Test
    public void testFindByStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 4,
            USAGE_ID_3, USAGE_ID_2, USAGE_ID_1, USAGE_ID_10);
    }

    @Test
    public void testFindByPaymentDateFilterSortByWorkTitle() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            Collections.emptySet(), null, PAYMENT_DATE, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort(WORK_TITLE_KEY, Sort.Direction.ASC)),
            3, USAGE_ID_3, USAGE_ID_2, USAGE_ID_1);
    }

    @Test
    public void testFindByFiscalYearFilterSortByArticle() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            Collections.emptySet(), null, null, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort(ARTICLE_KEY, Sort.Direction.ASC)), 3,
            USAGE_ID_3, USAGE_ID_1, USAGE_ID_2);
    }

    @Test
    public void testFindByFilterSortByStandartNumber() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(STANDART_NUMBER_KEY, Sort.Direction.ASC)), 4, USAGE_ID_2, USAGE_ID_3, USAGE_ID_1, USAGE_ID_10);
    }

    @Test
    public void testFindByFilterSortByWrWrkInst() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(WR_WRK_INST_KEY, Sort.Direction.ASC)), 4, USAGE_ID_1, USAGE_ID_2, USAGE_ID_3, USAGE_ID_10);
    }

    @Test
    public void testFindByFilterSortByRhAccountNumber() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(RH_ACCOUNT_NUMBER_KEY, Sort.Direction.ASC)), 4, USAGE_ID_2, USAGE_ID_3,
            USAGE_ID_1, USAGE_ID_10);
    }

    @Test
    public void testFindByFilterSortByPublisher() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(PUBLISHER_KEY, Sort.Direction.ASC)), 4, USAGE_ID_1, USAGE_ID_10, USAGE_ID_2, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByPublicationDate() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(PUBLICATION_DATE_KEY, Sort.Direction.ASC)), 4, USAGE_ID_3, USAGE_ID_2, USAGE_ID_1, USAGE_ID_10);
    }

    @Test
    public void testFindByFilterSortByNumberOfCopies() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(NUMBER_OF_COPIES_KEY, Sort.Direction.ASC)), 4, USAGE_ID_2, USAGE_ID_3, USAGE_ID_1, USAGE_ID_10);
    }

    @Test
    public void testFindByFilterSortByReportedValue() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(REPORTED_VALUE_KEY, Sort.Direction.ASC)), 4, USAGE_ID_10, USAGE_ID_3, USAGE_ID_2, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByGrossAmount() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(GROSS_AMOUNT_KEY, Sort.Direction.ASC)), 4, USAGE_ID_10, USAGE_ID_3, USAGE_ID_1, USAGE_ID_2);
    }

    @Test
    public void testFindByFilterSortByMarket() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(MARKET_KEY, Sort.Direction.ASC)), 4, USAGE_ID_2, USAGE_ID_1, USAGE_ID_10, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByMarketPeriodFrom() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(MARKED_PERIOD_FROM_KEY, Sort.Direction.ASC)), 4, USAGE_ID_1, USAGE_ID_10, USAGE_ID_2, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByMarketPeriodTo() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(MARKED_PERIOD_TO_KEY, Sort.Direction.ASC)), 4, USAGE_ID_1, USAGE_ID_10, USAGE_ID_2, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByAuthor() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(AUTHOR_KEY, Sort.Direction.ASC)), 4, USAGE_ID_1, USAGE_ID_3, USAGE_ID_2, USAGE_ID_10);
    }

    @Test
    public void testFindByFilterSortByBatchName() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(BATCH_NAME_KEY, Sort.Direction.ASC)), 4, USAGE_ID_2, USAGE_ID_10, USAGE_ID_1, USAGE_ID_3);
    }

    @Test
    public void testFindByFilterSortByFiscalYear() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(FISCAL_YEAR_KEY, Sort.Direction.ASC)), 4, USAGE_ID_3, USAGE_ID_1, USAGE_ID_2, USAGE_ID_10);
    }

    @Test
    public void testFindByFilterSortByRroAccountNumber() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(RRO_ACCOUNT_NUMBER_KEY, Sort.Direction.ASC)), 4, USAGE_ID_2, USAGE_ID_3, USAGE_ID_10, USAGE_ID_1);
    }

    @Test
    public void testFindByFilterSortByPaymentDate() {
        verifyUsageDtos(usageRepository.findByFilter(buildFilterWithStatuses(UsageStatusEnum.ELIGIBLE), null,
            new Sort(PAYMENT_DATE_KEY, Sort.Direction.ASC)), 4, USAGE_ID_3, USAGE_ID_1, USAGE_ID_2, USAGE_ID_10);
    }

    @Test
    public void testFindRightsholderTotalsHoldersByScenarioIdEmptySearchValue() {
        populateScenario();
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null, null);
        assertEquals(3, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 67874.80, 21720.00, 46154.80),
            rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 2125.24, 680.0768, 1445.1632),
            rightsholderTotalsHolders.get(2));
    }

    @Test
    public void testFindRightsholderTotalsHoldersByScenarioIdNotEmptySearchValue() {
        populateScenario();
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "JoHn", null, null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 67874.80, 21720.00, 46154.80),
            rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "IEEE", null, null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "ec", null, null);
        assertEquals(2, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 2125.24, 680.0768, 1445.1632),
            rightsholderTotalsHolders.get(1));
    }

    @Test
    public void testFindRightsholderTotalsHoldersByScenarioIdSortByAccountNumber() {
        populateScenario();
        Sort accountNumberSort = new Sort("rightsholder.accountNumber", Direction.ASC);
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null,
                accountNumberSort);
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 67874.80, 21720.00, 46154.80),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 2125.24, 680.0768, 1445.1632),
            rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(2));
    }

    @Test
    public void testFindRightsholderTotalsHoldersByScenarioIdSortByName() {
        populateScenario();
        Sort accountNumberSort = new Sort("rightsholder.name", Direction.DESC);
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null,
                accountNumberSort);
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 2125.24, 680.0768, 1445.1632),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 67874.80, 21720.00, 46154.80),
            rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(2));
    }

    @Test
    public void testFindCountByScenarioIdAndRhAccountNumberNullSearchValue() {
        populateScenario();
        Usage usage = buildUsage(RupPersistUtils.generateUuid(), USAGE_BATCH_ID_1);
        usageRepository.insert(usage);
        usageRepository.addToScenario(Collections.singletonList(usage));
        assertEquals(1, usageRepository.findCountByScenarioIdAndRhAccountNumber(1000009997L, SCENARIO_ID, null));
        assertEquals(3, usageRepository.findCountByScenarioIdAndRhAccountNumber(1000002859L, SCENARIO_ID, null));
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberNullSearchValue() {
        populateScenario();
        assertEquals(1,
            usageRepository.findByScenarioIdAndRhAccountNumber(1000009997L, SCENARIO_ID, null, null, null).size());
        assertEquals(3,
            usageRepository.findByScenarioIdAndRhAccountNumber(1000002859L, SCENARIO_ID, null, null, null).size());
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByRorName() {
        populateScenario();
        verifySearch("Access Copyright, The Canadian Copyright Agency", 1);
        verifySearch("Academic", 2);
        verifySearch("aCaDemiC", 2);
        verifySearch("Aca demic", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByRorAccountNumber() {
        populateScenario();
        verifySearch("2000017010", 2);
        verifySearch("0001700", 1);
        verifySearch("70014 40663", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchDetailId() {
        populateScenario();
        verifySearch("6997788885", 1);
        verifySearch("78888", 3);
        verifySearch("6997 788885", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByWrWrkInst() {
        populateScenario();
        verifySearch("243904752", 2);
        verifySearch("244614", 1);
        verifySearch("24461 4835", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByStandardNumber() {
        populateScenario();
        verifySearch("1008902002377655XX", 1);
        verifySearch("1008902002377655xx", 1);
        verifySearch("10089", 3);
        verifySearch("100890 2002377655XX", 0);
    }

    @Test
    public void testFindRightsholderTotalsHolderCountByScenarioId() {
        populateScenario();
        assertEquals(3, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY));
        assertEquals(1, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, "IEEE"));
    }

    @Test
    public void testWriteUsagesCsvReport() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(USAGE_BATCH_ID_1));
        EXECUTOR_SERVICE.execute(() -> usageRepository.writeUsagesCsvReport(usageFilter, outputStream));
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        assertEquals("Detail ID,Detail Status,Product Family,Usage Batch Name,Fiscal Year,RRO Account #,RRO Name," +
            "Payment Date,Title,Article,Standard Number,Wr Wrk Inst,RH Account #,RH Name,Publisher," +
            "Pub Date,Number of Copies,Reported value,Amt in USD,Gross Amt in USD,Market,Market Period From," +
            "Market Period To,Author", bufferedReader.readLine());
        assertEquals("6997788888,ELIGIBLE,FAS,CADRA_11Dec16,FY2017,7000813806," +
            "\"CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil\",01/11/2017," +
            "\"2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA\"," +
            "Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle,1008902112377654XX," +
            "180382914,1000009997,IEEE - Inst of Electrical and Electronics Engrs," +
            "IEEE,09/10/2013,2502232,2500.00,35000.0000000000,35000.00,Doc Del,2013,2017," +
            "\"Íñigo López de Mendoza, marqués de Santillana\"", bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    @Test
    public void testWriteUsagesEmptyCsvReport() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        EXECUTOR_SERVICE.execute(() -> usageRepository.writeUsagesCsvReport(new UsageFilter(), outputStream));
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        assertEquals("Detail ID,Detail Status,Product Family,Usage Batch Name,Fiscal Year,RRO Account #,RRO Name," +
            "Payment Date,Title,Article,Standard Number,Wr Wrk Inst,RH Account #,RH Name,Publisher,Pub Date," +
            "Number of Copies,Reported value,Amt in USD,Gross Amt in USD,Market,Market Period From," +
            "Market Period To,Author", bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    @Test
    public void testWriteScenarioUsagesCsvReport() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        EXECUTOR_SERVICE.execute(() -> usageRepository.writeScenarioUsagesCsvReport(SCENARIO_ID, outputStream));
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        assertEquals("Detail ID,Usage Batch Name,Fiscal Year,RRO Account #,RRO Name,Payment Date,Title,Article," +
            "Standard Number,Wr Wrk Inst,RH Account #,RH Name,Payee Account #,Payee Name,Publisher,Pub Date," +
            "Number of Copies,Reported value,Gross Amt in USD,Service Fee Amount,Net Amt in USD,Service Fee %," +
            "Market,Market Period From,Market Period To,Author", bufferedReader.readLine());
        assertEquals("6997788886,JAACC_11Dec16,FY2016,2000017010," +
                "\"JAC, Japan Academic Association for Copyright Clearance, Inc.\"," +
                "09/10/2015,100 ROAD MOVIES,DIN EN 779:2012,1008902112377654XX,243904752,1000002859," +
                "John Wiley & Sons - Books,1000002859,John Wiley & Sons - Books,IEEE,09/10/2013,250232,9900.00," +
                "16437.4000000000,5260.0000000000,11177.4000000000,32.0,Doc Del,2013,2017,Philippe de Mézières",
            bufferedReader.readLine());
        assertEquals("6213788886,JAACC_11Dec16,FY2016,2000017010," +
                "\"JAC, Japan Academic Association for Copyright Clearance, Inc.\"," +
                "09/10/2015,100 ROAD MOVIES,DIN EN 779:2012,1008902112317622XX,243904752,1000002859," +
                "John Wiley & Sons - Books,1000002859,John Wiley & Sons - Books,IEEE,09/10/2013,100,9900.00," +
                "16437.4000000000,5260.0000000000,11177.4000000000,32.0,Doc Del,2013,2017,Philippe de Mézières",
            bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    @Test
    public void testDeleteUsages() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Sets.newHashSet(USAGE_BATCH_ID_1));
        Sort sort = new Sort(DETAIL_ID_KEY, Direction.ASC);
        assertEquals(1, usageRepository.findByFilter(filter, null, sort).size());
        usageRepository.deleteUsages(USAGE_BATCH_ID_1);
        assertEquals(0, usageRepository.findByFilter(filter, null, sort).size());
    }

    @Test
    public void testFindByScenarioId() {
        List<Usage> usages = usageRepository.findByScenarioId(SCENARIO_ID);
        assertEquals(2, usages.size());
        usages.forEach(
            usage -> verifyUsage(usage, UsageStatusEnum.LOCKED, SCENARIO_ID, StoredEntity.DEFAULT_USER, 1000002859L));
    }

    @Test
    public void testDeleteByScenarioId() {
        assertEquals(2, usageRepository.findByScenarioId(SCENARIO_ID).size());
        usageRepository.deleteByScenarioId(SCENARIO_ID);
        assertTrue(usageRepository.findByScenarioId(SCENARIO_ID).isEmpty());
    }

    @Test
    public void testFindWithAmountsAndRightsholders() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                Collections.singleton(PRODUCT_FAMILY), UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testVerifyFindWithAmountsAndRightsholders() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                Collections.singleton(PRODUCT_FAMILY), UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        List<Usage> usages = usageRepository.findWithAmountsAndRightsholders(usageFilter);
        assertEquals(1, usages.size());
        Usage usage = usages.get(0);
        assertEquals(USAGE_ID_1, usage.getId());
        assertEquals(new BigDecimal("35000.0000000000"), usage.getGrossAmount());
        assertEquals(ZERO_AMOUNT, usage.getNetAmount());
        assertEquals(new BigDecimal("2500.00"), usage.getReportedValue());
        assertNull(usage.getServiceFee());
        assertNotNull(usage.getCreateDate());
        assertNotNull(usage.getUpdateDate());
        assertEquals("SYSTEM", usage.getCreateUser());
        assertEquals("SYSTEM", usage.getUpdateUser());
        assertEquals(1, usage.getVersion());
        assertNotNull(usage.getRightsholder());
        assertEquals(1000009997L, usage.getRightsholder().getAccountNumber(), 0);
        assertEquals("9905f006-a3e1-4061-b3d4-e7ece191103f", usage.getRightsholder().getId());
        assertEquals("IEEE - Inst of Electrical and Electronics Engrs", usage.getRightsholder().getName());
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByUsageBatchFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1),
            Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByRhAccountNumberFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.emptySet(),
            Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByProductFamiliesFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            Collections.singleton(PRODUCT_FAMILY), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 4, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3, USAGE_ID_10);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByPaymentDateFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            Collections.emptySet(), UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByFiscalYearFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, FISCAL_YEAR);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
    }

    @Test
    public void testAddToScenario() {
        Usage usage = usageRepository.findByDetailId(DETAIL_ID_1);
        verifyUsage(usage, UsageStatusEnum.ELIGIBLE, null, StoredEntity.DEFAULT_USER, null);
        usage.getPayee().setAccountNumber(2000017004L);
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setScenarioId(SCENARIO_ID);
        usage.setUpdateUser(USER_NAME);
        BigDecimal serviceFeeAmount = new BigDecimal("2205.536").setScale(10);
        usage.setServiceFeeAmount(serviceFeeAmount);
        BigDecimal netAmount = new BigDecimal("4686.764").setScale(10);
        usage.setNetAmount(netAmount);
        usage.setServiceFee(SERVICE_FEE);
        usageRepository.addToScenario(Collections.singletonList(usage));
        Usage updatedUsage = usageRepository.findByDetailId(DETAIL_ID_1);
        verifyUsage(updatedUsage, UsageStatusEnum.LOCKED, SCENARIO_ID, USER_NAME, 2000017004L);
        assertEquals(SERVICE_FEE, usage.getServiceFee());
        assertEquals(serviceFeeAmount, usage.getServiceFeeAmount());
        assertEquals(netAmount, usage.getNetAmount());
    }

    @Test
    public void testDeleteFromScenario() {
        verifyUsage(usageRepository.findByDetailId(DETAIL_ID_2), UsageStatusEnum.LOCKED, SCENARIO_ID,
            StoredEntity.DEFAULT_USER, 1000002859L);
        usageRepository.deleteFromScenario(SCENARIO_ID, USER_NAME);
        verifyUsageExcludedFromScenario(usageRepository.findByDetailId(DETAIL_ID_2));
    }

    @Test
    public void testFindDuplicateDetailIds() {
        assertTrue(CollectionUtils.containsAny(Sets.newHashSet(DETAIL_ID_1, DETAIL_ID_2),
            usageRepository.findDuplicateDetailIds(Lists.newArrayList(DETAIL_ID_1, DETAIL_ID_2, -1L))));
    }

    @Test
    public void testDeleteFromScenarioByAccountNumbers() {
        assertEquals(SCENARIO_ID, usageRepository.findByDetailId(6997788886L).getScenarioId());
        assertEquals(SCENARIO_ID, usageRepository.findByDetailId(6213788886L).getScenarioId());
        Usage usage = buildUsage(RupPersistUtils.generateUuid(), USAGE_BATCH_ID_1);
        usageRepository.insert(usage);
        usageRepository.addToScenario(Collections.singletonList(usage));
        usageRepository.deleteFromScenario(Lists.newArrayList(USAGE_ID_8, USAGE_ID_7), USER_NAME);
        verifyUsageExcludedFromScenario(usageRepository.findByDetailId(6997788886L));
        verifyUsageExcludedFromScenario(usageRepository.findByDetailId(6213788886L));
        assertEquals(SCENARIO_ID, usageRepository.findByDetailId(DETAIL_ID).getScenarioId());
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumbers() {
        Usage usage = buildUsage(RupPersistUtils.generateUuid(), USAGE_BATCH_ID_1);
        usageRepository.insert(usage);
        String scenarioId = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";
        usage.setScenarioId(scenarioId);
        usageRepository.addToScenario(Collections.singletonList(usage));
        List<String> usagesIds = usageRepository.findIdsByScenarioIdRroAccountNumberRhAccountNumbers(
            scenarioId, 2000017010L, Lists.newArrayList(1000002859L, 7000813806L));
        assertEquals(2, usagesIds.size());
        assertTrue(usagesIds.containsAll(Lists.newArrayList(USAGE_ID_8, USAGE_ID_7)));
    }

    @Test
    public void testFindForAuditByStatus() {
        AuditFilter filter = new AuditFilter();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.WORK_FOUND));
        assertEquals(1, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, 1, USAGE_ID_4);
    }

    @Test
    public void testFindForAuditByBatch() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton(BATCH_ID));
        assertEquals(2, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, 2, USAGE_ID_5, USAGE_ID_4);
    }

    @Test
    public void testFindForAuditByRightsholders() {
        AuditFilter filter = new AuditFilter();
        filter.setRhAccountNumbers(Collections.singleton(1000002475L));
        assertEquals(1, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, 1, USAGE_ID_4);
    }

    @Test
    public void testFindForAuditByProductFamilies() {
        AuditFilter filter = new AuditFilter();
        filter.setProductFamilies(Collections.singleton(PRODUCT_FAMILY));
        assertEquals(11, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 20), null);
        verifyUsageDtos(usages, 11, USAGE_ID_11, USAGE_ID_12, USAGE_ID_6, USAGE_ID_5, USAGE_ID_9, USAGE_ID_7,
            USAGE_ID_3, USAGE_ID_2, USAGE_ID_8, USAGE_ID_1, USAGE_ID_4);
    }

    @Test
    public void testFindForAuditBySearch() {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue("5423214587");
        assertEquals(1, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, 1, USAGE_ID_5);
        filter.setSearchValue("Nitrates");
        assertEquals(1, usageRepository.findCountForAudit(filter));
        usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, 1, USAGE_ID_4);
        filter.setSearchValue("103658926");
        assertEquals(1, usageRepository.findCountForAudit(filter));
        usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, 1, USAGE_ID_4);
    }

    @Test
    public void testFindForAuditPageable() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton(BATCH_ID));
        assertEquals(2, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 1), null);
        verifyUsageDtos(usages, 1, USAGE_ID_5);
        usages = usageRepository.findForAudit(filter, new Pageable(1, 1), null);
        verifyUsageDtos(usages, 1, USAGE_ID_4);
    }

    @Test
    public void testWriteAuditCsvReport() throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(
            Sets.newHashSet("7802802a-1f96-4d7a-8a27-b0bfd43936b0", "56282dbc-2468-48d4-b926-94d3458a666a"));
        filter.setRhAccountNumbers(Collections.singleton(1000002859L));
        EXECUTOR_SERVICE.execute(() -> usageRepository.writeAuditCsvReport(filter, outputStream));
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        assertEquals("Detail ID,Detail Status,Usage Batch Name,Payment Date,RH Account #,RH Name,Wr Wrk Inst,Title," +
                "Standard Number,Amt in USD,Service Fee %,Scenario Name,Check #,Check Date,Event ID,Dist. Name",
            bufferedReader.readLine());
        assertEquals("5423214888,PAID,Paid batch,02/12/2021,1000002859,John Wiley & Sons - Books,243904752," +
            "100 ROAD MOVIES,1008902112317555XX,1000.0000000000,16.0,Paid Scenario,578945,03/15/2017,53256," +
            "FDA March 17", bufferedReader.readLine());
        assertEquals("6997788885,ELIGIBLE,AccessCopyright_11Dec16,08/16/2018,1000002859," +
                "John Wiley & Sons - Books,244614835,15th International Conference on Environmental Degradation of " +
                "Materials in Nuclear Power Systems Water Reactors,1008902002377655XX,35000.0000000000,0.0,,,,,",
            bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    @Test
    public void testWriteAuditEmptyCsvReport() throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        EXECUTOR_SERVICE.execute(() -> usageRepository.writeAuditCsvReport(new AuditFilter(), outputStream));
        BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        assertEquals("Detail ID,Detail Status,Usage Batch Name,Payment Date,RH Account #,RH Name,Wr Wrk Inst,Title," +
                "Standard Number,Amt in USD,Service Fee %,Scenario Name,Check #,Check Date,Event ID,Dist. Name",
            bufferedReader.readLine());
        assertNull(bufferedReader.readLine());
    }

    @Test
    public void testFindForAuditWithSort() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton(BATCH_ID));
        verifyUsageDtos(findForAuditWithSort(filter, "detailId", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "detailId", false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, "status", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "status", false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, "rhAccountNumber", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "rhAccountNumber", false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, "rhName", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "rhName", false), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "wrWrkInst", false), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "wrWrkInst", true), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, "workTitle", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "workTitle", false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, "standardNumber", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "standardNumber", false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, "grossAmount", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "grossAmount", false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, "serviceFee", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "serviceFee", false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, "scenarioName", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "scenarioName", false), 2, USAGE_ID_4, USAGE_ID_5);
        filter.setBatchesIds(Sets.newHashSet(BATCH_ID, "74b736f2-81ce-41fa-bd8e-574299232458"));
        verifyUsageDtos(findForAuditWithSort(filter, "batchName", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "batchName", false), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "paymentDate", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "paymentDate", false), 2, USAGE_ID_5, USAGE_ID_4);
    }

    @Test
    public void testFindByStatuses() {
        List<Usage> usages =
            usageRepository.findByStatuses(UsageStatusEnum.WORK_FOUND, UsageStatusEnum.SENT_FOR_RA);
        assertEquals(2, CollectionUtils.size(usages));
        Usage usageSentForRa = usages.get(0);
        assertEquals(USAGE_ID_6, usageSentForRa.getId());
        assertEquals(UsageStatusEnum.SENT_FOR_RA, usageSentForRa.getStatus());
        Usage usageWorkFound = usages.get(1);
        assertEquals("d9ca07b5-8282-4a81-9b9d-e4480f529d34", usageWorkFound.getId());
        assertEquals(UsageStatusEnum.WORK_FOUND, usageWorkFound.getStatus());
    }

    @Test
    public void testUpdateStatusWithUsageId() {
        Usage usage = usageRepository.findByDetailId(8457965214L);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(USER_NAME, usage.getUpdateUser());
        usageRepository.updateStatus(usage.getId(), UsageStatusEnum.RH_NOT_FOUND);
        usage = usageRepository.findByDetailId(8457965214L);
        assertEquals(UsageStatusEnum.RH_NOT_FOUND, usage.getStatus());
        assertEquals(StoredEntity.DEFAULT_USER, usage.getUpdateUser());
    }

    @Test
    public void testUpdateStatusWithUsageIds() {
        Usage usage1 = usageRepository.findByDetailId(8457965214L);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage1.getStatus());
        assertEquals(USER_NAME, usage1.getUpdateUser());
        Usage usage2 = usageRepository.findByDetailId(3539748198L);
        assertEquals(UsageStatusEnum.SENT_FOR_RA, usage2.getStatus());
        assertEquals(USER_NAME, usage2.getUpdateUser());
        usageRepository.updateStatus(ImmutableSet.of(usage1.getId(), usage2.getId()), UsageStatusEnum.RH_NOT_FOUND);
        usage1 = usageRepository.findByDetailId(8457965214L);
        assertEquals(UsageStatusEnum.RH_NOT_FOUND, usage1.getStatus());
        assertEquals(StoredEntity.DEFAULT_USER, usage1.getUpdateUser());
        usage2 = usageRepository.findByDetailId(3539748198L);
        assertEquals(UsageStatusEnum.RH_NOT_FOUND, usage2.getStatus());
        assertEquals(StoredEntity.DEFAULT_USER, usage2.getUpdateUser());
    }

    @Test
    public void testUpdateStatusAndRhAccountNumber() {
        Usage usage1 = usageRepository.findByDetailId(8457965214L);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage1.getStatus());
        assertNull(usage1.getRightsholder().getAccountNumber());
        Usage usage2 = usageRepository.findByDetailId(3539748198L);
        assertEquals(UsageStatusEnum.SENT_FOR_RA, usage2.getStatus());
        assertNull(usage2.getRightsholder().getAccountNumber());
        usageRepository.updateStatusAndRhAccountNumber(ImmutableSet.of(usage1.getId(), usage2.getId()),
            UsageStatusEnum.ELIGIBLE, RH_ACCOUNT_NUMBER);
        usage1 = usageRepository.findByDetailId(8457965214L);
        assertEquals(UsageStatusEnum.ELIGIBLE, usage1.getStatus());
        assertEquals(RH_ACCOUNT_NUMBER, usage1.getRightsholder().getAccountNumber());
        usage2 = usageRepository.findByDetailId(3539748198L);
        assertEquals(UsageStatusEnum.ELIGIBLE, usage2.getStatus());
        assertEquals(RH_ACCOUNT_NUMBER, usage2.getRightsholder().getAccountNumber());
    }

    @Test
    public void testUpdateRhPayeeAndAmounts() {
        Usage usage = usageRepository.findByDetailId(6997788886L);
        assertEquals(1000002859L, usage.getRightsholder().getAccountNumber(), 0);
        assertEquals(new BigDecimal("16437.4000000000"), usage.getGrossAmount());
        assertEquals(new BigDecimal("11177.4000000000"), usage.getNetAmount());
        assertEquals(new BigDecimal("5260.0000000000"), usage.getServiceFeeAmount());
        assertEquals(new BigDecimal("0.32000"), usage.getServiceFee());
        usage.getRightsholder().setAccountNumber(1000000001L);
        usage.setServiceFee(new BigDecimal("0.16000"));
        usage.setNetAmount(new BigDecimal("13807.4000000000"));
        usage.setServiceFeeAmount(new BigDecimal("2630.0000000000"));
        usageRepository.updateRhPayeeAndAmounts(Collections.singletonList(usage));
        usage = usageRepository.findByDetailId(6997788886L);
        assertEquals(1000000001L, usage.getRightsholder().getAccountNumber(), 0);
        assertEquals(new BigDecimal("13807.4000000000"), usage.getNetAmount());
        assertEquals(new BigDecimal("2630.0000000000"), usage.getServiceFeeAmount());
        assertEquals(new BigDecimal("0.16000"), usage.getServiceFee());
        usage.getRightsholder().setAccountNumber(1000000001L);
    }

    @Test
    public void testFindProductFamiliesForFilter() {
        List<String> productFamilies = usageRepository.findProductFamiliesForFilter();
        assertEquals(2, CollectionUtils.size(productFamilies));
        assertEquals(PRODUCT_FAMILY, productFamilies.get(0));
        assertEquals("NTS", productFamilies.get(1));
    }

    @Test
    public void testFindProductFamiliesForAuditFilter() {
        List<String> productFamilies = usageRepository.findProductFamiliesForAuditFilter();
        assertEquals(2, CollectionUtils.size(productFamilies));
        assertEquals(PRODUCT_FAMILY, productFamilies.get(0));
        assertEquals("NTS", productFamilies.get(1));
    }

    @Test
    public void testUpdateStatusAndWrWrkInst() {
        List<Usage> usages = usageRepository.findUsagesWithBlankWrWrkInst();
        assertEquals(2, usages.size());
        usages.forEach(usage -> {
            usage.setWrWrkInst(WR_WRK_INST);
            usage.setStatus(UsageStatusEnum.WORK_FOUND);
        });
        usageRepository.updateStatusAndWrWrkInst(usages);
        usages.forEach(usage -> {
            Usage updatedUsage = usageRepository.findByDetailId(usage.getDetailId());
            assertEquals(WR_WRK_INST, updatedUsage.getWrWrkInst());
            assertEquals(UsageStatusEnum.WORK_FOUND, updatedUsage.getStatus());
            assertEquals(StoredEntity.DEFAULT_USER, updatedUsage.getUpdateUser());
        });
    }

    private List<UsageDto> findForAuditWithSort(AuditFilter filter, String property, boolean order) {
        return usageRepository.findForAudit(filter, new Pageable(0, 10), Sort.create(new Object[]{property}, order));
    }

    private void verifySearch(String searchValue, int expectedSize) {
        assertEquals(expectedSize, usageRepository.findByScenarioIdAndRhAccountNumber(1000002859L, SCENARIO_ID,
            searchValue, null, null).size());
        assertEquals(expectedSize, usageRepository.findCountByScenarioIdAndRhAccountNumber(1000002859L,
            SCENARIO_ID, searchValue));
    }

    private void verifyUsage(Usage usage, UsageStatusEnum status, String scenarioId, String defaultUser,
                             Long payeeAccountNumber) {
        assertNotNull(usage);
        assertEquals(status, usage.getStatus());
        assertEquals(scenarioId, usage.getScenarioId());
        assertEquals(defaultUser, usage.getUpdateUser());
        assertEquals(payeeAccountNumber, usage.getPayee().getAccountNumber());
        assertEquals(PRODUCT_FAMILY, usage.getProductFamily());
    }

    private void verifyUsageExcludedFromScenario(Usage usage) {
        assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        assertNull(usage.getScenarioId());
        assertNull(usage.getPayee().getAccountNumber());
        assertNull(usage.getServiceFee());
        BigDecimal expectedDefaultAmount = new BigDecimal("0.0000000000");
        assertEquals(expectedDefaultAmount, usage.getServiceFeeAmount());
        assertEquals(expectedDefaultAmount, usage.getNetAmount());
        assertFalse(usage.isRhParticipating());
        assertEquals(USER_NAME, usage.getUpdateUser());
        assertEquals(PRODUCT_FAMILY, usage.getProductFamily());
    }

    private UsageFilter buildUsageFilter(Set<Long> accountNumbers, Set<String> usageBatchIds,
                                         Set<String> productFamilies, UsageStatusEnum status,
                                         LocalDate paymentDate, Integer fiscalYear) {
        UsageFilter usageFilter = buildFilterWithStatuses(status);
        usageFilter.setRhAccountNumbers(accountNumbers);
        usageFilter.setUsageStatus(status);
        usageFilter.setUsageBatchesIds(usageBatchIds);
        usageFilter.setPaymentDate(paymentDate);
        usageFilter.setFiscalYear(fiscalYear);
        usageFilter.setProductFamilies(productFamilies);
        return usageFilter;
    }

    private UsageFilter buildFilterWithStatuses(UsageStatusEnum status) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(status);
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
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.getRightsholder().setName(RH_ACCOUNT_NAME);
        usage.getPayee().setAccountNumber(2000017004L);
        usage.setStatus(UsageStatusEnum.ELIGIBLE);
        usage.setProductFamily(PRODUCT_FAMILY);
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
        rightsholderTotalsHolder.getRightsholder().setAccountNumber(rhAccountNumber);
        rightsholderTotalsHolder.getRightsholder().setName(rhName);
        rightsholderTotalsHolder.getPayee().setAccountNumber(rhAccountNumber);
        rightsholderTotalsHolder.getPayee().setName(rhName);
        rightsholderTotalsHolder.setGrossTotal(BigDecimal.valueOf(grossTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setServiceFeeTotal(
            BigDecimal.valueOf(serviceFeeTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setNetTotal(BigDecimal.valueOf(netTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setServiceFee(SERVICE_FEE);
        return rightsholderTotalsHolder;
    }

    private void populateScenario() {
        List<Usage> usages = Lists.newArrayListWithExpectedSize(3);
        Usage usage = usageRepository.findByDetailId(6997788888L);
        usage.getPayee().setAccountNumber(1000009997L);
        usage.setScenarioId(SCENARIO_ID);
        usage.setServiceFee(SERVICE_FEE);
        calculateAmounts(usage);
        usages.add(usage);
        usage = usageRepository.findByDetailId(6997788885L);
        usage.getPayee().setAccountNumber(1000002859L);
        usage.setScenarioId(SCENARIO_ID);
        usage.setServiceFee(SERVICE_FEE);
        calculateAmounts(usage);
        usages.add(usage);
        usage = usageRepository.findByDetailId(DETAIL_ID_1);
        usage.getPayee().setAccountNumber(1000005413L);
        usage.setScenarioId(SCENARIO_ID);
        usage.setServiceFee(SERVICE_FEE);
        calculateAmounts(usage);
        usages.add(usage);
        usageRepository.addToScenario(Lists.newArrayList(usages));
    }

    private void calculateAmounts(Usage usage) {
        usage.setServiceFeeAmount(
            CalculationUtils.calculateServiceFeeAmount(usage.getGrossAmount(), usage.getServiceFee()));
        usage.setNetAmount(CalculationUtils.calculateNetAmount(usage.getGrossAmount(), usage.getServiceFeeAmount()));
    }

    private void verifyUsageDtos(List<UsageDto> usageDtos, int count, String... usageIds) {
        assertNotNull(usageDtos);
        assertEquals(count, usageDtos.size());
        IntStream.range(0, count - 1).forEach(i -> assertEquals(usageIds[i], usageDtos.get(i).getId()));
    }

    private void verifyUsages(List<Usage> usages, int count, String... usageIds) {
        assertNotNull(usages);
        assertEquals(count, usages.size());
        IntStream.range(0, count).forEach(i -> {
            assertEquals(usageIds[i], usages.get(i).getId());
            assertEquals(UsageStatusEnum.ELIGIBLE, usages.get(i).getStatus());
        });
    }
}
