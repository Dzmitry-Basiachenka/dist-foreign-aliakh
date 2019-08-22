package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.WorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Builder for {@link WorkflowIntegrationTest}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/11/18
 *
 * @author Ihar Suvorau
 */
@Component
public class WorkflowIntegrationTestBuilder implements Builder<Runner> {

    private final Map<String, List<Pair<UsageActionTypeEnum, String>>> expectedUsageLmDetailIdToAuditMap
        = Maps.newHashMap();
    private final Map<String, String> expectedRmsRequestsToResponses = new LinkedHashMap<>();
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private SqsClientMock sqsClientMock;
    @Autowired
    private ServiceTestHelper testHelper;

    private String usagesCsvFile;
    private List<String> predefinedUsageIds;
    private String productFamily;
    private UsageBatch usageBatch;
    private UsageFilter usageFilter;
    private int expectedInsertedUsagesCount;
    private String expectedPreferencesJson;
    private List<String> expectedPreferencesRightholderIds;
    private String expectedRollupsJson;
    private List<String> expectedRollupsRightsholdersIds;
    private int expecteLmDetailsMessagesCount;
    private List<String> expectedLmDetailsJsonFiles;
    private String expectedPaidUsagesJsonFile;
    private List<String> expectedPaidUsageLmDetailids;
    private List<String> expectedCrmGetRightsDistributionCccEventIds;
    private String expectedCrmInsertRightsDistributionRequestJsonFile;
    private String expectedCrmInsertRightsDistributionResponseJsonFile;
    private String expectedUsagesJsonFile;

     WorkflowIntegrationTestBuilder withUsagesCsvFile(String csvFile, String... usageIds) {
        this.usagesCsvFile = csvFile;
        this.predefinedUsageIds = Arrays.asList(usageIds);
        return this;
    }

     WorkflowIntegrationTestBuilder withProductFamily(String productFamilyToUse) {
        this.productFamily = productFamilyToUse;
        return this;
    }

     WorkflowIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

     WorkflowIntegrationTestBuilder withUsageFilter(UsageFilter filter) {
        this.usageFilter = filter;
        return this;
    }

     WorkflowIntegrationTestBuilder expectInsertedUsagesCount(int insertedUsagesCount) {
        this.expectedInsertedUsagesCount = insertedUsagesCount;
        return this;
    }

    public WorkflowIntegrationTestBuilder expectPreferences(String preferencesJson, String... rightholderIds) {
        this.expectedPreferencesJson = preferencesJson;
        this.expectedPreferencesRightholderIds = Arrays.asList(rightholderIds);
        return this;
    }

