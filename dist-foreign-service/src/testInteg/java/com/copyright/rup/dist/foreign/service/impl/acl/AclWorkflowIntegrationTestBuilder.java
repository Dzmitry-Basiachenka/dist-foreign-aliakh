package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.UdmValueStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioUsageRepository;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IPublicationTypeService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclFundPoolService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantSetService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IAclUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBaselineValueService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmProxyValueService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;
import com.copyright.rup.dist.foreign.service.impl.acl.AclWorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;

import com.google.common.collect.ImmutableSet;

import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Builder for {@link AclWorkflowIntegrationTest}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/21/21
 *
 * @author Uladzislau Shalamitski
 */
@Component
public class AclWorkflowIntegrationTestBuilder implements Builder<Runner> {

    private final List<String> uploadedUdmValuesIds = new ArrayList<>();
    @Autowired
    private IUdmBatchService udmBatchService;
    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IUdmValueService udmValueService;
    @Autowired
    private IUdmBaselineValueService udmBaselineValueService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUdmProxyValueService udmProxyValueService;
    @Autowired
    private IAclUsageBatchService aclUsageBatchService;
    @Autowired
    private IAclFundPoolService aclFundPoolService;
    @Autowired
    private IAclGrantSetService aclGrantSetService;
    @Autowired
    private IAclScenarioService aclScenarioService;
    @Autowired
    private IAclUsageService aclUsageService;
    @Autowired
    private IPublicationTypeService publicationTypeService;
    @Autowired
    private ILicenseeClassService licenseeClassService;
    @Autowired
    private IAclScenarioUsageRepository aclScenarioUsageRepository;

    private List<UdmUsageDto> udmUsageDtos;
    private UdmBatch expectedUdmBatch;
    private String pathToUsagesToUpload;
    private String pathToExpectedUsages;
    private String pathToExpectedValues;
    private String pathToExpectedValuesBaseline;
    private List<String> uploadedUdmUsagesIds = new ArrayList<>();
    private List<String> pathsToExpectedUdmUsageAuditItems = new ArrayList<>();
    private List<String> pathsToExpectedUdmValueAuditItems = new ArrayList<>();
    private Map<String, String> expectedRmsRequestToResponseMap = new HashMap<>();
    private Map<Long, String> expectedPrmAccountNumberToResponseMap = new HashMap<>();
    private String expectedIneligibleParentCallJson;
    private String expectedRollupsJson;
    private List<String> expectedRollupsRightholderIds = new ArrayList<>();
    private AclUsageBatch aclUsageBatch;
    private String pathToExpectedLdmtFundPoolDetails;
    private AclFundPool aclFundPool;
    private AclGrantSet aclGrantSet;
    private List<UsageAge> usageAges = new ArrayList<>();
    private List<AclPublicationType> publicationTypes = new ArrayList<>();
    private List<DetailLicenseeClass> detailLicenseeClasses = new ArrayList<>();
    private AclScenario aclScenario;
    private List<String> expectedLmDetailsJson = new ArrayList<>();
    private String pathToExpectedAclScenario;
    private String pathToExpectedAclScenarioDetails;

