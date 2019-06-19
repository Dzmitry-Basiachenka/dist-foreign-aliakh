package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.CreateNtsBatchIntegrationTestBuilder.Runner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Builder for {@link CreateNtsBatchIntegrationTest}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/11/18
 *
 * @author Uladzislau Shalamitski
 */
// TODO {pliakh} move common REST-expectation and assertion methods to helper, apply in other tests
@Component
public class CreateNtsBatchIntegrationTestBuilder implements Builder<Runner> {

    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private SqsClientMock sqsClientMock;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private ServiceTestHelper testHelper;

    private String expectedRmsRequest;
    private String expectedRmsResponse;
    private String expectedOracleResponse;
    private Long expectedOracleAccountNumber;
    private String expectedPrmResponse;
    private String expectedPreferencesResponse;
    private String expectedPreferencesRightsholderId;
    private Long expectedPrmAccountNumber;
    private UsageBatch usageBatch;
    // TODO {pliakh} allow to assert multiple usages
    private Usage expectedUsages;
    private UsageAuditItem expectedAudit;

    CreateNtsBatchIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectUsage(Usage usage) {
        this.expectedUsages = usage;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectAudit(UsageAuditItem audit) {
        this.expectedAudit = audit;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectRmsRights(String request, String response) {
        this.expectedRmsRequest = request;
        this.expectedRmsResponse = response;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectOracleCall(Long accountNumber, String response) {
        this.expectedOracleAccountNumber = accountNumber;
        this.expectedOracleResponse = response;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectPreferences(String preferencesJson, String rightsholderId) {
        this.expectedPreferencesResponse = preferencesJson;
        this.expectedPreferencesRightsholderId = rightsholderId;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectPrmCall(Long accountNumber, String expectedResponse) {
        this.expectedPrmAccountNumber = accountNumber;
        this.expectedPrmResponse = expectedResponse;
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
        this.sqsClientMock.reset();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private List<String> actualUsageIds;

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
            assertBatch();
            assertUsages();
            assertAudit();
            testHelper.verifyRestServer();
        }

        private void loadNtsBatch() {
            actualUsageIds = usageBatchService.insertNtsBatch(usageBatch, RupContextUtils.getUserName());
            usageBatchService.getAndSendForGettingRights(actualUsageIds, usageBatch.getName());
        }

        private void assertBatch() {
            UsageBatch insertedBatch = usageBatchRepository.findAll().stream()
                .filter(batch -> usageBatch.getName().equals(batch.getName()))
                .findFirst()
                .orElse(null);
            assertNotNull(insertedBatch);
            assertEquals(usageBatch.getFundPool(), insertedBatch.getFundPool());
            assertEquals(usageBatch.getFiscalYear(), insertedBatch.getFiscalYear());
            assertEquals(usageBatch.getGrossAmount(), insertedBatch.getGrossAmount());
            assertEquals(usageBatch.getPaymentDate(), insertedBatch.getPaymentDate());
            assertEquals(usageBatch.getRro().getAccountNumber(), insertedBatch.getRro().getAccountNumber());
            usageBatch.setId(insertedBatch.getId());
        }

        private void assertUsages() {
            if (Objects.isNull(expectedUsages)) {
                assertTrue(CollectionUtils.isEmpty(usageRepository.findByIds(actualUsageIds)));
            } else {
                List<Usage> actualUsages = usageRepository.findByIds(actualUsageIds);
                assertEquals(1, actualUsages.size());
                assertUsage(expectedUsages, actualUsages.get(0));
            }
        }

        private void assertUsage(Usage expectedUsage, Usage actualUsage) {
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
            assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
            assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
                actualUsage.getRightsholder().getAccountNumber());
        }

        private void assertAudit() {
            actualUsageIds.forEach(Objects.isNull(expectedAudit)
                ? usageId -> assertTrue(CollectionUtils.isEmpty(usageAuditService.getUsageAudit(usageId)))
                : this::assertAudit);
        }

        private void assertAudit(String usageId) {
            List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageId);
            assertEquals(1, CollectionUtils.size(auditItems));
            UsageAuditItem auditItem = auditItems.get(0);
            assertEquals(expectedAudit.getActionType(), auditItem.getActionType());
            assertEquals(expectedAudit.getActionReason(), auditItem.getActionReason());
        }
    }
}
