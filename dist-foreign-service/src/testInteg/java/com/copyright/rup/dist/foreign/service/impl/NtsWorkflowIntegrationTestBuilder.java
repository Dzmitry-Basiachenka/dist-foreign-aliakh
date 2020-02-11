package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
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
    private IUsageService usageService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private SqsClientMock sqsClientMock;
    @Autowired
    private ServiceTestHelper testHelper;

    private String expectedRollupsJson;
    private String expectedRollupsRightholderId;
    private String expectedRmsRequest;
    private String expectedRmsResponse;
    private String expectedOracleResponse;
    private Long expectedOracleAccountNumber;
    private String expectedPrmResponse;
    private String expectedPreferencesResponse;
    private String expectedPreferencesRightsholderId;
    private String expectedLmDetailsJsonFile;
    private String expectedPaidUsages;
    private String expectedCrmRequest;
    private String crmResponse;
    private Long expectedPrmAccountNumber;
    private UsageBatch usageBatch;
    private List<PaidUsage> expectedUsages;
    private Scenario expectedScenario;
    private Long expectedRroAccountNumber;
    private String expectedPrmResponseForUpdateRro;

    NtsWorkflowIntegrationTestBuilder expectRollups(String rollupsJson, String rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderId = rollupsRightsholdersIds;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectUsage(PaidUsage... usages) {
        this.expectedUsages = Arrays.asList(usages);
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

    NtsWorkflowIntegrationTestBuilder expectPaidInfo(String jsonFile) {
        this.expectedPaidUsages = jsonFile;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectCrmCall(String expectedRequest, String response) {
        this.expectedCrmRequest = expectedRequest;
        this.crmResponse = response;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectScenario(Scenario scenario) {
        this.expectedScenario = scenario;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectPrmCallForUpdateRro(Long accountNumber, String expectedResponse) {
        this.expectedRroAccountNumber = accountNumber;
        this.expectedPrmResponseForUpdateRro = expectedResponse;
        return this;
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    void reset() {
        usageBatch = null;
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
        expectedRollupsJson = null;
        expectedRollupsRightholderId = null;
        this.sqsClientMock.reset();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private List<String> actualUsageIds;
        private Scenario actualScenario;

        public void run() throws InterruptedException {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequest, expectedRmsResponse);
            testHelper.expectPrmCall(expectedPrmResponseForUpdateRro, expectedRroAccountNumber);
            testHelper.expectPrmCall(expectedPrmResponse, expectedPrmAccountNumber);

            if (Objects.nonNull(expectedOracleAccountNumber)) {
                testHelper.expectOracleCall(expectedOracleResponse, expectedOracleAccountNumber);
            }
            if (Objects.nonNull(expectedPreferencesResponse)) {
                testHelper.expectGetPreferences(expectedPreferencesRightsholderId, expectedPreferencesResponse);
            }
            if (Objects.nonNull(expectedRollupsJson)) {
                testHelper.expectGetRollups(expectedRollupsJson,
                    Collections.singletonList(expectedRollupsRightholderId));
            }
            testHelper.expectCrmCall(Objects.requireNonNull(expectedCrmRequest), Objects.requireNonNull(crmResponse),
                Arrays.asList("omOrderDetailNumber", "licenseCreateDate"));
            loadNtsBatch();
            createScenario();
            scenarioService.submit(actualScenario, "Submitting actualScenario for testing purposes");
            scenarioService.approve(actualScenario, "Approving actualScenario for testing purposes");
            sendScenarioToLm();
            receivePaidUsagesFromLm();
            usageService.sendToCrm();
            assertScenario();
            testHelper.assertPaidUsages(expectedUsages);
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
            usageFilter.setProductFamily("NTS");
            NtsFields ntsFields = new NtsFields();
            ntsFields.setRhMinimumAmount(new BigDecimal("300"));
            actualScenario = scenarioService.createNtsScenario("Test Scenario", ntsFields, null, usageFilter);
        }

        private void sendScenarioToLm() {
            scenarioService.sendNtsToLm(actualScenario);
            sqsClientMock.assertSendMessages("fda-test-sf-detail.fifo",
                Collections.singletonList(TestUtils.fileToString(this.getClass(), expectedLmDetailsJsonFile)),
                Collections.singletonList("detail_id"), ImmutableMap.of("source", "FDA"));
        }

        private void receivePaidUsagesFromLm() throws InterruptedException {
            testHelper.receivePaidUsagesFromLm(expectedPaidUsages,
                Collections.singletonList(getArchivedUsages().get(0).getId()));
        }

        private void assertScenario() {
            actualScenario = scenarioService.getScenarios("NTS").stream()
                .filter(scenario -> actualScenario.getId().equals(scenario.getId()))
                .findAny().get();
            assertNotNull(actualScenario);
            assertEquals(expectedScenario.getNtsFields().getRhMinimumAmount(),
                actualScenario.getNtsFields().getRhMinimumAmount());
            assertEquals(expectedScenario.getStatus(), actualScenario.getStatus());
        }

        private void assertAudit() {
            actualUsageIds.forEach(
                usageId -> assertTrue(CollectionUtils.isEmpty(usageAuditService.getUsageAudit(usageId))));
        }

        private List<UsageDto> getArchivedUsages() {
            List<UsageDto> actualUsages =
                usageArchiveRepository.findByScenarioIdAndRhAccountNumber(actualScenario.getId(),
                    1000023401L, null, null, null);
            assertEquals(1, CollectionUtils.size(actualUsages));
            return actualUsages;
        }
    }
}
