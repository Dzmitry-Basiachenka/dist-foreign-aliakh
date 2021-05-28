package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static junit.framework.TestCase.assertTrue;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryService;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link OracleRhTaxCountryCacheService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/05/2020
 *
 * @author Uladzislau Shalamitski
 */
public class OracleRhTaxCountryCacheServiceTest {

    private static final Set<Long> ACCOUNT_NUMBERS = Sets.newHashSet(7001413934L, 1000009522L);

    private IOracleRhTaxCountryService oracleRhTaxCountryService;
    private OracleRhTaxCountryCacheService oracleRhTaxCountryCacheService;

    @Before
    public void setUp() {
        oracleRhTaxCountryService = createMock(IOracleRhTaxCountryService.class);
        oracleRhTaxCountryCacheService = new OracleRhTaxCountryCacheService(100);
        Whitebox.setInternalState(oracleRhTaxCountryCacheService, oracleRhTaxCountryService);
        oracleRhTaxCountryCacheService.createCache();
    }

    @Test
    public void testConstructor() {
        assertEquals(oracleRhTaxCountryCacheService.getExpirationTime(), 6000L, 0);
    }

    @Test
    public void testIsUsTaxCountry() {
        Map<Long, Boolean> resultMap = ImmutableMap.of(7001413934L, Boolean.TRUE, 1000009522L, Boolean.FALSE);
        expect(oracleRhTaxCountryService.isUsTaxCountry(ACCOUNT_NUMBERS)).andReturn(resultMap).once();
        replay(oracleRhTaxCountryService);
        assertEquals(resultMap, oracleRhTaxCountryCacheService.isUsTaxCountry(ACCOUNT_NUMBERS));
        assertEquals(ImmutableMap.of(7001413934L, Boolean.TRUE),
            oracleRhTaxCountryCacheService.isUsTaxCountry(Collections.singleton(7001413934L)));
        verify(oracleRhTaxCountryService);
    }

    @Test
    public void testIsUsTaxCountryWithNotFoundResponse() {
        expect(oracleRhTaxCountryService.isUsTaxCountry(ACCOUNT_NUMBERS)).andReturn(Collections.emptyMap()).once();
        replay(oracleRhTaxCountryService);
        assertTrue(CollectionUtils.isEmpty(oracleRhTaxCountryCacheService.isUsTaxCountry(ACCOUNT_NUMBERS)));
        verify(oracleRhTaxCountryService);
    }
}
