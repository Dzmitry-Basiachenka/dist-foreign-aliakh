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
import com.copyright.rup.dist.foreign.service.impl.CreateNtsBatchChunkIntegrationTestBuilder.Runner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
public class CreateNtsBatchChunkIntegrationTestBuilder implements Builder<Runner> {

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

    private final Map<List<String>, String> expectedRhIdToPrmResponseMap = new LinkedHashMap<>();
    private final Map<String, String> expectedRmsRequestResponseMap = new LinkedHashMap<>();
    private final Map<Long, String> expectedAccountToPrmResponseMap = new LinkedHashMap<>();
    private final Map<List<Long>, String> expectedAccountToOracleResponseMap = new LinkedHashMap<>();
    private UsageBatch usageBatch;
    private int initialUsagesCount;
    private List<Usage> expectedUsages;
    private UsageAuditItem expectedAudit;

    CreateNtsBatchChunkIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    CreateNtsBatchChunkIntegrationTestBuilder withInitialUsagesCount(int usagesCount) {
        this.initialUsagesCount = usagesCount;
        return this;
    }

    CreateNtsBatchChunkIntegrationTestBuilder expectUsages(List<Usage> usages) {
        this.expectedUsages = usages;
        return this;
    }

    CreateNtsBatchChunkIntegrationTestBuilder expectAudit(UsageAuditItem audit) {
        this.expectedAudit = audit;
        return this;
    }

    CreateNtsBatchChunkIntegrationTestBuilder expectRmsRights(Map<String, String> rmsRequestResponseMap) {
        this.expectedRmsRequestResponseMap.putAll(rmsRequestResponseMap);
        return this;
    }

    CreateNtsBatchChunkIntegrationTestBuilder expectOracleCall(Map<List<Long>, String> accountToOracleResponseMap) {
        this.expectedAccountToOracleResponseMap.putAll(accountToOracleResponseMap);
        return this;
    }

    CreateNtsBatchChunkIntegrationTestBuilder expectPreferences(Map<List<String>, String> rhIdToPrmResponseMap) {
        this.expectedRhIdToPrmResponseMap.putAll(rhIdToPrmResponseMap);
        return this;
    }

    CreateNtsBatchChunkIntegrationTestBuilder expectPrmCall(Map<Long, String> accountToPrmResponseMap) {
        this.expectedAccountToPrmResponseMap.putAll(accountToPrmResponseMap);
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
        expectedAccountToOracleResponseMap.clear();
        expectedAccountToPrmResponseMap.clear();
        expectedRmsRequestResponseMap.clear();
        expectedRhIdToPrmResponseMap.clear();
        testHelper.reset();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private static final int ONE = 1;

        private List<String> actualUsageIds;

        public void run() {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(expectedRmsRequestResponseMap);
            expectedAccountToPrmResponseMap.forEach((accountNumber, response) ->
                testHelper.expectPrmCall(response, accountNumber));
            expectedAccountToOracleResponseMap.forEach((accountNumbers, response) ->
                testHelper.expectOracleCall(response, accountNumbers));
            expectedRhIdToPrmResponseMap.forEach((rightsholderIds, response) ->
                testHelper.expectGetPreferences(response, rightsholderIds));
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
            if (expectedAccountToPrmResponseMap.size() > ONE) {
                assertEquals("American College of Physicians - Journals", insertedBatch.getRro().getName());
            }
            usageBatch.setId(insertedBatch.getId());
        }

        private void assertUsages() {
            if (Objects.isNull(expectedUsages)) {
                assertTrue(CollectionUtils.isEmpty(usageRepository.findByIds(actualUsageIds)));
            } else {
                List<Usage> actualUsages = usageRepository.findByIds(actualUsageIds)
                    .stream()
                    .sorted(Comparator.comparing(Usage::getWrWrkInst))
                    .collect(Collectors.toList());
                assertEquals(expectedUsages.size(), actualUsages.size());
                IntStream.range(0, expectedUsages.size())
                    .forEach(index -> {assertUsage(actualUsages.get(0));});
            }
        }

        private void assertUsage(Usage actualUsage) {
            Usage expectedUsage = expectedUsages.stream()
                .filter(expected -> expected.getWrWrkInst().equals(actualUsage.getWrWrkInst()))
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
