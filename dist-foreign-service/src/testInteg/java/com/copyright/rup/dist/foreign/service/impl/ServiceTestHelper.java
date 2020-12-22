package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.service.impl.mock.PaidUsageConsumerMock;
import com.copyright.rup.dist.foreign.service.impl.mock.SnsMock;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
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
import java.util.Comparator;
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
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private ISalUsageService salUsageService;
    @Autowired
    private IScenarioAuditService scenarioAuditService;
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

    public void expectOracleCall(String expectedOracleResponse, List<Long> expectedOracleAccountNumbers) {
        mockServer.expect(MockRestRequestMatchers
            .requestTo(
                "http://localhost:8080/oracle-ap-rest/getRightsholderDataInfo?rightsholderAccountNumbers=" +
                    Joiner.on(",").join(expectedOracleAccountNumbers)))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                expectedOracleResponse), MediaType.APPLICATION_JSON));
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
                + "&prefCodes=IS-RH-FDA-PARTICIPATING,ISRHDISTINELIGIBLE,IS-RH-STM-IPRO,TAXBENEFICIALOWNER"))
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

    public void expectGetPreferences(String fileName, List<String> rightsholdersIds) {
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefv2?orgIds="
                + String.join(",", rightsholdersIds)
                + "&prefCodes=IS-RH-FDA-PARTICIPATING,ISRHDISTINELIGIBLE,IS-RH-STM-IPRO,TAXBENEFICIALOWNER"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                MediaType.APPLICATION_JSON));
    }

    public void expectGetRollups(String fileName, List<String> rightsholdersIds) {
        (prmRollUpAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollupv2?orgIds=" +
                String.join(",", rightsholdersIds) + "&relationshipCode=PARENT&prefCodes=payee"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), fileName),
                MediaType.APPLICATION_JSON));
    }

    public void expectCrmCall(String expectedRequestFileName, String responseFileName, List<String> fieldsToIgnore) {
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
        assertEquals(CollectionUtils.size(expectedAuditItems), CollectionUtils.size(actualAuditItems));
        IntStream.range(0, expectedAuditItems.size())
            .forEach(index -> {
                UsageAuditItem expectedItem = expectedAuditItems.get(index);
                UsageAuditItem actualItem = actualAuditItems.get(index);
                assertEquals(expectedItem.getActionReason(), actualItem.getActionReason());
                assertEquals(expectedItem.getActionType(), actualItem.getActionType());
            });
    }

    public void assertScenarioAudit(String scenarioId, List<Pair<ScenarioActionTypeEnum, String>> expectedAudit) {
        assertEquals(expectedAudit, scenarioAuditService.getActions(scenarioId).stream()
            .sorted(Comparator.comparing(ScenarioAuditItem::getCreateDate))
            .map(a -> Pair.of(a.getActionType(), a.getActionReason()))
            .collect(Collectors.toList()));
    }

    public void assertUsages(List<Usage> expectedUsages) {
        expectedUsages.forEach(expectedUsage -> {
            List<Usage> actualUsages = usageRepository.findByIds(Collections.singletonList(expectedUsage.getId()));
            assertUsage(expectedUsage, actualUsages.get(0));
        });
    }

    public void assertPaidAaclUsages(List<PaidUsage> expectedUsages) {
        Map<UsageStatusEnum, List<String>> usageIdsGroupedByStatus =
            aaclUsageService.getForAudit(new AuditFilter(), null, null).stream()
                .collect(Collectors.groupingBy(UsageDto::getStatus,
                    Collectors.mapping(UsageDto::getId, Collectors.toList())));
        assertPaidUsages(expectedUsages, usageIdsGroupedByStatus);
    }

    public void assertPaidSalUsages(List<PaidUsage> expectedUsages) {
        Map<UsageStatusEnum, List<String>> usageIdsGroupedByStatus =
            salUsageService.getForAudit(new AuditFilter(), null, null).stream()
                .collect(Collectors.groupingBy(UsageDto::getStatus,
                    Collectors.mapping(UsageDto::getId, Collectors.toList())));
        assertPaidUsages(expectedUsages, usageIdsGroupedByStatus);
    }

    public void assertPaidUsages(List<PaidUsage> expectedUsages) {
        Map<UsageStatusEnum, List<String>> usageIdsGroupedByStatus =
            usageService.getForAudit(new AuditFilter(), null, null).stream()
                .collect(Collectors.groupingBy(UsageDto::getStatus,
                    Collectors.mapping(UsageDto::getId, Collectors.toList())));
        assertPaidUsages(expectedUsages, usageIdsGroupedByStatus);
    }

    private void assertPaidUsages(List<PaidUsage> expectedUsages,
                                  Map<UsageStatusEnum, List<String>> usageIdsGroupedByStatus) {
        expectedUsages.forEach(expectedUsage -> {
            PaidUsage actualUsage = getPaidUsageByLmDetailId(expectedUsage.getLmDetailId(), usageIdsGroupedByStatus);
            assertUsage(expectedUsage, actualUsage);
            assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
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
            if (Objects.nonNull(actualUsage.getAaclUsage())) {
                verifyAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
            }
        });
    }

    private void verifyAaclUsage(AaclUsage expectedAaclUsage, AaclUsage actualAaclUsage) {
        assertEquals(expectedAaclUsage.getOriginalPublicationType(), actualAaclUsage.getOriginalPublicationType());
        assertEquals(expectedAaclUsage.getPublicationType().getId(), actualAaclUsage.getPublicationType().getId());
        assertEquals(expectedAaclUsage.getPublicationType().getName(), actualAaclUsage.getPublicationType().getName());
        assertEquals(expectedAaclUsage.getPublicationType().getWeight(),
            actualAaclUsage.getPublicationType().getWeight());
        assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
        assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
        assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
        assertEquals(expectedAaclUsage.getUsageAge().getPeriod(), actualAaclUsage.getUsageAge().getPeriod());
        assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
        assertEquals(expectedAaclUsage.getBatchPeriodEndDate(), actualAaclUsage.getBatchPeriodEndDate());
        assertEquals(expectedAaclUsage.getBaselineId(), actualAaclUsage.getBaselineId());
        assertEquals(expectedAaclUsage.getUsageAge().getWeight(), actualAaclUsage.getUsageAge().getWeight());
        assertEquals(expectedAaclUsage.getValueWeight(), actualAaclUsage.getValueWeight());
        assertEquals(expectedAaclUsage.getVolumeWeight(), actualAaclUsage.getVolumeWeight());
        assertEquals(expectedAaclUsage.getVolumeShare(), actualAaclUsage.getVolumeShare());
        assertEquals(expectedAaclUsage.getValueShare(), actualAaclUsage.getValueShare());
        assertEquals(expectedAaclUsage.getTotalShare(), actualAaclUsage.getTotalShare());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getId(),
            actualAaclUsage.getDetailLicenseeClass().getId());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getDiscipline(),
            actualAaclUsage.getDetailLicenseeClass().getDiscipline());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getEnrollmentProfile(),
            actualAaclUsage.getDetailLicenseeClass().getEnrollmentProfile());
        assertEquals(expectedAaclUsage.getAggregateLicenseeClass().getId(),
            actualAaclUsage.getAggregateLicenseeClass().getId());
        assertEquals(expectedAaclUsage.getAggregateLicenseeClass().getDiscipline(),
            actualAaclUsage.getAggregateLicenseeClass().getDiscipline());
        assertEquals(expectedAaclUsage.getAggregateLicenseeClass().getEnrollmentProfile(),
            actualAaclUsage.getAggregateLicenseeClass().getEnrollmentProfile());
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
        assertEquals(expectedUsage.isRhParticipating(), actualUsage.isRhParticipating());
        assertEquals(expectedUsage.isPayeeParticipating(), actualUsage.isPayeeParticipating());
        if (Objects.nonNull(actualUsage.getReportedValue())) {
            assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
        }
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
        assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
        assertEquals(expectedUsage.getServiceFeeAmount(), actualUsage.getServiceFeeAmount());
    }

    private PaidUsage getPaidUsageByLmDetailId(String lmDetailId,
                                               Map<UsageStatusEnum, List<String>> usageIdsGroupedByStatus) {
        assertNotNull(lmDetailId);
        List<PaidUsage> usages = usageIdsGroupedByStatus.entrySet().stream()
            .flatMap(entry -> usageArchiveRepository.findByIdAndStatus(entry.getValue(), entry.getKey()).stream())
            .filter(paidUsage -> Objects.equals(lmDetailId, paidUsage.getLmDetailId()))
            .collect(Collectors.toList());
        assertEquals(1, usages.size());
        return usages.get(0);
    }
}
