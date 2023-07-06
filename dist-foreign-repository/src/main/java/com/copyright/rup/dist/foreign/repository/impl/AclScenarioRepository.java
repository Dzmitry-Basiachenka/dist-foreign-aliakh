package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclScenarioFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import io.micrometer.core.annotation.Timed;

/**
 * Implementation of {@link IAclScenarioRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/24/2022
 *
 * @author Dzmitry Basiachenka
 */
@Repository
@Timed(percentiles = {0, 0.25, 0.5, 0.75, 0.95, 0.99})
public class AclScenarioRepository extends BaseRepository implements IAclScenarioRepository {

    private static final String SCENARIO_ID_KEY = "scenarioId";
    private static final String UPDATE_USER_KEY = "updateUser";

    @Override
    public List<AclScenario> findByFilter(AclScenarioFilter filter) {
        return selectList("IAclScenarioMapper.findByFilter", Objects.requireNonNull(filter));
    }

    @Override
    public List<AclScenario> findByPeriod(Integer period) {
        return selectList("IAclScenarioMapper.findByPeriod", period);
    }

    @Override
    public List<Integer> findPeriods() {
        return selectList("IAclScenarioMapper.findPeriods");
    }

    @Override
    public int findCountByName(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IAclScenarioMapper.findCountByName", name);
    }

    @Override
    public void insertAclScenario(AclScenario aclScenario) {
        insert("IAclScenarioMapper.insert", checkNotNull(aclScenario));
    }

    @Override
    public void insertAclScenarioUsageAgeWeight(UsageAge usageAge, String scenarioId, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("usageAge", Objects.requireNonNull(usageAge));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("createUser", Objects.requireNonNull(userName));
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        insert("IAclScenarioMapper.insertAclScenarioUsageAgeWeight", parameters);
    }

    @Override
    public void insertAclScenarioLicenseeClass(DetailLicenseeClass licenseeClass, String scenarioId, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("licenseeClass", Objects.requireNonNull(licenseeClass));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("createUser", Objects.requireNonNull(userName));
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        insert("IAclScenarioMapper.insertAclScenarioLicenseeClass", parameters);
    }

    @Override
    public void insertAclScenarioPubTypeWeight(AclPublicationType publicationType, String scenarioId, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put("publicationType", Objects.requireNonNull(publicationType));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("createUser", Objects.requireNonNull(userName));
        parameters.put(UPDATE_USER_KEY, Objects.requireNonNull(userName));
        insert("IAclScenarioMapper.insertAclScenarioPubTypeWeight", parameters);
    }

    @Override
    public AclScenario findById(String scenarioId) {
        return selectOne("IAclScenarioMapper.findScenarioById", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<String> findScenarioNamesByUsageBatchId(String usageBatchId) {
        return selectList("IAclScenarioMapper.findScenarioNamesByUsageBatchId", Objects.requireNonNull(usageBatchId));
    }

    @Override
    public List<String> findScenarioNamesByFundPoolId(String fundPoolId) {
        return selectList("IAclScenarioMapper.findScenarioNamesByFundPoolId", Objects.requireNonNull(fundPoolId));
    }

    @Override
    public List<String> findScenarioNamesByGrantSetId(String grantSetId) {
        return selectList("IAclScenarioMapper.findScenarioNamesByGrantSetId", Objects.requireNonNull(grantSetId));
    }

    @Override
    public List<UsageAge> findUsageAgeWeightsByScenarioId(String scenarioId) {
        return selectList("IAclScenarioMapper.findUsageAgeWeightsByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<AclPublicationType> findAclPublicationTypesByScenarioId(String scenarioId) {
        return selectList("IAclScenarioMapper.findAclPublicationTypesByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<DetailLicenseeClass> findDetailLicenseeClassesByScenarioId(String scenarioId) {
        return selectList("IAclScenarioMapper.findDetailLicenseeClassesByScenarioId",
            Objects.requireNonNull(scenarioId));
    }

    @Override
    public void deleteScenario(String scenarioId) {
        delete("IAclScenarioMapper.delete", Objects.requireNonNull(scenarioId));
    }

    @Override
    public void deleteScenarioData(String scenarioId) {
        delete("IAclScenarioMapper.deleteScenarioData", Objects.requireNonNull(scenarioId));
    }

    @Override
    public List<Scenario> findAclScenariosByStatuses(Set<ScenarioStatusEnum> statuses) {
        return selectList("IAclScenarioMapper.findAclScenariosByStatuses", Objects.requireNonNull(statuses));
    }

    @Override
    public boolean submittedScenarioExistWithLicenseTypeAndPeriod(String licenseType, Integer period) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("licenseType", Objects.requireNonNull(licenseType));
        parameters.put("period", Objects.requireNonNull(period));
        return selectOne("IAclScenarioMapper.scenarioExistWithLicenseTypeAndPeriod", parameters);
    }

    @Override
    public void updateStatus(AclScenario scenario) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(3);
        params.put("status", Objects.requireNonNull(scenario.getStatus()));
        params.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenario.getId()));
        params.put(UPDATE_USER_KEY, Objects.requireNonNull(scenario.getUpdateUser()));
        update("IAclScenarioMapper.updateStatusById", params);
    }
}
