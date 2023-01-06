package com.copyright.rup.dist.foreign.service.impl.aclci;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.aclci.IAclciUsageService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;
import com.copyright.rup.dist.foreign.service.impl.aclci.AclciWorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.csv.AclciUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Builder for {@link AclciWorkflowIntegrationTest}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/14/2022
 *
 * @author Aliaksandr Liakh
 */
@Component
public class AclciWorkflowIntegrationTestBuilder implements Builder<Runner> {

    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IAclciUsageService aclciUsageService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    private UsageBatch expectedUsageBatch;
    private String pathToUsagesToUpload;
    private Map<String, String> expectedRmsRequestToResponseMap = new HashMap<>();
    private Map<Long, String> expectedPrmAccountNumberToResponseMap = new HashMap<>();
    private String pathToExpectedUsages;
    private List<String> pathsToExpectedUsageAuditItems = new ArrayList<>();

    AclciWorkflowIntegrationTestBuilder withUsageBatch(UsageBatch usageBatch) {
        this.expectedUsageBatch = usageBatch;
        return this;
    }

    AclciWorkflowIntegrationTestBuilder withUsagesToUpload(String pathToUsages) {
        this.pathToUsagesToUpload = pathToUsages;
        return this;
    }

    AclciWorkflowIntegrationTestBuilder withRmsRequests(Map<String, String> rmsRequestToResponseMap) {
        this.expectedRmsRequestToResponseMap = rmsRequestToResponseMap;
        return this;
    }

    AclciWorkflowIntegrationTestBuilder withPrmRequests(Map<Long, String> prmAccountNumberToResponse) {
        this.expectedPrmAccountNumberToResponseMap = prmAccountNumberToResponse;
        return this;
    }

    AclciWorkflowIntegrationTestBuilder withExpectedUsages(String pathToUsages) {
        this.pathToExpectedUsages = pathToUsages;
        return this;
    }

    AclciWorkflowIntegrationTestBuilder withUsageAuditItems(List<String> pathsToAuditItems) {
        this.pathsToExpectedUsageAuditItems = pathsToAuditItems;
        return this;
    }

    void reset() {
        this.expectedUsageBatch = null;
        this.pathToUsagesToUpload = null;
        this.expectedRmsRequestToResponseMap.clear();
        this.expectedPrmAccountNumberToResponseMap.clear();
        this.pathToExpectedUsages = null;
        this.pathsToExpectedUsageAuditItems.clear();
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
            expectedPrmAccountNumberToResponseMap.forEach((accountNumber, response) ->
                testHelper.expectPrmCall(response, accountNumber));
            List<String> usageIds = loadUsageBatch();
            testHelper.verifyRestServer();
            assignUsages(usageIds);
            assertUsagesAudit(usageIds);
            //TODO: complete when the workflow is fully implemented
        }

        private List<String> loadUsageBatch() throws IOException {
            AclciUsageCsvProcessor processor = csvProcessorFactory.getAclciUsageCsvProcessor();
            ProcessingResult<Usage> result = processor.process(testHelper.getCsvOutputStream(pathToUsagesToUpload));
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            List<String> usageIds = usageBatchService.insertAclciBatch(expectedUsageBatch, usages);
            aclciUsageService.sendForMatching(usageIds, expectedUsageBatch.getName());
            return usageIds;
        }

        private void assignUsages(List<String> usageIds) throws IOException {
            List<Usage> expectedUsages = testHelper.loadExpectedUsages(pathToExpectedUsages);
            assertEquals(expectedUsages.size(), usageIds.size());
            IntStream.range(0, expectedUsages.size()).forEach(i -> {
                Usage expectedUsage = expectedUsages.get(i);
                Usage actualUsage = aclciUsageService.getUsagesByIds(List.of(usageIds.get(i))).get(0);
                testHelper.assertUsage(expectedUsage, actualUsage);
                testHelper.assertAclciUsage(expectedUsage.getAclciUsage(), actualUsage.getAclciUsage());
            });
        }

        private void assertUsagesAudit(List<String> usageIds) throws IOException {
            assertEquals(pathsToExpectedUsageAuditItems.size(), usageIds.size());
            for (int i = 0; i < pathsToExpectedUsageAuditItems.size(); i++) {
                List<UsageAuditItem> usageAuditItems =
                    testHelper.loadExpectedUsageAuditItems(pathsToExpectedUsageAuditItems.get(i));
                testHelper.assertAudit(usageIds.get(i), usageAuditItems);
            }
        }
    }
}
