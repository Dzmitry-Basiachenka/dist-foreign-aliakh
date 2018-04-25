package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Lists;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
public class ReconcileRightsholdersTestBuilder {

    private Scenario expectedScenario;
    private String rmsResponse;
    private String rmsRequest;
    private String expectedPreferencesJson;
    private String expectedRollupsJson;
    private String expectedRollupsIds;
    private String expectedPrmResponse;
    private Set<RightsholderDiscrepancy> expectedDiscrepancies = new HashSet<>();
    private List<Usage> expectedUsages = new ArrayList<>();

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Value("$RUP{dist.foreign.integration.rest.prm.rollups.async}")
    private boolean prmRollUpAsync;
    @Value("$RUP{dist.foreign.integration.rest.prm.rightsholder.async}")
    private boolean prmRightsholderAsync;

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

    ReconcileRightsholdersTestBuilder expectPrmCall(String expectedResponse) {
        this.expectedPrmResponse = expectedResponse;
        return this;
    }

    ReconcileRightsholdersTestBuilder expectRollups(String rollupsJson, String... rollups) {
        this.expectedRollupsJson = rollupsJson;
        this.expectedRollupsIds = StringUtils.join(rollups, ',');
        return this;
    }

    ReconcileRightsholdersTestBuilder expectDiscrepancies(Set<RightsholderDiscrepancy> discrepancies) {
        this.expectedDiscrepancies = discrepancies;
        return this;
    }

    ReconcileRightsholdersTestBuilder expectPreferences(String preferencesJson) {
        this.expectedPreferencesJson = preferencesJson;
        return this;
    }

    ReconcileRightsholdersTestBuilder expectUsages(Usage... usages) {
        this.expectedUsages = Arrays.asList(usages);
        return this;
    }

    void reset() {
        this.expectedDiscrepancies = new HashSet<>();
        this.expectedUsages = new ArrayList<>();
    }

    /**
     * Test runner class.
     */
    class Runner {

        private final MockRestServiceServer mockServer;
        private final MockRestServiceServer asyncMockServer;

        Runner() {
            this.mockServer = MockRestServiceServer.createServer(restTemplate);
            this.asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        }

        void run() {
            prepareRmsExpectations();
            if (Objects.nonNull(expectedPrmResponse)) {
                expectPrmCall();
            }
            if (Objects.nonNull(expectedRollupsJson)) {
                expectGetRollups();
            }
            expectGetPreferences();
            scenarioService.saveRightsholderDiscrepancies(expectedScenario);
            assertDiscrepancies();
            scenarioService.approveOwnershipChanges(expectedScenario);
            assertUsages();
            mockServer.verify();
            asyncMockServer.verify();
        }

        private void prepareRmsExpectations() {
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:9051/rms-rights-rest/all-rights/"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content()
                    .string(new JsonMatcher(TestUtils.fileToString(this.getClass(), rmsRequest),
                        Lists.newArrayList("period_end_date"))))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), rmsResponse),
                    MediaType.APPLICATION_JSON));
        }

        private void expectGetRollups() {
            (prmRollUpAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollup?orgIds%5B%5D=" +
                    expectedRollupsIds + "&relationshipCode=PARENT&prefCodes%5B%5D=payee"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(
                    MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), expectedRollupsJson),
                        MediaType.APPLICATION_JSON));
        }

        private void expectPrmCall() {
            (prmRightsholderAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
                .requestTo(
                    "http://localhost:8080/party-rest/organization/extorgkeys?extOrgKeys%5B%5D=1000002137&fmt=json"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(
                    MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), expectedPrmResponse),
                        MediaType.APPLICATION_JSON));
        }

        private void expectGetPreferences() {
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/all?fmt=json"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(
                    TestUtils.fileToString(this.getClass(), expectedPreferencesJson), MediaType.APPLICATION_JSON));
        }

        private void assertDiscrepancies() {
            List<RightsholderDiscrepancy> discrepancies =
                rightsholderDiscrepancyService.getDiscrepanciesByScenarioIdAndStatus(expectedScenario.getId(),
                    RightsholderDiscrepancyStatusEnum.IN_PROGRESS, null, null);
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
            IntStream.range(0, usages.size()).forEach(i -> assertUsage(expectedUsages.get(i), usages.get(i)));
        }

        private void assertUsage(Usage expected, Usage actual) {
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
