package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.slf4j.Logger;
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

    private static final Logger LOGGER = RupLogUtils.getLogger();

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

    @Override
    public int insertUsages(UsageBatch usageBatch, List<Usage> usages, String userName) {
        int size = usages.size();
        LOGGER.info("Insert usages. Started. Usages={}, UserName={}", size, userName);
        usages.forEach(usage -> {
            usage.setBatchId(usageBatch.getId());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            usageRepository.insertUsage(usage);
        });
        LOGGER.info("Insert usages. Finished. Usages={}, UserName={}", size, userName);
        return size;
    }

    @Override
    public void deleteUsageBatchDetails(String batchId) {
        usageRepository.deleteUsageBatchDetails(batchId);
    }
}
