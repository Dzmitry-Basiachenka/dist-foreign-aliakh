package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.impl.AaclWorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Maps;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Builder for {@link AaclWorkflowIntegrationTest}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 1/15/20
 *
 * @author Stanislau Rudak
 */
@Component
public class AaclWorkflowIntegrationTestBuilder implements Builder<Runner> {

    private final Map<String, List<UsageAuditItem>> expectedUsageCommentToAuditMap = Maps.newHashMap();
    private final Map<String, String> expectedRmsRequestsToResponses = new LinkedHashMap<>();
    private Long expectedPrmAccountNumber;
    private String expectedPrmResponse;
    private int expectedUploadedCount;
    private String usagesCsvFile;
    private String productFamily;
    private UsageBatch usageBatch;
    private String expectedUsagesJsonFile;

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IAaclUsageService usageService;
    @Autowired
    private ServiceTestHelper testHelper;

    AaclWorkflowIntegrationTestBuilder withUsagesCsvFile(String csvFile, int expectedCount) {
        this.usagesCsvFile = csvFile;
        this.expectedUploadedCount = expectedCount;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder withProductFamily(String productFamilyToUse) {
        this.productFamily = productFamilyToUse;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectUsages(String usagesJsonFile) {
        this.expectedUsagesJsonFile = usagesJsonFile;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectUsageAudit(String usageComment, List<UsageAuditItem> usageAudit) {
        this.expectedUsageCommentToAuditMap.put(usageComment, usageAudit);
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectRmsRights(String rmsRequest, String rmsResponse) {
        this.expectedRmsRequestsToResponses.put(rmsRequest, rmsResponse);
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectPrmCall(Long accountNumber, String expectedResponse) {
        this.expectedPrmAccountNumber = accountNumber;
        this.expectedPrmResponse = expectedResponse;
        return this;
    }

    void reset() {
        expectedUsageCommentToAuditMap.clear();
        expectedRmsRequestsToResponses.clear();
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private Map<String, UsageDto> actualCommentsToUsages;

        public void run() throws IOException {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequestsToResponses);
            testHelper.expectPrmCall(expectedPrmResponse, expectedPrmAccountNumber);
            loadUsageBatch();
            UsageFilter filter = new UsageFilter();
            filter.setProductFamily(productFamily);
            filter.setUsageBatchesIds(Collections.singleton(usageBatch.getId()));
            actualCommentsToUsages = usageService.getUsageDtos(filter, null, null).stream()
                .collect(Collectors.toMap(UsageDto::getComment, usageDto -> usageDto));
            verifyUsages();
            expectedUsageCommentToAuditMap.forEach((comment, expectedAudit) ->
                testHelper.assertAudit(actualCommentsToUsages.get(comment).getId(), expectedAudit));
            testHelper.verifyRestServer();
        }

        private void loadUsageBatch() throws IOException {
            AaclUsageCsvProcessor csvProcessor = csvProcessorFactory.getAaclUsageCsvProcessor();
            ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            List<String> insertedUsageIds = usageBatchService.insertAaclBatch(usageBatch, usages);
            assertEquals(expectedUploadedCount, insertedUsageIds.size());
            List<String> orderedIds = usageService.getUsagesByIds(insertedUsageIds).stream()
                .sorted(Comparator.comparing(Usage::getComment))
                .map(Usage::getId)
                .collect(Collectors.toList());
            usageBatchService.sendAaclForMatching(orderedIds, usageBatch.getName());
        }

        private ByteArrayOutputStream getCsvOutputStream() throws IOException {
            String csvText = TestUtils.fileToString(this.getClass(), usagesCsvFile);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.write(csvText, out, StandardCharsets.UTF_8);
            return out;
        }

        private void verifyUsages() throws IOException {
            List<UsageDto> expectedUsages = loadExpectedUsages(expectedUsagesJsonFile);
            assertEquals(expectedUsages.size(), actualCommentsToUsages.size());
            expectedUsages.forEach(
                expectedUsage -> assertUsage(expectedUsage, actualCommentsToUsages.get(expectedUsage.getComment())));
        }

        private List<UsageDto> loadExpectedUsages(String fileName) throws IOException {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
            });
        }

        // TODO {srudak} try to use ServiceTestHelper here
        private void assertUsage(UsageDto expectedUsage, UsageDto actualUsage) {
            assertNotNull(actualUsage);
            assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
            assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
            assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
            assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
            assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
            assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
            assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
            assertEquals(expectedUsage.getComment(), actualUsage.getComment());
            assertAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
        }

        private void assertAaclUsage(AaclUsage expectedAaclUsage, AaclUsage actualAaclUsage) {
            assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
            assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
            assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
            assertEquals(expectedAaclUsage.getUsagePeriod(), actualAaclUsage.getUsagePeriod());
            assertPublicationType(expectedAaclUsage.getPublicationType(), actualAaclUsage.getPublicationType());
            assertEquals(expectedAaclUsage.getOriginalPublicationType(), actualAaclUsage.getOriginalPublicationType());
            assertEquals(expectedAaclUsage.getPublicationTypeWeight(), actualAaclUsage.getPublicationTypeWeight());
            assertEquals(expectedAaclUsage.getDetailLicenseeClassId(), actualAaclUsage.getDetailLicenseeClassId());
            assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
            assertEquals(expectedAaclUsage.isBaseline(), actualAaclUsage.isBaseline());
        }

        private void assertPublicationType(PublicationType expectedPublicationType,
                                           PublicationType actualPublicationType) {
            assertEquals(expectedPublicationType.getId(), actualPublicationType.getId());
            assertEquals(expectedPublicationType.getName(), actualPublicationType.getName());
        }
    }
}
