package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static junit.framework.TestCase.assertTrue;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link OracleRhTaxCacheService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * </p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleRhTaxCacheServiceTest {

    private static final long ACCOUNT_NUMBER = 1000015778L;

    private IOracleRhTaxService oracleRhTaxService;
    private OracleRhTaxCacheService oracleRhTaxCacheService;

    @Before
    public void setUp() {
        oracleRhTaxService = createMock(IOracleRhTaxService.class);
        oracleRhTaxCacheService = new OracleRhTaxCacheService(100);
        Whitebox.setInternalState(oracleRhTaxCacheService, "oracleRhTaxService", oracleRhTaxService);
        oracleRhTaxCacheService.createCache();
    }

    @Test
    public void testConstructor() {
        assertEquals(oracleRhTaxCacheService.getExpirationTime(), 6000L, 0);
    }

    @Test
    public void testIsUsTaxCountry() {
        expect(oracleRhTaxService.isUsTaxCountry(ACCOUNT_NUMBER)).andReturn(true).once();
        replay(oracleRhTaxService);
        assertTrue(oracleRhTaxCacheService.isUsTaxCountry(ACCOUNT_NUMBER));
        verify(oracleRhTaxService);
    }

    @Test
    public void testIsUsTaxCountryWithNotFoundResponse() {
        expect(oracleRhTaxService.isUsTaxCountry(ACCOUNT_NUMBER)).andReturn(false).once();
        replay(oracleRhTaxService);
        assertFalse(oracleRhTaxCacheService.isUsTaxCountry(ACCOUNT_NUMBER));
        verify(oracleRhTaxService);
    }
}
