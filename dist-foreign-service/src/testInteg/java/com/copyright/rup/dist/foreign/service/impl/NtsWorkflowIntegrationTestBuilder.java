package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.service.impl.NtsWorkflowIntegrationTestBuilder.Runner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    private INtsScenarioService ntsScenarioService;
    @Autowired
    private IScenarioRepository scenarioRepository;
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

    NtsWorkflowIntegrationTestBuilder expectRmsRights(Map<String, String> rmsRequestResponseMap) {
        this.expectedRmsRequestResponseMap = rmsRequestResponseMap;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectOracleCall(Map<List<Long>, String> accountToOracleResponseMap) {
        this.expectedAccountToOracleResponseMap = accountToOracleResponseMap;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectPreferences(Map<List<String>, String> rhIdToPrmResponseMap) {
        this.expectedRhIdToPrmResponseMap = rhIdToPrmResponseMap;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectPrmCall(Map<Long, String> accountToPrmResponseMap) {
        this.expectedAccountToPrmResponseMap = accountToPrmResponseMap;
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
        expectedAccountToOracleResponseMap = null;
        expectedAccountToPrmResponseMap = null;
        expectedRmsRequestResponseMap = null;
        expectedRhIdToPrmResponseMap = null;
        expectedLmDetailsJsonFile = null;
        expectedRollupsJson = null;
        expectedRollupsRightholderId = null;
        testHelper.reset();
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
                testHelper.expectGetRollups(expectedRollupsJson, List.of(expectedRollupsRightholderId));
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
            ntsUsageService.sendForGettingRights(actualUsageIds, usageBatch.getName());
        }

        private void createScenario() {
            UsageFilter usageFilter = new UsageFilter();
            usageFilter.setUsageBatchesIds(Set.of(usageBatch.getId()));
            usageFilter.setProductFamily("NTS");
            NtsFields ntsFields = new NtsFields();
            ntsFields.setRhMinimumAmount(new BigDecimal("300"));
            actualScenario = ntsScenarioService.createScenario("Test Scenario", ntsFields, null, usageFilter);
        }

        private void sendScenarioToLm() {
            ntsScenarioService.sendToLm(actualScenario);
            testHelper.sendScenarioToLm(List.of(expectedLmDetailsJsonFile));
        }

        private void receivePaidUsagesFromLm() throws InterruptedException {
            testHelper.receivePaidUsagesFromLm(expectedPaidUsages, List.of(getArchivedUsages().get(0).getId()));
        }

        private void assertScenario() {
            actualScenario = scenarioRepository.findById(actualScenario.getId());
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
