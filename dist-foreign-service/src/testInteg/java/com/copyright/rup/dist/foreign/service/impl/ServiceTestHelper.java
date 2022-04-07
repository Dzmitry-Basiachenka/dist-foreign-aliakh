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
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
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
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmValueAuditService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;
import com.copyright.rup.dist.foreign.service.impl.mock.PaidUsageConsumerMock;
import com.copyright.rup.dist.foreign.service.impl.mock.SnsMock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

import java.io.IOException;
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
    private IUdmUsageAuditService udmUsageAuditService;
    @Autowired
    private IUdmValueAuditService udmValueAuditService;
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

    public void expectPrmIneligibleParentCall(String expectedPrmResponse) {
        mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/allpref?preferenceCode=INELIGIBLEFORSHARES"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(),
                expectedPrmResponse), MediaType.APPLICATION_JSON));
    }

    public void expectPrmIneligibleCall(String rightsholderId, String licenceProduct, String expectedPrmResponse) {
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/orgRelationship/drilldownv2?orgIds=" +
                rightsholderId + "&relationshipCode=PARENT&productId=" + licenceProduct))
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

    // TODO: investigate the order of audit items committed in one transaction
    public void assertAuditIgnoringOrder(String entityId, List<UsageAuditItem> expectedAuditItems) {
        List<UsageAuditItem> actualAuditItems = usageAuditService.getUsageAudit(entityId);
        assertEquals(CollectionUtils.size(expectedAuditItems), CollectionUtils.size(actualAuditItems));
        expectedAuditItems.forEach(expectedItem -> {
            assertTrue(actualAuditItems.stream().anyMatch(actualItem ->
                expectedItem.getActionReason().equals(actualItem.getActionReason()) &&
                    expectedItem.getActionType() == actualItem.getActionType()));
        });
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

    public void assertUdmUsageAudit(String entityId, List<UsageAuditItem> expectedAuditItems) {
        List<UsageAuditItem> actualAuditItems = udmUsageAuditService.getUdmUsageAudit(entityId);
        assertEquals(CollectionUtils.size(expectedAuditItems), CollectionUtils.size(actualAuditItems));
        IntStream.range(0, expectedAuditItems.size())
            .forEach(index -> {
                UsageAuditItem expectedItem = expectedAuditItems.get(index);
                UsageAuditItem actualItem = actualAuditItems.get(index);
                assertEquals(expectedItem.getActionReason(), actualItem.getActionReason());
                assertEquals(expectedItem.getActionType(), actualItem.getActionType());
            });
    }

    public void assertUdmValueAudit(String entityId, List<UdmValueAuditItem> expectedAuditItems) {
        List<UdmValueAuditItem> actualAuditItems = udmValueAuditService.getUdmValueAudit(entityId);
        assertEquals(CollectionUtils.size(expectedAuditItems), CollectionUtils.size(actualAuditItems));
        IntStream.range(0, expectedAuditItems.size())
            .forEach(index -> {
                UdmValueAuditItem expectedItem = expectedAuditItems.get(index);
                UdmValueAuditItem actualItem = actualAuditItems.get(index);
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

    public void assertUdmUsages(List<UdmUsage> expectedUsages, List<UdmUsage> actualUsages) {
        assertEquals(expectedUsages.size(), actualUsages.size());
        IntStream.range(0, expectedUsages.size()).forEach(index -> {
            UdmUsage expectedUsage = expectedUsages.get(index);
            UdmUsage actualUsage = actualUsages.get(index);
            assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
            assertEquals(expectedUsage.getOriginalDetailId(), actualUsage.getOriginalDetailId());
            assertEquals(expectedUsage.getPeriod(), actualUsage.getPeriod());
            assertEquals(expectedUsage.getPeriodEndDate(), actualUsage.getPeriodEndDate());
            assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
            assertEquals(expectedUsage.getReportedTitle(), actualUsage.getReportedTitle());
            assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
            assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getReportedPubType(), actualUsage.getReportedPubType());
            assertEquals(expectedUsage.getPubFormat(), actualUsage.getPubFormat());
            assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
            assertEquals(expectedUsage.getLanguage(), actualUsage.getLanguage());
            assertEquals(expectedUsage.getCompanyId(), actualUsage.getCompanyId());
            assertEquals(expectedUsage.getCompanyName(), actualUsage.getCompanyName());
            assertEquals(expectedUsage.getDetailLicenseeClassId(), actualUsage.getDetailLicenseeClassId());
            assertEquals(expectedUsage.getSurveyRespondent(), actualUsage.getSurveyRespondent());
            assertEquals(expectedUsage.getIpAddress(), actualUsage.getIpAddress());
            assertEquals(expectedUsage.getSurveyCountry(), actualUsage.getSurveyCountry());
            assertEquals(expectedUsage.getUsageDate(), actualUsage.getUsageDate());
            assertEquals(expectedUsage.getSurveyStartDate(), actualUsage.getSurveyStartDate());
            assertEquals(expectedUsage.getSurveyEndDate(), actualUsage.getSurveyEndDate());
            assertEquals(expectedUsage.getReportedTypeOfUse(), actualUsage.getReportedTypeOfUse());
            assertEquals(expectedUsage.getQuantity(), actualUsage.getQuantity());
            assertEquals(expectedUsage.getIneligibleReasonId(), actualUsage.getIneligibleReasonId());
            assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
            assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
            assertEquals(expectedUsage.getCompanyName(), actualUsage.getCompanyName());
            assertEquals(expectedUsage.getAnnualMultiplier(), actualUsage.getAnnualMultiplier());
            assertEquals(expectedUsage.getStatisticalMultiplier(), actualUsage.getStatisticalMultiplier());
            assertEquals(expectedUsage.getAnnualizedCopies(), actualUsage.getAnnualizedCopies());
            assertEquals(expectedUsage.getRightsholder(), actualUsage.getRightsholder());
            assertEquals(expectedUsage.getAssignee(), actualUsage.getAssignee());
            assertEquals(expectedUsage.isBaselineFlag(), actualUsage.isBaselineFlag());
            assertEquals(StringUtils.isNotBlank(expectedUsage.getValueId()),
                StringUtils.isNotBlank(actualUsage.getValueId()));
        });
    }

    public void assertUdmValueDtos(List<UdmValueDto> expectedValues, List<UdmValueDto> actualValues) {
        assertEquals(expectedValues.size(), expectedValues.size());
        IntStream.range(0, expectedValues.size()).forEach(index -> {
            UdmValueDto expectedValue = expectedValues.get(index);
            UdmValueDto actualValue = actualValues.get(index);
            assertEquals(expectedValue.getValuePeriod(), actualValue.getValuePeriod());
            assertEquals(expectedValue.getStatus(), actualValue.getStatus());
            assertEquals(expectedValue.getAssignee(), actualValue.getAssignee());
            assertEquals(expectedValue.getRhAccountNumber(), actualValue.getRhAccountNumber());
            assertEquals(expectedValue.getPublicationType().getName(), actualValue.getPublicationType().getName());
            assertEquals(expectedValue.getRhName(), actualValue.getRhName());
            assertEquals(expectedValue.getWrWrkInst(), actualValue.getWrWrkInst());
            assertEquals(expectedValue.getSystemTitle(), actualValue.getSystemTitle());
            assertEquals(expectedValue.getSystemStandardNumber(), actualValue.getSystemStandardNumber());
            assertEquals(expectedValue.getPrice(), actualValue.getPrice());
            assertEquals(expectedValue.getPriceSource(), actualValue.getPriceSource());
            assertEquals(expectedValue.getPriceInUsd(), actualValue.getPriceInUsd());
            assertEquals(expectedValue.getContent(), actualValue.getContent());
            assertEquals(expectedValue.getCurrency(), actualValue.getCurrency());
            assertEquals(expectedValue.getCurrencyExchangeRate(), actualValue.getCurrencyExchangeRate());
            assertEquals(expectedValue.getContentUnitPrice(), actualValue.getContentUnitPrice());
            assertEquals(expectedValue.isContentFlag(), actualValue.isContentFlag());
            assertEquals(expectedValue.getContentComment(), actualValue.getContentComment());
            assertEquals(expectedValue.getContentSource(), actualValue.getContentSource());
            assertEquals(expectedValue.getComment(), actualValue.getComment());
            assertEquals(expectedValue.getUpdateUser(), actualValue.getUpdateUser());
            assertEquals(expectedValue.getCreateUser(), actualValue.getCreateUser());
        });
    }

    public void assertValueBaselineDtos(List<UdmValueBaselineDto> expectedValues,
                                        List<UdmValueBaselineDto> actualValues) {
        assertEquals(expectedValues.size(), actualValues.size());
        IntStream.range(0, expectedValues.size()).forEach(index -> {
            UdmValueBaselineDto expectedValue = expectedValues.get(index);
            UdmValueBaselineDto actualValue = actualValues.get(index);
            assertEquals(expectedValue.getPeriod(), actualValue.getPeriod());
            assertEquals(expectedValue.getWrWrkInst(), actualValue.getWrWrkInst());
            assertEquals(expectedValue.getSystemTitle(), actualValue.getSystemTitle());
            assertEquals(expectedValue.getPublicationType(), actualValue.getPublicationType());
            assertEquals(expectedValue.getPrice(), actualValue.getPrice());
            assertEquals(expectedValue.getPriceFlag(), actualValue.getPriceFlag());
            assertEquals(expectedValue.getContent(), actualValue.getContent());
            assertEquals(expectedValue.getContentUnitPrice(), actualValue.getContentUnitPrice());
            assertEquals(expectedValue.getContentFlag(), actualValue.getContentFlag());
            assertEquals(expectedValue.getComment(), actualValue.getComment());
            assertEquals(expectedValue.getUpdateUser(), actualValue.getUpdateUser());
            assertEquals(expectedValue.getCreateUser(), actualValue.getCreateUser());
        });
    }

    public List<UdmUsage> loadExpectedUdmUsages(String pathToUsageDtosFile) throws IOException {
        String content = TestUtils.fileToString(ServiceTestHelper.class, pathToUsageDtosFile);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<UdmUsage>>() {
        });
    }

    public List<UdmValueDto> loadExpectedUdmValueDto(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<UdmValueDto>>() {
        });
    }

    public List<UdmValueBaselineDto> loadExpectedValueBaselineDto(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<UdmValueBaselineDto>>() {
        });
    }

    public List<UsageAuditItem> loadExpectedUsageAuditItems(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<UsageAuditItem>>() {
        });
    }

    public List<UdmValueAuditItem> loadExpectedUdmValueAuditItems(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<UdmValueAuditItem>>() {
        });
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
