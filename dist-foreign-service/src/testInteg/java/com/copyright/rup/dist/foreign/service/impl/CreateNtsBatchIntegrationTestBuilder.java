package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.CreateNtsBatchIntegrationTestBuilder.Runner;

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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Builder for {@link CreateNtsBatchIntegrationTest}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/11/18
 *
 * @author Uladzislau Shalamitski
 */
// TODO {pliakh} move common REST-expectation and assertion methods to helper, apply in other tests
@Component
public class CreateNtsBatchIntegrationTestBuilder implements Builder<Runner> {

    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageRepository usageRepository;
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
    private Long expectedPrmAccountNumber;
    private UsageBatch usageBatch;
    // TODO {pliakh} allow to assert multiple usages
    private Usage expectedUsages;
    private UsageAuditItem expectedAudit;

    CreateNtsBatchIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectUsage(Usage usage) {
        this.expectedUsages = usage;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectAudit(UsageAuditItem audit) {
        this.expectedAudit = audit;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectRmsRights(String request, String response) {
        this.expectedRmsRequest = request;
        this.expectedRmsResponse = response;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectOracleCall(Long accountNumber, String response) {
        this.expectedOracleAccountNumber = accountNumber;
        this.expectedOracleResponse = response;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectPreferences(String preferencesJson, String rightsholderId) {
        this.expectedPreferencesResponse = preferencesJson;
        this.expectedPreferencesRightsholderId = rightsholderId;
        return this;
    }

    CreateNtsBatchIntegrationTestBuilder expectPrmCall(Long accountNumber, String expectedResponse) {
        this.expectedPrmAccountNumber = accountNumber;
        this.expectedPrmResponse = expectedResponse;
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
        this.sqsClientMock.reset();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private MockRestServiceServer mockServer;
        private MockRestServiceServer asyncMockServer;
        private List<String> actualUsageIds;

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
            assertBatch();
            assertUsages();
            assertAudit();
            verifyRestServer();
        }

        private void loadNtsBatch() {
            actualUsageIds = usageBatchService.insertNtsBatch(usageBatch, RupContextUtils.getUserName());
            usageBatchService.getAndSendForGettingRights(actualUsageIds, usageBatch.getName());
        }

        private void assertBatch() {
            UsageBatch insertedBatch = usageBatchRepository.findAll().stream()
                .filter(batch -> usageBatch.getName().equals(batch.getName()))
                .findFirst()
                .orElse(null);
            assertNotNull(insertedBatch);
            assertEquals(usageBatch.getFundPool(), insertedBatch.getFundPool());
            assertEquals(usageBatch.getFiscalYear(), insertedBatch.getFiscalYear());
            assertEquals(usageBatch.getGrossAmount(), insertedBatch.getGrossAmount());
            assertEquals(usageBatch.getPaymentDate(), insertedBatch.getPaymentDate());
            assertEquals(usageBatch.getRro().getAccountNumber(), insertedBatch.getRro().getAccountNumber());
            usageBatch.setId(insertedBatch.getId());
        }

        private void assertUsages() {
            if (Objects.isNull(expectedUsages)) {
                assertTrue(CollectionUtils.isEmpty(usageRepository.findByIds(actualUsageIds)));
            } else {
                List<Usage> actualUsages = usageRepository.findByIds(actualUsageIds);
                assertEquals(1, actualUsages.size());
                assertUsage(expectedUsages, actualUsages.get(0));
            }
        }

        private void assertUsage(Usage expectedUsage, Usage actualUsage) {
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
            assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
            assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
            assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
                actualUsage.getRightsholder().getAccountNumber());
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
