package com.copyright.rup.dist.foreign.repository.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.copyright.rup.dist.common.repository.BaseRepository;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of Usage repository.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/02/17
 *
 * @author Darya Baraukova
 */
@Repository
public class UsageRepository extends BaseRepository implements IUsageRepository {

    @Override
    public int insertUsage(Usage usage) {
        return insert("IUsageMapper.insertUsage", checkNotNull(usage));
    }

    @Override
    public List<UsageDto> findByFilter(UsageFilter filter, Pageable pageable, Sort sort) {
        return Collections.emptyList();
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return 0;
    }
}
