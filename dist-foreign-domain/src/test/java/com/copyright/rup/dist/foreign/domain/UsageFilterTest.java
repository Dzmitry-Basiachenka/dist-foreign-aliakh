package com.copyright.rup.dist.foreign.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;

import com.google.common.collect.Sets;

import org.junit.Test;

/**
 * Verifies {@link UsageFilter}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/19/17
 *
 * @author Aliaksandr Radkevich
 */
public class UsageFilterTest {

    @Test
    public void testIsEmpty() {
        UsageFilter usageFilter = new UsageFilter();
        assertTrue(usageFilter.isEmpty());
        usageFilter.setUsageBatchesIds(Sets.newHashSet());
        assertTrue(usageFilter.isEmpty());
        usageFilter.setRhAccountNumbers(Sets.newHashSet());
        assertTrue(usageFilter.isEmpty());
        usageFilter.setUsageBatchesIds(Sets.newHashSet(RupPersistUtils.generateUuid()));
        assertFalse(usageFilter.isEmpty());
        usageFilter.setRhAccountNumbers(Sets.newHashSet(10000L));
        assertFalse(usageFilter.isEmpty());
        usageFilter.setUsageBatchesIds(null);
        assertFalse(usageFilter.isEmpty());
    }
}
