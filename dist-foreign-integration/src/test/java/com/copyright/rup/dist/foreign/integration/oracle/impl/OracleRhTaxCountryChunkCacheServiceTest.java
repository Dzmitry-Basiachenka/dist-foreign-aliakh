package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static junit.framework.TestCase.assertTrue;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxCountryChunkService;

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
 * Verifies {@link OracleRhTaxCountryChunkCacheService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/05/2020
 *
 * @author Uladzislau Shalamitski
 */
public class OracleRhTaxCountryChunkCacheServiceTest {

    private static final Set<Long> ACCOUNT_NUMBERS = Sets.newHashSet(7001413934L, 1000009522L);

    private IOracleRhTaxCountryChunkService oracleRhTaxCountryChunkService;
    private OracleRhTaxCountryChunkCacheService oracleRhTaxCountryChunkCacheService;

    @Before
    public void setUp() {
        oracleRhTaxCountryChunkService = createMock(IOracleRhTaxCountryChunkService.class);
        oracleRhTaxCountryChunkCacheService = new OracleRhTaxCountryChunkCacheService(100);
        Whitebox.setInternalState(oracleRhTaxCountryChunkCacheService, oracleRhTaxCountryChunkService);
        oracleRhTaxCountryChunkCacheService.createCache();
    }

    @Test
    public void testConstructor() {
        assertEquals(oracleRhTaxCountryChunkCacheService.getExpirationTime(), 6000L, 0);
    }

    @Test
    public void testIsUsTaxCountry() {
        Map<Long, Boolean> resultMap = ImmutableMap.of(7001413934L, Boolean.TRUE, 1000009522L, Boolean.FALSE);
        expect(oracleRhTaxCountryChunkService.isUsTaxCountry(ACCOUNT_NUMBERS)).andReturn(resultMap).once();
        replay(oracleRhTaxCountryChunkService);
        assertEquals(resultMap, oracleRhTaxCountryChunkCacheService.isUsTaxCountry(ACCOUNT_NUMBERS));
        assertEquals(ImmutableMap.of(7001413934L, Boolean.TRUE),
            oracleRhTaxCountryChunkCacheService.isUsTaxCountry(Collections.singleton(7001413934L)));
        verify(oracleRhTaxCountryChunkService);
    }

    @Test
    public void testIsUsTaxCountryWithNotFoundResponse() {
        expect(oracleRhTaxCountryChunkService.isUsTaxCountry(ACCOUNT_NUMBERS)).andReturn(Collections.emptyMap()).once();
        replay(oracleRhTaxCountryChunkService);
        assertTrue(CollectionUtils.isEmpty(oracleRhTaxCountryChunkCacheService.isUsTaxCountry(ACCOUNT_NUMBERS)));
        verify(oracleRhTaxCountryChunkService);
    }
}
