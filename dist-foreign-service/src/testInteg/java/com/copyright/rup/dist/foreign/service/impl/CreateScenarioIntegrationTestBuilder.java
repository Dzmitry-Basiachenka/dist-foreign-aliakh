package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Builder for {@link CreateScenarioIntegrationTest}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/13/2017
 *
 * @author Uladzislau_Shalamitski
 */
@Component
class CreateScenarioIntegrationTestBuilder {

    private static final String SCENARIO_NAME = "Test Scenario";
    private static final String DESCRIPTION = "Scenario Description";

    @Autowired
    private INtsScenarioService ntsScenarioService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;
    @Autowired
    private ServiceTestHelper testHelper;

    private String expectedRollupsJson;
    private List<String> expectedRollupsRightholderIds;
    private String expectedPreferencesJson;
    private List<String> expectedPreferencesRightholderIds;
    private UsageFilter usageFilter;
    private List<Usage> expectedUsages;
    private List<Usage> expectedScenarioExcludedUsages;
    private List<Usage> expectedAlreadyInScenarioUsages;
    private List<Pair<ScenarioActionTypeEnum, String>> expectedScenarioAudit;
    private Scenario expectedScenario;

    CreateScenarioIntegrationTestBuilder expectRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderIds = Arrays.asList(rollupsRightsholdersIds);
        return this;
    }

    CreateScenarioIntegrationTestBuilder expectPreferences(String preferencesJson, String... rightholderIds) {
        this.expectedPreferencesJson = preferencesJson;
        this.expectedPreferencesRightholderIds = Arrays.asList(rightholderIds);
        return this;
    }

    CreateScenarioIntegrationTestBuilder withFilter(UsageFilter filter) {
        this.usageFilter = filter;
        return this;
    }

    CreateScenarioIntegrationTestBuilder expectUsages(List<Usage> usages) {
        this.expectedUsages = usages;
        return this;
    }

    CreateScenarioIntegrationTestBuilder expectUsagesAlreadyInScenario(List<Usage> alreadyInScenarioUsages) {
        this.expectedAlreadyInScenarioUsages = alreadyInScenarioUsages;
        return this;
    }

    CreateScenarioIntegrationTestBuilder expectScenarioExcludedUsages(List<Usage> scenarioExcludedUsages) {
        this.expectedScenarioExcludedUsages = scenarioExcludedUsages;
        return this;
    }

    CreateScenarioIntegrationTestBuilder expectScenario(Scenario scenario) {
        this.expectedScenario = scenario;
        return this;
    }

    CreateScenarioIntegrationTestBuilder expectScenarioAudit(List<Pair<ScenarioActionTypeEnum, String>> expectedAudit) {
        this.expectedScenarioAudit = expectedAudit;
        return this;
    }

    Runner build() {
        return new Runner();
    }

    void reset() {
        expectedRollupsJson = null;
        expectedRollupsRightholderIds = null;
        expectedPreferencesJson = null;
        expectedPreferencesRightholderIds = null;
        usageFilter = null;
        expectedUsages = null;
        expectedAlreadyInScenarioUsages = null;
        expectedScenarioExcludedUsages = null;
        expectedScenario = null;
        expectedScenarioAudit = null;
    }

    /**
     * Test runner class.
     */
    class Runner {

        private String scenarioId;
        private final String productFamily = usageFilter.getProductFamily();

        Runner() {
            expectedUsages.forEach(usage -> usage.setReportedValue(
                usageRepository.findByIds(List.of(usage.getId())).get(0).getReportedValue()));
        }

        void run() {
            testHelper.createRestServer();
            if (Objects.nonNull(expectedRollupsRightholderIds)) {
                testHelper.expectGetRollups(expectedRollupsJson, expectedRollupsRightholderIds);
            }
            if (Objects.nonNull(expectedPreferencesRightholderIds)) {
                testHelper.expectGetPreferences(expectedPreferencesJson, expectedPreferencesRightholderIds);
            }
            createScenario();
            testHelper.verifyRestServer();
            assertScenario();
            assertUsages();
            testHelper.assertScenarioAudit(scenarioId, expectedScenarioAudit);
            assertScenarioUsageFilter();
        }

        private void createScenario() {
            Scenario scenario;
            if ("NTS".equals(usageFilter.getProductFamily())) {
                scenario = ntsScenarioService.createScenario(SCENARIO_NAME, expectedScenario.getNtsFields(),
                    DESCRIPTION, usageFilter);
            } else {
                scenario = scenarioService.createScenario(SCENARIO_NAME, DESCRIPTION, usageFilter);
            }
            assertEquals(productFamily, scenario.getProductFamily());
            scenarioId = scenario.getId();
            expectedScenario.setId(scenario.getId());
        }

        private void assertScenario() {
            Scenario scenario = scenarioService.getScenarioWithAmountsAndLastAction(expectedScenario);
            assertEquals(expectedScenario.getId(), scenario.getId());
            assertEquals(expectedScenario.getName(), scenario.getName());
            assertEquals(expectedScenario.getNetTotal(), scenario.getNetTotal());
            assertEquals(expectedScenario.getGrossTotal(), scenario.getGrossTotal());
            assertEquals(expectedScenario.getServiceFeeTotal(), scenario.getServiceFeeTotal());
            assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
            assertEquals(expectedScenario.getDescription(), scenario.getDescription());
            if ("NTS".equals(productFamily)) {
                NtsFields expectedNtsFields = expectedScenario.getNtsFields();
                NtsFields actualNtsFields = scenario.getNtsFields();
                assertEquals(expectedNtsFields.getPostServiceFeeAmount(), actualNtsFields.getPostServiceFeeAmount());
                assertEquals(expectedNtsFields.getPreServiceFeeAmount(), actualNtsFields.getPreServiceFeeAmount());
            }
            assertEquals("SYSTEM", scenario.getCreateUser());
            assertEquals("SYSTEM", scenario.getUpdateUser());
            assertEquals(1, scenario.getVersion());
        }

        private void assertScenarioUsageFilter() {
            ScenarioUsageFilter actualUsageFilter = scenarioUsageFilterService.getByScenarioId(scenarioId);
            assertNotNull(actualUsageFilter);
            assertEquals(scenarioId, actualUsageFilter.getScenarioId());
            assertTrue(actualUsageFilter.getRhAccountNumbers().isEmpty());
            assertEquals(usageFilter.getUsageBatchesIds(), actualUsageFilter.getUsageBatchesIds());
            assertEquals(usageFilter.getProductFamily(), actualUsageFilter.getProductFamily());
            assertEquals(UsageStatusEnum.ELIGIBLE, actualUsageFilter.getUsageStatus());
            assertNull(actualUsageFilter.getPaymentDate());
            assertNull(actualUsageFilter.getFiscalYear());
        }

        private void assertUsages() {
            testHelper.assertUsages(expectedUsages);
            if (Objects.nonNull(expectedScenarioExcludedUsages)) {
                assertScenarioExcludedUsages();
            }
            if (Objects.nonNull(expectedAlreadyInScenarioUsages)) {
                assertAlreadyInScenarioUsages();
            }
        }

        private void assertAlreadyInScenarioUsages() {
            usageRepository.findByIds(
                expectedAlreadyInScenarioUsages.stream().map(Usage::getId).collect(Collectors.toList()))
                .forEach(actualUsage -> {
                    assertNotNull(actualUsage.getScenarioId());
                    assertNotEquals(scenarioId, actualUsage.getScenarioId());
                });
        }

        private void assertScenarioExcludedUsages() {
            List<Usage> actualScenarioExcludedUsages =
                usageRepository.findByIds(expectedScenarioExcludedUsages.stream().map(Usage::getId).collect(
                    Collectors.toList()));
            assertEquals(expectedScenarioExcludedUsages.size(), actualScenarioExcludedUsages.size());
            actualScenarioExcludedUsages.forEach(actualUsage -> {
                assertEquals(UsageStatusEnum.SCENARIO_EXCLUDED, actualUsage.getStatus());
                assertEquals(0, actualUsage.getServiceFeeAmount().compareTo(BigDecimal.ZERO));
                assertEquals(0, actualUsage.getNetAmount().compareTo(BigDecimal.ZERO));
                assertEquals(0, actualUsage.getGrossAmount().compareTo(BigDecimal.ZERO));
            });
        }
    }
}
