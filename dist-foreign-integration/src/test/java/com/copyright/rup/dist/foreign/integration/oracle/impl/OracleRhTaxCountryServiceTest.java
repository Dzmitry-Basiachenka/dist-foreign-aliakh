package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;

import com.google.common.collect.ImmutableBiMap;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * Verifies {@link OracleRhTaxCountryService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleRhTaxCountryServiceTest {

    private static final String RH_TAX_COUNTRY_URL =
        "http://localhost:8080/oracle-ap-rest/getRightsholderDataInfo?rightsholderAccountNumbers={accountNumbers}";
    private static final long ACCOUNT_NUMBER = 7001413934L;

    private OracleRhTaxCountryService oracleRhTaxCountryService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = createMock(RestTemplate.class);
        oracleRhTaxCountryService = new OracleRhTaxCountryService();
        Whitebox.setInternalState(oracleRhTaxCountryService, restTemplate);
        Whitebox.setInternalState(oracleRhTaxCountryService, "rhTaxCountryUrl", RH_TAX_COUNTRY_URL);
    }

    @Test
    public void testIsUsTaxCountry() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(
            restTemplate.getForObject(
                RH_TAX_COUNTRY_URL, String.class, ImmutableBiMap.of("accountNumbers", ACCOUNT_NUMBER)))
            .andReturn(loadJson("rh_tax_country_response.json"))
            .once();
        replay(restTemplate);
        assertTrue(oracleRhTaxCountryService.isUsTaxCountry(ACCOUNT_NUMBER));
        verify(restTemplate);
    }

    @Test
    public void testIsUsTaxCountryNotFoundCode() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(
            restTemplate.getForObject(
                RH_TAX_COUNTRY_URL, String.class, ImmutableBiMap.of("accountNumbers", ACCOUNT_NUMBER)))
            .andReturn(loadJson("rh_tax_country_not_found_response.json"))
            .once();
        replay(restTemplate);
        assertFalse(oracleRhTaxCountryService.isUsTaxCountry(ACCOUNT_NUMBER));
        verify(restTemplate);
    }

    private String loadJson(String jsonFilePath) {
        return TestUtils.fileToString(this.getClass(), jsonFilePath);
    }
}
