package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

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
    public UsageBatch findById(String batchId) {
        return selectOne("IUsageBatchMapper.findById", Objects.requireNonNull(batchId));
    }

    @Override
    public List<UsageBatch> findByProductFamily(String productFamily) {
        return selectList("IUsageBatchMapper.findByProductFamily", Objects.requireNonNull(productFamily));
    }

    @Override
    public List<UsageBatch> findUsageBatchesForNtsFundPool() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("productFamilies", FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET);
        params.put("status", UsageStatusEnum.NTS_WITHDRAWN);
        return selectList("IUsageBatchMapper.findUsageBatchesForNtsFundPool", params);
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
    public List<String> findIneligibleForScenarioBatchNames(Set<String> batchesIds,
                                                            Set<UsageStatusEnum> eligibleStatuses) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("batchesIds", Objects.requireNonNull(batchesIds));
        params.put("eligibleStatuses", Objects.requireNonNull(eligibleStatuses));
        return selectList("IUsageBatchMapper.findIneligibleForScenarioBatchNames", params);
    }

    @Override
    public Map<String, String> findBatchesNamesToScenariosNames(Set<String> batchesIds) {
        BatchesNamesToScenariosNamesResultHandler handler = new BatchesNamesToScenariosNamesResultHandler();
        getTemplate().select("IUsageBatchMapper.findBatchesNamesToScenariosNames",
            Objects.requireNonNull(batchesIds), handler);
        return handler.getBatchesNamesToScenariosNames();
    }

    @Override
    public List<String> findBatchNamesForRightsAssignment() {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("productFamilies", FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET);
        params.put("status", UsageStatusEnum.RH_NOT_FOUND);
        return selectList("IUsageBatchMapper.findBatchNamesForRightsAssignment", params);
    }

    @Override
    public List<UsageBatch> findSalNotAttachedToScenario() {
        return selectList("IUsageBatchMapper.findSalNotAttachedToScenario", FdaConstants.SAL_PRODUCT_FAMILY);
    }

    @Override
    public List<SalLicensee> findSalLicensees() {
        return selectList("IUsageBatchMapper.findSalLicensees", FdaConstants.SAL_PRODUCT_FAMILY);
    }

    @Override
    public List<Integer> findSalUsagePeriods() {
        return selectList("IUsageBatchMapper.findSalUsagePeriods", FdaConstants.SAL_PRODUCT_FAMILY);
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
