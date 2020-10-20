package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.GradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.SalDetailTypeEnum;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=sal-usage-repository-test-data-init.groovy"})
@Transactional
public class SalUsageRepositoryIntegrationTest {

    private static final String USAGE_BATCH_ID_1 = "6aa46f9f-a0c2-4b61-97bc-aa35b7ce6e64";
    private static final String USAGE_BATCH_ID_2 = "56069b44-10b1-42d6-9a44-a3fae0029171";
    private static final String USAGE_BATCH_ID_3 = "09cc64a7-171a-4921-8d99-500768137cb8";
    private static final String SCENARIO_ID_1 = "6252afe5-e756-42d4-b96a-708afeda9122";
    private static final String SCENARIO_ID_2 = "71d242e6-4009-4393-9962-45daf962706a";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final String DETAIL_ID_KEY = "detailId";
    private static final String USAGE_ID_1 = "c95654c0-a607-4683-878f-99606e90c065";
    private static final String USAGE_ID_2 = "7b5ac9fc-63e2-4162-8d63-953b7023293c";
    private static final String USAGE_ID_3 = "5ab5e80b-89c0-4d78-9675-54c7ab284450";
    private static final String USAGE_ID_4 = "d8daeed3-e4ee-4b09-b6ec-ef12a12bcd3d";
    private static final String WORK_PORTION_ID_3 = "1101001IB2361";
    private static final String USER_NAME = "user@copyright.com";
    private static final BigDecimal ZERO_AMOUNT = new BigDecimal("0.0000000000");

    @Autowired
    private ISalUsageRepository salUsageRepository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Test
    public void testInsertItemBankDetail() throws IOException {
        Usage expectedUsage = loadExpectedUsages("json/sal/sal_usage_3883a15d.json").get(0);
        salUsageRepository.insertItemBankDetail(expectedUsage);
        List<Usage> actualUsages =
            salUsageRepository.findByIds(Collections.singletonList("3883a15d-53d3-4e51-af30-b8d8abfcbd4d"));
        assertEquals(1, actualUsages.size());
        verifyUsage(expectedUsage, actualUsages.get(0));
    }

    @Test
    public void testInsertUsageDataDetail() throws IOException {
        Usage usageToInsert = loadExpectedUsages("json/sal/sal_usage_e42e0321.json").get(0);
        Usage expectedUsage = loadExpectedUsages("json/sal/expected_sal_usage_e42e0321.json").get(0);
        salUsageRepository.insertUsageDataDetail(usageToInsert);
        List<Usage> actualUsages =
            salUsageRepository.findByIds(Collections.singletonList("e42e0321-772c-49b8-b3bf-b73b6d784089"));
        assertEquals(1, actualUsages.size());
        verifyUsage(expectedUsage, actualUsages.get(0));
    }

    @Test
    public void testFindCountByFilter() {
        assertEquals(1, salUsageRepository.findCountByFilter(buildUsageFilter(
            Collections.singleton(USAGE_BATCH_ID_1), UsageStatusEnum.NEW, SAL_PRODUCT_FAMILY, SalDetailTypeEnum.IB)));
    }

    @Test
    public void testFindDtosByFilter() throws IOException {
        verifyUsageDtos(loadExpectedUsageDtos("json/sal/sal_usage_dto_5ab5e80b.json"),
            salUsageRepository.findDtosByFilter(buildUsageFilter(
                Collections.singleton(USAGE_BATCH_ID_1), UsageStatusEnum.NEW, SAL_PRODUCT_FAMILY, SalDetailTypeEnum.IB),
                null, new Sort(DETAIL_ID_KEY, Sort.Direction.ASC)));
    }

