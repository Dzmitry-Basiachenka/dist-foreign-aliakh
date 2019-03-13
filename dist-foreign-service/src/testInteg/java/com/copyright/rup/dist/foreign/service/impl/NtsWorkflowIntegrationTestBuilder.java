package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.NtsWorkflowIntegrationTestBuilder.Runner;

import com.google.common.collect.Lists;
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
 * Builder for {@link NtsWorkflowIntegrationTest}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/11/18
 *
 * @author Uladzislau Shalamitski
 */
@Component
public class NtsWorkflowIntegrationTestBuilder implements Builder<Runner> {

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

    @Value("$RUP{dist.foreign.rest.prm.rightsholder.async}")
    private boolean prmRightsholderAsync;

    private String expectedRmsRequest;
    private String expectedRmsResponse;
    private String expectedOracleRequest;
    private String expectedOracleResponse;
    private String expectedPrmResponse;
    private String expectedPreferencesResponce;
    private String expectedPreferencesRightholderId;
    private Long expectedPrmAccountNumber;
    private UsageBatch usageBatch;
    private Usage expectedUsage;

    NtsWorkflowIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectUsage(Usage usage) {
        this.expectedUsage = usage;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectRmsRights(String request, String response) {
        this.expectedRmsRequest = request;
        this.expectedRmsResponse = response;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectOracleCall(String request, String response) {
        this.expectedOracleRequest = request;
        this.expectedOracleResponse = response;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectPreferences(String preferencesJson, String rightholderId) {
        this.expectedPreferencesResponce = preferencesJson;
        this.expectedPreferencesRightholderId = rightholderId;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectPrmCall(Long accountNumber, String expectedResponse) {
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
        expectedUsage = null;
        expectedPrmResponse = null;
        expectedRmsRequest = null;
        expectedRmsResponse = null;
        expectedOracleRequest = null;
        expectedOracleResponse = null;
        expectedPreferencesResponce = null;
        expectedPreferencesRightholderId = null;
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private MockRestServiceServer mockServer;
        private MockRestServiceServer asyncMockServer;

        public void run() throws InterruptedException {
            createRestServer();
            expectGetRmsRights();
            if (Objects.nonNull(expectedPrmResponse)) {
                expectPrmCall();
            }
            if (Objects.nonNull(expectedOracleRequest)) {
                expectOracleCall();
            }
            if (Objects.nonNull(expectedPreferencesResponce)) {
                expectGetPreferences();
            }
            loadNtsBatch();
            assertBatch();
            assertUsages();
            verifyRestServer();
        }

        private void loadNtsBatch() {
            String userName = RupContextUtils.getUserName();
            List<String> ntsUsageIds = usageBatchService.insertNtsBatch(usageBatch, userName);
            usageBatchService.getAndSendForGettingRights(ntsUsageIds, usageBatch.getName(), userName);
        }

        private void assertBatch() {
            UsageBatch insertedBatch = usageBatchRepository.findAll().stream()
                .filter(batch -> usageBatch.getName().equals(batch.getName()))
                .findFirst()
                .orElse(null);
            assertNotNull(insertedBatch);
            usageBatch.setId(insertedBatch.getId());
        }

        private void assertUsages() {
            UsageFilter usageFilter = new UsageFilter();
            usageFilter.setUsageBatchesIds(Collections.singleton(usageBatch.getId()));
            List<UsageDto> actualUsages = usageRepository.findDtosByFilter(usageFilter, null, null);
            if (Objects.isNull(expectedUsage)) {
                assertTrue(CollectionUtils.isEmpty(actualUsages));
            } else {
                assertEquals(1, CollectionUtils.size(actualUsages));
                UsageDto actualUsage = actualUsages.get(0);
                assertUsage(actualUsage);
                assertAudit(actualUsage.getId());
            }
        }

        private void assertUsage(UsageDto actualUsage) {
            assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
            assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
            assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
            assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
            assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getPublisher(), actualUsage.getPublisher());
            assertEquals(expectedUsage.getPublicationDate(), actualUsage.getPublicationDate());
            assertEquals(expectedUsage.getMarket(), actualUsage.getMarket());
            assertEquals(expectedUsage.getMarketPeriodFrom(), actualUsage.getMarketPeriodFrom());
            assertEquals(expectedUsage.getMarketPeriodTo(), actualUsage.getMarketPeriodTo());
            assertEquals(expectedUsage.getAuthor(), actualUsage.getAuthor());
            assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
            assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
            assertEquals(expectedUsage.getRightsholder().getAccountNumber(), actualUsage.getRhAccountNumber());
        }

        private void assertAudit(String usageId) {
            List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageId);
            assertEquals(1, CollectionUtils.size(auditItems));
            UsageAuditItem auditItem = auditItems.get(0);
            assertEquals(UsageActionTypeEnum.ELIGIBLE, auditItem.getActionType());
            assertEquals("Usage has become eligible", auditItem.getActionReason());
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
                        Lists.newArrayList("period_end_date"))))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                    expectedRmsResponse), MediaType.APPLICATION_JSON));
        }

        private void expectPrmCall() {
            (prmRightsholderAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/organization/extorgkeys?extOrgKeys%5B%5D=" +
                    expectedPrmAccountNumber + "&fmt=json"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                    expectedPrmResponse), MediaType.APPLICATION_JSON));
        }

        private void expectOracleCall() {
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/oracle-ap-rest/getRhTaxInformation?fmt=json"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content()
                    .string(new JsonMatcher(TestUtils.fileToString(this.getClass(), expectedOracleRequest))))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                    expectedOracleResponse), MediaType.APPLICATION_JSON));
        }

        private void expectGetPreferences() {
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelpref?orgIds%5B%5D="
                    + expectedPreferencesRightholderId
                    + "&prefCodes%5B%5D=IS-RH-FDA-PARTICIPATING,ISRHDISTINELIGIBLE"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                    expectedPreferencesResponce), MediaType.APPLICATION_JSON));
        }
    }
}
