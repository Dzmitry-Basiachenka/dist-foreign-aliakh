package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;

import org.apache.commons.collections4.MapUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Verifies {@link OracleService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleServiceTest {

    private static final String BASE_URL = "http://localhost:8080/oracle-ap-rest/";
    private static final String RH_TAX_INFORMATION_URL = BASE_URL + "getRhTaxInformation?fmt=json";
    private static final long ACCOUNT_NUMBER = 2000017000L;

    private OracleService oracleService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = createMock(RestTemplate.class);
        oracleService = new OracleService();
        Whitebox.setInternalState(oracleService, restTemplate);
        Whitebox.setInternalState(oracleService, "baseUrl", BASE_URL);
        oracleService.init();
    }

    @Test
    public void testGetAccountNumbersToCountryCodesMap() {
        Capture<String> urlCapture = new Capture<>();
        Capture<HttpEntity<String>> requestCapture = new Capture<>();
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.postForObject(capture(urlCapture), capture(requestCapture), eq(String.class)))
            .andReturn(loadJson("rh_tax_information_response.json"))
            .once();
        replay(restTemplate);
        Map<Long, String> result = oracleService.getAccountNumbersToCountryCodesMap(ImmutableList.of(ACCOUNT_NUMBER));
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("GB", result.get(ACCOUNT_NUMBER));
        assertTrue(urlCapture.getValue().contains(RH_TAX_INFORMATION_URL));
        verifyContentType(requestCapture.getValue().getHeaders().getContentType());
        verify(restTemplate);
    }

    @Test
    public void testGetAccountNumbersToCountryCodesMapWithNotFoundCode() {
        Capture<String> urlCapture = new Capture<>();
        Capture<HttpEntity<String>> requestCapture = new Capture<>();
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.postForObject(capture(urlCapture), capture(requestCapture), eq(String.class)))
            .andReturn(loadJson("rh_tax_information_not_found_response.json"))
            .once();
        replay(restTemplate);
        Map<Long, String> result = oracleService.getAccountNumbersToCountryCodesMap(ImmutableList.of(ACCOUNT_NUMBER));
        assertNotNull(result);
        assertTrue(MapUtils.isEmpty(result));
        assertTrue(urlCapture.getValue().contains(RH_TAX_INFORMATION_URL));
        verifyContentType(requestCapture.getValue().getHeaders().getContentType());
        verify(restTemplate);
    }

    private String loadJson(String jsonFilePath) {
        return TestUtils.fileToString(this.getClass(), jsonFilePath);
    }

    private void verifyContentType(MediaType contentType) {
        assertEquals(Charsets.UTF_8, contentType.getCharSet());
        assertEquals(MediaType.APPLICATION_JSON.getType(), contentType.getType());
        assertEquals(MediaType.APPLICATION_JSON.getSubtype(), contentType.getSubtype());
    }
}
