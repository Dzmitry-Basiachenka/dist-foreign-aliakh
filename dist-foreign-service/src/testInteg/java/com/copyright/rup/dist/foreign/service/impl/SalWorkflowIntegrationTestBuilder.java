package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.service.impl.SalWorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.SalItemBankCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.SalUsageDataCsvProcessor;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Builder for {@link SalWorkflowIntegrationTest}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/20/20
 *
 * @author Anton Azarenka
 */
@Component
public class SalWorkflowIntegrationTestBuilder implements Builder<Runner> {

    private final Map<String, List<UsageAuditItem>> expectedUsageIdToAuditMap = Maps.newHashMap();
    private final Map<String, String> expectedRmsRequestsToResponses = new LinkedHashMap<>();
    private List<String> predefinedItemBankDetailIds;
    private List<String> predefinedUsageDataDetailIds;
    private String itemBankCsvFile;
    private String usageDataCsvFile;
    private String productFamily;
    private UsageBatch usageBatch;
    private String expectedUsagesJsonFile;

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private ISalUsageService salUsageService;
    @Autowired
    private ServiceTestHelper testHelper;

    SalWorkflowIntegrationTestBuilder withItemBankCsvFile(String csvFile, String... usageIds) {
        this.itemBankCsvFile = csvFile;
        this.predefinedItemBankDetailIds = Arrays.asList(usageIds);
        return this;
    }

    SalWorkflowIntegrationTestBuilder withUsageDataCsvFile(String csvFile, String... usageIds) {
        this.usageDataCsvFile = csvFile;
        this.predefinedUsageDataDetailIds = Arrays.asList(usageIds);
        return this;
    }

    SalWorkflowIntegrationTestBuilder withProductFamily(String productFamilyToUse) {
        this.productFamily = productFamilyToUse;
        return this;
    }

    SalWorkflowIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    SalWorkflowIntegrationTestBuilder expectRmsRights(String rmsRequest, String rmsResponse) {
        this.expectedRmsRequestsToResponses.put(rmsRequest, rmsResponse);
        return this;
    }

    SalWorkflowIntegrationTestBuilder expectUsages(String usagesJsonFile) {
        this.expectedUsagesJsonFile = usagesJsonFile;
        return this;
    }

    SalWorkflowIntegrationTestBuilder expectUsageAudit(String usageId, List<UsageAuditItem> usageAudit) {
        this.expectedUsageIdToAuditMap.put(usageId, usageAudit);
        return this;
    }

    void reset() {
        itemBankCsvFile = null;
        usageDataCsvFile = null;
        predefinedItemBankDetailIds = null;
        predefinedUsageDataDetailIds = null;
        productFamily = null;
        usageBatch = null;
        expectedUsagesJsonFile = null;
        expectedUsageIdToAuditMap.clear();
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private List<UsageDto> actualUsages;

        public void run() throws IOException {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequestsToResponses);
            loadItemBank();
            if (Objects.nonNull(usageDataCsvFile)) {
                loadUsageData();
            }
            UsageFilter filter = new UsageFilter();
            filter.setProductFamily(productFamily);
            filter.setUsageBatchesIds(Collections.singleton(usageBatch.getId()));
            actualUsages = salUsageService.getUsageDtos(filter, null, null);
            verifyUsages();
            expectedUsageIdToAuditMap.forEach(testHelper::assertAudit);
            testHelper.verifyRestServer();
        }

        private void loadItemBank() throws IOException {
            SalItemBankCsvProcessor csvProcessor = csvProcessorFactory.getSalItemBankCsvProcessor();
            ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream(itemBankCsvFile));
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            setPredefinedUsageIds(usages, predefinedItemBankDetailIds);
            List<String> insertedUsageIds = usageBatchService.insertSalBatch(usageBatch, usages);
            assertEquals(predefinedItemBankDetailIds.size(), insertedUsageIds.size());
            List<String> orderedIds = salUsageService.getUsagesByIds(new ArrayList<>(insertedUsageIds)).stream()
                .sorted(Comparator.comparing(Usage::getComment))
                .map(Usage::getId)
                .collect(Collectors.toList());
            salUsageService.sendForMatching(orderedIds, usageBatch.getName());
        }

        private void loadUsageData() throws IOException {
            SalUsageDataCsvProcessor csvProcessor = csvProcessorFactory.getSalUsageDataCsvProcessor(usageBatch.getId());
            ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream(usageDataCsvFile));
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            setPredefinedUsageIds(usages, predefinedUsageDataDetailIds);
            salUsageService.insertUsageDataDetails(usageBatch, usages);
        }

        // predefined usage ids are used, otherwise during every test run the usage ids will be random
        private void setPredefinedUsageIds(List<Usage> usages, List<String> usageIds) {
            assertEquals(usages.size(), usageIds.size());
            AtomicInteger usageId = new AtomicInteger(0);
            usages.forEach(usage -> usage.setId(usageIds.get(usageId.getAndIncrement())));
        }

        private ByteArrayOutputStream getCsvOutputStream(String fileName) throws IOException {
            String csvText = TestUtils.fileToString(this.getClass(), fileName);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.write(csvText, out, StandardCharsets.UTF_8);
            return out;
        }

        private List<UsageDto> loadExpectedUsages(String fileName) throws IOException {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
            });
        }

        private void verifyUsages() throws IOException {
            List<UsageDto> expectedUsages = loadExpectedUsages(expectedUsagesJsonFile);
            assertEquals(expectedUsages.size(), actualUsages.size());
            Map<String, UsageDto> actualUsageIdsToUsages = actualUsages.stream()
                .collect(Collectors.toMap(UsageDto::getId, usageDto -> usageDto));
            expectedUsages.forEach(
                expectedUsage -> assertUsage(expectedUsage, actualUsageIdsToUsages.get(expectedUsage.getId())));
        }

        // TODO {srudak} move to ServiceTestHelper
        private void assertUsage(UsageDto expectedUsage, UsageDto actualUsage) {
            assertNotNull(actualUsage);
            assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
            assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
            assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
            assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
            assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
            assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
            assertEquals(expectedUsage.getComment(), actualUsage.getComment());
            assertSalUsage(expectedUsage.getSalUsage(), actualUsage.getSalUsage());
        }

        private void assertSalUsage(SalUsage expectedUsage, SalUsage actualUsage) {
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
        }
    }
}
