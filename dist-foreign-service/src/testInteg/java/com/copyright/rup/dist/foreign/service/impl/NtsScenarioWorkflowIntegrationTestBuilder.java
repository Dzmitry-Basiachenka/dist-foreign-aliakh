package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.NtsScenarioWorkflowIntegrationTestBuilder.Runner;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;
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
import java.util.Objects;

/**
 * Builder for {@link NtsScenarioWorkflowIntegrationTest}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/06/19
 *
 * @author Pavel Liakh
 */
@Component
public class NtsScenarioWorkflowIntegrationTestBuilder implements Builder<Runner> {

    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Autowired
    private SqsClientMock sqsClientMock;

    @Value("$RUP{dist.foreign.rest.prm.rightsholder.async}")
    private boolean prmRightsholderAsync;

    private String expectedRmsRequest;
    private String expectedRmsResponse;
    private String expectedOracleResponse;
    private Long expectedOracleAccountNumber;
    private String expectedPrmResponse;
    private String expectedPreferencesResponse;
    private String expectedPreferencesRightsholderId;
    private String expectedLmDetailsJsonFile;
    private Long expectedPrmAccountNumber;
    private UsageBatch usageBatch;
    private List<Usage> expectedUsages;
    private UsageAuditItem expectedAudit;
    private Scenario expectedScenario;

    NtsScenarioWorkflowIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    NtsScenarioWorkflowIntegrationTestBuilder expectUsage(Usage... usages) {
        this.expectedUsages = Arrays.asList(usages);
        return this;
    }

    NtsScenarioWorkflowIntegrationTestBuilder expectAudit(UsageAuditItem audit) {
        this.expectedAudit = audit;
        return this;
    }

    NtsScenarioWorkflowIntegrationTestBuilder expectRmsRights(String request, String response) {
        this.expectedRmsRequest = request;
        this.expectedRmsResponse = response;
        return this;
    }

    NtsScenarioWorkflowIntegrationTestBuilder expectOracleCall(Long accountNumber, String response) {
        this.expectedOracleAccountNumber = accountNumber;
        this.expectedOracleResponse = response;
        return this;
    }

    NtsScenarioWorkflowIntegrationTestBuilder expectPreferences(String preferencesJson, String rightsholderId) {
        this.expectedPreferencesResponse = preferencesJson;
        this.expectedPreferencesRightsholderId = rightsholderId;
        return this;
    }

    NtsScenarioWorkflowIntegrationTestBuilder expectPrmCall(Long accountNumber, String expectedResponse) {
        this.expectedPrmAccountNumber = accountNumber;
        this.expectedPrmResponse = expectedResponse;
        return this;
    }

    NtsScenarioWorkflowIntegrationTestBuilder expectLmDetails(String jsonFile) {
        this.expectedLmDetailsJsonFile = jsonFile;
        return this;
    }

