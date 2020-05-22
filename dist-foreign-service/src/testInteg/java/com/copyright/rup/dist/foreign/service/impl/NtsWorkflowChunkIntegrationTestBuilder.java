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
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.service.impl.NtsWorkflowChunkIntegrationTestBuilder.Runner;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Builder for {@link NtsWorkflowChunkIntegrationTest}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/06/19
 *
 * @author Pavel Liakh
 */
@Component
public class NtsWorkflowChunkIntegrationTestBuilder implements Builder<Runner> {

    @Autowired
    private INtsScenarioService ntsScenarioService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private INtsUsageService ntsUsageService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private SqsClientMock sqsClientMock;
    @Autowired
    private ServiceTestHelper testHelper;

    private Map<List<String>, String> expectedRhIdToPrmResponseMap;
    private Map<String, String> expectedRmsRequestResponseMap;
    private Map<Long, String> expectedAccountToPrmResponseMap;
    private Map<List<Long>, String> expectedAccountToOracleResponseMap;
    private String expectedRollupsJson;
    private String expectedRollupsRightholderId;
    private String expectedLmDetailsJsonFile;
    private String expectedPaidUsages;
    private String expectedCrmRequest;
    private String crmResponse;
    private UsageBatch usageBatch;
    private List<PaidUsage> expectedUsages;
    private Scenario expectedScenario;
    private Long expectedRroAccountNumber;
    private String expectedPrmResponseForUpdateRro;

    NtsWorkflowChunkIntegrationTestBuilder expectRollups(String rollupsJson, String rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderId = rollupsRightsholdersIds;
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder expectUsage(PaidUsage... usages) {
        this.expectedUsages = Arrays.asList(usages);
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder expectRmsRights(Map<String, String> rmsRequestResponseMap) {
        this.expectedRmsRequestResponseMap = rmsRequestResponseMap;
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder expectOracleCall(Map<List<Long>, String> accountToOracleResponseMap) {
        this.expectedAccountToOracleResponseMap = accountToOracleResponseMap;
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder expectPreferences(Map<List<String>, String> rhIdToPrmResponseMap) {
        this.expectedRhIdToPrmResponseMap = rhIdToPrmResponseMap;
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder expectPrmCall(Map<Long, String> accountToPrmResponseMap) {
        this.expectedAccountToPrmResponseMap = accountToPrmResponseMap;
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder expectLmDetails(String jsonFile) {
        this.expectedLmDetailsJsonFile = jsonFile;
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder expectPaidInfo(String jsonFile) {
        this.expectedPaidUsages = jsonFile;
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder expectCrmCall(String expectedRequest, String response) {
        this.expectedCrmRequest = expectedRequest;
        this.crmResponse = response;
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder expectScenario(Scenario scenario) {
        this.expectedScenario = scenario;
        return this;
    }

    NtsWorkflowChunkIntegrationTestBuilder expectPrmCallForUpdateRro(Long accountNumber, String expectedResponse) {
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
        expectedAccountToOracleResponseMap = null;
        expectedAccountToPrmResponseMap = null;
        expectedRmsRequestResponseMap = null;
        expectedRhIdToPrmResponseMap = null;
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
            testHelper.expectPrmCall(expectedPrmResponseForUpdateRro, expectedRroAccountNumber);
            testHelper.expectGetRmsRights(expectedRmsRequestResponseMap);
            expectedAccountToPrmResponseMap.forEach((accountNumber, response) ->
                testHelper.expectPrmCall(response, accountNumber));
            expectedAccountToOracleResponseMap.forEach((accountNumbers, response) ->
                testHelper.expectOracleCall(response, accountNumbers));
            expectedRhIdToPrmResponseMap.forEach((rightsholderIds, response) ->
                testHelper.expectGetPreferences(response, rightsholderIds));
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
            testHelper.assertPaidUsages(expectedUsages, usageDtos ->
                usageService.getForAudit(new AuditFilter(), null, null));
            assertAudit();
            testHelper.verifyRestServer();
        }

        private void loadNtsBatch() {
            actualUsageIds = usageBatchService.insertNtsBatch(usageBatch, RupContextUtils.getUserName());
            ntsUsageService.sendForGettingRights(actualUsageIds, usageBatch.getName());
        }

        private void createScenario() {
            UsageFilter usageFilter = new UsageFilter();
            usageFilter.setUsageBatchesIds(Collections.singleton(usageBatch.getId()));
            usageFilter.setProductFamily("NTS");
            NtsFields ntsFields = new NtsFields();
            ntsFields.setRhMinimumAmount(new BigDecimal("300"));
            actualScenario = ntsScenarioService.createScenario("Test Scenario", ntsFields, null, usageFilter);
        }

        private void sendScenarioToLm() {
            ntsScenarioService.sendToLm(actualScenario);
            sqsClientMock.assertSendMessages("fda-test-sf-detail.fifo",
                Collections.singletonList(TestUtils.fileToString(this.getClass(), expectedLmDetailsJsonFile)),
                Collections.singletonList("detail_id"), ImmutableMap.of("source", "FDA"));
        }

        private void receivePaidUsagesFromLm() throws InterruptedException {
            testHelper.receivePaidUsagesFromLm(expectedPaidUsages,
                Collections.singletonList(getArchivedUsages().get(0).getId()));
        }

        private void assertScenario() {
            actualScenario = scenarioService.getScenarios("NTS")
                .stream()
                .filter(scenario -> actualScenario.getId().equals(scenario.getId()))
                .findAny()
                .orElse(null);
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
