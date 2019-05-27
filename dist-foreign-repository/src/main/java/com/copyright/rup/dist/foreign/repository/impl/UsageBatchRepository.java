package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;

import com.google.common.collect.Maps;

import com.google.common.collect.Table;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IUsageBatchRepository} for MyBatis.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/02/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
@Repository
public class UsageBatchRepository extends BaseRepository implements IUsageBatchRepository {

    private static final EnumSet<UsageStatusEnum> PROCESSED_NTS_BATCH_USAGE_STATUSES = EnumSet.of(
        UsageStatusEnum.ELIGIBLE, UsageStatusEnum.UNCLASSIFIED, UsageStatusEnum.LOCKED, UsageStatusEnum.NTS_EXCLUDED);

    @Override
    public void insert(UsageBatch usageBatch) {
        insert("IUsageBatchMapper.insert", checkNotNull(usageBatch));
    }

    @Override
    public List<Integer> findFiscalYearsByProductFamily(String productFamily) {
        return selectList("IUsageBatchMapper.findFiscalYearsByProductFamily", Objects.requireNonNull(productFamily));
    }

    @Override
    public List<UsageBatch> findAll() {
        return selectList("IUsageBatchMapper.findAll");
    }

    @Override
    public List<UsageBatch> findByProductFamily(String productFamily) {
        return selectList("IUsageBatchMapper.findByProductFamily", Objects.requireNonNull(productFamily));
    }

    @Override
    public List<UsageBatch> findUsageBatchesForPreServiceFeeFunds() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("productFamilies", FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET);
        params.put("status", UsageStatusEnum.NTS_WITHDRAWN);
        return selectList("IUsageBatchMapper.findUsageBatchesForPreServiceFeeFunds", params);
    }

    @Override
    public int findCountByName(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IUsageBatchMapper.findCountByName", name);
    }

    @Override
    public void deleteUsageBatch(String batchId) {
        checkArgument(StringUtils.isNotBlank(batchId));
        delete("IUsageBatchMapper.deleteUsageBatch", batchId);
    }

    @Override
    public List<String> findBatchNamesWithoutUsagesForClassification(Set<String> batchIds, String classification) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("batchIds", Objects.requireNonNull(batchIds));
        params.put("classification", classification);
        return selectList("IUsageBatchMapper.findBatchNamesWithoutUsagesForClassification", params);
    }

    @Override
    public List<String> findProcessingBatchesNames(Set<String> batchesIds) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("batchesIds", Objects.requireNonNull(batchesIds));
        params.put("statuses", Objects.requireNonNull(PROCESSED_NTS_BATCH_USAGE_STATUSES));
        return selectList("IUsageBatchMapper.findProcessingBatchesNames", params);
    }

    @Override
    public Map<String, String> findBatchesNamesToScenariosNames(Set<String> batchesIds) {
        BatchesNamesToScenariosNamesResultHandler handler = new BatchesNamesToScenariosNamesResultHandler();
        getTemplate().select("IUsageBatchMapper.findBatchesNamesToScenariosNames",
            Objects.requireNonNull(batchesIds), handler);
        return handler.getBatchesNamesToScenariosNames();
    }

    @Override
    public Table<String, String, Long> findBatchNameUsageIdWrWrkInstTableByStatus(UsageStatusEnum status) {
        BatchNameToUsageIdWrWrkInstResultHandler handler = new BatchNameToUsageIdWrWrkInstResultHandler();
        getTemplate().select("IUsageBatchMapper.findBatchNameUsageIdWrWrkInstTableByStatus",
            Objects.requireNonNull(status), handler);
        return handler.getBatchNameToWrWrkInstsMap();
    }

    /**
     * Finds usage batch by provided name.
     *
     * @param name usage batch name
     * @return found {@link UsageBatch} instance
     */
    UsageBatch findByName(String name) {
        checkArgument(StringUtils.isNotBlank(name));
        return selectOne("IUsageBatchMapper.findByName", name);
    }
}
