package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IResearchService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclScenarioService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.impl.AaclWorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.ClassifiedUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private final Map<String, List<UsageAuditItem>> expectedUsageCommentToAuditMap = new HashMap<>();
    private final Map<String, String> expectedRmsRequestsToResponses = new LinkedHashMap<>();
    private int expectedUploadedCount;
    private String usagesCsvFile;
    private String productFamily;
    private UsageBatch usageBatch;
    private UsageFilter usageFilter;
    private String expectedUsagesJsonFile;
    private String expectedPaidUsagesJsonFile;
    private String expectedArchivedUsagesJsonFile;
    private List<String> expectedPaidUsageLmDetailIds;
    private AaclFields aaclFields;
    private Set<Long> payeesToExclude;
    private FundPool fundPool;
    private List<FundPoolDetail> fundPoolDetails;
    private String expectedRollupsJson;
    private List<String> expectedRollupsRightholderIds;
    private List<String> expectedLmDetailsJsonFiles;
    private Scenario expectedScenario;
    private String classifiedUsagesCsvFile;
    private List<String> predefinedUsageIds;
    private String expectedCrmRequestJsonFile;
    private String expectedCrmResponseJsonFile;
    private int expectCountScenarioUsages;
    private List<Pair<ScenarioActionTypeEnum, String>> expectedScenarioAudit;

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private IAaclUsageRepository aaclUsageRepository;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IAaclScenarioService aaclScenarioService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IFundPoolService fundPoolService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IResearchService researchService;

    AaclWorkflowIntegrationTestBuilder withUsagesCsvFile(String csvFile, int expectedCount, String... usageIds) {
        this.usagesCsvFile = csvFile;
        this.predefinedUsageIds = List.of(usageIds);
        this.expectedUploadedCount = expectedCount;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectScenarioAudit(List<Pair<ScenarioActionTypeEnum, String>> scenarioAudit) {
        this.expectedScenarioAudit = scenarioAudit;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder withClassifiedUsagesCsvFile(String csvFile) {
        this.classifiedUsagesCsvFile = csvFile;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder withFundPool(FundPool pool) {
        this.fundPool = pool;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder withFundPoolDetails(List<FundPoolDetail> details) {
        this.fundPoolDetails = details;
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

    AaclWorkflowIntegrationTestBuilder expectArchivedUsages(String usagesJsonFile) {
        this.expectedArchivedUsagesJsonFile = usagesJsonFile;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectUsageAudit(String usageComment, List<UsageAuditItem> usageAudit) {
        this.expectedUsageCommentToAuditMap.put(usageComment, usageAudit);
        return this;
    }

    AaclWorkflowIntegrationTestBuilder withUsageFilter(UsageFilter filter) {
        this.usageFilter = filter;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder withAaclFields(AaclFields fields) {
        this.aaclFields = fields;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder withPayeesToExclude(Long... payees) {
        this.payeesToExclude = Set.of(payees);
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderIds = List.of(rollupsRightsholdersIds);
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectRmsRights(String rmsRequest, String rmsResponse) {
        this.expectedRmsRequestsToResponses.put(rmsRequest, rmsResponse);
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectPaidUsagesFromLm(String jsonFile) {
        this.expectedPaidUsagesJsonFile = jsonFile;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectPaidUsageLmDetailIds(String... usageLmDetailIds) {
        this.expectedPaidUsageLmDetailIds = List.of(usageLmDetailIds);
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectScenario(Scenario scenario, int expectCountUsages) {
        expectCountScenarioUsages = expectCountUsages;
        expectedScenario = scenario;
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectLmDetails(String... lmDetailsJsonFile) {
        this.expectedLmDetailsJsonFiles = List.of(lmDetailsJsonFile);
        return this;
    }

    AaclWorkflowIntegrationTestBuilder expectCrmReporting(String requestJsonFile, String responseJsonFile) {
        this.expectedCrmRequestJsonFile = requestJsonFile;
        this.expectedCrmResponseJsonFile = responseJsonFile;
        return this;
    }

    void reset() {
        expectedUsageCommentToAuditMap.clear();
        expectedRmsRequestsToResponses.clear();
        expectedUsagesJsonFile = null;
        classifiedUsagesCsvFile = null;
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

        private List<PaidUsage> archiveUsages;
        private Scenario scenario;
        private Map<String, UsageDto> actualCommentsToUsages;

        public void run() throws IOException, InterruptedException {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequestsToResponses);
            testHelper.expectGetRollups(expectedRollupsJson, expectedRollupsRightholderIds);
            testHelper.expectCrmCall(expectedCrmRequestJsonFile, expectedCrmResponseJsonFile,
                List.of("omOrderDetailNumber", "licenseCreateDate"));
            loadUsageBatch();
            fundPoolService.createAaclFundPool(fundPool, fundPoolDetails);
            sendForClassification();
            loadClassification();
            addToScenario();
            excludeDetailsByPayees();
            scenarioService.submit(scenario, "Submitting scenario for testing purposes");
            scenarioService.approve(scenario, "Approving scenario for testing purposes");
            sendScenarioToLm();
            receivePaidUsagesFromLm();
            sendToCrm();
            if (null != expectedUsagesJsonFile) {
                verifyUsages();
            }
            testHelper.assertPaidAaclUsages(loadExpectedPaidUsages(expectedArchivedUsagesJsonFile));
            verifyUsageAudit();
            testHelper.assertScenarioAudit(scenario.getId(), expectedScenarioAudit);
            testHelper.verifyRestServer();
        }

        private void loadUsageBatch() throws IOException {
            AaclUsageCsvProcessor csvProcessor = csvProcessorFactory.getAaclUsageCsvProcessor();
            ProcessingResult<Usage> result = csvProcessor.process(testHelper.getCsvOutputStream(usagesCsvFile));
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            setPredefinedUsageIds(usages);
            List<String> insertedUsageIds = usageBatchService.insertAaclBatch(usageBatch, usages);
            assertEquals(expectedUploadedCount, insertedUsageIds.size());
            List<String> orderedIds = aaclUsageService.getUsagesByIds(new ArrayList<>(insertedUsageIds)).stream()
                .sorted(Comparator.comparing(Usage::getComment))
                .map(Usage::getId)
                .collect(Collectors.toList());
            aaclUsageService.sendForMatching(orderedIds, usageBatch.getName());
        }

        private void sendForClassification() {
            UsageFilter filter = new UsageFilter();
            filter.setProductFamily("AACL");
            filter.setUsageStatus(UsageStatusEnum.RH_FOUND);
            researchService.sendForClassification(filter, new ByteArrayOutputStream());
        }

        private void loadClassification() throws IOException {
            ClassifiedUsageCsvProcessor processor = csvProcessorFactory.getClassifiedUsageCsvProcessor();
            ProcessingResult<AaclClassifiedUsage> result =
                processor.process(testHelper.getCsvOutputStream(classifiedUsagesCsvFile));
            assertTrue(result.isSuccessful());
            List<AaclClassifiedUsage> classifiedUsages = result.get();
            aaclUsageService.updateClassifiedUsages(classifiedUsages);
        }

        private void addToScenario() {
            usageFilter.setUsageBatchesIds(Set.of(usageBatch.getId()));
            usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
            aaclFields.setFundPoolId(fundPoolService.getAaclNotAttachedToScenario().get(0).getId());
            scenario = aaclScenarioService.createScenario("Test AACL Scenario", aaclFields, "Test Scenario Description",
                usageFilter);
            List<Usage> usages = aaclUsageRepository.findByScenarioId(scenario.getId());
            assertEquals(expectCountScenarioUsages, usages.size());
            assertScenario();
        }

        private void assertScenario() {
            assertEquals(expectedScenario.getName(), scenario.getName());
            assertEquals(expectedScenario.getNetTotal(), scenario.getNetTotal());
            assertEquals(expectedScenario.getGrossTotal(), scenario.getGrossTotal());
            assertEquals(expectedScenario.getServiceFeeTotal(), scenario.getServiceFeeTotal());
            assertEquals(expectedScenario.getStatus(), scenario.getStatus());
            assertEquals(expectedScenario.getDescription(), scenario.getDescription());
            assertAaclFields(expectedScenario.getAaclFields(), scenario.getAaclFields());
        }

        private void excludeDetailsByPayees() {
            if (CollectionUtils.isNotEmpty(payeesToExclude)) {
                aaclUsageService.excludeDetailsFromScenarioByPayees(scenario.getId(), payeesToExclude, "Test Reason");
            }
        }

        private void sendScenarioToLm() {
            aaclScenarioService.sendToLm(scenario);
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

        // predefined usage ids are used, otherwise during every test run the usage ids will be random
        private void setPredefinedUsageIds(List<Usage> usages) {
            AtomicInteger usageId = new AtomicInteger(0);
            usages.forEach(usage -> usage.setId(predefinedUsageIds.get(usageId.getAndIncrement())));
        }

        private void verifyUsages() throws IOException {
            AuditFilter filter = new AuditFilter();
            filter.setProductFamily(productFamily);
            filter.setBatchesIds(Set.of(usageBatch.getId()));
            filter.setStatuses(Set.of(UsageStatusEnum.SENT_TO_LM));
            actualCommentsToUsages = aaclUsageService.getForAudit(filter, null, null).stream()
                .collect(Collectors.toMap(UsageDto::getComment, usageDto -> usageDto));
            List<UsageDto> expectedUsages = testHelper.loadExpectedUsageDtos(expectedUsagesJsonFile);
            assertEquals(expectedUsages.size(), actualCommentsToUsages.size());
            expectedUsages.forEach(expectedUsage -> {
                testHelper.assertUsageDto(expectedUsage, actualCommentsToUsages.get(expectedUsage.getComment()));
                testHelper.assertAaclUsage(expectedUsage.getAaclUsage(),
                    actualCommentsToUsages.get(expectedUsage.getComment()).getAaclUsage());
            });
        }

        private void assertAaclFields(AaclFields expected, AaclFields actual) {
            assertUsageAges(expected.getUsageAges(), actual.getUsageAges());
            assertPublicationTypes(expected.getPublicationTypes(), actual.getPublicationTypes());
            assertDetailLicenseeClasses(expected.getDetailLicenseeClasses(), actual.getDetailLicenseeClasses());
        }

        private List<PaidUsage> loadExpectedPaidUsages(String fileName) throws IOException {
            String content = String.format(TestUtils.fileToString(this.getClass(), fileName), usageBatch.getId());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            return mapper.readValue(content, new TypeReference<List<PaidUsage>>() {
            });
        }

        private void assertPublicationType(PublicationType expectedPublicationType,
                                           PublicationType actualPublicationType) {
            assertEquals(expectedPublicationType.getId(), actualPublicationType.getId());
            assertEquals(expectedPublicationType.getName(), actualPublicationType.getName());
        }

        private void assertUsageAges(List<UsageAge> expected, List<UsageAge> actual) {
            assertEquals(expected.size(), actual.size());
            IntStream.range(0, expected.size()).forEach(i -> assertUsageAge(expected.get(i), actual.get(i)));
        }

        private void assertUsageAge(UsageAge expected, UsageAge actual) {
            assertEquals(expected.getPeriod(), actual.getPeriod());
            assertEquals(expected.getWeight(), actual.getWeight());
        }

        private void assertPublicationTypes(List<PublicationType> expected, List<PublicationType> actual) {
            assertEquals(expected.size(), actual.size());
            IntStream.range(0, expected.size()).forEach(i -> assertPublicationType(expected.get(i), actual.get(i)));
        }

        private void assertDetailLicenseeClasses(List<DetailLicenseeClass> expected, List<DetailLicenseeClass> actual) {
            assertEquals(expected.size(), actual.size());
            IntStream.range(0, expected.size()).forEach(i -> assertDetailLicenseeClass(expected.get(i), actual.get(i)));
        }

        private void assertDetailLicenseeClass(DetailLicenseeClass expected, DetailLicenseeClass actual) {
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getDiscipline(), actual.getDiscipline());
            assertEquals(expected.getEnrollmentProfile(), actual.getEnrollmentProfile());
            assertAggregateLicenseeClass(expected.getAggregateLicenseeClass(), actual.getAggregateLicenseeClass());
        }

        private void assertAggregateLicenseeClass(AggregateLicenseeClass expected, AggregateLicenseeClass actual) {
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getDiscipline(), actual.getDiscipline());
            assertEquals(expected.getEnrollmentProfile(), actual.getEnrollmentProfile());
        }

        private void assertArchivedUsagesCount() {
            List<String> expectedArchivedUsageIds =
                archiveUsages.stream().map(PaidUsage::getId).collect(Collectors.toList());
            List<PaidUsage> actualArchivedUsages =
                usageArchiveRepository.findByIdAndStatus(expectedArchivedUsageIds, UsageStatusEnum.ARCHIVED);
            assertTrue(CollectionUtils.isNotEmpty(actualArchivedUsages));
            assertEquals(CollectionUtils.size(expectedArchivedUsageIds), CollectionUtils.size(actualArchivedUsages));
        }

        private void verifyUsageAudit() {
            AuditFilter filter = new AuditFilter();
            filter.setProductFamily(productFamily);
            filter.setBatchesIds(Set.of(usageBatch.getId()));
            actualCommentsToUsages = aaclUsageService.getForAudit(filter, null, null).stream()
                .collect(Collectors.toMap(UsageDto::getComment, usageDto -> usageDto));
            expectedUsageCommentToAuditMap.forEach((comment, expectedAudit) ->
                testHelper.assertAudit(actualCommentsToUsages.get(comment).getId(), expectedAudit));
        }

    }
}
