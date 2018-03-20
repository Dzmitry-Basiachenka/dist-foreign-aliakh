package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

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

    private MockRestServiceServer mockServer;
    private MockRestServiceServer asyncMockServer;
    private String expectedRollupsIds;
    private String expectedRollupsJson;
    private String expectedPreferencesJson;
    private String expectedScenarioId;
    private List<Usage> expectedUsages;
    private Scenario expectedScenario;

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Value("$RUP{dist.foreign.integration.rest.prm.rollups.async}")
    private boolean prmRollUpAsync;

    RefreshScenarioTestBuilder expectRollups(String rollupsJson, String... rollups) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsIds = StringUtils.join(rollups, ',');
        return this;
    }

    RefreshScenarioTestBuilder expectPreferences(String preferencesJson) {
        this.expectedPreferencesJson = preferencesJson;
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
            createRestServer();
            expectGetPreferences(expectedPreferencesJson);
            expectGetRollups(expectedRollupsJson, expectedRollupsIds);
            Scenario scenario = scenarioRepository.findAll()
                .stream()
                .filter(s -> s.getId().equals(expectedScenarioId))
                .findFirst()
                .get();
            scenarioService.refreshScenario(scenario);
            mockServer.verify();
            asyncMockServer.verify();
            assertScenario();
            assertUsages();
            assertScenarioActions();
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

        private void assertScenarioActions() {
            List<ScenarioAuditItem> actions = scenarioAuditService.getActions(expectedScenarioId);
            assertTrue(CollectionUtils.isNotEmpty(actions));
            assertEquals(1, CollectionUtils.size(actions));
            assertEquals(ScenarioActionTypeEnum.ADDED_USAGES, actions.get(0).getActionType());
        }

        private void assertUsages() {
            expectedUsages.forEach(usage -> {
                List<UsageDto> usages =
                    usageRepository.findByScenarioIdAndRhAccountNumber(usage.getRightsholder().getAccountNumber(),
                        Objects.isNull(usage.getScenarioId()) ? expectedScenarioId : usage.getScenarioId(), null,
                        new Pageable(0, 10), null);
                UsageDto result = usages.stream()
                    .filter(usageDto -> usage.getPayee().getAccountNumber().equals(usageDto.getPayeeAccountNumber())
                        && UsageStatusEnum.LOCKED == usageDto.getStatus()
                        && "SYSTEM".equals(usageDto.getUpdateUser())
                        && usage.getServiceFeeAmount().equals(usageDto.getServiceFeeAmount())
                        && usage.getNetAmount().equals(usageDto.getNetAmount()))
                    .findFirst()
                    .orElse(null);
                assertNotNull(result);
            });
        }

        private void createRestServer() {
            mockServer = MockRestServiceServer.createServer(restTemplate);
            asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        }

        private void expectGetPreferences(String fileName) {
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/all?fmt=json"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                    MediaType.APPLICATION_JSON));
        }

        private void expectGetRollups(String fileName, String rightsholdersIds) {
            (prmRollUpAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollup?orgIds%5B%5D=" +
                    rightsholdersIds + "&relationshipCode=PARENT&prefCodes%5B%5D=payee"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                    MediaType.APPLICATION_JSON));
        }
    }
}
