package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Builder for {@link RefreshScenarioTest}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/15/2018
 *
 * @author Aliaksandr Liakh
 */
@Component
class RefreshScenarioTestBuilder {

    private String expectedRollupsJson;
    private List<String> expectedRollupsRightholderIds;
    private String expectedPreferencesJson;
    private List<String> expectedPreferencesRightholderIds;
    private String expectedScenarioId;
    private List<Usage> expectedUsages;
    private Scenario expectedScenario;

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private ServiceTestHelper testHelper;

    RefreshScenarioTestBuilder expectRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderIds = Arrays.asList(rollupsRightsholdersIds);
        return this;
    }

    RefreshScenarioTestBuilder expectPreferences(String preferencesJson, String... rightholderIds) {
        this.expectedPreferencesJson = preferencesJson;
        this.expectedPreferencesRightholderIds = Arrays.asList(rightholderIds);
        return this;
    }

    RefreshScenarioTestBuilder withScenario(String scenarioId) {
        this.expectedScenarioId = scenarioId;
        return this;
    }

    RefreshScenarioTestBuilder expectUsages(List<Usage> usages) {
        this.expectedUsages = usages;
        return this;
    }

    RefreshScenarioTestBuilder expectScenario(Scenario scenario) {
        this.expectedScenario = scenario;
        return this;
    }

    Runner build() {
        return new Runner();
    }

    /**
     * Test runner class.
     */
    class Runner {

        void run() {
            testHelper.createRestServer();
            testHelper.expectGetRollups(expectedRollupsJson, expectedRollupsRightholderIds);
            testHelper.expectGetPreferences(expectedPreferencesJson, expectedPreferencesRightholderIds);
            Scenario scenario = scenarioRepository.findAll()
                .stream()
                .filter(s -> s.getId().equals(expectedScenarioId))
                .findFirst()
                .orElse(null);
            scenarioService.refreshScenario(scenario);
            testHelper.verifyRestServer();
            assertScenario();
            assertUsages();
            assertScenarioActions(expectedScenarioId);
        }

        private void assertScenario() {
            assertEquals(2, scenarioService.getScenarios().size());
            expectedScenario.setId(expectedScenarioId);
            Scenario scenario = scenarioService.getScenarioWithAmountsAndLastAction(expectedScenario);
            assertEquals(expectedScenario.getId(), scenario.getId());
            assertEquals(expectedScenario.getName(), scenario.getName());
            assertEquals(expectedScenario.getNetTotal(), scenario.getNetTotal());
            assertEquals(expectedScenario.getGrossTotal(), scenario.getGrossTotal());
            assertEquals(expectedScenario.getServiceFeeTotal(), scenario.getServiceFeeTotal());
            assertEquals(expectedScenario.getReportedTotal(), scenario.getReportedTotal());
            assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
            assertEquals(expectedScenario.getDescription(), scenario.getDescription());
            assertEquals("SYSTEM", scenario.getCreateUser());
            assertEquals("SYSTEM", scenario.getUpdateUser());
            assertEquals(1, scenario.getVersion());
        }

        private void assertUsages() {
            List<Usage> usages = usageRepository.findByScenarioId(expectedScenarioId);
            usages.sort(Comparator.comparing(Usage::getId));
            IntStream.range(0, expectedUsages.size()).forEach(i -> {
                Usage actualUsage = usages.get(i);
                Usage expectedUsage = expectedUsages.get(i);
                assertEquals(expectedUsage.getId(), actualUsage.getId());
                assertEquals(expectedUsage.getPayee().getAccountNumber(), actualUsage.getPayee().getAccountNumber(), 0);
                assertEquals(UsageStatusEnum.LOCKED, actualUsage.getStatus());
                assertEquals("SYSTEM", actualUsage.getUpdateUser());
                assertEquals(expectedUsage.getServiceFeeAmount(), actualUsage.getServiceFeeAmount());
                assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
                assertEquals(expectedUsage.getComment(), actualUsage.getComment());
            });
        }

        private void assertScenarioActions(String scenarioId) {
            List<ScenarioAuditItem> actions = scenarioAuditService.getActions(scenarioId);
            assertTrue(CollectionUtils.isNotEmpty(actions));
            assertEquals(1, CollectionUtils.size(actions));
            assertEquals(ScenarioActionTypeEnum.ADDED_USAGES, actions.get(0).getActionType());
        }
    }
}
