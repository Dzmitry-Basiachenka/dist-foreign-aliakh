package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.SendToCrmIntegrationTestBuilder.Runner;

import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Builder for send to CRM test.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 7/9/19
 *
 * @author Stanislau Rudak
 */
@Component
public class SendToCrmIntegrationTestBuilder implements Builder<Runner> {

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private ServiceTestHelper testHelper;

    private Map<String, UsageStatusEnum> usageIdToExpectedStatus = new HashMap<>();
    private Map<String, List<UsageAuditItem>> usageIdToExpectedAudit = new HashMap<>();
    private Map<String, ScenarioStatusEnum> scenarioIdToExpectedStatus = new HashMap<>();
    private Map<String, List<ScenarioAuditItem>> scenarioIdToExpectedAudit = new HashMap<>();
    private String expectedCrmRequest;
    private String crmResponse;
    private JobInfo expectedJobInfo;
    private String productFamily;

    SendToCrmIntegrationTestBuilder withProductFamily(String scenarioProductFamily) {
        this.productFamily = scenarioProductFamily;
        return this;
    }

    SendToCrmIntegrationTestBuilder expectUsageStatus(Map<String, UsageStatusEnum> usageIdToStatusMap) {
        usageIdToExpectedStatus = usageIdToStatusMap;
        return this;
    }

    SendToCrmIntegrationTestBuilder expectUsageAudit(Map<String, List<UsageAuditItem>> usageIdToAuditMap) {
        usageIdToExpectedAudit = usageIdToAuditMap;
        return this;
    }

    SendToCrmIntegrationTestBuilder expectScenarioStatus(Map<String, ScenarioStatusEnum> scenarioIdToStatusMap) {
        scenarioIdToExpectedStatus = scenarioIdToStatusMap;
        return this;
    }

    SendToCrmIntegrationTestBuilder expectScenarioAudit(Map<String, List<ScenarioAuditItem>> scenarioIdToAuditMap) {
        scenarioIdToExpectedAudit = scenarioIdToAuditMap;
        return this;
    }

    SendToCrmIntegrationTestBuilder expectCrmCall(String expectedRequest, String response) {
        this.expectedCrmRequest = expectedRequest;
        this.crmResponse = response;
        return this;
    }

    SendToCrmIntegrationTestBuilder expectJobInfo(JobInfo jobInfo) {
        this.expectedJobInfo = jobInfo;
        return this;
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    void reset() {
        usageIdToExpectedStatus = null;
        usageIdToExpectedAudit = null;
        scenarioIdToExpectedStatus = null;
        scenarioIdToExpectedAudit = null;
        expectedCrmRequest = null;
        crmResponse = null;
    }

    /**
     * Test runner class.
     */
    public class Runner {

        public void run() {
            testHelper.createRestServer();
            testHelper.expectCrmCall(expectedCrmRequest, crmResponse, Collections.emptyList());
            JobInfo jobInfo = usageService.sendToCrm();
            verifyJobInfo(jobInfo);
            verifyUsages();
            verifyScenarios();
            verifyUsageAudit();
            verifyScenarioAudit();
            testHelper.verifyRestServer();
        }

        private void verifyJobInfo(JobInfo actualJobInfo) {
            assertEquals(expectedJobInfo.getStatus(), actualJobInfo.getStatus());
            assertEquals(expectedJobInfo.getResult(), actualJobInfo.getResult());
        }

        private void verifyUsages() {
            usageIdToExpectedStatus.forEach((usageId, expectedStatus) -> {
                List<PaidUsage> usages =
                    usageArchiveRepository.findByIdAndStatus(Collections.singletonList(usageId), expectedStatus);
                assertEquals(1, usages.size());
            });
        }

        private void verifyScenarios() {
            scenarioIdToExpectedStatus.forEach((scenarioId, expectedStatus) -> {
                Scenario actualScenario =
                    scenarioService.getScenariosByProductFamilies(Collections.singleton(productFamily)).stream()
                        .filter(scenario -> Objects.equals(scenarioId, scenario.getId()))
                        .findFirst()
                        .orElseThrow(
                            () -> new AssertionError(String.format("Scenario must exists. ScenarioId=%s", scenarioId)));
                assertEquals(expectedStatus, actualScenario.getStatus());
            });
        }

        private void verifyUsageAudit() {
            usageIdToExpectedAudit.forEach(
                (usageId, expectedAuditItems) -> testHelper.assertAudit(usageId, expectedAuditItems));
        }

        private void verifyScenarioAudit() {
            scenarioIdToExpectedAudit.forEach((scenarioId, expectedAudit) -> {
                List<ScenarioAuditItem> actualAudit = scenarioAuditService.getActions(scenarioId);
                assertEquals(expectedAudit.size(), actualAudit.size());
                IntStream.range(0, expectedAudit.size())
                    .forEach(index -> {
                        assertEquals(expectedAudit.get(index).getActionReason(),
                            actualAudit.get(index).getActionReason());
                        assertEquals(expectedAudit.get(index).getActionType(), actualAudit.get(index).getActionType());
                    });
            });
        }
    }
}
