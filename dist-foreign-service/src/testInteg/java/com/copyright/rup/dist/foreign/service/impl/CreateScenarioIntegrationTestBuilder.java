package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;

import org.apache.commons.collections4.CollectionUtils;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private IScenarioService scenarioService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Value("$RUP{dist.foreign.rest.prm.rollups.async}")
    private boolean prmRollUpAsync;

    private MockRestServiceServer mockServer;
    private MockRestServiceServer asyncMockServer;
    private String expectedRollupsJson;
    private List<String> expectedRollupsRightholderIds;
    private String expectedPreferencesJson;
    private List<String> expectedPreferencesRightholderIds;
    private UsageFilter usageFilter;
    private List<Usage> expectedUsages;
    private List<Usage> expectedNtsExcludedUsages;
    private List<Usage> expectedAlreadyInScenarioUsages;
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

    CreateScenarioIntegrationTestBuilder expectNtsExcludedUsages(List<Usage> ntsExcludedUsages) {
        this.expectedNtsExcludedUsages = ntsExcludedUsages;
        return this;
    }

    CreateScenarioIntegrationTestBuilder expectScenario(Scenario scenario) {
        this.expectedScenario = scenario;
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
        expectedNtsExcludedUsages = null;
        expectedScenario = null;
    }

    /**
     * Test runner class.
     */
    class Runner {

        private String scenarioId;
        private final String productFamily = usageFilter.getProductFamilies().iterator().next();

        Runner() {
            expectedUsages.forEach(usage -> {
                usage.setReportedValue(
                    usageRepository.findByIds(Collections.singletonList(usage.getId())).get(0).getReportedValue());
            });
        }

        void run() {
            createRestServer();
            if (Objects.nonNull(expectedRollupsRightholderIds)) {
                expectGetRollups(expectedRollupsJson, expectedRollupsRightholderIds);
            }
            if (Objects.nonNull(expectedPreferencesRightholderIds)) {
                expectGetPreferences(expectedPreferencesJson, expectedPreferencesRightholderIds);
            }
            createScenario();
            mockServer.verify();
            asyncMockServer.verify();
            assertScenario();
            assertUsages();
            assertScenarioActions();
            assertScenarioUsageFilter();
        }

        private void createScenario() {
            Scenario scenario;
            if ("NTS".equals(usageFilter.getProductFamilies().iterator().next())) {
                scenario = scenarioService.createNtsScenario(SCENARIO_NAME, expectedScenario.getNtsFields(),
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
            assertEquals(expectedScenario.getReportedTotal(), scenario.getReportedTotal());
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

        private void assertScenarioActions() {
            List<ScenarioAuditItem> actions = scenarioAuditService.getActions(scenarioId);
            assertTrue(CollectionUtils.isNotEmpty(actions));
            assertEquals(1, CollectionUtils.size(actions));
            assertEquals(ScenarioActionTypeEnum.ADDED_USAGES, actions.get(0).getActionType());
        }

        private void assertScenarioUsageFilter() {
            ScenarioUsageFilter actualUsageFilter = scenarioUsageFilterService.getByScenarioId(scenarioId);
            assertNotNull(actualUsageFilter);
            assertEquals(scenarioId, actualUsageFilter.getScenarioId());
            assertTrue(actualUsageFilter.getRhAccountNumbers().isEmpty());
            assertEquals(usageFilter.getUsageBatchesIds(), actualUsageFilter.getUsageBatchesIds());
            assertEquals(usageFilter.getProductFamilies().iterator().next(), actualUsageFilter.getProductFamily());
            assertEquals(UsageStatusEnum.ELIGIBLE, actualUsageFilter.getUsageStatus());
            assertNull(actualUsageFilter.getPaymentDate());
            assertNull(actualUsageFilter.getFiscalYear());
        }

        private void assertUsages() {
            List<Usage> actualUsages = usageRepository.findByScenarioId(scenarioId);
            assertEquals(expectedUsages.size(), actualUsages.size());
            Map<String, Usage> expectedIdToUsageMap =
                expectedUsages.stream().collect(Collectors.toMap(Usage::getId, usage -> usage));
            Map<String, Usage> actualIdToUsageMap =
                actualUsages.stream().collect(Collectors.toMap(Usage::getId, usage -> usage));
            expectedIdToUsageMap.keySet().forEach(id ->
                assertUsage(expectedIdToUsageMap.get(id), actualIdToUsageMap.get(id)));
            if (Objects.nonNull(expectedNtsExcludedUsages)) {
                assertNtsExcludedUsages();
            }
            if (Objects.nonNull(expectedAlreadyInScenarioUsages)) {
                assertAlreadyInScenarioUsages();
            }
        }

        private void assertUsage(Usage expectedUsage, Usage actualUsage) {
            assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
            assertEquals(productFamily, actualUsage.getProductFamily());
            assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
            assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
            assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
            assertEquals(expectedUsage.getServiceFeeAmount(), actualUsage.getServiceFeeAmount());
            assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
            assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
                actualUsage.getRightsholder().getAccountNumber());
            assertEquals(expectedUsage.getPayee().getAccountNumber(), actualUsage.getPayee().getAccountNumber());
        }

        private void assertAlreadyInScenarioUsages() {
            usageRepository.findByIds(
                expectedAlreadyInScenarioUsages.stream().map(Usage::getId).collect(Collectors.toList()))
                .forEach(actualUsage -> {
                    assertNotNull(actualUsage.getScenarioId());
                    assertNotEquals(scenarioId, actualUsage.getScenarioId());
                });
        }

        private void assertNtsExcludedUsages() {
            List<Usage> actualNtsExcludedUsages =
                usageRepository.findByIds(expectedNtsExcludedUsages.stream().map(Usage::getId).collect(
                    Collectors.toList()));
            assertEquals(expectedNtsExcludedUsages.size(), actualNtsExcludedUsages.size());
            actualNtsExcludedUsages.forEach(actualUsage -> {
                assertEquals(UsageStatusEnum.NTS_EXCLUDED, actualUsage.getStatus());
                assertEquals(0, actualUsage.getServiceFeeAmount().compareTo(BigDecimal.ZERO));
                assertEquals(0, actualUsage.getNetAmount().compareTo(BigDecimal.ZERO));
                assertEquals(0, actualUsage.getGrossAmount().compareTo(BigDecimal.ZERO));
            });
        }

        private void createRestServer() {
            mockServer = MockRestServiceServer.createServer(restTemplate);
            asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        }

        private void expectGetPreferences(String fileName, List<String> rightholderIds) {
            rightholderIds.forEach(rightholderId ->
                mockServer.expect(MockRestRequestMatchers
                    .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefv2?orgIds="
                        + rightholderId
                        + "&prefCodes=IS-RH-FDA-PARTICIPATING,ISRHDISTINELIGIBLE"))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                        MediaType.APPLICATION_JSON))
            );
        }

        private void expectGetRollups(String fileName, List<String> rightsholdersIds) {
            rightsholdersIds.forEach(rightsholdersId ->
                (prmRollUpAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
                    .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollupv2?orgIds=" +
                        rightsholdersId + "&relationshipCode=PARENT&prefCodes=payee"))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                        MediaType.APPLICATION_JSON))
            );
        }
    }
}
