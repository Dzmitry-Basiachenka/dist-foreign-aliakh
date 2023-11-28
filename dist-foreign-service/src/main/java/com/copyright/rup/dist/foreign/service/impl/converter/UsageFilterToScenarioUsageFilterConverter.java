package com.copyright.rup.dist.foreign.service.impl.converter;

import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.Objects;
import java.util.function.Function;

/**
 * Class to convert {@link UsageFilter} to {@link ScenarioUsageFilter}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/27/2023
 *
 * @author Dzmitry Basiachenka
 */
public class UsageFilterToScenarioUsageFilterConverter implements Function<UsageFilter, ScenarioUsageFilter> {

    @Override
    public ScenarioUsageFilter apply(UsageFilter usageFilter) {
        ScenarioUsageFilter scenarioUsageFilter = new ScenarioUsageFilter();
        if (Objects.nonNull(usageFilter)) {
            scenarioUsageFilter.setRhAccountNumbers(usageFilter.getRhAccountNumbers());
            scenarioUsageFilter.setUsageBatchesIds(usageFilter.getUsageBatchesIds());
            scenarioUsageFilter.setProductFamily(usageFilter.getProductFamily());
            scenarioUsageFilter.setUsageStatus(usageFilter.getUsageStatus());
            scenarioUsageFilter.setPaymentDate(usageFilter.getPaymentDate());
            scenarioUsageFilter.setFiscalYear(usageFilter.getFiscalYear());
            scenarioUsageFilter.setUsagePeriod(usageFilter.getUsagePeriod());
        }
        return scenarioUsageFilter;
    }
}
