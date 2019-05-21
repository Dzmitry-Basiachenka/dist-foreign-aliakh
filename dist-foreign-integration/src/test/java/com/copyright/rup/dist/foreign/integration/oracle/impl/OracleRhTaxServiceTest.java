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
 * Verifies {@link OracleRhTaxService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleRhTaxServiceTest {

    private static final String RH_TAX_URL =
        "http://localhost:8080/oracle-ap-rest/getRightsholderDataInfo?rightsholderAccountNumbers={accountNumbers}";
    private static final long ACCOUNT_NUMBER = 7001413934L;

    private OracleRhTaxService oracleRhTaxService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = createMock(RestTemplate.class);
        oracleRhTaxService = new OracleRhTaxService();
        Whitebox.setInternalState(oracleRhTaxService, restTemplate);
        Whitebox.setInternalState(oracleRhTaxService, "rhTaxUrl", RH_TAX_URL);
    }

    @Test
    public void testIsUsTaxCountry() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.getForObject(RH_TAX_URL, String.class, ImmutableBiMap.of("accountNumbers", ACCOUNT_NUMBER)))
            .andReturn(loadJson("rh_tax_information_response.json"))
            .once();
        replay(restTemplate);
        assertTrue(oracleRhTaxService.isUsTaxCountry(ACCOUNT_NUMBER));
        verify(restTemplate);
    }

    @Test
    public void testIsUsTaxCountryNotFoundCode() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.getForObject(RH_TAX_URL, String.class, ImmutableBiMap.of("accountNumbers", ACCOUNT_NUMBER)))
            .andReturn(loadJson("rh_tax_information_not_found_response.json"))
            .once();
        replay(restTemplate);
        assertFalse(oracleRhTaxService.isUsTaxCountry(ACCOUNT_NUMBER));
        verify(restTemplate);
    }

    private String loadJson(String jsonFilePath) {
        return TestUtils.fileToString(this.getClass(), jsonFilePath);
    }
}
