package com.copyright.rup.dist.foreign.integration.oracle.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleIntegrationService;
import com.copyright.rup.dist.foreign.integration.oracle.api.IOracleService;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link OracleIntegrationServiceTest}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/27/2018
 *
 * @author Uladzislau Shalamitski
 */
public class OracleIntegrationServiceTest {

    private IOracleService oracleService;
    private IOracleIntegrationService oracleIntegrationService;

    @Before
    public void setUp() {
        oracleIntegrationService = new OracleIntegrationService();
        oracleService = createMock(IOracleService.class);
        Whitebox.setInternalState(oracleIntegrationService, oracleService);
    }

    @Test
    public void testIsUsCountryCodeTrue() {
        expect(oracleService.getAccountNumbersToCountryCodesMap(Collections.singletonList(1000009522L)))
            .andReturn(ImmutableMap.of(1000009522L, "US"))
            .once();
        replay(oracleService);
        assertTrue(oracleIntegrationService.isUsCountryCode(1000009522L));
        verify(oracleService);
    }

    @Test
    public void testIsUsCountryCodeFalse() {
        expect(oracleService.getAccountNumbersToCountryCodesMap(Collections.singletonList(1000009522L)))
            .andReturn(ImmutableMap.of(1000009522L, "GB"))
            .once();
        replay(oracleService);
        assertFalse(oracleIntegrationService.isUsCountryCode(1000009522L));
        verify(oracleService);
    }
}
