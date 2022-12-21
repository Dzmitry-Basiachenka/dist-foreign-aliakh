package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.RhTaxInformation;
import com.copyright.rup.dist.foreign.integration.oracle.api.domain.OracleRhTaxInformationRequest;

import com.google.common.base.Charsets;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Map;

/**
 * Verifies {@link OracleRhTaxInformationService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/18/20
 *
 * @author Stanislau Rudak
 */
public class OracleRhTaxInformationServiceTest {

    private static final String RH_TAX_INFO_URL = "http://localhost:8080/oracle-ap-rest/getRhTaxInformation";
    private static final Long EXPECTED_ACCOUNT_NUMBER = 7001413934L;

    private OracleRhTaxInformationService oracleRhTaxInformationService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = createStrictMock(RestTemplate.class);
        oracleRhTaxInformationService = new OracleRhTaxInformationService();
        Whitebox.setInternalState(oracleRhTaxInformationService, restTemplate);
        Whitebox.setInternalState(oracleRhTaxInformationService, "rhTaxInfoUrl", RH_TAX_INFO_URL);
    }

    @Test
    public void testGetRhTaxInformation() {
        Capture<String> urlCapture = newCapture();
        Capture<HttpEntity<String>> requestCapture = newCapture();
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.postForObject(capture(urlCapture), capture(requestCapture), eq(String.class)))
            .andReturn(
                TestUtils.fileToString(OracleRhTaxInformationServiceTest.class, "rh_tax_information_response.json"))
            .once();
        replay(restTemplate);
        Map<Long, RhTaxInformation> rhTaxInformationMap =
            oracleRhTaxInformationService.getRhTaxInformation(Collections.singleton(
                new OracleRhTaxInformationRequest(EXPECTED_ACCOUNT_NUMBER, EXPECTED_ACCOUNT_NUMBER)));
        assertNotNull(rhTaxInformationMap);
        assertEquals(1, rhTaxInformationMap.size());
        RhTaxInformation rhTaxInformation = rhTaxInformationMap.get(EXPECTED_ACCOUNT_NUMBER);
        verifyRhTaxInformation(rhTaxInformation);
        assertTrue(urlCapture.getValue().contains(RH_TAX_INFO_URL));
        HttpEntity<String> requestEntity = requestCapture.getValue();
        MediaType contentType = requestEntity.getHeaders().getContentType();
        assertNotNull(contentType);
        verifyContentType(contentType);
        verify(restTemplate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRhTaxInformationEmptyParameter() {
        Map<Long, RhTaxInformation> information = oracleRhTaxInformationService.getRhTaxInformation(null);
        assertNotNull(information);
        assertTrue(information.isEmpty());
    }

    @Test
    public void testGetRhTaxInformationResponseNotArray() {
        Capture<String> urlCapture = newCapture();
        Capture<HttpEntity<String>> requestCapture = newCapture();
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.postForObject(capture(urlCapture), capture(requestCapture), eq(String.class)))
            .andReturn(TestUtils.fileToString(OracleRhTaxInformationServiceTest.class, "response_not_array.json"))
            .once();
        replay(restTemplate);
        Map<Long, RhTaxInformation> rhTaxInformationMap =
            oracleRhTaxInformationService.getRhTaxInformation(Collections.singleton(
                new OracleRhTaxInformationRequest(EXPECTED_ACCOUNT_NUMBER, EXPECTED_ACCOUNT_NUMBER)));
        assertNotNull(rhTaxInformationMap);
        assertTrue(rhTaxInformationMap.isEmpty());
        assertTrue(urlCapture.getValue().contains(RH_TAX_INFO_URL));
        HttpEntity<String> requestEntity = requestCapture.getValue();
        MediaType contentType = requestEntity.getHeaders().getContentType();
        assertNotNull(contentType);
        verifyContentType(contentType);
        verify(restTemplate);
    }

    @Test
    public void testGetRhTaxInformationNullPaidDate() {
        Capture<String> urlCapture = newCapture();
        Capture<HttpEntity<String>> requestCapture = newCapture();
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.postForObject(capture(urlCapture), capture(requestCapture), eq(String.class)))
            .andReturn(TestUtils.fileToString(OracleRhTaxInformationServiceTest.class, "response_null_node.json"))
            .once();
        replay(restTemplate);
        Map<Long, RhTaxInformation> rhTaxInformationMap =
            oracleRhTaxInformationService.getRhTaxInformation(Collections.singleton(
                new OracleRhTaxInformationRequest(EXPECTED_ACCOUNT_NUMBER, EXPECTED_ACCOUNT_NUMBER)));
        assertNotNull(rhTaxInformationMap);
        assertTrue(urlCapture.getValue().contains(RH_TAX_INFO_URL));
        HttpEntity<String> requestEntity = requestCapture.getValue();
        MediaType contentType = requestEntity.getHeaders().getContentType();
        assertNotNull(contentType);
        verifyContentType(contentType);
        verify(restTemplate);
    }

    private void verifyRhTaxInformation(RhTaxInformation rhTaxInformation) {
        assertNotNull(rhTaxInformation);
        assertEquals(EXPECTED_ACCOUNT_NUMBER, rhTaxInformation.getTboAccountNumber());
        assertEquals("1", rhTaxInformation.getTaxOnFile());
        assertEquals(2, rhTaxInformation.getNotificationsSent().intValue());
        assertEquals("W8BEN", rhTaxInformation.getTypeOfForm());
        assertEquals("810 E. 10th Street", rhTaxInformation.getAddressLine1());
        assertEquals("Address line 2", rhTaxInformation.getAddressLine2());
        assertEquals("Address line 3", rhTaxInformation.getAddressLine3());
        assertEquals("Address line 4", rhTaxInformation.getAddressLine4());
        assertEquals("Lawrence", rhTaxInformation.getCity());
        assertEquals("KS", rhTaxInformation.getState());
        assertEquals("66044-8897", rhTaxInformation.getZip());
        assertEquals("US", rhTaxInformation.getCountryCode());
        assertEquals("RIGHTS", rhTaxInformation.getPayGroup());
        assertEquals("U", rhTaxInformation.getWithHoldingIndicator());
        assertEquals(LocalDateTime.parse("2000-04-10T00:00:00").atZone(ZoneOffset.systemDefault()).toOffsetDateTime(),
            rhTaxInformation.getDateOfLastNotificationSent());
    }

    private void verifyContentType(MediaType contentType) {
        assertEquals(Charsets.UTF_8, contentType.getCharset());
        assertEquals(MediaType.APPLICATION_JSON.getType(), contentType.getType());
        assertEquals(MediaType.APPLICATION_JSON.getSubtype(), contentType.getSubtype());
    }
}
