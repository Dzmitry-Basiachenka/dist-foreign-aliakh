package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.mock.PaidUsageConsumerMock;
import com.copyright.rup.dist.foreign.service.impl.mock.SnsMock;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
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
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Helper for test.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 18/06/19
 *
 * @author Anton Azarenka
 */
@Component
public class ServiceTestHelper {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Value("$RUP{dist.foreign.rest.prm.rightsholder.async}")
    private boolean prmRightsholderAsync;
    @Value("$RUP{dist.foreign.rest.prm.rollups.async}")
    private boolean prmRollUpAsync;
    @Autowired
    private SqsClientMock sqsClientMock;
    @Autowired
    @Qualifier("df.service.paidUsageConsumer")
    private PaidUsageConsumerMock paidUsageConsumer;

    private MockRestServiceServer mockServer;
    private MockRestServiceServer asyncMockServer;

    public void createRestServer() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
    }

    public void verifyRestServer() {
        mockServer.verify();
        asyncMockServer.verify();
    }

    public void reset() {
        sqsClientMock.reset();
    }

    public void expectGetRmsRights(String expectedRmsRequest, String expectedRmsResponse) {
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:9051/rms-rights-rest/rights/"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content()
                .string(new JsonMatcher(TestUtils.fileToString(this.getClass(), expectedRmsRequest),
                    Collections.singletonList("period_end_date"))))
            .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                expectedRmsResponse), MediaType.APPLICATION_JSON));
    }

    public void expectPrmCall(String expectedPrmResponse, Long expectedPrmAccountNumber) {
        (prmRightsholderAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/organization/extorgkeysv2?extOrgKeys=" +
                expectedPrmAccountNumber))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                expectedPrmResponse), MediaType.APPLICATION_JSON));
    }

    public void expectOracleCall(String expectedOracleResponse, Long expectedOracleAccountNumber) {
        mockServer.expect(MockRestRequestMatchers
            .requestTo(
                "http://localhost:8080/oracle-ap-rest/getRightsholderDataInfo?rightsholderAccountNumbers=" +
                    expectedOracleAccountNumber))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                expectedOracleResponse), MediaType.APPLICATION_JSON));
    }

    public void expectGetPreferences(String expectedPreferencesRightsholderId, String expectedPreferencesResponse) {
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefv2?orgIds="
                + expectedPreferencesRightsholderId
                + "&prefCodes=IS-RH-FDA-PARTICIPATING,ISRHDISTINELIGIBLE,IS-RH-STM-IPRO"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                expectedPreferencesResponse), MediaType.APPLICATION_JSON));
    }

    public void expectGetRmsRights(Map<String, String> expectedRmsRequestsToResponses) {
        expectedRmsRequestsToResponses.forEach((expectedRmsRequest, expectedRmsResponse)
            -> mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:9051/rms-rights-rest/rights/"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content()
                .string(new JsonMatcher(TestUtils.fileToString(this.getClass(), expectedRmsRequest),
                    Lists.newArrayList("period_end_date"))))
            .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                expectedRmsResponse),
                MediaType.APPLICATION_JSON)));
    }

    public void expectGetPreferences(String fileName, List<String> rightsholderIds) {
        rightsholderIds.forEach(rightsholderId ->
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefv2?orgIds="
                    + rightsholderId
                    + "&prefCodes=IS-RH-FDA-PARTICIPATING,ISRHDISTINELIGIBLE,IS-RH-STM-IPRO"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                    MediaType.APPLICATION_JSON)));
    }

    public void expectGetRollups(String fileName, List<String> rightsholdersIds) {
        rightsholdersIds.forEach(rightsholdersId ->
            (prmRollUpAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollupv2?orgIds=" +
                    rightsholdersId + "&relationshipCode=PARENT&prefCodes=payee"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                    MediaType.APPLICATION_JSON)));
    }

    public void expectCrmGetRightsDistribution(List<String> cccEventIds) {
        mockServer.expect(MockRestRequestMatchers.requestTo(
            "http://localhost:9061/legacy-integration-rest/getCCCRightsDistributionV2?eventIds="
                + String.join(",", cccEventIds)))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(this.getClass(), "crm/sendToCrm/get_rights_distribution_response_empty.json"),
                MediaType.APPLICATION_JSON));
    }

    public void expectCrmInsertRightsDistribution(String expectedRequestFileName, String responseFileName,
                                                  List<String> fieldsToIgnore) {
        String expectedRequestBody = TestUtils.fileToString(this.getClass(), expectedRequestFileName);
        String responseBody = TestUtils.fileToString(this.getClass(), responseFileName);
        mockServer.expect(MockRestRequestMatchers.requestTo(
            "http://localhost:9061/legacy-integration-rest/insertCCCRightsDistribution"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content().string(new JsonMatcher(expectedRequestBody, fieldsToIgnore)))
            .andRespond(MockRestResponseCreators.withSuccess(responseBody, MediaType.APPLICATION_JSON));
    }

    public void receivePaidUsagesFromLm(String paidUsagesMessageFile) throws InterruptedException {
        doReceivePaidUsagesFromLm(TestUtils.fileToString(this.getClass(), paidUsagesMessageFile));
    }

    public void receivePaidUsagesFromLm(String paidUsagesMessageTemplateFile, List<String> usageIds)
        throws InterruptedException {
        doReceivePaidUsagesFromLm(
            String.format(TestUtils.fileToString(this.getClass(), paidUsagesMessageTemplateFile), usageIds.toArray()));
    }

    public void assertAudit(String entityId, List<UsageAuditItem> expectedAuditItems) {
        List<UsageAuditItem> actualAuditItems = usageAuditService.getUsageAudit(entityId);
        assertEquals(CollectionUtils.size(expectedAuditItems), expectedAuditItems.size());
        IntStream.range(0, expectedAuditItems.size())
            .forEach(index -> {
                UsageAuditItem expectedItem = expectedAuditItems.get(index);
                UsageAuditItem actualItem = actualAuditItems.get(index);
                assertEquals(expectedItem.getActionReason(), actualItem.getActionReason());
                assertEquals(expectedItem.getActionType(), actualItem.getActionType());
            });
    }

    public void assertUsages(List<Usage> expectedUsages) {
        expectedUsages.forEach(expectedUsage -> {
            List<Usage> actualUsages = usageRepository.findByIds(Collections.singletonList(expectedUsage.getId()));
            assertUsage(expectedUsage, actualUsages.get(0));
        });
    }

    public void assertPaidUsages(List<PaidUsage> expectedUsages) {
        expectedUsages.forEach(expectedUsage -> {
            PaidUsage actualUsage = getPaidUsageByLmDetailId(expectedUsage.getLmDetailId());
            assertUsage(expectedUsage, actualUsage);
            assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
            assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
            assertEquals(expectedUsage.getPublisher(), actualUsage.getPublisher());
            assertEquals(expectedUsage.getPublicationDate(), actualUsage.getPublicationDate());
            assertEquals(expectedUsage.getMarket(), actualUsage.getMarket());
            assertEquals(expectedUsage.getMarketPeriodFrom(), actualUsage.getMarketPeriodFrom());
            assertEquals(expectedUsage.getMarketPeriodTo(), actualUsage.getMarketPeriodTo());
            assertEquals(expectedUsage.getAuthor(), actualUsage.getAuthor());
            assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
            assertEquals(expectedUsage.isRhParticipating(), actualUsage.isRhParticipating());
            assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
            assertEquals(expectedUsage.getDistributionName(), actualUsage.getDistributionName());
            assertEquals(expectedUsage.getDistributionDate(), actualUsage.getDistributionDate());
            assertEquals(expectedUsage.getCccEventId(), actualUsage.getCccEventId());
            assertEquals(expectedUsage.getCheckNumber(), actualUsage.getCheckNumber());
            assertEquals(expectedUsage.getCheckDate(), actualUsage.getCheckDate());
            assertEquals(expectedUsage.getPeriodEndDate(), actualUsage.getPeriodEndDate());
            assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        });
    }

    private void doReceivePaidUsagesFromLm(String message) throws InterruptedException {
        paidUsageConsumer.setLatch(new CountDownLatch(1));
        sqsClientMock.sendMessage("fda-test-df-consumer-sf-detail-paid", SnsMock.wrapBody(message),
            Collections.emptyMap());
        assertTrue(paidUsageConsumer.getLatch().await(10, TimeUnit.SECONDS));
        sqsClientMock.assertQueueMessagesReceived("fda-test-df-consumer-sf-detail-paid");
    }

    private void assertUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
            actualUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedUsage.getPayee().getAccountNumber(), actualUsage.getPayee().getAccountNumber());
        assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
        assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
        assertEquals(expectedUsage.getServiceFeeAmount(), actualUsage.getServiceFeeAmount());
        assertEquals(expectedUsage.isPayeeParticipating(), actualUsage.isPayeeParticipating());
    }

    private PaidUsage getPaidUsageByLmDetailId(String lmDetailId) {
        assertNotNull(lmDetailId);
        Map<UsageStatusEnum, List<String>> usageIdsGroupedByStatus =
            usageService.getForAudit(new AuditFilter(), null, null).stream()
                .collect(Collectors.groupingBy(UsageDto::getStatus,
                    Collectors.mapping(UsageDto::getId, Collectors.toList())));
        List<PaidUsage> usages = usageIdsGroupedByStatus.entrySet().stream()
            .flatMap(entry -> usageArchiveRepository.findByIdAndStatus(entry.getValue(), entry.getKey()).stream())
            .filter(paidUsage -> Objects.equals(lmDetailId, paidUsage.getLmDetailId()))
            .collect(Collectors.toList());
        assertEquals(1, usages.size());
        return usages.get(0);
    }
}
