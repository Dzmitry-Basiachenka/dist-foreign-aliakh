package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.common.util.CalculationUtils;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static final String RH_ACCOUNT_NAME_1 = "IEEE - Inst of Electrical and Electronics Engrs";
    private static final String RH_ACCOUNT_NAME_2 = "John Wiley & Sons - Books";
    private static final String RH_ACCOUNT_NAME_3 = "Kluwer Academic Publishers - Dordrecht";
    private static final String WORK_TITLE_1 = "Wissenschaft & Forschung Japan";
    private static final String WORK_TITLE_2 = "100 ROAD MOVIES";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FAS2_PRODUCT_FAMILY = "FAS2";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String BUS_MARKET = "Bus";
    private static final String DOC_DEL_MARKET = "Doc Del";
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
    private static final String STANDARD_NUMBER_TYPE = "VALISBN13";
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
    private static final String USAGE_ID_18 = "721ca627-09bc-4204-99f4-6acae415fa5d";
    private static final String USAGE_ID_19 = "9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a";
    private static final String USAGE_ID_20 = "dcc794ba-42aa-481d-937b-8f431929a611";
    private static final String USAGE_ID_21 = "47d48889-76b5-4957-aca0-2a7850a09f92";
    private static final String USAGE_ID_22 = "c5ea47b0-b269-4791-9aa7-76308fe835e6";
    private static final String USAGE_ID_23 = "3b6892a9-49b2-41a2-aa3a-8705ea6640cc";
    private static final String USAGE_ID_24 = "3c31db4f-4065-4fe1-84c2-b48a0f3bc079";
    private static final String USAGE_ID_25 = "f6cb5b07-45c0-4188-9da3-920046eec4c0";
    private static final String USAGE_ID_26 = "f255188f-d582-4516-8c08-835cfe1d68c3";
    private static final String USAGE_ID_27 = "2f2ca785-a7d3-4a7f-abd9-2bad80ac71dd";
    private static final String USAGE_ID_28 = "cbd6768d-a424-476e-b502-a832d9dbe85e";
    private static final String USAGE_ID_29 = "d5e3c637-155a-4c05-999a-31a07e335491";
    private static final String USAGE_ID_30 = "e2834925-ede5-4796-a30b-05770a6f04be";
    private static final String USAGE_ID_31 = "3cf274c5-8eac-4d4a-96be-5921ae026840";
    private static final String USAGE_ID_32 = "f5eb98ce-ab59-44c8-9a50-1afea2b5ae15";
    private static final String USAGE_ID_33 = "45445974-5bee-477a-858b-e9e8c1a642b8";
    private static final String USAGE_ID_BELLETRISTIC = "bbbd64db-2668-499a-9d18-be8b3f87fbf5";
    private static final String USAGE_ID_UNCLASSIFIED = "6cad4cf2-6a19-4e5b-b4e0-f2f7a62ff91c";
    private static final String USAGE_ID_STM = "83a26087-a3b3-43ca-8b34-c66134fb6edf";
    private static final String NTS_USAGE_ID = "6dc54058-5566-4aa2-8cd4-d1a09805ae20";
    private static final String POST_DISTRIBUTION_USAGE_ID = "cce295c6-23cf-47b4-b00c-2e0e50cce169";
    private static final String USAGE_ID_34 = "ade68eac-0d79-4d23-861b-499a0c6e91d3";
    private static final String USAGE_ID_35 = "5d422f76-7d20-4e04-bdd2-810ca930a50d";
    private static final String USAGE_ID_36 = "6ca311bc-394e-4cd2-9e39-c944d9206ea2";
    private static final String SCENARIO_ID = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";
    private static final String NTS_BATCH_ID = "b9d0ea49-9e38-4bb0-a7e0-0ca299e3dcfa";
    private static final String NTS_SCENARIO_ID = "ca163655-8978-4a45-8fe3-c3b5572c6879";
    private static final String USER_NAME = "user@copyright.com";
    private static final String BATCH_ID = "e0af666b-cbb7-4054-9906-12daa1fbd76e";
    private static final String PERCENT = "%";
    private static final String UNDERSCORE = "_";
    private static final BigDecimal SERVICE_FEE = new BigDecimal("0.32000");
    private static final BigDecimal ZERO_AMOUNT = new BigDecimal("0.00");
    private static final BigDecimal DEFAULT_ZERO_AMOUNT = new BigDecimal("0.0000000000");
    private static final BigDecimal HUNDRED_AMOUNT = new BigDecimal("100.00");
    private static final BigDecimal STM_MIN_AMOUNT = new BigDecimal("50.00");
    private static final BigDecimal NON_STM_MIN_AMOUNT = new BigDecimal("7.00");

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private UsageRepository usageRepository;

    @Test
    public void testInsert() throws IOException {
        Usage expectedUsage = buildUsage();
        expectedUsage.setNetAmount(expectedUsage.getNetAmount().setScale(10, RoundingMode.HALF_UP));
        usageRepository.insert(expectedUsage);
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(expectedUsage.getId()));
        assertEquals(1, CollectionUtils.size(usages));
        verifyFasUsage(expectedUsage, usages.get(0));
    }

    @Test
    public void testInsertAaclUsages() throws IOException {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily("AACL");
        assertEquals(2, usageRepository.findDtosByFilter(filter, null, null).size());
        Usage expectedUsage = buildAaclUsage();
        usageRepository.insertAaclUsage(expectedUsage);
        assertEquals(3, usageRepository.findDtosByFilter(filter, null, null).size());
    }

    @Test
    public void testFindCountByFilter() {
        assertEquals(1, usageRepository.findCountByFilter(
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                FAS_PRODUCT_FAMILY, UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR)));
    }

    @Test
    public void testFindDtosByFilter() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                FAS_PRODUCT_FAMILY, UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID_1);
    }

    @Test
    public void testFindIdsByStatusAnsProductFamily() {
        List<String> actualUsageIds =
            usageRepository.findIdsByStatusAndProductFamily(UsageStatusEnum.US_TAX_COUNTRY, NTS_PRODUCT_FAMILY);
        assertEquals(Arrays.asList("463e2239-1a36-41cc-9a51-ee2a80eae0c7", "bd407b50-6101-4304-8316-6404fe32a800"),
            actualUsageIds);
    }

    @Test
    public void testFindByStatusAnsProductFamily() throws IOException {
        List<Usage> actualUsages =
            usageRepository.findByStatusAndProductFamily(UsageStatusEnum.US_TAX_COUNTRY, NTS_PRODUCT_FAMILY);
        verifyUsages(loadExpectedUsages("json/usages_find_by_status.json"), actualUsages);
    }

    @Test
    public void testFindDtosByUsageBatchFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1),
            null, null, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID_1);
    }

    @Test
    public void testFindDtosByRhAccountNumberFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.emptySet(),
            null, null, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID_1);
    }

    @Test
    public void testFindDtosByProductFamilyFasFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            FAS_PRODUCT_FAMILY, null, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID_14, USAGE_ID_27, USAGE_ID_1, USAGE_ID_23,
            USAGE_ID_21, USAGE_ID_12, USAGE_ID_3, USAGE_ID_6, USAGE_ID_13, USAGE_ID_18, USAGE_ID_11, USAGE_ID_2,
            USAGE_ID_19, USAGE_ID_17, USAGE_ID_22, USAGE_ID_28, USAGE_ID_29, USAGE_ID_4, USAGE_ID_20, USAGE_ID_30);
    }

    @Test
    public void testFindDtosByProductFamilyFas2Filter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            FAS2_PRODUCT_FAMILY, null, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), USAGE_ID_24);
    }

    @Test
    public void testFindDtosByProductFamilyNtsFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            NTS_PRODUCT_FAMILY, null, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)), "0f86df24-39a0-4420-8e9b-327713ddd2b9",
            "2255188f-d582-4516-8c08-835cfe1d68c2", "3adb01b0-6dc0-4f3c-ba71-c47a1f8d69b8",
            "3cf274c5-8eac-4d4a-96be-5921ae026840", "45445974-5bee-477a-858b-e9e8c1a642b8",
            "463e2239-1a36-41cc-9a51-ee2a80eae0c7", "4dd8cdf8-ca10-422e-bdd5-3220105e6379",
            "6dc54058-5566-4aa2-8cd4-d1a09805ae20", "775ceaf9-125f-4387-b076-459eb4673d92",
            "a86308b1-7f89-474b-9390-fc926c5b218b", USAGE_ID_34,
            "af1f25e5-75ca-463f-8c9f-1f1e4b92f699", "ba95f0b3-dc94-4925-96f2-93d05db9c469",
            "bd407b50-6101-4304-8316-6404fe32a800", "c6cb5b07-45c0-4188-9da3-920046eec4cf",
            "f255188f-d582-4516-8c08-835cfe1d68c3", "f5eb98ce-ab59-44c8-9a50-1afea2b5ae15",
            "f6cb5b07-45c0-4188-9da3-920046eec4c0", "f9ddb072-a411-443b-89ca-1bb5a63425a4");
    }

    @Test
    public void testFindDtosByProductFamilyAaclFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            AACL_PRODUCT_FAMILY, null, null, null);
        usageFilter.setUsagePeriod(2019);
        List<UsageDto> usageDtos = usageRepository.findDtosByFilter(usageFilter, null,
            new Sort(DETAIL_ID_KEY, Direction.ASC));
        assertEquals(1, usageDtos.size());
        assertEquals(LocalDate.of(2019, 2, 13), usageDtos.get(0).getAaclUsage().getBatchPeriodEndDate());
        verifyUsageDtos(usageDtos, USAGE_ID_35);
    }

    @Test
    public void testFindDtosByStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            null, UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null, new Sort(DETAIL_ID_KEY,
            Sort.Direction.ASC)), USAGE_ID_1, USAGE_ID_3, NTS_USAGE_ID, USAGE_ID_2, USAGE_ID_26, USAGE_ID_25);
    }

    @Test
    public void testFindDtosByPaymentDateFilterSortByWorkTitle() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            null, null, PAYMENT_DATE, null);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null, new Sort(WORK_TITLE_KEY,
            Sort.Direction.ASC)), USAGE_ID_3, USAGE_ID_2, USAGE_ID_1);
    }

    @Test
    public void testFindDtosByFiscalYearFilterSortByArticle() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            null, null, null, FISCAL_YEAR);
        verifyUsageDtos(usageRepository.findDtosByFilter(usageFilter, null, new Sort("article", Sort.Direction.ASC)),
            USAGE_ID_3, USAGE_ID_1, USAGE_ID_2);
    }

    @Test
    public void testFindInvalidRightsholdersByFilter() throws IOException {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1),
                null, UsageStatusEnum.ELIGIBLE, null, null);
        assertTrue(CollectionUtils.isEmpty(usageRepository.findInvalidRightsholdersByFilter(usageFilter)));
        Usage usage = buildUsage();
        usage.getRightsholder().setAccountNumber(1000000003L);
        usageRepository.insert(usage);
        List<Long> accountNumbers = usageRepository.findInvalidRightsholdersByFilter(usageFilter);
        assertEquals(1, accountNumbers.size());
        assertEquals(1000000003L, accountNumbers.get(0), 0);
    }

    @Test
    public void testFindPayeeTotalHoldersByScenarioId() {
        List<PayeeTotalHolder> payeeTotalHolders =
            usageRepository.findPayeeTotalHoldersByScenarioId("e13ecc44-6795-4b75-90f0-4a3fc191f1b9");
        assertEquals(2, CollectionUtils.size(payeeTotalHolders));
        verifyPayeeTotalsHolder(7000813806L,
            "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil",
            new BigDecimal("100.0000000000"), new BigDecimal("68.0000000000"), new BigDecimal("32.0000000000"), true,
            payeeTotalHolders.get(0));
        verifyPayeeTotalsHolder(1000002859L,
            "John Wiley & Sons - Books", new BigDecimal("200.0000000000"), new BigDecimal("152.0000000000"),
            new BigDecimal("48.0000000000"), false, payeeTotalHolders.get(1));
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
    public void testFindCountByScenarioIdAndRhAccountNumberNullSearchValue() throws IOException {
        populateScenario();
        Usage usage = buildUsage();
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
        verifyFindByScenarioIdAndRhSearch(USAGE_ID_8, 1);
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
    public void testFindReferencedFasUsagesCountByIds() {
        assertEquals(2, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_31, USAGE_ID_2));
        assertEquals(1, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_32));
        assertEquals(1, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_31, "invalidId"));
    }

    @Test
    public void testDeleteByBatchId() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Sets.newHashSet(USAGE_BATCH_ID_1));
        Sort sort = new Sort(DETAIL_ID_KEY, Direction.ASC);
        List<UsageDto> usages = usageRepository.findDtosByFilter(filter, null, sort);
        assertEquals(1, usages.size());
        assertEquals(USAGE_ID_1, usages.get(0).getId());
        assertEquals(1, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_1));
        usageRepository.deleteByBatchId(USAGE_BATCH_ID_1);
        assertEquals(0, usageRepository.findDtosByFilter(filter, null, sort).size());
        assertEquals(0, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_1));
    }

    @Test
    public void testDeleteById() {
        List<Usage> usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_31, USAGE_ID_32));
        assertEquals(2, CollectionUtils.size(usages));
        assertEquals(1, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_31));
        assertEquals(1, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_32));
        usageRepository.deleteById(USAGE_ID_31);
        usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_31, USAGE_ID_32));
        assertEquals(1, CollectionUtils.size(usages));
        assertEquals(USAGE_ID_32, usages.get(0).getId());
        assertEquals(0, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_31));
        assertEquals(1, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_32));
    }

    @Test
    public void testDeleteFromAdditionalFund() {
        String fundPoolId = "3fef25b0-c0d1-4819-887f-4c6acc01390e";
        List<Usage> usages =
            usageRepository.findByIds(Collections.singletonList("ba95f0b3-dc94-4925-96f2-93d05db9c469"));
        assertEquals(1, CollectionUtils.size(usages));
        Usage usage = usages.get(0);
        assertEquals(UsageStatusEnum.TO_BE_DISTRIBUTED, usage.getStatus());
        assertEquals(fundPoolId, usage.getFundPoolId());
        usageRepository.deleteFromPreServiceFeeFund(fundPoolId, USER_NAME);
        usage =
            usageRepository.findByIds(Collections.singletonList("ba95f0b3-dc94-4925-96f2-93d05db9c469")).get(0);
        assertEquals(UsageStatusEnum.NTS_WITHDRAWN, usage.getStatus());
        assertNull(usage.getFundPoolId());
    }

    @Test
    public void testFindByScenarioId() {
        List<Usage> usages = usageRepository.findByScenarioId(SCENARIO_ID);
        assertEquals(2, usages.size());
        usages.forEach(
            usage -> verifyFasUsage(usage, UsageStatusEnum.LOCKED, SCENARIO_ID, StoredEntity.DEFAULT_USER,
                1000002859L));
    }

    @Test
    public void testFindForReconcile() {
        List<Usage> usages = usageRepository.findForReconcile(SCENARIO_ID);
        assertEquals(2, usages.size());
        usages.forEach(usage -> {
            assertEquals(243904752L, usage.getWrWrkInst(), 0);
            assertEquals(1000002859L, usage.getRightsholder().getAccountNumber(), 0);
            assertEquals("John Wiley & Sons - Books", usage.getRightsholder().getName());
            assertEquals(WORK_TITLE_2, usage.getWorkTitle());
            assertEquals(FAS_PRODUCT_FAMILY, usage.getProductFamily());
            assertNotNull(usage.getGrossAmount());
        });
    }

    @Test
    public void testFindRightsholdersInformation() {
        Map<Long, Usage> rhInfo = usageRepository.findRightsholdersInformation("ee8fc320-692c-4f7d-9981-54945e4ae127");
        assertEquals(1, rhInfo.size());
        Entry<Long, Usage> entry = rhInfo.entrySet().iterator().next();
        assertEquals(1000002859L, entry.getKey(), 0);
        assertEquals(1000002859L, entry.getValue().getPayee().getAccountNumber(), 0);
        assertTrue(entry.getValue().isRhParticipating());
        assertTrue(entry.getValue().isPayeeParticipating());
    }

    @Test
    public void testDeleteByScenarioId() {
        assertEquals(2, usageRepository.findByScenarioId(SCENARIO_ID).size());
        assertEquals(2, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_7, USAGE_ID_8));
        usageRepository.deleteByScenarioId(SCENARIO_ID);
        assertTrue(usageRepository.findByScenarioId(SCENARIO_ID).isEmpty());
        assertEquals(2, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_7, USAGE_ID_8));
    }

    @Test
    public void testDeleteByScenarioIdNtsExcluded() {
        assertEquals(2, usageRepository.findByStatuses(UsageStatusEnum.NTS_EXCLUDED).size());
        assertEquals(2,
            usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_34, USAGE_ID_33));
        usageRepository.deleteNtsByScenarioId(NTS_SCENARIO_ID);
        assertEquals(1, usageRepository.findByStatuses(UsageStatusEnum.NTS_EXCLUDED).size());
        assertEquals(1,
            usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_34, USAGE_ID_33));
    }

    @Test
    public void testDeleteBelletristicByScenarioId() {
        String scenarioId = "dd4fca1d-eac8-4b76-85e4-121b7971d049";
        verifyUsageIdsInScenario(Arrays.asList(USAGE_ID_BELLETRISTIC, USAGE_ID_STM, USAGE_ID_UNCLASSIFIED), scenarioId);
        assertTrue(CollectionUtils.isNotEmpty(
            usageRepository.findByIds(Collections.singletonList(USAGE_ID_BELLETRISTIC))));
        usageRepository.deleteBelletristicByScenarioId(scenarioId);
        verifyUsageIdsInScenario(Arrays.asList(USAGE_ID_STM, USAGE_ID_UNCLASSIFIED), scenarioId);
        assertTrue(CollectionUtils.isEmpty(
            usageRepository.findByIds(Collections.singletonList(USAGE_ID_BELLETRISTIC))));
        assertEquals(0, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_BELLETRISTIC));
    }

    @Test
    public void testFindByIds() {
        List<Usage> usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_1, USAGE_ID_2));
        assertEquals(2, CollectionUtils.size(usages));
        assertEquals(Arrays.asList(USAGE_ID_1, USAGE_ID_2),
            usages.stream().map(Usage::getId).collect(Collectors.toList()));
    }

    @Test
    public void testFindUsageIdsForClassificationUpdate() {
        List<String> actualUsageIds = usageRepository.findUsageIdsForClassificationUpdate();
        assertNotNull(actualUsageIds);
        assertEquals(2, actualUsageIds.size());
        assertEquals("c6cb5b07-45c0-4188-9da3-920046eec4cf", actualUsageIds.get(0));
        assertEquals("6dc54058-5566-4aa2-8cd4-d1a09805ae20", actualUsageIds.get(1));
    }

    @Test
    public void testFindByStatusAndWrWrkInsts() {
        assertEquals(2, usageRepository.findCountByStatusAndWrWrkInsts(UsageStatusEnum.UNCLASSIFIED,
            Sets.newHashSet(987632764L, 12318778798L)));
        assertEquals(0,
            usageRepository.findCountByStatusAndWrWrkInsts(UsageStatusEnum.UNCLASSIFIED, Collections.singleton(1L)));
    }

    @Test
    public void testFindWithAmountsAndRightsholders() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                FAS_PRODUCT_FAMILY, UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testVerifyFindWithAmountsAndRightsholders() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                FAS_PRODUCT_FAMILY, UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
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
            null, UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByRhAccountNumberFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.emptySet(),
            null, UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByProductFamiliesFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            FAS_PRODUCT_FAMILY, UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            null, UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 6, USAGE_ID_1,
            USAGE_ID_2, USAGE_ID_3, NTS_USAGE_ID, USAGE_ID_25, USAGE_ID_26);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByPaymentDateFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            null, UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, null);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByFiscalYearFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            null, UsageStatusEnum.ELIGIBLE, null, FISCAL_YEAR);
        verifyUsages(usageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
    }

    @Test
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
    public void testDeleteFromScenario() {
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_8));
        assertEquals(1, CollectionUtils.size(usages));
        verifyFasUsage(usages.get(0), UsageStatusEnum.LOCKED, SCENARIO_ID, StoredEntity.DEFAULT_USER, 1000002859L);
        usageRepository.deleteFromScenario(SCENARIO_ID, USER_NAME);
        verifyUsageExcludedFromScenario(usageRepository.findByIds(Collections.singletonList(USAGE_ID_8)).get(0),
            FAS_PRODUCT_FAMILY, UsageStatusEnum.ELIGIBLE);
    }

    @Test
    public void testDeleteFromNtsScenario() {
        List<Usage> usages = usageRepository.findByIds(
            Arrays.asList("c09aa888-85a5-4377-8c7a-85d84d255b5a", USAGE_ID_33));
        assertEquals(2, CollectionUtils.size(usages));
        BigDecimal reportedValue = new BigDecimal("900.00");
        verifyNtsUsage(usages.get(0), UsageStatusEnum.NTS_EXCLUDED, null, StoredEntity.DEFAULT_USER,
            DEFAULT_ZERO_AMOUNT, HUNDRED_AMOUNT, null, DEFAULT_ZERO_AMOUNT, DEFAULT_ZERO_AMOUNT);
        verifyNtsUsage(usages.get(1), UsageStatusEnum.LOCKED, NTS_SCENARIO_ID, StoredEntity.DEFAULT_USER,
            new BigDecimal("900.0000000000"), reportedValue, SERVICE_FEE, new BigDecimal("288.0000000000"),
            new BigDecimal("612.0000000000"));
        usageRepository.deleteFromNtsScenario(NTS_SCENARIO_ID, USER_NAME);
        usages = usageRepository.findByIds(
            Arrays.asList("c09aa888-85a5-4377-8c7a-85d84d255b5a", USAGE_ID_33));
        assertEquals(2, CollectionUtils.size(usages));
        verifyNtsUsage(usages.get(0), UsageStatusEnum.ELIGIBLE, null, USER_NAME, DEFAULT_ZERO_AMOUNT, HUNDRED_AMOUNT,
            null, DEFAULT_ZERO_AMOUNT, DEFAULT_ZERO_AMOUNT);
        verifyNtsUsage(usages.get(1), UsageStatusEnum.UNCLASSIFIED, null, USER_NAME, DEFAULT_ZERO_AMOUNT,
            reportedValue, null, DEFAULT_ZERO_AMOUNT, DEFAULT_ZERO_AMOUNT);
    }

    @Test
    public void testFindCountByDetailIdAndStatus() {
        assertEquals(0, usageRepository.findCountByUsageIdAndStatus(USAGE_ID_4, UsageStatusEnum.NEW));
        assertEquals(1, usageRepository.findCountByUsageIdAndStatus(USAGE_ID_1, UsageStatusEnum.ELIGIBLE));
        assertEquals(1, usageRepository.findCountByUsageIdAndStatus(USAGE_ID_5, UsageStatusEnum.SENT_TO_LM));
    }

    @Test
    public void testDeleteFromScenarioByPayees() {
        List<Usage> usagesBeforeExclude = usageRepository.findByIds(
            Arrays.asList("7234feb4-a59e-483b-985a-e8de2e3eb190", "582c86e2-213e-48ad-a885-f9ff49d48a69",
                "730d7964-f399-4971-9403-dbedc9d7a180"));
        assertEquals(3, CollectionUtils.size(usagesBeforeExclude));
        usagesBeforeExclude.forEach(usage ->
            assertEquals("edbcc8b3-8fa4-4c58-9244-a91627cac7a9", usage.getScenarioId()));
        Set<String> excludedIds = usageRepository.deleteFromScenarioByPayees("edbcc8b3-8fa4-4c58-9244-a91627cac7a9",
            Collections.singleton(7000813806L), USER_NAME);
        assertEquals(2, CollectionUtils.size(excludedIds));
        assertTrue(excludedIds.contains("730d7964-f399-4971-9403-dbedc9d7a180"));
        List<Usage> usages = usageRepository.findByIds(
            Arrays.asList("730d7964-f399-4971-9403-dbedc9d7a180", "582c86e2-213e-48ad-a885-f9ff49d48a69"));
        assertEquals(2, CollectionUtils.size(usages));
        usages.forEach(usage -> verifyUsageExcludedFromScenario(usage, "FAS2", UsageStatusEnum.ELIGIBLE));
        List<String> usageIds = usageRepository.findByScenarioId("edbcc8b3-8fa4-4c58-9244-a91627cac7a9")
            .stream()
            .map(Usage::getId)
            .collect(Collectors.toList());
        assertEquals(1, CollectionUtils.size(usageIds));
        assertTrue(usageIds.contains("7234feb4-a59e-483b-985a-e8de2e3eb190"));
    }

    @Test
    public void testRedisignateToNtsWithdrawnByPayees() {
        List<Usage> usagesBeforeExclude = usageRepository.findByIds(
            Arrays.asList("209a960f-5896-43da-b020-fc52981b9633", "1ae671ca-ed5a-4d92-8ab6-a10a53d9884a",
                "72f6abdb-c82d-4cee-aadf-570942cf0093"));
        assertEquals(3, CollectionUtils.size(usagesBeforeExclude));
        usagesBeforeExclude.forEach(usage ->
            assertEquals("767a2647-7e6e-4479-b381-e642de480863", usage.getScenarioId()));
        Set<String> excludedIds = usageRepository.redesignateToNtsWithdrawnByPayees(
            "767a2647-7e6e-4479-b381-e642de480863", Collections.singleton(7000813806L), USER_NAME);
        assertEquals(2, CollectionUtils.size(excludedIds));
        assertTrue(excludedIds.contains("72f6abdb-c82d-4cee-aadf-570942cf0093"));
        List<Usage> usages = usageRepository.findByIds(
            Arrays.asList("72f6abdb-c82d-4cee-aadf-570942cf0093", "1ae671ca-ed5a-4d92-8ab6-a10a53d9884a"));
        assertEquals(2, CollectionUtils.size(usages));
        usages.forEach(usage -> verifyUsageExcludedFromScenario(usage, "NTS", UsageStatusEnum.NTS_WITHDRAWN));
        List<String> usageIds = usageRepository.findByScenarioId("767a2647-7e6e-4479-b381-e642de480863")
            .stream()
            .map(Usage::getId)
            .collect(Collectors.toList());
        assertEquals(1, CollectionUtils.size(usageIds));
        assertTrue(usageIds.contains("209a960f-5896-43da-b020-fc52981b9633"));
    }

    @Test
    public void testDeleteFromScenarioByAccountNumbers() throws IOException {
        List<Usage> usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_8, USAGE_ID_7));
        assertEquals(2, CollectionUtils.size(usages));
        usages.forEach(usage -> assertEquals(SCENARIO_ID, usage.getScenarioId()));
        Usage usage = buildUsage();
        usageRepository.insert(usage);
        usageRepository.addToScenario(Collections.singletonList(usage));
        usageRepository.deleteFromScenario(Lists.newArrayList(USAGE_ID_8, USAGE_ID_7), USER_NAME);
        usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_8, USAGE_ID_7));
        assertEquals(2, CollectionUtils.size(usages));
        usages.forEach(usage1 -> verifyUsageExcludedFromScenario(usage1, FAS_PRODUCT_FAMILY, UsageStatusEnum.ELIGIBLE));
        usages = usageRepository.findByIds(Collections.singletonList(usage.getId()));
        assertEquals(SCENARIO_ID, usages.get(0).getScenarioId());
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumbers() throws IOException {
        Usage usage = buildUsage();
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
        assertEquals(ZERO_AMOUNT,
            usageRepository.getTotalAmountByStandardNumberAndBatchId("100 ROAD 5475802112214578XX", "invalid id"));
    }

    @Test
    public void testGetTotalAmountByTitleAndBatchId() {
        assertEquals(new BigDecimal("2000.00"),
            usageRepository.getTotalAmountByTitleAndBatchId("Wissenschaft & Forschung Japan",
                "9776da8d-098d-4f39-99fd-85405c339e9b"));
        assertEquals(ZERO_AMOUNT, usageRepository.getTotalAmountByTitleAndBatchId(WORK_TITLE_2,
            "cb597f4e-f636-447f-8710-0436d8994d10"));
        assertEquals(ZERO_AMOUNT, usageRepository.getTotalAmountByTitleAndBatchId(WORK_TITLE_2, "invalid id"));
    }

    @Test
    public void testUpdateNtsWithdrawnUsagesAndGetIds() {
        List<String> actualIds = usageRepository.updateNtsWithdrawnUsagesAndGetIds();
        assertEquals(3, actualIds.size());
        assertTrue(actualIds.containsAll(Arrays.asList(USAGE_ID_27, USAGE_ID_28, USAGE_ID_29)));
        usageRepository.findByIds(actualIds).forEach(usage -> {
            assertEquals(UsageStatusEnum.NTS_WITHDRAWN, usage.getStatus());
            assertEquals("NTS", usage.getProductFamily());
        });
    }

    @Test
    public void testFindForAuditFas() throws IOException {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(USAGE_ID_8);
        verifyUsageDtosForAudit(loadExpectedUsageDtos("json/usage_dto_find_for_audit_fas.json"),
            usageRepository.findForAudit(filter, null, null));
    }

    @Test
    public void testFindForAuditArchivedFas() throws IOException {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(USAGE_ID_15);
        verifyUsageDtosForAudit(loadExpectedUsageDtos("json/usage_dto_find_for_audit_archive_fas.json"),
            usageRepository.findForAudit(filter, null, null));
    }

    @Test
    public void testFindForAuditNts() throws IOException {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue("c09aa888-85a5-4377-8c7a-85d84d255b5a");
        verifyUsageDtosForAudit(loadExpectedUsageDtos("json/usage_dto_find_for_audit_nts.json"),
            usageRepository.findForAudit(filter, null, null));
    }

    @Test
    public void testFindForAuditArchivedNts() throws IOException {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue("fb297d5d-4d46-4492-93b0-cd6e02b8ce8d");
        verifyUsageDtosForAudit(loadExpectedUsageDtos("json/usage_dto_find_for_audit_archive_nts.json"),
            usageRepository.findForAudit(filter, null, null));
    }

    @Test
    public void testFindForAuditByStatus() {
        AuditFilter filter = new AuditFilter();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.SENT_TO_LM));
        assertEquals(1, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_5);
    }

    @Test
    public void testFindForAuditByBatch() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton(BATCH_ID));
        assertEquals(2, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_5, USAGE_ID_4);
    }

    @Test
    public void testFindForAuditByRightsholders() {
        AuditFilter filter = new AuditFilter();
        filter.setRhAccountNumbers(Collections.singleton(1000002475L));
        assertEquals(1, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages = usageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_5);
    }

    @Test
    public void testFindForAuditByProductFamilies() {
        AuditFilter filter = new AuditFilter();
        filter.setProductFamily(FAS_PRODUCT_FAMILY);
        assertEquals(32, usageRepository.findCountForAudit(filter));
        List<UsageDto> usages =
            usageRepository.findForAudit(filter, null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC));
        verifyUsageDtos(usages, USAGE_ID_14, USAGE_ID_15, USAGE_ID_16, USAGE_ID_27,
            "33113b79-791a-4aa9-b192-12b292c32823", USAGE_ID_1, USAGE_ID_23, USAGE_ID_21, USAGE_ID_12,
            "5b8c2754-2f63-425a-a95f-dbd744e815fc", USAGE_ID_3, USAGE_ID_6, USAGE_ID_13,
            USAGE_ID_18, USAGE_ID_11, USAGE_ID_2, USAGE_ID_19, USAGE_ID_5, "a9fac1e1-5a34-416b-9ecb-f2615b24d1c1",
            USAGE_ID_8, USAGE_ID_17, "b6fc6063-a0ea-4e4d-832d-b1cbc896963d", "bc0fe9bc-9b24-4324-b624-eed0d9773e19",
            USAGE_ID_22, USAGE_ID_28, POST_DISTRIBUTION_USAGE_ID, USAGE_ID_7, USAGE_ID_29, USAGE_ID_4, USAGE_ID_20,
            USAGE_ID_30, "f06de87a-511e-46ae-88a8-fc9778efc194");
    }

    @Test
    public void testFindForAuditWithSearch() {
        assertFindForAuditSearch(USAGE_ID_5, USAGE_ID_5);
        assertFindForAuditSearch("Nitrates", USAGE_ID_4);
        assertFindForAuditSearch(USAGE_ID_4, USAGE_ID_4);
        assertFindForAuditSearch("Hydronitrous", USAGE_ID_4);
        assertFindForAuditSearch(POST_DISTRIBUTION_USAGE_ID, POST_DISTRIBUTION_USAGE_ID);
        assertFindForAuditSearch(PERCENT);
        assertFindForAuditSearch(UNDERSCORE);
    }

    @Test
    public void testFindForAuditSearchByCccEventId() {
        assertFindForAuditSearchByCccEventId("53256", USAGE_ID_15, POST_DISTRIBUTION_USAGE_ID);
        assertFindForAuditSearchByCccEventId("53257", USAGE_ID_16);
        assertFindForAuditSearchByCccEventId(PERCENT);
        assertFindForAuditSearchByCccEventId(UNDERSCORE);
    }

    @Test
    public void testFindForAuditSearchByDistributionName() {
        assertFindForAuditSearchByDistributionName("FDA July 17", USAGE_ID_16);
        assertFindForAuditSearchByDistributionName("FDA_March_17", USAGE_ID_15, POST_DISTRIBUTION_USAGE_ID);
        assertFindForAuditSearchByDistributionName(PERCENT);
        assertFindForAuditSearchByDistributionName(UNDERSCORE, USAGE_ID_15, POST_DISTRIBUTION_USAGE_ID);
    }

    @Test
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
    public void testFindByFilterSortingByWorkInfo() {
        UsageFilter filter = buildUsageFilter(Sets.newHashSet(2000017000L, 7000896777L), Collections.emptySet(),
            null, null, null, null);
        verifyFindByFilterSort(filter, WORK_TITLE_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, WORK_TITLE_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "article", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "article", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, STANDARD_NUMBER_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, STANDARD_NUMBER_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "standardNumberType", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "standardNumberType", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, WR_WRK_INST_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, WR_WRK_INST_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "systemTitle", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "systemTitle", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "publisher", Direction.ASC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "publisher", Direction.DESC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "publicationDate", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "publicationDate", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "numberOfCopies", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "numberOfCopies", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "market", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "market", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "marketPeriodFrom", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "marketPeriodFrom", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "marketPeriodTo", Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, "marketPeriodTo", Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "author", Direction.ASC, USAGE_ID_24, USAGE_ID_23);
        verifyFindByFilterSort(filter, "author", Direction.DESC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, COMMENT_KEY, Direction.ASC, USAGE_ID_23, USAGE_ID_24);
        verifyFindByFilterSort(filter, COMMENT_KEY, Direction.DESC, USAGE_ID_24, USAGE_ID_23);
        filter = buildUsageFilter(Sets.newHashSet(2000017000L, 7000896777L), Collections.emptySet(),
            AACL_PRODUCT_FAMILY, null, null, null);
        verifyFindByFilterSort(filter, "paymentDate", Direction.ASC, USAGE_ID_36, USAGE_ID_35);
        verifyFindByFilterSort(filter, "paymentDate", Direction.DESC, USAGE_ID_35, USAGE_ID_36);
        verifyFindByFilterSort(filter, "institution", Direction.ASC, USAGE_ID_36, USAGE_ID_35);
        verifyFindByFilterSort(filter, "institution", Direction.DESC, USAGE_ID_35, USAGE_ID_36);
        verifyFindByFilterSort(filter, "usagePeriod", Direction.ASC, USAGE_ID_36, USAGE_ID_35);
        verifyFindByFilterSort(filter, "usagePeriod", Direction.DESC, USAGE_ID_35, USAGE_ID_36);
        verifyFindByFilterSort(filter, "usageSource", Direction.ASC, USAGE_ID_36, USAGE_ID_35);
        verifyFindByFilterSort(filter, "usageSource", Direction.DESC, USAGE_ID_35, USAGE_ID_36);
        verifyFindByFilterSort(filter, "numberOfPages", Direction.ASC, USAGE_ID_36, USAGE_ID_35);
        verifyFindByFilterSort(filter, "numberOfPages", Direction.DESC, USAGE_ID_35, USAGE_ID_36);
        verifyFindByFilterSort(filter, "rightLimitation", Direction.ASC, USAGE_ID_36, USAGE_ID_35);
        verifyFindByFilterSort(filter, "rightLimitation", Direction.DESC, USAGE_ID_35, USAGE_ID_36);
    }

    @Test
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
    public void testFindByStatuses() {
        List<Usage> usages =
            usageRepository.findByStatuses(UsageStatusEnum.SENT_TO_LM, UsageStatusEnum.SENT_FOR_RA);
        assertEquals(1, CollectionUtils.size(usages));
        Usage usageSentForRa = usages.get(0);
        assertEquals(USAGE_ID_6, usageSentForRa.getId());
        assertEquals(UsageStatusEnum.SENT_FOR_RA, usageSentForRa.getStatus());
    }

    @Test
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
    public void testUpdateResearchedUsages() {
        String usageId1 = "721ca627-09bc-4204-99f4-6acae415fa5d";
        String usageId2 = "9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a";
        verifyFasUsage(usageId1, null, null, STANDARD_NUMBER, null, UsageStatusEnum.WORK_RESEARCH);
        verifyFasUsage(usageId2, null, null, null, null, UsageStatusEnum.WORK_RESEARCH);
        usageRepository.updateResearchedUsages(Arrays.asList(
            buildResearchedUsage(usageId1, "Technical Journal", 180382916L, STANDARD_NUMBER, "VALISSN"),
            buildResearchedUsage(usageId2, "Medical Journal", 854030733L, "2192-3566", STANDARD_NUMBER_TYPE)));
        verifyFasUsage(usageId1, "Technical Journal", 180382916L, STANDARD_NUMBER, "VALISSN",
            UsageStatusEnum.WORK_FOUND);
        verifyFasUsage(usageId2, "Medical Journal", 854030733L, "2192-3566", STANDARD_NUMBER_TYPE,
            UsageStatusEnum.WORK_FOUND);
    }

    @Test
    public void testIsValidUsagesState() {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("ee575916-f6d0-4c3c-b589-32663e0f4793"));
        assertFalse(usageRepository.isValidUsagesState(usageFilter, UsageStatusEnum.WORK_NOT_FOUND));
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        assertTrue(usageRepository.isValidUsagesState(usageFilter, UsageStatusEnum.WORK_NOT_FOUND));
    }

    @Test
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
    public void testUpdateProcessedAaclUsage() {
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_35));
        assertEquals(1, CollectionUtils.size(usages));
        Usage usage = usages.get(0);
        usage.setStatus(UsageStatusEnum.RH_FOUND);
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.setSystemTitle(WORK_TITLE_1);
        usage.setStandardNumberType(STANDARD_NUMBER_TYPE);
        usage.setStandardNumber(STANDARD_NUMBER);
        usage.getAaclUsage().setRightLimitation("ALL");
        assertNotNull(usageRepository.updateProcessedAaclUsage(usage));
        List<Usage> updatedUsages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_35));
        assertEquals(1, CollectionUtils.size(updatedUsages));
        Usage updatedUsage = updatedUsages.get(0);
        assertEquals(RH_ACCOUNT_NUMBER, updatedUsage.getRightsholder().getAccountNumber());
        assertEquals(UsageStatusEnum.RH_FOUND, updatedUsage.getStatus());
        assertEquals(STANDARD_NUMBER_TYPE, updatedUsage.getStandardNumberType());
        assertEquals(STANDARD_NUMBER, updatedUsage.getStandardNumber());
        assertEquals(WORK_TITLE_1, updatedUsage.getSystemTitle());
        assertEquals("ALL", updatedUsage.getAaclUsage().getRightLimitation());
    }

    @Test
    public void testInsertNtsUsages() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(NTS_BATCH_ID);
        usageBatch.setFundPool(buildNtsFundPool(HUNDRED_AMOUNT));
        List<String> insertedUsageIds = usageRepository.insertNtsUsages(usageBatch, USER_NAME);
        assertNotNull(insertedUsageIds);
        assertEquals(3, insertedUsageIds.size());
        List<Usage> insertedUsages = usageRepository.findByIds(insertedUsageIds);
        insertedUsages.sort(Comparator.comparing(Usage::getMarketPeriodFrom));
        verifyInsertedFundPoolUsage(243904752L, WORK_TITLE_2, DOC_DEL_MARKET, 2013, new BigDecimal("1176.92"),
            insertedUsages.get(0));
        verifyInsertedFundPoolUsage(105062654L, "Our fathers lies", BUS_MARKET, 2014, new BigDecimal("500.00"),
            insertedUsages.get(1));
        verifyInsertedFundPoolUsage(243904752L, WORK_TITLE_2, BUS_MARKET, 2016, new BigDecimal("500.00"),
            insertedUsages.get(2));
    }

    @Test
    public void testInsertNtsUsagesZeroFundPoolAmount() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(NTS_BATCH_ID);
        usageBatch.setFundPool(buildNtsFundPool(BigDecimal.ZERO));
        List<String> insertedUsageIds = usageRepository.insertNtsUsages(usageBatch, USER_NAME);
        assertNotNull(insertedUsageIds);
        assertEquals(1, insertedUsageIds.size());
        List<Usage> insertedUsages = usageRepository.findByIds(insertedUsageIds);
        insertedUsages.sort(Comparator.comparing(Usage::getMarketPeriodFrom));
        verifyInsertedFundPoolUsage(105062654L, "Our fathers lies", BUS_MARKET, 2014, new BigDecimal("500.00"),
            insertedUsages.get(0));
    }

    @Test
    public void testAddWithdrawnUsagesToPreServiceFeeFund() {
        List<String> usageIds = Collections.singletonList("4dd8cdf8-ca10-422e-bdd5-3220105e6379");
        List<Usage> usages = usageRepository.findByIds(usageIds);
        assertEquals(1, usages.size());
        Usage usage = usages.get(0);
        assertEquals(UsageStatusEnum.NTS_WITHDRAWN, usage.getStatus());
        assertNull(usage.getFundPoolId());
        String fundPoolId = "3fef25b0-c0d1-4819-887f-4c6acc01390e";
        Set<String> batchIds = Collections.singleton("cb597f4e-f636-447f-8710-0436d8994d10");
        usageRepository.addWithdrawnUsagesToPreServiceFeeFund(fundPoolId, batchIds, StoredEntity.DEFAULT_USER);
        usages = usageRepository.findByIds(usageIds);
        assertEquals(1, usages.size());
        usage = usages.get(0);
        assertEquals(UsageStatusEnum.TO_BE_DISTRIBUTED, usage.getStatus());
        assertEquals(fundPoolId, usage.getFundPoolId());
    }

    @Test
    public void testUpdateUsagesStatusToUnclassified() {
        ArrayList<String> usageIds = Lists.newArrayList(USAGE_ID_25, USAGE_ID_26);
        List<Usage> usages = usageRepository.findByIds(usageIds);
        assertEquals(2, usages.size());
        assertEquals(UsageStatusEnum.ELIGIBLE, usages.get(0).getStatus());
        assertEquals(UsageStatusEnum.ELIGIBLE, usages.get(1).getStatus());
        usageRepository.updateUsagesStatusToUnclassified(Lists.newArrayList(122267671L, 159526526L),
            StoredEntity.DEFAULT_USER);
        usages = usageRepository.findByIds(usageIds);
        assertEquals(2, usages.size());
        assertEquals(UsageStatusEnum.UNCLASSIFIED, usages.get(0).getStatus());
        assertEquals(UsageStatusEnum.UNCLASSIFIED, usages.get(1).getStatus());
    }

    @Test
    public void testFindWrWrkInstToUsageIdsByBatchNameAndUsageStatus() {
        Map<Long, Set<String>> wrWrkInstToUsageIdsMap =
            ImmutableMap.of(243904752L, Sets.newHashSet(USAGE_ID_7, USAGE_ID_8));
        assertEquals(wrWrkInstToUsageIdsMap,
            usageRepository.findWrWrkInstToUsageIdsByBatchNameAndUsageStatus("JAACC_11Dec16", UsageStatusEnum.LOCKED));
    }

    @Test
    public void testCalculateAmountsAndUpdatePayeeByAccountNumber() {
        assertNtsUsageAfterServiceFeeCalculation("8a80a2e7-4758-4e43-ae42-e8b29802a210",
            new BigDecimal("256.0000000000"), DEFAULT_ZERO_AMOUNT, null,
            DEFAULT_ZERO_AMOUNT, new BigDecimal("296.72"), null, false);
        assertNtsUsageAfterServiceFeeCalculation("bfc9e375-c489-4600-9308-daa101eed97c",
            new BigDecimal("145.2000000000"), DEFAULT_ZERO_AMOUNT, null,
            DEFAULT_ZERO_AMOUNT, new BigDecimal("16.24"), null, false);
        assertNtsUsageAfterServiceFeeCalculation("085268cd-7a0c-414e-8b28-2acb299d9698",
            new BigDecimal("1452.0000000000"), DEFAULT_ZERO_AMOUNT, null,
            DEFAULT_ZERO_AMOUNT, new BigDecimal("162.41"), null, false);
        usageRepository.calculateAmountsAndUpdatePayeeByAccountNumber(1000002859L,
            "d7e9bae8-6b10-4675-9668-8e3605a47dad", SERVICE_FEE, true, 243904752L, "SYSTEM");
        assertNtsUsageAfterServiceFeeCalculation("8a80a2e7-4758-4e43-ae42-e8b29802a210",
            new BigDecimal("256.0000000000"), new BigDecimal("174.0800000000"), SERVICE_FEE,
            new BigDecimal("81.9200000000"), new BigDecimal("296.72"), 243904752L, true);
        assertNtsUsageAfterServiceFeeCalculation("bfc9e375-c489-4600-9308-daa101eed97c",
            new BigDecimal("145.2000000000"), new BigDecimal("98.7360000000"), SERVICE_FEE,
            new BigDecimal("46.4640000000"), new BigDecimal("16.24"), 243904752L, true);
        assertNtsUsageAfterServiceFeeCalculation("085268cd-7a0c-414e-8b28-2acb299d9698",
            new BigDecimal("1452.0000000000"), DEFAULT_ZERO_AMOUNT, null, DEFAULT_ZERO_AMOUNT,
            new BigDecimal("162.41"), null, false);
    }

    @Test
    public void testApplyPostServiceFeeAmount() {
        // Post Service Fee Amount = 100
        usageRepository.applyPostServiceFeeAmount("c4bc09c1-eb9b-41f3-ac93-9cd088dff408");
        assertNtsUsageAmounts("7778a37d-6184-42c1-8e23-5841837c5411", new BigDecimal("71.1818181818"),
            new BigDecimal("65.9018181818"), new BigDecimal("0.16000"), new BigDecimal("5.2800000000"),
            new BigDecimal("33.00"));
        assertNtsUsageAmounts("54247c55-bf6b-4ad6-9369-fb4baea6b19b", new BigDecimal("127.8181818182"),
            new BigDecimal("106.6981818182"), SERVICE_FEE, new BigDecimal("21.1200000000"), new BigDecimal("66.00"));
        assertNtsUsageAmounts(USAGE_ID_34, new BigDecimal("11.0000000000"),
            DEFAULT_ZERO_AMOUNT, null, DEFAULT_ZERO_AMOUNT, new BigDecimal("11.00"));
    }

    @Test
    public void testFindAaclUsagePeriods() {
        List<Integer> usagePeriods = usageRepository.findAaclUsagePeriods();
        assertEquals(2, usagePeriods.size());
        assertEquals(2018, usagePeriods.get(0).longValue());
        assertEquals(2019, usagePeriods.get(1).longValue());
    }

    private void assertNtsUsageAmounts(String usageId, BigDecimal grossAmount, BigDecimal netAmount,
                                       BigDecimal serviceFee, BigDecimal serviceFeeAmount, BigDecimal reportedValue) {
        Usage usage = usageRepository.findByIds(Collections.singletonList(usageId)).get(0);
        assertAmounts(usage, grossAmount, netAmount, serviceFee, serviceFeeAmount, reportedValue);
    }

    private void assertNtsUsageAfterServiceFeeCalculation(String usageId, BigDecimal grossAmount, BigDecimal netAmount,
                                                          BigDecimal serviceFee, BigDecimal serviceFeeAmount,
                                                          BigDecimal reportedValue, Long payeeAccountNumber,
                                                          boolean rhParticipating) {
        Usage usage = usageRepository.findByIds(Collections.singletonList(usageId)).get(0);
        assertEquals(rhParticipating, usage.isRhParticipating());
        assertEquals(payeeAccountNumber, usage.getPayee().getAccountNumber());
        assertAmounts(usage, grossAmount, netAmount, serviceFee, serviceFeeAmount, reportedValue);
    }

    private void assertAmounts(Usage usage, BigDecimal grossAmount, BigDecimal netAmount, BigDecimal serviceFee,
                               BigDecimal serviceFeeAmount, BigDecimal reportedValue) {
        assertEquals(reportedValue, usage.getReportedValue());
        assertEquals(grossAmount, usage.getGrossAmount());
        assertEquals(netAmount, usage.getNetAmount());
        assertEquals(serviceFee, usage.getServiceFee());
        assertEquals(serviceFeeAmount, usage.getServiceFeeAmount());
    }

    private FundPool buildNtsFundPool(BigDecimal nonStmAmount) {
        FundPool fundPool = new FundPool();
        fundPool.setMarkets(Sets.newHashSet(BUS_MARKET, DOC_DEL_MARKET));
        fundPool.setFundPoolPeriodFrom(2015);
        fundPool.setFundPoolPeriodTo(2016);
        fundPool.setStmAmount(HUNDRED_AMOUNT);
        fundPool.setStmMinimumAmount(STM_MIN_AMOUNT);
        fundPool.setNonStmAmount(nonStmAmount);
        fundPool.setNonStmMinimumAmount(NON_STM_MIN_AMOUNT);
        return fundPool;
    }

    private ResearchedUsage buildResearchedUsage(String id, String title, Long wrWrkInst, String standardNumber,
                                                 String standardNumberType) {
        ResearchedUsage researchedUsage = new ResearchedUsage();
        researchedUsage.setUsageId(id);
        researchedUsage.setSystemTitle(title);
        researchedUsage.setWrWrkInst(wrWrkInst);
        researchedUsage.setStandardNumber(standardNumber);
        researchedUsage.setStandardNumberType(standardNumberType);
        return researchedUsage;
    }

    private void verifyFasUsage(String usageId, String title, Long wrWrkInst, String standardNumber,
                                String standardNumberType, UsageStatusEnum status) {
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(usageId));
        assertEquals(1, CollectionUtils.size(usages));
        Usage usage = usages.get(0);
        assertEquals(status, usage.getStatus());
        assertEquals(WORK_TITLE_1, usage.getWorkTitle());
        assertEquals(title, usage.getSystemTitle());
        assertEquals(wrWrkInst, usage.getWrWrkInst());
        assertEquals(standardNumber, usage.getStandardNumber());
        assertEquals(standardNumberType, usage.getStandardNumberType());
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

    private void verifyNtsUsage(Usage usage, UsageStatusEnum status, String scenarioId, String username,
                                BigDecimal grossAmount, BigDecimal reportedValue, BigDecimal serviceFee,
                                BigDecimal serviceFeeAmount, BigDecimal netAmount) {
        verifyUsage(usage, status, scenarioId, username, null);
        assertEquals(NTS_PRODUCT_FAMILY, usage.getProductFamily());
        assertAmounts(usage, grossAmount, netAmount, serviceFee, serviceFeeAmount, reportedValue);
    }

    private void verifyInsertedFundPoolUsage(Long wrWrkInst, String workTitle, String market, Integer marketPeriodFrom,
                                             BigDecimal reportedValue, Usage actualUsage) {
        assertEquals(actualUsage.getBatchId(), NTS_BATCH_ID);
        assertEquals(wrWrkInst, actualUsage.getWrWrkInst(), 0);
        assertEquals(workTitle, actualUsage.getWorkTitle());
        assertEquals(workTitle, actualUsage.getSystemTitle());
        assertEquals(UsageStatusEnum.WORK_FOUND, actualUsage.getStatus());
        assertEquals(NTS_PRODUCT_FAMILY, actualUsage.getProductFamily());
        assertEquals("1008902112317555XX", actualUsage.getStandardNumber());
        assertEquals(STANDARD_NUMBER_TYPE, actualUsage.getStandardNumberType());
        assertEquals(market, actualUsage.getMarket());
        assertEquals(marketPeriodFrom, actualUsage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2017), actualUsage.getMarketPeriodTo());
        assertEquals(DEFAULT_ZERO_AMOUNT, actualUsage.getGrossAmount());
        assertEquals(reportedValue, actualUsage.getReportedValue());
        assertEquals("for nts batch", actualUsage.getComment());
        assertEquals(USER_NAME, actualUsage.getCreateUser());
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

    private Usage buildUsage() throws IOException {
        List<Usage> usages = loadExpectedUsages("json/usage.json");
        assertEquals(1, CollectionUtils.size(usages));
        return usages.get(0);
    }

    private Usage buildAaclUsage() throws IOException {
        List<Usage> usages = loadExpectedUsages("json/aacl_usage.json");
        assertEquals(1, CollectionUtils.size(usages));
        return usages.get(0);
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

    private void verifyUsages(List<Usage> usages, int count, String... usageIds) {
        assertNotNull(usages);
        assertEquals(count, usages.size());
        IntStream.range(0, count).forEach(i -> {
            assertEquals(usageIds[i], usages.get(i).getId());
            assertEquals(UsageStatusEnum.ELIGIBLE, usages.get(i).getStatus());
        });
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

    private void verifyUsageIdsInScenario(List<String> expectedUsageIds, String scenarioId) {
        List<Usage> actualUsages = usageRepository.findByScenarioId(scenarioId);
        assertEquals(CollectionUtils.size(expectedUsageIds), CollectionUtils.size(actualUsages));
        List<String> usagesIdsBeforeDeletion = actualUsages.stream()
            .map(Usage::getId)
            .collect(Collectors.toList());
        assertTrue(CollectionUtils.containsAll(usagesIdsBeforeDeletion, expectedUsageIds));
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