    NtsScenarioWorkflowIntegrationTestBuilder expectScenario(Scenario scenario) {
        this.expectedScenario = scenario;
        return this;
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    void reset() {
        usageBatch = null;
        expectedAudit = null;
        expectedUsages = null;
        expectedPrmAccountNumber = null;
        expectedOracleAccountNumber = null;
        expectedPrmResponse = null;
        expectedRmsRequest = null;
        expectedRmsResponse = null;
        expectedOracleResponse = null;
        expectedPreferencesResponse = null;
        expectedPreferencesRightsholderId = null;
        expectedLmDetailsJsonFile = null;
        this.sqsClientMock.reset();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private MockRestServiceServer mockServer;
        private MockRestServiceServer asyncMockServer;
        private List<String> actualUsageIds;
        private Scenario actualScenario;

        public void run() {
            createRestServer();
            expectGetRmsRights();
            if (Objects.nonNull(expectedPrmResponse)) {
                expectPrmCall();
            }
            if (Objects.nonNull(expectedOracleAccountNumber)) {
                expectOracleCall();
            }
            if (Objects.nonNull(expectedPreferencesResponse)) {
                expectGetPreferences();
            }
            loadNtsBatch();
            createScenario();
            scenarioService.submit(actualScenario, "Submitting actualScenario for testing purposes");
            scenarioService.approve(actualScenario, "Approving actualScenario for testing purposes");
            sendScenarioToLm();
            assertScenario();
            assertArchivedUsages();
            assertAudit();
            verifyRestServer();
        }

        private void loadNtsBatch() {
            actualUsageIds = usageBatchService.insertNtsBatch(usageBatch, RupContextUtils.getUserName());
            usageBatchService.getAndSendForGettingRights(actualUsageIds, usageBatch.getName());
        }

        private void createScenario() {
            UsageFilter usageFilter = new UsageFilter();
            usageFilter.setUsageBatchesIds(Collections.singleton(usageBatch.getId()));
            usageFilter.setProductFamilies(Collections.singleton("NTS"));
            NtsFields ntsFields = new NtsFields();
            ntsFields.setRhMinimumAmount(new BigDecimal("300"));
            actualScenario = scenarioService.createNtsScenario("Test Scenario", ntsFields, null, usageFilter);
        }

        private void sendScenarioToLm() {
            scenarioService.sendToLm(actualScenario);
            sqsClientMock.assertSendMessages("fda-test-sf-detail.fifo",
                Collections.singletonList(TestUtils.fileToString(this.getClass(), expectedLmDetailsJsonFile)),
                Collections.singletonList("detail_id"), ImmutableMap.of("source", "FDA"));
        }

        private void assertScenario() {
            actualScenario = scenarioService.getScenarios().stream()
                .filter(scenario -> actualScenario.getId().equals(scenario.getId()))
                .findAny().get();
            assertNotNull(actualScenario);
            assertEquals(expectedScenario.getNtsFields().getRhMinimumAmount(),
                actualScenario.getNtsFields().getRhMinimumAmount());
            assertEquals(expectedScenario.getStatus(), actualScenario.getStatus());
        }

        private void assertArchivedUsages() {
            List<UsageDto> actualUsages =
                usageArchiveRepository.findByScenarioIdAndRhAccountNumber(actualScenario.getId(),
                    1000023401L, null, null, null);
            assertEquals(1, CollectionUtils.size(actualUsages));
            assertUsage(expectedUsages.get(0), actualUsages.get(0));
        }

        private void assertUsage(Usage expectedUsage, UsageDto actualUsage) {
            assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
            assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
            assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
            assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
            assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
            assertEquals(expectedUsage.getPublisher(), actualUsage.getPublisher());
            assertEquals(expectedUsage.getPublicationDate(), actualUsage.getPublicationDate());
            assertEquals(expectedUsage.getMarket(), actualUsage.getMarket());
            assertEquals(expectedUsage.getMarketPeriodFrom(), actualUsage.getMarketPeriodFrom());
            assertEquals(expectedUsage.getMarketPeriodTo(), actualUsage.getMarketPeriodTo());
            assertEquals(expectedUsage.getAuthor(), actualUsage.getAuthor());
            assertEquals(0, expectedUsage.getGrossAmount().compareTo(actualUsage.getGrossAmount()));
            assertEquals(0, expectedUsage.getNetAmount().compareTo(actualUsage.getNetAmount()));
            assertEquals(0, expectedUsage.getServiceFee().compareTo(actualUsage.getServiceFee()));
            assertEquals(0, expectedUsage.getServiceFeeAmount().compareTo(actualUsage.getServiceFeeAmount()));
            assertEquals(0, expectedUsage.getReportedValue().compareTo(actualUsage.getReportedValue()));
            assertEquals(expectedUsage.getRightsholder().getAccountNumber(), actualUsage.getRhAccountNumber());
        }

        private void assertAudit() {
            actualUsageIds.forEach(Objects.isNull(expectedAudit)
                ? usageId -> assertTrue(CollectionUtils.isEmpty(usageAuditService.getUsageAudit(usageId)))
                : this::assertAudit);
        }

        private void assertAudit(String usageId) {
            List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageId);
            assertEquals(1, CollectionUtils.size(auditItems));
            UsageAuditItem auditItem = auditItems.get(0);
            assertEquals(expectedAudit.getActionType(), auditItem.getActionType());
            assertEquals(expectedAudit.getActionReason(), auditItem.getActionReason());
        }

        private void createRestServer() {
            mockServer = MockRestServiceServer.createServer(restTemplate);
            asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        }

        private void verifyRestServer() {
            mockServer.verify();
            asyncMockServer.verify();
        }

        private void expectGetRmsRights() {
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:9051/rms-rights-rest/rights/"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content()
                    .string(new JsonMatcher(TestUtils.fileToString(this.getClass(), expectedRmsRequest),
                        Collections.singletonList("period_end_date"))))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                    expectedRmsResponse), MediaType.APPLICATION_JSON));
        }

        private void expectPrmCall() {
            (prmRightsholderAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/organization/extorgkeysv2?extOrgKeys=" +
                    expectedPrmAccountNumber))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                    expectedPrmResponse), MediaType.APPLICATION_JSON));
        }

        private void expectOracleCall() {
            mockServer.expect(MockRestRequestMatchers
                .requestTo(
                    "http://localhost:8080/oracle-ap-rest/getRightsholderDataInfo?rightsholderAccountNumbers=" +
                        expectedOracleAccountNumber))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                    expectedOracleResponse), MediaType.APPLICATION_JSON));
        }

        private void expectGetPreferences() {
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefv2?orgIds="
                    + expectedPreferencesRightsholderId
                    + "&prefCodes=IS-RH-FDA-PARTICIPATING,ISRHDISTINELIGIBLE"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                    expectedPreferencesResponse), MediaType.APPLICATION_JSON));
        }
    }
}
