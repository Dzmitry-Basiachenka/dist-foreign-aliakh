package com.copyright.rup.dist.foreign.service.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioUsageFilterRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IScenarioUsageFilterService}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/05/2018
 *
 * @author Aliaksandr Liakh
 */
@Service
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class ScenarioUsageFilterService implements IScenarioUsageFilterService {

    @Autowired
    private IScenarioUsageFilterRepository scenarioUsageFilterRepository;

    @Override
    @Transactional
    public void insert(String scenarioId, ScenarioUsageFilter usageFilter) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        Objects.requireNonNull(usageFilter);
        final String filterId = RupPersistUtils.generateUuid();
        usageFilter.setId(filterId);
        usageFilter.setScenarioId(scenarioId);
        scenarioUsageFilterRepository.insert(usageFilter);
        Set<Long> accountNumbers = usageFilter.getRhAccountNumbers();
        if (CollectionUtils.isNotEmpty(accountNumbers)) {
            scenarioUsageFilterRepository.insertRhAccountNumbers(filterId, accountNumbers);
        }
        Set<String> usageBatchesIds = usageFilter.getUsageBatchesIds();
        if (CollectionUtils.isNotEmpty(usageBatchesIds)) {
            scenarioUsageFilterRepository.insertUsageBatchesIds(filterId, usageBatchesIds);
        }
    }

    @Override
    @Transactional
    public void removeByScenarioId(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        scenarioUsageFilterRepository.deleteByScenarioId(scenarioId);
    }

    @Override
    public ScenarioUsageFilter getByScenarioId(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        return scenarioUsageFilterRepository.findByScenarioId(scenarioId);
    }
}
