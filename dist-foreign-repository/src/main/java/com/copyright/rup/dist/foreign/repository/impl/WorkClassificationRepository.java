package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.repository.api.IWorkClassificationRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IWorkClassificationRepository}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/19
 *
 * @author Pavel Liakh
 */
@Repository
public class WorkClassificationRepository extends BaseRepository implements IWorkClassificationRepository {

    private static final long serialVersionUID = 9159369427467116091L;
    private static final String SEARCH_VALUE_KEY = "searchValue";

    @Override
    public void insertOrUpdate(WorkClassification workClassification) {
        insert("IWorkClassificationMapper.insertOrUpdate", Objects.requireNonNull(workClassification));
    }

    @Override
    public void deleteByWrWrkInst(Long wrWrkInst) {
        delete("IWorkClassificationMapper.deleteByWrWrkInst", Objects.requireNonNull(wrWrkInst));
    }

    @Override
    public String findClassificationByWrWrkInst(Long wrWrkInst) {
        return selectOne("IWorkClassificationMapper.findClassificationByWrWrkInst", Objects.requireNonNull(wrWrkInst));
    }

    @Override
    public List<WorkClassification> findByBatchIds(Set<String> batchesIds, String searchValue,
                                                   Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put("batchesIds", Objects.requireNonNull(batchesIds));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("IWorkClassificationMapper.findByBatchIds", parameters);
    }

    @Override
    public int findCountByBatchIds(Set<String> batchesIds, String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put("batchesIds", Objects.requireNonNull(batchesIds));
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        return selectOne("IWorkClassificationMapper.findCountByBatchIds", parameters);
    }

    @Override
    public List<WorkClassification> findBySearch(String searchValue, Pageable pageable, Sort sort) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(4);
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put("productFamilies", FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET);
        parameters.put("pageable", pageable);
        parameters.put("sort", sort);
        return selectList("IWorkClassificationMapper.findBySearch", parameters);
    }

    @Override
    public int findCountBySearch(String searchValue) {
        Map<String, Object> parameters = Maps.newHashMapWithExpectedSize(2);
        parameters.put(SEARCH_VALUE_KEY, searchValue);
        parameters.put("productFamilies", FdaConstants.FAS_FAS2_PRODUCT_FAMILY_SET);
        return selectOne("IWorkClassificationMapper.findCountBySearch", parameters);
    }
}
