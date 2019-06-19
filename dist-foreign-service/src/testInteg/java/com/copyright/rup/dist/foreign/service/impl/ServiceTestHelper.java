package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;

import com.google.common.collect.Lists;

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
import java.util.Map;

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
    private AsyncRestTemplate asyncRestTemplate;
    @Value("$RUP{dist.foreign.rest.prm.rightsholder.async}")
    private boolean prmRightsholderAsync;
    @Value("$RUP{dist.foreign.rest.prm.rollups.async}")
    private boolean prmRollUpAsync;

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
                + "&prefCodes=IS-RH-FDA-PARTICIPATING,ISRHDISTINELIGIBLE"))
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
                    + "&prefCodes=IS-RH-FDA-PARTICIPATING,ISRHDISTINELIGIBLE"))
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
}
