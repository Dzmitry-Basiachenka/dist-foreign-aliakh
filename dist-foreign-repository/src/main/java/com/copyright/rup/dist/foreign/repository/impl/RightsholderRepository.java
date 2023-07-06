package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.impl.CommonRightsholderRepository;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTypeOfUsePair;
import com.copyright.rup.dist.foreign.repository.api.IRightsholderRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IRightsholderRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/07/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 */
@Repository
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class RightsholderRepository extends CommonRightsholderRepository implements IRightsholderRepository {

    @Override
    public List<Rightsholder> findRros(String productFamily) {
        return selectList("RightsholderMapper.findRros", Objects.requireNonNull(productFamily));
    }

    @Override
    public Set<Long> findAccountNumbers() {
        return selectSet("RightsholderMapper.findAccountNumbers");
    }

    @Override
    public void deleteByAccountNumber(Long accountNumber) {
        delete("RightsholderMapper.deleteByAccountNumber", checkNotNull(accountNumber));
    }

    @Override
    public List<Rightsholder> findAllWithSearch(String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("searchValue", searchValue);
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("RightsholderMapper.findAllWithSearch", parameters);
    }

    @Override
    public int findCountWithSearch(String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(1);
        parameters.put("searchValue", searchValue);
        return selectOne("RightsholderMapper.findCountWithSearch", parameters);
    }

    @Override
    public List<Rightsholder> findByScenarioId(String scenarioId) {
        return selectList("RightsholderMapper.findByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<RightsholderTypeOfUsePair> findByAclScenarioId(String scenarioId) {
        return selectList("RightsholderMapper.findByAclScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    protected String getPrefix() {
        return "df";
    }

    @Override
    public List<RightsholderPayeePair> findRhPayeePairByScenarioId(String scenarioId) {
        return selectList("RightsholderMapper.findRhPayeeByScenarioIds", scenarioId);
    }
}
