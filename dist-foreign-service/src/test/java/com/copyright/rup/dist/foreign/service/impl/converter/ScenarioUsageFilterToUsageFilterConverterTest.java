package com.copyright.rup.dist.foreign.service.impl.converter;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Set;

/**
 * Verifies {@link ScenarioUsageFilterToUsageFilterConverter}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/27/2023
 *
 * @author Dzmitry Basiachenka
 */
public class ScenarioUsageFilterToUsageFilterConverterTest {

    private static final Long ACCOUNT_NUMBER_1 = 1000000001L;
    private static final String BATCH_ID_1 = "170c530c-c781-45fd-8109-61104ce9d5c5";
    private static final String BATCH_ID_2 = "d91ac632-f4eb-4848-a8eb-363706d043ea";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final Integer FISCAL_YEAR = 2023;
    private static final Integer USAGE_PERIOD = 2022;
    private static final LocalDate DATE = LocalDate.of(2023, 5, 23);

    private ScenarioUsageFilterToUsageFilterConverter converter;

    @Before
    public void setUp() {
        converter = new ScenarioUsageFilterToUsageFilterConverter();
    }

    @Test
    public void testApply() {
        assertEquals(buildUsageFilter(), converter.apply(buildScenarioUsageFilter()));
    }

    @Test(expected = NullPointerException.class)
    public void testApplyScenarioUsageFilterNull() {
        converter.apply(null);
    }

    private ScenarioUsageFilter buildScenarioUsageFilter() {
        ScenarioUsageFilter filter = new ScenarioUsageFilter();
        filter.setRhAccountNumbers(Set.of(ACCOUNT_NUMBER_1));
        filter.setUsageBatchesIds(Set.of(BATCH_ID_1, BATCH_ID_2));
        filter.setProductFamily(FAS_PRODUCT_FAMILY);
        filter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        filter.setPaymentDate(DATE);
        filter.setFiscalYear(FISCAL_YEAR);
        filter.setUsagePeriod(USAGE_PERIOD);
        return filter;
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setRhAccountNumbers(Set.of(ACCOUNT_NUMBER_1));
        filter.setUsageBatchesIds(Set.of(BATCH_ID_1, BATCH_ID_2));
        filter.setProductFamily(FAS_PRODUCT_FAMILY);
        filter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        filter.setPaymentDate(DATE);
        filter.setFiscalYear(FISCAL_YEAR);
        return filter;
    }
}
