package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Integration test for {@link UsageArchiveRepository}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/08/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usage-archive-repository-test-data-init.groovy"})
@Transactional
public class UsageArchiveRepositoryIntegrationTest {

    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final String RH_ACCOUNT_NAME_1 = "IEEE - Inst of Electrical and Electronics Engrs";
    private static final String RH_ACCOUNT_NAME_2 = "John Wiley & Sons - Books";
    private static final String RH_ACCOUNT_NAME_3 = "Kluwer Academic Publishers - Dordrecht";
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("54.4400000000");
    private static final Long WR_WRK_INST = 123456783L;
    private static final String WORK_TITLE = "Work Title";
    private static final String SYSTEM_TITLE = "System Title";
    private static final String ARTICLE = "Article";
    private static final String STANDARD_NUMBER = "StandardNumber";
    private static final String STANDARD_NUMBER_TYPE = "VALISBN13";
    private static final String PUBLISHER = "Publisher";
    private static final String MARKET = "Market";
    private static final String USER = "user@copyright.com";
    private static final Integer MARKED_PERIOD_FROM = 2015;
    private static final Integer MARKED_PERIOD_TO = 2017;
    private static final String AUTHOR = "Author";
    private static final BigDecimal REPORTED_VALUE = new BigDecimal("11.25");
    private static final LocalDate PUBLICATION_DATE = LocalDate.of(2016, 11, 3);
    private static final OffsetDateTime PAID_DATE =
        LocalDate.of(2016, 11, 3).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime();
    private static final Integer NUMBER_OF_COPIES = 155;
    private static final String SCENARIO_ID = "b1f0b236-3ae9-4a60-9fab-61db84199d6f";
    private static final String FAS_SCENARIO_ID = "5f7c87e7-34d9-4548-8b85-97e405235f4a";
    private static final String NTS_SCENARIO_ID = "e65833c8-3a40-47ba-98fe-21aba07ef11e";
    private static final String PAID_USAGE_ID = "3f8ce825-6514-4307-a118-3ec89187bef3";
    private static final String ARCHIVED_USAGE_ID = "5f90f7d7-566f-402a-975b-d54466862704";
    private static final String LM_DETAIL_ID = "5963a9c2-b639-468c-a4c1-02101a4597c6";
    private static final String AACL_SCENARIO_ID = "4f1714a1-5e23-4e46-aeb1-b44fbeea17e6";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IUsageRepository usageRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(UsageArchiveRepositoryIntegrationTest.class);
    }

    @Test
    public void testDeleteByBatchId() {
        assertTrue(CollectionUtils.isNotEmpty(
            usageArchiveRepository.findByIdAndStatus(Collections.singletonList(ARCHIVED_USAGE_ID),
                UsageStatusEnum.ARCHIVED)));
        usageArchiveRepository.deleteByBatchId("f043c988-3344-4c0f-bce9-120af0027d09");
        assertTrue(CollectionUtils.isEmpty(
            usageArchiveRepository.findByIdAndStatus(Collections.singletonList(ARCHIVED_USAGE_ID),
                UsageStatusEnum.ARCHIVED)));
        assertEquals(0, usageRepository.findReferencedUsagesCountByIds(ARCHIVED_USAGE_ID));
    }

    @Test
    public void testDeleteByIds() {
        List<String> usageIds = Arrays.asList(ARCHIVED_USAGE_ID, "a9fac1e1-5a34-416b-9ecb-f2615b24d1c1");
        assertTrue(
            CollectionUtils.isNotEmpty(usageArchiveRepository.findByIdAndStatus(usageIds, UsageStatusEnum.ARCHIVED)));
        usageArchiveRepository.deleteByIds(usageIds);
        assertTrue(
            CollectionUtils.isEmpty(usageArchiveRepository.findByIdAndStatus(usageIds, UsageStatusEnum.ARCHIVED)));
        assertEquals(0, usageRepository.findReferencedUsagesCountByIds(ARCHIVED_USAGE_ID,
            "a9fac1e1-5a34-416b-9ecb-f2615b24d1c1"));
    }

    @Test
    public void testFindRightsholderTotalsHoldersByScenarioIdEmptySearchValue() {
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null,
                null);
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
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "JoHn", null, null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_2, 1000002859L, 67874.80, 21720.00, 46154.80),
            rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "IEEE", null, null);
        assertEquals(1, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(0));
        rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "ec", null, null);
        assertEquals(2, rightsholderTotalsHolders.size());
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_1, 1000009997L, 35000.00, 11200.00, 23800.00),
            rightsholderTotalsHolders.get(0));
        assertEquals(buildRightsholderTotalsHolder(RH_ACCOUNT_NAME_3, 1000005413L, 2125.24, 680.0768, 1445.1632),
            rightsholderTotalsHolders.get(1));
        assertEquals(0,
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "%", null, null).size());
        assertEquals(0,
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, "_", null, null).size());
    }

    @Test
    public void testFindRightsholderTotalsHoldersByScenarioIdSortByAccountNumber() {
        Sort accountNumberSort = new Sort("rightsholder.accountNumber", Direction.ASC);
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null,
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
        Sort accountNumberSort = new Sort("rightsholder.name", Direction.DESC);
        List<RightsholderTotalsHolder> rightsholderTotalsHolders =
            usageArchiveRepository.findRightsholderTotalsHoldersByScenarioId(SCENARIO_ID, StringUtils.EMPTY, null,
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
        assertEquals(1, usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009997L, null));
        assertEquals(1, usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000002859L, null));
        assertEquals(1, usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(
            "e19570d3-e9a0-4805-90ed-bd5dbcfcf803", 1000002859L, null));
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberNullSearchValue() {
        assertEquals(1,
            usageArchiveRepository.findByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000009997L, null, null, null)
                .size());
        assertEquals(1,
            usageArchiveRepository.findByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000002859L, null, null, null)
                .size());
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByRorName() {
        assertFindByScenarioIdAndRhSearch("JAC, Japan Academic Association for Copyright Clearance, Inc.", 1);
        assertFindByScenarioIdAndRhSearch("Copyright Clearance, Inc.", 1);
        assertFindByScenarioIdAndRhSearch("CoPyRight", 1);
        assertFindByScenarioIdAndRhSearch("JAC, Japan Acade mic", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByRorAccountNumber() {
        assertFindByScenarioIdAndRhSearch("2000017010", 1);
        assertFindByScenarioIdAndRhSearch("0001701", 1);
        assertFindByScenarioIdAndRhSearch("200001 7010", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByDetailId() {
        assertFindByScenarioIdAndRhSearch("2f660585-35a1-48a5-a506-904c725cda11", 1);
        assertFindByScenarioIdAndRhSearch("48a5", 1);
        assertFindByScenarioIdAndRhSearch("49", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByWrWrkInst() {
        assertFindByScenarioIdAndRhSearch("243904752", 1);
        assertFindByScenarioIdAndRhSearch("24390", 1);
        assertFindByScenarioIdAndRhSearch("24461 4835", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchByStandardNumber() {
        assertFindByScenarioIdAndRhSearch("1008902112377654XX", 1);
        assertFindByScenarioIdAndRhSearch("1008902112377654xx", 1);
        assertFindByScenarioIdAndRhSearch("100890 2002377655XX", 0);
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumberSearchBySqlLikePattern() {
        assertFindByScenarioIdAndRhSearch("%", 0);
        assertFindByScenarioIdAndRhSearch("_", 0);
    }

    @Test
    public void testFindAaclCountByScenarioIdAndRhAccountNumberWithEmptySearch() {
        assertEquals(2,
            usageArchiveRepository.findAaclCountByScenarioIdAndRhAccountNumber(AACL_SCENARIO_ID, 1000009997L, null));
    }

    @Test
    public void testFindAaclByScenarioIdAndRhAccountNumberWithEmptySearch() {
        List<UsageDto> expectedUsageDtos =
            loadExpectedUsageDtos(Arrays.asList("json/aacl/aacl_archived_usage_dtos.json"));
        List<UsageDto> actualUsageDtos =
            usageArchiveRepository.findAaclByScenarioIdAndRhAccountNumber(AACL_SCENARIO_ID, 1000009997L, null,
                null, null);
        assertEquals(expectedUsageDtos.size(), actualUsageDtos.size());
        IntStream.range(0, expectedUsageDtos.size())
            .forEach(index -> assertUsageDto(expectedUsageDtos.get(0), actualUsageDtos.get(0)));
    }

    @Test
    public void testFindAaclByScenarioIdAndRhAccountNumberSearchByDetailId() {
        assertFindAaclByScenarioIdAndRhSearch("cd1a9398-0b86-42f1-bdc9-ff1ac764b1c2", 1);
        assertFindAaclByScenarioIdAndRhSearch("42f1", 1);
        assertFindAaclByScenarioIdAndRhSearch("49", 0);
    }

    @Test
    public void testFindAaclByScenarioIdAndRhAccountNumberSearchByWrWrkInst() {
        assertFindAaclByScenarioIdAndRhSearch("243904752", 1);
        assertFindAaclByScenarioIdAndRhSearch("24390", 1);
        assertFindAaclByScenarioIdAndRhSearch("904", 2);
        assertFindAaclByScenarioIdAndRhSearch("24390 4752", 0);
    }

    @Test
    public void testFindAaclByScenarioIdAndRhAccountNumberSearchByStandardNumber() {
        assertFindAaclByScenarioIdAndRhSearch("1008902112377654XX", 1);
        assertFindAaclByScenarioIdAndRhSearch("1008902112377654xx", 1);
        assertFindAaclByScenarioIdAndRhSearch("21", 2);
        assertFindAaclByScenarioIdAndRhSearch("100890 2002377655XX", 0);
    }

    @Test
    public void testFindAaclByScenarioIdAndRhAccountNumberSearchBySqlLikePattern() {
        assertFindAaclByScenarioIdAndRhSearch("%", 0);
        assertFindAaclByScenarioIdAndRhSearch("_", 0);
    }

    @Test
    public void testFindRightsholderTotalsHolderCountByScenarioId() {
        assertEquals(3,
            usageArchiveRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, StringUtils.EMPTY));
        assertEquals(1, usageArchiveRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, "IEEE"));
        assertEquals(0, usageArchiveRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, "%"));
        assertEquals(0, usageArchiveRepository.findRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, "_"));
    }

    @Test
    public void testUpdatePaidInfo() {
        String scenarioId = "98caae9b-2f20-4c6d-b2af-3190a1115c48";
        Long oldAccountNumber = 1000002859L;
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.setId("7241b7e0-6ab8-4483-896d-fd485c574293");
        Rightsholder payee = new Rightsholder();
        payee.setAccountNumber(oldAccountNumber);
        paidUsage.setPayee(payee);
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(oldAccountNumber);
        paidUsage.setRightsholder(rightsholder);
        paidUsage.setNetAmount(new BigDecimal("80.0000000000"));
        paidUsage.setServiceFeeAmount(new BigDecimal("12.8000000000"));
        paidUsage.setGrossAmount(new BigDecimal("92.8000000000"));
        assertUsagePaidInformation(paidUsage, scenarioId, oldAccountNumber, UsageStatusEnum.SENT_TO_LM);
        payee.setAccountNumber(1000005413L);
        rightsholder.setAccountNumber(1000009255L);
        paidUsage.setCheckNumber("578945");
        paidUsage.setCheckDate(PAID_DATE);
        paidUsage.setCccEventId("53256");
        paidUsage.setDistributionName("FDA March 17");
        paidUsage.setDistributionDate(PAID_DATE);
        paidUsage.setPeriodEndDate(CommonDateUtils.getOffsetDateTime(PUBLICATION_DATE));
        paidUsage.setStatus(UsageStatusEnum.PAID);
        paidUsage.setLmDetailId(LM_DETAIL_ID);
        paidUsage.setNetAmount(new BigDecimal("40.0000000000"));
        paidUsage.setServiceFeeAmount(new BigDecimal("6.4000000000"));
        paidUsage.setGrossAmount(new BigDecimal("46.4000000000"));
        usageArchiveRepository.updatePaidInfo(paidUsage);
        assertUsagePaidInformation(paidUsage, scenarioId, 1000009255L, UsageStatusEnum.PAID);
    }

    @Test
    public void testFindByIdsAndStatus() {
        List<PaidUsage> paidUsages =
            usageArchiveRepository.findByIdAndStatus(Collections.singletonList(PAID_USAGE_ID), UsageStatusEnum.PAID);
        assertTrue(CollectionUtils.isNotEmpty(paidUsages));
        assertEquals(1, paidUsages.size());
        PaidUsage paidUsage = paidUsages.get(0);
        assertEquals("3f8ce825-6514-4307-a118-3ec89187bef3", paidUsage.getId());
        assertEquals(Long.valueOf("7000813806"), paidUsage.getRroAccountNumber());
        assertEquals(Long.valueOf("1000002859"), paidUsage.getRightsholder().getAccountNumber());
        assertEquals(Long.valueOf("1000002859"), paidUsage.getPayee().getAccountNumber());
        assertEquals(Long.valueOf("243904752"), paidUsage.getWrWrkInst());
        assertEquals("100 ROAD MOVIES", paidUsage.getWorkTitle());
        assertEquals("DIN EN 779:2012", paidUsage.getArticle());
        assertEquals("Philippe de Mézières", paidUsage.getAuthor());
        assertEquals(PUBLICATION_DATE, paidUsage.getPublicationDate());
        assertEquals(Integer.valueOf(100), paidUsage.getNumberOfCopies());
        assertEquals("Doc Del", paidUsage.getMarket());
        assertEquals(Integer.valueOf(2013), paidUsage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2017), paidUsage.getMarketPeriodTo());
        assertEquals(new BigDecimal("80.0000000000"), paidUsage.getNetAmount());
        assertEquals(new BigDecimal("420.0000000000"), paidUsage.getServiceFeeAmount());
        assertEquals(new BigDecimal("500.0000000000"), paidUsage.getGrossAmount());
        assertEquals("578945", paidUsage.getCheckNumber());
        assertEquals(PAID_DATE, paidUsage.getCheckDate());
        assertEquals("53256", paidUsage.getCccEventId());
        assertEquals("FDA March 17", paidUsage.getDistributionName());
        assertEquals(PAID_DATE, paidUsage.getDistributionDate());
        assertEquals(LM_DETAIL_ID, paidUsage.getLmDetailId());
        assertTrue(CollectionUtils.isEmpty(
            usageArchiveRepository.findByIdAndStatus(Collections.singletonList(PAID_USAGE_ID),
                UsageStatusEnum.ARCHIVED)));
    }

    @Test
    public void testFindAaclUsagesByIds() {
        List<PaidUsage> actualUsages =
            usageArchiveRepository.findByIdAndStatus(
                Arrays.asList("6e8172d6-c16f-4522-8606-e55db1b8e5a4", "1537f313-975e-420e-b745-95f2808a388a"),
                UsageStatusEnum.LOCKED);
        verifyPaidUsages(
            Collections.singletonList("json/aacl/aacl_archived_usage_6e8172d6.json"), actualUsages,
            this::verifyPaidUsage);
    }

    @Test
    public void testFindPaidIds() {
        List<String> usagesIds = usageArchiveRepository.findPaidIds();
        assertTrue(CollectionUtils.isNotEmpty(usagesIds));
        assertEquals(1, usagesIds.size());
        assertEquals(PAID_USAGE_ID, usagesIds.get(0));
    }

    @Test
    public void testInsertPaid() {
        PaidUsage paidUsage = buildPaidUsage();
        usageArchiveRepository.insertPaid(paidUsage);
        assertUsagePaidInformation(paidUsage);
    }

    @Test
    public void testInsertPaidAacl() {
        List<PaidUsage> paidUsages =
            loadExpectedPaidUsages(Collections.singletonList("json/aacl/aacl_paid_usage_278adb86.json"));
        usageArchiveRepository.insertAaclPaid(paidUsages.get(0));
        List<PaidUsage> actualUsages =
            usageArchiveRepository.findByIdAndStatus(Collections.singletonList("278adb86-792d-417f-aa6b-0ee2254c356f"),
                UsageStatusEnum.PAID);
        assertEquals(1, actualUsages.size());
        verifyPaidUsages(
            Collections.singletonList("json/aacl/aacl_paid_usage_278adb86.json"), actualUsages, this::verifyPaidUsage);
    }

    @Test
    public void testMoveFundUsagesToArchive() {
        List<String> usageIds = Arrays.asList("677e1740-c791-4929-87f9-e7fc68dd4699",
            "2a868c86-a639-400f-b407-0602dd7ec8df", "9abfd0a0-2779-4321-af07-ebabe22627a0");
        assertEquals(0, usageArchiveRepository.findByIds(usageIds).size());
        assertEquals(3, usageRepository.findByIds(usageIds).size());
        usageArchiveRepository.moveFundUsagesToArchive("79d47e6e-2e84-4e9a-b92c-ab8d745935ef");
        assertEquals(1, usageRepository.findByIds(usageIds).size());
        List<Usage> archivedUsages = usageArchiveRepository.findByIds(usageIds);
        assertEquals(2, archivedUsages.size());
        archivedUsages.forEach(usage -> {
            assertNull(usage.getScenarioId());
            assertNull(usage.getRightsholder().getAccountNumber());
            assertNull(usage.getPayee().getAccountNumber());
            assertNull(usage.getWrWrkInst());
            assertEquals(UsageStatusEnum.ARCHIVED, usage.getStatus());
            assertEquals("NTS", usage.getProductFamily());
            assertEquals("Doc Del", usage.getMarket());
            assertEquals(Integer.valueOf(2013), usage.getMarketPeriodFrom());
            assertEquals(Integer.valueOf(2017), usage.getMarketPeriodTo());
            assertEquals(new BigDecimal("0.0000000000"), usage.getNetAmount());
            assertEquals(new BigDecimal("0.0000000000"), usage.getServiceFeeAmount());
            assertEquals(new BigDecimal("99.9900000000"), usage.getGrossAmount());
        });
    }

    @Test
    public void testCopyNtsToArchiveByScenarioId() {
        List<String> archivedIds = usageArchiveRepository.copyNtsToArchiveByScenarioId(NTS_SCENARIO_ID, USER);
        assertEquals(1, archivedIds.size());
        assertEquals(2, usageRepository.findByScenarioId(NTS_SCENARIO_ID).size());
        List<Usage> archivedUsages = usageArchiveRepository.findByIds(archivedIds);
        assertEquals(1, archivedUsages.size());
        verifyNtsCopiedUsage(archivedUsages.get(0));
    }

    @Test
    public void testCopyFasToArchiveByScenarioId() {
        List<String> archivedIds = usageArchiveRepository.copyToArchiveByScenarioId(FAS_SCENARIO_ID, USER);
        assertEquals(1, archivedIds.size());
        assertEquals(1, usageRepository.findByScenarioId(FAS_SCENARIO_ID).size());
        List<Usage> archivedUsages = usageArchiveRepository.findByIds(archivedIds);
        assertEquals(1, archivedUsages.size());
        verifyFasCopiedUsage(archivedUsages.get(0));
    }

    @Test
    public void testFindUsageByIds() {
        PaidUsage paidUsage = buildPaidUsage();
        usageArchiveRepository.insertPaid(paidUsage);
        List<Usage> usages = usageArchiveRepository.findByIds(ImmutableList.of(paidUsage.getId()));
        assertTrue(CollectionUtils.isNotEmpty(usages));
        assertEquals(1, CollectionUtils.size(usages));
        verifyUsage(paidUsage, usages.get(0));
    }

    @Test
    public void testFindForSendToLmByIds() {
        List<Usage> expectedUsages = loadExpectedUsages(Collections.singletonList("json/usage_for_send_to_lm.json"));
        List<Usage> actualUsages =
            usageArchiveRepository.findForSendToLmByIds(
                Collections.singletonList("37ea653f-c748-4cb9-b4a3-7b11d434244a"));
        assertEquals(expectedUsages, actualUsages);
    }

    @Test
    public void testFindAaclDtosByScenarioId() {
        List<UsageDto> expectedUsageDtos = loadExpectedUsageDtos(
            Arrays.asList("json/aacl/aacl_archived_usage_dto_927ea8a1.json",
                "json/aacl/aacl_archived_usage_dto_cd1a9398.json", "json/aacl/aacl_archived_usage_dto_cfd3e488.json"));
        List<UsageDto> actualUsageDtos =
            usageArchiveRepository.findAaclDtosByScenarioId(AACL_SCENARIO_ID)
                .stream()
                .sorted(Comparator.comparing(UsageDto::getId))
                .collect(Collectors.toList());
        assertEquals(expectedUsageDtos.size(), actualUsageDtos.size());
        IntStream.range(0, expectedUsageDtos.size())
            .forEach(i -> assertUsageDto(expectedUsageDtos.get(i), actualUsageDtos.get(i)));
    }

    private void assertUsagePaidInformation(PaidUsage expectedPaidUsage) {
        List<Usage> usages = usageArchiveRepository.findByIds(ImmutableList.of(expectedPaidUsage.getId()));
        assertTrue(CollectionUtils.isNotEmpty(usages));
        assertEquals(1, CollectionUtils.size(usages));
        verifyUsage(expectedPaidUsage, usages.get(0));
        List<PaidUsage> paidUsages =
            usageArchiveRepository.findByIdAndStatus(ImmutableList.of(expectedPaidUsage.getId()), UsageStatusEnum.PAID);
        assertTrue(CollectionUtils.isNotEmpty(paidUsages));
        assertEquals(1, CollectionUtils.size(paidUsages));
        assertUsagePaidInformation(expectedPaidUsage, paidUsages.get(0));
    }

    private void verifyPaidUsages(List<String> expectedUsageJsonFiles, List<PaidUsage> actualUsages,
                                  BiConsumer<PaidUsage, PaidUsage> verifier) {
        List<PaidUsage> expectedUsages = loadExpectedPaidUsages(expectedUsageJsonFiles);
        assertEquals(
            CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(index -> verifier.accept(expectedUsages.get(index), actualUsages.get(index)));
    }

    private List<PaidUsage> loadExpectedPaidUsages(List<String> fileNames) {
        List<PaidUsage> usages = new ArrayList<>();
        fileNames.forEach(fileName -> {
            try {
                String content = TestUtils.fileToString(this.getClass(), fileName);
                usages.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<PaidUsage>>() {
                }));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        });
        return usages;
    }

    private void assertUsagePaidInformation(PaidUsage expectedPaidUsage, String scenarioId, Long scenarioPayee,
                                            UsageStatusEnum status) {
        List<PaidUsage> paidUsages =
            usageArchiveRepository.findByIdAndStatus(ImmutableList.of(expectedPaidUsage.getId()), status);
        assertEquals(1, CollectionUtils.size(paidUsages));
        PaidUsage actualPaidUsage = paidUsages.get(0);
        assertUsagePaidInformation(expectedPaidUsage, actualPaidUsage);
        List<UsageDto> usageDtos =
            usageArchiveRepository.findByScenarioIdAndRhAccountNumber(scenarioId, scenarioPayee, null, null, null);
        assertTrue(CollectionUtils.isNotEmpty(usageDtos));
        assertEquals(1, usageDtos.size());
        UsageDto usageDto = usageDtos.get(0);
        assertEquals(expectedPaidUsage.getPayee().getAccountNumber(), usageDto.getPayeeAccountNumber());
        assertEquals(expectedPaidUsage.getRightsholder().getAccountNumber(), usageDto.getRhAccountNumber());
        assertEquals(status, usageDto.getStatus());
    }

    private void assertUsagePaidInformation(PaidUsage expectedPaidUsage, PaidUsage actualPaidUsage) {
        assertEquals(expectedPaidUsage.getId(), actualPaidUsage.getId());
        assertEquals(expectedPaidUsage.getCheckNumber(), actualPaidUsage.getCheckNumber());
        assertEquals(expectedPaidUsage.getCheckDate(), actualPaidUsage.getCheckDate());
        assertEquals(expectedPaidUsage.getCccEventId(), actualPaidUsage.getCccEventId());
        assertEquals(expectedPaidUsage.getDistributionName(), actualPaidUsage.getDistributionName());
        assertEquals(expectedPaidUsage.getDistributionDate(), actualPaidUsage.getDistributionDate());
        assertEquals(expectedPaidUsage.getPeriodEndDate(), actualPaidUsage.getPeriodEndDate());
        assertEquals(expectedPaidUsage.getLmDetailId(), actualPaidUsage.getLmDetailId());
        assertEquals(expectedPaidUsage.getNetAmount(), actualPaidUsage.getNetAmount());
        assertEquals(expectedPaidUsage.getServiceFeeAmount(), actualPaidUsage.getServiceFeeAmount());
        assertEquals(expectedPaidUsage.getGrossAmount(), actualPaidUsage.getGrossAmount());
    }

    private void assertFindByScenarioIdAndRhSearch(String searchValue, int expectedSize) {
        assertEquals(expectedSize, usageArchiveRepository.findByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000002859L,
            searchValue, null, null).size());
        assertEquals(expectedSize,
            usageArchiveRepository.findCountByScenarioIdAndRhAccountNumber(SCENARIO_ID, 1000002859L, searchValue));
    }

    private void assertFindAaclByScenarioIdAndRhSearch(String searchValue, int expectedSize) {
        assertEquals(expectedSize,
            usageArchiveRepository.findAaclByScenarioIdAndRhAccountNumber(AACL_SCENARIO_ID, 1000009997L, searchValue,
                null, null).size());
        assertEquals(expectedSize,
            usageArchiveRepository.findAaclCountByScenarioIdAndRhAccountNumber(AACL_SCENARIO_ID, 1000009997L,
                searchValue));
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
        rightsholderTotalsHolder.setServiceFee(new BigDecimal("0.32000"));
        return rightsholderTotalsHolder;
    }

    private PaidUsage buildPaidUsage() {
        PaidUsage paidUsage = new PaidUsage();
        setUsageFields(paidUsage, RupPersistUtils.generateUuid(), null);
        paidUsage.setLmDetailId(LM_DETAIL_ID);
        paidUsage.setCheckNumber("578945");
        paidUsage.setCheckDate(PAID_DATE);
        paidUsage.setCccEventId("53256");
        paidUsage.setDistributionName("FDA March 17");
        paidUsage.setDistributionDate(PAID_DATE);
        paidUsage.setPeriodEndDate(CommonDateUtils.getOffsetDateTime(PUBLICATION_DATE));
        paidUsage.setStatus(UsageStatusEnum.PAID);
        paidUsage.setLmDetailId(LM_DETAIL_ID);
        paidUsage.setNetAmount(new BigDecimal("50.0000000000"));
        paidUsage.setServiceFeeAmount(new BigDecimal("16.0000000000"));
        paidUsage.setGrossAmount(new BigDecimal("66.0000000000"));
        paidUsage.setServiceFee(new BigDecimal("0.32000"));
        return paidUsage;
    }

    private void verifyUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
        assertEquals(expectedUsage.getScenarioId(), actualUsage.getScenarioId());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
            actualUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedUsage.getPayee().getAccountNumber(), actualUsage.getPayee().getAccountNumber());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getPublisher(), actualUsage.getPublisher());
        assertEquals(expectedUsage.getPublicationDate(), actualUsage.getPublicationDate());
        assertEquals(expectedUsage.getMarket(), actualUsage.getMarket());
        assertEquals(expectedUsage.getMarketPeriodFrom(), actualUsage.getMarketPeriodFrom());
        assertEquals(expectedUsage.getMarketPeriodTo(), actualUsage.getMarketPeriodTo());
        assertEquals(expectedUsage.getAuthor(), actualUsage.getAuthor());
        assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
        assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        if (Objects.nonNull(expectedUsage.getAaclUsage())) {
            assertAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
        } else {
            assertNull(actualUsage.getAaclUsage());
        }
    }

    private void assertUsageDto(UsageDto expectedUsageDto, UsageDto actualUsageDto) {
        assertEquals(expectedUsageDto.getBatchName(), actualUsageDto.getBatchName());
        assertEquals(expectedUsageDto.getFiscalYear(), actualUsageDto.getFiscalYear());
        assertEquals(expectedUsageDto.getRroAccountNumber(), actualUsageDto.getRroAccountNumber());
        assertEquals(expectedUsageDto.getRroName(), actualUsageDto.getRroName());
        assertEquals(expectedUsageDto.getWrWrkInst(), actualUsageDto.getWrWrkInst());
        assertEquals(expectedUsageDto.getSystemTitle(), actualUsageDto.getSystemTitle());
        assertEquals(expectedUsageDto.getWorkTitle(), actualUsageDto.getWorkTitle());
        assertEquals(expectedUsageDto.getArticle(), actualUsageDto.getArticle());
        assertEquals(expectedUsageDto.getRhAccountNumber(), actualUsageDto.getRhAccountNumber());
        assertEquals(expectedUsageDto.getRhName(), actualUsageDto.getRhName());
        assertEquals(expectedUsageDto.getStandardNumber(), actualUsageDto.getStandardNumber());
        assertEquals(expectedUsageDto.getStandardNumberType(), actualUsageDto.getStandardNumberType());
        assertEquals(expectedUsageDto.getPublisher(), actualUsageDto.getPublisher());
        assertEquals(expectedUsageDto.getNumberOfCopies(), actualUsageDto.getNumberOfCopies());
        assertEquals(expectedUsageDto.getMarket(), actualUsageDto.getMarket());
        assertEquals(expectedUsageDto.getArticle(), actualUsageDto.getArticle());
        assertEquals(expectedUsageDto.getPayeeAccountNumber(), actualUsageDto.getPayeeAccountNumber());
        assertEquals(expectedUsageDto.getPayeeName(), actualUsageDto.getPayeeName());
        assertEquals(expectedUsageDto.getGrossAmount(), actualUsageDto.getGrossAmount());
        assertEquals(expectedUsageDto.getReportedValue(), actualUsageDto.getReportedValue());
        assertEquals(expectedUsageDto.getNetAmount(), actualUsageDto.getNetAmount());
        assertEquals(expectedUsageDto.getStatus(), actualUsageDto.getStatus());
        assertEquals(expectedUsageDto.getServiceFee(), actualUsageDto.getServiceFee());
        assertEquals(expectedUsageDto.getComment(), actualUsageDto.getComment());
        if (Objects.nonNull(expectedUsageDto.getAaclUsage())) {
            assertAaclUsage(expectedUsageDto.getAaclUsage(), actualUsageDto.getAaclUsage());
        } else {
            assertNull(actualUsageDto.getAaclUsage());
        }
    }

    private void assertAaclUsage(AaclUsage expectedAaclUsage, AaclUsage actualAaclUsage) {
        assertNotNull(actualAaclUsage);
        assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
        assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
        assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
        assertEquals(expectedAaclUsage.getUsageAge().getPeriod(), actualAaclUsage.getUsageAge().getPeriod());
        assertPublicationType(expectedAaclUsage.getPublicationType(), actualAaclUsage.getPublicationType());
        assertEquals(expectedAaclUsage.getOriginalPublicationType(), actualAaclUsage.getOriginalPublicationType());
        assertEquals(expectedAaclUsage.getPublicationType().getWeight(),
            actualAaclUsage.getPublicationType().getWeight());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getId(),
            actualAaclUsage.getDetailLicenseeClass().getId());
        assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
        assertEquals(expectedAaclUsage.getBaselineId(), actualAaclUsage.getBaselineId());
    }

    private void assertPublicationType(PublicationType expectedPublicationType,
                                       PublicationType actualPublicationType) {
        assertEquals(expectedPublicationType.getId(), actualPublicationType.getId());
        assertEquals(expectedPublicationType.getName(), actualPublicationType.getName());
        assertEquals(expectedPublicationType.getWeight(), actualPublicationType.getWeight());
    }

    private void verifyPaidUsage(PaidUsage expectedUsage, PaidUsage actualUsage) {
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
            actualUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedUsage.getPayee().getAccountNumber(), actualUsage.getPayee().getAccountNumber());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getPublicationDate(), actualUsage.getPublicationDate());
        assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertUsagePaidInformation(expectedUsage, actualUsage);
        if (Objects.nonNull(actualUsage.getAaclUsage())) {
            verifyAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
        }
    }

    private void verifyAaclUsage(AaclUsage expectedAaclUsage, AaclUsage actualAaclUsage) {
        assertEquals(expectedAaclUsage.getOriginalPublicationType(), actualAaclUsage.getOriginalPublicationType());
        assertEquals(expectedAaclUsage.getPublicationType().getId(), actualAaclUsage.getPublicationType().getId());
        assertEquals(expectedAaclUsage.getPublicationType().getName(), actualAaclUsage.getPublicationType().getName());
        assertEquals(expectedAaclUsage.getPublicationType().getWeight(),
            actualAaclUsage.getPublicationType().getWeight());
        assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
        assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
        assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
        assertEquals(expectedAaclUsage.getUsageAge().getPeriod(), actualAaclUsage.getUsageAge().getPeriod());
        assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
        assertEquals(expectedAaclUsage.getBatchPeriodEndDate(), actualAaclUsage.getBatchPeriodEndDate());
        assertEquals(expectedAaclUsage.getBaselineId(), actualAaclUsage.getBaselineId());
        assertEquals(expectedAaclUsage.getValueWeight(), actualAaclUsage.getValueWeight());
        assertEquals(expectedAaclUsage.getVolumeWeight(), actualAaclUsage.getVolumeWeight());
        assertEquals(expectedAaclUsage.getVolumeShare(), actualAaclUsage.getVolumeShare());
        assertEquals(expectedAaclUsage.getValueShare(), actualAaclUsage.getValueShare());
        assertEquals(expectedAaclUsage.getTotalShare(), actualAaclUsage.getTotalShare());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getId(),
            actualAaclUsage.getDetailLicenseeClass().getId());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getDiscipline(),
            actualAaclUsage.getDetailLicenseeClass().getDiscipline());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getEnrollmentProfile(),
            actualAaclUsage.getDetailLicenseeClass().getEnrollmentProfile());
    }

    private void setUsageFields(Usage usage, String usageId, String usageBatchId) {
        usage.setId(usageId);
        usage.setBatchId(usageBatchId);
        usage.setScenarioId("b1f0b236-3ae9-4a60-9fab-61db84199d6f");
        usage.setWrWrkInst(WR_WRK_INST);
        usage.setWorkTitle(WORK_TITLE);
        usage.setSystemTitle(SYSTEM_TITLE);
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.getRightsholder().setName("CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil");
        usage.getPayee().setAccountNumber(2000017004L);
        usage.setStatus(UsageStatusEnum.SENT_TO_LM);
        usage.setProductFamily("FAS");
        usage.setArticle(ARTICLE);
        usage.setStandardNumber(STANDARD_NUMBER);
        usage.setStandardNumberType(STANDARD_NUMBER_TYPE);
        usage.setPublisher(PUBLISHER);
        usage.setPublicationDate(PUBLICATION_DATE);
        usage.setMarket(MARKET);
        usage.setMarketPeriodFrom(MARKED_PERIOD_FROM);
        usage.setMarketPeriodTo(MARKED_PERIOD_TO);
        usage.setAuthor(AUTHOR);
        usage.setNumberOfCopies(NUMBER_OF_COPIES);
        usage.setReportedValue(REPORTED_VALUE);
        usage.setGrossAmount(GROSS_AMOUNT);
        usage.setNetAmount(new BigDecimal("25.1500000000"));
        usage.setComment("usage from usages.csv");
    }

    private void verifyCommonArchivedUsageFields(Usage usage, BigDecimal reportedValue, BigDecimal netAmount,
                                                 BigDecimal serviceFeeAmount, BigDecimal grossAmount) {
        assertEquals("5bcf2c37-2f32-48e9-90fe-c9d75298eeed", usage.getRightsholder().getId());
        assertEquals(Long.valueOf(1000002859), usage.getRightsholder().getAccountNumber());
        assertEquals(reportedValue, usage.getReportedValue());
        assertEquals(netAmount, usage.getNetAmount());
        assertEquals(serviceFeeAmount, usage.getServiceFeeAmount());
        assertEquals(new BigDecimal("0.32000"), usage.getServiceFee());
        assertEquals(grossAmount, usage.getGrossAmount());
        assertEquals(Long.valueOf(1000002859), usage.getPayee().getAccountNumber());
        assertFalse(usage.isRhParticipating());
        assertEquals(USER, usage.getCreateUser());
        assertEquals(USER, usage.getUpdateUser());
        assertNull(usage.getStandardNumberType());
        assertNull(usage.getAuthor());
        assertNull(usage.getPublisher());
        assertNull(usage.getNumberOfCopies());
        assertNull(usage.getComment());
    }

    private void verifyFasCopiedUsage(Usage usage) {
        verifyCommonArchivedUsageFields(usage, new BigDecimal("15000.00"), new BigDecimal("4426.3300000000"),
            new BigDecimal("2082.9800000000"), new BigDecimal("6509.3100000000"));
        assertEquals("82dff947-9fa8-4aae-9d42-1453c7d56fed", usage.getId());
        assertEquals("83eef503-0f35-44fc-8b0a-9b6bf6a7f41d", usage.getBatchId());
        assertEquals("FAS", usage.getProductFamily());
        assertEquals(FAS_SCENARIO_ID, usage.getScenarioId());
        assertEquals(Long.valueOf(569526592), usage.getWrWrkInst());
        assertEquals("Cell Biology", usage.getWorkTitle());
        assertEquals("Cell Biology", usage.getSystemTitle());
        assertEquals("0804709114", usage.getStandardNumber());
        assertEquals("DIN EN 779:2012", usage.getArticle());
        assertEquals("Univ", usage.getMarket());
        assertEquals(Integer.valueOf(2013), usage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2017), usage.getMarketPeriodTo());
    }

    private void verifyNtsCopiedUsage(Usage usage) {
        verifyCommonArchivedUsageFields(usage, new BigDecimal("0.00"), new BigDecimal("8506.3300000000"),
            new BigDecimal("4002.9800000000"), new BigDecimal("12509.3100000000"));
        assertEquals("NTS", usage.getProductFamily());
        assertEquals("e65833c8-3a40-47ba-98fe-21aba07ef11e", usage.getScenarioId());
        assertEquals(Long.valueOf(151811999), usage.getWrWrkInst());
        assertEquals("NON-TITLE NTS", usage.getWorkTitle());
        assertEquals("NON-TITLE NTS", usage.getSystemTitle());
        assertNotNull(usage.getId());
        assertNull(usage.getBatchId());
        assertNull(usage.getStandardNumber());
        assertNull(usage.getStandardNumberType());
        assertNull(usage.getArticle());
        assertNull(usage.getAuthor());
        assertNull(usage.getPublisher());
        assertNull(usage.getNumberOfCopies());
        assertNull(usage.getMarket());
        assertNull(usage.getMarketPeriodFrom());
        assertNull(usage.getMarketPeriodTo());
        assertNull(usage.getComment());
    }

    private List<Usage> loadExpectedUsages(List<String> fileNames) {
        List<Usage> usages = new ArrayList<>();
        fileNames.forEach(fileName -> {
            try {
                String content = TestUtils.fileToString(this.getClass(), fileName);
                usages.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<Usage>>() {
                }));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        });
        return usages;
    }

    private List<UsageDto> loadExpectedUsageDtos(List<String> fileNames) {
        List<UsageDto> usages = new ArrayList<>();
        fileNames.forEach(fileName -> {
            try {
                String content = TestUtils.fileToString(this.getClass(), fileName);
                usages.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UsageDto>>() {
                }));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        });
        return usages;
    }
}
