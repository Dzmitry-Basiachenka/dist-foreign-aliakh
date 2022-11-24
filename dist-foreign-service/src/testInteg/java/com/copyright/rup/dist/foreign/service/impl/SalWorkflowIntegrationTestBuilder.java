package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
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
    private final Map<String, List<UsageAuditItem>> expectedUsageLmDetailIdToAuditMap = Maps.newHashMap();
    private final Map<String, String> expectedRmsRequestsToResponses = new LinkedHashMap<>();
    private String expectedRollupsJson;
    private List<String> expectedRollupsRightholderIds;
    private List<String> predefinedItemBankDetailIds;
    private List<String> predefinedUsageDataDetailIds;
    private String itemBankCsvFile;
    private String usageDataCsvFile;
    private String fundPoolId;
    private String productFamily;
    private UsageBatch usageBatch;
    private String expectedUsagesJsonFile;
    private List<String> expectedLmDetailsJsonFiles;
    private String expectedPaidUsagesJsonFile;
    private List<String> expectedPaidUsageLmDetailIds;
    private String expectedArchivedUsagesJsonFile;
    private String expectedCrmRequestJsonFile;
    private String expectedCrmResponseJsonFile;

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private ISalUsageService salUsageService;
    @Autowired
    private ISalScenarioService salScenarioService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private IUsageAuditService usageAuditService;

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

    SalWorkflowIntegrationTestBuilder withFundPoolId(String fundId) {
        this.fundPoolId = fundId;
        return this;
    }

    SalWorkflowIntegrationTestBuilder expectRmsRights(String rmsRequest, String rmsResponse) {
        this.expectedRmsRequestsToResponses.put(rmsRequest, rmsResponse);
        return this;
    }

    SalWorkflowIntegrationTestBuilder expectRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderIds = Arrays.asList(rollupsRightsholdersIds);
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

    SalWorkflowIntegrationTestBuilder expectPostDistributionUsageAudit(String usageId,
                                                                       List<UsageAuditItem> usageAudit) {
        this.expectedUsageLmDetailIdToAuditMap.put(usageId, usageAudit);
        return this;
    }

    SalWorkflowIntegrationTestBuilder expectLmDetails(String... lmDetailsJsonFile) {
        this.expectedLmDetailsJsonFiles = Arrays.asList(lmDetailsJsonFile);
        return this;
    }

    SalWorkflowIntegrationTestBuilder expectPaidUsagesFromLm(String jsonFile) {
        this.expectedPaidUsagesJsonFile = jsonFile;
        return this;
    }

    SalWorkflowIntegrationTestBuilder expectPaidUsageLmDetailIds(String... usageLmDetailIds) {
        this.expectedPaidUsageLmDetailIds = Arrays.asList(usageLmDetailIds);
        return this;
    }

    SalWorkflowIntegrationTestBuilder expectArchivedUsages(String usagesJsonFile) {
        this.expectedArchivedUsagesJsonFile = usagesJsonFile;
        return this;
    }

    SalWorkflowIntegrationTestBuilder expectCrmReporting(String requestJsonFile, String responseJsonFile) {
        this.expectedCrmRequestJsonFile = requestJsonFile;
        this.expectedCrmResponseJsonFile = responseJsonFile;
        return this;
    }

    void reset() {
        this.expectedUsageIdToAuditMap.clear();
        this.expectedUsageLmDetailIdToAuditMap.clear();
        this.expectedRmsRequestsToResponses.clear();
        this.expectedRollupsJson = null;
        this.expectedRollupsRightholderIds = null;
        this.predefinedItemBankDetailIds = null;
        this.predefinedUsageDataDetailIds = null;
        this.itemBankCsvFile = null;
        this.usageDataCsvFile = null;
        this.fundPoolId = null;
        this.productFamily = null;
        this.usageBatch = null;
        this.expectedUsagesJsonFile = null;
        this.expectedLmDetailsJsonFiles = null;
        this.expectedPaidUsagesJsonFile = null;
        this.expectedPaidUsageLmDetailIds = null;
        this.expectedArchivedUsagesJsonFile = null;
        this.expectedCrmRequestJsonFile = null;
        this.expectedCrmResponseJsonFile = null;
        testHelper.reset();
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private final List<String> usageIds = new ArrayList<>();
        private Scenario scenario;
        private List<PaidUsage> archiveUsages;

        public void run() throws IOException, InterruptedException {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequestsToResponses);
            testHelper.expectGetRollups(expectedRollupsJson, expectedRollupsRightholderIds);
            testHelper.expectCrmCall(expectedCrmRequestJsonFile, expectedCrmResponseJsonFile,
                Arrays.asList("omOrderDetailNumber", "licenseCreateDate"));
            loadItemBank();
            if (Objects.nonNull(usageDataCsvFile)) {
                loadUsageData();
            }
            addToScenario();
            verifyUsages();
            scenarioService.submit(scenario, "Submitting scenario for testing purposes");
            scenarioService.approve(scenario, "Approving scenario for testing purposes");
            sendScenarioToLm();
            receivePaidUsagesFromLm();
            sendToCrm();
            testHelper.assertPaidSalUsages(loadExpectedPaidUsages(expectedArchivedUsagesJsonFile));
            expectedUsageIdToAuditMap.forEach(testHelper::assertAudit);
            verifyPostDistributionUsageAudit();
            testHelper.verifyRestServer();
        }

        private void verifyPostDistributionUsageAudit() {
            Map<String, List<UsageAuditItem>> expectedPostDistributionUsageIdToAuditMap =
                expectedUsageLmDetailIdToAuditMap.keySet().stream()
                    .collect(Collectors.toMap(
                        usageLmDetailIdId ->
                            archiveUsages.stream()
                                .filter(usage -> usageLmDetailIdId.equals(usage.getLmDetailId())).findFirst().get()
                                .getId(),
                        usageLmDetailIdId ->
                            usageAuditService.getUsageAudit(
                                archiveUsages.stream()
                                    .filter(usage -> usageLmDetailIdId.equals(usage.getLmDetailId())).findFirst().get()
                                    .getId())
                    ));
            expectedPostDistributionUsageIdToAuditMap.forEach(testHelper::assertAudit);
        }

        private void loadItemBank() throws IOException {
            SalItemBankCsvProcessor csvProcessor = csvProcessorFactory.getSalItemBankCsvProcessor();
            ProcessingResult<Usage> result = csvProcessor.process(testHelper.getCsvOutputStream(itemBankCsvFile));
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            setPredefinedUsageIds(usages, predefinedItemBankDetailIds);
            List<String> insertedUsageIds = usageBatchService.insertSalBatch(usageBatch, usages);
            assertEquals(predefinedItemBankDetailIds.size(), insertedUsageIds.size());
            usageIds.addAll(predefinedItemBankDetailIds);
            List<String> orderedIds = salUsageService.getUsagesByIds(new ArrayList<>(insertedUsageIds)).stream()
                .sorted(Comparator.comparing(Usage::getComment))
                .map(Usage::getId)
                .collect(Collectors.toList());
            salUsageService.sendForMatching(orderedIds, usageBatch.getName());
        }

        private void loadUsageData() throws IOException {
            SalUsageDataCsvProcessor csvProcessor = csvProcessorFactory.getSalUsageDataCsvProcessor(usageBatch.getId());
            ProcessingResult<Usage> result = csvProcessor.process(testHelper.getCsvOutputStream(usageDataCsvFile));
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            setPredefinedUsageIds(usages, predefinedUsageDataDetailIds);
            usageIds.addAll(predefinedUsageDataDetailIds);
            usageBatchService.addUsageDataToSalBatch(usageBatch, usages);
        }

        private void addToScenario() {
            UsageFilter filter = new UsageFilter();
            filter.setUsageBatchesIds(Collections.singleton(usageBatch.getId()));
            filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
            filter.setProductFamily(productFamily);
            scenario = salScenarioService.createScenario("Test SAL Scenario", fundPoolId,
                "Test Scenario Description", filter);
            assertScenario(scenario);
        }

        private void sendScenarioToLm() {
            salScenarioService.sendToLm(scenario);
            testHelper.sendScenarioToLm(expectedLmDetailsJsonFiles);
        }

        private void receivePaidUsagesFromLm() throws InterruptedException {
            testHelper.receivePaidUsagesFromLm(expectedPaidUsagesJsonFile);
            assertPaidUsagesCountByLmDetailIds();
        }

        private void sendToCrm() {
            usageService.sendToCrm();
            assertArchivedUsagesCount();
        }

        private void assertScenario(Scenario expectedScenario) {
            assertEquals("Test SAL Scenario", expectedScenario.getName());
            assertEquals(ScenarioStatusEnum.IN_PROGRESS, expectedScenario.getStatus());
            assertEquals("Test Scenario Description", expectedScenario.getDescription());
            assertEquals(fundPoolId, expectedScenario.getSalFields().getFundPoolId());
        }

        // predefined usage ids are used, otherwise during every test run the usage ids will be random
        private void setPredefinedUsageIds(List<Usage> usages, List<String> predefinedDetailIds) {
            assertEquals(usages.size(), predefinedDetailIds.size());
            AtomicInteger usageId = new AtomicInteger(0);
            usages.forEach(usage -> usage.setId(predefinedDetailIds.get(usageId.getAndIncrement())));
        }

        private List<PaidUsage> loadExpectedPaidUsages(String fileName) throws IOException {
            String content = String.format(TestUtils.fileToString(this.getClass(), fileName), usageBatch.getId());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<PaidUsage>>() {
            });
        }

        private void verifyUsages() throws IOException {
            List<Usage> expectedUsages = testHelper.loadExpectedUsages(expectedUsagesJsonFile);
            assertEquals(expectedUsages.size(), usageIds.size());
            Map<String, Usage> actualUsageIdsToUsages = salUsageService.getUsagesByIds(usageIds).stream()
                .collect(Collectors.toMap(Usage::getId, Function.identity()));
            expectedUsages.forEach(expectedUsage -> {
                testHelper.assertUsage(expectedUsage, actualUsageIdsToUsages.get(expectedUsage.getId()));
                testHelper.assertSalUsage(expectedUsage.getSalUsage(),
                    actualUsageIdsToUsages.get(expectedUsage.getId()).getSalUsage());
            });
        }

        private void assertPaidUsagesCountByLmDetailIds() {
            List<String> paidIds = usageArchiveRepository.findPaidIds();
            List<PaidUsage> actualUsages = usageArchiveRepository.findByIdAndStatus(paidIds, UsageStatusEnum.PAID);
            assertTrue(CollectionUtils.isNotEmpty(actualUsages));
            assertEquals(CollectionUtils.size(expectedPaidUsageLmDetailIds), CollectionUtils.size(actualUsages));
            List<String> actualLmDetailIds =
                actualUsages.stream().map(PaidUsage::getLmDetailId).distinct().collect(Collectors.toList());
            assertEquals(CollectionUtils.size(expectedPaidUsageLmDetailIds), CollectionUtils.size(actualLmDetailIds));
            assertTrue(expectedPaidUsageLmDetailIds.containsAll(actualLmDetailIds));
            archiveUsages = actualUsages;
        }

        private void assertArchivedUsagesCount() {
            List<String> expectedArchivedUsageIds =
                archiveUsages.stream().map(PaidUsage::getId).collect(Collectors.toList());
            List<PaidUsage> actualArchivedUsages =
                usageArchiveRepository.findByIdAndStatus(expectedArchivedUsageIds, UsageStatusEnum.ARCHIVED);
            assertTrue(CollectionUtils.isNotEmpty(actualArchivedUsages));
            assertEquals(CollectionUtils.size(expectedArchivedUsageIds), CollectionUtils.size(actualArchivedUsages));
        }
    }
}
