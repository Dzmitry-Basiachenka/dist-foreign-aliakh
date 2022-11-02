package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclUsageRepository;

import com.copyright.rup.dist.foreign.repository.impl.csv.acl.AclUsageCsvReportHandler;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IAclUsageRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Aliaksandr Liakh
 */
@Repository
public class AclUsageRepository extends AclBaseRepository implements IAclUsageRepository {

    private static final List<String> ELIGIBLE_GRANT_STATUSES = Arrays.asList("Print&Digital", "Different RH");
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String UPDATE_USER = "updateUser";
    private static final String CREATE_USER = "createUser";

    @Override
    public List<String> populateAclUsages(String usageBatchId, Set<Integer> periods, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put("usageBatchId", Objects.requireNonNull(usageBatchId));
        parameters.put("periods", Objects.requireNonNull(periods));
        parameters.put(UPDATE_USER, Objects.requireNonNull(userName));
        parameters.put(CREATE_USER, userName);
        return selectList("IAclUsageMapper.populateAclUsages", parameters);
    }

    @Override
    public void update(AclUsageDto aclUsageDto) {
        insert("IAclUsageMapper.update", Objects.requireNonNull(aclUsageDto));
    }

    @Override
    public List<AclUsageDto> findByIds(List<String> usageIds) {
        return selectList("IAclUsageMapper.findByIds", Objects.requireNonNull(usageIds));
    }

    @Override
    public int findCountByFilter(AclUsageFilter filter) {
        return selectOne("IAclUsageMapper.findCountByFilter",
            ImmutableMap.of("filter", escapeSqlLikePattern(Objects.requireNonNull(filter))));
    }

    @Override
    public List<AclUsageDto> findDtosByFilter(AclUsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("filter", escapeSqlLikePattern(Objects.requireNonNull(filter)));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IAclUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public List<Integer> findPeriods() {
        return selectList("IAclUsageMapper.findPeriods");
    }

    @Override
    public boolean usageExistForLicenseeClassesAndTypeOfUse(String batchId, String grantSetId,
                                                            Set<Integer> licenseeClassIds, String typeOfUse) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put("batchId", escapeSqlLikePattern(Objects.requireNonNull(batchId)));
        parameters.put("grantSetId", grantSetId);
        parameters.put("licenseeClassIds", licenseeClassIds);
        parameters.put("typeOfUse", typeOfUse);
        parameters.put("grantStatuses", ELIGIBLE_GRANT_STATUSES);
        return selectOne("IAclUsageMapper.usageExistForLicenseeClassesAndTypeOfUse", parameters);
    }

    @Override
    public List<UsageAge> findDefaultUsageAgesWeights() {
        return selectList("IAclUsageMapper.findDefaultUsageAgesWeights");
    }

    @Override
    public int findCountInvalidUsages(String batchId, String grantSetId, Integer distributionPeriod,
                                      List<Integer> periodPriors) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
        params.put("batchId", Objects.requireNonNull(batchId));
        params.put("grantSetId", Objects.requireNonNull(grantSetId));
        params.put("periodPriors", Objects.requireNonNull(periodPriors));
        params.put("distributionPeriod", Objects.requireNonNull(distributionPeriod));
        params.put("grantStatuses", ELIGIBLE_GRANT_STATUSES);
        return selectOne("IAclUsageMapper.findCountInvalidUsages", params);
    }

    @Override
    public void writeInvalidUsagesCsvReport(String batchId, String grantSetId, Integer distributionPeriod,
                                            List<Integer> periodPriors, OutputStream outputStream) {
        try (AclUsageCsvReportHandler handler = new AclUsageCsvReportHandler(Objects.requireNonNull(outputStream))) {
            Map<String, Object> params = Maps.newHashMapWithExpectedSize(5);
            params.put("batchId", Objects.requireNonNull(batchId));
            params.put("grantSetId", Objects.requireNonNull(grantSetId));
            params.put("periodPriors", Objects.requireNonNull(periodPriors));
            params.put("distributionPeriod", Objects.requireNonNull(distributionPeriod));
            params.put("grantStatuses", ELIGIBLE_GRANT_STATUSES);
            getTemplate().select("IAclUsageMapper.findInvalidUsagesDtos", Objects.requireNonNull(params), handler);
        }
    }

    @Override
    public List<String> copyAclUsages(String sourceUsageBatchId, String targetUsageBatchId, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put("sourceUsageBatchId", Objects.requireNonNull(sourceUsageBatchId));
        parameters.put("targetUsageBatchId", Objects.requireNonNull(targetUsageBatchId));
        parameters.put(CREATE_USER, Objects.requireNonNull(userName));
        parameters.put(UPDATE_USER, userName);
        return selectList("IAclUsageMapper.copyAclUsages", parameters);
    }

    @Override
    public void deleteByUsageBatchId(String usageBatchId) {
        delete("IAclUsageMapper.deleteByUsageBatchId", Objects.requireNonNull(usageBatchId));
    }

    private AclUsageFilter escapeSqlLikePattern(AclUsageFilter filter) {
        AclUsageFilter filterCopy = new AclUsageFilter(filter);
        filterCopy.setUsageDetailIdExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getUsageDetailIdExpression()));
        filterCopy.setSystemTitleExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getSystemTitleExpression()));
        filterCopy.setSurveyCountryExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getSurveyCountryExpression()));
        return filterCopy;
    }
}
