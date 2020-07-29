package com.copyright.rup.dist.foreign.service.impl.sal;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link ISalUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
@Service
public class SalUsageService implements ISalUsageService {

    @Autowired
    private ISalUsageRepository salUsageRepository;

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? salUsageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? salUsageRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }
}
