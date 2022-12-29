package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclciLicenseTypeEnum;
import com.copyright.rup.dist.foreign.domain.AclciUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclciUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Integration test for {@link AclciUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/08/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclciUsageRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "aclci-usage-repository-integration-test/";
    private static final String ACLCI_PRODUCT_FAMILY = "ACLCI";
    private static final String USAGE_ID_1 = "5262bc87-e5b4-447b-9294-122e41f01c7e";
    private static final String USAGE_ID_2 = "4c81e637-3fe4-4777-a486-5dd8e43215b8";
    private static final String USAGE_ID_3 = "50fe503f-3cf9-49f4-a4ae-817e979eb613";
    private static final String USAGE_BATCH_ID_1 = "228c1b83-a69d-4935-9940-4ec51192b140";
    private static final String USAGE_BATCH_ID_2 = "a6b4c77e-7ee3-48db-b845-202fe6884899";
    private static final String DETAIL_ID_KEY = "detailId";
    private static final String USER_NAME = "user@copyright.com";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IAclciUsageRepository aclciUsageRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "insert.groovy")
    public void testInsert() {
        Usage expectedUsage = loadExpectedUsages(List.of("json/aclci/aclci_usage_5262bc87.json")).get(0);
        aclciUsageRepository.insert(expectedUsage);
        List<Usage> actualUsages = aclciUsageRepository.findByIds(List.of(USAGE_ID_1));
        assertEquals(1, actualUsages.size());
        verifyUsage(expectedUsage, actualUsages.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-to-eligible-by-ids.groovy")
    public void testUpdateToEligibleByIdsNoWorkUpdates() {
        Set<String> usageIds = Set.of(USAGE_ID_2, USAGE_ID_3);
        aclciUsageRepository.updateToEligibleByIds(usageIds, 1000011450L, null, USER_NAME);
        List<Usage> actualUsages = aclciUsageRepository.findByIds(new ArrayList<>(usageIds));
        assertEquals(2, actualUsages.size());
        actualUsages.forEach(usage -> {
            assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
            assertEquals(1000011450L, usage.getRightsholder().getAccountNumber(), 0);
            assertNotNull(usage.getWrWrkInst());
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-to-eligible-by-ids.groovy")
    public void testUpdateToEligibleByIdsWithWorkUpdates() {
        Set<String> usageIds = Set.of(USAGE_ID_2, USAGE_ID_3);
        aclciUsageRepository.updateToEligibleByIds(usageIds, 1000011450L, 269040891L, USER_NAME);
        List<Usage> actualUsages = aclciUsageRepository.findByIds(new ArrayList<>(usageIds));
        assertEquals(2, actualUsages.size());
        actualUsages.forEach(usage -> {
            assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
            assertEquals(1000011450L, usage.getRightsholder().getAccountNumber(), 0);
            assertEquals(269040891L, usage.getWrWrkInst(), 0);
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-dtos-by-filter.groovy")
    public void testFindCountDtosByFilter() {
        assertEquals(1, aclciUsageRepository.findCountByFilter(
            buildUsageFilter(Set.of(USAGE_BATCH_ID_1), UsageStatusEnum.NEW, ACLCI_PRODUCT_FAMILY,
                Set.of(AclciLicenseTypeEnum.CURR_REPUB_K12))));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-dtos-by-filter.groovy")
    public void testFindDtosByFilter() throws IOException {
        verifyAclciUsageDto(
            loadExpectedUsageDtos(List.of("json/aclci/aclci_usage_dto_1_find_by_filter.json")).get(0),
            aclciUsageRepository.findDtosByFilter(buildUsageFilter(Set.of(USAGE_BATCH_ID_1), UsageStatusEnum.NEW,
                ACLCI_PRODUCT_FAMILY, Set.of(AclciLicenseTypeEnum.CURR_REPUB_K12)), null,
                new Sort(DETAIL_ID_KEY, Direction.ASC)).get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-dtos-by-filter.groovy")
    public void testSortingDtosFindByFilter() {
        UsageDto dto1 = loadExpectedUsageDtos(List.of("json/aclci/aclci_usage_dto_1_find_by_filter.json")).get(0);
        UsageDto dto2 = loadExpectedUsageDtos(List.of("json/aclci/aclci_usage_dto_2_find_by_filter.json")).get(0);
        assertSortingUsageDto(dto1, dto2, "detailId");
        assertSortingUsageDto(dto1, dto1, "status");
        assertSortingUsageDto(dto1, dto2, "licenseType");
        assertSortingUsageDto(dto1, dto1, "productFamily");
        assertSortingUsageDto(dto1, dto2, "batchName");
        assertSortingUsageDto(dto2, dto1, "periodEndDate");
        assertSortingUsageDto(dto2, dto1, "coveragePeriod");
        assertSortingUsageDto(dto2, dto1, "licenseeAccountNumber");
        assertSortingUsageDto(dto1, dto2, "licenseeName");
        assertSortingUsageDto(dto1, dto2, "rhAccountNumber");
        assertSortingUsageDto(dto2, dto1, "rhName");
        assertSortingUsageDto(dto2, dto1, "wrWrkInst");
        assertSortingUsageDto(dto2, dto1, "systemTitle");
        assertSortingUsageDto(dto2, dto1, "standardNumber");
        assertSortingUsageDto(dto1, dto2, "standardNumberType");
        assertSortingUsageDto(dto2, dto1, "workTitle");
        assertSortingUsageDto(dto1, dto2, "reportedMediaType");
        assertSortingUsageDto(dto1, dto2, "mediaTypeWeight");
        assertSortingUsageDto(dto2, dto1, "reportedArticle");
        assertSortingUsageDto(dto1, dto2, "reportedStandardNumber");
        assertSortingUsageDto(dto2, dto1, "reportedAuthor");
        assertSortingUsageDto(dto2, dto1, "reportedPublisher");
        assertSortingUsageDto(dto2, dto1, "reportedPublicationDate");
        assertSortingUsageDto(dto2, dto1, "reportedGrade");
        assertSortingUsageDto(dto1, dto2, "comment");
    }

    private UsageFilter buildUsageFilter(Set<String> usageBatchIds, UsageStatusEnum status, String productFamily,
                                         Set<AclciLicenseTypeEnum> licenseTypes) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(usageBatchIds);
        usageFilter.setUsageStatus(status);
        usageFilter.setProductFamily(productFamily);
        usageFilter.setLicenseTypes(licenseTypes);
        return usageFilter;
    }

    private void assertSortingUsageDto(UsageDto detailAsc, UsageDto detailDesc, String sortProperty) {
        List<UsageDto> usageDtos = aclciUsageRepository.findDtosByFilter(buildUsageFilter(
            Set.of(USAGE_BATCH_ID_1, USAGE_BATCH_ID_2), UsageStatusEnum.NEW, ACLCI_PRODUCT_FAMILY,
            Set.of(AclciLicenseTypeEnum.CURR_REPUB_K12, AclciLicenseTypeEnum.CURR_REUSE_K12)),
            null, new Sort(sortProperty, Direction.ASC));
        verifyAclciUsageDto(detailAsc, usageDtos.get(0));
        usageDtos = aclciUsageRepository.findDtosByFilter(buildUsageFilter(
            Set.of(USAGE_BATCH_ID_1, USAGE_BATCH_ID_2), UsageStatusEnum.NEW, ACLCI_PRODUCT_FAMILY,
            Set.of(AclciLicenseTypeEnum.CURR_REPUB_K12, AclciLicenseTypeEnum.CURR_REUSE_K12)),
            null, new Sort(sortProperty, Direction.DESC));
        verifyAclciUsageDto(detailDesc, usageDtos.get(0));
    }

    private void verifyUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        verifyUsageIgnoringId(expectedUsage, actualUsage);
    }

    private void verifyUsageIgnoringId(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getRightsholder().getName(), actualUsage.getRightsholder().getName());
        assertEquals(expectedUsage.getRightsholder().getId(), actualUsage.getRightsholder().getId());
        assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
            actualUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        verifyAclciUsage(expectedUsage.getAclciUsage(), actualUsage.getAclciUsage());
    }

    private void verifyAclciUsageDto(UsageDto expected, UsageDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getProductFamily(), actual.getProductFamily());
        assertEquals(expected.getBatchName(), actual.getBatchName());
        assertEquals(expected.getPeriodEndDate(), actual.getPeriodEndDate());
        assertEquals(expected.getRhAccountNumber(), actual.getRhAccountNumber());
        assertEquals(expected.getRhName(), actual.getRhName());
        assertEquals(expected.getWrWrkInst(), actual.getWrWrkInst());
        assertEquals(expected.getSystemTitle(), actual.getSystemTitle());
        assertEquals(expected.getStandardNumber(), actual.getStandardNumber());
        assertEquals(expected.getStandardNumberType(), actual.getStandardNumberType());
        assertEquals(expected.getWorkTitle(), actual.getWorkTitle());
        assertEquals(expected.getComment(), actual.getComment());
        verifyAclciUsage(expected.getAclciUsage(), actual.getAclciUsage());
    }

    private void verifyAclciUsage(AclciUsage expectedUsage, AclciUsage actualUsage) {
        assertEquals(expectedUsage.getLicenseeAccountNumber(), actualUsage.getLicenseeAccountNumber());
        assertEquals(expectedUsage.getLicenseeName(), actualUsage.getLicenseeName());
        assertEquals(expectedUsage.getCoveragePeriod(), actualUsage.getCoveragePeriod());
        assertEquals(expectedUsage.getLicenseType(), actualUsage.getLicenseType());
        assertEquals(expectedUsage.getReportedMediaType(), actualUsage.getReportedMediaType());
        assertEquals(expectedUsage.getMediaTypeWeight(), actualUsage.getMediaTypeWeight());
        assertEquals(expectedUsage.getReportedArticle(), actualUsage.getReportedArticle());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getReportedAuthor(), actualUsage.getReportedAuthor());
        assertEquals(expectedUsage.getReportedPublisher(), actualUsage.getReportedPublisher());
        assertEquals(expectedUsage.getReportedPublicationDate(), actualUsage.getReportedPublicationDate());
        assertEquals(expectedUsage.getReportedGrade(), actualUsage.getReportedGrade());
        assertEquals(expectedUsage.getGradeGroup(), actualUsage.getGradeGroup());
        assertEquals(expectedUsage.getBatchPeriodEndDate(), actualUsage.getBatchPeriodEndDate());
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
        List<UsageDto> usageDtos = new ArrayList<>();
        fileNames.forEach(fileName -> {
            try {
                String content = TestUtils.fileToString(this.getClass(), fileName);
                usageDtos.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UsageDto>>() {
                }));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        });
        return usageDtos;
    }
}
