package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.service.api.fas.IFasScenarioService;
import com.copyright.rup.dist.foreign.service.api.fas.IRightsholderDiscrepancyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    private List<Usage> expectedUsages;
    private Map<String, List<UsageAuditItem>> usageIdToAuditItemsMap;

    @Autowired
    private IFasScenarioService fasScenarioService;
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
        this.expectedRollupsRightholderIds = List.of(rollupsRightsholdersIds);
        return this;
    }

    ReconcileRightsholdersTestBuilder expectDiscrepancies(Set<RightsholderDiscrepancy> discrepancies) {
        this.expectedDiscrepancies = discrepancies;
        return this;
    }

    ReconcileRightsholdersTestBuilder expectPreferences(String preferencesJson, String... rightsholderIds) {
        this.expectedPreferencesJson = preferencesJson;
        this.expectedPreferencesRightholderIds = List.of(rightsholderIds);
        return this;
    }

    ReconcileRightsholdersTestBuilder expectUsages(Usage... usages) {
        this.expectedUsages = List.of(usages);
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
            fasScenarioService.reconcileRightsholders(expectedScenario);
            assertDiscrepancies();
            fasScenarioService.approveOwnershipChanges(expectedScenario);
            testHelper.assertUsages(expectedUsages);
            assertAudit();
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
                String workTitle = expected.getWorkTitle();
                assertEquals(1, discrepancies.stream()
                    .filter(actual -> oldAccountNumber.equals(actual.getOldRightsholder().getAccountNumber())
                        && newAccountNumber.equals(actual.getNewRightsholder().getAccountNumber())
                        && wrWrkInst.equals(actual.getWrWrkInst())
                        && workTitle.equals(actual.getWorkTitle()))
                    .count());
            });
        }

        private void assertAudit() {
            usageIdToAuditItemsMap.forEach(
                (usageId, usageAuditItems) -> testHelper.assertAudit(usageId, usageAuditItems));
        }
    }
}
