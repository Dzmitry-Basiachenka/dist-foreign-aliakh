package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of UDM usage repository.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/29/21
 *
 * @author Uladzislau Shalamitski
 */
@Repository
public class UdmUsageRepository extends AclBaseRepository implements IUdmUsageRepository {

    private static final int MAX_VARIABLES_COUNT = 32000;
    private static final String FILTER_KEY = "filter";
    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";

    @Override
    public void insert(UdmUsage udmUsage) {
        insert("IUdmUsageMapper.insert", Objects.requireNonNull(udmUsage));
    }

    @Override
    public void update(UdmUsageDto udmUsageDto) {
        insert("IUdmUsageMapper.update", Objects.requireNonNull(udmUsageDto));
    }

    @Override
    public boolean isOriginalDetailIdExist(String originalDetailId) {
        return selectOne("IUdmUsageMapper.isOriginalDetailIdExist", Objects.requireNonNull(originalDetailId));
    }

    @Override
    public List<UdmUsageDto> findDtosByFilter(UdmUsageFilter filter, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter)));
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUdmUsageMapper.findDtosByFilter", parameters);
    }

    @Override
    public int findCountByFilter(UdmUsageFilter filter) {
        return selectOne("IUdmUsageMapper.findCountByFilter",
            ImmutableMap.of(FILTER_KEY, escapeSqlLikePattern(Objects.requireNonNull(filter))));
    }

    @Override
    public List<UdmUsage> findByIds(List<String> udmUsagesIds) {
        return selectList("IUdmUsageMapper.findByIds", Objects.requireNonNull(udmUsagesIds));
    }

    @Override
    public String updateProcessedUsage(UdmUsage udmUsage) {
        return selectOne("IUdmUsageMapper.updateProcessedUsage", Objects.requireNonNull(udmUsage));
    }

    @Override
    public List<String> findIdsByStatus(UsageStatusEnum status) {
        return selectList("IUdmUsageMapper.findIdsByStatus", Objects.requireNonNull(status));
    }

    @Override
    public List<Integer> findPeriods() {
        return selectList("IUdmUsageMapper.findPeriods");
    }

    @Override
    public List<String> findUserNames() {
        return selectList("IUdmUsageMapper.findUserNames");
    }

    @Override
    public List<String> findAssignees() {
        return selectList("IUdmUsageMapper.findAssignees");
    }

    @Override
    public List<String> findPublicationTypes() {
        return selectList("IUdmUsageMapper.findPublicationTypes");
    }

    @Override
    public List<String> findPublicationFormats() {
        return selectList("IUdmUsageMapper.findPublicationFormats");
    }

    private UdmUsageFilter escapeSqlLikePattern(UdmUsageFilter udmUsageFilter) {
        UdmUsageFilter filterCopy = new UdmUsageFilter(udmUsageFilter);
        filterCopy.setReportedTitleExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getReportedTitleExpression()));
        filterCopy.setSystemTitleExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getSystemTitleExpression()));
        filterCopy.setCompanyNameExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getCompanyNameExpression()));
        filterCopy.setSurveyRespondentExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getSurveyRespondentExpression()));
        filterCopy.setSurveyCountryExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getSurveyCountryExpression()));
        filterCopy.setLanguageExpression(
            escapePropertyForMyBatisSqlFragment(filterCopy.getLanguageExpression()));
        filterCopy.setSearchValue(escapeSqlLikePattern(filterCopy.getSearchValue()));
        return filterCopy;
    }

    @Override
    public void updateStatusByIds(Set<String> udmUsageIds, UsageStatusEnum status) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("status", Objects.requireNonNull(status));
        parameters.put("updateUser", StoredEntity.DEFAULT_USER);
        Iterables.partition(Objects.requireNonNull(udmUsageIds), MAX_VARIABLES_COUNT)
            .forEach(partition -> {
                parameters.put("usageIds", partition);
                update("IUdmUsageMapper.updateStatusByIds", parameters);
            });
    }

    @Override
    public void deleteByBatchId(String udmBatchId) {
        delete("IUdmUsageMapper.deleteByBatchId", Objects.requireNonNull(udmBatchId));
    }

    @Override
    public void updateAssignee(Set<String> udmUsageIds, String assignee, String updateUser) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("udmUsageIds", Objects.requireNonNull(udmUsageIds));
        parameters.put("updateUser", Objects.requireNonNull(updateUser));
        parameters.put("assignee", assignee);
        update("IUdmUsageMapper.updateAssignee", parameters);
    }

    @Override
    public Set<String> publishUdmUsagesToBaseline(Integer period, String userName) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("period", Objects.requireNonNull(period));
        parameters.put("createUser", Objects.requireNonNull(userName));
        return new HashSet<>(selectList("IUdmUsageMapper.publishToBaseline", parameters));
    }

    @Override
    public Set<Long> findWrWrkInstPublishedToBaselineUdmUsages(Set<Integer> periods) {
        return new HashSet<>(selectList("IUdmUsageMapper.findWrWrkInstPublishedToBaseline", periods));
    }
}
