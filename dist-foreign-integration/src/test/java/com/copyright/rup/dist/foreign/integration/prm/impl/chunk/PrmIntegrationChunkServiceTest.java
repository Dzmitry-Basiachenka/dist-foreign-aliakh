package com.copyright.rup.dist.foreign.integration.prm.impl.chunk;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.integration.rest.prm.IPrmPreferenceService;
import com.copyright.rup.dist.foreign.domain.FdaConstants;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.Map;

/**
 * Verifies {@link PrmIntegrationChunkService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Liakh
 */
public class PrmIntegrationChunkServiceTest {

    private static final String RIGHTSHOLDER_ID = "66a7c2c0-3b09-48ad-9aa5-a6d0822226c7";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String ALL_PRODUCTS = "*";
    private PrmIntegrationChunkService prmIntegrationService;
    private IPrmPreferenceService prmPreferenceService;

    @Before
    public void setUp() {
        prmIntegrationService = new PrmIntegrationChunkService();
        prmPreferenceService = createMock(IPrmPreferenceService.class);
        Whitebox.setInternalState(prmIntegrationService, prmPreferenceService);
    }

    @Test
    public void testAreStmRightsholdersTrue() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(NTS_PRODUCT_FAMILY, FdaConstants.IS_RH_STM_IPRO_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Collections.singleton(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        Map<String, Boolean> rhIdsToIsStmRhs =
            prmIntegrationService.areStmRightsholders(Collections.singleton(RIGHTSHOLDER_ID), NTS_PRODUCT_FAMILY);
        assertTrue(rhIdsToIsStmRhs.get(RIGHTSHOLDER_ID));
        verify(prmPreferenceService);
    }

    @Test
    public void testAreStmRightsholdersFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(NTS_PRODUCT_FAMILY, FdaConstants.IS_RH_STM_IPRO_CODE, false);
        expect(prmPreferenceService.getPreferencesMap(Collections.singleton(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        Map<String, Boolean> rhIdsToIsStmRhs =
            prmIntegrationService.areStmRightsholders(Collections.singleton(RIGHTSHOLDER_ID), NTS_PRODUCT_FAMILY);
        assertFalse(rhIdsToIsStmRhs.get(RIGHTSHOLDER_ID));
        verify(prmPreferenceService);
    }

    @Test
    public void testAreStmRightsholdersAllProductsTrue() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(ALL_PRODUCTS, FdaConstants.IS_RH_STM_IPRO_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Collections.singleton(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        Map<String, Boolean> rhIdsToIsStmRhs =
            prmIntegrationService.areStmRightsholders(Collections.singleton(RIGHTSHOLDER_ID), NTS_PRODUCT_FAMILY);
        assertTrue(rhIdsToIsStmRhs.get(RIGHTSHOLDER_ID));
        verify(prmPreferenceService);
    }

    @Test
    public void testAreStmRightsholdersAllProductsFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(ALL_PRODUCTS, FdaConstants.IS_RH_STM_IPRO_CODE, false);
        expect(prmPreferenceService.getPreferencesMap(Collections.singleton(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        Map<String, Boolean> rhIdsToIsStmRhs =
            prmIntegrationService.areStmRightsholders(Collections.singleton(RIGHTSHOLDER_ID), NTS_PRODUCT_FAMILY);
        assertFalse(rhIdsToIsStmRhs.get(RIGHTSHOLDER_ID));
        verify(prmPreferenceService);
    }

    @Test
    public void testAreStmRightsholdersDefaultFalse() {
        Table<String, String, Object> preferencesTable = HashBasedTable.create();
        preferencesTable.put(FAS_PRODUCT_FAMILY, FdaConstants.IS_RH_STM_IPRO_CODE, true);
        expect(prmPreferenceService.getPreferencesMap(Collections.singleton(RIGHTSHOLDER_ID)))
            .andReturn(ImmutableMap.of(RIGHTSHOLDER_ID, preferencesTable)).once();
        replay(prmPreferenceService);
        Map<String, Boolean> rhIdsToIsStmRhs =
            prmIntegrationService.areStmRightsholders(Collections.singleton(RIGHTSHOLDER_ID), NTS_PRODUCT_FAMILY);
        assertFalse(rhIdsToIsStmRhs.get(RIGHTSHOLDER_ID));
        verify(prmPreferenceService);
    }
}
