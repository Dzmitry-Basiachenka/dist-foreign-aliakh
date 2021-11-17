package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeeProductFamilyHolder;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UsageRepositoryIntegrationTest {

    private static final String TEST_DATA_FIND_DTOS_BY_FILTER =
        "usage-repository-test-data-init-find-dtos-by-filter.groovy";
    private static final String TEST_DATA_FIND_DTOS_BY_PRODUCT_FAMILY_FILTER =
        "usage-repository-test-data-init-find-dtos-by-product-family-filter.groovy";
    private static final String TEST_DATA_FIND_PAYEE_TOTAL_HOLDER_BY_FILTER =
        "usage-repository-test-data-init-find-payee-total-holder-by-filter.groovy";
    private static final String TEST_DATA_FIND_RH_TOTAL_HOLDERS_BY_SCENARIO_ID =
        "usage-repository-test-data-init-find-rh-total-holders-by-scenario-id.groovy";
    private static final String TEST_DATA_FIND_BY_SCENARIO_ID_AND_RH =
        "usage-repository-test-data-init-find-by-scenario-id-and-rh.groovy";
    private static final String TEST_DATA_FIND_FOR_AUDIT =
        "usage-repository-test-data-init-find-for-audit.groovy";
    private static final String USAGE_BATCH_ID_1 = "56282dbc-2468-48d4-b926-93d3458a656a";
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2018, 12, 11);
    private static final Integer FISCAL_YEAR = 2019;
    private static final String RH_ACCOUNT_NAME_1 = "IEEE - Inst of Electrical and Electronics Engrs";
    private static final String RH_ACCOUNT_NAME_2 = "John Wiley & Sons - Books";
    private static final String RH_ACCOUNT_NAME_3 = "Kluwer Academic Publishers - Dordrecht";
    private static final String RH_ACCOUNT_NAME_4 =
        "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil";
    private static final String WORK_TITLE = "100 ROAD MOVIES";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FAS2_PRODUCT_FAMILY = "FAS2";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String DETAIL_ID_KEY = "detailId";
    private static final String WORK_TITLE_KEY = "workTitle";
    private static final String BATCH_NAME_KEY = "batchName";
    private static final String PAYMENT_DATE_KEY = "paymentDate";
    private static final String STANDARD_NUMBER_KEY = "standardNumber";
    private static final String WR_WRK_INST_KEY = "wrWrkInst";
    private static final String RH_ACCOUNT_NUMBER_KEY = "rhAccountNumber";
    private static final String RH_NAME_KEY = "rhName";
    private static final String REPORTED_VALUE_KEY = "reportedValue";
    private static final String GROSS_AMOUNT_KEY = "grossAmount";
    private static final String BATCH_GROSS_AMOUNT_KEY = "batchGrossAmount";
    private static final String SERVICE_FEE_KEY = "serviceFee";
    private static final String COMMENT_KEY = "comment";
    private static final String STATUS_KEY = "status";
    private static final String STANDARD_NUMBER = "2192-3558";
    private static final String USAGE_ID_1 = "3ab5e80b-89c0-4d78-9675-54c7ab284450";
    private static final String USAGE_ID_2 = "8a06905f-37ae-4e1f-8550-245277f8165c";
    private static final String USAGE_ID_3 = "5c5f8c1c-1418-4cfd-8685-9212f4c421d1";
    private static final String USAGE_ID_4 = "d9ca07b5-8282-4a81-9b9d-e4480f529d34";
    private static final String USAGE_ID_5 = "a71a0544-128e-41c0-b6b0-cfbbea6d2182";
    private static final String USAGE_ID_6 = "62e0ddd7-a37f-4810-8ada-abab805cb48d";
    private static final String USAGE_ID_7 = "cf38d390-11bb-4af7-9685-e034c9c32fb6";
    private static final String USAGE_ID_8 = "b1f0b236-3ae9-4a60-9fab-61db84199d11";
    private static final String USAGE_ID_11 = "7db6455e-5249-44db-801a-307f1c239310";
    private static final String USAGE_ID_12 = "593c49c3-eb5b-477b-8556-f7a4725df2b3";
    private static final String USAGE_ID_13 = "66a7c2c0-3b09-48ad-9aa5-a6d0822226c7";
    private static final String USAGE_ID_14 = "0c099fc0-e6f5-43c0-b2d5-ad971f974c10";
    private static final String USAGE_ID_15 = "0d85f51d-212b-4181-9972-3154cad74bd0";
    private static final String USAGE_ID_16 = "1cb766c6-7c49-489a-bd8f-9b8b052f5785";
    private static final String USAGE_ID_17 = "b53bb4f3-9eee-4732-8e3d-0c88722081d8";
    private static final String USAGE_ID_18 = "9a898ab5-30c9-4289-8e17-2c35dcb7f9e1";
    private static final String USAGE_ID_20 = "dcc794ba-42aa-481d-937b-8f431929a611";
    private static final String USAGE_ID_21 = "47d48889-76b5-4957-aca0-2a7850a09f92";
    private static final String USAGE_ID_22 = "c5ea47b0-b269-4791-9aa7-76308fe835e6";
    private static final String USAGE_ID_23 = "3b6892a9-49b2-41a2-aa3a-8705ea6640cc";
    private static final String USAGE_ID_24 = "3c31db4f-4065-4fe1-84c2-b48a0f3bc079";
    private static final String USAGE_ID_31 = "3cf274c5-8eac-4d4a-96be-5921ae026840";
    private static final String USAGE_ID_32 = "f5eb98ce-ab59-44c8-9a50-1afea2b5ae15";
    private static final String SCENARIO_ID = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";
    private static final String SCENARIO_ID_2 = "e13ecc44-6795-4b75-90f0-4a3fc191f1b9";
    private static final String USER_NAME = "user@copyright.com";
    private static final String BATCH_ID = "e0af666b-cbb7-4054-9906-12daa1fbd76e";
    private static final String PERCENT = "%";
    private static final String UNDERSCORE = "_";
    private static final BigDecimal SERVICE_FEE = new BigDecimal("0.32000");
    private static final BigDecimal ZERO_AMOUNT = new BigDecimal("0.00");
    private static final BigDecimal DEFAULT_ZERO_AMOUNT = new BigDecimal("0.0000000000");

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private UsageRepository usageRepository;

    @Test
    @TestData(fileName = TEST_DATA_FIND_DTOS_BY_FILTER)
    public void testFindCountByFilter() {
        assertEquals(1, usageRepository.findCountByFilter(
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                FAS_PRODUCT_FAMILY, UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR)));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_DTOS_BY_FILTER)
    public void testFindDtosByFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                FAS_PRODUCT_FAMILY, UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID_1);
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-by-status-and-product-family.groovy")
    public void testFindIdsByStatusAnsProductFamily() {
        List<String> actualUsageIds =
            usageRepository.findIdsByStatusAndProductFamily(UsageStatusEnum.US_TAX_COUNTRY, NTS_PRODUCT_FAMILY);
        assertEquals(Arrays.asList("463e2239-1a36-41cc-9a51-ee2a80eae0c7", "bd407b50-6101-4304-8316-6404fe32a800"),
            actualUsageIds);
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-by-status-and-product-family.groovy")
    public void testFindByStatusAnsProductFamily() throws IOException {
        List<Usage> actualUsages =
            usageRepository.findByStatusAndProductFamily(UsageStatusEnum.US_TAX_COUNTRY, NTS_PRODUCT_FAMILY);
        verifyUsages(loadExpectedUsages("json/usages_find_by_status.json"), actualUsages);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_DTOS_BY_FILTER)
    public void testFindDtosByUsageBatchFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1),
            null, null, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID_1);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_DTOS_BY_FILTER)
    public void testFindDtosByRhAccountNumberFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.emptySet(),
            null, null, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID_1);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_DTOS_BY_PRODUCT_FAMILY_FILTER)
    public void testFindDtosByProductFamilyFasFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            FAS_PRODUCT_FAMILY, null, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID_14, USAGE_ID_1, USAGE_ID_23, USAGE_ID_21,
            USAGE_ID_12, USAGE_ID_3, USAGE_ID_6, USAGE_ID_13, USAGE_ID_11, USAGE_ID_2, USAGE_ID_17, USAGE_ID_22,
            USAGE_ID_4, USAGE_ID_20);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_DTOS_BY_PRODUCT_FAMILY_FILTER)
    public void testFindDtosByProductFamilyFas2Filter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            FAS2_PRODUCT_FAMILY, null, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID_24);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_DTOS_BY_PRODUCT_FAMILY_FILTER)
    public void testFindDtosByProductFamilyNtsFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            NTS_PRODUCT_FAMILY, null, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), "2255188f-d582-4516-8c08-835cfe1d68c2",
            "4dd8cdf8-ca10-422e-bdd5-3220105e6379", "f5eb98ce-ab59-44c8-9a50-1afea2b5ae15");
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_DTOS_BY_PRODUCT_FAMILY_FILTER)
    public void testFindDtosByStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            null, UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null, new Sort(DETAIL_ID_KEY,
            Sort.Direction.ASC)), USAGE_ID_1, USAGE_ID_3, USAGE_ID_2);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_DTOS_BY_PRODUCT_FAMILY_FILTER)
    public void testFindDtosByPaymentDateFilterSortByWorkTitle() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            null, null, PAYMENT_DATE, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null, new Sort(WORK_TITLE_KEY,
            Sort.Direction.ASC)), USAGE_ID_3, USAGE_ID_2, USAGE_ID_1);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_DTOS_BY_PRODUCT_FAMILY_FILTER)
    public void testFindDtosByFiscalYearFilterSortByArticle() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            null, null, null, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null, new Sort("article", Sort.Direction.ASC)),
            USAGE_ID_3, USAGE_ID_1, USAGE_ID_2);
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-invalid-rh-by-filter.groovy")
    public void testFindInvalidRightsholdersByFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1),
                null, UsageStatusEnum.ELIGIBLE, null, null);
        assertTrue(CollectionUtils.isEmpty(usageRepository.findInvalidRightsholdersByFilter(usageFilter)));
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_18));
        usages.get(0).getRightsholder().setAccountNumber(1000000003L);
        usageRepository.update(usages);
        List<Long> accountNumbers = usageRepository.findInvalidRightsholdersByFilter(usageFilter);
        assertEquals(1, accountNumbers.size());
        assertEquals(1000000003L, accountNumbers.get(0), 0);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_PAYEE_TOTAL_HOLDER_BY_FILTER)
    public void testFindPayeeTotalHoldersByScenarioFilter() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton(SCENARIO_ID_2));
        List<PayeeTotalHolder> payeeTotalHolders = usageRepository.findPayeeTotalHoldersByFilter(filter);
        assertEquals(2, CollectionUtils.size(payeeTotalHolders));
        verifyPayeeTotalsHolder(7000813806L, RH_ACCOUNT_NAME_4, new BigDecimal("100.0000000000"),
            new BigDecimal("68.0000000000"), new BigDecimal("32.0000000000"), true, payeeTotalHolders.get(0));
        verifyPayeeTotalsHolder(1000002859L,
            "John Wiley & Sons - Books", new BigDecimal("200.0000000000"), new BigDecimal("152.0000000000"),
            new BigDecimal("48.0000000000"), false, payeeTotalHolders.get(1));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_PAYEE_TOTAL_HOLDER_BY_FILTER)
    public void testFindPayeeTotalHoldersByScenarioFilterAndSearch() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton(SCENARIO_ID_2));
        filter.setSearchValue("Administracion");
        List<PayeeTotalHolder> payeeTotalHolders = usageRepository.findPayeeTotalHoldersByFilter(filter);
        assertEquals(1, CollectionUtils.size(payeeTotalHolders));
        verifyPayeeTotalsHolder(7000813806L, RH_ACCOUNT_NAME_4, new BigDecimal("100.0000000000"),
            new BigDecimal("68.0000000000"), new BigDecimal("32.0000000000"), true, payeeTotalHolders.get(0));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_PAYEE_TOTAL_HOLDER_BY_FILTER)
    public void testFindPayeeTotalHoldersByScenarioFilterAndThreshold() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton(SCENARIO_ID_2));
        filter.setNetAmountMinThreshold(new BigDecimal(150));
        List<PayeeTotalHolder> payeeTotalHolders = usageRepository.findPayeeTotalHoldersByFilter(filter);
        assertEquals(1, CollectionUtils.size(payeeTotalHolders));
        verifyPayeeTotalsHolder(7000813806L, RH_ACCOUNT_NAME_4, new BigDecimal("100.0000000000"),
            new BigDecimal("68.0000000000"), new BigDecimal("32.0000000000"), true, payeeTotalHolders.get(0));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_PAYEE_TOTAL_HOLDER_BY_FILTER)
    public void testFindPayeeTotalHoldersByScenarioFilterAndParticipationStatus() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton(SCENARIO_ID_2));
        filter.setPayeeParticipating(false);
        List<PayeeTotalHolder> payeeTotalHolders = usageRepository.findPayeeTotalHoldersByFilter(filter);
        assertEquals(1, CollectionUtils.size(payeeTotalHolders));
        verifyPayeeTotalsHolder(1000002859L,
            "John Wiley & Sons - Books", new BigDecimal("200.0000000000"), new BigDecimal("152.0000000000"),
            new BigDecimal("48.0000000000"), false, payeeTotalHolders.get(0));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_RH_TOTAL_HOLDERS_BY_SCENARIO_ID)
    public void testFindRightsholderTotalsHoldersByScenarioIdEmptySearchValue() {
        populateScenario();
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null, null);
        assertEquals(3, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, RH_ACCOUNT_NAME_1, 1000009997L,
            35000.00, 11200.00, 23800.00), rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, RH_ACCOUNT_NAME_2, 1000002859L,
            67874.80, 21720.00, 46154.80), rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, RH_ACCOUNT_NAME_4, 7000813806L,
            2125.24, 680.0768, 1445.1632), rightsholderTotalsHolders.get(2));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_RH_TOTAL_HOLDERS_BY_SCENARIO_ID)
    public void testFindRightsholderTotalsHoldersByScenarioIdNotEmptySearchValue() {
        populateScenario();
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "JoHn", null, null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, RH_ACCOUNT_NAME_2, 1000002859L,
            67874.80, 21720.00, 46154.80), rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "IEEE", null, null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, RH_ACCOUNT_NAME_1, 1000009997L,
            35000.00, 11200.00, 23800.00), rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "700081380", null, null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, RH_ACCOUNT_NAME_4, 7000813806L,
            2125.24, 680.0768, 1445.1632), rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "ec", null, null);
        assertEquals(2, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, RH_ACCOUNT_NAME_1, 1000009997L,
            35000.00, 11200.00, 23800.00), rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, RH_ACCOUNT_NAME_4, 7000813806L,
            2125.24, 680.0768, 1445.1632), rightsholderTotalsHolders.get(1));
        assertEquals(0,
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, PERCENT, null, null).size());
        assertEquals(0,
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, UNDERSCORE, null, null).size());
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_RH_TOTAL_HOLDERS_BY_SCENARIO_ID)
    public void testFindRightsholderTotalsHoldersByScenarioIdSortByAccountNumber() {
        populateScenario();
        Sort accountNumberSort = new Sort("rightsholder.accountNumber", Direction.ASC);
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null,
                accountNumberSort);
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, RH_ACCOUNT_NAME_2, 1000002859L,
            67874.80, 21720.00, 46154.80), rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, RH_ACCOUNT_NAME_4, 7000813806L,
            2125.24, 680.0768, 1445.1632), rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, RH_ACCOUNT_NAME_1, 1000009997L,
            35000.00, 11200.00, 23800.00), rightsholderTotalsHolders.get(2));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_RH_TOTAL_HOLDERS_BY_SCENARIO_ID)
    public void testFindRightsholderTotalsHoldersByScenarioIdSortByName() {
        populateScenario();
        Sort accountNumberSort = new Sort("rightsholder.name", Direction.DESC);
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null,
                accountNumberSort);
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, RH_ACCOUNT_NAME_4, 7000813806L,
            2125.24, 680.0768, 1445.1632), rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, RH_ACCOUNT_NAME_2, 1000002859L,
            67874.80, 21720.00, 46154.80), rightsholderTotalsHolders.get(1));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, RH_ACCOUNT_NAME_1, 1000009997L,
            35000.00, 11200.00, 23800.00), rightsholderTotalsHolders.get(2));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_BY_SCENARIO_ID_AND_RH)
    public void testFindCountByScenarioIdAndRhAccountNumberNullSearchValue() {
        populateScenario();
        Usage usage = usageRepository.findByIds(Collections.singletonList(USAGE_ID_18)).get(0);
        usageRepository.addToScenario(Collections.singletonList(usage));
        assertEquals(1, usageRepository.findCountByScenarioIdAndRhAccountNumber(1000009997L, SCENARIO_ID, null));
        assertEquals(3, usageRepository.findCountByScenarioIdAndRhAccountNumber(1000002859L, SCENARIO_ID, null));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_BY_SCENARIO_ID_AND_RH)
    public void testFindByScenarioIdAndRhAccountNumberNullSearchValue() {
        populateScenario();
        assertEquals(1,
            usageRepository.findByScenarioIdAndRhAccountNumber(1000009997L, SCENARIO_ID, null, null, null).size());
        assertEquals(3,
            usageRepository.findByScenarioIdAndRhAccountNumber(1000002859L, SCENARIO_ID, null, null, null).size());
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_BY_SCENARIO_ID_AND_RH)
    public void testFindByScenarioIdAndRhAccountNumberSearchByRorName() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch("Access Copyright, The Canadian Copyright Agency", 1);
        verifyFindByScenarioIdAndRhSearch("Academic", 2);
        verifyFindByScenarioIdAndRhSearch("aCaDemiC", 2);
        verifyFindByScenarioIdAndRhSearch("Aca demic", 0);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_BY_SCENARIO_ID_AND_RH)
    public void testFindByScenarioIdAndRhAccountNumberSearchByRorAccountNumber() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch("2000017010", 2);
        verifyFindByScenarioIdAndRhSearch("0001700", 1);
        verifyFindByScenarioIdAndRhSearch("70014 40663", 0);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_BY_SCENARIO_ID_AND_RH)
    public void testFindByScenarioIdAndRhAccountNumberSearchDetailId() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch(USAGE_ID_8, 1);
        verifyFindByScenarioIdAndRhSearch("4a60", 1);
        verifyFindByScenarioIdAndRhSearch("4a", 2);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_BY_SCENARIO_ID_AND_RH)
    public void testFindByScenarioIdAndRhAccountNumberSearchByWrWrkInst() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch("243904752", 2);
        verifyFindByScenarioIdAndRhSearch("244614", 1);
        verifyFindByScenarioIdAndRhSearch("24461 4835", 0);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_BY_SCENARIO_ID_AND_RH)
    public void testFindByScenarioIdAndRhAccountNumberSearchByStandardNumber() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch("1008902002377655XX", 1);
        verifyFindByScenarioIdAndRhSearch("1008902002377655xx", 1);
        verifyFindByScenarioIdAndRhSearch("10089", 3);
        verifyFindByScenarioIdAndRhSearch("100890 2002377655XX", 0);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_BY_SCENARIO_ID_AND_RH)
    public void testFindByScenarioIdAndRhAccountNumberSearchBySqlLikePattern() {
        populateScenario();
        verifyFindByScenarioIdAndRhSearch(PERCENT, 0);
        verifyFindByScenarioIdAndRhSearch(UNDERSCORE, 0);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_RH_TOTAL_HOLDERS_BY_SCENARIO_ID)
    public void testFindRightsholderTotalsHolderCountByScenarioId() {
        populateScenario();
        assertEquals(3, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY));
        assertEquals(1, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, "IEEE"));
        assertEquals(1, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, "1000009"));
        assertEquals(1, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, "1000005413"));
        assertEquals(0, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, PERCENT));
        assertEquals(0, usageRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, UNDERSCORE));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-is-scenario-empty.groovy")
    public void testIsScenarioEmpty() {
        populateScenario();
        assertFalse(usageRepository.isScenarioEmpty("b1f0b236-3ae9-4a60-9fab-61db84199d6f"));
        assertTrue(usageRepository.isScenarioEmpty("e27551ed-3f69-4e08-9e4f-8ac03f67595f"));
        assertTrue(usageRepository.isScenarioEmpty("979c981c-6a3a-46f3-bbd7-83d322ce9136"));
        assertTrue(usageRepository.isScenarioEmpty("091c08cf-8a93-4a64-87b5-4bdd44f97e79"));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-referenced-usages-count-by-ids.groovy")
    public void testFindReferencedUsagesCountByIds() {
        assertEquals(2, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_31, USAGE_ID_2));
        assertEquals(1, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_32));
        assertEquals(1, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_31, "invalidId"));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-delete-by-batch-id.groovy")
    public void testDeleteByBatchId() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Sets.newHashSet(USAGE_BATCH_ID_1));
        Sort sort = new Sort(DETAIL_ID_KEY, Direction.ASC);
        List<UsageDto> usages = usageRepository.findDtosByFilter(filter, null, sort);
        assertEquals(1, usages.size());
        assertEquals(USAGE_ID_1, usages.get(0).getId());
        assertEquals(1, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_1));
        usageRepository.deleteByBatchId(USAGE_BATCH_ID_1);
        assertEquals(0, usageRepository.findDtosByFilter(filter, null, sort).size());
        assertEquals(0, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_1));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-delete-by-id.groovy")
    public void testDeleteById() {
        List<Usage> usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_31, USAGE_ID_32));
        assertEquals(2, CollectionUtils.size(usages));
        assertEquals(1, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_31));
        assertEquals(1, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_32));
        usageRepository.deleteById(USAGE_ID_31);
        usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_31, USAGE_ID_32));
        assertEquals(1, CollectionUtils.size(usages));
        assertEquals(USAGE_ID_32, usages.get(0).getId());
        assertEquals(0, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_31));
        assertEquals(1, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_32));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-by-scenario-id.groovy")
    public void testFindByScenarioId() {
        List<Usage> usages = usageRepository.findByScenarioId(SCENARIO_ID);
        assertEquals(2, usages.size());
        usages.forEach(
            usage -> verifyFasUsage(usage, UsageStatusEnum.LOCKED, SCENARIO_ID, StoredEntity.DEFAULT_USER,
                1000002859L));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-delete-by-scenario-id.groovy")
    public void testDeleteByScenarioId() {
        assertEquals(2, usageRepository.findByScenarioId(SCENARIO_ID).size());
        assertEquals(2, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_7, USAGE_ID_8));
        usageRepository.deleteByScenarioId(SCENARIO_ID);
        assertTrue(usageRepository.findByScenarioId(SCENARIO_ID).isEmpty());
        assertEquals(2, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_7, USAGE_ID_8));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-by-ids.groovy")
    public void testFindByIds() {
        List<Usage> usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_1, USAGE_ID_2));
        assertEquals(2, CollectionUtils.size(usages));
        assertEquals(Arrays.asList(USAGE_ID_1, USAGE_ID_2),
            usages.stream().map(Usage::getId).collect(Collectors.toList()));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-add-to-scenario.groovy")
    public void testAddToScenario() {
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_3));
        assertEquals(1, CollectionUtils.size(usages));
        Usage usage = usages.get(0);
        verifyFasUsage(usage, UsageStatusEnum.ELIGIBLE, null, StoredEntity.DEFAULT_USER, null);
        usage.getPayee().setAccountNumber(2000017004L);
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setScenarioId(SCENARIO_ID);
        usage.setUpdateUser(USER_NAME);
        BigDecimal serviceFeeAmount = new BigDecimal("2205.536").setScale(10);
        usage.setServiceFeeAmount(serviceFeeAmount);
        BigDecimal netAmount = new BigDecimal("4686.764").setScale(10);
        usage.setNetAmount(netAmount);
        usage.setServiceFee(SERVICE_FEE);
        usage.setRhParticipating(true);
        usage.setPayeeParticipating(true);
        usageRepository.addToScenario(Collections.singletonList(usage));
        usages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_3));
        assertEquals(1, CollectionUtils.size(usages));
        verifyFasUsage(usages.get(0), UsageStatusEnum.LOCKED, SCENARIO_ID, USER_NAME, 2000017004L);
        assertEquals(SERVICE_FEE, usage.getServiceFee());
        assertEquals(serviceFeeAmount, usage.getServiceFeeAmount());
        assertEquals(netAmount, usage.getNetAmount());
        assertTrue(usage.isRhParticipating());
        assertTrue(usage.isPayeeParticipating());
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-delete-from-scenario.groovy")
    public void testDeleteFromScenario() {
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_8));
        assertEquals(1, CollectionUtils.size(usages));
        verifyFasUsage(usages.get(0), UsageStatusEnum.LOCKED, SCENARIO_ID, StoredEntity.DEFAULT_USER, 1000002859L);
        usageRepository.deleteFromScenario(SCENARIO_ID, USER_NAME);
        verifyUsageExcludedFromScenario(usageRepository.findByIds(Collections.singletonList(USAGE_ID_8)).get(0),
            FAS_PRODUCT_FAMILY, UsageStatusEnum.ELIGIBLE);
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-count-by-usage-id-and-status.groovy")
    public void testFindCountByDetailIdAndStatus() {
        assertEquals(0, usageRepository.findCountByUsageIdAndStatus(USAGE_ID_4, UsageStatusEnum.NEW));
        assertEquals(1, usageRepository.findCountByUsageIdAndStatus(USAGE_ID_1, UsageStatusEnum.ELIGIBLE));
        assertEquals(1, usageRepository.findCountByUsageIdAndStatus(USAGE_ID_5, UsageStatusEnum.SENT_TO_LM));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-ids-by-scenario-id-and-rh-account-number.groovy")
    public void testFindByScenarioIdAndRhAccountNumbers() {
        Usage usage = usageRepository.findByIds(Collections.singletonList(USAGE_ID_18)).get(0);
        usage.setScenarioId(SCENARIO_ID);
        usageRepository.addToScenario(Collections.singletonList(usage));
        List<String> usagesIds = usageRepository.findIdsByScenarioIdRroAccountNumberRhAccountNumbers(
            SCENARIO_ID, 2000017010L, Lists.newArrayList(1000002859L, 7000813806L));
        assertEquals(2, usagesIds.size());
        assertTrue(usagesIds.containsAll(Lists.newArrayList(USAGE_ID_8, USAGE_ID_7)));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-get-total-amount-by-standard-number-and-batch-id.groovy")
    public void testGetTotalAmountByStandardNumberAndBatchId() {
        assertEquals(new BigDecimal("10000.00"), usageRepository.getTotalAmountByStandardNumberAndBatchId("2192-3558",
            "c10a11c6-dae3-43d7-a632-c682542b1209"));
        assertEquals(new BigDecimal("16.40"),
            usageRepository.getTotalAmountByStandardNumberAndBatchId("5475802112214578XX",
                "cb597f4e-f636-447f-8710-0436d8994d10"));
        assertEquals(ZERO_AMOUNT,
            usageRepository.getTotalAmountByStandardNumberAndBatchId("100 ROAD 5475802112214578XX", "invalid id"));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-get-total-amount-by-title-and-batch-id.groovy")
    public void testGetTotalAmountByTitleAndBatchId() {
        assertEquals(new BigDecimal("2000.00"),
            usageRepository.getTotalAmountByTitleAndBatchId("Wissenschaft & Forschung Japan",
                "9776da8d-098d-4f39-99fd-85405c339e9b"));
        assertEquals(ZERO_AMOUNT, usageRepository.getTotalAmountByTitleAndBatchId(WORK_TITLE,
            "cb597f4e-f636-447f-8710-0436d8994d10"));
        assertEquals(ZERO_AMOUNT, usageRepository.getTotalAmountByTitleAndBatchId(WORK_TITLE, "invalid id"));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditFas() throws IOException {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(USAGE_ID_8);
        verifyUsageDtosForAudit(loadExpectedUsageDtos("json/usage_dto_find_for_audit_fas.json"),
            usageRepository.findForAudit(filter, null, null));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditArchivedFas() throws IOException {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(USAGE_ID_15);
        verifyUsageDtosForAudit(loadExpectedUsageDtos("json/usage_dto_find_for_audit_archive_fas.json"),
            usageRepository.findForAudit(filter, null, null));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditNts() throws IOException {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue("c09aa888-85a5-4377-8c7a-85d84d255b5a");
        verifyUsageDtosForAudit(loadExpectedUsageDtos("json/usage_dto_find_for_audit_nts.json"),
            usageRepository.findForAudit(filter, null, null));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditArchivedNts() throws IOException {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue("fb297d5d-4d46-4492-93b0-cd6e02b8ce8d");
        verifyUsageDtosForAudit(loadExpectedUsageDtos("json/usage_dto_find_for_audit_archive_nts.json"),
            usageRepository.findForAudit(filter, null, null));
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditByStatus() {
        AuditFilter filter = new AuditFilter();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.SENT_TO_LM));
        assertEquals(1, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_5);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditByBatch() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton(BATCH_ID));
        assertEquals(2, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_5, USAGE_ID_4);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditByRightsholders() {
        AuditFilter filter = new AuditFilter();
        filter.setRhAccountNumbers(Collections.singleton(1000002475L));
        assertEquals(1, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_5);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditByProductFamilies() {
        AuditFilter filter = new AuditFilter();
        filter.setProductFamily(FAS_PRODUCT_FAMILY);
        assertEquals(11, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC));
        verifyUsageDtos(usages, USAGE_ID_15, USAGE_ID_16, USAGE_ID_1, USAGE_ID_3, USAGE_ID_6, USAGE_ID_2, USAGE_ID_18,
            USAGE_ID_5, USAGE_ID_8, USAGE_ID_7, USAGE_ID_4);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditWithSearch() {
        assertFindForAuditSearch(USAGE_ID_5, USAGE_ID_5);
        assertFindForAuditSearch("Nitrates", USAGE_ID_4);
        assertFindForAuditSearch(USAGE_ID_4, USAGE_ID_4);
        assertFindForAuditSearch("Hydronitrous", USAGE_ID_4);
        assertFindForAuditSearch(PERCENT);
        assertFindForAuditSearch(UNDERSCORE);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditSearchByCccEventId() {
        assertFindForAuditSearchByCccEventId("53256", USAGE_ID_15);
        assertFindForAuditSearchByCccEventId(PERCENT);
        assertFindForAuditSearchByCccEventId(UNDERSCORE);
        assertFindForAuditSearchByCccEventId("3257");
        assertFindForAuditSearchByCccEventId("5325");
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditSearchByDistributionName() {
        assertFindForAuditSearchByDistributionName("FDA March 17", USAGE_ID_15);
        assertFindForAuditSearchByDistributionName("FDA March 1");
        assertFindForAuditSearchByDistributionName("DA March 17");
        assertFindForAuditSearchByDistributionName(PERCENT);
        assertFindForAuditSearchByDistributionName(UNDERSCORE);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditPageable() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton(BATCH_ID));
        assertEquals(2, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 1), null);
        verifyUsageDtos(usages, USAGE_ID_5);
        usages = usageRepository.findForAudit(filter, new Pageable(1, 1), null);
        verifyUsageDtos(usages, USAGE_ID_4);
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-dtos-by-filter-sort.groovy")
    public void testFindByFilterSortingByBatchInfo() {
        UsageFilter filter = buildUsageFilter(Sets.newHashSet(2000017000L, 7000896777L), Collections.emptySet(),
            null, null, null, null);
        verifyFindByFilterSort(filter, BATCH_NAME_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, BATCH_NAME_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "fiscalYear", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "fiscalYear", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "rroAccountNumber", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "rroAccountNumber", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "rroName", Direction.ASC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "rroName", Direction.DESC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, PAYMENT_DATE_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, PAYMENT_DATE_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, BATCH_GROSS_AMOUNT_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, BATCH_GROSS_AMOUNT_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, COMMENT_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, COMMENT_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-dtos-by-filter-sort.groovy")
    public void testFindByFilterSortingByUsageInfo() {
        UsageFilter filter = buildUsageFilter(Sets.newHashSet(2000017000L, 7000896777L), Collections.emptySet(),
            null, null, null, null);
        verifyFindByFilterSort(filter, "productFamily", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "productFamily", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, DETAIL_ID_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, DETAIL_ID_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, STATUS_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, STATUS_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, RH_ACCOUNT_NUMBER_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, RH_ACCOUNT_NUMBER_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, RH_NAME_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, RH_NAME_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, REPORTED_VALUE_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, REPORTED_VALUE_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, GROSS_AMOUNT_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, GROSS_AMOUNT_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "serviceFeeAmount", Direction.ASC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "serviceFeeAmount", Direction.DESC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "netAmount", Direction.ASC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "netAmount", Direction.DESC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, SERVICE_FEE_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, SERVICE_FEE_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, COMMENT_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, COMMENT_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-dtos-by-filter-sort.groovy")
    public void testFindByFilterSortingByWorkInfo() {
        UsageFilter filter = buildUsageFilter(Sets.newHashSet(2000017000L, 7000896777L), Collections.emptySet(),
            null, null, null, null);
        verifyFindByFilterSort(filter, STANDARD_NUMBER_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, STANDARD_NUMBER_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "standardNumberType", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "standardNumberType", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, WR_WRK_INST_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, WR_WRK_INST_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "systemTitle", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "systemTitle", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "numberOfCopies", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "numberOfCopies", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, COMMENT_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, COMMENT_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditSortingByCommonUsageInfo() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton(BATCH_ID));
        verifyFindForAuditSort(filter, DETAIL_ID_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, DETAIL_ID_KEY, Direction.DESC, USAGE_ID_4, USAGE_ID_5);
        verifyFindForAuditSort(filter, STATUS_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, STATUS_KEY, Direction.DESC, USAGE_ID_4, USAGE_ID_5);
        verifyFindForAuditSort(filter, RH_ACCOUNT_NUMBER_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, RH_ACCOUNT_NUMBER_KEY, Direction.DESC, USAGE_ID_4, USAGE_ID_5);
        verifyFindForAuditSort(filter, RH_NAME_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, RH_NAME_KEY, Direction.DESC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, WR_WRK_INST_KEY, Direction.DESC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, WR_WRK_INST_KEY, Direction.ASC, USAGE_ID_4, USAGE_ID_5);
        verifyFindForAuditSort(filter, WORK_TITLE_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, WORK_TITLE_KEY, Direction.DESC, USAGE_ID_4, USAGE_ID_5);
        verifyFindForAuditSort(filter, STANDARD_NUMBER_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, STANDARD_NUMBER_KEY, Direction.DESC, USAGE_ID_4, USAGE_ID_5);
        verifyFindForAuditSort(filter, REPORTED_VALUE_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, REPORTED_VALUE_KEY, Direction.DESC, USAGE_ID_4, USAGE_ID_5);
        verifyFindForAuditSort(filter, GROSS_AMOUNT_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, GROSS_AMOUNT_KEY, Direction.DESC, USAGE_ID_4, USAGE_ID_5);
        verifyFindForAuditSort(filter, SERVICE_FEE_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, SERVICE_FEE_KEY, Direction.DESC, USAGE_ID_4, USAGE_ID_5);
        verifyFindForAuditSort(filter, "scenarioName", Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, "scenarioName", Direction.DESC, USAGE_ID_4, USAGE_ID_5);
        verifyFindForAuditSort(filter, COMMENT_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, COMMENT_KEY, Direction.DESC, USAGE_ID_4, USAGE_ID_5);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditSortingByBatchInfo() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Sets.newHashSet(BATCH_ID, "74b736f2-81ce-41fa-bd8e-574299232458"));
        verifyFindForAuditSort(filter, BATCH_NAME_KEY, Direction.DESC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, BATCH_NAME_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, BATCH_GROSS_AMOUNT_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, BATCH_GROSS_AMOUNT_KEY, Direction.DESC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, PAYMENT_DATE_KEY, Direction.ASC, USAGE_ID_5, USAGE_ID_4);
        verifyFindForAuditSort(filter, PAYMENT_DATE_KEY, Direction.DESC, USAGE_ID_5, USAGE_ID_4);
    }

    @Test
    @TestData(fileName = TEST_DATA_FIND_FOR_AUDIT)
    public void testFindForAuditSortingByPaidInfo() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton("48bfe456-fbc1-436e-8762-baca46a0e09c"));
        verifyFindForAuditSort(filter, "payeeAccountNumber", Direction.ASC, USAGE_ID_16, USAGE_ID_15);
        verifyFindForAuditSort(filter, "payeeAccountNumber", Direction.DESC, USAGE_ID_15, USAGE_ID_16);
        verifyFindForAuditSort(filter, "payeeName", Direction.ASC, USAGE_ID_16, USAGE_ID_15);
        verifyFindForAuditSort(filter, "payeeName", Direction.DESC, USAGE_ID_15, USAGE_ID_16);
        verifyFindForAuditSort(filter, "checkNumber", Direction.ASC, USAGE_ID_15, USAGE_ID_16);
        verifyFindForAuditSort(filter, "checkNumber", Direction.DESC, USAGE_ID_16, USAGE_ID_15);
        verifyFindForAuditSort(filter, "checkDate", Direction.ASC, USAGE_ID_15, USAGE_ID_16);
        verifyFindForAuditSort(filter, "checkDate", Direction.DESC, USAGE_ID_16, USAGE_ID_15);
        verifyFindForAuditSort(filter, "cccEventId", Direction.ASC, USAGE_ID_15, USAGE_ID_16);
        verifyFindForAuditSort(filter, "cccEventId", Direction.DESC, USAGE_ID_16, USAGE_ID_15);
        verifyFindForAuditSort(filter, "distributionName", Direction.ASC, USAGE_ID_16, USAGE_ID_15);
        verifyFindForAuditSort(filter, "distributionName", Direction.DESC, USAGE_ID_15, USAGE_ID_16);
        verifyFindForAuditSort(filter, "distributionDate", Direction.ASC, USAGE_ID_15, USAGE_ID_16);
        verifyFindForAuditSort(filter, "distributionDate", Direction.DESC, USAGE_ID_16, USAGE_ID_15);
        verifyFindForAuditSort(filter, "periodEndDate", Direction.ASC, USAGE_ID_15, USAGE_ID_16);
        verifyFindForAuditSort(filter, "periodEndDate", Direction.DESC, USAGE_ID_16, USAGE_ID_15);
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-by-statuses.groovy")
    public void testFindByStatuses() {
        List<Usage> usages =
            usageRepository.findByStatuses(UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.SENT_FOR_RA);
        assertEquals(1, CollectionUtils.size(usages));
        Usage usageSentForRa = usages.get(0);
        assertEquals(USAGE_ID_6, usageSentForRa.getId());
        assertEquals(UsageStatusEnum.SENT_FOR_RA, usageSentForRa.getStatus());
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-update-status.groovy")
    public void testUpdateStatusWithUsageIds() {
        List<Usage> usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_4, USAGE_ID_6));
        assertEquals(2, CollectionUtils.size(usages));
        Usage usage1 = usages.get(0);
        assertEquals(UsageStatusEnum.SENT_FOR_RA, usage1.getStatus());
        assertEquals(USER_NAME, usage1.getUpdateUser());
        Usage usage2 = usages.get(1);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage2.getStatus());
        assertEquals(USER_NAME, usage2.getUpdateUser());
        usageRepository.updateStatus(ImmutableSet.of(usage1.getId(), usage2.getId()), UsageStatusEnum.RH_NOT_FOUND);
        usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_4, USAGE_ID_6));
        assertEquals(2, CollectionUtils.size(usages));
        usage1 = usages.get(0);
        assertEquals(UsageStatusEnum.RH_NOT_FOUND, usage1.getStatus());
        assertEquals(StoredEntity.DEFAULT_USER, usage1.getUpdateUser());
        usage2 = usages.get(1);
        assertEquals(UsageStatusEnum.RH_NOT_FOUND, usage2.getStatus());
        assertEquals(StoredEntity.DEFAULT_USER, usage2.getUpdateUser());
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-update-status.groovy")
    public void testUpdateStatusAndRhAccountNumber() {
        List<Usage> usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_4, USAGE_ID_6));
        assertEquals(2, CollectionUtils.size(usages));
        Usage usage1 = usages.get(0);
        assertEquals(UsageStatusEnum.SENT_FOR_RA, usage1.getStatus());
        assertNull(usage1.getRightsholder().getAccountNumber());
        Usage usage2 = usages.get(1);
        assertEquals(UsageStatusEnum.WORK_FOUND, usage2.getStatus());
        assertNull(usage2.getRightsholder().getAccountNumber());
        usageRepository.updateStatusAndRhAccountNumber(ImmutableSet.of(usage1.getId(), usage2.getId()),
            UsageStatusEnum.ELIGIBLE, RH_ACCOUNT_NUMBER);
        usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_4, USAGE_ID_6));
        assertEquals(2, CollectionUtils.size(usages));
        usage1 = usages.get(0);
        assertEquals(UsageStatusEnum.ELIGIBLE, usage1.getStatus());
        assertEquals(RH_ACCOUNT_NUMBER, usage1.getRightsholder().getAccountNumber());
        usage2 = usages.get(1);
        assertEquals(UsageStatusEnum.ELIGIBLE, usage2.getStatus());
        assertEquals(RH_ACCOUNT_NUMBER, usage2.getRightsholder().getAccountNumber());
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-update.groovy")
    public void testUpdate() {
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_8));
        assertEquals(1, CollectionUtils.size(usages));
        Usage usage = usages.get(0);
        verifyUsageAmountsAccountNumberAndParticipating(1000002859L, new BigDecimal("16437.4000000000"),
            new BigDecimal("11177.4000000000"), new BigDecimal("5260.0000000000"), SERVICE_FEE, false, false, usage);
        usage.getRightsholder().setAccountNumber(1000000001L);
        usage.setServiceFee(new BigDecimal("0.16000"));
        usage.setNetAmount(new BigDecimal("13807.4000000000"));
        usage.setServiceFeeAmount(new BigDecimal("2630.0000000000"));
        usage.setRhParticipating(true);
        usage.setPayeeParticipating(true);
        usageRepository.update(Collections.singletonList(usage));
        usages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_8));
        assertEquals(1, CollectionUtils.size(usages));
        verifyUsageAmountsAccountNumberAndParticipating(1000000001L, new BigDecimal("16437.4000000000"),
            new BigDecimal("13807.4000000000"), new BigDecimal("2630.0000000000"), new BigDecimal("0.16000"), true,
            true, usages.get(0));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-is-valid-filtered-usage-status.groovy")
    public void testIsValidFilteredUsageStatus() {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("ee575916-f6d0-4c3c-b589-32663e0f4793"));
        assertFalse(usageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.WORK_NOT_FOUND));
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        assertTrue(usageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.WORK_NOT_FOUND));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-update-processed-usage.groovy")
    public void testUpdateProcessedUsage() {
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_24));
        assertEquals(1, CollectionUtils.size(usages));
        Usage usage = usages.get(0);
        usage.setStatus(UsageStatusEnum.RH_FOUND);
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.setProductFamily(FAS_PRODUCT_FAMILY);
        usage.setWrWrkInst(5697789789L);
        usage.setWorkTitle("Wissenschaft & Forschung Italy");
        usage.setSystemTitle("Wissenschaft & Forschung France");
        usage.setStandardNumberType("VALISBN10");
        usage.setStandardNumber(STANDARD_NUMBER);
        assertNotNull(usageRepository.updateProcessedUsage(usage));
        List<Usage> updatedUsages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_24));
        assertEquals(1, CollectionUtils.size(updatedUsages));
        Usage updatedUsage = updatedUsages.get(0);
        assertEquals(RH_ACCOUNT_NUMBER, updatedUsage.getRightsholder().getAccountNumber());
        assertEquals(UsageStatusEnum.RH_FOUND, updatedUsage.getStatus());
        assertEquals(FAS_PRODUCT_FAMILY, updatedUsage.getProductFamily());
        assertEquals("VALISBN10", updatedUsage.getStandardNumberType());
        assertEquals(STANDARD_NUMBER, updatedUsage.getStandardNumber());
        assertEquals("Wissenschaft & Forschung Italy", updatedUsage.getWorkTitle());
        assertEquals("Wissenschaft & Forschung France", updatedUsage.getSystemTitle());
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-find-works-to-usage-ids-by-batch-name.groovy")
    public void testFindWrWrkInstToUsageIdsByBatchNameAndUsageStatus() {
        Map<Long, Set<String>> wrWrkInstToUsageIdsMap =
            ImmutableMap.of(243904752L, Sets.newHashSet(USAGE_ID_7, USAGE_ID_8));
        assertEquals(wrWrkInstToUsageIdsMap,
            usageRepository.findWrWrkInstToUsageIdsByBatchNameAndUsageStatus("JAACC_11Dec16", UsageStatusEnum.LOCKED));
    }

    @Test
    @TestData(fileName = "usage-repository-test-data-init-update-rh-payee-product-family-holders-by-scenario-id.groovy")
    public void testFindRightsholderPayeeProductFamilyHoldersByScenarioIds() throws IOException {
        Comparator<RightsholderPayeeProductFamilyHolder> comparator = Comparator
            .comparing(RightsholderPayeeProductFamilyHolder::getProductFamily)
            .thenComparing(RightsholderPayeeProductFamilyHolder::getRightsholder,
                Comparator.comparing(Rightsholder::getAccountNumber))
            .thenComparing(RightsholderPayeeProductFamilyHolder::getPayee,
                Comparator.comparing(Rightsholder::getAccountNumber));
        Set<String> scenarioIds = new HashSet<>(
            Arrays.asList("05ebb365-fa0d-4329-8a47-0b49968c6b82", "642b8342-a322-4b3e-afbd-4446cb218841"));
        List<RightsholderPayeeProductFamilyHolder> actual =
            usageRepository.findRightsholderPayeeProductFamilyHoldersByScenarioIds(scenarioIds).stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        List<RightsholderPayeeProductFamilyHolder> expected =
            loadExpectedRightsholderPayeeProductFamilyHolders("json/rh_payee_product_family_holders.json");
        verifyRightsholderPayeeProductFamilyHolders(expected, actual);
    }

    private void verifyFindForAuditSort(AuditFilter filter, String property, Direction direction,
                                        String... expectedIds) {
        List<UsageDto> actualUsageDtos =
            usageRepository.findForAudit(filter, new Pageable(0, 10), new Sort(property, direction));
        verifyUsageDtosInExactOrder(actualUsageDtos, expectedIds);
    }

    private void verifyFindByFilterSort(UsageFilter filter, String property, Direction direction,
                                        String... expectedIds) {
        List<UsageDto> actualUsageDtos = usageRepository.findDtosByFilter(filter, null, new Sort(property, direction));
        verifyUsageDtosInExactOrder(actualUsageDtos, expectedIds);
    }

    private void verifyFindByScenarioIdAndRhSearch(String searchValue, int expectedSize) {
        assertEquals(expectedSize, usageRepository.findByScenarioIdAndRhAccountNumber(1000002859L, SCENARIO_ID,
            searchValue, null, null).size());
        assertEquals(expectedSize, usageRepository.findCountByScenarioIdAndRhAccountNumber(1000002859L,
            SCENARIO_ID, searchValue));
    }

    private void verifyUsage(Usage usage, UsageStatusEnum status, String scenarioId, String username,
                             Long payeeAccountNumber) {
        assertNotNull(usage);
        assertEquals(status, usage.getStatus());
        assertEquals(scenarioId, usage.getScenarioId());
        assertEquals(username, usage.getUpdateUser());
        assertEquals(payeeAccountNumber, usage.getPayee().getAccountNumber());
    }

    private void verifyFasUsage(Usage usage, UsageStatusEnum status, String scenarioId, String username,
                                Long payeeAccountNumber) {
        verifyUsage(usage, status, scenarioId, username, payeeAccountNumber);
        assertEquals(FAS_PRODUCT_FAMILY, usage.getProductFamily());
    }

    private void verifyUsageExcludedFromScenario(Usage usage, String productFamily, UsageStatusEnum status) {
        assertEquals(status, usage.getStatus());
        assertNull(usage.getScenarioId());
        assertNull(usage.getPayee().getAccountNumber());
        assertNull(usage.getServiceFee());
        assertEquals(DEFAULT_ZERO_AMOUNT, usage.getServiceFeeAmount());
        assertEquals(DEFAULT_ZERO_AMOUNT, usage.getNetAmount());
        assertFalse(usage.isRhParticipating());
        assertFalse(usage.isPayeeParticipating());
        assertEquals(USER_NAME, usage.getUpdateUser());
        assertEquals(productFamily, usage.getProductFamily());
    }

    private UsageFilter buildUsageFilter(Set<Long> accountNumbers, Set<String> usageBatchIds,
                                         String productFamily, UsageStatusEnum status,
                                         LocalDate paymentDate, Integer fiscalYear) {
        UsageFilter usageFilter = buildFilterWithStatuses(status);
        usageFilter.setRhAccountNumbers(accountNumbers);
        usageFilter.setUsageStatus(status);
        usageFilter.setUsageBatchesIds(usageBatchIds);
        usageFilter.setPaymentDate(paymentDate);
        usageFilter.setFiscalYear(fiscalYear);
        usageFilter.setProductFamily(productFamily);
        return usageFilter;
    }

    private UsageFilter buildFilterWithStatuses(UsageStatusEnum status) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(status);
        return usageFilter;
    }

    private List<Usage> loadExpectedUsages(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        return OBJECT_MAPPER.readValue(content, new TypeReference<List<Usage>>() {
        });
    }

    private List<UsageDto> loadExpectedUsageDtos(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        return OBJECT_MAPPER.readValue(content, new TypeReference<List<UsageDto>>() {
        });
    }

    private List<RightsholderPayeeProductFamilyHolder> loadExpectedRightsholderPayeeProductFamilyHolders(
        String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        return OBJECT_MAPPER.readValue(content, new TypeReference<List<RightsholderPayeeProductFamilyHolder>>() {
        });
    }

    private void verifyUsages(List<Usage> expectedUsages, List<Usage> actualUsages) {
        assertEquals(CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(index -> verifyFasUsage(expectedUsages.get(index), actualUsages.get(index)));
    }

    private void verifyUsageDtosForAudit(List<UsageDto> expectedUsages, List<UsageDto> actualUsages) {
        assertEquals(CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(index -> verifyUsageDtoForAudit(expectedUsages.get(index), actualUsages.get(index)));
    }

    private void verifyFasUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
        assertEquals(expectedUsage.getScenarioId(), actualUsage.getScenarioId());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getPublisher(), actualUsage.getPublisher());
        assertEquals(expectedUsage.getMarket(), actualUsage.getMarket());
        assertEquals(expectedUsage.getMarketPeriodTo(), actualUsage.getMarketPeriodTo());
        assertEquals(expectedUsage.getMarketPeriodFrom(), actualUsage.getMarketPeriodFrom());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
        assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
        assertEquals(expectedUsage.getRightsholder().getName(), actualUsage.getRightsholder().getName());
        assertEquals(expectedUsage.getRightsholder().getId(), actualUsage.getRightsholder().getId());
        assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
            actualUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
    }

    private void verifyRightsholderPayeeProductFamilyHolders(List<RightsholderPayeeProductFamilyHolder> expectedHolders,
                                                             List<RightsholderPayeeProductFamilyHolder> actualHolders) {
        assertEquals(CollectionUtils.size(expectedHolders), CollectionUtils.size(actualHolders));
        IntStream.range(0, expectedHolders.size())
            .forEach(index ->
                verifyRightsholderPayeeProductFamilyHolder(expectedHolders.get(index), actualHolders.get(index)));
    }

    private void verifyRightsholderPayeeProductFamilyHolder(RightsholderPayeeProductFamilyHolder expected,
                                                            RightsholderPayeeProductFamilyHolder actual) {
        assertEquals(expected.getRightsholder().getId(), actual.getRightsholder().getId());
        assertEquals(expected.getRightsholder().getAccountNumber(), actual.getRightsholder().getAccountNumber());
        assertEquals(expected.getRightsholder().getName(), actual.getRightsholder().getName());
        assertEquals(expected.getPayee().getId(), actual.getPayee().getId());
        assertEquals(expected.getPayee().getAccountNumber(), actual.getPayee().getAccountNumber());
        assertEquals(expected.getPayee().getName(), actual.getPayee().getName());
        assertEquals(expected.getProductFamily(), actual.getProductFamily());
    }

    private RightsholderTotalsHolder buildRightsholderTotalsHolder(String rhName, Long rhAccountNumber,
                                                                   String payeeName, Long payeeAccountNumber,
                                                                   Double grossTotal, Double serviceFeeTotal,
                                                                   Double netTotal) {
        RightsholderTotalsHolder rightsholderTotalsHolder = new RightsholderTotalsHolder();
        rightsholderTotalsHolder.getRightsholder().setAccountNumber(rhAccountNumber);
        rightsholderTotalsHolder.getRightsholder().setName(rhName);
        rightsholderTotalsHolder.getPayee().setAccountNumber(payeeAccountNumber);
        rightsholderTotalsHolder.getPayee().setName(payeeName);
        rightsholderTotalsHolder.setGrossTotal(BigDecimal.valueOf(grossTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setServiceFeeTotal(
            BigDecimal.valueOf(serviceFeeTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setNetTotal(BigDecimal.valueOf(netTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        rightsholderTotalsHolder.setServiceFee(SERVICE_FEE);
        return rightsholderTotalsHolder;
    }

    private void populateScenario() {
        List<Usage> usages = Lists.newArrayListWithExpectedSize(3);
        Usage usage = usageRepository.findByIds(Collections.singletonList(USAGE_ID_1)).get(0);
        usage.getPayee().setAccountNumber(1000009997L);
        usage.setScenarioId(SCENARIO_ID);
        usage.setServiceFee(SERVICE_FEE);
        calculateAmounts(usage);
        usages.add(usage);
        usage = usageRepository.findByIds(Collections.singletonList(USAGE_ID_2)).get(0);
        usage.getPayee().setAccountNumber(1000002859L);
        usage.setScenarioId(SCENARIO_ID);
        usage.setServiceFee(SERVICE_FEE);
        calculateAmounts(usage);
        usages.add(usage);
        usage = usageRepository.findByIds(Collections.singletonList(USAGE_ID_3)).get(0);
        usage.getPayee().setAccountNumber(7000813806L);
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

    private void verifyUsageDtoForAudit(UsageDto expectedUsage, UsageDto actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getBatchName(), actualUsage.getBatchName());
        assertEquals(expectedUsage.getPaymentDate(), actualUsage.getPaymentDate());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
        assertEquals(expectedUsage.getRhName(), actualUsage.getRhName());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getPayeeAccountNumber(), actualUsage.getPayeeAccountNumber());
        assertEquals(expectedUsage.getPayeeName(), actualUsage.getPayeeName());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
        assertEquals(expectedUsage.getBatchGrossAmount(), actualUsage.getBatchGrossAmount());
        assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getScenarioName(), actualUsage.getScenarioName());
        assertEquals(expectedUsage.getCheckNumber(), actualUsage.getCheckNumber());
        assertEquals(expectedUsage.getCheckDate(), actualUsage.getCheckDate());
        assertEquals(expectedUsage.getCccEventId(), actualUsage.getCccEventId());
        assertEquals(expectedUsage.getDistributionName(), actualUsage.getDistributionName());
        assertEquals(expectedUsage.getDistributionDate(), actualUsage.getDistributionDate());
        assertEquals(expectedUsage.getPeriodEndDate(), actualUsage.getPeriodEndDate());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertNotNull(expectedUsage.getUpdateDate());
    }

    private void verifyUsageDtos(List<UsageDto> usageDtos, String... usageIds) {
        assertNotNull(usageDtos);
        usageDtos.sort(Comparator.comparing(UsageDto::getId));
        Arrays.sort(usageIds);
        verifyUsageDtosInExactOrder(usageDtos, usageIds);
    }

    private void verifyUsageDtosInExactOrder(List<UsageDto> usageDtos, String... expectedIds) {
        assertNotNull(usageDtos);
        List<String> actualIds = usageDtos.stream()
            .map(UsageDto::getId)
            .collect(Collectors.toList());
        assertEquals(Arrays.asList(expectedIds), actualIds);
    }

    private void assertFindForAuditSearch(String searchValue, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(searchValue);
        assertEquals(usageIds.length, usageRepository.findCountForAudit(filter));
        verifyUsageDtos(usageRepository.findForAudit(filter, null, null), usageIds);
    }

    private void assertFindForAuditSearchByCccEventId(String cccEventId, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setCccEventId(cccEventId);
        assertEquals(usageIds.length, usageRepository.findCountForAudit(filter));
        verifyUsageDtos(usageRepository.findForAudit(filter, null, null), usageIds);
    }

    private void assertFindForAuditSearchByDistributionName(String distributionName, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setDistributionName(distributionName);
        assertEquals(usageIds.length, usageRepository.findCountForAudit(filter));
        verifyUsageDtos(usageRepository.findForAudit(filter, null, null), usageIds);
    }

    private void verifyUsageAmountsAccountNumberAndParticipating(long accountNumber, BigDecimal grossAmount,
                                                                 BigDecimal netAmount, BigDecimal serviceFeeAmount,
                                                                 BigDecimal serviceFee, boolean rhParticipating,
                                                                 boolean payeeParticipating, Usage usage) {
        assertEquals(accountNumber, usage.getRightsholder().getAccountNumber(), 0);
        assertEquals(grossAmount, usage.getGrossAmount());
        assertEquals(netAmount, usage.getNetAmount());
        assertEquals(serviceFeeAmount, usage.getServiceFeeAmount());
        assertEquals(serviceFee, usage.getServiceFee());
        assertEquals(rhParticipating, usage.isRhParticipating());
        assertEquals(payeeParticipating, usage.isPayeeParticipating());
    }

    private void verifyPayeeTotalsHolder(Long accountNumber, String name, BigDecimal grossTotal, BigDecimal netTotal,
                                         BigDecimal serviceFeeTotal, boolean payeeParticipating,
                                         PayeeTotalHolder holder) {
        assertEquals(accountNumber, holder.getPayee().getAccountNumber());
        assertEquals(name, holder.getPayee().getName());
        assertEquals(grossTotal, holder.getGrossTotal());
        assertEquals(netTotal, holder.getNetTotal());
        assertEquals(serviceFeeTotal, holder.getServiceFeeTotal());
        assertEquals(payeeParticipating, holder.isPayeeParticipating());
    }
}
