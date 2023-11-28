package com.copyright.rup.dist.foreign.service.impl.converter;

import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import java.util.Objects;
import java.util.function.Function;

/**
 * Class to convert {@link ScenarioUsageFilter} to {@link UsageFilter}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 11/27/2023
 *
 * @author Dzmitry Basiachenka
 */
public class ScenarioUsageFilterToUsageFilterConverter implements Function<ScenarioUsageFilter, UsageFilter> {

    @Override
    public UsageFilter apply(ScenarioUsageFilter scenarioUsageFilter) {
        Objects.requireNonNull(scenarioUsageFilter);
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setRhAccountNumbers(scenarioUsageFilter.getRhAccountNumbers());
        usageFilter.setUsageBatchesIds(scenarioUsageFilter.getUsageBatchesIds());
        usageFilter.setUsageStatus(scenarioUsageFilter.getUsageStatus());
        usageFilter.setPaymentDate(scenarioUsageFilter.getPaymentDate());
        usageFilter.setFiscalYear(scenarioUsageFilter.getFiscalYear());
        usageFilter.setProductFamily(scenarioUsageFilter.getProductFamily());
        return usageFilter;
    }
}