    @Test
    public void testFindDtosByFilterSort() {
        assertFindDtosByFilterSort(USAGE_ID_3, "detailId", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, "detailId", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "status", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "status", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "detailType", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "detailType", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "productFamily", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "productFamily", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "batchName", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, "batchName", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "periodEndDate", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, "periodEndDate", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "rhAccountNumber", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, "rhAccountNumber", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, "rhName", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "rhName", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, "licenseeAccountNumber", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "licenseeAccountNumber", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "licenseeName", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, "licenseeName", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_2, "wrWrkInst", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, "wrWrkInst", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "workTitle", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "workTitle", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "systemTitle", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "systemTitle", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "standardNumber", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "standardNumber", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "standardNumberType", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "standardNumberType", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "assessmentName", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, "assessmentName", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "assessmentType", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, "assessmentType", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "reportedWorkPortionId", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, "reportedWorkPortionId", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "reportedArticle", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "reportedArticle", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "reportedStandardNumber", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "reportedStandardNumber", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "reportedAuthor", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "reportedAuthor", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "reportedPublisher", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "reportedPublisher", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "reportedPublicationDate", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "reportedPublicationDate", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "reportedPageRange", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "reportedPageRange", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "reportedVolNumberSeries", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "reportedVolNumberSeries", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "reportedMediaType", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "reportedMediaType", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, "coverageYear", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "coverageYear", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, "scoredAssessmentDate", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "scoredAssessmentDate", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, "questionIdentifier", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "questionIdentifier", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_2, "grade", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_1, "grade", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, "gradeGroup", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "gradeGroup", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "states", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_2, "states", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_1, "numberOfViews", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "numberOfViews", Sort.Direction.DESC);
        assertFindDtosByFilterSort(USAGE_ID_3, "comment", Sort.Direction.ASC);
        assertFindDtosByFilterSort(USAGE_ID_3, "comment", Sort.Direction.DESC);
    }

    @Test
    public void testWorkPortionIdExists() {
        assertTrue(salUsageRepository.workPortionIdExists(WORK_PORTION_ID_3));
        assertTrue(salUsageRepository.workPortionIdExists("1101024IB2192"));
        assertFalse(salUsageRepository.workPortionIdExists("1101024IB"));
    }

    @Test
    public void testUsageDetailsExist() {
        assertTrue(salUsageRepository.usageDataExist("cb932497-086d-4a7e-9b34-e9a62f17adab4"));
        assertFalse(salUsageRepository.usageDataExist("b0e669d2-68d0-4add-9946-34215011f74b"));
    }

    @Test
    public void testWorkPortionIdExistsInBatch() {
        assertTrue(salUsageRepository.workPortionIdExists(WORK_PORTION_ID_3, USAGE_BATCH_ID_1));
        assertFalse(salUsageRepository.workPortionIdExists(WORK_PORTION_ID_3, USAGE_BATCH_ID_2));
    }

    @Test
    public void testFindItemBankDetailGradeByWorkPortionId() {
        assertEquals("5", salUsageRepository.findItemBankDetailGradeByWorkPortionId(WORK_PORTION_ID_3));
        assertNull(salUsageRepository.findItemBankDetailGradeByWorkPortionId("1201064IB2200"));
    }

