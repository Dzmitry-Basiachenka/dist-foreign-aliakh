package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * Builder for {@link CreateScenarioTest}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 11/13/2017
 *
 * @author Uladzislau_Shalamitski
 */
@Component
class CreateScenarioTestBuilder {

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;
    private String expectedRollupsIds;
    private String expectedRollupsJson;
    private String expectedPreferencesJson;
    private UsageFilter usageFilter;
    private List<Usage> expectedUsages;
    private String scenarioId;
    private Scenario expectedScenario;

    CreateScenarioTestBuilder expectRollups(String rollupsJson, String... rollups) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsIds = StringUtils.join(rollups, ',');
        return this;
    }

    CreateScenarioTestBuilder expectPreferences(String preferencesJson) {
        this.expectedPreferencesJson = preferencesJson;
        return this;
    }

    CreateScenarioTestBuilder withFilter(UsageFilter filter) {
        this.usageFilter = filter;
        return this;
    }

    CreateScenarioTestBuilder expectUsages(List<Usage> usages) {
        this.expectedUsages = usages;
        return this;
    }

    CreateScenarioTestBuilder expectScenario(Scenario scenario) {
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
            createRestServer();
            expectGetPreferences(expectedPreferencesJson);
            expectGetRollups(expectedRollupsJson, expectedRollupsIds);
            scenarioId = scenarioService.createScenario("Test Scenario", "Scenario Description", usageFilter);
            assertScenario();
            assertUsages();
            assertScenarioActions();
            mockServer.verify();
        }

        private void assertScenario() {
            assertEquals(2, scenarioService.getScenarios().size());
            expectedScenario.setId(scenarioId);
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

        private void assertScenarioActions() {
            List<ScenarioAuditItem> actions = scenarioAuditService.getActions(scenarioId);
            assertTrue(CollectionUtils.isNotEmpty(actions));
            assertEquals(1, CollectionUtils.size(actions));
            assertEquals(ScenarioActionTypeEnum.ADDED_USAGES, actions.get(0).getActionType());
        }

        private void assertUsages() {
            expectedUsages.forEach(this::assertUsage);
        }

        private void assertUsage(Usage usage) {
            List<UsageDto> usages =
                usageRepository.findByScenarioIdAndRhAccountNumber(usage.getRightsholder().getAccountNumber(),
                    Objects.isNull(usage.getScenarioId()) ? scenarioId : usage.getScenarioId(), null,
                    new Pageable(0, 10), null);
            assertEquals(1, usages.size());
            UsageDto usageDto = usages.get(0);
            assertEquals(usage.getPayee().getAccountNumber(), usageDto.getPayeeAccountNumber(), 0);
            assertEquals(UsageStatusEnum.LOCKED, usageDto.getStatus());
            assertEquals("SYSTEM", usageDto.getUpdateUser());
            assertEquals(usage.getServiceFeeAmount(), usageDto.getServiceFeeAmount());
            assertEquals(usage.getNetAmount(), usageDto.getNetAmount());
        }

        private void createRestServer() {
            mockServer = MockRestServiceServer.createServer(restTemplate);
        }

        private void expectGetPreferences(String fileName) {
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/all?fmt=json"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                    MediaType.APPLICATION_JSON));
        }

        private void expectGetRollups(String fileName, String rightsholdersIds) {
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollup?orgIds%5B%5D=" +
                    rightsholdersIds + "&relationshipCode=PARENT&prefCodes%5B%5D=payee"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                    MediaType.APPLICATION_JSON));
        }
    }
}
