package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchStatusRepository;

import com.google.common.collect.Maps;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link IUsageBatchStatusRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 02/10/2021
 *
 * @author Ihar Suvorau
 */
@Repository
public class UsageBatchStatusRepository extends BaseRepository implements IUsageBatchStatusRepository {

    @Override
    public List<UsageBatchStatus> findUsageBatchStatusesFas(Set<String> batchIds) {
        return selectList("IUsageBatchStatusMapper.findUsageBatchStatusesFas", batchIds);
    }

    @Override
    public List<UsageBatchStatus> findUsageBatchStatusesFas2() {
        return Collections.emptyList();
    }

    @Override
    public List<UsageBatchStatus> findUsageBatchStatusesNts() {
        return Collections.emptyList();
    }

    @Override
    public List<UsageBatchStatus> findUsageBatchStatusesAacl() {
        return Collections.emptyList();
    }

    @Override
    public List<UsageBatchStatus> findUsageBatchStatusesSal() {
        return Collections.emptyList();
    }

    @Override
    public Set<String> findUsageBatchIdsByProductFamilyAndStartDateFrom(String productFamily, LocalDate startDate) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(2);
        params.put("productFamily", productFamily);
        params.put("startDate", startDate);
        return new HashSet<>(
            selectList("IUsageBatchStatusMapper.findUsageBatchIdsByProductFamilyAndStartDateFrom", params));
    }
}
