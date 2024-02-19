package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderDiscrepancyRepository;

import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    private static final long serialVersionUID = -3062191312806775784L;
    private static final String STATUS_KEY = "status";
    private static final String SCENARIO_ID_KEY = "scenarioId";

    @Override
    public void insertAll(List<RightsholderDiscrepancy> rightsholderDiscrepancies, String scenarioId) {
        checkArgument(CollectionUtils.isNotEmpty(rightsholderDiscrepancies));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        rightsholderDiscrepancies.forEach(rightsholderDiscrepancy -> {
            parameters.put("rightsholderDiscrepancy", rightsholderDiscrepancy);
            insert("IRightsholderDiscrepancyMapper.insert", parameters);
        });
    }

    @Override
    public int findCountByScenarioIdAndStatus(String scenarioId, RightsholderDiscrepancyStatusEnum status) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        return selectOne("IRightsholderDiscrepancyMapper.findCountByScenarioIdAndStatus", parameters);
    }

    @Override
    public List<Long> findProhibitedAccountNumbers(String scenarioId) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(STATUS_KEY, RightsholderDiscrepancyStatusEnum.DRAFT);
        return selectList("IRightsholderDiscrepancyMapper.findProhibitedAccountNumbers", parameters);
    }

    @Override
    public List<RightsholderDiscrepancy> findByScenarioIdAndStatus(String scenarioId,
                                                                   RightsholderDiscrepancyStatusEnum status,
                                                                   Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("statuses", Set.of(Objects.requireNonNull(status)));
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("IRightsholderDiscrepancyMapper.findByScenarioIdAndStatuses", parameters);
    }

    @Override
    public void deleteByScenarioIdAndStatus(String scenarioId, RightsholderDiscrepancyStatusEnum status) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(STATUS_KEY, Objects.requireNonNull(status));
        delete("IRightsholderDiscrepancyMapper.deleteByScenarioIdAndStatus", parameters);
    }

    @Override
    public void deleteByScenarioId(String scenarioId) {
        delete("IRightsholderDiscrepancyMapper.deleteByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public void approveByScenarioId(String scenarioId) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(STATUS_KEY, RightsholderDiscrepancyStatusEnum.APPROVED);
        update("IRightsholderDiscrepancyMapper.approveByScenarioId", parameters);
    }
}
