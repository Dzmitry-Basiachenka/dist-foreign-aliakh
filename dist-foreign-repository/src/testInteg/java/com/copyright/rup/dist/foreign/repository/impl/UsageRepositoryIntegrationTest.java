package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
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
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2018, 12, 11);
    private static final Integer FISCAL_YEAR = 2019;
    private static final String RH_ACCOUNT_NAME =
        "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil";
    private static final String RH_ACCOUNT_NAME_1 = "IEEE - Inst of Electrical and Electronics Engrs";
    private static final String RH_ACCOUNT_NAME_2 = "John Wiley & Sons - Books";
    private static final String RH_ACCOUNT_NAME_3 = "Kluwer Academic Publishers - Dordrecht";
    private static final Long WR_WRK_INST = 123456783L;
    private static final String WORK_TITLE = "Wissenschaft & Forschung Japan";
    private static final String PRODUCT_FAMILY_FAS = "FAS";
    private static final String DETAIL_ID_KEY = "detailId";
    private static final String WORK_TITLE_KEY = "workTitle";
    private static final String BATCH_NAME_KEY = "batchName";
    private static final String PAYMENT_DATE_KEY = "paymentDate";
    private static final String STANDARD_NUMBER_KEY = "standardNumber";
    private static final String WR_WRK_INST_KEY = "wrWrkInst";
    private static final String RH_ACCOUNT_NUMBER_KEY = "rhAccountNumber";
    private static final String RH_NAME_KEY = "rhName";
    private static final String GROSS_AMOUNT_KEY = "grossAmount";
    private static final String SERVICE_FEE_KEY = "serviceFee";
    private static final String STATUS_KEY = "status";
    private static final String USAGE_ID_1 = "3ab5e80b-89c0-4d78-9675-54c7ab284450";
    private static final String USAGE_ID_2 = "8a06905f-37ae-4e1f-8550-245277f8165c";
    private static final String USAGE_ID_3 = "5c5f8c1c-1418-4cfd-8685-9212f4c421d1";
    private static final String USAGE_ID_4 = "d9ca07b5-8282-4a81-9b9d-e4480f529d34";
    private static final String USAGE_ID_5 = "a71a0544-128e-41c0-b6b0-cfbbea6d2182";
    private static final String USAGE_ID_6 = "62e0ddd7-a37f-4810-8ada-abab805cb48d";
    private static final String USAGE_ID_7 = "cf38d390-11bb-4af7-9685-e034c9c32fb6";
    private static final String USAGE_ID_8 = "b1f0b236-3ae9-4a60-9fab-61db84199dss";
    private static final String USAGE_ID_9 = "5c5f8c1c-1418-4cfd-8685-9212f4c421d1";
    private static final String USAGE_ID_11 = "7db6455e-5249-44db-801a-307f1c239310";
    private static final String USAGE_ID_12 = "593c49c3-eb5b-477b-8556-f7a4725df2b3";
    private static final String USAGE_ID_13 = "66a7c2c0-3b09-48ad-9aa5-a6d0822226c7";
    private static final String USAGE_ID_14 = "0c099fc0-e6f5-43c0-b2d5-ad971f974c10";
    private static final String USAGE_ID_15 = "0d85f51d-212b-4181-9972-3154cad74bd0";
    private static final String USAGE_ID_16 = "1cb766c6-7c49-489a-bd8f-9b8b052f5785";
    private static final String USAGE_ID_17 = "b53bb4f3-9eee-4732-8e3d-0c88722081d8";
    private static final String USAGE_ID_18 = "721ca627-09bc-4204-99f4-6acae415fa5d";
    private static final String USAGE_ID_19 = "9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a";
    private static final String USAGE_ID_20 = "dcc794ba-42aa-481d-937b-8f431929a611";
    private static final String USAGE_ID_21 = "47d48889-76b5-4957-aca0-2a7850a09f92";
    private static final String USAGE_ID_22 = "c5ea47b0-b269-4791-9aa7-76308fe835e6";
    private static final String USAGE_ID_23 = "3b6892a9-49b2-41a2-aa3a-8705ea6640cc";
    private static final String USAGE_ID_24 = "3c31db4f-4065-4fe1-84c2-b48a0f3bc079";
    private static final String POST_DISTRIBUTION_USAGE_ID = "cce295c6-23cf-47b4-b00c-2e0e50cce169";
    private static final String SCENARIO_ID = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";
    private static final String USER_NAME = "user@copyright.com";
    private static final BigDecimal SERVICE_FEE = new BigDecimal("0.32000");
    private static final String BATCH_ID = "e0af666b-cbb7-4054-9906-12daa1fbd76e";
    private static final String PERCENT = "%";
    private static final String UNDERSCORE = "_";

    @Autowired
    private UsageRepository usageRepository;

    @Test
    public void testInsert() {
        String usageId = RupPersistUtils.generateUuid();
        usageRepository.insert(buildUsage(usageId, USAGE_BATCH_ID_1));
        Usage usage = usageRepository.findById(usageId);
        assertNotNull(usage);
        assertEquals(usageId, usage.getId());
        assertEquals(SCENARIO_ID, usage.getScenarioId());
        assertEquals(WR_WRK_INST, usage.getWrWrkInst());
        assertEquals("Work Title", usage.getWorkTitle());
        assertEquals("System Title", usage.getSystemTitle());
        assertEquals(RH_ACCOUNT_NUMBER, usage.getRightsholder().getAccountNumber());
        assertEquals(RH_ACCOUNT_NAME, usage.getRightsholder().getName());
        assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        assertEquals(PRODUCT_FAMILY_FAS, usage.getProductFamily());
        assertEquals("Article", usage.getArticle());
        assertEquals("StandardNumber", usage.getStandardNumber());
        assertEquals("Publisher", usage.getPublisher());
        assertEquals(LocalDate.of(2016, 11, 3), usage.getPublicationDate());
        assertEquals("Market", usage.getMarket());
        assertEquals(2015, usage.getMarketPeriodFrom(), 0);
        assertEquals(2017, usage.getMarketPeriodTo(), 0);
        assertEquals("Author", usage.getAuthor());
        assertEquals(155, usage.getNumberOfCopies(), 0);
        assertEquals(new BigDecimal("11.25"), usage.getReportedValue());
        assertEquals(new BigDecimal("54.4400000000"), usage.getGrossAmount());
        assertFalse(usage.isRhParticipating());
    }

    @Test
    public void testFindCountByFilter() {
        assertEquals(1, usageRepository.findCountByFilter(
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                Collections.singleton(PRODUCT_FAMILY_FAS), UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR)));
    }

    @Test
    public void testFindByFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                Collections.singleton(PRODUCT_FAMILY_FAS), UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 1,
            USAGE_ID_1);
    }

    @Test
    public void testFindUsagesByFilter() {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setProductFamilies(ImmutableSet.of("NTS"));
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        usageFilter.setUsageBatchesIds(ImmutableSet.of("928e2693-2646-4a04-85f1-4ca9cd78551a"));
        List<Usage> actualUsages = usageRepository.findUsagesByFilter(usageFilter);
        assertTrue(CollectionUtils.isNotEmpty(actualUsages));
        assertEquals(2, CollectionUtils.size(actualUsages));
        actualUsages.sort(Comparator.comparing(Usage::getId));
        Usage actualUsage1 = actualUsages.get(0);
        assertEquals("3290f7ae-23f7-4286-baf6-a74a9d4ef2d4", actualUsage1.getId());
        assertEquals(1000009523L, actualUsage1.getRightsholder().getAccountNumber(), 0);
        Usage actualUsage2 = actualUsages.get(1);
        assertEquals("97f76fe1-9254-4d2e-9f4e-fa6904140371", actualUsage2.getId());
        assertEquals(1000009524L, actualUsage2.getRightsholder().getAccountNumber(), 0);
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
            Collections.singleton(PRODUCT_FAMILY_FAS), null, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)),
            16, USAGE_ID_14, USAGE_ID_1, USAGE_ID_23, USAGE_ID_21, USAGE_ID_12, USAGE_ID_9, USAGE_ID_6, USAGE_ID_13,
            USAGE_ID_18, USAGE_ID_11, USAGE_ID_2, USAGE_ID_19, USAGE_ID_17, USAGE_ID_22, USAGE_ID_4, USAGE_ID_20);
    }

    @Test
    public void testFindByStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), 3,
            USAGE_ID_1, USAGE_ID_3, USAGE_ID_2);
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
        verifyUsageDtos(usageRepository.findByFilter(usageFilter, null, new Sort("article", Sort.Direction.ASC)), 3,
            USAGE_ID_3, USAGE_ID_1, USAGE_ID_2);
    }

    @Test
    public void testFindInvalidRightsholdersByFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1),
                Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, null);
        assertTrue(CollectionUtils.isEmpty(usageRepository.findInvalidRightsholdersByFilter(usageFilter)));
        Usage usage = buildUsage(RupPersistUtils.generateUuid(), USAGE_BATCH_ID_1);
        usage.getRightsholder().setAccountNumber(1000000003L);
        usageRepository.insert(usage);
        List<Long> accountNumbers = usageRepository.findInvalidRightsholdersByFilter(usageFilter);
        assertEquals(1, accountNumbers.size());
        assertEquals(1000000003L, accountNumbers.get(0), 0);
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
        assertEquals(0,
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, PERCENT, null, null).size());
        assertEquals(0,
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, UNDERSCORE, null, null).size());
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
        verifyFindByScenarioIdAndRhSearch("Access Copyright, The Canadian Copyright Agency", 1);
        verifyFindByScenarioIdAndRhSearch("Academic", 2);
        verifyFindByScenarioIdAndRhSearch("aCaDemiC", 2);
        verifyFindByScenarioIdAndRhSearch("Aca demic", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByRorAccountNumber() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch("2000017010", 2);
        verifyFindByScenarioIdAndRhSearch("0001700", 1);
        verifyFindByScenarioIdAndRhSearch("70014 40663", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchDetailId() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch("b1f0b236-3ae9-4a60-9fab-61db84199dss", 1);
        verifyFindByScenarioIdAndRhSearch("4a60", 1);
        verifyFindByScenarioIdAndRhSearch("4a", 2);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByWrWrkInst() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch("243904752", 2);
        verifyFindByScenarioIdAndRhSearch("244614", 1);
        verifyFindByScenarioIdAndRhSearch("24461 4835", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByStandardNumber() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch("1008902002377655XX", 1);
        verifyFindByScenarioIdAndRhSearch("1008902002377655xx", 1);
        verifyFindByScenarioIdAndRhSearch("10089", 3);
        verifyFindByScenarioIdAndRhSearch("100890 2002377655XX", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchBySqlLikePattern() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch(PERCENT, 0);
        verifyFindByScenarioIdAndRhSearch(UNDERSCORE, 0);
    }

    @Test
    public void testFindRightsholderTotalsHolderCountByScenarioId() {
        populateScenario();
        assertEquals(3, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY));
        assertEquals(1, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, "IEEE"));
        assertEquals(0, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, PERCENT));
        assertEquals(0, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, UNDERSCORE));
    }

    @Test
    public void testIsScenarioEmpty() {
        populateScenario();
        assertFalse(usageRepository.isScenarioEmpty("b1f0b236-3ae9-4a60-9fab-61db84199d6f"));
        assertTrue(usageRepository.isScenarioEmpty("e27551ed-3f69-4e08-9e4f-8ac03f67595f"));
        assertTrue(usageRepository.isScenarioEmpty("979c981c-6a3a-46f3-bbd7-83d322ce9136"));
        assertTrue(usageRepository.isScenarioEmpty("091c08cf-8a93-4a64-87b5-4bdd44f97e79"));
    }

    @Test
    public void testDeleteByBatchId() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Sets.newHashSet(USAGE_BATCH_ID_1));
        Sort sort = new Sort(DETAIL_ID_KEY, Direction.ASC);
        assertEquals(1, usageRepository.findByFilter(filter, null, sort).size());
        usageRepository.deleteByBatchId(USAGE_BATCH_ID_1);
        assertEquals(0, usageRepository.findByFilter(filter, null, sort).size());
    }

    @Test
    public void testDeleteById() {
        assertNotNull(usageRepository.findById("3cf274c5-8eac-4d4a-96be-5921ae026840"));
        assertNotNull(usageRepository.findById("f5eb98ce-ab59-44c8-9a50-1afea2b5ae15"));
        usageRepository.deleteById("3cf274c5-8eac-4d4a-96be-5921ae026840");
        assertNull(usageRepository.findById("3cf274c5-8eac-4d4a-96be-5921ae026840"));
        assertNotNull(usageRepository.findById("f5eb98ce-ab59-44c8-9a50-1afea2b5ae15"));
    }

    @Test
    public void testFindByScenarioId() {
        List<Usage> usages = usageRepository.findByScenarioId(SCENARIO_ID);
        assertEquals(2, usages.size());
        usages.forEach(
            usage -> verifyUsage(usage, UsageStatusEnum.LOCKED, SCENARIO_ID, StoredEntity.DEFAULT_USER, 1000002859L));
    }

    @Test
    public void testFindForReconcile() {
        List<Usage> usages = usageRepository.findForReconcile(SCENARIO_ID);
        assertEquals(2, usages.size());
        usages.forEach(usage -> {
            assertEquals(243904752L, usage.getWrWrkInst(), 0);
            assertEquals(1000002859L, usage.getRightsholder().getAccountNumber(), 0);
            assertEquals("John Wiley & Sons - Books", usage.getRightsholder().getName());
            assertEquals("100 ROAD MOVIES", usage.getWorkTitle());
            assertEquals(PRODUCT_FAMILY_FAS, usage.getProductFamily());
            assertNotNull(usage.getGrossAmount());
        });
    }

    @Test
    public void testFindRightsholdersInformation() {
        Map<Long, Usage> rhInfo = usageRepository.findRightsholdersInformation(SCENARIO_ID);
        assertEquals(1, rhInfo.size());
        Entry<Long, Usage> entry = rhInfo.entrySet().iterator().next();
        assertEquals(1000002859L, entry.getKey(), 0);
        assertEquals(1000002859L, entry.getValue().getPayee().getAccountNumber(), 0);
        assertFalse(entry.getValue().isRhParticipating());
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
                Collections.singleton(PRODUCT_FAMILY_FAS), UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testVerifyFindWithAmountsAndRightsholders() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                Collections.singleton(PRODUCT_FAMILY_FAS), UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        List<Usage> usages = usageRepository.findWithAmountsAndRightsholders(usageFilter);
        assertEquals(1, usages.size());
        Usage usage = usages.get(0);
        assertEquals(USAGE_ID_1, usage.getId());
        assertEquals(new BigDecimal("35000.0000000000"), usage.getGrossAmount());
        assertEquals(BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP), usage.getNetAmount());
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
            Collections.singleton(PRODUCT_FAMILY_FAS), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            Collections.emptySet(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
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
        Usage usage = usageRepository.findById(USAGE_ID_9);
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
        Usage updatedUsage = usageRepository.findById(USAGE_ID_9);
        verifyUsage(updatedUsage, UsageStatusEnum.LOCKED, SCENARIO_ID, USER_NAME, 2000017004L);
        assertEquals(SERVICE_FEE, usage.getServiceFee());
        assertEquals(serviceFeeAmount, usage.getServiceFeeAmount());
        assertEquals(netAmount, usage.getNetAmount());
    }

    @Test
    public void testDeleteFromScenario() {
        verifyUsage(usageRepository.findById(USAGE_ID_8), UsageStatusEnum.LOCKED,
            SCENARIO_ID, StoredEntity.DEFAULT_USER, 1000002859L);
        usageRepository.deleteFromScenario(SCENARIO_ID, USER_NAME);
        verifyUsageExcludedFromScenario(usageRepository.findById(USAGE_ID_8));
    }

    @Test
    public void testFindCountByDetailIdAndStatus() {
        assertEquals(0, usageRepository.findCountByUsageIdAndStatus(USAGE_ID_4, UsageStatusEnum.NEW));
        assertEquals(1, usageRepository.findCountByUsageIdAndStatus(USAGE_ID_1, UsageStatusEnum.ELIGIBLE));
        assertEquals(1, usageRepository.findCountByUsageIdAndStatus(USAGE_ID_5, UsageStatusEnum.SENT_TO_LM));
    }

    @Test
    public void testDeleteFromScenarioByAccountNumbers() {
        assertEquals(SCENARIO_ID, usageRepository.findById(USAGE_ID_8).getScenarioId());
        assertEquals(SCENARIO_ID, usageRepository.findById(USAGE_ID_7).getScenarioId());
        String usageId = RupPersistUtils.generateUuid();
        Usage usage = buildUsage(usageId, USAGE_BATCH_ID_1);
        usageRepository.insert(usage);
        usageRepository.addToScenario(Collections.singletonList(usage));
        usageRepository.deleteFromScenario(Lists.newArrayList(USAGE_ID_8, USAGE_ID_7), USER_NAME);
        verifyUsageExcludedFromScenario(usageRepository.findById(USAGE_ID_8));
        verifyUsageExcludedFromScenario(usageRepository.findById(USAGE_ID_7));
        assertEquals(SCENARIO_ID, usageRepository.findById(usageId).getScenarioId());
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumbers() {
        Usage usage = buildUsage(RupPersistUtils.generateUuid(), USAGE_BATCH_ID_1);
        usageRepository.insert(usage);
        usage.setScenarioId(SCENARIO_ID);
        usageRepository.addToScenario(Collections.singletonList(usage));
        List<String> usagesIds = usageRepository.findIdsByScenarioIdRroAccountNumberRhAccountNumbers(
            SCENARIO_ID, 2000017010L, Lists.newArrayList(1000002859L, 7000813806L));
        assertEquals(2, usagesIds.size());
        assertTrue(usagesIds.containsAll(Lists.newArrayList(USAGE_ID_8, USAGE_ID_7)));
    }

    @Test
    public void testGetTotalAmountByStandardNumberAndBatchId() {
        assertEquals(new BigDecimal("10000.00"), usageRepository.getTotalAmountByStandardNumberAndBatchId("2192-3558",
            "c10a11c6-dae3-43d7-a632-c682542b1209"));
        assertEquals(new BigDecimal("16.40"),
            usageRepository.getTotalAmountByStandardNumberAndBatchId("5475802112214578XX",
                "cb597f4e-f636-447f-8710-0436d8994d10"));
        assertEquals(new BigDecimal("0.00"),
            usageRepository.getTotalAmountByStandardNumberAndBatchId("100 ROAD 5475802112214578XX", "invalid id"));
    }

    @Test
    public void testGetTotalAmountByTitleAndBatchId() {
        assertEquals(new BigDecimal("2000.00"),
            usageRepository.getTotalAmountByTitleAndBatchId("Wissenschaft & Forschung Japan",
                "9776da8d-098d-4f39-99fd-85405c339e9b"));
        assertEquals(new BigDecimal("0.00"),
            usageRepository.getTotalAmountByTitleAndBatchId("100 ROAD MOVIES",
                "cb597f4e-f636-447f-8710-0436d8994d10"));
        assertEquals(new BigDecimal("0.00"),
            usageRepository.getTotalAmountByTitleAndBatchId("100 ROAD MOVIES", "invalid id"));
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
        verifyUsageDtos(usages, 1, USAGE_ID_5);
    }

    @Test
    public void testFindForAuditByProductFamilies() {
        AuditFilter filter = new AuditFilter();
        filter.setProductFamilies(Collections.singleton(PRODUCT_FAMILY_FAS));
        assertEquals(22, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 25), null);
        verifyUsageDtos(usages, 22, USAGE_ID_14, USAGE_ID_15, USAGE_ID_16, USAGE_ID_1, USAGE_ID_23,
            USAGE_ID_21, USAGE_ID_12, USAGE_ID_9, USAGE_ID_6, USAGE_ID_13, USAGE_ID_18, USAGE_ID_11, USAGE_ID_2,
            USAGE_ID_19, USAGE_ID_5, USAGE_ID_8, USAGE_ID_17, USAGE_ID_22, POST_DISTRIBUTION_USAGE_ID, USAGE_ID_7,
            USAGE_ID_4, USAGE_ID_20);
    }

    @Test
    public void testFindForAuditWithSearch() {
        assertFindForAuditSearch(USAGE_ID_5, 1, USAGE_ID_5);
        assertFindForAuditSearch("Nitrates", 1, USAGE_ID_4);
        assertFindForAuditSearch(USAGE_ID_4, 1, USAGE_ID_4);
        assertFindForAuditSearch("Hydronitrous", 1, USAGE_ID_4);
        assertFindForAuditSearch(POST_DISTRIBUTION_USAGE_ID, 1, POST_DISTRIBUTION_USAGE_ID);
        assertFindForAuditSearch(PERCENT, 0);
        assertFindForAuditSearch(UNDERSCORE, 0);
    }

    @Test
    public void testFindForAuditSearchByCccEventId() {
        assertFindForAuditSearchByCccEventId("53256", 2, USAGE_ID_15, POST_DISTRIBUTION_USAGE_ID);
        assertFindForAuditSearchByCccEventId("53257", 1, USAGE_ID_16);
        assertFindForAuditSearchByCccEventId(PERCENT, 0);
        assertFindForAuditSearchByCccEventId(UNDERSCORE, 0);
    }

    @Test
    public void testFindForAuditSearchByDistributionName() {
        assertFindForAuditSearchByDistributionName("FDA July 17", 1, USAGE_ID_16);
        assertFindForAuditSearchByDistributionName("FDA_March_17", 2, USAGE_ID_15, POST_DISTRIBUTION_USAGE_ID);
        assertFindForAuditSearchByDistributionName(PERCENT, 0);
        assertFindForAuditSearchByDistributionName(UNDERSCORE, 2, USAGE_ID_15, POST_DISTRIBUTION_USAGE_ID);
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
    public void testFindByFilterSortingByBatchInfo() {
        UsageFilter filter = buildUsageFilter(Sets.newHashSet(2000017000L, 7000896777L), Collections.emptySet(),
            Collections.emptySet(), null, null, null);
        verifyUsageDtos(findByFilterWithSort(filter, BATCH_NAME_KEY, Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, BATCH_NAME_KEY, Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "fiscalYear", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "fiscalYear", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "rroAccountNumber", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "rroAccountNumber", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "rroName", Direction.ASC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "rroName", Direction.DESC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, PAYMENT_DATE_KEY, Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, PAYMENT_DATE_KEY, Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "batchGrossAmount", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "batchGrossAmount", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
    }

    @Test
    public void testFindByFilterSortingByUsageInfo() {
        UsageFilter filter = buildUsageFilter(Sets.newHashSet(2000017000L, 7000896777L), Collections.emptySet(),
            Collections.emptySet(), null, null, null);
        verifyUsageDtos(findByFilterWithSort(filter, "productFamily", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "productFamily", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, DETAIL_ID_KEY, Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, DETAIL_ID_KEY, Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, STATUS_KEY, Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, STATUS_KEY, Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, RH_ACCOUNT_NUMBER_KEY, Direction.ASC), 2, USAGE_ID_23,
            USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, RH_ACCOUNT_NUMBER_KEY, Direction.DESC), 2, USAGE_ID_24,
            USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, RH_NAME_KEY, Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, RH_NAME_KEY, Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "reportedValue", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "reportedValue", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, GROSS_AMOUNT_KEY, Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, GROSS_AMOUNT_KEY, Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "serviceFeeAmount", Direction.ASC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "serviceFeeAmount", Direction.DESC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "netAmount", Direction.ASC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "netAmount", Direction.DESC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, SERVICE_FEE_KEY, Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, SERVICE_FEE_KEY, Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
    }

    @Test
    public void testFindByFilterSortingByWorkInfo() {
        UsageFilter filter = buildUsageFilter(Sets.newHashSet(2000017000L, 7000896777L), Collections.emptySet(),
            Collections.emptySet(), null, null, null);
        verifyUsageDtos(findByFilterWithSort(filter, WORK_TITLE_KEY, Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, WORK_TITLE_KEY, Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "article", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "article", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, STANDARD_NUMBER_KEY, Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, STANDARD_NUMBER_KEY, Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, WR_WRK_INST_KEY, Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, WR_WRK_INST_KEY, Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "systemTitle", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "systemTitle", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "publisher", Direction.ASC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "publisher", Direction.DESC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "publicationDate", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "publicationDate", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "numberOfCopies", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "numberOfCopies", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "market", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "market", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "marketPeriodFrom", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "marketPeriodFrom", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "marketPeriodTo", Direction.ASC), 2, USAGE_ID_23, USAGE_ID_24);
        verifyUsageDtos(findByFilterWithSort(filter, "marketPeriodTo", Direction.DESC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "author", Direction.ASC), 2, USAGE_ID_24, USAGE_ID_23);
        verifyUsageDtos(findByFilterWithSort(filter, "author", Direction.DESC), 2, USAGE_ID_23, USAGE_ID_24);
    }

    @Test
    public void testFindForAuditWithSort() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton(BATCH_ID));
        verifyUsageDtos(findForAuditWithSort(filter, DETAIL_ID_KEY, true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, DETAIL_ID_KEY, false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, STATUS_KEY, true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, STATUS_KEY, false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, RH_ACCOUNT_NUMBER_KEY, true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, RH_ACCOUNT_NUMBER_KEY, false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, RH_NAME_KEY, true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, RH_NAME_KEY, false), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, WR_WRK_INST_KEY, false), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, WR_WRK_INST_KEY, true), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, WORK_TITLE_KEY, true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, WORK_TITLE_KEY, false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, STANDARD_NUMBER_KEY, true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, STANDARD_NUMBER_KEY, false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, GROSS_AMOUNT_KEY, true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, GROSS_AMOUNT_KEY, false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, SERVICE_FEE_KEY, true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, SERVICE_FEE_KEY, false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyUsageDtos(findForAuditWithSort(filter, "scenarioName", true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, "scenarioName", false), 2, USAGE_ID_4, USAGE_ID_5);
        verifyFilterForTwoBatches(filter);
        filter.setBatchesIds(Sets.newHashSet("48bfe456-fbc1-436e-8762-baca46a0e09c"));
        verifyUsageDtos(findForAuditWithSort(filter, "payeeAccountNumber", true), 2, USAGE_ID_16, USAGE_ID_15);
        verifyUsageDtos(findForAuditWithSort(filter, "payeeAccountNumber", false), 2, USAGE_ID_15, USAGE_ID_16);
        verifyUsageDtos(findForAuditWithSort(filter, "payeeName", true), 2, USAGE_ID_16, USAGE_ID_15);
        verifyUsageDtos(findForAuditWithSort(filter, "payeeName", false), 2, USAGE_ID_15, USAGE_ID_16);
        verifyUsageDtos(findForAuditWithSort(filter, "checkNumber", true), 2, USAGE_ID_15, USAGE_ID_16);
        verifyUsageDtos(findForAuditWithSort(filter, "checkNumber", false), 2, USAGE_ID_16, USAGE_ID_15);
        verifyUsageDtos(findForAuditWithSort(filter, "checkDate", true), 2, USAGE_ID_15, USAGE_ID_16);
        verifyUsageDtos(findForAuditWithSort(filter, "checkDate", false), 2, USAGE_ID_16, USAGE_ID_15);
        verifyUsageDtos(findForAuditWithSort(filter, "cccEventId", true), 2, USAGE_ID_15, USAGE_ID_16);
        verifyUsageDtos(findForAuditWithSort(filter, "cccEventId", false), 2, USAGE_ID_16, USAGE_ID_15);
        verifyUsageDtos(findForAuditWithSort(filter, "distributionName", true), 2, USAGE_ID_16, USAGE_ID_15);
        verifyUsageDtos(findForAuditWithSort(filter, "distributionName", false), 2, USAGE_ID_15, USAGE_ID_16);
        verifyUsageDtos(findForAuditWithSort(filter, "distributionDate", true), 2, USAGE_ID_15, USAGE_ID_16);
        verifyUsageDtos(findForAuditWithSort(filter, "distributionDate", false), 2, USAGE_ID_16, USAGE_ID_15);
        verifyUsageDtos(findForAuditWithSort(filter, "periodEndDate", true), 2, USAGE_ID_15, USAGE_ID_16);
        verifyUsageDtos(findForAuditWithSort(filter, "periodEndDate", false), 2, USAGE_ID_16, USAGE_ID_15);
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
        assertEquals(USAGE_ID_4, usageWorkFound.getId());
        assertEquals(UsageStatusEnum.WORK_FOUND, usageWorkFound.getStatus());
    }

    @Test
    public void testUpdateStatusWithUsageIds() {
        Usage usage1 = usageRepository.findById(USAGE_ID_4);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage1.getStatus());
        assertEquals(USER_NAME, usage1.getUpdateUser());
        Usage usage2 = usageRepository.findById(USAGE_ID_6);
        assertEquals(UsageStatusEnum.SENT_FOR_RA, usage2.getStatus());
        assertEquals(USER_NAME, usage2.getUpdateUser());
        usageRepository.updateStatus(ImmutableSet.of(usage1.getId(), usage2.getId()), UsageStatusEnum.RH_NOT_FOUND);
        usage1 = usageRepository.findById(USAGE_ID_4);
        assertEquals(UsageStatusEnum.RH_NOT_FOUND, usage1.getStatus());
        assertEquals(StoredEntity.DEFAULT_USER, usage1.getUpdateUser());
        usage2 = usageRepository.findById(USAGE_ID_6);
        assertEquals(UsageStatusEnum.RH_NOT_FOUND, usage2.getStatus());
        assertEquals(StoredEntity.DEFAULT_USER, usage2.getUpdateUser());
    }

    @Test
    public void testUpdateStatusAndRhAccountNumber() {
        Usage usage1 = usageRepository.findById(USAGE_ID_4);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage1.getStatus());
        assertNull(usage1.getRightsholder().getAccountNumber());
        Usage usage2 = usageRepository.findById(USAGE_ID_6);
        assertEquals(UsageStatusEnum.SENT_FOR_RA, usage2.getStatus());
        assertNull(usage2.getRightsholder().getAccountNumber());
        usageRepository.updateStatusAndRhAccountNumber(ImmutableSet.of(usage1.getId(), usage2.getId()),
            UsageStatusEnum.ELIGIBLE, RH_ACCOUNT_NUMBER);
        usage1 = usageRepository.findById(USAGE_ID_4);
        assertEquals(UsageStatusEnum.ELIGIBLE, usage1.getStatus());
        assertEquals(RH_ACCOUNT_NUMBER, usage1.getRightsholder().getAccountNumber());
        usage2 = usageRepository.findById(USAGE_ID_6);
        assertEquals(UsageStatusEnum.ELIGIBLE, usage2.getStatus());
        assertEquals(RH_ACCOUNT_NUMBER, usage2.getRightsholder().getAccountNumber());
    }

    @Test
    public void testUpdateRhPayeeAndAmounts() {
        Usage usage = usageRepository.findById(USAGE_ID_8);
        assertEquals(1000002859L, usage.getRightsholder().getAccountNumber(), 0);
        assertEquals(new BigDecimal("16437.4000000000"), usage.getGrossAmount());
        assertEquals(new BigDecimal("11177.4000000000"), usage.getNetAmount());
        assertEquals(new BigDecimal("5260.0000000000"), usage.getServiceFeeAmount());
        assertEquals(new BigDecimal("0.32000"), usage.getServiceFee());
        usage.getRightsholder().setAccountNumber(1000000001L);
        usage.setServiceFee(new BigDecimal("0.16000"));
        usage.setNetAmount(new BigDecimal("13807.4000000000"));
        usage.setServiceFeeAmount(new BigDecimal("2630.0000000000"));
        usageRepository.update(Collections.singletonList(usage));
        usage = usageRepository.findById(USAGE_ID_8);
        assertEquals(1000000001L, usage.getRightsholder().getAccountNumber(), 0);
        assertEquals(new BigDecimal("13807.4000000000"), usage.getNetAmount());
        assertEquals(new BigDecimal("2630.0000000000"), usage.getServiceFeeAmount());
        assertEquals(new BigDecimal("0.16000"), usage.getServiceFee());
        usage.getRightsholder().setAccountNumber(1000000001L);
    }

    @Test
    public void testUpdateToNtsWithdrawn() {
        Usage usage = usageRepository.findById(USAGE_ID_20);
        assertNotNull(usage);
        assertEquals(USAGE_ID_20, usage.getId());
        assertEquals(PRODUCT_FAMILY_FAS, usage.getProductFamily());
        assertEquals(UsageStatusEnum.NEW, usage.getStatus());
        usage.setProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
        usage.setStatus(UsageStatusEnum.NTS_WITHDRAWN);
        usageRepository.updateToNtsWithdrawn(usage);
        Usage actualUsage = usageRepository.findById(usage.getId());
        assertEquals(FdaConstants.NTS_PRODUCT_FAMILY, actualUsage.getProductFamily());
        assertEquals(UsageStatusEnum.NTS_WITHDRAWN, actualUsage.getStatus());
    }

    @Test
    public void testUpdateResearchedUsages() {
        String usageId1 = "721ca627-09bc-4204-99f4-6acae415fa5d";
        String usageId2 = "9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a";
        String title1 = "Title1";
        String title2 = "Title2";
        Usage usage1 = usageRepository.findById(usageId1);
        assertEquals(UsageStatusEnum.WORK_RESEARCH, usage1.getStatus());
        assertEquals(WORK_TITLE, usage1.getWorkTitle());
        assertNull(usage1.getSystemTitle());
        assertNull(usage1.getWrWrkInst());
        Usage usage2 = usageRepository.findById(usageId2);
        assertEquals(UsageStatusEnum.WORK_RESEARCH, usage2.getStatus());
        assertEquals(WORK_TITLE, usage2.getWorkTitle());
        assertNull(usage2.getSystemTitle());
        assertNull(usage2.getWrWrkInst());
        ResearchedUsage researchedUsage1 = new ResearchedUsage();
        researchedUsage1.setUsageId(usageId1);
        researchedUsage1.setSystemTitle(title1);
        researchedUsage1.setWrWrkInst(180382916L);
        ResearchedUsage researchedUsage2 = new ResearchedUsage();
        researchedUsage2.setUsageId(usageId2);
        researchedUsage2.setSystemTitle(title2);
        researchedUsage2.setWrWrkInst(854030733L);
        usageRepository.updateResearchedUsages(Lists.newArrayList(researchedUsage1, researchedUsage2));
        usage1 = usageRepository.findById(usageId1);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage1.getStatus());
        assertEquals(WORK_TITLE, usage1.getWorkTitle());
        assertEquals(title1, usage1.getSystemTitle());
        assertEquals(180382916L, usage1.getWrWrkInst().longValue());
        usage2 = usageRepository.findById(usageId2);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage2.getStatus());
        assertEquals(WORK_TITLE, usage2.getWorkTitle());
        assertEquals(title2, usage2.getSystemTitle());
        assertEquals(854030733L, usage2.getWrWrkInst().longValue());
    }

    @Test
    public void testFindByStatus() {
        List<Usage> usages = usageRepository.findByStatus(UsageStatusEnum.NEW, 5, 0);
        assertEquals(5, usages.size());
        assertTrue(usages.stream()
            .map(Usage::getId)
            .collect(Collectors.toSet())
            .containsAll(Sets.newHashSet(USAGE_ID_14, USAGE_ID_24, USAGE_ID_21, USAGE_ID_12, USAGE_ID_13)));
    }

    @Test
    public void testIsValidUsagesState() {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("ee575916-f6d0-4c3c-b589-32663e0f4793"));
        assertFalse(usageRepository.isValidUsagesState(usageFilter, UsageStatusEnum.WORK_NOT_FOUND));
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        assertTrue(usageRepository.isValidUsagesState(usageFilter, UsageStatusEnum.WORK_NOT_FOUND));
    }

    private void verifyFilterForTwoBatches(AuditFilter filter) {
        filter.setBatchesIds(Sets.newHashSet(BATCH_ID, "74b736f2-81ce-41fa-bd8e-574299232458"));
        verifyUsageDtos(findForAuditWithSort(filter, BATCH_NAME_KEY, true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, BATCH_NAME_KEY, false), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, PAYMENT_DATE_KEY, true), 2, USAGE_ID_5, USAGE_ID_4);
        verifyUsageDtos(findForAuditWithSort(filter, PAYMENT_DATE_KEY, false), 2, USAGE_ID_5, USAGE_ID_4);
    }

    private List<UsageDto> findForAuditWithSort(AuditFilter filter, String property, boolean order) {
        return usageRepository.findForAudit(filter, new Pageable(0, 10),
            new Sort(property, order ? Direction.ASC : Direction.DESC));
    }

    private List<UsageDto> findByFilterWithSort(UsageFilter filter, String property, Direction direction) {
        return usageRepository.findByFilter(filter, null, new Sort(property, direction));
    }

    private void verifyFindByScenarioIdAndRhSearch(String searchValue, int expectedSize) {
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
        assertEquals(PRODUCT_FAMILY_FAS, usage.getProductFamily());
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
        assertEquals(PRODUCT_FAMILY_FAS, usage.getProductFamily());
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
        usage.setWrWrkInst(WR_WRK_INST);
        usage.setWorkTitle("Work Title");
        usage.setSystemTitle("System Title");
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.getRightsholder().setName(RH_ACCOUNT_NAME);
        usage.setStatus(UsageStatusEnum.ELIGIBLE);
        usage.setProductFamily(PRODUCT_FAMILY_FAS);
        usage.setArticle("Article");
        usage.setStandardNumber("StandardNumber");
        usage.setPublisher("Publisher");
        usage.setPublicationDate(LocalDate.of(2016, 11, 3));
        usage.setMarket("Market");
        usage.setMarketPeriodFrom(2015);
        usage.setMarketPeriodTo(2017);
        usage.setAuthor("Author");
        usage.setNumberOfCopies(155);
        usage.setReportedValue(new BigDecimal("11.25"));
        usage.setGrossAmount(new BigDecimal("54.4400000000"));
        usage.setNetAmount(new BigDecimal("25.1500000000"));
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
        Usage usage = usageRepository.findById("3ab5e80b-89c0-4d78-9675-54c7ab284450");
        usage.getPayee().setAccountNumber(1000009997L);
        usage.setScenarioId(SCENARIO_ID);
        usage.setServiceFee(SERVICE_FEE);
        calculateAmounts(usage);
        usages.add(usage);
        usage = usageRepository.findById("8a06905f-37ae-4e1f-8550-245277f8165c");
        usage.getPayee().setAccountNumber(1000002859L);
        usage.setScenarioId(SCENARIO_ID);
        usage.setServiceFee(SERVICE_FEE);
        calculateAmounts(usage);
        usages.add(usage);
        usage = usageRepository.findById(USAGE_ID_9);
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
        IntStream.range(0, count).forEach(i -> assertEquals(usageIds[i], usageDtos.get(i).getId()));
    }

    private void verifyUsages(List<Usage> usages, int count, String... usageIds) {
        assertNotNull(usages);
        assertEquals(count, usages.size());
        IntStream.range(0, count).forEach(i -> {
            assertEquals(usageIds[i], usages.get(i).getId());
            assertEquals(UsageStatusEnum.ELIGIBLE, usages.get(i).getStatus());
        });
    }

    private void assertFindForAuditSearch(String searchValue, int expectedSize, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(searchValue);
        assertEquals(expectedSize, usageRepository.findCountForAudit(filter));
        verifyUsageDtos(usageRepository.findForAudit(filter, null, null), expectedSize, usageIds);
    }

    private void assertFindForAuditSearchByCccEventId(String cccEventId, int expectedSize, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setCccEventId(cccEventId);
        assertEquals(expectedSize, usageRepository.findCountForAudit(filter));
        verifyUsageDtos(usageRepository.findForAudit(filter, null, null), expectedSize, usageIds);
    }

    private void assertFindForAuditSearchByDistributionName(String distributionName, int expectedSize,
                                                            String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setDistributionName(distributionName);
        assertEquals(expectedSize, usageRepository.findCountForAudit(filter));
        verifyUsageDtos(usageRepository.findForAudit(filter, null, null), expectedSize, usageIds);
    }
}
