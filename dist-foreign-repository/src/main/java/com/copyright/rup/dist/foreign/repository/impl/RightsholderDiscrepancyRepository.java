package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderDiscrepancyRepository;

import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IRightsholderDiscrepancyRepository}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/18/2018
 *
 * @author Ihar Suvorau
 */
@Repository
public class RightsholderDiscrepancyRepository extends BaseRepository implements IRightsholderDiscrepancyRepository {

    @Override
    public void insertAll(List<RightsholderDiscrepancy> rightsholderDiscrepancies, String scenarioId) {
        checkArgument(CollectionUtils.isNotEmpty(rightsholderDiscrepancies));
        checkArgument(StringUtils.isNotBlank(scenarioId));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("scenarioId", scenarioId);
        rightsholderDiscrepancies.forEach(rightsholderDiscrepancy -> {
            parameters.put("rightsholderDiscrepancy", rightsholderDiscrepancy);
            insert("IRightsholderDiscrepancyMapper.insert", parameters);
        });
    }

    @Override
    public List<RightsholderDiscrepancy> findByScenarioId(String scenarioId, Pageable pageable, Sort sort) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("scenarioId", scenarioId);
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("IRightsholderDiscrepancyMapper.findByScenarioId", parameters);
    }

    @Override
    public void deleteByScenarioId(String scenarioId) {
        delete("IRightsholderDiscrepancyMapper.deleteByScenarioId", scenarioId);
    }
}
