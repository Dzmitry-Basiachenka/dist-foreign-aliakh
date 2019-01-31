package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.NtsWorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.mock.RhTaxConsumerMock;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Autowired
    private SqsClientMock sqsClientMock;
    @Autowired
    @Qualifier("df.service.rhTaxConsumer")
    private RhTaxConsumerMock rhTaxConsumer;

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
    private List<UsageAuditItem> expectedUsageAuditItems;
    private UsageBatch usageBatch;
    private Usage processedUsage;
    private String expectedInternalTaxUsage;

    NtsWorkflowIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectedUsage(Usage usage) {
        this.processedUsage = usage;
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

    NtsWorkflowIntegrationTestBuilder expectUsageAudit(List<UsageAuditItem> usageAudit) {
        this.expectedUsageAuditItems = usageAudit;
        return this;
    }

    NtsWorkflowIntegrationTestBuilder expectInternalTaxUsage(String usage) {
        this.expectedInternalTaxUsage = usage;
        return this;
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    void reset() {
        usageBatch = null;
        processedUsage = null;
        expectedPrmResponse = null;
        expectedRmsRequest = null;
        expectedRmsResponse = null;
        expectedOracleRequest = null;
        expectedOracleResponse = null;
        expectedUsageAuditItems = null;
        expectedPreferencesResponce = null;
        expectedPreferencesRightholderId = null;
        expectedInternalTaxUsage = null;
        sqsClientMock.reset();
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
            if (Objects.nonNull(expectedInternalTaxUsage)) {
                expectRhTaxProducer();
            }
            loadNtsBatch();
            if (Objects.nonNull(expectedInternalTaxUsage)) {
                assertRhTaxConsumer(assertRhTaxProducer());
            }
            assertBatch();
            assertUsages();
            verifyRestServer();
        }

        private void expectRhTaxProducer() {
            sqsClientMock.expectSendMessages("fda-internal-test-df-tax.fifo",
                Collections.singletonList(TestUtils.fileToString(this.getClass(), expectedInternalTaxUsage)),
                ImmutableList.of("id", "batch_id"), Collections.emptyMap());
        }

        private String assertRhTaxProducer() {
            sqsClientMock.assertSendMessages();
            List<SendMessageRequest> requests = sqsClientMock.getActualSendMessages();
            assertEquals(1, requests.size());
            return requests.get(0).getMessageBody();
        }

        private void assertRhTaxConsumer(String message) throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(1);
            rhTaxConsumer.setLatch(latch);
            sqsClientMock.prepareReceivedMessage("fda-internal-test-df-tax.fifo", message, Collections.emptyMap());
            assertTrue(latch.await(10, TimeUnit.SECONDS));
            sqsClientMock.assertReceivedMessageDeleted();
        }

        private void loadNtsBatch() {
            List<Usage> insertedNtsUsages = usageBatchService.insertNtsBatch(usageBatch);
            usageBatchService.sendForGettingRights(insertedNtsUsages);
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
            List<UsageDto> usages = usageRepository.findDtosByFilter(usageFilter, null, null);
            if (Objects.isNull(processedUsage)) {
                assertTrue(CollectionUtils.isEmpty(usages));
            } else {
                assertEquals(1, CollectionUtils.size(usages));
                assertUsage(usages.get(0));
                assertAudit(usages.get(0));
            }
        }

        private void assertUsage(UsageDto usageDto) {
            assertEquals(processedUsage.getStatus(), usageDto.getStatus());
            assertEquals(processedUsage.getWrWrkInst(), usageDto.getWrWrkInst());
            assertEquals(processedUsage.getWorkTitle(), usageDto.getWorkTitle());
            assertEquals(processedUsage.getProductFamily(), usageDto.getProductFamily());
            assertEquals(processedUsage.getArticle(), usageDto.getArticle());
            assertEquals(processedUsage.getStandardNumber(), usageDto.getStandardNumber());
            assertEquals(processedUsage.getPublisher(), usageDto.getPublisher());
            assertEquals(processedUsage.getPublicationDate(), usageDto.getPublicationDate());
            assertEquals(processedUsage.getMarket(), usageDto.getMarket());
            assertEquals(processedUsage.getMarketPeriodFrom(), usageDto.getMarketPeriodFrom());
            assertEquals(processedUsage.getMarketPeriodTo(), usageDto.getMarketPeriodTo());
            assertEquals(processedUsage.getAuthor(), usageDto.getAuthor());
            assertEquals(processedUsage.getGrossAmount(), usageDto.getGrossAmount());
            assertEquals(processedUsage.getRightsholder().getAccountNumber(), usageDto.getRhAccountNumber());
        }

        private void assertAudit(UsageDto usageDto) {
            List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageDto.getId());
            assertEquals(CollectionUtils.size(expectedUsageAuditItems), CollectionUtils.size(auditItems));
            IntStream.range(0, expectedUsageAuditItems.size()).forEach(index -> {
                assertUsageAuditItem(expectedUsageAuditItems.get(index), auditItems.get(index));
            });
        }

        private void assertUsageAuditItem(UsageAuditItem expectedItem, UsageAuditItem actualItem) {
            assertEquals(expectedItem.getActionType(), actualItem.getActionType());
            assertEquals(expectedItem.getActionReason(), actualItem.getActionReason());
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
                .requestTo("http://localhost:9051/rms-rights-rest/all-rights/"))
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
