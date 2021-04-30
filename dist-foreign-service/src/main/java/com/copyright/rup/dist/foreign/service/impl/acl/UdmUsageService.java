package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link IUdmUsageService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/30/2021
 *
 * @author Uladzislau Shalamitski
 */
@Service
public class UdmUsageService implements IUdmUsageService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUdmUsageRepository udmUsageRepository;

    @Override
    @Transactional
    public void insertUdmUsages(UdmBatch udmBatch, List<UdmUsage> udmUsages) {
        String userName = RupContextUtils.getUserName();
        int size = udmUsages.size();
        LOGGER.info("Insert UDM usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}", udmBatch.getName(),
            size, userName);
        udmUsages.forEach(usage -> {
            usage.setBatchId(udmBatch.getId());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            udmUsageRepository.insert(usage);
        });
        LOGGER.info("Insert UDM usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}", udmBatch.getName(),
            size, userName);
    }

    @Override
    public boolean isOriginalDetailIdExist(String originalDetailId) {
        return udmUsageRepository.isOriginalDetailIdExist(originalDetailId);
    }

    @Override
    public List<UdmUsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? udmUsageRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? udmUsageRepository.findCountByFilter(filter) : 0;
    }
}