    @Test
    public void testDeleteUsageData() {
        assertTrue(salUsageRepository.usageDataExist(USAGE_BATCH_ID_3));
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(USAGE_BATCH_ID_3), UsageStatusEnum.NEW,
                SAL_PRODUCT_FAMILY, null);
        assertEquals(3, salUsageRepository.findDtosByFilter(usageFilter, null, null).size());
        salUsageRepository.deleteUsageData(USAGE_BATCH_ID_3);
        assertFalse(salUsageRepository.usageDataExist(USAGE_BATCH_ID_3));
        assertEquals(1, salUsageRepository.findDtosByFilter(usageFilter, null, null).size());
    }

    @Test
    public void testDeleteByBatchId() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Collections.singleton("b54293db-bfb9-478a-bc13-d70aef5d3ecb"));
        assertEquals(2, salUsageRepository.findCountByFilter(filter));
        salUsageRepository.deleteByBatchId("b54293db-bfb9-478a-bc13-d70aef5d3ecb");
        assertEquals(0, salUsageRepository.findCountByFilter(filter));
    }

    @Test
    public void testFindUsageDataGradeGroups() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Collections.singleton(USAGE_BATCH_ID_1));
        assertEquals(Collections.singletonList(GradeGroupEnum.GRADE9_12),
            salUsageRepository.findUsageDataGradeGroups(filter));
        filter.setUsageBatchesIds(Collections.singleton(USAGE_BATCH_ID_2));
        assertEquals(Collections.singletonList(GradeGroupEnum.GRADE6_8),
            salUsageRepository.findUsageDataGradeGroups(filter));
    }

    @Test
    public void testUpdatePayeeByAccountNumber() {
        List<String> usageIds =
            Arrays.asList("d7764071-935f-4281-a643-656354ccf690", "ad4caa00-c95a-453e-9253-f9810d84d269");
        salUsageRepository.findByIds(usageIds).forEach(usage -> assertNull(usage.getPayee().getAccountNumber()));
        salUsageRepository.updatePayeeByAccountNumber(1000000001L, SCENARIO_ID_1, 7000813806L, USER_NAME);
        salUsageRepository.findByIds(usageIds)
            .forEach(usage -> assertEquals(7000813806L, usage.getPayee().getAccountNumber(), 0));
    }

    @Test
    public void testAddToScenario() {
        List<String> usageIds =
            Arrays.asList("e823d079-3e82-4a5c-bdad-a8707b47b665", "9439530a-d7a9-40a9-a881-f892d13eaf9f");
        salUsageRepository.findByIds(usageIds).forEach(usage -> {
            assertNull(usage.getScenarioId());
            assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        });
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton("87a8b327-6fcc-417f-8fd6-bb6615103b53"), UsageStatusEnum.ELIGIBLE,
                SAL_PRODUCT_FAMILY, null);
        salUsageRepository.addToScenario(SCENARIO_ID_1, usageFilter, USER_NAME);
        salUsageRepository.findByIds(usageIds).forEach(usage -> {
            assertEquals(SCENARIO_ID_1, usage.getScenarioId());
            assertEquals(USER_NAME, usage.getUpdateUser());
            assertEquals(UsageStatusEnum.LOCKED, usage.getStatus());
        });
    }

    @Test
    public void testDeleteFromScenario() {
        List<Usage> usages = salUsageRepository.findByIds(Collections.singletonList(USAGE_ID_4));
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
        usages = salUsageRepository.findByIds(Collections.singletonList(USAGE_ID_4));
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
    public void testCalculateScenario() {
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
    public void testFindCountByScenarioIdAndRhAccountNumber() {
        assertEquals(1, salUsageRepository
            .findCountByScenarioIdAndRhAccountNumber("bafc8277-d9f2-44b6-a68c-9e46165175f8", 1000000026L, null));
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumber() throws IOException {
        List<UsageDto> expectedUsageDtos =
            loadExpectedUsageDtos("json/sal/sal_usage_dto_1d437cb1.json");
        List<UsageDto> actualUsageDtos = salUsageRepository
            .findByScenarioIdAndRhAccountNumber("bafc8277-d9f2-44b6-a68c-9e46165175f8", 1000000026L, null, null, null);
        assertEquals(expectedUsageDtos.size(), actualUsageDtos.size());
        verifyUsageDtos(expectedUsageDtos, actualUsageDtos);
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
            Sets.newHashSet(USAGE_BATCH_ID_1, USAGE_BATCH_ID_2), UsageStatusEnum.NEW, SAL_PRODUCT_FAMILY, null), null,
            new Sort(sortProperty, sortDirection));
        assertEquals(3, CollectionUtils.size(usageDtos));
        assertEquals(detailId, usageDtos.get(0).getId());
    }
}
