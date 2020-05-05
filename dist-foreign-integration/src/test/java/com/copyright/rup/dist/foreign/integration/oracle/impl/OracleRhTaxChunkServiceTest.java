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
 * Verifies {@link OracleRhTaxChunkService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/05/2020
 *
 * @author Uladzislau Shalamitski
 */
public class OracleRhTaxChunkServiceTest {

    private static final String RH_TAX_URL =
        "http://localhost:8080/oracle-ap-rest/getRightsholderDataInfo?rightsholderAccountNumbers={accountNumbers}";
    private static final Set<Long> ACCOUNT_NUMBERS = Sets.newHashSet(7001413934L, 1000009522L);

    private OracleRhTaxChunkService oracleRhTaxService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = createMock(RestTemplate.class);
        oracleRhTaxService = new OracleRhTaxChunkService();
        Whitebox.setInternalState(oracleRhTaxService, restTemplate);
        Whitebox.setInternalState(oracleRhTaxService, "rhTaxUrl", RH_TAX_URL);
    }

    @Test
    public void testIsUsTaxCountry() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.getForObject(RH_TAX_URL, String.class, ImmutableBiMap.of("accountNumbers",
            Joiner.on(",").skipNulls().join(ACCOUNT_NUMBERS))))
            .andReturn(loadJson("rh_tax_information_response_1.json"))
            .once();
        replay(restTemplate);
        assertEquals(ImmutableMap.of(7001413934L, Boolean.TRUE, 1000009522L, Boolean.FALSE),
            oracleRhTaxService.isUsTaxCountry(ACCOUNT_NUMBERS));
        verify(restTemplate);
    }

    @Test
    public void testIsUsTaxCountryNotFoundCode() {
        restTemplate.setErrorHandler(anyObject(DefaultResponseErrorHandler.class));
        expectLastCall().once();
        expect(restTemplate.getForObject(RH_TAX_URL, String.class, ImmutableBiMap.of("accountNumbers",
            Joiner.on(",").skipNulls().join(ACCOUNT_NUMBERS))))
            .andReturn(loadJson("rh_tax_information_not_found_response.json"))
            .once();
        replay(restTemplate);
        assertTrue(MapUtils.isEmpty(oracleRhTaxService.isUsTaxCountry(ACCOUNT_NUMBERS)));
        verify(restTemplate);
    }

    private String loadJson(String jsonFilePath) {
        return TestUtils.fileToString(this.getClass(), jsonFilePath);
    }
}
