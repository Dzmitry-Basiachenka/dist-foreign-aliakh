package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of Usage archive repository.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/08/18
 *
 * @author Ihar Suvorau
 */
@Repository
public class UsageArchiveRepository extends BaseRepository implements IUsageArchiveRepository {

    private static final String PAGEABLE_KEY = "pageable";
    private static final String SORT_KEY = "sort";
    private static final String SEARCH_VALUE_KEY = "searchValue";
    private static final String SCENARIO_ID_KEY = "scenarioId";

    @Override
    public void insert(Usage usage) {
        insert("IUsageArchiveMapper.insert", Objects.requireNonNull(usage));
    }

    @Override
    public List<RightsholderTotalsHolder> findRightsholderTotalsHoldersByScenarioId(String scenarioId,
                                                                                    String searchValue,
                                                                                    Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageArchiveMapper.findRightsholderTotalsHoldersByScenarioId", parameters);
    }

    @Override
    public int findRightsholderTotalsHolderCountByScenarioId(String scenarioId, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        return selectOne("IUsageArchiveMapper.findRightsholderTotalsHolderCountByScenarioId", parameters);
    }

    @Override
    public List<UsageDto> findByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber,
                                                             String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(5);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put(PAGEABLE_KEY, pageable);
        parameters.put(SORT_KEY, sort);
        return selectList("IUsageArchiveMapper.findByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public int findCountByScenarioIdAndRhAccountNumber(String scenarioId, Long accountNumber, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("accountNumber", Objects.requireNonNull(accountNumber));
        parameters.put(SCENARIO_ID_KEY, Objects.requireNonNull(scenarioId));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        return selectOne("IUsageArchiveMapper.findCountByScenarioIdAndRhAccountNumber", parameters);
    }

    @Override
    public void writeScenarioUsagesCsvReport(String scenarioId, PipedOutputStream pipedOutputStream) {
        Objects.requireNonNull(pipedOutputStream);
        try (ScenarioUsagesCsvReportHandler handler = new ScenarioUsagesCsvReportHandler(pipedOutputStream)) {
            if (Objects.nonNull(scenarioId)) {
                getTemplate().select("IUsageArchiveMapper.findDtoByScenarioId", scenarioId, handler);
            }
        } catch (IOException e) {
            throw new RupRuntimeException(e);
        }
    }

    @Override
    public void deleteByScenarioId(String scenarioId) {
        delete("IUsageArchiveMapper.deleteByScenarioId", Objects.requireNonNull(scenarioId));
    }

    @Override
    public Map<Long, String> findDetailIdToIdMap(List<Long> detailIds) {
        MapResultHandler handler = new MapResultHandler();
        Objects.requireNonNull(detailIds);
        Iterables.partition(detailIds, 32000)
            .forEach(
                partition -> getTemplate().select("IUsageArchiveMapper.findDetailIdToUsageIdMap", partition, handler));
        return handler.getResult();
    }

    @Override
    public void updatePaidInfo(PaidUsage usage) {
        update("IUsageArchiveMapper.updatePaidInfo", Objects.requireNonNull(usage));
    }

    @Override
    public void updateStatus(Set<String> usageIds, UsageStatusEnum status) {
        checkArgument(CollectionUtils.isNotEmpty(usageIds));
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(3);
        parameters.put("status", Objects.requireNonNull(status));
        parameters.put("updateUser", StoredEntity.DEFAULT_USER);
        usageIds.forEach(usageId -> {
            parameters.put("usageId", usageId);
            update("IUsageArchiveMapper.updateStatusById", parameters);
        });
    }

    @Override
    public List<PaidUsage> findByIdAndStatus(List<String> usageIds, UsageStatusEnum status) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("status", status);
        parameters.put("usageIds", Objects.requireNonNull(usageIds));
        return selectList("IUsageArchiveMapper.findByIdAndStatus", parameters);
    }

    @Override
    public List<String> findPaidIds() {
        return selectList("IUsageArchiveMapper.findPaidIds", UsageStatusEnum.PAID);
    }

    /**
     * Handler to collect data into map. MyBatis returns keys in different case depending on the database driver,
     * so both upper case and lower case are used to get value from resultContext.
     */
    private static class MapResultHandler implements ResultHandler<Map<String, String>> {

        private final Map<Long, String> result = Maps.newHashMap();

        @Override
        public void handleResult(ResultContext<? extends Map<String, String>> resultContext) {
            Map<String, String> object = resultContext.getResultObject();
            String detailId = object.get("detail_id");
            if (null != detailId) {
                result.put(Long.valueOf(detailId), object.get("df_usage_archive_uid"));
            } else {
                result.put(Long.valueOf(object.get("DETAIL_ID")), object.get("DF_USAGE_ARCHIVE_UID"));
            }
        }

        private Map<Long, String> getResult() {
            return result;
        }
    }
}
