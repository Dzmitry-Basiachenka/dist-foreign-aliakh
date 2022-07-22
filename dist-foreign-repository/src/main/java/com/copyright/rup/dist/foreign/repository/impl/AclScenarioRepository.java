package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioRepository;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
public class AclScenarioRepository extends BaseRepository implements IAclScenarioRepository {

    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String SCENARIO_ID_KEY = "scenarioId";

    @Override
    public List<AclScenario> findAll() {
        return selectList("IAclScenarioMapper.findAll");
    }

    @Override
    public int findCountByName(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IAclScenarioMapper.findCountByName", name);
    }

    @Override
    public AclScenarioDto findWithAmountsAndLastAction(String scenarioId) {
        return selectOne("IAclScenarioMapper.findWithAmountsAndLastAction", Objects.requireNonNull(scenarioId));
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
        parameters.put("updateUser", Objects.requireNonNull(userName));
        insert("IAclScenarioMapper.insertAclScenarioUsageAgeWeight", parameters);
    }

    @Override
    public void insertAclScenarioLicenseeClass(DetailLicenseeClass licenseeClass, String scenarioId, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("licenseeClass", Objects.requireNonNull(licenseeClass));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("createUser", Objects.requireNonNull(userName));
        parameters.put("updateUser", Objects.requireNonNull(userName));
        insert("IAclScenarioMapper.insertAclScenarioLicenseeClass", parameters);
    }

    @Override
    public void insertAclScenarioPubTypeWeight(AclPublicationType publicationType, String scenarioId, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("publicationType", Objects.requireNonNull(publicationType));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put("createUser", Objects.requireNonNull(userName));
        parameters.put("updateUser", Objects.requireNonNull(userName));
        insert("IAclScenarioMapper.insertAclScenarioPubTypeWeight", parameters);
    }

    @Override
    public AclScenario findById(String scenarioId) {
        return selectOne("IAclScenarioMapper.findScenarioById", scenarioId);
    }

    @Override
    public List<AclScenarioDetailDto> findByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId,
                                                                         String searchValue, Pageable pageable,
                                                                         Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IAclScenarioMapper.findByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(Long accountNumber, String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, escapeSqlLikePattern(searchValue));
        return selectOne("IAclScenarioMapper.findCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public List<String> findScenarioNamesByFundPoolId(String fundPoolId) {
        return selectList("IAclScenarioMapper.findScenarioNamesByFundPoolId", fundPoolId);
    }

    @Override
    public List<String> findScenarioNamesByGrantSetId(String grantSetId) {
        return selectList("IAclScenarioMapper.findScenarioNamesByGrantSetId", grantSetId);
    }
}
