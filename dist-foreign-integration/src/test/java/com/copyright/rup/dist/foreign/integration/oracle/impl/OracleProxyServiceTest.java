package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static junit.framework.TestCase.assertTrue;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.collections4.MapUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * Verifies {@link OracleProxyService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * </p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleProxyServiceTest {

    private static final long ACCOUNT_NUMBER = 1000015778L;
    private static final String COUNTRY_CODE = "US";

    private IOracleService oracleServiceMock;
    private OracleProxyService oracleIntegrationProxyService;

    @Before
    public void setUp() {
        oracleServiceMock = createMock(IOracleService.class);
        oracleIntegrationProxyService = new OracleProxyService();
        oracleIntegrationProxyService.setOracleService(oracleServiceMock);
        oracleIntegrationProxyService.setExpirationTime(100L);
        oracleIntegrationProxyService.createCache();
    }

    @Test
    public void testGetAccountNumbersToCountryCodesMap() {
        expect(oracleServiceMock.getAccountNumbersToCountryCodesMap(ImmutableList.of(ACCOUNT_NUMBER))).andReturn(
            ImmutableMap.of(ACCOUNT_NUMBER, COUNTRY_CODE)).once();
        replay(oracleServiceMock);
        Map<Long, String> actualResult =
            oracleIntegrationProxyService.getAccountNumbersToCountryCodesMap(Collections.singletonList(ACCOUNT_NUMBER));
        assertTrue(MapUtils.isNotEmpty(actualResult));
        assertEquals(1, actualResult.size());
        assertEquals(COUNTRY_CODE, actualResult.get(ACCOUNT_NUMBER));
        assertEquals(COUNTRY_CODE,
            oracleIntegrationProxyService.getAccountNumbersToCountryCodesMap(Collections.singletonList(ACCOUNT_NUMBER))
                .get(ACCOUNT_NUMBER));
        verify(oracleServiceMock);
    }

    @Test
    public void testGetAccountNumbersToCountryCodesMapWithNotFoundResponse() {
        expect(oracleServiceMock.getAccountNumbersToCountryCodesMap(ImmutableList.of(ACCOUNT_NUMBER)))
            .andReturn(Collections.EMPTY_MAP).once();
        replay(oracleServiceMock);
        Map<Long, String> actualResult =
            oracleIntegrationProxyService.getAccountNumbersToCountryCodesMap(Collections.singletonList(ACCOUNT_NUMBER));
        assertNotNull(actualResult);
        assertTrue(MapUtils.isEmpty(actualResult));
        verify(oracleServiceMock);
    }
}
