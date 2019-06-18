package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Builder for {@link ReconcileRightsholdersTest}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/01/18
 *
 * @author Ihar Suvorau
 */
@Component
class ReconcileRightsholdersTestBuilder {

    private Scenario expectedScenario;
    private String rmsResponse;
    private String rmsRequest;
    private String expectedPreferencesJson;
    private List<String> expectedPreferencesRightholderIds;
    private String expectedRollupsJson;
    private List<String> expectedRollupsRightholderIds;
    private String expectedPrmResponse;
    private Long expectedPrmAccountNumber;
    private Set<RightsholderDiscrepancy> expectedDiscrepancies = new HashSet<>();
    private List<Usage> expectedUsages = new ArrayList<>();
    private Map<String, List<UsageAuditItem>> usageIdToAuditItemsMap;

    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    @Autowired
    private ServiceTestHelper testHelper;

    Runner build() {
        return new Runner();
    }

    ReconcileRightsholdersTestBuilder forScenario(Scenario scenario) {
        this.expectedScenario = scenario;
        return this;
    }

    ReconcileRightsholdersTestBuilder expectRmsCall(String expectedRequest, String expectedResponse) {
        this.rmsRequest = expectedRequest;
        this.rmsResponse = expectedResponse;
        return this;
    }

    ReconcileRightsholdersTestBuilder expectPrmCall(Long accountNumber, String expectedResponse) {
        this.expectedPrmAccountNumber = accountNumber;
        this.expectedPrmResponse = expectedResponse;
        return this;
    }

    ReconcileRightsholdersTestBuilder expectRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderIds = Arrays.asList(rollupsRightsholdersIds);
        return this;
    }

    ReconcileRightsholdersTestBuilder expectDiscrepancies(Set<RightsholderDiscrepancy> discrepancies) {
        this.expectedDiscrepancies = discrepancies;
        return this;
    }

    ReconcileRightsholdersTestBuilder expectPreferences(String preferencesJson, String... rightholderIds) {
        this.expectedPreferencesJson = preferencesJson;
        this.expectedPreferencesRightholderIds = Arrays.asList(rightholderIds);
        return this;
    }

    ReconcileRightsholdersTestBuilder expectUsages(Usage... usages) {
        this.expectedUsages = Arrays.asList(usages);
        return this;
    }

    ReconcileRightsholdersTestBuilder expectUsageAudit(Map<String, List<UsageAuditItem>> usageIdToAuditItems) {
        this.usageIdToAuditItemsMap = usageIdToAuditItems;
        return this;
    }

    void reset() {
        this.expectedDiscrepancies = new HashSet<>();
        this.expectedUsages = new ArrayList<>();
        this.usageIdToAuditItemsMap = new HashMap<>();
    }

    /**
     * Test runner class.
     */
    class Runner {

        void run() {
            testHelper.createRestServer();
            testHelper.expectGetRmsRights(rmsRequest, rmsResponse);
            if (Objects.nonNull(expectedPrmResponse)) {
                testHelper.expectPrmCall(expectedPrmResponse, expectedPrmAccountNumber);
            }
            if (Objects.nonNull(expectedRollupsJson)) {
                testHelper.expectGetRollups(expectedRollupsJson, expectedRollupsRightholderIds);
            }
            testHelper.expectGetPreferences(expectedPreferencesJson, expectedPreferencesRightholderIds);
            scenarioService.reconcileRightsholders(expectedScenario);
            assertDiscrepancies();
            scenarioService.approveOwnershipChanges(expectedScenario);
            assertUsages();
            testHelper.verifyRestServer();
        }

        private void assertDiscrepancies() {
            List<RightsholderDiscrepancy> discrepancies =
                rightsholderDiscrepancyService.getByScenarioIdAndStatus(expectedScenario.getId(),
                    RightsholderDiscrepancyStatusEnum.DRAFT, null, null);
            expectedDiscrepancies.forEach(expected -> {
                Long oldAccountNumber = expected.getOldRightsholder().getAccountNumber();
                Long newAccountNumber = expected.getNewRightsholder().getAccountNumber();
                Long wrWrkInst = expected.getWrWrkInst();
                assertEquals(1, discrepancies.stream()
                    .filter(actual -> oldAccountNumber.equals(actual.getOldRightsholder().getAccountNumber())
                        && newAccountNumber.equals(actual.getNewRightsholder().getAccountNumber())
                        && wrWrkInst.equals(actual.getWrWrkInst())).count());
            });
        }

        private void assertUsages() {
            List<Usage> usages = usageService.getUsagesByScenarioId(expectedScenario.getId());
            usages.sort(Comparator.comparing(Usage::getId));
            assertEquals(expectedUsages.size(), CollectionUtils.size(usages));
            IntStream.range(0, usages.size()).forEach(i -> {
                assertUsage(expectedUsages.get(i), usages.get(i));
                assertAudit(usages.get(i).getId());
            });
        }

        private void assertAudit(String usageId) {
            List<UsageAuditItem> items = usageAuditService.getUsageAudit(usageId);
            assertEquals(CollectionUtils.size(usageIdToAuditItemsMap.get(usageId)), CollectionUtils.size(items));
            IntStream.range(0, items.size()).forEach(index -> {
                UsageAuditItem expectedAuditItem = usageIdToAuditItemsMap.get(usageId).get(index);
                UsageAuditItem actualAuditItem = items.get(index);
                assertEquals(expectedAuditItem.getActionType(), actualAuditItem.getActionType());
                assertEquals(expectedAuditItem.getActionReason(), actualAuditItem.getActionReason());
            });
        }

        private void assertUsage(Usage expected, Usage actual) {
            assertNotNull(actual.getRightsholder().getId());
            assertNotNull(actual.getPayee().getId());
            assertEquals(expected.getRightsholder().getAccountNumber(), actual.getRightsholder().getAccountNumber());
            assertEquals(expected.getPayee().getAccountNumber(), actual.getPayee().getAccountNumber());
            assertEquals(expected.getWrWrkInst(), actual.getWrWrkInst());
            assertEquals(0, expected.getGrossAmount().compareTo(actual.getGrossAmount()));
            assertEquals(0, expected.getNetAmount().compareTo(actual.getNetAmount()));
            assertEquals(0, expected.getServiceFeeAmount().compareTo(actual.getServiceFeeAmount()));
            assertEquals(0, expected.getServiceFee().compareTo(actual.getServiceFee()));
        }
    }
}
