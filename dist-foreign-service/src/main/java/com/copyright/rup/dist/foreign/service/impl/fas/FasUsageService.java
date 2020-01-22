package com.copyright.rup.dist.foreign.service.impl.fas;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IFasUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/22/2020
 *
 * @author Ihar Suvorau
 */
@Service
public class FasUsageService implements IFasUsageService {

    @Autowired
    private IUsageRepository usageRepository;

    @Override
    public List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? usageRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? usageRepository.findCountByFilter(filter) : 0;
    }
}
