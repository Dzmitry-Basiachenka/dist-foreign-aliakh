package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static junit.framework.TestCase.assertTrue;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link OracleRhTaxCountryCacheService}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * </p>
 * Date: 11/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class OracleRhTaxCountryCacheServiceTest {

    private static final long ACCOUNT_NUMBER = 1000015778L;

    private IOracleRhTaxCountryService oracleRhTaxCountryService;
    private OracleRhTaxCountryCacheService oracleRhTaxCountryCacheService;

    @Before
    public void setUp() {
        oracleRhTaxCountryService = createMock(IOracleRhTaxCountryService.class);
        oracleRhTaxCountryCacheService = new OracleRhTaxCountryCacheService(100);
        Whitebox.setInternalState(oracleRhTaxCountryCacheService, "oracleRhTaxCountryService",
            oracleRhTaxCountryService);
        oracleRhTaxCountryCacheService.createCache();
    }

    @Test
    public void testConstructor() {
        assertEquals(oracleRhTaxCountryCacheService.getExpirationTime(), 6000L, 0);
    }

    @Test
    public void testIsUsTaxCountry() {
        expect(oracleRhTaxCountryService.isUsTaxCountry(ACCOUNT_NUMBER)).andReturn(true).once();
        replay(oracleRhTaxCountryService);
        assertTrue(oracleRhTaxCountryCacheService.isUsTaxCountry(ACCOUNT_NUMBER));
        verify(oracleRhTaxCountryService);
    }

    @Test
    public void testIsUsTaxCountryWithNotFoundResponse() {
        expect(oracleRhTaxCountryService.isUsTaxCountry(ACCOUNT_NUMBER)).andReturn(false).once();
        replay(oracleRhTaxCountryService);
        assertFalse(oracleRhTaxCountryCacheService.isUsTaxCountry(ACCOUNT_NUMBER));
        verify(oracleRhTaxCountryService);
    }
}
