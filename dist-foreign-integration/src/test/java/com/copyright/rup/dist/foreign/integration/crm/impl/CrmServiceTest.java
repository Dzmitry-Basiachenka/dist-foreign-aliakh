package com.copyright.rup.dist.foreign.integration.crm.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.IntegrationConnectionException;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResult;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmResultStatusEnum;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionRequest;
import com.copyright.rup.dist.foreign.integration.crm.api.CrmRightsDistributionResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.apache.commons.collections4.CollectionUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link CrmService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/29/18
 *
 * @author Darya Baraukova
 */
public class CrmServiceTest {

    private static final String GET_RIGHTS_DISTRIBUTION_URL =
        "http://localhost:9032/legacy-integration-rest/getCCCRightsDistributionV2?eventIds={cccEventIds}";
    private static final String INSERT_RIGHTS_DISTRIBUTION_URL =
        "http://localhost:9032/legacy-integration-rest/insertCCCRightsDistribution";
    private static final ImmutableList<String> CCC_EVENT_IDS = ImmutableList.of("12477", "13315");

    private CrmService crmService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        crmService = new CrmService();
        restTemplate = createStrictMock(RestTemplate.class);
        Whitebox.setInternalState(crmService, RestTemplate.class, restTemplate);
        Whitebox.setInternalState(crmService, "getRightsDistributionRequestsUrl", GET_RIGHTS_DISTRIBUTION_URL);
        Whitebox.setInternalState(crmService, "insertRightsDistributionRequestsUrl", INSERT_RIGHTS_DISTRIBUTION_URL);
    }

    @Test
    public void testSendCrmRightsDistributionRequests() throws IOException {
        String expectedBody = formatJson(TestUtils.fileToString(CrmServiceTest.class,
            "crm_rights_distribution_request.json"));
        Capture<String> urlCapture = new Capture<>();
        Capture<HttpEntity> httpEntityCapture = new Capture<>();
        expect(restTemplate.postForObject(capture(urlCapture), capture(httpEntityCapture), eq(String.class)))
            .andReturn(TestUtils.fileToString(CrmServiceTest.class, "crm_response.json")).once();
        replay(restTemplate);
        CrmResult actualResult = crmService.sendRightsDistributionRequests(Collections.singletonList(buildRequest()));
        assertEquals(INSERT_RIGHTS_DISTRIBUTION_URL, urlCapture.getValue());
        assertEquals(CrmResultStatusEnum.SUCCESS, actualResult.getCrmResultStatus());
        assertTrue(CollectionUtils.isEmpty(actualResult.getInvalidUsageIds()));
        HttpEntity httpEntity = httpEntityCapture.getValue();
        assertNotNull(httpEntity);
        assertEquals(expectedBody, formatJson(httpEntity.getBody()));
        verify(restTemplate);
    }

    @Test
    public void testSendRightsDistributionRequestsIntegrationConnectionException() throws IOException {
        String expectedBody =
            formatJson(TestUtils.fileToString(CrmServiceTest.class, "crm_rights_distribution_request.json"));
        Capture<HttpEntity> httpEntityCapture = new Capture<>();
        expect(restTemplate.postForObject(anyObject(String.class), capture(httpEntityCapture), eq(String.class)))
            .andThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY)).once();
        replay(restTemplate);
        try {
            crmService.sendRightsDistributionRequests(Collections.singletonList(buildRequest()));
            fail();
        } catch (IntegrationConnectionException e) {
            assertEquals("Could not connect to the CRM system", e.getMessage());
        }
        HttpEntity httpEntity = httpEntityCapture.getValue();
        assertNotNull(httpEntity);
        assertEquals(expectedBody, formatJson(httpEntity.getBody()));
        verify(restTemplate);
    }

    @Test
    public void testParseResponse() throws IOException {
        CrmResult actualResult =
            crmService.parseResponse(TestUtils.fileToString(CrmServiceTest.class, "crm_response.json"),
                Collections.emptyMap());
        assertEquals(CrmResultStatusEnum.SUCCESS, actualResult.getCrmResultStatus());
        assertTrue(CollectionUtils.isEmpty(actualResult.getInvalidUsageIds()));
    }

    @Test
    public void testParseInvalidResponseWith() throws IOException {
        assertParseResponseWithInvalidUsages("crm_response_failed.json",
            ImmutableSet.of("2e9747c7-f3e8-4c3c-94cf-4f9ba3d10109"));
    }

    @Test
    public void testParseInvalidResponseWithTwoInvalidUsages() throws IOException {
        assertParseResponseWithInvalidUsages("crm_response_failed_two_usages.json",
            ImmutableSet.of("ca9763ab-1ce7-486e-8938-272f6c3392a7", "bbdc5eb3-7396-47b8-bc18-5ec6ad0c4ef1"));
    }

    @Test
    public void testParseResponseWithNoListElement() {
        try {
            crmService.parseResponse("{\"elements\":{}}", Collections.emptyMap());
            fail();
        } catch (IOException e) {
            assertEquals("Send usages to CRM. Failed. Reason=Couldn't parse response. " +
                "Response={\"elements\":{}}, JsonNode={\"elements\":{}}", e.getMessage());
        }
    }

    @Test(expected = IOException.class)
    public void testParseResponseInvalidJson() throws IOException {
        crmService.parseResponse("{abc123", Collections.emptyMap());
    }

    @Test
    public void testGetRightsDistribution() throws IOException {
        Capture<String> urlCapture = new Capture<>();
        Capture<Map<String, List<String>>> urlVariablesCapture = new Capture<>();
        expect(restTemplate.getForObject(capture(urlCapture), eq(String.class), capture(urlVariablesCapture)))
            .andReturn(TestUtils.fileToString(CrmServiceTest.class, "crm_get_rights_distribution_response.json"))
            .once();
        replay(restTemplate);
        List<CrmRightsDistributionResponse> actualResult = crmService.getRightsDistribution(CCC_EVENT_IDS);
        List<CrmRightsDistributionResponse> expectedResult =
            loadFromJson("expected_crm_read_rights_distribution_response.json");
        assertEquals(expectedResult, actualResult);
        assertEquals(GET_RIGHTS_DISTRIBUTION_URL, urlCapture.getValue());
        assertEquals(ImmutableMap.of("cccEventIds", CCC_EVENT_IDS), urlVariablesCapture.getValue());
        verify(restTemplate);
    }

    @Test
    public void testGetRightsDistributionIntegrationConnectionException() throws IOException {
        Capture<String> urlCapture = new Capture<>();
        Capture<Map<String, List<String>>> urlVariablesCapture = new Capture<>();
        expect(restTemplate.getForObject(capture(urlCapture), eq(String.class), capture(urlVariablesCapture)))
            .andThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY)).once();
        replay(restTemplate);
        try {
            crmService.getRightsDistribution(CCC_EVENT_IDS);
            fail();
        } catch (IntegrationConnectionException e) {
            assertEquals("Could not connect to the CRM system", e.getMessage());
        }
        assertEquals(GET_RIGHTS_DISTRIBUTION_URL, urlCapture.getValue());
        assertEquals(ImmutableMap.of("cccEventIds", CCC_EVENT_IDS), urlVariablesCapture.getValue());
        verify(restTemplate);
    }

    @Test
    public void testGetRightsDistributionInvalidJson() {
        Capture<String> urlCapture = new Capture<>();
        Capture<Map<String, List<String>>> urlVariablesCapture = new Capture<>();
        expect(restTemplate.getForObject(capture(urlCapture), eq(String.class), capture(urlVariablesCapture)))
            .andReturn("{abc123").once();
        replay(restTemplate);
        try {
            crmService.getRightsDistribution(CCC_EVENT_IDS);
            fail();
        } catch (RupRuntimeException e) {
            assertEquals("Problem with processing (parsing, generating) JSON content", e.getMessage());
            Throwable cause = e.getCause();
            assertTrue(cause instanceof IOException);
        }
        assertEquals(GET_RIGHTS_DISTRIBUTION_URL, urlCapture.getValue());
        assertEquals(ImmutableMap.of("cccEventIds", CCC_EVENT_IDS), urlVariablesCapture.getValue());
        verify(restTemplate);
    }

    private void assertParseResponseWithInvalidUsages(String fileName, Set<String> expectedUsagesIds)
        throws IOException {
        CrmResult actualResult =
            crmService.parseResponse(TestUtils.fileToString(CrmServiceTest.class, fileName), Collections.emptyMap());
        assertEquals(CrmResultStatusEnum.CRM_ERROR, actualResult.getCrmResultStatus());
        Set<String> actualInvalidUsageIds = actualResult.getInvalidUsageIds();
        assertTrue(CollectionUtils.isNotEmpty(actualInvalidUsageIds));
        assertEquals(CollectionUtils.size(expectedUsagesIds), CollectionUtils.size(actualInvalidUsageIds));
        assertEquals(expectedUsagesIds, actualInvalidUsageIds);
    }

    private String formatJson(Object objectToFormat) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(objectToFormat.toString(), Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

    private CrmRightsDistributionRequest buildRequest() {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(1000012755L);
        Rightsholder payee = new Rightsholder();
        payee.setAccountNumber(1000010022L);
        PaidUsage paidUsage = new PaidUsage();
        paidUsage.setId("1e7d5a65-4cf8-49b6-a804-e15cdbe38d91");
        paidUsage.setProductFamily("FAS");
        paidUsage.setRroAccountNumber(7001047151L);
        paidUsage.setCccEventId("53256");
        paidUsage.setRightsholder(rightsholder);
        paidUsage.setPayee(payee);
        paidUsage.setNetAmount(new BigDecimal("1019.99"));
        paidUsage.setCreateDate(
            Date.from(LocalDate.of(2017, 1, 16).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        paidUsage.setServiceFeeAmount(new BigDecimal("479.99"));
        paidUsage.setWrWrkInst(127778306L);
        paidUsage.setSystemTitle("Adaptations");
        paidUsage.setAuthor("Cartmell, Deborah.");
        paidUsage.setArticle("Routledge");
        paidUsage.setGrossAmount(new BigDecimal("1499.99"));
        paidUsage.setPeriodEndDate(OffsetDateTime.parse("2017-06-16T00:00:00-04:00"));
        paidUsage.setPublicationDate(LocalDate.of(2006, 12, 12));
        paidUsage.setNumberOfCopies(10006);
        paidUsage.setMarket("Univ,Bus,Doc,S");
        paidUsage.setMarketPeriodFrom(2016);
        paidUsage.setMarketPeriodTo(2016);
        return new CrmRightsDistributionRequest(paidUsage);
    }

    private List<CrmRightsDistributionResponse> loadFromJson(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<CrmRightsDistributionResponse>>() {
        });
    }
}
