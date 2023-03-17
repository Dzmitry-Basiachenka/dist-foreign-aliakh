package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.SalGradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableSet;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Integration test for {@link SalUsageRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class SalUsageRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "sal-usage-repository-integration-test/";
    private static final String FIND_FOR_AUDIT = FOLDER_NAME + "find-for-audit.groovy";
    private static final String USAGE_BATCH_ID_1 = "6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64";
    private static final String USAGE_BATCH_ID_2 = "56069b44-10b1-42d6-9a44-a3fae0029171";
    private static final String USAGE_BATCH_ID_3 = "09cc64a7-171a-4921-8d99-500768137cb8";
    private static final String USAGE_BATCH_ID_4 = "d67611c2-ee99-445b-9ce3-e798e2ed640a";
    private static final String SCENARIO_ID_1 = "6252afe5-e756-42d4-b96a-708afeda9122";
    private static final String SCENARIO_ID_2 = "71d242e6-4009-4393-9962-45daf962706a";
    private static final String USAGE_ID_1 = "c95654c0-a607-4683-878f-99606e90c065";
    private static final String USAGE_ID_2 = "7b5ac9fc-63e2-4162-8d63-953b7023293c";
    private static final String USAGE_ID_3 = "5ab5e80b-89c0-4d78-9675-54c7ab284450";
    private static final String USAGE_ID_4 = "d8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d";
    private static final String USAGE_ID_5 = "83e26dc4-87af-464d-9edc-bb37611947fa";
    private static final String USAGE_ID_6 = "7f82f654-d906-4cc3-83cf-0409c69a0891";
    private static final String USAGE_ID_7 = "c75305bc-9458-40bc-9926-004a47b072fc";
    private static final String USAGE_ID_8 = "870ee1dc-8596-409f-8ffe-717d17a33c9e";
    private static final String USAGE_ID_9 = "a85d26e1-deb6-4f3f-96fa-58b4175825f4";
    private static final String USAGE_ID_10 = "497ef524-242b-49b3-9b47-c64082f6895d";
    private static final String WORK_PORTION_ID_1 = "1101001IB2361";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final String DETAIL_ID_KEY = "detailId";
    private static final String STATUS_KEY = "status";
    private static final String PRODUCT_FAMILY_KEY = "productFamily";
    private static final String BATCH_NAME_KEY = "batchName";
    private static final String BATCH_PERIOD_END_DATE_KEY = "batchPeriodEndDate";
    private static final String RH_ACCOUNT_NUMBER_KEY = "rhAccountNumber";
    private static final String RH_NAME_KEY = "rhName";
    private static final String WR_WRK_INST_KEY = "wrWrkInst";
    private static final String WORK_TITLE_KEY = "workTitle";
    private static final String SYSTEM_TITLE_KEY = "systemTitle";
    private static final String STANDARD_NUMBER_KEY = "standardNumber";
    private static final String STANDARD_NUMBER_TYPE_KEY = "standardNumberType";
    private static final String COMMENT_KEY = "comment";
    private static final String PERIOD_END_DATE_KEY = "periodEndDate";
    private static final String SCENARIO_NAME_KEY = "scenarioName";
    private static final String GROSS_AMOUNT_KEY = "grossAmount";
    private static final String SERVICE_FEE_AMOUNT_KEY = "serviceFeeAmount";
    private static final String NET_AMOUNT_KEY = "netAmount";
    private static final String PAYEE_ACCOUNT_NUMBER_KEY = "payeeAccountNumber";
    private static final String PAYEE_NAME_KEY = "payeeName";
    private static final String CHECK_NUMBER_KEY = "checkNumber";
    private static final String CHECK_DATE_KEY = "checkDate";
    private static final String CCC_EVENT_ID_KEY = "cccEventId";
    private static final String DISTRIBUTION_NAME_KEY = "distributionName";
    private static final String DISTRIBUTION_DATE_KEY = "distributionDate";
    private static final String DETAIL_TYPE_KEY = "detailType";
    private static final String LICENSEE_ACCOUNT_NUMBER_KEY = "licenseeAccountNumber";
    private static final String LICENSEE_NAME_KEY = "licenseeName";
    private static final String REPORTED_WORK_PORTION_ID_KEY = "reportedWorkPortionId";
    private static final String REPORTED_ARTICLE_KEY = "reportedArticle";
    private static final String REPORTED_STANDARD_NUMBER_KEY = "reportedStandardNumber";
    private static final String REPORTED_AUTHOR_KEY = "reportedAuthor";
    private static final String REPORTED_PUBLISHER_KEY = "reportedPublisher";
    private static final String REPORTED_PUBLICATION_DATE_KEY = "reportedPublicationDate";
    private static final String REPORTED_PAGE_RANGE_KEY = "reportedPageRange";
    private static final String REPORTED_VOL_NUMBER_SERIES_KEY = "reportedVolNumberSeries";
    private static final String REPORTED_MEDIA_TYPE_KEY = "reportedMediaType";
    private static final String MEDIA_TYPE_WEIGHT_KEY = "mediaTypeWeight";
    private static final String COVERAGE_YEAR_KEY = "coverageYear";
    private static final String SCORED_ASSESSMENT_DATE_KEY = "scoredAssessmentDate";
    private static final String QUESTION_IDENTIFIER_KEY = "questionIdentifier";
    private static final String ASSESSMENT_NAME_KEY = "assessmentName";
    private static final String ASSESSMENT_TYPE_KEY = "assessmentType";
    private static final String GRADE_KEY = "grade";
    private static final String GRADE_GROUP_KEY = "gradeGroup";
    private static final String STATES_KEY = "states";
    private static final String NUMBER_OF_VIEWS_KEY = "numberOfViews";
    private static final String USER_NAME = "user@copyright.com";
    private static final BigDecimal ZERO_AMOUNT = new BigDecimal("0.0000000000");
    private static final String PERCENT = "%";
    private static final String UNDERSCORE = "_";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private ISalUsageRepository salUsageRepository;

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-item-bank-detail.groovy")
    public void testInsertItemBankDetail() throws IOException {
        Usage expectedUsage = loadExpectedUsages("json/sal/sal_usage_3883a15d.json").get(0);
        salUsageRepository.insertItemBankDetail(expectedUsage);
        List<Usage> actualUsages =
            salUsageRepository.findByIds(List.of("3883a15d-53d3-4e51-af30-b8d8abfcbd4d"));
        assertEquals(1, actualUsages.size());
        verifyUsage(expectedUsage, actualUsages.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-usage-data-detail.groovy")
    public void testInsertUsageDataDetail() throws IOException {
        Usage usageToInsert = loadExpectedUsages("json/sal/sal_usage_e42e0321.json").get(0);
        Usage expectedUsage = loadExpectedUsages("json/sal/expected_sal_usage_e42e0321.json").get(0);
        salUsageRepository.insertUsageDataDetail(usageToInsert);
        List<Usage> actualUsages =
            salUsageRepository.findByIds(List.of("e42e0321-772c-49b8-b3bf-b73b6d784089"));
        assertEquals(1, actualUsages.size());
        verifyUsage(expectedUsage, actualUsages.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-dtos-by-filter.groovy")
    public void testFindCountByFilter() {
        assertEquals(1, salUsageRepository.findCountByFilter(buildUsageFilter(
            Set.of(USAGE_BATCH_ID_1), UsageStatusEnum.NEW, SAL_PRODUCT_FAMILY, SalDetailTypeEnum.IB)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-dtos-by-filter.groovy")
    public void testFindDtosByFilter() throws IOException {
        verifyUsageDtos(loadExpectedUsageDtos("json/sal/sal_usage_dto_5ab5e80b.json"),
            salUsageRepository.findDtosByFilter(buildUsageFilter(Set.of(USAGE_BATCH_ID_1), UsageStatusEnum.NEW,
                SAL_PRODUCT_FAMILY, SalDetailTypeEnum.IB), null, new Sort(DETAIL_ID_KEY, Direction.ASC)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-dtos-by-filter.groovy")
    public void testFindDtosByFilterSort() {
        assertFindDtosByFilterSort(USAGE_ID_3, DETAIL_ID_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, DETAIL_ID_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, STATUS_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, STATUS_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, DETAIL_TYPE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, DETAIL_TYPE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, PRODUCT_FAMILY_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, PRODUCT_FAMILY_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, BATCH_NAME_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, BATCH_NAME_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, PERIOD_END_DATE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, PERIOD_END_DATE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, RH_ACCOUNT_NUMBER_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, RH_ACCOUNT_NUMBER_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, RH_NAME_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, RH_NAME_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, LICENSEE_ACCOUNT_NUMBER_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, LICENSEE_ACCOUNT_NUMBER_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, LICENSEE_NAME_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, LICENSEE_NAME_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_2, WR_WRK_INST_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, WR_WRK_INST_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, WORK_TITLE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, WORK_TITLE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, SYSTEM_TITLE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, SYSTEM_TITLE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, STANDARD_NUMBER_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, STANDARD_NUMBER_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, STANDARD_NUMBER_TYPE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, STANDARD_NUMBER_TYPE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, ASSESSMENT_NAME_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, ASSESSMENT_NAME_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, ASSESSMENT_TYPE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, ASSESSMENT_TYPE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, REPORTED_WORK_PORTION_ID_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, REPORTED_WORK_PORTION_ID_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, REPORTED_ARTICLE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, REPORTED_ARTICLE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, REPORTED_STANDARD_NUMBER_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, REPORTED_STANDARD_NUMBER_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, REPORTED_AUTHOR_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, REPORTED_AUTHOR_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, REPORTED_PUBLISHER_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, REPORTED_PUBLISHER_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, REPORTED_PUBLICATION_DATE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, REPORTED_PUBLICATION_DATE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, REPORTED_PAGE_RANGE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, REPORTED_PAGE_RANGE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, REPORTED_VOL_NUMBER_SERIES_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, REPORTED_VOL_NUMBER_SERIES_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, REPORTED_MEDIA_TYPE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, REPORTED_MEDIA_TYPE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, COVERAGE_YEAR_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, COVERAGE_YEAR_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, SCORED_ASSESSMENT_DATE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, SCORED_ASSESSMENT_DATE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, QUESTION_IDENTIFIER_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, QUESTION_IDENTIFIER_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_2, GRADE_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, GRADE_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, GRADE_GROUP_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, GRADE_GROUP_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, STATES_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, STATES_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, NUMBER_OF_VIEWS_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, NUMBER_OF_VIEWS_KEY, Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, COMMENT_KEY, Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, COMMENT_KEY, Direction.DESC);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-rightsholders.groovy")
    public void testFindRightsholders() {
        List<Rightsholder> rightsholders = salUsageRepository.findRightsholders();
        assertEquals(3, rightsholders.size());
        assertRightsholder(rightsholders.get(0), 1000000026L, "Georgia State University Business Press [C]");
        assertRightsholder(rightsholders.get(1), 1000011450L, "Delmar Learning, a division of Cengage Learning");
        assertRightsholder(rightsholders.get(2), 2000017004L, "Access Copyright, The Canadian Copyright Agency");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "work-portion-id-exists.groovy")
    public void testWorkPortionIdExists() {
        assertTrue(salUsageRepository.workPortionIdExists(WORK_PORTION_ID_1));
        assertTrue(salUsageRepository.workPortionIdExists("1101024IB2192"));
        assertFalse(salUsageRepository.workPortionIdExists("1101024IB"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "work-portion-id-exists.groovy")
    public void testWorkPortionIdExistsInBatch() {
        assertTrue(salUsageRepository.workPortionIdExists(WORK_PORTION_ID_1, USAGE_BATCH_ID_1));
        assertFalse(salUsageRepository.workPortionIdExists(WORK_PORTION_ID_1, USAGE_BATCH_ID_2));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "usage-data-exist.groovy")
    public void testUsageDataExistByBatchId() {
        assertTrue(salUsageRepository.usageDataExistByBatchId("cb932497-086d-4a7e-9b34-e9a62f17adab4"));
        assertFalse(salUsageRepository.usageDataExistByBatchId("b0e669d2-68d0-4add-9946-34215011f74b"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "usage-data-exist.groovy")
    public void testUsageDataExistByWorkPortionIds() {
        assertTrue(salUsageRepository.usageDataExistByWorkPortionIds(Set.of("33064IB2190", "1101024IB2192")));
        assertFalse(salUsageRepository.usageDataExistByWorkPortionIds(Set.of("33064IB2190")));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "work-portion-id-exists.groovy")
    public void testFindItemBankDetailGradeByWorkPortionId() {
        assertEquals("5", salUsageRepository.findItemBankDetailGradeByWorkPortionId(WORK_PORTION_ID_1));
        assertNull(salUsageRepository.findItemBankDetailGradeByWorkPortionId("1201064IB2200"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-usage-data.groovy")
    public void testDeleteUsageData() {
        assertTrue(salUsageRepository.usageDataExistByBatchId(USAGE_BATCH_ID_3));
        UsageFilter usageFilter =
            buildUsageFilter(Set.of(USAGE_BATCH_ID_3), UsageStatusEnum.NEW, SAL_PRODUCT_FAMILY, null);
        assertEquals(3, salUsageRepository.findDtosByFilter(usageFilter, null, null).size());
        salUsageRepository.deleteUsageData(USAGE_BATCH_ID_3);
        assertFalse(salUsageRepository.usageDataExistByBatchId(USAGE_BATCH_ID_3));
        assertEquals(1, salUsageRepository.findDtosByFilter(usageFilter, null, null).size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-usages-by-work-portion-ids.groovy")
    public void testDeleteUsagesByWorkPortionIds() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Set.of("04f4a53e-a8d9-4f50-a052-25eb051218fe"));
        assertEquals(3, salUsageRepository.findCountByFilter(filter));
        salUsageRepository.deleteUsagesByWorkPortionIds(Set.of("1101024IB2192"));
        assertEquals(1, salUsageRepository.findCountByFilter(filter));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-batch-id.groovy")
    public void testDeleteByBatchId() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Set.of("b54293db-bfb9-478a-bc13-d70aef5d3ecb"));
        assertEquals(2, salUsageRepository.findCountByFilter(filter));
        salUsageRepository.deleteByBatchId("b54293db-bfb9-478a-bc13-d70aef5d3ecb");
        assertEquals(0, salUsageRepository.findCountByFilter(filter));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-usage-data-grade-groups.groovy")
    public void testFindUsageDataGradeGroups() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Set.of(USAGE_BATCH_ID_1));
        assertEquals(List.of(SalGradeGroupEnum.GRADE9_12),
            salUsageRepository.findUsageDataGradeGroups(filter));
        filter.setUsageBatchesIds(Set.of(USAGE_BATCH_ID_2));
        assertEquals(List.of(SalGradeGroupEnum.GRADE6_8),
            salUsageRepository.findUsageDataGradeGroups(filter));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-payee-by-account-number.groovy")
    public void testUpdatePayeeByAccountNumber() {
        List<String> usageIds = List.of("d7764071-935f-4281-a643-656354ccf690", "ad4caa00-c95a-453e-9253-f9810d84d269");
        salUsageRepository.findByIds(usageIds).forEach(usage -> assertNull(usage.getPayee().getAccountNumber()));
        salUsageRepository.updatePayeeByAccountNumber(1000000001L, SCENARIO_ID_1, 7000813806L, USER_NAME);
        salUsageRepository.findByIds(usageIds)
            .forEach(usage -> assertEquals(7000813806L, usage.getPayee().getAccountNumber(), 0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "add-to-scenario.groovy")
    public void testAddToScenario() {
        List<String> usageIds = List.of("e823d079-3e82-4a5c-bdad-a8707b47b665", "9439530a-d7a9-40a9-a881-f892d13eaf9f");
        salUsageRepository.findByIds(usageIds).forEach(usage -> {
            assertNull(usage.getScenarioId());
            assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        });
        UsageFilter usageFilter = buildUsageFilter(Set.of("87a8b327-6fcc-417f-8fd6-bb6615103b53"),
            UsageStatusEnum.ELIGIBLE, SAL_PRODUCT_FAMILY, null);
        salUsageRepository.addToScenario(SCENARIO_ID_1, usageFilter, USER_NAME);
        salUsageRepository.findByIds(usageIds).forEach(usage -> {
            assertEquals(SCENARIO_ID_1, usage.getScenarioId());
            assertEquals(USER_NAME, usage.getUpdateUser());
            assertEquals(UsageStatusEnum.LOCKED, usage.getStatus());
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-from-scenario.groovy")
    public void testDeleteFromScenario() {
        List<Usage> usages = salUsageRepository.findByIds(List.of(USAGE_ID_4));
        assertEquals(1, usages.size());
        usages.forEach(usage -> {
            assertEquals(UsageStatusEnum.LOCKED, usage.getStatus());
            assertNotNull(usage.getScenarioId());
            assertNotNull(usage.getPayee().getAccountNumber());
            assertEquals(new BigDecimal("980.0000000000"), usage.getGrossAmount());
            assertEquals(new BigDecimal("667.4000000000"), usage.getNetAmount());
            assertEquals(new BigDecimal("312.6000000000"), usage.getServiceFeeAmount());
            assertEquals(new BigDecimal("0.32000"), usage.getServiceFee());
            assertEquals("SYSTEM", usage.getUpdateUser());
        });
        salUsageRepository.deleteFromScenario("c0b30809-4a38-46cc-a0dc-641924d1fc43", USER_NAME);
        usages = salUsageRepository.findByIds(List.of(USAGE_ID_4));
        assertEquals(1, usages.size());
        usages.forEach(usage -> {
            assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
            assertNull(usage.getScenarioId());
            assertNull(usage.getPayee().getAccountNumber());
            assertEquals(ZERO_AMOUNT, usage.getGrossAmount());
            assertEquals(ZERO_AMOUNT, usage.getNetAmount());
            assertEquals(ZERO_AMOUNT, usage.getServiceFeeAmount());
            assertNull(usage.getServiceFee());
            assertEquals(USER_NAME, usage.getUpdateUser());
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "calculate-amounts.groovy")
    public void testCalculateAmounts() {
        salUsageRepository.calculateAmounts(SCENARIO_ID_2, USER_NAME);
        List<Usage> actualUsages = salUsageRepository.findByScenarioId(SCENARIO_ID_2);
        actualUsages.sort(Comparator.comparing(Usage::getId));
        assertEquals(6, actualUsages.size());
        verifyUsageWithAmounts(actualUsages.get(0), "86.9608695652", "65.2206521739", "21.7402173913");
        verifyUsageWithAmounts(actualUsages.get(1), "26.0882608696", "19.5661956522", "6.5220652174");
        verifyUsageWithAmounts(actualUsages.get(2), "86.9608695652", "65.2206521739", "21.7402173913");
        verifyUsageWithAmounts(actualUsages.get(3), "296.2944444444", "222.2208333333", "74.0736111111");
        verifyUsageWithAmounts(actualUsages.get(4), "237.0355555556", "177.7766666667", "59.2588888889");
        verifyUsageWithAmounts(actualUsages.get(5), "266.6600000000", "199.9950000000", "66.6650000000");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id-and-rh-account-number.groovy")
    public void testFindCountByScenarioIdAndRhAccountNumber() {
        assertEquals(1, salUsageRepository
            .findCountByScenarioIdAndRhAccountNumber("bafc8277-d9f2-44b6-a68c-9e46165175f8", 1000000026L, null));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id-and-rh-account-number.groovy")
    public void testFindByScenarioIdAndRhAccountNumber() throws IOException {
        List<UsageDto> expectedUsageDtos =
            loadExpectedUsageDtos("json/sal/sal_usage_dto_1d437cb1.json");
        List<UsageDto> actualUsageDtos = salUsageRepository
            .findByScenarioIdAndRhAccountNumber("bafc8277-d9f2-44b6-a68c-9e46165175f8", 1000000026L, null, null, null);
        assertEquals(expectedUsageDtos.size(), actualUsageDtos.size());
        verifyUsageDtos(expectedUsageDtos, actualUsageDtos);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-rh-account-number-and-status-by-ids.groovy")
    public void testUpdateRhAccountNumberAndStatusByIds() {
        List<Usage> usages = salUsageRepository.findByIds(List.of(USAGE_ID_5, USAGE_ID_10));
        assertEquals(2, usages.size());
        usages.forEach(usage -> {
            assertNull(usage.getRightsholder().getAccountNumber());
            assertEquals(UsageStatusEnum.WORK_NOT_GRANTED, usage.getStatus());
        });
        salUsageRepository.updateRhAccountNumberAndStatusByIds(ImmutableSet.of(USAGE_ID_5, USAGE_ID_10),
            1234567856L, UsageStatusEnum.ELIGIBLE, "user@copyright.com");
        usages = salUsageRepository.findByIds(List.of(USAGE_ID_5, USAGE_ID_10));
        assertEquals(2, usages.size());
        usages.forEach(usage -> {
            assertEquals(Long.valueOf(1234567856L), usage.getRightsholder().getAccountNumber());
            assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
            assertEquals("user@copyright.com", usage.getUpdateUser());
        });
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAudit() throws IOException {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(USAGE_ID_7);
        verifyUsageDtosForAudit(loadExpectedUsageDtos("json/sal/sal_audit_usage_dto.json"),
            salUsageRepository.findForAudit(filter, null, null));
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditArchived() throws IOException {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(USAGE_ID_8);
        verifyUsageDtosForAudit(loadExpectedUsageDtos("json/sal/sal_archived_audit_usage_dto.json"),
            salUsageRepository.findForAudit(filter, null, null));
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditByRightsholder() {
        AuditFilter filter = new AuditFilter();
        filter.setRhAccountNumbers(Set.of(2000173934L));
        assertEquals(1, salUsageRepository.findCountForAudit(filter));
        List<UsageDto> usages = salUsageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_6);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditByLicensee() {
        AuditFilter filter = new AuditFilter();
        filter.setLicenseeAccountNumbers(Set.of(2000017003L));
        assertEquals(2, salUsageRepository.findCountForAudit(filter));
        List<UsageDto> usages = salUsageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_8, USAGE_ID_9);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditByBatch() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Set.of(USAGE_BATCH_ID_4));
        assertEquals(2, salUsageRepository.findCountForAudit(filter));
        List<UsageDto> usages = salUsageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_6, USAGE_ID_7);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditByStatus() {
        AuditFilter filter = new AuditFilter();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.ARCHIVED));
        assertEquals(2, salUsageRepository.findCountForAudit(filter));
        List<UsageDto> usages = salUsageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_8, USAGE_ID_9);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditBySalDetailType() {
        AuditFilter filter = new AuditFilter();
        filter.setSalDetailType(SalDetailTypeEnum.UD);
        assertEquals(2, salUsageRepository.findCountForAudit(filter));
        List<UsageDto> usages = salUsageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_9, USAGE_ID_7);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditByUsagePeriod() {
        AuditFilter filter = new AuditFilter();
        filter.setUsagePeriod(2018);
        assertEquals(2, salUsageRepository.findCountForAudit(filter));
        List<UsageDto> usages = salUsageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_8, USAGE_ID_9);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditByCccEventId() {
        assertFindForAuditByCccEventId("53258", USAGE_ID_8);
        assertFindForAuditByCccEventId(PERCENT);
        assertFindForAuditByCccEventId(UNDERSCORE);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditByDistributionName() {
        assertFindForAuditByDistributionName("SAL_March_2018", USAGE_ID_9);
        assertFindForAuditByDistributionName(PERCENT);
        assertFindForAuditByDistributionName(UNDERSCORE);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditSearch() {
        assertFindForAuditSearch(USAGE_ID_6, USAGE_ID_6);
        assertFindForAuditSearch("f82f654-d906-4cc3-83cf-0409c69a089", USAGE_ID_6);
        assertFindForAuditSearch("131858485", USAGE_ID_6);
        assertFindForAuditSearch("3185848", USAGE_ID_6);
        assertFindForAuditSearch("Leseleiter", USAGE_ID_6);
        assertFindForAuditSearch("eseleite", USAGE_ID_6);
        assertFindForAuditSearch(PERCENT);
        assertFindForAuditSearch(UNDERSCORE);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditSortingByCommonUsageInfo() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Set.of(USAGE_BATCH_ID_4));
        verifyFindForAuditSort(filter, DETAIL_ID_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, DETAIL_ID_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, STATUS_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, STATUS_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, PRODUCT_FAMILY_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, PRODUCT_FAMILY_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, RH_ACCOUNT_NUMBER_KEY, Direction.ASC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, RH_ACCOUNT_NUMBER_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, RH_NAME_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, RH_NAME_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, WR_WRK_INST_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, WR_WRK_INST_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, WORK_TITLE_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, WORK_TITLE_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, SYSTEM_TITLE_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, SYSTEM_TITLE_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, STANDARD_NUMBER_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, STANDARD_NUMBER_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, STANDARD_NUMBER_TYPE_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, STANDARD_NUMBER_TYPE_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, GROSS_AMOUNT_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, GROSS_AMOUNT_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, SERVICE_FEE_AMOUNT_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, SERVICE_FEE_AMOUNT_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, NET_AMOUNT_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, NET_AMOUNT_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, SCENARIO_NAME_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, SCENARIO_NAME_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, COMMENT_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, COMMENT_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditSortingBySalUsageInfo() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Set.of(USAGE_BATCH_ID_4));
        verifyFindForAuditSort(filter, DETAIL_TYPE_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, DETAIL_TYPE_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, REPORTED_WORK_PORTION_ID_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_WORK_PORTION_ID_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_ARTICLE_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_ARTICLE_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, REPORTED_STANDARD_NUMBER_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_STANDARD_NUMBER_KEY, Direction.DESC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, REPORTED_AUTHOR_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_AUTHOR_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_PUBLISHER_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_PUBLISHER_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_PUBLICATION_DATE_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_PUBLICATION_DATE_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_PAGE_RANGE_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_PAGE_RANGE_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_VOL_NUMBER_SERIES_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_VOL_NUMBER_SERIES_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_MEDIA_TYPE_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, REPORTED_MEDIA_TYPE_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, MEDIA_TYPE_WEIGHT_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, MEDIA_TYPE_WEIGHT_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, COVERAGE_YEAR_KEY, Direction.ASC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, COVERAGE_YEAR_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, SCORED_ASSESSMENT_DATE_KEY, Direction.ASC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, SCORED_ASSESSMENT_DATE_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, ASSESSMENT_NAME_KEY, Direction.ASC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, ASSESSMENT_NAME_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, ASSESSMENT_TYPE_KEY, Direction.ASC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, ASSESSMENT_TYPE_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, QUESTION_IDENTIFIER_KEY, Direction.ASC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, QUESTION_IDENTIFIER_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, GRADE_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, GRADE_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, GRADE_GROUP_KEY, Direction.ASC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, GRADE_GROUP_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, STATES_KEY, Direction.ASC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, STATES_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, NUMBER_OF_VIEWS_KEY, Direction.ASC, USAGE_ID_7, USAGE_ID_6);
        verifyFindForAuditSort(filter, NUMBER_OF_VIEWS_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditSortingByBatchInfo() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Set.of(USAGE_BATCH_ID_4));
        verifyFindForAuditSort(filter, BATCH_NAME_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, BATCH_NAME_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, BATCH_PERIOD_END_DATE_KEY, Direction.ASC, USAGE_ID_6, USAGE_ID_7);
        verifyFindForAuditSort(filter, BATCH_PERIOD_END_DATE_KEY, Direction.DESC, USAGE_ID_6, USAGE_ID_7);
    }

    @Test
    @TestData(fileName = FIND_FOR_AUDIT)
    public void testFindForAuditSortingByPaidInfo() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Set.of("a375c049-1289-4c85-994b-b2bd8ac043cf"));
        verifyFindForAuditSort(filter, PAYEE_ACCOUNT_NUMBER_KEY, Direction.ASC, USAGE_ID_8, USAGE_ID_9);
        verifyFindForAuditSort(filter, PAYEE_ACCOUNT_NUMBER_KEY, Direction.DESC, USAGE_ID_8, USAGE_ID_9);
        verifyFindForAuditSort(filter, PAYEE_NAME_KEY, Direction.ASC, USAGE_ID_8, USAGE_ID_9);
        verifyFindForAuditSort(filter, PAYEE_NAME_KEY, Direction.DESC, USAGE_ID_8, USAGE_ID_9);
        verifyFindForAuditSort(filter, CHECK_NUMBER_KEY, Direction.ASC, USAGE_ID_8, USAGE_ID_9);
        verifyFindForAuditSort(filter, CHECK_NUMBER_KEY, Direction.DESC, USAGE_ID_8, USAGE_ID_9);
        verifyFindForAuditSort(filter, CHECK_DATE_KEY, Direction.ASC, USAGE_ID_8, USAGE_ID_9);
        verifyFindForAuditSort(filter, CHECK_DATE_KEY, Direction.DESC, USAGE_ID_9, USAGE_ID_8);
        verifyFindForAuditSort(filter, CCC_EVENT_ID_KEY, Direction.ASC, USAGE_ID_8, USAGE_ID_9);
        verifyFindForAuditSort(filter, CCC_EVENT_ID_KEY, Direction.DESC, USAGE_ID_9, USAGE_ID_8);
        verifyFindForAuditSort(filter, DISTRIBUTION_NAME_KEY, Direction.ASC, USAGE_ID_8, USAGE_ID_9);
        verifyFindForAuditSort(filter, DISTRIBUTION_NAME_KEY, Direction.DESC, USAGE_ID_9, USAGE_ID_8);
        verifyFindForAuditSort(filter, DISTRIBUTION_DATE_KEY, Direction.ASC, USAGE_ID_8, USAGE_ID_9);
        verifyFindForAuditSort(filter, DISTRIBUTION_DATE_KEY, Direction.DESC, USAGE_ID_8, USAGE_ID_9);
    }

    private void verifyUsageWithAmounts(Usage usage, String grossAmount, String netAmount, String serviceFeeAmount) {
        assertEquals(new BigDecimal(grossAmount), usage.getGrossAmount());
        assertEquals(new BigDecimal(netAmount), usage.getNetAmount());
        assertEquals(new BigDecimal(serviceFeeAmount), usage.getServiceFeeAmount());
        assertEquals(new BigDecimal("0.25000"), usage.getServiceFee());
        assertEquals(USER_NAME, usage.getUpdateUser());
    }

    private UsageFilter buildUsageFilter(Set<String> usageBatchIds, UsageStatusEnum status, String productFamily,
                                         SalDetailTypeEnum salDetailType) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(usageBatchIds);
        usageFilter.setUsageStatus(status);
        usageFilter.setProductFamily(productFamily);
        usageFilter.setSalDetailType(salDetailType);
        return usageFilter;
    }

    private List<UsageDto> loadExpectedUsageDtos(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        return OBJECT_MAPPER.readValue(content, new TypeReference<List<UsageDto>>() {
        });
    }

    private List<Usage> loadExpectedUsages(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        return OBJECT_MAPPER.readValue(content, new TypeReference<List<Usage>>() {
        });
    }

    private void verifyUsageDtos(List<UsageDto> expectedUsages, List<UsageDto> actualUsages) {
        assertEquals(CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(i -> {
                UsageDto expectedUsage = expectedUsages.get(i);
                UsageDto actualUsage = actualUsages.get(i);
                assertNotNull(expectedUsage);
                assertNotNull(actualUsage);
                verifyUsageDto(expectedUsage, actualUsage);
            });
    }

    private void verifyUsageDto(UsageDto expectedUsage, UsageDto actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getBatchName(), actualUsage.getBatchName());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
        assertEquals(expectedUsage.getRhName(), actualUsage.getRhName());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
        assertEquals(expectedUsage.getServiceFeeAmount(), actualUsage.getServiceFeeAmount());
        assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
        assertSalUsageFields(expectedUsage.getSalUsage(), actualUsage.getSalUsage());
    }

    private void verifyUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
            actualUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertEquals(expectedUsage.getCreateUser(), actualUsage.getCreateUser());
        assertEquals(expectedUsage.getUpdateUser(), actualUsage.getUpdateUser());
        assertSalUsageFields(expectedUsage.getSalUsage(), actualUsage.getSalUsage());
    }

    private void assertSalUsageFields(SalUsage expectedUsage, SalUsage actualUsage) {
        assertEquals(expectedUsage.getAssessmentName(), actualUsage.getAssessmentName());
        assertEquals(expectedUsage.getCoverageYear(), actualUsage.getCoverageYear());
        assertEquals(expectedUsage.getGrade(), actualUsage.getGrade());
        assertEquals(expectedUsage.getGradeGroup(), actualUsage.getGradeGroup());
        assertEquals(expectedUsage.getDetailType(), actualUsage.getDetailType());
        assertEquals(expectedUsage.getReportedWorkPortionId(), actualUsage.getReportedWorkPortionId());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getReportedMediaType(), actualUsage.getReportedMediaType());
        assertEquals(expectedUsage.getMediaTypeWeight(), actualUsage.getMediaTypeWeight());
        assertEquals(expectedUsage.getReportedArticle(), actualUsage.getReportedArticle());
        assertEquals(expectedUsage.getReportedAuthor(), actualUsage.getReportedAuthor());
        assertEquals(expectedUsage.getReportedPublisher(), actualUsage.getReportedPublisher());
        assertEquals(expectedUsage.getReportedPublicationDate(), actualUsage.getReportedPublicationDate());
        assertEquals(expectedUsage.getReportedPageRange(), actualUsage.getReportedPageRange());
        assertEquals(expectedUsage.getReportedVolNumberSeries(), actualUsage.getReportedVolNumberSeries());
        assertEquals(expectedUsage.getAssessmentType(), actualUsage.getAssessmentType());
        assertEquals(expectedUsage.getStates(), actualUsage.getStates());
        assertEquals(expectedUsage.getNumberOfViews(), actualUsage.getNumberOfViews());
        assertEquals(expectedUsage.getScoredAssessmentDate(), actualUsage.getScoredAssessmentDate());
        assertEquals(expectedUsage.getQuestionIdentifier(), actualUsage.getQuestionIdentifier());
        assertEquals(expectedUsage.getBatchPeriodEndDate(), actualUsage.getBatchPeriodEndDate());
    }

    private void assertFindDtosByFilterSort(String detailId, String sortProperty, Sort.Direction sortDirection) {
        List<UsageDto> usageDtos = salUsageRepository.findDtosByFilter(buildUsageFilter(
            Set.of(USAGE_BATCH_ID_1, USAGE_BATCH_ID_2), UsageStatusEnum.NEW, SAL_PRODUCT_FAMILY, null), null,
            new Sort(sortProperty, sortDirection));
        assertEquals(3, CollectionUtils.size(usageDtos));
        assertEquals(detailId, usageDtos.get(0).getId());
    }

    private void assertFindForAuditSearch(String searchValue, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(searchValue);
        assertEquals(usageIds.length, salUsageRepository.findCountForAudit(filter));
        verifyUsageDtos(salUsageRepository.findForAudit(filter, null, null), usageIds);
    }

    private void assertFindForAuditByCccEventId(String cccEventId, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setCccEventId(cccEventId);
        assertEquals(usageIds.length, salUsageRepository.findCountForAudit(filter));
        verifyUsageDtos(salUsageRepository.findForAudit(filter, null, null), usageIds);
    }

    private void assertFindForAuditByDistributionName(String distributionName, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setDistributionName(distributionName);
        assertEquals(usageIds.length, salUsageRepository.findCountForAudit(filter));
        verifyUsageDtos(salUsageRepository.findForAudit(filter, null, null), usageIds);
    }

    private void assertRightsholder(Rightsholder rightsholder, Long expectedAccountNumber, String expectedName) {
        assertEquals(expectedAccountNumber, rightsholder.getAccountNumber());
        assertEquals(expectedName, rightsholder.getName());
    }

    private void verifyUsageDtos(List<UsageDto> usageDtos, String... usageIds) {
        assertNotNull(usageDtos);
        usageDtos.sort(Comparator.comparing(UsageDto::getId));
        Arrays.sort(usageIds);
        verifyUsageDtosInExactOrder(usageDtos, usageIds);
    }

    private void verifyFindForAuditSort(AuditFilter filter, String property, Sort.Direction direction,
                                        String... expectedIds) {
        List<UsageDto> actualUsageDtos =
            salUsageRepository.findForAudit(filter, new Pageable(0, 10), new Sort(property, direction));
        verifyUsageDtosInExactOrder(actualUsageDtos, expectedIds);
    }

    private void verifyUsageDtosInExactOrder(List<UsageDto> usageDtos, String... expectedIds) {
        assertNotNull(usageDtos);
        List<String> actualIds = usageDtos.stream()
            .map(UsageDto::getId)
            .collect(Collectors.toList());
        assertEquals(List.of(expectedIds), actualIds);
    }

    private void verifyUsageDtosForAudit(List<UsageDto> expectedUsages, List<UsageDto> actualUsages) {
        assertEquals(CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(index -> verifyUsageDtoForAudit(expectedUsages.get(index), actualUsages.get(index)));
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
        assertSalUsageFields(expectedUsage.getSalUsage(), actualUsage.getSalUsage());
    }
}
