package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Represents interface of service for usage business logic.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/17
 *
 * @author Aliaksei Pchelnikau
 */
@Service
public class UsageService implements IUsageService {

    @Autowired
    private IUsageRepository usageRepository;

    @Override
    public List<UsageDto> getUsages(UsageFilter filter, Pageable pageable, Sort sort) {
        List<UsageDto> result = Collections.emptyList();
        if (!filter.isEmpty()) {
            result = usageRepository.findByFilter(filter, pageable, sort);
        }
        return result;
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        int result = 0;
        if (!filter.isEmpty()) {
            result = usageRepository.getUsagesCount(filter);
        }
        return result;
    }
}
