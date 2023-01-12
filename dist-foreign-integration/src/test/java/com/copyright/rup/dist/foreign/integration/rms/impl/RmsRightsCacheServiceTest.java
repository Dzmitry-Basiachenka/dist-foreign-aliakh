package com.copyright.rup.dist.foreign.integration.rms.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsRightsService;

import com.google.common.collect.Sets;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private static final Long WR_WRK_INST_3 = 659815489L;

    private IRmsRightsService rmsService;
    private RmsRightsCacheService rmsAllRightsCacheService;

    @Before
    public void setUp() {
        rmsService = createMock(IRmsRightsService.class);
        rmsAllRightsCacheService = new RmsRightsCacheService(rmsService, 10, true);
        Whitebox.setInternalState(rmsAllRightsCacheService, rmsService);
        rmsAllRightsCacheService.createCache();
    }

    @Test
    public void testGetAllRmsGrants() {
        List<Long> wrWrkInst1 = List.of(WR_WRK_INST_1);
        List<Long> wrWrkInst2 = List.of(WR_WRK_INST_2, WR_WRK_INST_3);
        Set<RmsGrant> expectedRmsGrants1 = Sets.newHashSet(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1));
        Set<RmsGrant> expectedRmsGrants2 = Sets.newHashSet(buildRmsGrant(WR_WRK_INST_2, RH_ACCOUNT_NUMBER_2),
            buildRmsGrant(WR_WRK_INST_3, RH_ACCOUNT_NUMBER_2));
        Capture<List<Long>> wrWrkInstsCapture = newCapture();
        expect(rmsService.getGrants(eq(wrWrkInst1), anyObject(), eq(Set.of()), eq(Set.of()),
            eq(Set.of()))).andReturn(expectedRmsGrants1).once();
        expect(rmsService.getGrants(capture(wrWrkInstsCapture), anyObject(), eq(Set.of()),
            eq(Set.of()), eq(Set.of()))).andReturn(expectedRmsGrants2).once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsAllRightsCacheService.getGrants(wrWrkInst1, LocalDate.now(), Set.of(),
            Set.of(), Set.of());
        assertEquals(1, CollectionUtils.size(grants));
        assertTrue(grants.containsAll(expectedRmsGrants1));
        grants = rmsAllRightsCacheService.getGrants(wrWrkInst2, LocalDate.now(), Set.of(), Set.of(), Set.of());
        assertEquals(2, CollectionUtils.size(grants));
        assertTrue(grants.containsAll(expectedRmsGrants2));
        verifyWrWrkInsts(wrWrkInst2, wrWrkInstsCapture.getValue());
        verify(rmsService);
    }

    @Test
    public void testGetAllRmsGrantsFromCache() {
        List<Long> wrWrkInsts = List.of(WR_WRK_INST_1, WR_WRK_INST_2, WR_WRK_INST_3);
        Set<RmsGrant> expectedRmsGrants = Sets.newHashSet(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1),
            buildRmsGrant(WR_WRK_INST_2, RH_ACCOUNT_NUMBER_2),
            buildRmsGrant(WR_WRK_INST_3, RH_ACCOUNT_NUMBER_2));
        Capture<List<Long>> wrWrkInstsCapture = newCapture();
        expect(rmsService.getGrants(capture(wrWrkInstsCapture), anyObject(), eq(Set.of()),
            eq(Set.of()), eq(Set.of()))).andReturn(expectedRmsGrants).once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsAllRightsCacheService.getGrants(wrWrkInsts, LocalDate.now(), Set.of(),
            Set.of(), Set.of());
        assertEquals(3, CollectionUtils.size(grants));
        assertTrue(grants.containsAll(expectedRmsGrants));
        Set<RmsGrant> grantsFromCache = rmsAllRightsCacheService.getGrants(wrWrkInsts, LocalDate.now(),
            Set.of(), Set.of(), Set.of());
        assertEquals(3, CollectionUtils.size(grantsFromCache));
        assertTrue(grants.containsAll(expectedRmsGrants));
        verifyWrWrkInsts(wrWrkInsts, wrWrkInstsCapture.getValue());
        verify(rmsService);
    }

    @Test
    public void testGetAllRmsGrantsFromCacheWithTypeOfUses() {
        LocalDate periodEndDate = LocalDate.of(2019, 6, 30);
        Set<String> typeOfUses = Sets.newHashSet("PRINT", "DIGITAL");
        List<Long> wrWrkInsts = List.of(WR_WRK_INST_1, WR_WRK_INST_2);
        Set<RmsGrant> expectedRmsGrants = Sets.newHashSet(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1),
            buildRmsGrant(WR_WRK_INST_2, RH_ACCOUNT_NUMBER_2));
        Capture<List<Long>> wrWrkInstsCapture = newCapture();
        expect(rmsService.getGrants(capture(wrWrkInstsCapture), eq(periodEndDate), eq(Set.of()),
            eq(typeOfUses), eq(Set.of()))).andReturn(expectedRmsGrants).once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsAllRightsCacheService.getGrants(wrWrkInsts, periodEndDate, Set.of(),
            typeOfUses, Set.of());
        assertEquals(2, CollectionUtils.size(grants));
        assertTrue(grants.containsAll(expectedRmsGrants));
        Set<RmsGrant> grantsFromCache = rmsAllRightsCacheService.getGrants(wrWrkInsts, periodEndDate,
            Set.of(), typeOfUses, Set.of());
        assertEquals(2, CollectionUtils.size(grantsFromCache));
        assertTrue(grantsFromCache.containsAll(expectedRmsGrants));
        verifyWrWrkInsts(wrWrkInsts, wrWrkInstsCapture.getValue());
        verify(rmsService);
    }

    @Test
    public void testGetAllRmsGrantsNotFound() {
        List<Long> wrWrkInsts = List.of(WR_WRK_INST_1, WR_WRK_INST_3);
        Capture<List<Long>> wrWrkInstsCapture = newCapture();
        expect(rmsService.getGrants(capture(wrWrkInstsCapture), anyObject(), eq(Set.of()),
            eq(Set.of()), eq(Set.of()))).andReturn(new HashSet<>()).once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsAllRightsCacheService.getGrants(wrWrkInsts, LocalDate.now(), Set.of(),
            Set.of(), Set.of());
        assertTrue(CollectionUtils.isEmpty(grants));
        verifyWrWrkInsts(wrWrkInsts, wrWrkInstsCapture.getValue());
        verify(rmsService);
    }

    private RmsGrant buildRmsGrant(Long wrWrkInst, Long rhAccountNumber) {
        RmsGrant rmsGrant = new RmsGrant();
        rmsGrant.setWrWrkInst(wrWrkInst);
        rmsGrant.setStatus("GRANT");
        rmsGrant.setTypeOfUse("PRINT");
        rmsGrant.setWorkGroupOwnerOrgNumber(new BigDecimal(rhAccountNumber));
        return rmsGrant;
    }

    private void verifyWrWrkInsts(List<Long> expectedWrWrkInsts, List<Long> actualWrWrkInsts) {
        assertEquals(expectedWrWrkInsts.size(), actualWrWrkInsts.size());
        assertTrue(actualWrWrkInsts.containsAll(expectedWrWrkInsts));
    }
}