    AclWorkflowIntegrationTestBuilder withUdmBatch(UdmBatch udmBatch) {
        this.expectedUdmBatch = udmBatch;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withExpectedUsages(String pathToUsages) {
        this.pathToExpectedUsages = pathToUsages;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withExpectedValues(String pathToValues) {
        this.pathToExpectedValues = pathToValues;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withExpectedValuesBaseline(String pathToValuesBaseline) {
        this.pathToExpectedValuesBaseline = pathToValuesBaseline;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withUsagesToUpload(String usagesToUpload) {
        this.pathToUsagesToUpload = usagesToUpload;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withRmsRequests(Map<String, String> rmsRequestToResponseMap) {
        this.expectedRmsRequestToResponseMap = rmsRequestToResponseMap;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withPrmRequests(Map<Long, String> prmAccountNumberToResponse) {
        this.expectedPrmAccountNumberToResponseMap = prmAccountNumberToResponse;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withPrmIneligibleParentCall(String ineligibleParentCallJson) {
        this.expectedIneligibleParentCallJson = ineligibleParentCallJson;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withPrmRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderIds = List.of(rollupsRightsholdersIds);
        return this;
    }

    AclWorkflowIntegrationTestBuilder withUdmUsageAuditItems(List<String> pathsToAuditItems) {
        this.pathsToExpectedUdmUsageAuditItems = pathsToAuditItems;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withUdmValueAuditItems(List<String> pathsToAuditItems) {
        this.pathsToExpectedUdmValueAuditItems = pathsToAuditItems;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withAclUsageBatch(AclUsageBatch usageBatch) {
        this.aclUsageBatch = usageBatch;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withLdmtFundPoolDetails(String pathToLdmtFundPoolDetails) {
        this.pathToExpectedLdmtFundPoolDetails = pathToLdmtFundPoolDetails;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withAclFundPool(AclFundPool fundPool) {
        this.aclFundPool = fundPool;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withAclGrantSet(AclGrantSet grantSet) {
        this.aclGrantSet = grantSet;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withDefaultAclScenarioUsageAges() {
        this.usageAges = aclUsageService.getDefaultUsageAgesWeights();
        return this;
    }

    AclWorkflowIntegrationTestBuilder withDefaultAclScenarioPubTypes() {
        this.publicationTypes = publicationTypeService.getAclHistoricalPublicationTypes();
        return this;
    }

    AclWorkflowIntegrationTestBuilder withDefaultAclScenarioDetLicClasses() {
        this.detailLicenseeClasses = licenseeClassService.getDetailLicenseeClasses(FdaConstants.ACL_PRODUCT_FAMILY);
        return this;
    }

    AclWorkflowIntegrationTestBuilder withAclScenario(AclScenario scenario) {
        this.aclScenario = scenario;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withLmDetails(String... lmDetailsJson) {
        this.expectedLmDetailsJson = List.of(lmDetailsJson);
        return this;
    }

    AclWorkflowIntegrationTestBuilder withExpectedAclScenario(String pathToAclScenario) {
        this.pathToExpectedAclScenario = pathToAclScenario;
        return this;
    }

    AclWorkflowIntegrationTestBuilder withExpectedAclScenarioDetails(String pathToAclScenarioDetails) {
        this.pathToExpectedAclScenarioDetails = pathToAclScenarioDetails;
        return this;
    }

    void reset() {
        this.expectedUdmBatch = null;
        this.pathToUsagesToUpload = null;
        this.pathToExpectedUsages = null;
        this.pathToExpectedValues = null;
        this.pathToExpectedValuesBaseline = null;
        this.uploadedUdmUsagesIds.clear();
        this.uploadedUdmValuesIds.clear();
        this.expectedRmsRequestToResponseMap.clear();
        this.expectedPrmAccountNumberToResponseMap.clear();
        this.expectedIneligibleParentCallJson = null;
        this.expectedRollupsJson = null;
        this.expectedRollupsRightholderIds.clear();
        this.pathsToExpectedUdmUsageAuditItems.clear();
        this.pathsToExpectedUdmValueAuditItems.clear();
        this.aclUsageBatch = null;
        this.pathToExpectedLdmtFundPoolDetails = null;
        this.aclFundPool = null;
        this.aclGrantSet = null;
        this.usageAges.clear();
        this.publicationTypes.clear();
        this.detailLicenseeClasses.clear();
        this.aclScenario = null;
        this.expectedLmDetailsJson.clear();
        this.pathToExpectedAclScenario = null;
        this.pathToExpectedAclScenarioDetails = null;
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

        public void run() throws IOException, InterruptedException {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequestToResponseMap);
            expectedPrmAccountNumberToResponseMap.forEach(
                (accountNumber, response) -> testHelper.expectPrmCall(response, accountNumber));
            testHelper.expectPrmIneligibleParentCall(expectedIneligibleParentCallJson);
            testHelper.expectGetRollups(expectedRollupsJson, expectedRollupsRightholderIds);
            loadUdmBatch();
            assignUsages();
            publishUsagesToBaseline();
            populateValueBatch();
            publishValuesToBaseline();
            assertUdmUsages();
            assertUdmValues();
            assertUdmValuesBaseline();
            assertUdmUsageAudit();
            assertUdmValueAudit();
            createAclUsageBatch();
            createAclFundPool();
            createAclGrantSet();
            createAclScenario();
            submitAclScenario();
            approveAclScenario();
            sendAclScenarioToLm();
            verifyAclScenario();
            verifyAclScenarioDetails();
            verifyAclScenarioAudit();
            testHelper.verifyRestServer();
        }

        private void loadUdmBatch() throws IOException {
            UdmCsvProcessor csvProcessor = csvProcessorFactory.getUdmCsvProcessor();
            ProcessingResult<UdmUsage> result =
                csvProcessor.process(testHelper.getCsvOutputStream(pathToUsagesToUpload));
            assertTrue(result.isSuccessful());
            List<UdmUsage> udmUsages = result.get();
            udmBatchService.insertUdmBatch(expectedUdmBatch, udmUsages);
            udmUsageService.sendForMatching(udmUsages);
            uploadedUdmUsagesIds = udmUsages.stream().map(BaseEntity::getId).collect(Collectors.toList());
        }

        private void assignUsages() {
            UdmUsageFilter filter = new UdmUsageFilter();
            filter.setPeriods(Set.of(expectedUdmBatch.getPeriod()));
            filter.setUsageStatus(UsageStatusEnum.RH_FOUND);
            udmUsageDtos = udmUsageService.getUsageDtos(filter, null, null)
                .stream()
                .peek(udmUsageDto -> udmUsageDto.setAssignee("user@copyright.com"))
                .collect(Collectors.toList());
            udmUsageService.assignUsages(new HashSet<>(udmUsageDtos));
        }

        private void publishUsagesToBaseline() {
            Map<UdmUsageDto, List<String>> dtoToActionReasonsMap = udmUsageDtos.stream()
                .collect(Collectors.toMap(udmUsageDto -> {
                    udmUsageDto.setStatus(UsageStatusEnum.ELIGIBLE);
                    return udmUsageDto;
                }, udmUsageDto -> List.of()));
            udmUsageService.updateUsages(dtoToActionReasonsMap, false, "Reason");
            udmUsageService.publishUdmUsagesToBaseline(expectedUdmBatch.getPeriod());
        }

        private void populateValueBatch() {
            udmValueService.populateValueBatch(expectedUdmBatch.getPeriod());
        }

        private void publishValuesToBaseline() {
            UdmValueFilter filter = new UdmValueFilter();
            filter.setPeriods(ImmutableSet.of(expectedUdmBatch.getPeriod()));
            udmValueService.getValueDtos(filter, null, null)
                .stream()
                .filter(udmValueDto -> Objects.isNull(udmValueDto.getPublicationType()))
                .forEach(udmValueDto -> {
                    uploadedUdmValuesIds.add(udmValueDto.getId());
                    PublicationType publicationType = new PublicationType();
                    publicationType.setId("076f2c40-f524-405d-967a-3840df2b57df");
                    publicationType.setName("NP");
                    udmValueDto.setPublicationType(publicationType);
                    udmValueDto.setPrice(new BigDecimal("100"));
                    udmValueDto.setPriceInUsd(new BigDecimal("150"));
                    udmValueDto.setCurrency("EUR");
                    udmValueDto.setCurrencyExchangeRate(new BigDecimal("1.5"));
                    udmValueDto.setPriceFlag(true);
                    udmValueDto.setContent(BigDecimal.TEN);
                    udmValueDto.setContentFlag(true);
                    udmValueDto.setContentUnitPrice(new BigDecimal("15"));
                    udmValueDto.setStatus(UdmValueStatusEnum.RESEARCH_COMPLETE);
                    List<String> actionReasons = List.of(
                        "The field 'Price' was edited. Old Value is not specified. New Value is '100'",
                        "The field 'Currency' was edited. Old Value is not specified. New Value is 'EUR'");
                    udmValueService.updateValue(udmValueDto, actionReasons);
                });
            udmProxyValueService.calculateProxyValues(202006);
            udmValueService.publishToBaseline(expectedUdmBatch.getPeriod());
        }

        private void assertUdmUsages() throws IOException {
            List<UdmUsage> actualUsages = udmUsageService.getUdmUsagesByIds(uploadedUdmUsagesIds);
            List<UdmUsage> expectedUsages = testHelper.loadExpectedUdmUsages(pathToExpectedUsages);
            testHelper.assertUdmUsages(expectedUsages, actualUsages);
        }

        private void assertUdmValues() throws IOException {
            UdmValueFilter filter = new UdmValueFilter();
            filter.setPeriods(ImmutableSet.of(expectedUdmBatch.getPeriod()));
            List<UdmValueDto> actualValueDtos =
                udmValueService.getValueDtos(filter, null, new Sort("wrWrkInst", Sort.Direction.ASC));
            List<UdmValueDto> expectedValueDtos = testHelper.loadExpectedUdmValueDto(pathToExpectedValues);
            testHelper.assertUdmValueDtos(expectedValueDtos, actualValueDtos);
        }

        private void assertUdmValuesBaseline() throws IOException {
            UdmBaselineValueFilter filter = new UdmBaselineValueFilter();
            filter.setPeriods(Set.of(expectedUdmBatch.getPeriod()));
            List<UdmValueBaselineDto> actualUdmValues = udmBaselineValueService.getValueDtos(filter, null,
                new Sort("wrWrkInst", Sort.Direction.ASC));
            List<UdmValueBaselineDto> expectedUdmValues =
                testHelper.loadExpectedValueBaselineDto(pathToExpectedValuesBaseline);
            testHelper.assertValueBaselineDtos(expectedUdmValues, actualUdmValues);
        }

        private void assertUdmUsageAudit() throws IOException {
            assertEquals(pathsToExpectedUdmUsageAuditItems.size(), uploadedUdmUsagesIds.size());
            for (int index = 0; index < pathsToExpectedUdmUsageAuditItems.size(); index++) {
                List<UsageAuditItem> usageAuditItems =
                    testHelper.loadExpectedUsageAuditItems(pathsToExpectedUdmUsageAuditItems.get(index));
                testHelper.assertUdmUsageAudit(uploadedUdmUsagesIds.get(index), usageAuditItems);
            }
        }

        private void assertUdmValueAudit() throws IOException {
            assertEquals(pathsToExpectedUdmValueAuditItems.size(), uploadedUdmValuesIds.size());
            for (int index = 0; index < pathsToExpectedUdmValueAuditItems.size(); index++) {
                List<UdmValueAuditItem> udmValueAuditItems =
                    testHelper.loadExpectedUdmValueAuditItems(pathsToExpectedUdmValueAuditItems.get(index));
                testHelper.assertUdmValueAudit(uploadedUdmValuesIds.get(index), udmValueAuditItems);
            }
        }

        private void createAclUsageBatch() {
            aclUsageBatchService.insert(aclUsageBatch);
        }

        private void createAclFundPool() throws InterruptedException {
            if (!aclFundPool.isManualUploadFlag()) {
                testHelper.receiveLdtmDetailsFromOracle(Objects.requireNonNull(pathToExpectedLdmtFundPoolDetails));
            }
            aclFundPoolService.insertLdmtAclFundPool(aclFundPool);
        }

        private void createAclGrantSet() {
            aclGrantSetService.insert(aclGrantSet);
        }

        private void createAclScenario() {
            aclScenario.setUsageBatchId(aclUsageBatch.getId());
            aclScenario.setFundPoolId(aclFundPool.getId());
            aclScenario.setGrantSetId(aclGrantSet.getId());
            aclScenario.setUsageAges(usageAges);
            aclScenario.setPublicationTypes(publicationTypes);
            aclScenario.setDetailLicenseeClasses(detailLicenseeClasses);
            assertTrue(aclScenarioService.getFundPoolDetailsNotToBeDistributed(
                aclScenario.getUsageBatchId(), aclScenario.getFundPoolId(),
                aclScenario.getGrantSetId(), aclScenario.getDetailLicenseeClasses())
                .isEmpty());
            aclScenarioService.insertScenario(aclScenario);
        }

        private void submitAclScenario() {
            aclScenarioService.changeScenarioState(aclScenario, ScenarioStatusEnum.SUBMITTED,
                ScenarioActionTypeEnum.SUBMITTED, "Submitting scenario for testing purposes");
        }

        private void approveAclScenario() {
            aclScenarioService.changeScenarioState(aclScenario, ScenarioStatusEnum.APPROVED,
                ScenarioActionTypeEnum.APPROVED, "Approving scenario for testing purposes");
        }

        private void sendAclScenarioToLm() {
            aclScenarioService.sendToLm(aclScenario);
            testHelper.sendScenarioToLm(expectedLmDetailsJson, List.of("detail_id"),
                aclScenario.getId(), aclScenario.getName(), "ACL");
        }

        private void verifyAclScenario() throws IOException {
            AclScenario expectedScenario = testHelper.loadExpectedAclScenario(pathToExpectedAclScenario);
            AclScenario actualScenario = aclScenarioService.getScenarioById(aclScenario.getId());
            actualScenario.setFundPoolId(expectedScenario.getFundPoolId());
            actualScenario.setUsageBatchId(expectedScenario.getUsageBatchId());
            actualScenario.setGrantSetId(expectedScenario.getGrantSetId());
            testHelper.verifyAclScenario(expectedScenario, actualScenario);
        }

        private void verifyAclScenarioDetails() throws IOException {
            List<AclScenarioDetail> scenarioDetails =
                aclScenarioUsageRepository.findScenarioDetailsByScenarioId(aclScenario.getId());
            testHelper.verifyAclScenarioDetails(pathToExpectedAclScenarioDetails, scenarioDetails);
        }

        private void verifyAclScenarioAudit() {
            List<Pair<ScenarioActionTypeEnum, String>> expectedAudit = List.of(
                Pair.of(ScenarioActionTypeEnum.ARCHIVED, ""),
                Pair.of(ScenarioActionTypeEnum.SENT_TO_LM, ""),
                Pair.of(ScenarioActionTypeEnum.APPROVED, "Approving scenario for testing purposes"),
                Pair.of(ScenarioActionTypeEnum.SUBMITTED, "Submitting scenario for testing purposes"),
                Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, ""));
            testHelper.assertAclScenarioAudit(aclScenario.getId(), expectedAudit);
        }
    }
}
