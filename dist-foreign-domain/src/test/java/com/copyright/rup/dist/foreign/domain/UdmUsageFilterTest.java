package com.copyright.rup.dist.foreign.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;

import com.google.common.collect.Sets;

import org.junit.Test;

import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link UdmUsageFilter}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 05/04/21
 *
 * @author Dzmitry Basiachenka
 */
public class UdmUsageFilterTest {

    private static final Set<String> USAGE_BATCH_IDS = Collections.singleton("Usage Batch Id");
    private static final Integer PERIOD = 202012;

    @Test
    public void testIsEmpty() {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        assertTrue(udmUsageFilter.isEmpty());
        assertTrue(udmUsageFilter.isEmpty());
        udmUsageFilter.setUsageBatchesIds(Sets.newHashSet());
        assertTrue(udmUsageFilter.isEmpty());
        udmUsageFilter.setUsageStatus(UsageStatusEnum.NEW);
        assertFalse(udmUsageFilter.isEmpty());
        udmUsageFilter.setUsageStatus(null);
        assertTrue(udmUsageFilter.isEmpty());
        udmUsageFilter.setPeriod(PERIOD);
        assertFalse(udmUsageFilter.isEmpty());
        udmUsageFilter.setPeriod(null);
        assertTrue(udmUsageFilter.isEmpty());
        udmUsageFilter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        assertFalse(udmUsageFilter.isEmpty());
        udmUsageFilter.setUdmUsageOrigin(null);
        assertTrue(udmUsageFilter.isEmpty());
        udmUsageFilter.setUsageBatchesIds(Sets.newHashSet(RupPersistUtils.generateUuid()));
        assertFalse(udmUsageFilter.isEmpty());
    }

    @Test
    public void testConstructor() {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setUsageBatchesIds(USAGE_BATCH_IDS);
        udmUsageFilter.setPeriod(PERIOD);
        udmUsageFilter.setUdmUsageOrigin(UdmUsageOriginEnum.RFA);
        udmUsageFilter.setUsageStatus(UsageStatusEnum.NEW);
        UdmUsageFilter udmUsageFilterCopy = new UdmUsageFilter(udmUsageFilter);
        assertEquals(USAGE_BATCH_IDS, udmUsageFilterCopy.getUsageBatchesIds());
        assertEquals(PERIOD, udmUsageFilterCopy.getPeriod());
        assertEquals(UdmUsageOriginEnum.RFA, udmUsageFilterCopy.getUdmUsageOrigin());
        assertEquals(UsageStatusEnum.NEW, udmUsageFilterCopy.getUsageStatus());
    }
}
