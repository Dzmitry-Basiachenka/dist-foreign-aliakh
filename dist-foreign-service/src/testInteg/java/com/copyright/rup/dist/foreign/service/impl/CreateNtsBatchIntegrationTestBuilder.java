package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.service.impl.CreateNtsBatchIntegrationTestBuilder.Runner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
@Component
public class CreateNtsBatchIntegrationTestBuilder implements Builder<Runner> {

    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private INtsUsageService ntsUsageService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private ServiceTestHelper testHelper;

    private String expectedRmsRequest;
    private String expectedRmsResponse;
    private String expectedOracleResponse;
    private Long expectedOracleAccountNumber;
    private Long expectedRroAccountNumber;
    private String expectedPrmResponseForUpdateRro;
    private String expectedPrmResponse;
    private String expectedPreferencesResponse;
    private String expectedPreferencesRightsholderId;
    private Long expectedPrmAccountNumber;
    private UsageBatch usageBatch;
    private int initialUsagesCount;
    private List<Usage> expectedUsages;
    private UsageAuditItem expectedAudit;

    CreateNtsBatchIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder withInitialUsagesCount(int usagesCount) {
        this.initialUsagesCount = usagesCount;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectUsages(List<Usage> usages) {
        this.expectedUsages = usages;
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

    CreateNtsBatchIntegrationTestBuilder expectPrmCallForUpdateRro(Long accountNumber, String expectedResponse) {
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
        expectedRroAccountNumber = null;
        expectedPrmResponseForUpdateRro = null;
        testHelper.reset();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private List<String> actualUsageIds;

        public void run() {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequest, expectedRmsResponse);
            testHelper.expectPrmCall(expectedPrmResponseForUpdateRro, expectedRroAccountNumber);
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
            ntsUsageService.sendForGettingRights(actualUsageIds, usageBatch.getName());
        }

        private void assertBatch() {
            UsageBatch insertedBatch = usageBatchRepository.findAll().stream()
                .filter(batch -> usageBatch.getName().equals(batch.getName()))
                .findFirst()
                .orElse(null);
            assertNotNull(insertedBatch);
            assertEquals(usageBatch.getNtsFields(), insertedBatch.getNtsFields());
            assertEquals(usageBatch.getFiscalYear(), insertedBatch.getFiscalYear());
            assertEquals(usageBatch.getGrossAmount(), insertedBatch.getGrossAmount());
            assertEquals(usageBatch.getPaymentDate(), insertedBatch.getPaymentDate());
            assertEquals(usageBatch.getRro().getAccountNumber(), insertedBatch.getRro().getAccountNumber());
            assertEquals(initialUsagesCount, insertedBatch.getInitialUsagesCount());
            if (Objects.nonNull(expectedPrmResponse)) {
                assertEquals("American College of Physicians - Journals", insertedBatch.getRro().getName());
            }
            usageBatch.setId(insertedBatch.getId());
        }

        private void assertUsages() {
            if (Objects.isNull(expectedUsages)) {
                assertTrue(CollectionUtils.isEmpty(usageRepository.findByIds(actualUsageIds)));
            } else {
                List<Usage> actualUsages = usageRepository.findByIds(actualUsageIds);
                assertEquals(expectedUsages.size(), actualUsages.size());
                assertUsage(actualUsages.get(0));
            }
        }

        private void assertUsage(Usage actualUsage) {
            Usage expectedUsage = expectedUsages.stream()
                .filter(expected -> expected.getReportedValue().equals(actualUsage.getReportedValue()))
                .findFirst()
                .orElse(null);
            assertNotNull(expectedUsage);
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
            assertEquals("American College of Physicians - Journals", actualUsage.getRightsholder().getName());
        }

        private void assertAudit() {
            actualUsageIds.forEach(Objects.isNull(expectedAudit)
                ? usageId -> assertTrue(CollectionUtils.isEmpty(usageAuditService.getUsageAudit(usageId)))
                : usageId -> testHelper.assertAudit(usageId, Collections.singletonList(expectedAudit)));
        }
    }
}
