package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import org.springframework.stereotype.Repository;

/**
 * Implementation of Usage repository.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/02/17
 *
 * @author Darya Baraukova
 */
@Repository("df.repository.usageRepository")
public class UsageRepository extends BaseRepository implements IUsageRepository {

    @Override
    public int insertUsage(Usage usage) {
        return insert("IUsageMapper.insertUsage", checkNotNull(usage));
    }
}
