package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.RmsGrant;
import com.copyright.rup.dist.common.integration.rest.rms.IRmsService;
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
 * Verifies {@link RmsProxyService}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 11/21/18
 *
 * @author Uladzislau Shalamitski
 */
public class RmsProxyServiceTest {

    private static final Long RH_ACCOUNT_NUMBER_1 = 1000009522L;
    private static final Long RH_ACCOUNT_NUMBER_2 = 2000009522L;
    private static final Long WR_WRK_INST_1 = 459815489L;
    private static final Long WR_WRK_INST_2 = 559815489L;

    private IRmsService rmsService;
    private RmsProxyService rmsProxyService;

    @Before
    public void setUp() {
        rmsService = createMock(IRmsService.class);
        rmsProxyService = new RmsProxyService(rmsService, 10);
        Whitebox.setInternalState(rmsProxyService, rmsService);
        rmsProxyService.createCache();
    }

    @Test
    public void testGetAllRmsGrants() {
        List<Long> wrWrkInst1 = Collections.singletonList(WR_WRK_INST_1);
        List<Long> wrWrkInst2 = Collections.singletonList(WR_WRK_INST_2);
        expect(rmsService.getAllRmsGrants(eq(wrWrkInst1), anyObject()))
            .andReturn(Sets.newHashSet(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1)))
            .once();
        expect(rmsService.getAllRmsGrants(eq(wrWrkInst2), anyObject()))
            .andReturn(Sets.newHashSet(buildRmsGrant(WR_WRK_INST_2, RH_ACCOUNT_NUMBER_2)))
            .once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsProxyService.getAllRmsGrants(wrWrkInst1, LocalDate.now());
        assertEquals(1, CollectionUtils.size(grants));
        assertTrue(grants.contains(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1)));
        grants = rmsProxyService.getAllRmsGrants(wrWrkInst2, LocalDate.now());
        assertEquals(1, CollectionUtils.size(grants));
        assertTrue(grants.contains(buildRmsGrant(WR_WRK_INST_2, RH_ACCOUNT_NUMBER_2)));
        verify(rmsService);
    }

    @Test
    public void testGetAllRmsGrantsFromCache() {
        List<Long> wrWrkInst = Collections.singletonList(WR_WRK_INST_1);
        expect(rmsService.getAllRmsGrants(eq(wrWrkInst), anyObject()))
            .andReturn(Sets.newHashSet(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1)))
            .once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsProxyService.getAllRmsGrants(wrWrkInst, LocalDate.now());
        assertEquals(1, CollectionUtils.size(grants));
        assertTrue(grants.contains(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1)));
        grants = rmsProxyService.getAllRmsGrants(wrWrkInst, LocalDate.now());
        assertEquals(1, CollectionUtils.size(grants));
        assertTrue(grants.contains(buildRmsGrant(WR_WRK_INST_1, RH_ACCOUNT_NUMBER_1)));
        verify(rmsService);
    }

    @Test
    public void testGetAllRmsGrantsNotFound() {
        List<Long> wrWrkInsts = Collections.singletonList(WR_WRK_INST_1);
        expect(rmsService.getAllRmsGrants(eq(wrWrkInsts), anyObject()))
            .andReturn(new HashSet<>())
            .once();
        replay(rmsService);
        Set<RmsGrant> grants = rmsProxyService.getAllRmsGrants(wrWrkInsts, LocalDate.now());
        assertTrue(CollectionUtils.isEmpty(grants));
        verify(rmsService);
    }

    private RmsGrant buildRmsGrant(Long wrWrkInst, Long rhAccountNumber) {
        RmsGrant rmsGrant = new RmsGrant();
        rmsGrant.setWrWrkInst(wrWrkInst);
        rmsGrant.setStatus("GRANT");
        rmsGrant.setTypeOfUse("NGT_PHOTOCOPY");
        rmsGrant.setMarket("CORPORATE");
        rmsGrant.setDistribution("EXTERNAL");
        rmsGrant.setWorkGroupOwnerOrgNumber(new BigDecimal(rhAccountNumber));
        return rmsGrant;
    }
}
