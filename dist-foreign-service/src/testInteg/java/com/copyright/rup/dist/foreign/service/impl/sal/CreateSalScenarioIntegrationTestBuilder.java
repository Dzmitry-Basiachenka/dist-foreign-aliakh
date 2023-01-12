package com.copyright.rup.dist.foreign.service.impl.sal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Builder for {@link CreateSalScenarioIntegrationTest}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/12/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
public class CreateSalScenarioIntegrationTestBuilder {

    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private ISalScenarioService salScenarioService;
    @Autowired
    private ISalUsageService salUsageService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;
    @Autowired
    private ServiceTestHelper testHelper;

    private String scenarioName;
    private String scenarioDescription;
    private String expectedRollupsJson;
    private List<String> expectedRollupsRightholderIds;
    private SalFields scenarioSalFields;
    private UsageFilter scenarioUsageFilter;
    private Scenario expectedScenario;
    private String expectedUsagesJsonFile;
    private ScenarioUsageFilter expectedScenarioFilter;
    private List<Pair<ScenarioActionTypeEnum, String>> expectedScenarioAudit;

    CreateSalScenarioIntegrationTestBuilder withFilter(UsageFilter usageFilter) {
        scenarioUsageFilter = usageFilter;
        return this;
    }

    CreateSalScenarioIntegrationTestBuilder expectRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderIds = List.of(rollupsRightsholdersIds);
        return this;
    }

    CreateSalScenarioIntegrationTestBuilder withScenario(String name, String description, SalFields salFields) {
        scenarioName = name;
        scenarioDescription = description;
        scenarioSalFields = salFields;
        return this;
    }

    CreateSalScenarioIntegrationTestBuilder expectScenario(Scenario scenario) {
        expectedScenario = scenario;
        return this;
    }

    CreateSalScenarioIntegrationTestBuilder expectScenarioAudit(
        List<Pair<ScenarioActionTypeEnum, String>> expectedAudit) {
        this.expectedScenarioAudit = expectedAudit;
        return this;
    }

    CreateSalScenarioIntegrationTestBuilder expectUsages(String usageJsonFile) {
        expectedUsagesJsonFile = usageJsonFile;
        return this;
    }

    CreateSalScenarioIntegrationTestBuilder expectScenarioFilter(ScenarioUsageFilter scenarioFilter) {
        expectedScenarioFilter = scenarioFilter;
        return this;
    }

    Runner build() {
        return new Runner();
    }

    void reset() {
        expectedRollupsJson = null;
        expectedRollupsRightholderIds = null;
        scenarioName = null;
        scenarioDescription = null;
        scenarioSalFields = null;
        scenarioUsageFilter = null;
        expectedScenario = null;
        expectedUsagesJsonFile = null;
        expectedScenarioFilter = null;
        expectedScenarioAudit = null;
    }

    /**
     * Test runner class.
     */
    class Runner {

        private String scenarioId;

        void run() throws IOException {
            testHelper.createRestServer();
            testHelper.expectGetRollups(expectedRollupsJson, expectedRollupsRightholderIds);
            scenarioId = salScenarioService.createScenario(scenarioName, scenarioSalFields.getFundPoolId(),
                scenarioDescription, scenarioUsageFilter).getId();
            testHelper.verifyRestServer();
            assertScenario();
            assertUsages();
            testHelper.assertScenarioAudit(scenarioId, expectedScenarioAudit);
            assertScenarioUsageFilter();
        }

        private void assertScenario() {
            Scenario actualScenario = scenarioRepository.findById(scenarioId);
            assertNotNull(actualScenario);
            assertEquals(expectedScenario.getName(), actualScenario.getName());
            assertEquals(expectedScenario.getNetTotal(), actualScenario.getNetTotal());
            assertEquals(expectedScenario.getGrossTotal(), actualScenario.getGrossTotal());
            assertEquals(expectedScenario.getServiceFeeTotal(), actualScenario.getServiceFeeTotal());
            assertEquals(expectedScenario.getStatus(), actualScenario.getStatus());
            assertEquals(expectedScenario.getDescription(), actualScenario.getDescription());
            assertSalFields(expectedScenario.getSalFields(), actualScenario.getSalFields());
        }

        private void assertUsages() throws IOException {
            List<Usage> expectedUsages = testHelper.loadExpectedUsages(expectedUsagesJsonFile);
            expectedUsages.forEach(expectedUsage -> {
                List<Usage> actualUsages = salUsageService.getUsagesByIds(List.of(expectedUsage.getId()));
                assertEquals(1, actualUsages.size());
                assertUsage(expectedUsage, actualUsages.get(0));
            });
        }

        private void assertScenarioUsageFilter() {
            ScenarioUsageFilter actual = scenarioUsageFilterService.getByScenarioId(scenarioId);
            assertNotNull(actual);
            assertEquals(expectedScenarioFilter.getRhAccountNumbers(), actual.getRhAccountNumbers());
            assertEquals(expectedScenarioFilter.getUsageBatchesIds(), actual.getUsageBatchesIds());
            assertEquals(expectedScenarioFilter.getProductFamily(), actual.getProductFamily());
            assertEquals(expectedScenarioFilter.getUsageStatus(), actual.getUsageStatus());
            assertEquals(expectedScenarioFilter.getPaymentDate(), actual.getPaymentDate());
            assertEquals(expectedScenarioFilter.getFiscalYear(), actual.getFiscalYear());
            assertEquals(expectedScenarioFilter.getUsagePeriod(), actual.getUsagePeriod());
        }

        private void assertSalFields(SalFields expected, SalFields actual) {
            assertEquals(expected.getFundPoolId(), actual.getFundPoolId());
        }

        // TODO reuse ServiceTestHelper.assertUsage
        private void assertUsage(Usage expectedUsage, Usage actualUsage) {
            assertNotNull(actualUsage);
            assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
            assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
            assertEquals(scenarioId, actualUsage.getScenarioId());
            assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
            assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
                actualUsage.getRightsholder().getAccountNumber());
            assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
            assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
            assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
            assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
            assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
            assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
            assertEquals(expectedUsage.getServiceFeeAmount(), actualUsage.getServiceFeeAmount());
            assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
            assertEquals(expectedUsage.getComment(), actualUsage.getComment());
            testHelper.assertSalUsage(expectedUsage.getSalUsage(), actualUsage.getSalUsage());
        }
    }
}
