package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.MapUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

/**
 * Verifies {@link OracleRhTaxCountryService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/05/2020
 *
 * @author Uladzislau Shalamitski
 */
public class OracleRhTaxCountryServiceTest {

    private static final String RH_TAX_COUNTRY_URL =
        "http://localhost:8080/oracle-ap-rest/getRightsholderDataInfo?rightsholderAccountNumbers={accountNumbers}";
    private static final Set<Long> ACCOUNT_NUMBERS = Sets.newHashSet(7001413934L, 1000009522L);

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
    public void testGetAccountNumbersToUsTaxCountryFlags() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.getForObject(RH_TAX_COUNTRY_URL, String.class,
            ImmutableBiMap.of("accountNumbers", Joiner.on(",").skipNulls().join(ACCOUNT_NUMBERS))))
            .andReturn(loadJson("rh_tax_country_response_1.json"))
            .once();
        replay(restTemplate);
        assertEquals(ImmutableMap.of(7001413934L, Boolean.TRUE, 1000009522L, Boolean.FALSE),
            oracleRhTaxCountryService.getAccountNumbersToUsTaxCountryFlags(ACCOUNT_NUMBERS));
        verify(restTemplate);
    }

    @Test
    public void testGetAccountNumbersToUsTaxCountryFlagsNotFoundCode() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.getForObject(RH_TAX_COUNTRY_URL, String.class, ImmutableBiMap.of("accountNumbers",
            Joiner.on(",").skipNulls().join(ACCOUNT_NUMBERS))))
            .andReturn(loadJson("rh_tax_country_not_found_response.json"))
            .once();
        replay(restTemplate);
        assertTrue(MapUtils.isEmpty(oracleRhTaxCountryService.getAccountNumbersToUsTaxCountryFlags(ACCOUNT_NUMBERS)));
        verify(restTemplate);
    }

    private String loadJson(String jsonFilePath) {
        return TestUtils.fileToString(this.getClass(), jsonFilePath);
    }
}
