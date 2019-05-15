package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static junit.framework.TestCase.assertTrue;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleService;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link OracleCacheService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * </p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleCacheServiceTest {

    private static final long ACCOUNT_NUMBER = 1000015778L;

    private IOracleService oracleServiceMock;
    private OracleCacheService oracleCacheService;

    @Before
    public void setUp() {
        oracleServiceMock = createMock(IOracleService.class);
        oracleCacheService = new OracleCacheService(100);
        oracleCacheService.setOracleService(oracleServiceMock);
        oracleCacheService.createCache();
    }

    @Test
    public void testConstructor() {
        assertEquals(oracleCacheService.getExpirationTime(), 6000L, 0);
    }

    @Test
    public void testIsUsTaxCountry() {
        expect(oracleServiceMock.isUsTaxCountry(ACCOUNT_NUMBER)).andReturn(Boolean.TRUE).once();
        replay(oracleServiceMock);
        assertTrue(oracleCacheService.isUsTaxCountry(ACCOUNT_NUMBER));
        verify(oracleServiceMock);
    }

    @Test
    public void testIsUsTaxCountryWithNotFoundResponse() {
        expect(oracleServiceMock.isUsTaxCountry(ACCOUNT_NUMBER)).andReturn(Boolean.FALSE).once();
        replay(oracleServiceMock);
        assertFalse(oracleCacheService.isUsTaxCountry(ACCOUNT_NUMBER));
        verify(oracleServiceMock);
    }
}
