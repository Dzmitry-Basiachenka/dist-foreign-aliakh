package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IScenarioRepository} for MyBatis.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 * @author Mikalai Bezmen
 */
@Repository
public class ScenarioRepository extends BaseRepository implements IScenarioRepository {

    @Override
    public void insert(Scenario scenario) {
        insert("IScenarioMapper.insert", checkNotNull(scenario));
    }

    @Override
    public void insertNtsScenarioAndAddUsages(Scenario scenario, UsageFilter filter) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        params.put("scenario", Objects.requireNonNull(scenario));
        params.put("filter", Objects.requireNonNull(filter));
        params.put("status", UsageStatusEnum.LOCKED);
        params.put("createUser", scenario.getCreateUser());
        params.put("updateUser", scenario.getUpdateUser());
        insert("IScenarioMapper.insertNtsScenarioAndAddUsages", params);
    }

    @Override
    public void refresh(Scenario scenario) {
        update("IScenarioMapper.refresh", Objects.requireNonNull(scenario));
    }

    @Override
    public int findCountByName(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IScenarioMapper.findCountByName", name);
    }

    @Override
    public List<Scenario> findByProductFamily(String productFamily) {
        return selectList("IScenarioMapper.findByProductFamily", Objects.requireNonNull(productFamily));
    }

    @Override
    public List<Scenario> findByProductFamiliesAndStatuses(Set<String> productFamilies,
                                                           Set<ScenarioStatusEnum> statuses) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("statuses", Objects.requireNonNull(statuses));
        params.put("productFamilies", Objects.requireNonNull(productFamilies));
        return selectList("IScenarioMapper.findByProductFamiliesAndStatuses", params);
    }

    @Override
    public List<String> findNamesByUsageBatchId(String usageBatchId) {
        checkArgument(StringUtils.isNotBlank(usageBatchId));
        return selectList("IScenarioMapper.findNamesByUsageBatchId", usageBatchId);
    }

    @Override
    public String findNameByNtsFundPoolId(String fundPoolId) {
        checkArgument(StringUtils.isNotBlank(fundPoolId));
        return selectOne("IScenarioMapper.findNameByNtsFundPoolId", fundPoolId);
    }

    @Override
    public String findNameByAaclFundPoolId(String fundPoolId) {
        checkArgument(StringUtils.isNotBlank(fundPoolId));
        return selectOne("IScenarioMapper.findNameByAaclFundPoolId", fundPoolId);
    }

    @Override
    public String findNameBySalFundPoolId(String fundPoolId) {
        checkArgument(StringUtils.isNotBlank(fundPoolId));
        return selectOne("IScenarioMapper.findNameBySalFundPoolId", fundPoolId);
    }

    @Override
    public void remove(String scenarioId) {
        checkArgument(StringUtils.isNotBlank(scenarioId));
        delete("IScenarioMapper.remove", scenarioId);
    }

    @Override
    public List<Rightsholder> findSourceRros(String scenarioId) {
        return selectList("IScenarioMapper.findSourceRros", scenarioId);
    }

    @Override
    public Scenario findWithAmountsAndLastAction(String scenarioId) {
        return selectOne("IScenarioMapper.findWithAmountsAndLastAction", Objects.requireNonNull(scenarioId));
    }

    @Override
    public Scenario findArchivedWithAmountsAndLastAction(String scenarioId) {
        return selectOne("IScenarioMapper.findArchivedWithAmountsAndLastAction", Objects.requireNonNull(scenarioId));
    }

    @Override
    public void updateStatus(Scenario scenario) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("status", Objects.requireNonNull(scenario.getStatus()));
        params.put("scenarioId", Objects.requireNonNull(scenario.getId()));
        params.put("updateUser", StoredEntity.DEFAULT_USER);
        update("IScenarioMapper.updateStatusById", params);
    }

    @Override
    public void updateStatus(List<String> scenarioIds, ScenarioStatusEnum status) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("status", Objects.requireNonNull(status));
        params.put("updateUser", StoredEntity.DEFAULT_USER);
        scenarioIds.forEach(scenarioId -> {
            params.put("scenarioId", scenarioId);
            update("IScenarioMapper.updateStatusById", params);
        });
    }

    @Override
    public List<RightsholderPayeePair> findRightsholdersByScenarioIdAndSourceRro(String scenarioId,
                                                                                 Long rroAccountNumber) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("scenarioId", Objects.requireNonNull(scenarioId));
        params.put("rroAccountNumber", Objects.requireNonNull(rroAccountNumber));
        return selectList("IScenarioMapper.findRightsholdersByScenarioIdAndSourceRro", params);
    }

    @Override
    public List<String> findIdsForArchiving() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("usageStatus", UsageStatusEnum.ARCHIVED);
        params.put("scenarioStatus", ScenarioStatusEnum.SENT_TO_LM);
        return selectList("IScenarioMapper.findIdsForArchiving", params);
    }
}
