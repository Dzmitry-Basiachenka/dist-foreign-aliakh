package com.copyright.rup.dist.foreign.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import com.google.common.collect.Sets;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Set;

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

    private static final Set<Long> RH_ACCOUNT_NUMBERS = Set.of(12345678L);
    private static final Set<String> USAGE_BATCH_IDS = Set.of("Usage Batch Id");
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2016, 10, 22);
    private static final int FISCAL_YEAR = 2016;

    @Test
    public void testIsEmpty() {
        UsageFilter usageFilter = new UsageFilter();
        assertTrue(usageFilter.isEmpty());
        usageFilter.setProductFamily("FAS");
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

    @Test
    public void testConstructor() {
        UsageFilter uf = new UsageFilter();
        uf.setRhAccountNumbers(RH_ACCOUNT_NUMBERS);
        uf.setUsageBatchesIds(USAGE_BATCH_IDS);
        uf.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        uf.setPaymentDate(PAYMENT_DATE);
        uf.setFiscalYear(FISCAL_YEAR);
        UsageFilter newUsageFilter = new UsageFilter(uf);
        assertEquals(RH_ACCOUNT_NUMBERS, newUsageFilter.getRhAccountNumbers());
        assertEquals(USAGE_BATCH_IDS, newUsageFilter.getUsageBatchesIds());
        assertEquals(UsageStatusEnum.ELIGIBLE, newUsageFilter.getUsageStatus());
        assertEquals(PAYMENT_DATE, newUsageFilter.getPaymentDate());
        assertEquals(FISCAL_YEAR, newUsageFilter.getFiscalYear(), 0);
    }
}
