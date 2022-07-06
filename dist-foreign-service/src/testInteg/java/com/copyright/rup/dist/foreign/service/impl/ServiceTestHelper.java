package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetail;
import com.copyright.rup.dist.foreign.domain.AclScenarioShareDetail;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;
import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAge;
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
import com.copyright.rup.dist.foreign.service.impl.mock.LdmtDetailsConsumerMock;
import com.copyright.rup.dist.foreign.service.impl.mock.PaidUsageConsumerMock;
import com.copyright.rup.dist.foreign.service.impl.mock.SnsMock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
    @Autowired
    @Qualifier("df.service.ldmtDetailsConsumer")
    private LdmtDetailsConsumerMock ldmtDetailsConsumer;

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

    public void expectRmsRightsAssignmentCall(String requestFileName, String responseFileName) {
        mockServer.expect(MockRestRequestMatchers.requestTo("http://localhost:9051/rms-rights-rest/jobs/wrwrkinst/"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content()
                .string(new JsonMatcher(StringUtils.trim(TestUtils.fileToString(this.getClass(), requestFileName)))))
            .andRespond(MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), responseFileName),
                MediaType.APPLICATION_JSON));
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

    public void receiveLdtmDetailsFromOracle(String fileName) throws InterruptedException {
        ldmtDetailsConsumer.setLatch(new CountDownLatch(1));
        String message = TestUtils.fileToString(this.getClass(), fileName);
        sqsClientMock.sendMessage("fda-test-ldmt-licensedata", message, Collections.emptyMap());
        assertTrue(ldmtDetailsConsumer.getLatch().await(10, TimeUnit.SECONDS));
        sqsClientMock.assertQueueMessagesReceived("fda-test-ldmt-licensedata");
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

    public void assertAclFundPoolDetails(List<AclFundPoolDetail> expectedValues, List<AclFundPoolDetail> actualValues) {
        assertEquals(expectedValues.size(), expectedValues.size());
        IntStream.range(0, expectedValues.size()).forEach(index -> {
            AclFundPoolDetail expectedDetail = expectedValues.get(index);
            AclFundPoolDetail actualDetail = actualValues.get(index);
            assertEquals(expectedDetail.getFundPoolId(), actualDetail.getFundPoolId());
            DetailLicenseeClass expectedDetailLicenseeClass = expectedDetail.getDetailLicenseeClass();
            DetailLicenseeClass actualDetailLicenseeClass = actualDetail.getDetailLicenseeClass();
            assertEquals(expectedDetailLicenseeClass.getId(), actualDetailLicenseeClass.getId());
            assertEquals(expectedDetailLicenseeClass.getDescription(), actualDetailLicenseeClass.getDescription());
            assertEquals(expectedDetail.getLicenseType(), actualDetail.getLicenseType());
            assertEquals(expectedDetail.getTypeOfUse(), actualDetail.getTypeOfUse());
            assertEquals(expectedDetail.getGrossAmount(), actualDetail.getGrossAmount());
            assertEquals(expectedDetail.getNetAmount(), actualDetail.getNetAmount());
            assertEquals(expectedDetail.isLdmtFlag(), actualDetail.isLdmtFlag());
        });
    }

    public List<Usage> loadExpectedUsages(String fileName) throws IOException {
        String content = TestUtils.fileToString(ServiceTestHelper.class, fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<Usage>>() {
        });
    }

    public List<UsageDto> loadExpectedUsageDtos(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
        });
    }

    public List<PaidUsage> loadExpectedPaidUsages(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<PaidUsage>>() {
        });
    }

    public List<UdmUsage> loadExpectedUdmUsages(String fileName) throws IOException {
        String content = TestUtils.fileToString(ServiceTestHelper.class, fileName);
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

    public List<AclFundPoolDetail> loadExpectedAclFundPoolDetails(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<List<AclFundPoolDetail>>() {
        });
    }

    public List<AclScenarioDetail> loadExpectedAclScenarioDetails(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<List<AclScenarioDetail>>() {
        });
    }

    public ByteArrayOutputStream getCsvOutputStream(String fileName) throws IOException {
        String csvText = TestUtils.fileToString(this.getClass(), fileName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.write(csvText, out, StandardCharsets.UTF_8);
        return out;
    }

    private void doReceivePaidUsagesFromLm(String message) throws InterruptedException {
        paidUsageConsumer.setLatch(new CountDownLatch(1));
        sqsClientMock.sendMessage("fda-test-df-consumer-sf-detail-paid", SnsMock.wrapBody(message),
            Collections.emptyMap());
        assertTrue(paidUsageConsumer.getLatch().await(10, TimeUnit.SECONDS));
        sqsClientMock.assertQueueMessagesReceived("fda-test-df-consumer-sf-detail-paid");
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
                assertAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
            }
        });
    }

    public void assertAaclUsage(AaclUsage expectedUsage, AaclUsage actualUsage) {
        assertEquals(expectedUsage.getDetailLicenseeClass().getId(),
            actualUsage.getDetailLicenseeClass().getId());
        assertEquals(expectedUsage.getDetailLicenseeClass().getDiscipline(),
            actualUsage.getDetailLicenseeClass().getDiscipline());
        assertEquals(expectedUsage.getDetailLicenseeClass().getEnrollmentProfile(),
            actualUsage.getDetailLicenseeClass().getEnrollmentProfile());
        assertEquals(expectedUsage.getAggregateLicenseeClass().getId(),
            actualUsage.getAggregateLicenseeClass().getId());
        assertEquals(expectedUsage.getAggregateLicenseeClass().getDiscipline(),
            actualUsage.getAggregateLicenseeClass().getDiscipline());
        assertEquals(expectedUsage.getAggregateLicenseeClass().getEnrollmentProfile(),
            actualUsage.getAggregateLicenseeClass().getEnrollmentProfile());
        assertEquals(expectedUsage.getPublicationType().getId(), actualUsage.getPublicationType().getId());
        assertEquals(expectedUsage.getPublicationType().getName(), actualUsage.getPublicationType().getName());
        assertEquals(expectedUsage.getPublicationType().getWeight(), actualUsage.getPublicationType().getWeight());
        assertEquals(expectedUsage.getUsageAge().getPeriod(), actualUsage.getUsageAge().getPeriod());
        assertEquals(expectedUsage.getUsageAge().getWeight(), actualUsage.getUsageAge().getWeight());
        assertEquals(expectedUsage.getOriginalPublicationType(), actualUsage.getOriginalPublicationType());
        assertEquals(expectedUsage.getInstitution(), actualUsage.getInstitution());
        assertEquals(expectedUsage.getUsageSource(), actualUsage.getUsageSource());
        assertEquals(expectedUsage.getRightLimitation(), actualUsage.getRightLimitation());
        assertEquals(expectedUsage.getNumberOfPages(), actualUsage.getNumberOfPages());
        assertEquals(expectedUsage.getBatchPeriodEndDate(), actualUsage.getBatchPeriodEndDate());
        assertEquals(expectedUsage.getBaselineId(), actualUsage.getBaselineId());
        assertEquals(expectedUsage.getVolumeWeight(), actualUsage.getVolumeWeight());
        assertEquals(expectedUsage.getValueWeight(), actualUsage.getValueWeight());
        assertEquals(expectedUsage.getValueShare(), actualUsage.getValueShare());
        assertEquals(expectedUsage.getVolumeShare(), actualUsage.getVolumeShare());
        assertEquals(expectedUsage.getTotalShare(), actualUsage.getTotalShare());
    }

    public void assertUsage(Usage expectedUsage, Usage actualUsage) {
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

    public void assertUsageDto(UsageDto expectedUsage, UsageDto actualUsage) {
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
    }

    public void assertSalUsage(SalUsage expectedUsage, SalUsage actualUsage) {
        assertEquals(expectedUsage.getAssessmentName(), actualUsage.getAssessmentName());
        assertEquals(expectedUsage.getAssessmentType(), actualUsage.getAssessmentType());
        assertEquals(expectedUsage.getCoverageYear(), actualUsage.getCoverageYear());
        assertEquals(expectedUsage.getGrade(), actualUsage.getGrade());
        assertEquals(expectedUsage.getGradeGroup(), actualUsage.getGradeGroup());
        assertEquals(expectedUsage.getDetailType(), actualUsage.getDetailType());
        assertEquals(expectedUsage.getReportedWorkPortionId(), actualUsage.getReportedWorkPortionId());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getReportedMediaType(), actualUsage.getReportedMediaType());
        assertEquals(expectedUsage.getMediaTypeWeight(), actualUsage.getMediaTypeWeight());
        assertEquals(expectedUsage.getReportedArticle(), actualUsage.getReportedArticle());
        assertEquals(expectedUsage.getReportedAuthor(), actualUsage.getReportedAuthor());
        assertEquals(expectedUsage.getReportedPublisher(), actualUsage.getReportedPublisher());
        assertEquals(expectedUsage.getReportedPublicationDate(), actualUsage.getReportedPublicationDate());
        assertEquals(expectedUsage.getReportedPageRange(), actualUsage.getReportedPageRange());
        assertEquals(expectedUsage.getReportedVolNumberSeries(), actualUsage.getReportedVolNumberSeries());
        assertEquals(expectedUsage.getAssessmentType(), actualUsage.getAssessmentType());
        assertEquals(expectedUsage.getStates(), actualUsage.getStates());
        assertEquals(expectedUsage.getNumberOfViews(), actualUsage.getNumberOfViews());
        assertEquals(expectedUsage.getScoredAssessmentDate(), actualUsage.getScoredAssessmentDate());
        assertEquals(expectedUsage.getQuestionIdentifier(), actualUsage.getQuestionIdentifier());
    }

    public void verifyAclScenario(AclScenario expectedAclScenario, AclScenario actualAclScenario) {
        assertEquals(expectedAclScenario.getFundPoolId(), actualAclScenario.getFundPoolId());
        assertEquals(expectedAclScenario.getUsageBatchId(), actualAclScenario.getUsageBatchId());
        assertEquals(expectedAclScenario.getGrantSetId(), actualAclScenario.getGrantSetId());
        assertEquals(expectedAclScenario.getName(), actualAclScenario.getName());
        assertEquals(expectedAclScenario.getDescription(), actualAclScenario.getDescription());
        assertEquals(expectedAclScenario.getStatus(), actualAclScenario.getStatus());
        assertEquals(expectedAclScenario.isEditableFlag(), actualAclScenario.isEditableFlag());
        assertEquals(expectedAclScenario.getPeriodEndDate(), actualAclScenario.getPeriodEndDate());
        assertEquals(expectedAclScenario.getLicenseType(), actualAclScenario.getLicenseType());
        assertEquals(expectedAclScenario.getCreateUser(), actualAclScenario.getCreateUser());
        assertEquals(expectedAclScenario.getUpdateUser(), actualAclScenario.getUpdateUser());
        assertEquals(expectedAclScenario.getUsageAges().size(), actualAclScenario.getUsageAges().size());
        actualAclScenario.getUsageAges().sort(Comparator.comparingInt(UsageAge::getPeriod));
        IntStream.range(0, expectedAclScenario.getUsageAges().size())
            .forEach(i -> verifyAclScenarioUsageAge(expectedAclScenario.getUsageAges().get(i),
                actualAclScenario.getUsageAges().get(i)));
        assertEquals(expectedAclScenario.getDetailLicenseeClasses().size(),
            actualAclScenario.getDetailLicenseeClasses().size());
        actualAclScenario.getDetailLicenseeClasses().sort(Comparator.comparingInt(BaseEntity::getId));
        IntStream.range(0, actualAclScenario.getDetailLicenseeClasses().size())
            .forEach(i -> verifyAclScenarioLicenseeClasses(expectedAclScenario.getDetailLicenseeClasses().get(i),
                actualAclScenario.getDetailLicenseeClasses().get(i)));
        Set<PublicationType> publicationTypeSet = new HashSet<>(actualAclScenario.getPublicationTypes());
        assertEquals(
            expectedAclScenario.getPublicationTypes().size(), publicationTypeSet.size());
        IntStream.range(0, actualAclScenario.getPublicationTypes().size())
            .forEach(i -> verifyAclScenarioPublicationType(expectedAclScenario.getPublicationTypes().get(i),
                actualAclScenario.getPublicationTypes().get(i)));
    }

    public void verifyAclScenarioDetails(String pathToExpectedScenarioDetail,
                                         List<AclScenarioDetail> actualScenarioDetails) throws IOException {
        assertNotNull(pathToExpectedScenarioDetail);
        List<AclScenarioDetail> expectedScenarioDetails = loadExpectedAclScenarioDetails(pathToExpectedScenarioDetail);
        assertEquals(expectedScenarioDetails.size(), actualScenarioDetails.size());
        IntStream.range(0, expectedScenarioDetails.size())
            .forEach(i -> verifyAclScenarioDetail(expectedScenarioDetails.get(i), actualScenarioDetails.get(i)));
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

    private void verifyAclScenarioDetail(AclScenarioDetail expectedScenarioDetail,
                                         AclScenarioDetail actualScenarioDetail) {
        assertEquals(expectedScenarioDetail.getPeriod(), actualScenarioDetail.getPeriod());
        assertEquals(expectedScenarioDetail.getOriginalDetailId(), actualScenarioDetail.getOriginalDetailId());
        assertEquals(expectedScenarioDetail.getWrWrkInst(), actualScenarioDetail.getWrWrkInst());
        assertEquals(expectedScenarioDetail.getSystemTitle(), actualScenarioDetail.getSystemTitle());
        assertEquals(expectedScenarioDetail.getDetailLicenseeClass().getId(),
            actualScenarioDetail.getDetailLicenseeClass().getId());
        assertEquals(expectedScenarioDetail.getAggregateLicenseeClassId(),
            actualScenarioDetail.getAggregateLicenseeClassId());
        assertEquals(expectedScenarioDetail.getAggregateLicenseeClassName(),
            actualScenarioDetail.getAggregateLicenseeClassName());
        assertEquals(expectedScenarioDetail.getPublicationType().getId(),
            actualScenarioDetail.getPublicationType().getId());
        assertEquals(expectedScenarioDetail.getPublicationType().getWeight(),
            actualScenarioDetail.getPublicationType().getWeight());
        assertEquals(expectedScenarioDetail.getContentUnitPrice(), actualScenarioDetail.getContentUnitPrice());
        assertEquals(expectedScenarioDetail.getQuantity(), actualScenarioDetail.getQuantity());
        assertEquals(expectedScenarioDetail.getUsageAgeWeight(), actualScenarioDetail.getUsageAgeWeight());
        assertEquals(expectedScenarioDetail.getWeightedCopies(), actualScenarioDetail.getWeightedCopies());
        assertEquals(expectedScenarioDetail.getSurveyCountry(), actualScenarioDetail.getSurveyCountry());
        List<AclScenarioShareDetail> expectedScenarioShareDetail = expectedScenarioDetail.getScenarioShareDetails();
        List<AclScenarioShareDetail> scenarioShareDetails = actualScenarioDetail.getScenarioShareDetails();
        assertEquals(expectedScenarioShareDetail.size(), scenarioShareDetails.size());
        IntStream.range(0, scenarioShareDetails.size()).forEach(i ->
            verifyAclScenarioShareDetails(expectedScenarioShareDetail.get(i), scenarioShareDetails.get(i)));
    }

    private void verifyAclScenarioLicenseeClasses(DetailLicenseeClass expectedDetailLicenseeClass,
                                                  DetailLicenseeClass actualDetailLicenseeClass) {
        assertEquals(expectedDetailLicenseeClass.getId(), actualDetailLicenseeClass.getId());
        assertEquals(expectedDetailLicenseeClass.getAggregateLicenseeClass().getId(),
            actualDetailLicenseeClass.getAggregateLicenseeClass().getId());
    }

    private void verifyAclScenarioPublicationType(PublicationType expectedPublicationType,
                                                  PublicationType actualPublicationType) {
        assertEquals(expectedPublicationType.getId(), actualPublicationType.getId());
        assertEquals(expectedPublicationType.getWeight(), actualPublicationType.getWeight());
        assertEquals(expectedPublicationType.getCreateUser(), actualPublicationType.getCreateUser());
        assertEquals(expectedPublicationType.getUpdateUser(), actualPublicationType.getUpdateUser());
    }

    private void verifyAclScenarioUsageAge(UsageAge expectedUsageAge, UsageAge actualUsageAge) {
        assertEquals(expectedUsageAge.getPeriod(), actualUsageAge.getPeriod());
        assertEquals(expectedUsageAge.getWeight(), actualUsageAge.getWeight());
    }

    private void verifyAclScenarioShareDetails(AclScenarioShareDetail expectedDetail,
                                               AclScenarioShareDetail actualDetail) {
        assertEquals(expectedDetail.getRhAccountNumber(), actualDetail.getRhAccountNumber());
        assertEquals(expectedDetail.getTypeOfUse(), actualDetail.getTypeOfUse());
        assertEquals(expectedDetail.getVolumeWeight(), actualDetail.getVolumeWeight());
        assertEquals(expectedDetail.getValueWeight(), actualDetail.getValueWeight());
    }
}
