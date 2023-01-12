package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private String scenarioId;
    private String productFamily;
    private List<Usage> expectedUsages;
    private Scenario expectedScenario;
    private List<Pair<ScenarioActionTypeEnum, String>> expectedScenarioAudit;

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private ServiceTestHelper testHelper;

    RefreshScenarioTestBuilder expectRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderIds = List.of(rollupsRightsholdersIds);
        return this;
    }

    RefreshScenarioTestBuilder expectPreferences(String preferencesJson, String... rightholderIds) {
        this.expectedPreferencesJson = preferencesJson;
        this.expectedPreferencesRightholderIds = List.of(rightholderIds);
        return this;
    }

    RefreshScenarioTestBuilder withScenario(String id) {
        this.scenarioId = id;
        return this;
    }

    RefreshScenarioTestBuilder withProductFamily(String scenarioProductFamily) {
        this.productFamily = scenarioProductFamily;
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

    RefreshScenarioTestBuilder expectScenarioAudit(List<Pair<ScenarioActionTypeEnum, String>> expectedAudit) {
        this.expectedScenarioAudit = expectedAudit;
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
            Scenario scenario = scenarioRepository.findByProductFamily(productFamily)
                .stream()
                .filter(s -> s.getId().equals(scenarioId))
                .findFirst()
                .orElse(null);
            scenarioService.refreshScenario(scenario);
            testHelper.verifyRestServer();
            assertScenario();
            testHelper.assertUsages(expectedUsages);
            testHelper.assertScenarioAudit(scenarioId, expectedScenarioAudit);
        }

        private void assertScenario() {
            expectedScenario.setId(scenarioId);
            Scenario scenario = scenarioService.getScenarioWithAmountsAndLastAction(expectedScenario);
            assertEquals(expectedScenario.getId(), scenario.getId());
            assertEquals(expectedScenario.getName(), scenario.getName());
            assertEquals(expectedScenario.getNetTotal(), scenario.getNetTotal());
            assertEquals(expectedScenario.getGrossTotal(), scenario.getGrossTotal());
            assertEquals(expectedScenario.getServiceFeeTotal(), scenario.getServiceFeeTotal());
            assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
            assertEquals(expectedScenario.getDescription(), scenario.getDescription());
            assertEquals("SYSTEM", scenario.getCreateUser());
            assertEquals("SYSTEM", scenario.getUpdateUser());
            assertEquals(2, scenario.getVersion());
        }
    }
}
