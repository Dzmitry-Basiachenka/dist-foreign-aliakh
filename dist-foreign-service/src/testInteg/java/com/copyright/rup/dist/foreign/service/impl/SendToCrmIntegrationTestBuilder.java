package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.SendToCrmIntegrationTestBuilder.Runner;

import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private ServiceTestHelper testHelper;

    private Map<String, UsageStatusEnum> usageIdToExpectedStatus = new HashMap<>();
    private Map<String, List<UsageAuditItem>> usageIdToExpectedAudit = new HashMap<>();
    private Map<String, ScenarioStatusEnum> scenarioIdToExpectedStatus = new HashMap<>();
    private Map<String, List<Pair<ScenarioActionTypeEnum, String>>> scenarioIdToExpectedAudit = new HashMap<>();
    private String expectedCrmRequest;
    private String crmResponse;
    private JobInfo expectedJobInfo;

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

    SendToCrmIntegrationTestBuilder expectScenarioAudit(
        Map<String, List<Pair<ScenarioActionTypeEnum, String>>> scenarioIdToAuditMap) {
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
            testHelper.expectCrmCall(expectedCrmRequest, crmResponse, List.of());
            JobInfo jobInfo = usageService.sendToCrm();
            verifyJobInfo(jobInfo);
            verifyUsages();
            verifyScenarios();
            verifyUsageAudit();
            scenarioIdToExpectedAudit.forEach(testHelper::assertScenarioAudit);
            testHelper.verifyRestServer();
        }

        private void verifyJobInfo(JobInfo actualJobInfo) {
            assertEquals(expectedJobInfo.getStatus(), actualJobInfo.getStatus());
            assertEquals(expectedJobInfo.getResult(), actualJobInfo.getResult());
        }

        private void verifyUsages() {
            usageIdToExpectedStatus.forEach((usageId, expectedStatus) -> {
                List<PaidUsage> usages = usageArchiveRepository.findByIdAndStatus(List.of(usageId), expectedStatus);
                assertEquals(1, usages.size());
            });
        }

        private void verifyScenarios() {
            scenarioIdToExpectedStatus.forEach((scenarioId, expectedStatus) -> {
                Scenario actualScenario = scenarioRepository.findById(scenarioId);
                assertNotNull(actualScenario);
                assertEquals(expectedStatus, actualScenario.getStatus());
            });
        }

        private void verifyUsageAudit() {
            usageIdToExpectedAudit.forEach(
                (usageId, expectedAuditItems) -> testHelper.assertAudit(usageId, expectedAuditItems));
        }
    }
}
