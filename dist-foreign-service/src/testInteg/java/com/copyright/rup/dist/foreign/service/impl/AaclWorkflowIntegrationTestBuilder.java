package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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

    private final Map<String, List<UsageAuditItem>> expectedUsageIdToAuditMap = Maps.newHashMap();
    private List<String> predefinedUsageIds;
    private String usagesCsvFile;
    private String productFamily;
    private UsageBatch usageBatch;
    private String expectedUsagesJsonFile;

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private ServiceTestHelper testHelper;

    AaclWorkflowIntegrationTestBuilder withUsagesCsvFile(String csvFile, String... usageIds) {
        this.usagesCsvFile = csvFile;
        this.predefinedUsageIds = Arrays.asList(usageIds);
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

    AaclWorkflowIntegrationTestBuilder expectUsageAudit(String usageId, List<UsageAuditItem> usageAudit) {
        this.expectedUsageIdToAuditMap.put(usageId, usageAudit);
        return this;
    }

    void reset() {
        usagesCsvFile = null;
        predefinedUsageIds = null;
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
            loadUsageBatch();
            UsageFilter filter = new UsageFilter();
            filter.setProductFamily(productFamily);
            filter.setUsageBatchesIds(Collections.singleton(usageBatch.getId()));
            actualUsages = usageService.getUsageDtos(filter, null, null);
            verifyUsages();
            expectedUsageIdToAuditMap.forEach(testHelper::assertAudit);
        }

        private void loadUsageBatch() throws IOException {
            AaclUsageCsvProcessor csvProcessor = csvProcessorFactory.getAaclUsageCsvProcessor();
            ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            setPredefinedUsageIds(usages);
            int usagesInsertedCount = usageBatchService.insertAaclBatch(usageBatch, usages);
            assertEquals(predefinedUsageIds.size(), usagesInsertedCount);
        }

        // predefined usage ids are used, otherwise during every test run the usage ids will be random
        private void setPredefinedUsageIds(List<Usage> usages) {
            AtomicInteger usageId = new AtomicInteger(0);
            usages.forEach(usage -> usage.setId(predefinedUsageIds.get(usageId.getAndIncrement())));
        }

        private ByteArrayOutputStream getCsvOutputStream() throws IOException {
            String csvText = TestUtils.fileToString(this.getClass(), usagesCsvFile);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.write(csvText, out, StandardCharsets.UTF_8);
            return out;
        }

        private void verifyUsages() throws IOException {
            List<UsageDto> expectedUsages = loadExpectedUsages(expectedUsagesJsonFile);
            assertEquals(expectedUsages.size(), actualUsages.size());
            Map<String, UsageDto> actualUsageIdsToUsages = actualUsages.stream()
                .collect(Collectors.toMap(UsageDto::getId, usageDto -> usageDto));
            expectedUsages.forEach(
                expectedUsage -> assertUsage(expectedUsage, actualUsageIdsToUsages.get(expectedUsage.getId())));
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
            assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
            assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
            assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
            assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
            assertEquals(expectedUsage.getComment(), actualUsage.getComment());
            AaclUsage expectedAaclUsage = expectedUsage.getAaclUsage();
            AaclUsage actualAaclUsage = actualUsage.getAaclUsage();
            assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
            assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
            assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
            assertEquals(expectedAaclUsage.getUsagePeriod(), actualAaclUsage.getUsagePeriod());
        }
    }
}
