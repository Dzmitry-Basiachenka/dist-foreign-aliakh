package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private String scenarioId;
    private Scenario expectedScenario;

    CreateScenarioTestBuilder expectRollups(String rollupsJson, String... rollupsRightsholdersIds) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsRightholderIds = Arrays.asList(rollupsRightsholdersIds);
        return this;
    }

    CreateScenarioTestBuilder expectPreferences(String preferencesJson, String... rightholderIds) {
        this.expectedPreferencesJson = preferencesJson;
        this.expectedPreferencesRightholderIds = Arrays.asList(rightholderIds);
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

    void reset() {
        expectedRollupsJson = null;
        expectedRollupsRightholderIds = null;
        expectedPreferencesJson = null;
        expectedPreferencesRightholderIds = null;
        usageFilter = null;
        expectedUsages = null;
        scenarioId = null;
        expectedScenario = null;
    }

    /**
     * Test runner class.
     */
    class Runner {

        void run() {
            createRestServer();
            if (Objects.nonNull(expectedRollupsRightholderIds)) {
                expectGetRollups(expectedRollupsJson, expectedRollupsRightholderIds);
            }
            if (Objects.nonNull(expectedPreferencesRightholderIds)) {
                expectGetPreferences(expectedPreferencesJson, expectedPreferencesRightholderIds);
            }
            Scenario scenario = createScenario();
            assertEquals(usageFilter.getProductFamilies().iterator().next(), scenario.getProductFamily());
            scenarioId = scenario.getId();
            mockServer.verify();
            asyncMockServer.verify();
            assertScenario();
            assertUsages();
            assertScenarioActions();
            assertScenarioUsageFilter();
        }

        private Scenario createScenario() {
            if ("NTS".equals(usageFilter.getProductFamilies().iterator().next())) {
                NtsFields ntsFields = new NtsFields();
                ntsFields.setRhMinimumAmount(new BigDecimal("5.00"));
                return scenarioService.createNtsScenario(SCENARIO_NAME, ntsFields, DESCRIPTION, usageFilter);
            } else {
                return scenarioService.createScenario(SCENARIO_NAME, DESCRIPTION, usageFilter);
            }
        }

        private void assertScenario() {
            assertEquals(2, scenarioService.getScenarios().size());
            expectedScenario.setId(scenarioId);
            Scenario scenario = scenarioService.getScenarioWithAmountsAndLastAction(expectedScenario);
            assertEquals(expectedScenario.getId(), scenario.getId());
            assertEquals(expectedScenario.getName(), scenario.getName());
            assertEquals(0, expectedScenario.getNetTotal().compareTo(scenario.getNetTotal()));
            assertEquals(0, expectedScenario.getGrossTotal().compareTo(scenario.getGrossTotal()));
            assertEquals(0, expectedScenario.getServiceFeeTotal().compareTo(scenario.getServiceFeeTotal()));
            assertEquals(0, expectedScenario.getReportedTotal().compareTo(scenario.getReportedTotal()));
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
            expectedUsages.stream()
                .filter(usage -> UsageStatusEnum.ELIGIBLE == usage.getStatus())
                .forEach(this::assertUsage);
            assertNtsExcludedUsages(expectedUsages.stream()
                .filter(usage -> UsageStatusEnum.NTS_EXCLUDED == usage.getStatus())
                .collect(Collectors.toList()));
        }

        private void assertUsage(Usage usage) {
            List<UsageDto> usages =
                usageRepository.findByScenarioIdAndRhAccountNumber(usage.getRightsholder().getAccountNumber(),
                    Objects.isNull(usage.getScenarioId()) ? scenarioId : usage.getScenarioId(), null,
                    new Pageable(0, 10), null);
            assertEquals(1, usages.size());
            UsageDto usageDto = usages.get(0);
            if ("NTS".equals(usageFilter.getProductFamilies().iterator().next())) {
                assertNull(usage.getPayee().getAccountNumber());
            } else {
                assertEquals(usage.getPayee().getAccountNumber(), usageDto.getPayeeAccountNumber());
            }
            assertEquals("SYSTEM", usageDto.getUpdateUser());
            assertEquals(0, usage.getServiceFeeAmount().compareTo(usageDto.getServiceFeeAmount()));
            assertEquals(0, usage.getNetAmount().compareTo(usageDto.getNetAmount()));
            assertEquals(0, usage.getGrossAmount().compareTo(usageDto.getGrossAmount()));
        }

        private void assertNtsExcludedUsages(List<Usage> expected) {
            List<Usage> actualUsages = usageRepository.findByStatuses(UsageStatusEnum.NTS_EXCLUDED);
            assertEquals(expected.size(), actualUsages.size());
            actualUsages.forEach(usage -> {
                assertNull(usage.getScenarioId());
                assertEquals(0, usage.getServiceFeeAmount().compareTo(BigDecimal.ZERO));
                assertEquals(0, usage.getNetAmount().compareTo(BigDecimal.ZERO));
                assertEquals(0, usage.getGrossAmount().compareTo(BigDecimal.ZERO));
            });
        }

        private void createRestServer() {
            mockServer = MockRestServiceServer.createServer(restTemplate);
            asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        }

        private void expectGetPreferences(String fileName, List<String> rightholderIds) {
            rightholderIds.forEach(rightholderId ->
                mockServer.expect(MockRestRequestMatchers
                    .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelpref?orgIds%5B%5D="
                        + rightholderId
                        + "&prefCodes%5B%5D=IS-RH-FDA-PARTICIPATING,ISRHDISTINELIGIBLE"))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                        MediaType.APPLICATION_JSON))
            );
        }

        private void expectGetRollups(String fileName, List<String> rightsholdersIds) {
            rightsholdersIds.forEach(rightsholdersId ->
                (prmRollUpAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
                    .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollup?orgIds%5B%5D=" +
                        rightsholdersId + "&relationshipCode=PARENT&prefCodes%5B%5D=payee"))
                    .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                    .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                        MediaType.APPLICATION_JSON))
            );
        }
    }
}
