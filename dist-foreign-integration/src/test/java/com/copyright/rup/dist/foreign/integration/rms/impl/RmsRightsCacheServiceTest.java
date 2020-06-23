package com.copyright.rup.dist.foreign.integration.rms.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsService;

import com.google.common.collect.Sets;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link RmsRightsCacheService}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 11/21/18
 *
 * @author Uladzislau Shalamitski
 */
public class RmsRightsCacheServiceTest {

    private static final Long RH_ACCOUNT_NUMBER_1 = 1000009522L;
    private static final Long RH_ACCOUNT_NUMBER_2 = 2000009522L;
    private static final Long WR_WRK_INST_1 = 459815489L;
    private static final Long WR_WRK_INST_2 = 559815489L;

    private IRmsRightsService rmsService;
    private RmsRightsCacheService rmsAllRightsCacheService;

    @Before
    public void setUp() {
        rmsService = createMock(IRmsRightsService.class);
        rmsAllRightsCacheService = new RmsRightsCacheService(rmsService, 10);
        Whitebox.setInternalState(rmsAllRightsCacheService, rmsService);
        rmsAllRightsCacheService.createCache();
    }

    @Test
    public void testGetAllRmsGrants() {
        List<Long> wrWrkInst1 = Collections.singletonList(WR_WRK_INST_1);
        List<Long> wrWrkInst2 = Collections.singletonList(WR_WRK_INST_2);
        expect(rmsService.getGrants(eq(wrWrkInst1), anyObject(), eq(Collections.emptySet()), eq(Collections.emptySet()),
            eq(Collections.emptySet())))
            .andReturn(Sets.newHashSet(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1))).once();
        expect(rmsService.getGrants(eq(wrWrkInst2), anyObject(), eq(Collections.emptySet()), eq(Collections.emptySet()),
            eq(Collections.emptySet())))
            .andReturn(Sets.newHashSet(buildRmsGrant(WR_WRK_INST_2, RH_ACCOUNT_NUMBER_2))).once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsAllRightsCacheService.getGrants(wrWrkInst1, LocalDate.now(), Collections.emptySet(),
            Collections.emptySet(), Collections.emptySet());
        assertEquals(1, CollectionUtils.size(grants));
        assertTrue(grants.contains(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1)));
        grants = rmsAllRightsCacheService.getGrants(wrWrkInst2, LocalDate.now(), Collections.emptySet(),
            Collections.emptySet(), Collections.emptySet());
        assertEquals(1, CollectionUtils.size(grants));
        assertTrue(grants.contains(buildRmsGrant(WR_WRK_INST_2, RH_ACCOUNT_NUMBER_2)));
        verify(rmsService);
    }

    @Test
    public void testGetAllRmsGrantsFromCache() {
        List<Long> wrWrkInst = Collections.singletonList(WR_WRK_INST_1);
        expect(rmsService.getGrants(eq(wrWrkInst), anyObject(), eq(Collections.emptySet()), eq(Collections.emptySet()),
            eq(Collections.emptySet())))
            .andReturn(Sets.newHashSet(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1))).once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsAllRightsCacheService.getGrants(wrWrkInst, LocalDate.now(), Collections.emptySet(),
            Collections.emptySet(), Collections.emptySet());
        assertEquals(1, CollectionUtils.size(grants));
        assertTrue(grants.contains(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1)));
        Set<RmsGrant> grantsFromCache = rmsAllRightsCacheService.getGrants(wrWrkInst, LocalDate.now(),
            Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
        assertEquals(1, CollectionUtils.size(grantsFromCache));
        assertTrue(grantsFromCache.contains(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1)));
        verify(rmsService);
    }

    @Test
    public void testGetAllRmsGrantsFromCacheWithTypeOfUses() {
        LocalDate periodEndDate = LocalDate.of(2019, 6, 30);
        Set<String> typeOfUses = Sets.newHashSet("PRINT", "DIGITAL");
        List<Long> wrWrkInst = Collections.singletonList(WR_WRK_INST_1);
        expect(rmsService.getGrants(wrWrkInst, periodEndDate, Collections.emptySet(), typeOfUses,
            Collections.emptySet()))
            .andReturn(Sets.newHashSet(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1))).once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsAllRightsCacheService.getGrants(wrWrkInst, periodEndDate, Collections.emptySet(),
            typeOfUses, Collections.emptySet());
        assertEquals(1, CollectionUtils.size(grants));
        assertTrue(grants.contains(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1)));
        Set<RmsGrant> grantsFromCache = rmsAllRightsCacheService.getGrants(wrWrkInst, periodEndDate,
            Collections.emptySet(), typeOfUses, Collections.emptySet());
        assertEquals(1, CollectionUtils.size(grantsFromCache));
        assertTrue(grantsFromCache.contains(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1)));
        verify(rmsService);
    }

    @Test
    public void testGetAllRmsGrantsNotFound() {
        List<Long> wrWrkInsts = Collections.singletonList(WR_WRK_INST_1);
        expect(rmsService.getGrants(eq(wrWrkInsts), anyObject(), eq(Collections.emptySet()), eq(Collections.emptySet()),
            eq(Collections.emptySet()))).andReturn(new HashSet<>()).once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsAllRightsCacheService.getGrants(wrWrkInsts, LocalDate.now(), Collections.emptySet(),
            Collections.emptySet(), Collections.emptySet());
        assertTrue(CollectionUtils.isEmpty(grants));
        verify(rmsService);
    }

    private RmsGrant buildRmsGrant(Long wrWrkInst, Long rhAccountNumber) {
        RmsGrant rmsGrant = new RmsGrant();
        rmsGrant.setWrWrkInst(wrWrkInst);
        rmsGrant.setStatus("GRANT");
        rmsGrant.setTypeOfUse("NGT_PHOTOCOPY");
        rmsGrant.setWorkGroupOwnerOrgNumber(new BigDecimal(rhAccountNumber));
        return rmsGrant;
    }
}
