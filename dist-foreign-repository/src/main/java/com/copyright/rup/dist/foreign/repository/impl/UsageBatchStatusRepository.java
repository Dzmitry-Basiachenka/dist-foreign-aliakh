package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatchStatus;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchStatusRepository;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

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
    public List<UsageBatchStatus> findUsageBatchStatusesFas() {
        return selectList("IUsageBatchStatusMapper.findUsageBatchStatusesFas", FdaConstants.FAS_PRODUCT_FAMILY);
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
}
