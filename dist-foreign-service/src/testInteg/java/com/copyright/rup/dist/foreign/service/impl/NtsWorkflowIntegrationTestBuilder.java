package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.NtsWorkflowIntegrationTestBuilder.Runner;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Builder for {@link NtsWorkflowIntegrationTest}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/06/19
 *
 * @author Pavel Liakh
 */
@Component
public class NtsWorkflowIntegrationTestBuilder implements Builder<Runner> {

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private SqsClientMock sqsClientMock;
    @Autowired
    private ServiceTestHelper testHelper;

    private String expectedRmsRequest;
    private String expectedRmsResponse;
    private String expectedOracleResponse;
    private Long expectedOracleAccountNumber;
    private String expectedPrmResponse;
    private String expectedPreferencesResponse;
    private String expectedPreferencesRightsholderId;
    private String expectedLmDetailsJsonFile;
    private Long expectedPrmAccountNumber;
    private UsageBatch usageBatch;
    private List<Usage> expectedUsages;
    private Scenario expectedScenario;
    private UsageAuditItem expectedAudit;

    NtsWorkflowIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectUsage(Usage... usages) {
        this.expectedUsages = Arrays.asList(usages);
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectAudit(UsageAuditItem audit) {
        this.expectedAudit = audit;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectRmsRights(String request, String response) {
        this.expectedRmsRequest = request;
        this.expectedRmsResponse = response;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectOracleCall(Long accountNumber, String response) {
        this.expectedOracleAccountNumber = accountNumber;
        this.expectedOracleResponse = response;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectPreferences(String preferencesJson, String rightsholderId) {
        this.expectedPreferencesResponse = preferencesJson;
        this.expectedPreferencesRightsholderId = rightsholderId;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectPrmCall(Long accountNumber, String expectedResponse) {
        this.expectedPrmAccountNumber = accountNumber;
        this.expectedPrmResponse = expectedResponse;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectLmDetails(String jsonFile) {
        this.expectedLmDetailsJsonFile = jsonFile;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectScenario(Scenario scenario) {
        this.expectedScenario = scenario;
        return this;
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    void reset() {
        usageBatch = null;
        expectedAudit = null;
        expectedUsages = null;
        expectedPrmAccountNumber = null;
        expectedOracleAccountNumber = null;
        expectedPrmResponse = null;
        expectedRmsRequest = null;
        expectedRmsResponse = null;
        expectedOracleResponse = null;
        expectedPreferencesResponse = null;
        expectedPreferencesRightsholderId = null;
        expectedLmDetailsJsonFile = null;
        this.sqsClientMock.reset();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private List<String> actualUsageIds;
        private Scenario actualScenario;

        public void run() {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequest, expectedRmsResponse);
            if (Objects.nonNull(expectedPrmResponse)) {
                testHelper.expectPrmCall(expectedPrmResponse, expectedPrmAccountNumber);
            }
            if (Objects.nonNull(expectedOracleAccountNumber)) {
                testHelper.expectOracleCall(expectedOracleResponse, expectedOracleAccountNumber);

            }
            if (Objects.nonNull(expectedPreferencesResponse)) {
                testHelper.expectGetPreferences(expectedPreferencesRightsholderId, expectedPreferencesResponse);
            }
            loadNtsBatch();
            createScenario();
            scenarioService.submit(actualScenario, "Submitting actualScenario for testing purposes");
            scenarioService.approve(actualScenario, "Approving actualScenario for testing purposes");
            sendScenarioToLm();
            assertScenario();
            assertArchivedUsages();
            assertAudit();
            testHelper.verifyRestServer();
        }

        private void loadNtsBatch() {
            actualUsageIds = usageBatchService.insertNtsBatch(usageBatch, RupContextUtils.getUserName());
            usageBatchService.getAndSendForGettingRights(actualUsageIds, usageBatch.getName());
        }

        private void createScenario() {
            UsageFilter usageFilter = new UsageFilter();
            usageFilter.setUsageBatchesIds(Collections.singleton(usageBatch.getId()));
            usageFilter.setProductFamilies(Collections.singleton("NTS"));
            NtsFields ntsFields = new NtsFields();
            ntsFields.setRhMinimumAmount(new BigDecimal("300"));
            actualScenario = scenarioService.createNtsScenario("Test Scenario", ntsFields, null, usageFilter);
        }

        private void sendScenarioToLm() {
            scenarioService.sendToLm(actualScenario);
            sqsClientMock.assertSendMessages("fda-test-sf-detail.fifo",
                Collections.singletonList(TestUtils.fileToString(this.getClass(), expectedLmDetailsJsonFile)),
                Collections.singletonList("detail_id"), ImmutableMap.of("source", "FDA"));
        }

        private void assertScenario() {
            actualScenario = scenarioService.getScenarios().stream()
                .filter(scenario -> actualScenario.getId().equals(scenario.getId()))
                .findAny().get();
            assertNotNull(actualScenario);
            assertEquals(expectedScenario.getNtsFields().getRhMinimumAmount(),
                actualScenario.getNtsFields().getRhMinimumAmount());
            assertEquals(expectedScenario.getStatus(), actualScenario.getStatus());
        }

        private void assertAudit() {
            actualUsageIds.forEach(Objects.isNull(expectedAudit)
                ? usageId -> assertTrue(CollectionUtils.isEmpty(usageAuditService.getUsageAudit(usageId)))
                : usageId -> testHelper.assertAudit(usageId, Collections.singletonList(expectedAudit)));
        }

        private void assertArchivedUsages() {
            List<UsageDto> actualUsages =
                usageArchiveRepository.findByScenarioIdAndRhAccountNumber(actualScenario.getId(),
                    1000023401L, null, null, null);
            assertEquals(1, CollectionUtils.size(actualUsages));
            assertUsage(expectedUsages.get(0), actualUsages.get(0));
        }

        private void assertUsage(Usage expectedUsage, UsageDto actualUsage) {
            assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
            assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
            assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
            assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
            assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
            assertEquals(expectedUsage.getPublisher(), actualUsage.getPublisher());
            assertEquals(expectedUsage.getPublicationDate(), actualUsage.getPublicationDate());
            assertEquals(expectedUsage.getMarket(), actualUsage.getMarket());
            assertEquals(expectedUsage.getMarketPeriodFrom(), actualUsage.getMarketPeriodFrom());
            assertEquals(expectedUsage.getMarketPeriodTo(), actualUsage.getMarketPeriodTo());
            assertEquals(expectedUsage.getAuthor(), actualUsage.getAuthor());
            assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
            assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
            assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
            assertEquals(expectedUsage.getServiceFeeAmount(), actualUsage.getServiceFeeAmount());
            assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
            assertEquals(expectedUsage.getRightsholder().getAccountNumber(), actualUsage.getRhAccountNumber());
        }
    }
}