    public WorkflowIntegrationTestBuilder expectRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightsholdersIds = Arrays.asList(rollupsRightsholdersIds);
        return this;
    }

     WorkflowIntegrationTestBuilder expectLmDetails(int messagesCount, String... lmDetailsJsonFile) {
        this.expecteLmDetailsMessagesCount = messagesCount;
        this.expectedLmDetailsJsonFiles = Arrays.asList(lmDetailsJsonFile);
        return this;
    }

     WorkflowIntegrationTestBuilder expectPaidUsagesFromLm(String jsonFile) {
        this.expectedPaidUsagesJsonFile = jsonFile;
        return this;
    }

     WorkflowIntegrationTestBuilder expectPaidUsageLmDetailIds(String... usageLmDetailIds) {
        this.expectedPaidUsageLmDetailids = Arrays.asList(usageLmDetailIds);
        return this;
    }

    WorkflowIntegrationTestBuilder expectCrmGetRightsDistribution(String... cccEventIds) {
        this.expectedCrmGetRightsDistributionCccEventIds = Arrays.asList(cccEventIds);
        return this;
    }

     WorkflowIntegrationTestBuilder expectCrmInsertRightsDistribution(String requestJsonFile, String responseJsonFile) {
        this.expectedCrmInsertRightsDistributionRequestJsonFile = requestJsonFile;
        this.expectedCrmInsertRightsDistributionResponseJsonFile = responseJsonFile;
        return this;
    }

     WorkflowIntegrationTestBuilder expectUsageAudit(String usageLmDetailId,
                                                           List<Pair<UsageActionTypeEnum, String>> usageAudit) {
        this.expectedUsageLmDetailIdToAuditMap.put(usageLmDetailId, usageAudit);
        return this;
    }

     WorkflowIntegrationTestBuilder expectRmsRights(String rmsRequest, String rmsResponse) {
        this.expectedRmsRequestsToResponses.put(rmsRequest, rmsResponse);
        return this;
    }

    public WorkflowIntegrationTestBuilder expectUsages(String usagesJsonFile) {
        this.expectedUsagesJsonFile = usagesJsonFile;
        return this;
    }

    void reset() {
        this.expectedUsageLmDetailIdToAuditMap.clear();
        this.expectedRmsRequestsToResponses.clear();
        this.sqsClientMock.reset();
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private List<PaidUsage> archiveUsages;

        private Scenario scenario;

        public void run() throws IOException, InterruptedException {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequestsToResponses);
            testHelper.expectGetRollups(expectedRollupsJson, expectedRollupsRightsholdersIds);
            testHelper.expectGetPreferences(expectedPreferencesJson, expectedPreferencesRightholderIds);
            testHelper.expectCrmGetRightsDistribution(expectedCrmGetRightsDistributionCccEventIds);
            testHelper.expectCrmInsertRightsDistribution(expectedCrmInsertRightsDistributionRequestJsonFile,
                expectedCrmInsertRightsDistributionResponseJsonFile,
                Arrays.asList("omOrderDetailNumber", "licenseCreateDate"));
            loadUsageBatch();
            addToScenario();
            scenarioService.submit(scenario, "Submitting scenario for testing purposes");
            scenarioService.approve(scenario, "Approving scenario for testing purposes");
            sendScenarioToLm();
            receivePaidUsagesFromLm();
            sendUsagesToCrm();
            testHelper.assertPaidUsages(loadExpectedUsages(expectedUsagesJsonFile));
            verifyScenarioAudit();
            verifyUsagesAudit();
            testHelper.verifyRestServer();
        }

        private void loadUsageBatch() throws IOException {
            UsageCsvProcessor csvProcessor = csvProcessorFactory.getUsageCsvProcessor(productFamily);
            ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            setPredefinedUsageIds(usages);
            int usagesInsertedCount = usageBatchService.insertFasBatch(usageBatch, usages);
            assertEquals(expectedInsertedUsagesCount, usagesInsertedCount);
            usageBatchService.sendForMatching(usages);
            usageBatchService.sendForGettingRights(usages, usageBatch.getName());
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

        private void addToScenario() {
            scenario = scenarioService.createScenario("Test Scenario", "Test Scenario Description", usageFilter);
            List<Usage> usages = usageService.getUsagesByScenarioId(scenario.getId());
            assertEquals(expectedInsertedUsagesCount, usages.size());
        }

        void sendScenarioToLm() {
            scenarioService.sendToLm(scenario);
            List<String> lmUsageMessages = Lists.newArrayListWithExpectedSize(expecteLmDetailsMessagesCount);
            expectedLmDetailsJsonFiles.forEach(lmDetailsFile ->
                lmUsageMessages.add(TestUtils.fileToString(this.getClass(), lmDetailsFile)));
            sqsClientMock.assertSendMessages("fda-test-sf-detail.fifo", lmUsageMessages,
                Collections.emptyList(), ImmutableMap.of("source", "FDA"));
        }

        private void receivePaidUsagesFromLm() throws InterruptedException {
            testHelper.receivePaidUsagesFromLm(expectedPaidUsagesJsonFile);
            assertPaidUsagesCountByLmDetailIds();
        }

        private void sendUsagesToCrm() {
            usageService.sendToCrm();
            assertArchivedUsagesCount();
        }

        private void assertPaidUsagesCountByLmDetailIds() {
            List<String> paidIds = usageArchiveRepository.findPaidIds();
            List<PaidUsage> actualUsages = usageArchiveRepository.findByIdAndStatus(paidIds, UsageStatusEnum.PAID);
            assertTrue(CollectionUtils.isNotEmpty(actualUsages));
            assertEquals(CollectionUtils.size(expectedPaidUsageLmDetailids), CollectionUtils.size(actualUsages));
            List<String> actualLmDetailIds =
                actualUsages.stream().map(PaidUsage::getLmDetailId).distinct().collect(Collectors.toList());
            assertEquals(CollectionUtils.size(expectedPaidUsageLmDetailids), CollectionUtils.size(actualLmDetailIds));
            assertTrue(expectedPaidUsageLmDetailids.containsAll(actualLmDetailIds));
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

        private List<PaidUsage> loadExpectedUsages(String fileName) throws IOException {
            String content = TestUtils.fileToString(this.getClass(), fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<PaidUsage>>() {
            });
        }

        private void verifyScenarioAudit() {
            assertEquals(buildExpectedScenarioAudit(), buildActualScenarioAudit());
        }

        private List<Pair<ScenarioActionTypeEnum, String>> buildExpectedScenarioAudit() {
            return Arrays.asList(
                Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, ""),
                Pair.of(ScenarioActionTypeEnum.SUBMITTED, "Submitting scenario for testing purposes"),
                Pair.of(ScenarioActionTypeEnum.APPROVED, "Approving scenario for testing purposes"),
                Pair.of(ScenarioActionTypeEnum.SENT_TO_LM, ""),
                Pair.of(ScenarioActionTypeEnum.ARCHIVED, "All usages from scenario have been sent to CRM"));
        }

        private List<Pair<ScenarioActionTypeEnum, String>> buildActualScenarioAudit() {
            return scenarioAuditService.getActions(scenario.getId()).stream()
                .sorted(Comparator.comparing(ScenarioAuditItem::getCreateDate))
                .map(a -> Pair.of(a.getActionType(), a.getActionReason()))
                .collect(Collectors.toList());
        }

        private void verifyUsagesAudit() {
            assertEquals(expectedUsageLmDetailIdToAuditMap, buildUsageAuditMapForAssertion());
        }

        private Map<String, List<Pair<UsageActionTypeEnum, String>>> buildUsageAuditMapForAssertion() {
            return expectedUsageLmDetailIdToAuditMap.keySet().stream()
                .collect(Collectors.toMap(
                    usageLmDetailIdId -> usageLmDetailIdId,
                    usageLmDetailIdId ->
                        getUsageAuditActionTypeAndMessagePairs(
                            archiveUsages.stream()
                                .filter(usage -> usageLmDetailIdId.equals(usage.getLmDetailId())).findFirst().get()
                                .getId())
                ));
        }

        private List<Pair<UsageActionTypeEnum, String>> getUsageAuditActionTypeAndMessagePairs(String usageId) {
            return usageAuditService.getUsageAudit(usageId).stream()
                .map(usageAuditItem -> Pair.of(usageAuditItem.getActionType(), usageAuditItem.getActionReason()))
                .collect(Collectors.toList());
        }
    }
}
