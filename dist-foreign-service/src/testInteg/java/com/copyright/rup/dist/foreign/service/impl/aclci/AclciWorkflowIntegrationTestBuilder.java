package com.copyright.rup.dist.foreign.service.impl.aclci;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;
import com.copyright.rup.dist.foreign.service.impl.aclci.AclciWorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.csv.AclciUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private ServiceTestHelper testHelper;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    private UsageBatch expectedUsageBatch;
    private String pathToUsagesToUpload;
    private String pathToExpectedUsages;
    private List<String> uploadedUsagesIds = new ArrayList<>();
    private List<String> pathsToExpectedUsageAuditItems = new ArrayList<>();

    AclciWorkflowIntegrationTestBuilder withUsageBatch(UsageBatch usageBatch) {
        this.expectedUsageBatch = usageBatch;
        return this;
    }

    AclciWorkflowIntegrationTestBuilder withUsagesToUpload(String pathToUsages) {
        this.pathToUsagesToUpload = pathToUsages;
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
        this.pathToExpectedUsages = null;
        this.uploadedUsagesIds.clear();
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
            loadUsageBatch();
            assignUsages();
            assertUsagesAudit();
            //TODO: complete when the workflow is fully implemented
        }

        private void loadUsageBatch() throws IOException {
            AclciUsageCsvProcessor processor = csvProcessorFactory.getAclciUsageCsvProcessor();
            ProcessingResult<Usage> result = processor.process(testHelper.getCsvOutputStream(pathToUsagesToUpload));
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            usageBatchService.insertAclciBatch(expectedUsageBatch, usages);
            //usageService.sendForMatching(usages); TODO: will be implemented in a separate story
            uploadedUsagesIds = usages.stream().map(BaseEntity::getId).collect(Collectors.toList());
        }

        private void assignUsages() throws IOException {
            //TODO implement when reading ACLCI usages by filter is implemented
            List<Usage> expectedUsages = testHelper.loadExpectedUsages(pathToExpectedUsages);
            expectedUsages.forEach(expectedUsage -> {
                testHelper.assertUsage(expectedUsage, expectedUsage);
                //TODO implement testHelper.assertAclciUsage
            });
        }

        private void assertUsagesAudit() throws IOException {
            assertEquals(pathsToExpectedUsageAuditItems.size(), uploadedUsagesIds.size());
            for (int index = 0; index < pathsToExpectedUsageAuditItems.size(); index++) {
                List<UsageAuditItem> usageAuditItems =
                    testHelper.loadExpectedUsageAuditItems(pathsToExpectedUsageAuditItems.get(index));
                testHelper.assertAudit(uploadedUsagesIds.get(index), usageAuditItems);
            }
        }
    }
}
