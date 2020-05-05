package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static junit.framework.TestCase.assertTrue;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleRhTaxChunkService;

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
 * Verifies {@link OracleRhTaxChunkCacheService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/05/2020
 *
 * @author Uladzislau Shalamitski
 */
public class OracleRhTaxChunkCacheServiceTest {

    private static final Set<Long> ACCOUNT_NUMBERS = Sets.newHashSet(7001413934L, 1000009522L);

    private IOracleRhTaxChunkService oracleRhTaxChunkService;
    private OracleRhTaxChunkCacheService oracleRhTaxChunkCacheService;

    @Before
    public void setUp() {
        oracleRhTaxChunkService = createMock(IOracleRhTaxChunkService.class);
        oracleRhTaxChunkCacheService = new OracleRhTaxChunkCacheService(100);
        Whitebox.setInternalState(oracleRhTaxChunkCacheService, oracleRhTaxChunkService);
        oracleRhTaxChunkCacheService.createCache();
    }

    @Test
    public void testConstructor() {
        assertEquals(oracleRhTaxChunkCacheService.getExpirationTime(), 6000L, 0);
    }

    @Test
    public void testIsUsTaxCountry() {
        Map<Long, Boolean> resultMap = ImmutableMap.of(7001413934L, Boolean.TRUE, 1000009522L, Boolean.FALSE);
        expect(oracleRhTaxChunkService.isUsTaxCountry(ACCOUNT_NUMBERS)).andReturn(resultMap).once();
        replay(oracleRhTaxChunkService);
        assertEquals(resultMap, oracleRhTaxChunkCacheService.isUsTaxCountry(ACCOUNT_NUMBERS));
        assertEquals(ImmutableMap.of(7001413934L, Boolean.TRUE),
            oracleRhTaxChunkCacheService.isUsTaxCountry(Collections.singleton(7001413934L)));
        verify(oracleRhTaxChunkService);
    }

    @Test
    public void testIsUsTaxCountryWithNotFoundResponse() {
        expect(oracleRhTaxChunkService.isUsTaxCountry(ACCOUNT_NUMBERS)).andReturn(Collections.emptyMap()).once();
        replay(oracleRhTaxChunkService);
        assertTrue(CollectionUtils.isEmpty(oracleRhTaxChunkCacheService.isUsTaxCountry(ACCOUNT_NUMBERS)));
        verify(oracleRhTaxChunkService);
    }
}
