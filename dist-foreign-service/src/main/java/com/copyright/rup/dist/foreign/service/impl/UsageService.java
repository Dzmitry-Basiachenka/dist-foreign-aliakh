package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
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
        return !filter.isEmpty()
            ? usageRepository.findByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? usageRepository.getUsagesCount(filter) : 0;
    }

    @Override
    public void writeUsageCsvReport(UsageFilter filter, OutputStream outputStream) {
        usageRepository.writeUsagesCsvReport(filter, outputStream);
    }
}
